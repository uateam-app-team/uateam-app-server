package com.xitxer.uateam.notification.parser;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SiteSource implements ISiteSource {
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1) "
			+ "AppleWebKit/535.19 (KHTML, like Gecko) "
			+ "Chrome/18.0.1025.168 Safari/535.19";

	public static final double GOLDEN_RATIO = 1.61803399;
	public static final int TIME_BASE = 5000;

	private String url;

	public SiteSource(String url) {
		this.url = url;
	}

	public Connection getConnection() {
		return Jsoup.connect(url).userAgent(USER_AGENT);
	}

	@Override
	public Document getDocument() {
		Connection connection = getConnection();
		int timeOut = TIME_BASE;
		Document document = null;
		while (document == null) {
			try {
				connection.timeout(timeOut);
				document = connection.get();
			} catch (UnknownHostException e) {
				e.printStackTrace();
				break;
			} catch (SocketTimeoutException e) {
				timeOut *= GOLDEN_RATIO;
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return document;
	}
}
