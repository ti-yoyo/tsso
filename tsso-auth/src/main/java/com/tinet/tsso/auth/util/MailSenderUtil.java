package com.tinet.tsso.auth.util;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 发送邮件的功用类
 * @date 2017-08-25
 * @author lizy
 */
public class MailSenderUtil {
	/**
	 * 发送邮件的方法
	 * 
	 * @param mailTo
	 * @param mailContent
	 */
	public static void sendMail(JavaMailSender mailSender,String mailFrom, String mailTo, String subject, String mailContent) {
		Thread thread = new Thread(() -> {
			// 发送邮件代码
			MimeMessage mailMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper;
			try {
				messageHelper = new MimeMessageHelper(mailMessage, true, "UTF-8");
				messageHelper.setFrom(mailFrom);
				messageHelper.setTo(mailTo);
				messageHelper.setSubject(subject);
				messageHelper.setText(mailContent, true);
			} catch (Exception e) {
				Logger logger = LoggerFactory.getLogger(MailSenderUtil.class);
				logger.error("messageHelper构建异常", e);
			}
			mailSender.send(mailMessage);
		});
		thread.start();
	}
}
