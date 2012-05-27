package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xitxer.uateam.notification.server.model.ReleaseEntry;
import com.xitxer.uateam.notification.server.parser.RecentReleasesParser;
import com.xitxer.uateam.notification.server.parser.sitesource.HttpSiteSource;
import com.xitxer.uateam.notification.server.utils.UateamSiteUtils;

@SuppressWarnings("serial")
public class OnlineParserTest extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");
		PrintWriter printWriter = resp.getWriter();
		try {
			List<ReleaseEntry> episodeEntries = new RecentReleasesParser(
					new HttpSiteSource(UateamSiteUtils.URL_BASE)).get();
			for (ReleaseEntry releaseEntry : episodeEntries) {
				printWriter.println(releaseEntry);
			}
		} catch (Exception e) {
			e.printStackTrace(printWriter);
		}
	}
}
