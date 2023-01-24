package com.hryshchenko.command.util;

import com.hryshchenko.command.AppException;
import com.hryshchenko.constant.AdminConst;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Util class containing tools for sending email.
 * 
 * @author Hryshchenko
 *
 */
public class MessageSender {
	private static final Logger log = LogManager.getLogger(MessageSender.class);

	/**
	 * Sending email.
	 * 
	 * @param to
	 * @param subject
	 * @param sendMessage
	 * @throws AppException
	 */
	public static void sendMessage(String to, String subject, String sendMessage) throws AppException {
		log.debug("Getting properties for sending email to " + to);

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", AdminConst.HOST);
		props.put("mail.smtp.port", AdminConst.PORT);

		Session session = getSession(props);
		log.debug("Properties are received.");

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(AdminConst.ADMIN_EMAIL));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(sendMessage);

			Transport.send(message);
			log.info("Message has been sent to " + to);
		} catch (MessagingException e) {
			log.error("Failed to send message. " + e);
			throw new AppException("messagesender.failure", e);
		}
	}

	/**
	 * Getting session for sending email.
	 * 
	 * @param props
	 * @return Session for sending email.
	 */
	private static Session getSession(Properties props) {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(AdminConst.ADMIN_EMAIL, AdminConst.PASSWORD);
			}
		});
		log.debug("Session for sending email is received.");
		return session;
	}

	/**
	 * Private constructor.
	 */
	private MessageSender() {

	}
}
