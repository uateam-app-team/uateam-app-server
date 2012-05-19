package com.xitxer.uateam.notification.server.parser;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpSiteSource implements SiteSource {
	protected static final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1) "
			+ "AppleWebKit/535.19 (KHTML, like Gecko) "
			+ "Chrome/18.0.1025.168 Safari/535.19";
	protected static final int TIMEOUT = 60000;

	protected String url;

	public HttpSiteSource(String url) {
		this.url = url;
	}

	protected Connection getConnection() {
		return Jsoup.connect(url).userAgent(USER_AGENT).timeout(TIMEOUT);
	}

	@Override
	public Document getDocument() throws IOException {
		Connection connection = getConnection();
		Document document = null;
		while (document == null) {
			try {
				document = connection.get();
			} catch (IOException e) {
				e.fillInStackTrace();
				throw e;
			}
		}
		return document;
	}
}
