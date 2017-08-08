package com.tinet.tsso.cas.encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义的密码Encoder，在application.properties文件里配置引用
 * @author 李政
 * @date 2017年8月8日
 */
@Component
public class TssoPasswordEncoder implements PasswordEncoder {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public String encode(CharSequence password) {

		return "这里不能为空，但是现在没有用到";
	}

	@Override
	public boolean matches(CharSequence plainPassword, String encodedPassword) {
		logger.debug("====================plainPassword:" + plainPassword);
		logger.debug("====================encodedPassword:" + encodedPassword);
		// TODO Auto-generated method stub
		String[] pwdAndSult = encodedPassword.split(",");
		logger.debug("====================db密码:" + pwdAndSult[0]);
		logger.debug("====================盐:" + pwdAndSult[1]);
		
		if(pwdAndSult[0].equals(plainPassword)){
			return true;
		}else{
			return false;
		}
	}

}
