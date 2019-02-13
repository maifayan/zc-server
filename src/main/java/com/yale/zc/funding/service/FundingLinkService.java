package com.yale.zc.funding.service;

import com.yale.zc.funding.bean.FundingLink;
import com.yale.zc.funding.bean.FundingLink;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author victorZhang
 * @email 
 * @date 2018-01-02 14:15:40
 */
public interface FundingLinkService {

	FundingLink queryObject(String id);
	
	List<FundingLink> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(FundingLink fundingLink);
	
	void update(FundingLink fundingLink);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
