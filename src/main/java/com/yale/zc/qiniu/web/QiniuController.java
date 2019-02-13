package com.yale.zc.qiniu.web;

import com.yale.zc.core.util.RespMessage;
import com.yale.zc.qiniu.service.QiniuService;
import com.yale.zc.qiniu.vo.QiniuCallbackVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 七牛服务controller
 * @author GrayFox
 *
 */
@RestController
@RequestMapping(value = "/qiniu")
public class QiniuController {

	@Autowired
	QiniuService qiniuService;

	/**
	 * 获取上传token
	 * @return
	 */
	@RequestMapping(value = "/token")
	@ResponseBody
	public RespMessage token() {
		return qiniuService.token();
	}

	/**
	 * 上传回调
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/callback")
	@ResponseBody
	public RespMessage calloback(@RequestBody QiniuCallbackVo vo) {
		return qiniuService.callback(vo);
	}


	/**
	 * 上传个人名片
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/createCard")
	@ResponseBody
	public RespMessage createCard(String token)  throws IOException {
		return qiniuService.createCard(token);
	}

}
