package com.yale.zc.funding.dao;

import com.yale.zc.funding.bean.CrowdFunding;

import com.yale.zc.funding.vo.CrowdFundingVo;
import com.yale.zc.system.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author victorZhang
 * @email 
 * @date 2017-12-26 15:06:13
 */
@Mapper
public interface CrowdFundingMapper extends BaseDao<CrowdFunding> {

    List<CrowdFunding> queryAll();
    List<CrowdFundingVo> queryAllVo();
}
