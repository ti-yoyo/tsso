package com.tinet.tsso.auth.controller;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.UserModel;
import com.tinet.tsso.auth.model.UsernameAndUuidModel;
import com.tinet.tsso.auth.param.PasswordChangeParam;
import com.tinet.tsso.auth.param.PasswordParam;
import com.tinet.tsso.auth.service.UserService;
import com.tinet.tsso.auth.util.MailSenderUtil;
import com.tinet.tsso.auth.util.ResetPasswordTmp;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 对于密码的Controller
 * 
 * @date 2017-08-18
 * @author lizy
 */
@RestController
@RequestMapping("/api/password")
public class PasswordController {


	private static Map<String, UsernameAndUuidModel> userModelMap = new Hashtable<String, UsernameAndUuidModel>();

	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.from.address}")
	private String mailFrom;

	@Value("${mail.passwordRetrieval.effictiveTime}")
	private Integer effictiveTime;

	@Value("${tsso.cas.clientUrl}")
	private String changePasswordAddress;

	@PostMapping("/forget_password")
	public ResponseModel forgetPassword(@RequestBody UserModel userModel) {

		if (userModel.getUsername() == null || userModel.getUsername().equals("")) {
			return new ResponseModel.Builder().error("用户名不能为空").status(HttpStatus.BAD_REQUEST).build();
		}
		User u = userService.selectByUserName(userModel.getUsername());

		if (u == null) {
			return new ResponseModel.Builder().error("用户名不存在").status(HttpStatus.BAD_REQUEST).build();
		}
		if (u.getEmail() == null) {
			return new ResponseModel.Builder().error("用户未认证邮箱").status(HttpStatus.BAD_REQUEST).build();
		}
		// 存储username 和key 以及时间
		UsernameAndUuidModel forgetPasswordModel = new UsernameAndUuidModel();
		String key = UUID.randomUUID().toString();
		forgetPasswordModel.setKey(key);
		forgetPasswordModel.setDate(new Date());
		userModelMap.put(userModel.getUsername(), forgetPasswordModel);

		MailSenderUtil.sendMail(mailSender, mailFrom, u.getEmail(), "TSSO天润单点登录系统密码重置链接",
				this.getMailContent(u.getUsername(), key));
		return new ResponseModel.Builder().msg("新的密码已经生成，请到邮箱查看").result(userModel.getUsername()).build();

	}

	/**
	 * 修改密码的实现类
	 * 
	 * @param passwordParam
	 *            密码和重置密码
	 * @param username
	 *            帐号
	 * @param key
	 *            标识key
	 * @return
	 */
	@PutMapping("/reset_password")
	public ResponseModel resetPassWord(@RequestBody PasswordParam passwordParam, String username, String key) {
		return userService.setPassword(passwordParam, userModelMap, username, key, effictiveTime);
	}

	/**
	 * 新建用户设置密码的链接
	 * 
	 * @param passwordParam
	 * @param username
	 * @param key
	 * @return
	 */
	@PostMapping("/set_password")
	public ResponseModel setPassWord(@RequestBody PasswordParam passwordParam, String username, String key) {
		return userService.setPassword(passwordParam, ResetPasswordTmp.getResetMap(), username, key, effictiveTime);
	}

	
	@PutMapping("/change_password")
	public ResponseModel changePassword(@RequestBody PasswordChangeParam passwordChangeParam,String username) {
		
		return userService.updateUserPassword(passwordChangeParam, username);
	}

	/**
	 * 生成重置密码的邮件内容
	 * 
	 * @param username
	 * @param key
	 * @return
	 */
	private String getMailContent(String username, String key) {
		// 邮件格式为：
		// 您重置密码的链接为：http://auth.tinetcloud.com/api/password/password_modify?username=lizy&key=7c3668d8-6ad4-4230-9d2a-634d5a79ae61
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("您好,").append(username).append("先生/女士：").append("<br/>")
				.append("您申请了密码重置。请访问此链接，输入您的新密码(该链接有效时长为10分钟)：<br/>").append("<a href=\"")
				.append(changePasswordAddress).append("/password_reset").append("?username=").append(username)
				.append("&key=").append(key).append("&changeKey=").append(1).append("\">").append(changePasswordAddress)
				.append("/password_reset").append("?username=").append(username).append("&key=").append(key)
				.append("&changeKey=").append(1).append("</a>").append("<br/>");

		return stringBuffer.toString();
	}

}
