package com.yale.zc.funding.service.impl;

import com.yale.zc.funding.dao.FundingLinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.yale.zc.funding.dao.FundingLinkMapper;
import com.yale.zc.funding.bean.FundingLink;
import com.yale.zc.funding.service.FundingLinkService;



@Service("fundingLinkService")
public class FundingLinkServiceImpl implements FundingLinkService {
	@Autowired
	private FundingLinkMapper fundingLinkDao;
	
	@Override
	public FundingLink queryObject(String id){
		return fundingLinkDao.queryObject(id);
	}
	
	@Override
	public List<FundingLink> queryList(Map<String, Object> map){
		return fundingLinkDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return fundingLinkDao.queryTotal(map);
	}
	
	@Override
	public void save(FundingLink fundingLink){
		fundingLinkDao.save(fundingLink);
	}
	
	@Override
	public void update(FundingLink fundingLink){
		fundingLinkDao.update(fundingLink);
	}
	
	@Override
	public void delete(String id){
		fundingLinkDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		fundingLinkDao.deleteBatch(ids);
	}
	
}
