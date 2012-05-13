package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
import com.xitxer.uateam.notification.model.EpisodeEntry;

public class Main {
	public static final String URL = "http://uateam.tv/";
	public static final String CONTENT_COLUMN_ID = "ja-col2";
	public static double GOLDEN_RATIO = 1.61803399;
	public static final int TIME_BASE = 5000;
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 5.1) "
			+ "AppleWebKit/535.19 (KHTML, like Gecko) "
			+ "Chrome/18.0.1025.168 Safari/535.19";
	public static final Set<String> SERIES_TITLES = ImmutableSet.of("Межа",
			"Доктор Хаус", "Гра Престолів");
	public static final List<String> IMG_SRCS = ImmutableList.of(
			"/img/det.png", "/img/online.png", "/img/download.png",
			"/img/hd.jpg");

	public static final String TAG_A = "a";
	public static final String TAG_HR = "hr";
	public static final String ATTR_HREF = "href";
	public static final String ATTR_SRC = "src";

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

		Document document = getDocument();
		Element rootElement = document.getElementById(CONTENT_COLUMN_ID);
		Elements elements = rootElement.children();
		while (elements.size() == 1) {
			rootElement = elements.get(0);
			elements = rootElement.children();
		}
		List<EpisodeEntry> episodeEntries = new ArrayList<EpisodeEntry>();
		EpisodeEntry episodeEntryTemp = new EpisodeEntry();
		for (Element element : elements) {
			String text = element.text();
			if (text.isEmpty()) {
				String tagName = element.tag().getName();
				if (TAG_A.equals(tagName) && element.hasAttr(ATTR_HREF)) {
					String link = element.absUrl(ATTR_HREF);
					try {
						java.net.URL url = new java.net.URL(link);
						element = element.children().select("img[src]").get(0);
						String imageSrc = element.attr(ATTR_SRC);
						if (imageSrc.endsWith(".jpg")) {
							episodeEntryTemp.setCategory(url.toString());
						}
						switch (IMG_SRCS.indexOf(imageSrc)) {
						case 0:
							episodeEntryTemp.setDetails(url.toString());
							break;
						case 1:
							episodeEntryTemp.setWatchOnline(url.toString());
							break;
						case 2:
							episodeEntryTemp.setTorrent(url.toString());
							break;
						case 3:
							episodeEntryTemp.setTorrentHd(url.toString());
							break;
						}
					} catch (IndexOutOfBoundsException e) {
					} catch (MalformedURLException e) {
					}
					continue;
				}
				if (TAG_HR.equals(tagName)) {
					episodeEntries.add(episodeEntryTemp);
					episodeEntryTemp = new EpisodeEntry();
					continue;
				}
			} else {
				if (SERIES_TITLES.contains(text)) {
					episodeEntryTemp.setSeriesTitle(text);
					continue;
				}
				if (text.contains(".")) {
					String[] splited = text.split("\\.");
					int seasonNumber, episodeNumber;
					try {
						seasonNumber = Integer.parseInt(splited[0]);
						episodeNumber = Integer.parseInt(splited[1]);
						episodeEntryTemp.setSeasonNumber(seasonNumber);
						episodeEntryTemp.setEpisodeNumber(episodeNumber);
						continue;
					} catch (NumberFormatException e) {
						// If not parsed than it is episode title
					}
				}
				episodeEntryTemp.setEpisodeTitle(text);
			}
		}
		String json = new Gson().toJson(episodeEntries);
		System.out.println(json);
		System.out.println(Arrays.asList(new Gson().fromJson(json,
				EpisodeEntry[].class)));
	}
}
