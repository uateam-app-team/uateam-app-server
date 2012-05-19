package com.xitxer.uateam.notification.server.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xitxer.uateam.notification.server.model.ReleaseEntry;
import com.xitxer.uateam.notification.server.parser.exceptions.HtmlLayoutChangedException;

public class RecentReleasesParser {

	private static final String DIV_FRESHRELEASE = "div.freshrelease";

	private SiteSource siteSource;

	public RecentReleasesParser(SiteSource siteSource) {
		this.siteSource = siteSource;
	}

	private Elements check(Elements elements) throws HtmlLayoutChangedException {
		if (elements.isEmpty()) {
			throw new HtmlLayoutChangedException(
					"Could not select with css query - " + DIV_FRESHRELEASE);
		}
		return elements;
	}

	private Elements getContentElements() throws IOException,
			HtmlLayoutChangedException {
		return check(siteSource.getDocument().select(DIV_FRESHRELEASE));
	}

	public List<ReleaseEntry> getRecent() throws HtmlLayoutChangedException,
			IOException {
		ReleaseEntryFiller filler = new ReleaseEntryFiller();
		List<ReleaseEntry> episodeEntries = new ArrayList<ReleaseEntry>();
		for (Element element : getContentElements()) {
			if (filler.parse(element)) {
				episodeEntries.add(filler.refresh());
			}
		}
		return episodeEntries;
	}
}
