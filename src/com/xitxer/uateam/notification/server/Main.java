package com.xitxer.uateam.notification.server;

import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.xitxer.uateam.notification.model.EpisodeEntry;
import com.xitxer.uateam.notification.parser.RecentEnriesParser;
import com.xitxer.uateam.notification.parser.SiteSource;

public class Main {
	public static final String URL = "http://uateam.tv/";

	public static void main(String[] args) {
		List<EpisodeEntry> episodeEntries = new RecentEnriesParser(
				new SiteSource(URL)).getRecent();
		String json = new Gson().toJson(episodeEntries);
		System.out.println(json);
		System.out.println(Arrays.asList(new Gson().fromJson(json,
				EpisodeEntry[].class)));
	}
}
