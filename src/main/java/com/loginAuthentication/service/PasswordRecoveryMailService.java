package com.loginAuthentication.service;

import java.time.Instant;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

import com.loginAuthentication.Dao.UserRepository;
import com.loginAuthentication.Entity.Otp;
import com.loginAuthentication.Entity.User;

@Service
public class PasswordRecoveryMailService {
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Value("${spring.app.senderEmail}")
	private String senderEmail;
	
	@Value("${spring.app.senderPassword}")
	private String senderPassword;
	
	@Value("${spring.app.gmailHost}")
	private String gmailHost;
	
	@Value("${spring.app.smtpPort}")
	private int smtpPort;

	public void sendEmail(String email) {
		
		
		User user = this.userDetailsServiceImpl.loadUserByEmail(email);
		
		Random random = new Random();
		int otpVal = random.nextInt(900000) + 100000;
		
		String defaultSubject = "Password Reset OTP - LoginApplication";
		
		String defaultMessage = "Dear " + user.getUsername() + ",\n\n"
                + "A password reset was requested for your account. Please use the following OTP to proceed: "
                + otpVal + "\n\n"
                + "Please do not share this OTP with anyone for security reasons.\n\n"
                + "Regards,\nYour Company Name";
		
		Instant expiryTime = Instant.now().plusMillis(60000);
		
		Otp otp = new Otp(otpVal, user, expiryTime);
		
		Properties properties = System.getProperties();
		
		properties.put("mail.smtp.host", gmailHost);
		properties.put("mail.smptp.port", smtpPort);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");
		
		Authenticator authenticator = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		};
		
		Session session = Session.getInstance(properties, authenticator);
		session.setDebug(true);
		
		MimeMessage m = new MimeMessage(session);
		
		try {
			m.setFrom(senderEmail);
			m.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			m.setSubject(defaultSubject);
			m.setText(defaultMessage);
			Transport.send(m);
			this.otpService.saveOtp(otp);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
	
}
