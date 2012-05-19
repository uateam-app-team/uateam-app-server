package com.xitxer.uateam.notification.server.parser;

import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.nodes.Element;

import com.google.common.collect.ImmutableList;
import com.xitxer.uateam.notification.server.model.EpisodeEntry;

public class RecentEntryParser {

	public static final String TAG_A = "a";
	public static final String TAG_HR = "hr";
	public static final String ATTR_HREF = "href";
	public static final String ATTR_SRC = "src";

	public static final List<String> IMG_SRCS = ImmutableList.of(
			"/img/det.png", "/img/online.png", "/img/download.png",
			"/img/hd.jpg");

	private EpisodeEntry episodeEntry = new EpisodeEntry();

	public boolean parse(Element element) {
		String text = element.text();
		if (text.isEmpty()) {
			String tagName = element.tag().getName();
			if (TAG_A.equals(tagName) && element.hasAttr(ATTR_HREF)) {
				String link = element.absUrl(ATTR_HREF);
				try {
					java.net.URL url = new java.net.URL(link);
					element = element.children().select("img[src]").get(0);
					String imageSrc = element.attr(ATTR_SRC);
					switch (IMG_SRCS.indexOf(imageSrc)) {
					case 0:
						episodeEntry.setDetails(url.toString());
						return false;
					case 1:
						episodeEntry.setWatchOnline(url.toString());
						return false;
					case 2:
						episodeEntry.setTorrent(url.toString());
						return false;
					case 3:
						episodeEntry.setTorrentHd(url.toString());
						return false;
					}
					if (imageSrc.endsWith(".jpg")) {
						episodeEntry.setCategory(url.toString());
						return false;
					}
				} catch (IndexOutOfBoundsException e) {
				} catch (MalformedURLException e) {
				}
				return false;
			}
			if (TAG_HR.equals(tagName)) {
				return true;
			}
		} else {
			if (text.contains(".")) {
				String[] splited = text.split("\\.");
				int seasonNumber, episodeNumber;
				try {
					seasonNumber = Integer.parseInt(splited[0]);
					episodeNumber = Integer.parseInt(splited[1]);
					episodeEntry.setSeasonNumber(seasonNumber);
					episodeEntry.setEpisodeNumber(episodeNumber);
					return false;
				} catch (NumberFormatException e) {
				}
			}
			if (episodeEntry.getSeriesTitle() == null) {
				episodeEntry.setSeriesTitle(text);
				return false;
			}
			episodeEntry.setEpisodeTitle(text);
			return false;
		}
		return false;
	}

	public EpisodeEntry refresh() {
		EpisodeEntry old = episodeEntry;
		episodeEntry = new EpisodeEntry();
		return old;
	}
}
