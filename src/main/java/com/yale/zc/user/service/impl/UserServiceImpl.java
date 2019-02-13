package com.yale.zc.user.service.impl;

import com.yale.zc.core.config.Constant;
import com.yale.zc.core.config.RedisConfig;
import com.yale.zc.core.dao.JedisClient;
import com.yale.zc.core.util.*;

import com.yale.zc.system.bean.SysMessageToUser;
import com.yale.zc.system.bean.UserCertLog;
import com.yale.zc.user.bean.User;
import com.yale.zc.user.bean.UserDetail;
import com.yale.zc.user.bean.UserWx;
import com.yale.zc.user.dao.*;
import com.yale.zc.user.service.UserService;
import com.yale.zc.user.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 用户Service实现
 *
 * @author GrayFox on 20170619
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    JedisClient jedisClient;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserWxMapper userWxMapper;

    @Autowired
    UserDetailMapper userDetailMapper;


    @Autowired
    UserService userService;


    @Override
    public UserRedisVo getUserByToken(String token) {

        //根据token从redis中查询用户信息
        String json = jedisClient.get(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token);
        //判断是否为空
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        //更新过期时间
        jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);
        //返回用户信息
        return JsonUtils.jsonToPojo(json, UserRedisVo.class);

    }

    @Override
    public RespMessage signIn(SignInVo vo, String token) {

        LOGGER.info("#phone:" + vo.getPhone() + "#pass:" + vo.getPassword() + "#token:" + token);
        // 按手机号查询用户
        User user = userMapper.selectByPhone(vo.getPhone());
        if (user == null) {
            LOGGER.info("手机号未注册!");
            return RespMessage.fail(400, "手机号未注册!");
        }
        // 校验密码
        if (!DigestUtils.md5DigestAsHex(vo.getPassword().getBytes()).equals(user.getPassword())) {
            LOGGER.info("密码错误!");
            return RespMessage.fail(400, "密码错误");
        }
        // 生成token
        if (StringUtils.isEmpty(token)) {
            token = UUID.randomUUID().toString();
        }
        // 存入redis
        // 保存用户之前，把用户对象中的密码清空。
        user.setPassword(null);

        UserRedisVo userRedisVo = new UserRedisVo();
        userRedisVo.setUser(user);

        // 查询微信信息
        UserWx userWx = userWxMapper.selectByUserId(user.getId());
        userRedisVo.setUserWx(userWx);

        // 把用户信息写入redis
        jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(userRedisVo));
        // 设置session的过期时间
        jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);


        // 返回token和用户信息
        SignInResultVo result = new SignInResultVo();
        result.setToken(token);
        result.setUserInfoWrapper(userRedisVo);
        return RespMessage.success(result);
    }

    @Override
    public RespMessage signOut(String token) {

        // 清除redis
        jedisClient.del(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token);
        return RespMessage.success();
    }

    @Override
    public RespMessage sendSms(String phone) {
        // 检查是否在保护时间内
        String protectionKey = RedisConfig.REDIS_SMS_PROTECTION_PREFIX + phone;
        String protection = jedisClient.get(protectionKey);
        if (protection != null) {
            LOGGER.info("获取验证码过于频繁，请稍后再试!");
            return RespMessage.fail(400, "获取验证码过于频繁，请稍后再试");
        }

        String key = RedisConfig.REDIS_SMS_PREFIX + phone;
        String random = RandomUtil.getRandom();
        jedisClient.set(key, random);
        jedisClient.expire(key, RedisConfig.REDIS_SMS_EXPIRE);
        // 设置保护时间
        jedisClient.set(protectionKey, "protection");
        jedisClient.expire(protectionKey, RedisConfig.REDIS_SMS_PROTECTION_EXPIRE);

        String smsValidate = RedisConfig.REDIS_SMS_VALIDATE;
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("code", random);
        paramMap.put("product", "房公信");
//        SmsUtil.send(phone, "房公信", paramMap, smsValidate);
        return RespMessage.success();
    }

    @Override
    public RespMessage signInSms(SignInSmsVo vo, String token) {
        // 按手机号查询用户
        User user = userMapper.selectByPhone(vo.getPhone());
        if (user == null) {
            LOGGER.info("手机号未注册!");
            return RespMessage.fail(400, "手机号未注册!");
        }
        // 校验手机验证码
        // 从redis取出code
        String key = RedisConfig.REDIS_SMS_PREFIX + vo.getPhone();
        String random = jedisClient.get(key);
        if (random == null || vo.getCode() == null || "".equals(vo.getCode())) {
            LOGGER.info("验证码不正确!");
            return RespMessage.fail(400, "验证码不正确");
        }
        if (!random.equals(vo.getCode())) {
            LOGGER.info("验证码不正确");
            return RespMessage.fail(400, "验证码不正确");
        }
        // 清除被验证过的code
        jedisClient.del(key);

        // 生成token
        if (StringUtils.isEmpty(token)) {
            token = UUID.randomUUID().toString();
        }
        // 存入redis
        // 保存用户之前，把用户对象中的密码清空。
        user.setPassword(null);

        UserRedisVo userRedisVo = new UserRedisVo();
        userRedisVo.setUser(user);

        // 查询微信信息
        UserWx userWx = userWxMapper.selectByUserId(user.getId());
        userRedisVo.setUserWx(userWx);

        // 把用户信息写入redis
        jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(userRedisVo));
        // 设置session的过期时间
        jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);

        // 返回token和用户信息
        SignInResultVo result = new SignInResultVo();
        result.setToken(token);
        result.setUserInfoWrapper(userRedisVo);
        return RespMessage.success(result);
    }

    @Override
    //手机号绑定页面，绑定手机号功能
    public RespMessage signUp(SignUpVo vo, String token) {

        LOGGER.info("#sign up token:" + token);

        if (StringUtils.isEmpty(vo.getPhone())) {
            LOGGER.info("手机号不能为空");
            return RespMessage.fail(400, "手机号不能为空");
        }

        if (StringUtils.isEmpty(vo.getCode())) {
            LOGGER.info("验证码不能为空");
            return RespMessage.fail(400, "验证码不能为空");
        }

        // if (StringUtils.isEmpty(vo.getPassword())) {
        //     return RespMessage.fail(400, "密码不能为空");
        // }

        // 校验手机验证码
        // 从redis取出code
        String key = RedisConfig.REDIS_SMS_PREFIX + vo.getPhone();
        String random = jedisClient.get(key);
        if (random == null || vo.getCode() == null || "".equals(vo.getCode())) {
            LOGGER.info("验证码不正确");
            return RespMessage.fail(400, "验证码不正确");
        }
        if (!random.equals(vo.getCode())) {
            LOGGER.info("验证码不正确!");
            return RespMessage.fail(400, "验证码不正确");
        }
        // 清除被验证过的code
        jedisClient.del(key);

        // 校验手机号
        User existUser = userMapper.selectByPhone(vo.getPhone());
        //  if (existUser != null) {  //根据手机号自动关联之前WEB页面注册过的账号

        //     return RespMessage.fail(400, "该手机号已经被注册");

        //  }

        // token换取用户信息
        UserRedisVo currentUser = getUserByToken(token);
        if (currentUser == null) {
            LOGGER.info("此session已经过期，请重新登录");
            return RespMessage.fail(400, "此session已经过期，请重新登录");
        }

        User updateUser = currentUser.getUser();
        if (existUser == null) {  //如果手机号之前没注册过
            LOGGER.info("#update user");
            // 更新数据库
            BeanUtils.copyProperties(vo, updateUser);
            updateUser.setPassword(DigestUtils.md5DigestAsHex(vo.getPassword().getBytes()));
            updateUser.setUpdatedAt(null);
            userMapper.updateByPrimaryKeySelective(updateUser);

            // 更新redis
            currentUser.setUser(updateUser);

        } else {   //如果手机号注册过，自动关联之前账号
            // 更新User
            LOGGER.info("#手机号已经被注册，自动关联");
            BeanUtils.copyProperties(vo, existUser);
            if (!StringUtils.isEmpty(vo.getPassword())) {
                existUser.setPassword(DigestUtils.md5DigestAsHex(vo.getPassword().getBytes()));
            }
            existUser.setUpdatedAt(new Date());
            if (!StringUtils.isEmpty(updateUser.getRefereeId())) {
                existUser.setRefereeId(updateUser.getRefereeId());   //复制推荐人ID
            }

            userMapper.updateByPrimaryKeySelective(existUser);

            //更新userWX
            UserWx userWx = currentUser.getUserWx();
            String oldUserId = userWx.getUserId();
            userWx.setUserId(existUser.getId());   //关联之前web页面注册过的userId
            LOGGER.info("#oldUserId:" + oldUserId + "#new userId:" + existUser.getId());
            userWxMapper.updateByPrimaryKey(userWx);
            // userMapper.deleteByPrimaryKey(oldUserId); //删除微信授权时，自动生成的临时user账号   ps: 先保留
            // 更新redis
            currentUser.setUser(existUser);
            currentUser.setUserWx(userWx);
        }

        // 把用户信息写入redis
        jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(currentUser));
        // 设置session的过期时间
        jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);

        // 返回token和用户信息
        SignInResultVo result = new SignInResultVo();
        result.setToken(token);
        result.setUserInfoWrapper(currentUser);
        return RespMessage.success(result);
    }

    @Override
    public RespMessage webSignUp(SignUpVo vo) {

        if (StringUtils.isEmpty(vo.getPhone())) {
            LOGGER.info("手机号不能为空");
            return RespMessage.fail(400, "手机号不能为空");
        }

        if (StringUtils.isEmpty(vo.getCode())) {
            LOGGER.info("验证码不能为空");
            return RespMessage.fail(400, "验证码不能为空");
        }

        if (StringUtils.isEmpty(vo.getPassword())) {
            LOGGER.info("密码不能为空");
            return RespMessage.fail(400, "密码不能为空");
        }

        // 校验手机验证码
        // 从redis取出code
        String key = RedisConfig.REDIS_SMS_PREFIX + vo.getPhone();
        String random = jedisClient.get(key);
        LOGGER.info("#random:" + random);
        if (random == null || vo.getCode() == null || "".equals(vo.getCode())) {
            LOGGER.info("验证码不正确");
            return RespMessage.fail(400, "验证码不正确");
        }
        if (!random.equals(vo.getCode())) {
            LOGGER.info("验证码不正确!");
            return RespMessage.fail(400, "验证码不正确");
        }
        // 清除被验证过的code
        jedisClient.del(key);

        // 校验手机号
        User existUser = userMapper.selectByPhone(vo.getPhone());
        if (existUser != null) {
            LOGGER.info("该手机号已经被注册");
            return RespMessage.fail(400, "该手机号已经被注册");
        }


        // 新建user
        User user = new User();
        String userId = UUID.randomUUID().toString();
        user.setId(userId);
        user.setPassword(DigestUtils.md5DigestAsHex(vo.getPassword().getBytes()));
        user.setPhone(vo.getPhone());
        user.setNickname(vo.getNickname());
        userMapper.insertSelective(user);

        String token = UUID.randomUUID().toString();
        LOGGER.info("#token:" + token);
        user.setPassword(null);

        UserRedisVo userRedisVo = new UserRedisVo();
        userRedisVo.setUser(user);
        userRedisVo.setUserWx(null);

        // 把用户信息写入redis
        jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(userRedisVo));
        // 设置session的过期时间
        jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);


        // 返回token和用户信息
        SignInResultVo result = new SignInResultVo();
        result.setToken(token);
        result.setUserInfoWrapper(userRedisVo);
        return RespMessage.success(result);
    }


    @Override
    public RespMessage preSignUp(UserWxIOSVo vo) {
        // 如果该用户已经微信授权登录过 就不让他再往数据库插入数据，直接返回他之前插入的数据和新生成的token并更新redis 缓存 因为ios app会有退出删除token功能
        UserWx userWx1 = userWxMapper.selectByOpenId(vo.getOpenid());
        if (userWx1 != null) {
            UserRedisVo userRedisVo = new UserRedisVo();
            userRedisVo.setUserWx(userWx1);
            User user = userMapper.selectByPrimaryKey(userWx1.getUserId());
            user.setPassword(null);
            userRedisVo.setUser(user);
            String token = UUID.randomUUID().toString();
            // 把用户信息写入redis
            jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(userRedisVo));
            // 设置session的过期时间
            jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);
            SignInResultVo result = new SignInResultVo();
            result.setToken(token);
            result.setUserInfoWrapper(userRedisVo);
            return RespMessage.success(result);
        }
        // 之前用户没有插入数据过   我们就新增user和userWx保存用户信息
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        userMapper.insert(user);
        // 新增userWx
        UserWx userWx = new UserWx();
        BeanUtils.copyProperties(vo, userWx);
        userWx.setId(UUID.randomUUID().toString());
        userWx.setUserId(user.getId());
        userWx.setHeadImageUrl(vo.getHeadimgurl());
        userWxMapper.insert(userWx);

        // 生成token
        String token = UUID.randomUUID().toString();
        // 存入redis
        // 保存用户之前，把用户对象中的密码清空。
        user.setPassword(null);

        UserRedisVo userRedisVo = new UserRedisVo();
        userRedisVo.setUser(user);

        // 查询微信信息
        userRedisVo.setUserWx(userWx);

        // 把用户信息写入redis
        jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(userRedisVo));
        // 设置session的过期时间
        jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);

        // 返回token和用户信息
        SignInResultVo result = new SignInResultVo();
        result.setToken(token);
        result.setUserInfoWrapper(userRedisVo);
        return RespMessage.success(result);
    }

    @Override
    public RespMessage resetPassword(ResetPasswordVo vo, String token) {

        if (StringUtils.isEmpty(vo.getPhone())) {
            return RespMessage.fail(400, "手机号不能为空");
        }

        if (StringUtils.isEmpty(vo.getCode())) {
            return RespMessage.fail(400, "验证码不能为空");
        }

        if (StringUtils.isEmpty(vo.getPassword())) {
            return RespMessage.fail(400, "密码不能为空");
        }

        // 按手机号查询用户
        User user = userMapper.selectByPhone(vo.getPhone());
        if (user == null) {
            return RespMessage.fail(400, "手机号错误");
        }
        // 校验手机验证码
        // 从redis取出code
        String key = RedisConfig.REDIS_SMS_PREFIX + vo.getPhone();
        String random = jedisClient.get(key);
        if (random == null || vo.getCode() == null || "".equals(vo.getCode())) {
            return RespMessage.fail(400, "验证码不正确");
        }
        if (!random.equals(vo.getCode())) {
            return RespMessage.fail(400, "验证码不正确");
        }
        // 清除被验证过的code
        jedisClient.del(key);

        // token换取用户信息
        UserRedisVo currentUser = getUserByToken(token);
        if (currentUser == null) {
            return RespMessage.fail(400, "此session已经过期，请重新登录");
        }

        // 存入redis
        // 保存用户之前，把用户对象中的密码清空。
        user.setPassword(DigestUtils.md5DigestAsHex(vo.getPassword().getBytes()));

        return RespMessage.success("修改成功");
    }

    @Override
    public RespMessage realnameCert(RealNameCertVo vo, String token) {


        User user = new User();
        UserRedisVo currentUser = getUserByToken(token);
        if (currentUser == null) {

            return RespMessage.fail(400, "登录超时，请重新登录");
        }
        user.setId(currentUser.getUser().getId());
        BeanUtils.copyProperties(vo, user);

        // 保护
        int status = currentUser.getUser().getRealnameCertStatus();
        if (status == Constant.AUDIT_STATUS_OK) {
            return RespMessage.fail(400, "已认证通过，无需重复提交");
        }
        LOGGER.info("#sex:" + vo.getSex());
        if (!StringUtils.isEmpty(vo.getSex())) {
            user.setSex(vo.getSex());
        }

        user.setRealnameCertStatus(Constant.AUDIT_STATUS_SUBMITTED);

        // 同步redis
        // 把用户信息写入redis
        currentUser.getUser().setRealnameCertStatus(Constant.AUDIT_STATUS_SUBMITTED);
        if (!StringUtils.isEmpty(vo.getSex())) {
            currentUser.getUser().setSex(vo.getSex());
        }
        jedisClient.set(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(currentUser));
        // 设置session的过期时间
        jedisClient.expire(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, RedisConfig.REDIS_SESSION_EXPIRE);

        userMapper.updateByPrimaryKeySelective(user);

        return RespMessage.success();
    }

    @Override
    public RespMessage addBankDetail(UserDetail vo, String token) {

        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        UserDetail userDetail = userDetailMapper.selectByUserId(user.getId());
        if (userDetail != null) {
            return RespMessage.success("您已经添加过银行卡信息");
        }

        vo.setId(UUID.randomUUID().toString());
        vo.setUserId(user.getId());
        userDetailMapper.insert(vo);

        return RespMessage.success();
    }


    public RespMessage mark(String userId, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        String uId = user.getId();
        if (StringUtils.isEmpty(userId)) {
            return RespMessage.fail(400, "userId不能为空");
        }


        return RespMessage.success();
    }

    @Override
    public RespMessage updateUserWx(UserWxVo userWxVo, String token) {

        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        String userId = user.getId();
        //更新个人信息（微信用户）
        UserWx userWx = userWxMapper.selectByUserId(userId);
        UserWx updateUserWx = new UserWx();
        BeanUtils.copyProperties(userWxVo, userWx);
        updateUserWx.setId(userWx.getId());
        if (userWx.getUpdatedAt() != null) {
            userWx.setUpdatedAt(null);
        }
        updateUserWx.setUpdatedAt(new Date());
        userWxMapper.insertSelective(updateUserWx);
        return RespMessage.success();
    }

    @Override
    public RespMessage updateBank(UserDetailVo userDetailVo, String token) {

        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        UserDetail userDetail = userDetailMapper.selectByUserId(user.getId());
        if (userDetail == null) {
            return RespMessage.success("你尚未添加过银行卡信息");
        }

        //修改银行卡信息
        UserDetail updateUserDetail = new UserDetail();
        BeanUtils.copyProperties(userDetailVo, updateUserDetail);
        updateUserDetail.setId(userDetail.getId());
        updateUserDetail.setUserId(userDetail.getUserId());
        updateUserDetail.setUpdatedAt(new Date());
        userDetailMapper.updateByPrimaryKey(updateUserDetail);
        return RespMessage.success();
    }

    public RespMessage deleteMark(String userId, String token) {

        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }

        String markedBy = user.getId();

        return RespMessage.success();
    }

    @Override
    public RespMessage selectRealnameCert(String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        String userId = user.getId();
        // List<UserCertLog> userCertLogs = userCertLogMapper.selectByUserId(userId);
        ///  return RespMessage.success(userCertLogs);
        return null;
    }

    @Override
    public RespMessage queryByUserId(String userId) {
        UserRedisVo userRedisVo = new UserRedisVo();
        userRedisVo.setUser(userMapper.selectByPrimaryKey(userId));
        userRedisVo.setUserWx(userWxMapper.selectByUserId(userId));
        //      userRedisVo.setUserAgent(userAgentMapper.selectByUserId(userId));
        return RespMessage.success(userRedisVo);
    }


    @Override
    public RespMessage selectByUserId(String userId) {
        //    List<SysMessageToUser> sysMessageToUsers = sysMessageToUserMapper.selectByUserId(userId);
        //  return RespMessage.success(sysMessageToUsers);
        return null;
    }

    @Override
    public RespMessage deleteMessage(String id, String token) {

        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        String myId = user.getId();

//        String userId = sysMessageToUserMapper.selectByPrimaryKey(id).getUserId();
//        if (myId == userId) {
//            sysMessageToUserMapper.deleteByPrimaryKey(id);
//            return RespMessage.success();
//        } else {
//            return RespMessage.fail("这不是你的消息，你无权删除");
//        }
        return RespMessage.success();
    }

    public RespMessage deleteAll(List<SysMessageToUser> sysMessageToUsers, String token) {

        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            LOGGER.info("登录超时");
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            LOGGER.info("用户不存在");
            return RespMessage.fail("用户不存在");
        }
        String myId = user.getId();
        for (SysMessageToUser sys : sysMessageToUsers) {
            if (myId == sys.getUserId()) {
//                sysMessageToUserMapper.deleteByPrimaryKey(sys.getId());
            } else {
                LOGGER.info("这不是你的消息，你无权删除");
                return RespMessage.fail("这不是你的消息，你无权删除");
            }
        }
        return RespMessage.success();
    }

    @Override
    public RespMessage queryUserDetail(String userId, String token) {
        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            LOGGER.info("登录超时");
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            LOGGER.info("用户不存在");
            return RespMessage.fail("用户不存在");
        }
        UserDetail userDetail = userDetailMapper.selectByUserId(userId);
        if (userDetail == null) {
            LOGGER.info("您尚未添加过银行卡信息");
            return RespMessage.success("您尚未添加过银行卡信息");
        }
        return RespMessage.success(userDetail);
    }

    @Override
    public RespMessage updateUser(User vo, String token) {

        LOGGER.info("#token:" + token + "#image:" + vo.getHeadImageUrl() + "#sex:" + vo.getSex() + "#name:" + vo.getNickname());

        UserRedisVo userRedisVo = userService.getUserByToken(token);
        if (userRedisVo == null) {
            return RespMessage.fail(400, "登录超时");
        }
        User user = userRedisVo.getUser();
        if (user == null) {
            return RespMessage.fail("用户不存在");
        }
        if (!StringUtils.isEmpty(vo.getNickname())) {
            user.setNickname(vo.getNickname());
        }
        if (!StringUtils.isEmpty(vo.getHeadImageUrl())) {
            user.setHeadImageUrl(vo.getHeadImageUrl());
            //更新当前用户发布的二手房信息的头像和昵称

        }

        //更新当前用户发布的租房信息的头像和昵称


        return null;
    }
}
