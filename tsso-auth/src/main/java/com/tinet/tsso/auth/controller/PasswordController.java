package com.tinet.tsso.auth.controller;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.ForgetPasswordModel;
import com.tinet.tsso.auth.model.UserModel;
import com.tinet.tsso.auth.param.PasswordParam;
import com.tinet.tsso.auth.service.UserService;
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

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static Map<String, ForgetPasswordModel> userModelMap = new Hashtable<String, ForgetPasswordModel>();

	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.from.address}")
	private String mailFrom;

	@Value("${mail.effictiveTime}")
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
		ForgetPasswordModel forgetPasswordModel = new ForgetPasswordModel();
		String key = UUID.randomUUID().toString();
		forgetPasswordModel.setKey(key);
		forgetPasswordModel.setDate(new Date());
		userModelMap.put(userModel.getUsername(), forgetPasswordModel);

		this.sendMail(u.getEmail(), this.getMailContent(u.getUsername(), key));
		return new ResponseModel.Builder().msg("新的密码已经生成，请到邮箱查看").result(userModel.getUsername()).build();

	}

	/**
	 * 修改密码的实现类
	 * 
	 * @param passwordParam
	 * @return
	 */
	@PutMapping("/change_password")
	public ResponseModel changePassWord(@RequestBody PasswordParam passwordParam, String username, String key) {
		// 密码和确认密码不能为空
		if (passwordParam.getPassword() == null || passwordParam.getRepassword() == null) {
			return new ResponseModel.Builder().error("密码和确认密码都不能为空").status(HttpStatus.BAD_REQUEST).build();
		} else if (!passwordParam.getPassword().equals(passwordParam.getRepassword())) {
			return new ResponseModel.Builder().error("密码和确认密码不同").status(HttpStatus.FORBIDDEN).build();
		}
		if(username == null) {
			return new ResponseModel.Builder().error("用户名不能为空").status(HttpStatus.FORBIDDEN).build();
		}
		// 获取key和生成key的时间
		ForgetPasswordModel forgetPassWord = userModelMap.get(username);

		if (forgetPassWord == null || !forgetPassWord.getKey().equals(key)) {
			return new ResponseModel.Builder().error("请求错误").status(HttpStatus.BAD_REQUEST).build();
		}

		Date nowDate = new Date();
		long dateDiffer = nowDate.getTime() - forgetPassWord.getDate().getTime();
		// 确认不超时
		if (dateDiffer > 60000 * effictiveTime) {
			userModelMap.remove(username);
			return new ResponseModel.Builder().error("链接已失效").status(HttpStatus.BAD_REQUEST).build();
		}
		// 根据用户名查询用户
		User user = userService.selectByUserName(username);

		if (user == null) {
			return new ResponseModel.Builder().error("用户名错误").status(HttpStatus.BAD_REQUEST).build();
		}
		user.setPassword(passwordParam.getPassword());

		user = userService.updatePasswordByUsername(user);

		userModelMap.remove(username);

		return new ResponseModel.Builder().msg("密码重置成功").result(user).build();
	}

	/**
	 * 生成邮件内容
	 * 
	 * @param username
	 * @param key
	 * @return
	 */
	private String getMailContent(String username, String key) {
		//邮件格式为：
		//您重置密码的链接为：http://auth.tinetcloud.com/api/password/password_modify?username=lizy&key=7c3668d8-6ad4-4230-9d2a-634d5a79ae61
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("您好,").append(username).append("先生/女士：").append("</br>").append("您申请了密码重置。请访问此链接，输入您的新密码：</br>").append("<a href=\"").append(changePasswordAddress).append("/password_modify")
				.append("?username=").append(username).append("&key=").append(key).append("\">")
				.append(changePasswordAddress).append("/password_modify").append("?username=").append(username)
				.append("&key=").append(key).append("</a>").append("</br>");

		return stringBuffer.toString();
	}

	/**
	 * 发送邮件的类
	 * 
	 * @param mailTo
	 * @param mailContent
	 */
	private void sendMail(String mailTo, String mailContent) {
		Thread thread = new Thread(() -> {
			// 发送邮件代码
			MimeMessage mailMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper;
			try {
				messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
				messageHelper.setFrom(mailFrom);
				messageHelper.setTo(mailTo);
				messageHelper.setSubject("TSSO天润单点登录系统密码重置链接");
				messageHelper.setText(mailContent, true);
			} catch (Exception e) {
				logger.error("messageHelper构建异常", e);
			}
			mailSender.send(mailMessage);
		});
		thread.start();
	}
}
