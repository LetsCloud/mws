/**
 * 
 */
package io.crs.mws.server.security.signup;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import io.crs.mws.server.entity.Account;
import io.crs.mws.server.service.AccountService;

/**
 * @author robi
 *
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	private static final Logger logger = LoggerFactory.getLogger(RegistrationListener.class);

	private static final String SENDER_EMAIL = "csernikr@gmail.com";
	private static final String SENDER_NAME = "HostWare Cloud Admin";
	private static final String ACTIVATION_URL = "https://hw-cloud4.appspot.com/activate/";

	private static final String ACT_SUBJECT = "actSubject";
	private static final String ACT_MESSAGE = "actMessage";

	@Autowired
	private AccountService accountService;

	@Autowired
	private MessageSource messageSource;

	public void setAppUserService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		logger.info("confirmRegistration");

		Account account = event.getAccount();
		logger.info("confirmRegistration->user=" + account);

		String token = UUID.randomUUID().toString();
		try {
			accountService.createVerificationToken(account.getWebSafeKey(), token);
		} catch (Throwable e1) {
			logger.info("confirmRegistration->" + e1.getMessage());
			e1.printStackTrace();
		}
		logger.info("confirmRegistration-2");

		String recipientAddress = account.getEmail();
		String recipientName = account.getNickname();
		String subject = messageSource.getMessage(ACT_SUBJECT, null, "There is no locale for ACT_SUBJECT",
				event.getLocale());
		String message = messageSource.getMessage(ACT_MESSAGE, null, "There is no locale for ACT_MESSAGE",
				event.getLocale());

		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
			logger.info("confirmRegistration-3");
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(SENDER_EMAIL, SENDER_NAME));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientAddress, recipientName));
			msg.setSubject(subject);
			msg.setText(message + " " + ACTIVATION_URL + token);
			Transport.send(msg);
			logger.info("confirmRegistration-4");
		} catch (AddressException e) {
			logger.info("AddressException");
		} catch (MessagingException e) {
			logger.info("MessagingException->" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.info("UnsupportedEncodingException");
		}
	}
}