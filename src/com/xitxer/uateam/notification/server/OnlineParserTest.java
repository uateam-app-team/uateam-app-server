package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xitxer.uateam.notification.server.model.EpisodeEntry;
import com.xitxer.uateam.notification.server.parser.HttpSiteSource;
import com.xitxer.uateam.notification.server.parser.RecentEnriesParser;

@SuppressWarnings("serial")
public class OnlineParserTest extends HttpServlet {
	public static final String URL = "http://uateam.tv/";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		List<EpisodeEntry> episodeEntries = new RecentEnriesParser(
				new HttpSiteSource(URL)).getRecent();
		String json = new Gson().toJson(episodeEntries);
		resp.setContentType("text/plain");
		resp.getWriter().println(
				Arrays.asList(new Gson().fromJson(json, EpisodeEntry[].class)));
	}
}
