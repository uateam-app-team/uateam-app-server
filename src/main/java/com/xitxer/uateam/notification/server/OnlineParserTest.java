package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xitxer.uateam.notification.core.parser.RecentReleasesParser;
import com.xitxer.uateam.notification.core.parser.sitesource.HttpSiteSource;
import com.xitxer.uateam.notification.core.util.UateamSiteUtil;

public class OnlineParserTest extends HttpServlet {
	private static final long serialVersionUID = 6990633723696248267L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");
		PrintWriter printWriter = resp.getWriter();
		try {
			RecentReleasesParser releasesParser = new RecentReleasesParser(new HttpSiteSource(UateamSiteUtil.URL_BASE));
			printWriter.print(releasesParser.parseReleaseLinks(releasesParser.get()));
		} catch (Exception e) {
			e.printStackTrace(printWriter);
		}
	}
}
