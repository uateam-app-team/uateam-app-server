package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xitxer.uateam.notification.server.model.ReleaseEntry;
import com.xitxer.uateam.notification.server.parser.HttpSiteSource;
import com.xitxer.uateam.notification.server.parser.RecentReleasesParser;

@SuppressWarnings("serial")
public class OnlineParserTest extends HttpServlet {
	public static final String URL = "http://uateam.tv/";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");
		PrintWriter printWriter = resp.getWriter();
		try {
			List<ReleaseEntry> episodeEntries = new RecentReleasesParser(
					new HttpSiteSource(URL)).getRecent();
			for (ReleaseEntry releaseEntry : episodeEntries) {
				printWriter.println(releaseEntry);
			}
		} catch (Exception e) {
			e.printStackTrace(printWriter);
		}
	}
}
