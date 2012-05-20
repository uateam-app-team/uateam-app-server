package com.xitxer.uateam.notification.server.parser;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

	private ReleaseEntry episodeEntry = new ReleaseEntry();

	public boolean parse(Element rootElement) throws HtmlLayoutChangedException {
		try {
			Elements elements = rootElement.children();
			Element element = elements.get(0);
			if (TAG_DIV.equalsIgnoreCase(element.tagName())) {
				try {
					episodeEntry.setSeason(Integer.parseInt(element.text()
							.split("\\.")[0]));
					episodeEntry.setEpisode(Integer.parseInt(element.text()
							.split("\\.")[1]));
				} catch (Exception e) {
					// do nothing because can be not series release
				}
			}
			elements = elements.select(TAG_P).get(0).children();
			Elements titleElements = elements.select(TAG_SPAN);
			episodeEntry.setGroup(titleElements.get(0).text());
			episodeEntry.setRelease(titleElements.get(1).text());
			Elements linkelements = elements.select(TAG_A);
			episodeEntry.setGroupLink(linkelements.get(0).attr(ATTR_HREF));
			episodeEntry.setDetailsLink(linkelements.get(1).attr(ATTR_HREF));
			return true;
		} catch (Exception e) {
			HtmlLayoutChangedException exception = new HtmlLayoutChangedException(
					"Unknown change: " + e.toString());
			exception.setStackTrace(e.getStackTrace());
			throw exception;
		}
	}

	public ReleaseEntry refresh() {
		ReleaseEntry old = episodeEntry;
		episodeEntry = new ReleaseEntry();
		return old;
	}
}
