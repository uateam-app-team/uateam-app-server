package com.xitxer.uateam.notification.server.parser;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xitxer.uateam.notification.server.model.ReleaseEntry;
import com.xitxer.uateam.notification.server.parser.exceptions.HtmlLayoutChangedException;
import com.xitxer.uateam.notification.server.parser.exceptions.PageNotAvailableException;
import com.xitxer.uateam.notification.server.parser.sitesource.SiteSource;

public class ReleaseEntryFiller {

	private static final String TAG_A = "a";
	private static final String TAG_DIV = "div";
	private static final String TAG_P = "P";
	private static final String TAG_SPAN = "span";

	private static final String ATTR_HREF = "href";

	private static final String REGEXP_REALEASE_NUMBERS = "\\.";

	private ReleaseEntry episodeEntry = new ReleaseEntry();

	public boolean parse(Element rootElement) throws HtmlLayoutChangedException {
		try {
			Elements elements = rootElement.children();
			Element element = elements.get(0);
			if (TAG_DIV.equalsIgnoreCase(element.tagName())) {
				try {
					episodeEntry.setSeason(Integer.parseInt(element.text()
							.split(REGEXP_REALEASE_NUMBERS)[0]));
					episodeEntry.setEpisode(Integer.parseInt(element.text()
							.split(REGEXP_REALEASE_NUMBERS)[1]));
				} catch (Exception e) {
					// do nothing because can be not series release
				}
			}
			elements = elements.select(TAG_P).get(0).children();
			Elements titleElements = elements.select(TAG_SPAN);
			episodeEntry.setGroup(titleElements.get(0).text());
			episodeEntry.setRelease(titleElements.get(1).text());
			Elements linkElements = elements.select(TAG_A);
			episodeEntry.setGroupLink(linkElements.get(0).attr(ATTR_HREF));
			episodeEntry.setDetailsLink(linkElements.get(1).attr(ATTR_HREF));
			return true;
		} catch (Exception e) {
			HtmlLayoutChangedException exception = new HtmlLayoutChangedException(
					"Unknown change: " + e.toString());
			exception.setStackTrace(e.getStackTrace());
			throw exception;
		}
	}

	public void parseReleaseLinks(SiteSource siteSource)
			throws PageNotAvailableException {
		try {
			Document document = siteSource.getSubPage(episodeEntry
					.getDetailsLink());
		} catch (IOException e) {
			PageNotAvailableException exception = new PageNotAvailableException(
					"Sub page not found: " + episodeEntry.getDetailsLink());
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
