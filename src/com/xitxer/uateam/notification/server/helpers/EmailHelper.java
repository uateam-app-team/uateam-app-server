package com.xitxer.uateam.notification.server.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailHelper {
	private EmailHelper() {
	}

	public static final String ADMINS = "admins";
	public static final String MY_ADDRESS = "z" + "i" + "p" + "u" + "4" + "."
			+ "p" + "o" + "s" + "t" + "@" + "g" + "m" + "a" + "i" + "l" + "."
			+ "c" + "o" + "m";
	public static final String PARTNER_ADDRESS = "c" + "o" + "n" + "c" + "u"
			+ "r" + "o" + "r" + "@" + "g" + "m" + "a" + "i" + "l" + "." + "c"
			+ "o" + "m";
	public static final String MY_TITLE = "uateam-notifier admin";

	public static void sendEmailException(Throwable throwable) {
		try {
			StringWriter writer = new StringWriter();
			throwable.printStackTrace(new PrintWriter(writer));
			sendEmailMe(throwable.getMessage(), writer.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendEmailMe(String subject, String message)
			throws UnsupportedEncodingException, MessagingException {
		sendEmail(ADMINS, ADMINS, subject, message);
	}

	public static void sendEmail(String recipientAddress, String recipientName,
			String subject, String message)
			throws UnsupportedEncodingException, MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(MY_ADDRESS, MY_TITLE));
		if (ADMINS.equals(recipientAddress)) {
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					recipientAddress));
		} else {
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					recipientAddress, recipientName));
		}
		msg.setSubject(subject);
		msg.setText(message);
		Transport.send(msg);
	}
}
