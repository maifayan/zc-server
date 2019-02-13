package com.yale.zc.funding.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.CrowdFunding;
import com.yale.zc.funding.bean.FundingLink;
import com.yale.zc.funding.dao.CrowdFundingMapper;
import com.yale.zc.funding.dao.FundingLinkMapper;
import com.yale.zc.funding.service.CrowdFundingService;
import com.yale.zc.funding.vo.CrowdFundingResultVo;
import com.yale.zc.funding.vo.CrowdFundingVo;
import com.yale.zc.funding.vo.PageVo;
import com.yale.zc.user.service.UserService;
import com.yale.zc.user.vo.UserRedisVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by asus on 2017/12/26.
 */
@Service
public class CrowdFundingServiceImpl implements CrowdFundingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CrowdFundingServiceImpl.class);

    @Autowired
    UserService userService;

    @Autowired
    CrowdFundingMapper crowdFundingMapper;

    @Autowired
    FundingLinkMapper fundingLinkMapper;


    @Override
    public RespMessage add(CrowdFundingVo vo, String token ){

        UserRedisVo currentUser = userService.getUserByToken(token);
        if (currentUser == null) {
            return RespMessage.fail(400, "登录超时");
        }

        JSONObject jsonObj = JSONObject.fromObject(vo);
        LOGGER.info("#add zc vo:"+jsonObj.toString());

        CrowdFunding crowdFunding = new CrowdFunding();
        vo.setId(UUID.randomUUID().toString());
        vo.setPublisherId(currentUser.getUser().getId());

        BeanUtils.copyProperties(vo, crowdFunding);
        crowdFundingMapper.save(crowdFunding);

        //插入图片 视频链接等
         String urls = vo.getUrls();
         if (!StringUtils.isEmpty(urls)){
             JSONArray jsonArray = JSONArray.fromObject(urls);
             List<FundingLink> fundingLinks= (List<FundingLink>)JSONArray.toCollection(jsonArray, FundingLink.class);

             for (FundingLink fundingLink : fundingLinks) {
                 LOGGER.info("#linkUrl:"+fundingLink.getLinkUrl()+"####type:"+fundingLink.getType()+"#desc:"+fundingLink.getDesc());
                 fundingLink.setId(UUID.randomUUID().toString());
                 fundingLink.setFundingId(vo.getId());
                 fundingLinkMapper.save(fundingLink);
             }
            }

        return RespMessage.success();
    }



    @Override
    public RespMessage update(CrowdFunding vo, String token ){

        UserRedisVo currentUser = userService.getUserByToken(token);
        if (currentUser == null) {
            return RespMessage.fail(400, "登录超时");
        }

        JSONObject jsonObj = JSONObject.fromObject(vo);
        LOGGER.info("#update zc vo:"+jsonObj.toString());

        crowdFundingMapper.update(vo);

        return RespMessage.success();
    }


    @Override
    public RespMessage delete(String id, String token){
        LOGGER.info("#delete id:"+id);
        UserRedisVo currentUser = userService.getUserByToken(token);
        if (currentUser == null) {
            return RespMessage.fail(400, "登录超时");
        }

        crowdFundingMapper.delete(id);
        fundingLinkMapper.deleteByFundingId(id);
        return RespMessage.success();
    }

    @Override
    public RespMessage deleteBatch(String[] ids, String token){

        crowdFundingMapper.deleteBatch(ids);
        return RespMessage.success();
    }


     @Override
    public RespMessage list(PageVo vo) {
        if (vo.getPageNum() == null || vo.getPageSize() == null) {
            PageHelper.startPage(1, 10);
        } else {
            PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        }

        List<CrowdFundingVo> crowdFundingVoList = crowdFundingMapper.queryAllVo();
        List<CrowdFundingResultVo> crowdFundingResultVoList = getCrowdFundingResult(crowdFundingVoList);
         PageInfo pageResult = new PageInfo(crowdFundingResultVoList);

         return RespMessage.success(pageResult);

    }

    public List<CrowdFundingResultVo> getCrowdFundingResult(List<CrowdFundingVo> crowdFundingVoList) {
        List<CrowdFundingResultVo> crowdFundingResultList = new ArrayList<>();

         for (CrowdFundingVo crowdFundingVo : crowdFundingVoList) {
             List<FundingLink> fundingLinks = fundingLinkMapper.queryByFundingId(crowdFundingVo.getId());
             CrowdFundingResultVo result = new CrowdFundingResultVo();
             result.setCrowdFundingVo(crowdFundingVo);
             result.setUrls(fundingLinks);
             crowdFundingResultList.add(result);
        }

        return crowdFundingResultList;

    }


   // @Override
    public RespMessage queryObject(String id){
        LOGGER.info("#query Object:"+id);
        CrowdFunding crowdFunding = crowdFundingMapper.queryObject(id);

        return RespMessage.success(crowdFunding);

    }


}
