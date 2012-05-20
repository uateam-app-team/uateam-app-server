package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xitxer.uateam.notification.server.model.ReleaseEntry;
import com.xitxer.uateam.notification.server.parser.HttpSiteSource;
import com.xitxer.uateam.notification.server.parser.RecentReleasesParser;
import com.xitxer.uateam.notification.server.parser.exceptions.HtmlLayoutChangedException;

@SuppressWarnings("serial")
public class OnlineParserTest extends HttpServlet {
	public static final String URL = "http://uateam.tv/";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		List<ReleaseEntry> episodeEntries;
		try {
			episodeEntries = new RecentReleasesParser(new HttpSiteSource(URL))
					.getRecent();
			String json = new Gson().toJson(episodeEntries);
			resp.setContentType("text/plain");
			resp.setCharacterEncoding("utf-8");
			resp.getWriter().println(json);
		} catch (HtmlLayoutChangedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
