package com.xitxer.uateam.notification.parser;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.xitxer.uateam.notification.model.EpisodeEntry;

public class RecentEnriesParser {

	public static final String CONTENT_COLUMN_ID = "ja-col2";

	private ISiteSource siteSource;

	public RecentEnriesParser(ISiteSource siteSource) {
		this.siteSource = siteSource;
	}

	public Elements getContentElements() {
		Element rootElement = siteSource.getDocument().getElementById(
				CONTENT_COLUMN_ID);
		Elements elements = rootElement.children();
		while (elements.size() == 1) {
			rootElement = elements.get(0);
			elements = rootElement.children();
		}
		return elements;
	}

	public List<EpisodeEntry> getRecent() {
		List<EpisodeEntry> episodeEntries = new ArrayList<EpisodeEntry>();
		Elements elements = getContentElements();
		RecentEntryParser filler = new RecentEntryParser();
		elements.remove(0);
		for (Element element : elements) {
			if (filler.parse(element)) {
				episodeEntries.add(filler.refresh());
			}
		}
		return episodeEntries;
	}
}
