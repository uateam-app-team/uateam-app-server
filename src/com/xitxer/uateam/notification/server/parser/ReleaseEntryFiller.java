package com.xitxer.uateam.notification.server.parser;

import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.collect.ImmutableList;
import com.xitxer.uateam.notification.server.model.ReleaseEntry;
import com.xitxer.uateam.notification.server.parser.exceptions.HtmlLayoutChangedException;

public class ReleaseEntryFiller {

	public static final String TAG_A = "a";
	public static final String TAG_HR = "hr";
	public static final String TAG_DIV = "div";
	public static final String ATTR_HREF = "href";
	public static final String ATTR_SRC = "src";

	public static final List<String> IMG_SRCS = ImmutableList.of(
			"/img/det.png", "/img/online.png", "/img/download.png",
			"/img/hd.jpg");

	private ReleaseEntry episodeEntry = new ReleaseEntry();

	public boolean parse(Element rootElement) throws HtmlLayoutChangedException {
		try {
			Elements elements = rootElement.children();
			Element numberElement = elements.get(0);
			if (!TAG_DIV.equalsIgnoreCase(numberElement.tagName())) {
				throw new HtmlLayoutChangedException(
						"Can`t find season and episode numbers");
			}
			try {
				episodeEntry.setSeason(Integer.parseInt(numberElement.text()
						.split("\\.")[0]));
				episodeEntry.setEpisode(Integer.parseInt(numberElement.text()
						.split("\\.")[1]));
			} catch (Exception e) {
				throw new HtmlLayoutChangedException("");
			}
		} catch (HtmlLayoutChangedException e) {
			throw e;
		} catch (Exception e) {
			throw new HtmlLayoutChangedException("Unknown change["
					+ e.getMessage() + "]");
		}
		return true;
	}

	public ReleaseEntry refresh() {
		ReleaseEntry old = episodeEntry;
		episodeEntry = new ReleaseEntry();
		return old;
	}
}
