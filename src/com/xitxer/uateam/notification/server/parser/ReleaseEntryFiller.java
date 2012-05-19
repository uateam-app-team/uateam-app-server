package com.xitxer.uateam.notification.server.parser;

import java.io.UnsupportedEncodingException;
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
	public static final String TAG_P = "P";
	public static final String TAG_SPAN = "span";

	public static final String ATTR_HREF = "href";
	public static final String ATTR_SRC = "src";

	public static final List<String> IMG_SRCS = ImmutableList.of(
			"/img/det.png", "/img/online.png", "/img/download.png",
			"/img/hd.jpg");

	private ReleaseEntry episodeEntry = new ReleaseEntry();

	private String fixWin1251(String string)
			throws UnsupportedEncodingException {
		// FIXME:bad encoding, try to convert from cp1252 to unicode
		return fix(string, "windows-1252");
	}

	private String fix(String string, String encoding)
			throws UnsupportedEncodingException {
		return new String(string.getBytes(), encoding);
	}

	public boolean parse(Element rootElement) throws HtmlLayoutChangedException {
		try {
			Elements elements = rootElement.children();
			Element element = elements.get(0);
			if (!TAG_DIV.equalsIgnoreCase(element.tagName())) {
				throw new HtmlLayoutChangedException(
						"Can`t find season and episode numbers");
			}
			try {
				episodeEntry.setSeason(Integer.parseInt(element.text().split(
						"\\.")[0]));
				episodeEntry.setEpisode(Integer.parseInt(element.text().split(
						"\\.")[1]));
			} catch (Exception e) {
				// do nothing because can be not series release
			}
			elements = elements.select(TAG_P).get(0).children();
			Elements titleElements = elements.select(TAG_SPAN);
			episodeEntry.setGroup(fixWin1251(titleElements.get(0).text()));
			episodeEntry.setRelease(fixWin1251(titleElements.get(1).text()));
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
