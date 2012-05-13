package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.xitxer.uateam.notification.model.EpisodeEntry;

public class Main {
	public static final String URL = "http://uateam.tv/";
	public static double GOLDEN_RATIO = 1.61803399;
	public static final int TIME_BASE = 5000;
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.168 Safari/535.19";

	public static Connection getConnection() {
		return Jsoup.connect(URL).userAgent(USER_AGENT);
	}

	public static Document getDocument() {
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

	public static void main(String[] args) {
		Element element = getDocument().getElementById("ja-col2");
		System.out.println(element);
		System.out.println(new EpisodeEntry());
	}
}
