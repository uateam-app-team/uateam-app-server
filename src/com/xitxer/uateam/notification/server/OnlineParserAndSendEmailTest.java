package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xitxer.uateam.notification.server.model.ReleaseEntry;
import com.xitxer.uateam.notification.server.parser.RecentReleasesParser;
import com.xitxer.uateam.notification.server.parser.sitesource.HttpSiteSource;
import com.xitxer.uateam.notification.server.utils.UateamSiteUtils;

@SuppressWarnings("serial")
public class OnlineParserAndSendEmailTest extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		try {
			List<ReleaseEntry> episodeEntries = new RecentReleasesParser(
					new HttpSiteSource(UateamSiteUtils.URL_BASE)).get();
			for (ReleaseEntry releaseEntry : episodeEntries) {
				printWriter.println(releaseEntry);
			}
		} catch (Exception e) {
			e.printStackTrace(printWriter);
		}
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/plain");
		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(
					"info@uateam-notification.appspotmail.com",
					"New  Release Information"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
					"zipu4.post@gmail.com", "Mr. Xitx"));
			msg.setSubject("New releases are avalible");
			msg.setText(stringWriter.toString());
			Transport.send(msg);
			resp.getWriter().print("Success!");
		} catch (Exception e) {
			e.printStackTrace(resp.getWriter());
		}
	}
}
