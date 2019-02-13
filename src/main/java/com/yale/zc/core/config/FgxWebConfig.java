package com.yale.zc.core.config;

import com.yale.zc.core.dao.JedisClient;
import com.yale.zc.core.util.JsonUtils;
import com.yale.zc.core.util.RespMessage;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Created by chenmingfa on 2017/7/17.
 */
@Configuration
public class FgxWebConfig extends WebMvcConfigurerAdapter {
/*
    @Autowired
    JedisClient jedisClient;

    @Autowired
    SysRoleModuleMapper sysRoleModuleMapper;

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    SysModuleMapper sysModuleMapper;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    UserService userService;

    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new HandlerInterceptorAdapter() {

            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

                String token = request.getParameter("token");
                SysUser sysUser = sysUserService.getSysUserByToken(token);
                if(sysUser == null){
                    writeJsonResponse(response, RespMessage.fail(400, "登录超时，请重新登录"));
                    return false;
                }

                if (!checkPermission(request, sysUser)) {
                    writeJsonResponse(response, RespMessage.fail(400, "没有操作该模块的权限"));
                    return false;
                }

                sysUser.setPassword(null);
                refreshToken(RedisConfig.REDIS_SYS_USER_SESSION_KEY + ":" + token, sysUser);

                return true;
            }

        }).addPathPatterns("/sys*//**")
                .excludePathPatterns("/sys/sign-in")
                .excludePathPatterns("/sys/commission/get-default");

//        registry.addInterceptor(new HandlerInterceptorAdapter() {
//
//            @Override
//            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//                String token = request.getParameter("token");
//                UserRedisVo userRedisVo = userService.getUserByToken(token);
//                if(userRedisVo == null || userRedisVo.getUser() == null){
//                    writeJsonResponse(response, RespMessage.fail(400, "登录超时，请重新登录"));
//                    return false;
//                }
//
//                refreshToken(RedisConfig.REDIS_USER_SESSION_KEY + ":" + token, userRedisVo);
//
//                return true;
//            }
//
//        }).addPathPatterns("*//**")
//                .excludePathPatterns("/sys*//**")
//                .excludePathPatterns("/user/token")
//                .excludePathPatterns("/weixin*//**");
    }

    private void writeJsonResponse(HttpServletResponse response, RespMessage message) throws IOException {
        JSONObject responseJSONObject = JSONObject.fromObject(message);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(responseJSONObject.toString());
        out.flush();
    }

    private void refreshToken(String key, Object json) {
        jedisClient.set(key, JsonUtils.objectToJson(json));
        jedisClient.expire(key, RedisConfig.REDIS_SESSION_EXPIRE);
    }

    private boolean checkPermission(HttpServletRequest request, SysUser sysUser) {
        // 校验权限
        // 如果角色是管理员 放行
        SysRole role = sysRoleMapper.selectByPrimaryKey(sysUser.getSysRoleId());
        if (Constant.SYS_ROLE_ADMIN.equals(role.getName())) {
            return true;
        }

        // 非管理员 检查权限
        String uri = request.getRequestURI();
        String requestPath = uri.replace("/sys/", "");
        List<SysRoleModule> modules = sysRoleModuleMapper.selectByRoleId(sysUser.getSysRoleId());
        for (SysRoleModule module : modules) {
            SysModule sysModule = sysModuleMapper.selectByPrimaryKey(module.getModuleId());
            if (sysModule == null) {
                continue;
            }
            if (requestPath.equals(sysModule.getPath())) {
                return true;
            }
        }
        return false;
    }*/
}

