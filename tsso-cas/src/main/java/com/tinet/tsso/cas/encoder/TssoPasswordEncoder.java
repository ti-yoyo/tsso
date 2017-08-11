package com.tinet.tsso.cas.encoder;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义的密码Encoder，在application.properties文件里配置引用
 * 
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

	/**
	 * plainPassword: 用户界面上数据的密码
	 * encodedPassword: 数据库里的密码和盐
	 */
	@Override
	public boolean matches(CharSequence plainPassword, String dbPasswordAndSalt) {
		// 如果查出来的密码和盐非空
		if (StringUtils.isNotBlank(dbPasswordAndSalt)) {
			String[] pwdAndSult = dbPasswordAndSalt.split(",");
			// 如果两个都查出来了
			if (pwdAndSult.length == 2) {
				
				String salt = pwdAndSult[1];
				
				PasswordHash passwordHash = new PasswordHash();
				passwordHash.setAlgorithmName("SHA-256");
				passwordHash.setHashIterations(6);
				
				String encodedPassword = passwordHash.toHex(plainPassword, salt);
				// 如果数据库中的password和encodedPassword相同，则登录成功
				if (pwdAndSult[0].equals(encodedPassword)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/*public static void main(String[] args) {
		PasswordHash passwordHash = new PasswordHash();
		passwordHash.setAlgorithmName("SHA-256");
		passwordHash.setHashIterations(6);
		
		String encodedPassword = passwordHash.toHex("lizheng", "123123");
		System.out.println(encodedPassword);
	}*/
}
