package com.xitxer.uateam.notification.server.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xitxer.uateam.notification.server.model.EpisodeEntry;
import com.xitxer.uateam.notification.server.parser.exceptions.HtmlLayoutChanged;

public class RecentEnriesParser {

	public static final String CONTENT_COLUMN_ID = "ja-col2";

	private SiteSource siteSource;

	public RecentEnriesParser(SiteSource siteSource) {
		this.siteSource = siteSource;
	}

	public Elements getContentElements() throws HtmlLayoutChanged {
		Element rootElement = siteSource.getDocument().getElementById(
				CONTENT_COLUMN_ID);
		if (rootElement == null) {
			throw new HtmlLayoutChanged("Could not find element with id - "
					+ CONTENT_COLUMN_ID);
		}
		Elements elements = rootElement.children();
		while (elements.size() == 1) {
			rootElement = elements.get(0);
			elements = rootElement.children();
		}
		return elements;
	}

	public List<EpisodeEntry> getRecent() throws HtmlLayoutChanged {
		List<EpisodeEntry> episodeEntries = new ArrayList<EpisodeEntry>();
		Elements elements = getContentElements();
		elements.remove(0);
		RecentEntryParser filler = new RecentEntryParser();
		for (Element element : elements) {
			if (filler.parse(element)) {
				episodeEntries.add(filler.refresh());
			}
		}
		return episodeEntries;
	}
}
