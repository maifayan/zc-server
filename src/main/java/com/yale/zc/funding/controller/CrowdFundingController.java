package com.yale.zc.funding.controller;

import java.util.UUID;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.funding.bean.CrowdFunding;
import com.yale.zc.funding.service.CrowdFundingService;
import com.yale.zc.funding.vo.CrowdFundingVo;
import com.yale.zc.funding.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 * 
 * @author victorZhang
 * @email 
 * @date 2017-12-26 15:06:13
 */
@RestController
@RequestMapping("/crowdfunding")
public class CrowdFundingController {
	@Autowired
	private CrowdFundingService crowdFundingService;



	/**
	 * 添加
	 * @param vo
	 * @param token
	 * @return
	 */
	@RequestMapping("/add")
	public RespMessage add(CrowdFundingVo vo, String token) {

		return crowdFundingService.add(vo, token);
	}

	/**
	 * 修改
	 */
	@RequestMapping("/update")
	public RespMessage update( CrowdFunding vo, String token){

		return crowdFundingService.update(vo, token);
	}

	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	public RespMessage delete( String id, String token){
		return crowdFundingService.delete(id,token);
    }

	/**
	 * 批量删除
	 */
	@RequestMapping("/deleteBatch")
	public RespMessage delete(String[] ids, String token){
		return  crowdFundingService.deleteBatch(ids,token);

	}

	/**
	 * 展示所有众筹
	 * @return
	 */
	@RequestMapping("/list")
	public RespMessage list(PageVo vo) {
		return crowdFundingService.list(vo);
	}


	@RequestMapping("/search")
	public RespMessage search(String id) {

		return crowdFundingService.queryObject(id);
	}


}
