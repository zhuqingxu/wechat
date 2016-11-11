package com.fangxin365.wechat.wechat.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fangxin365.core.commons.BaseController;
import com.fangxin365.wechat.wechat.service.WechatService;
import com.fangxin365.wechat.wechat.util.SHA1;

@Controller
public class WechatController extends BaseController {
	
	private String Token = "xamxlxsmxq";
	
	@Autowired
	private WechatService wechatService;
	
	@RequestMapping(value = "/chat", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public void chat(Model model, HttpServletRequest request, HttpServletResponse response) {
        logger.info("进入chat");
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        if (isGet) {
            access(request, response);
        } else {
            // 进入POST聊天处理
            logger.info("enter post");
            try {
                // 接收消息并返回消息
                acceptMessage(request, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	
	/**
	 * 验证URL真实性
	 */
	private String access(HttpServletRequest request, HttpServletResponse response) {
		// 验证URL真实性
		logger.info("进入验证access");
		
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		logger.info("signature=" + signature);
        logger.info("timestamp=" + timestamp);
        logger.info("nonce=" + nonce);
        logger.info("echostr=" + echostr);
		
		List<String> params = new ArrayList<String>();
		params.add(Token);
		params.add(timestamp);
		params.add(nonce);
		
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		Collections.sort(params, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));
		if (temp.equals(signature)) {
			try {
				response.getWriter().write(echostr);
				logger.info("成功返回 echostr：" + echostr);
				return echostr;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("认证失败");
		return null;
	}
	
	private void acceptMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
		
		// 调用核心业务类接收消息、处理消息
		String respMessage = wechatService.processRequest(request);
		
		// 响应消息
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(respMessage);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			out = null;
		}
	}

}
