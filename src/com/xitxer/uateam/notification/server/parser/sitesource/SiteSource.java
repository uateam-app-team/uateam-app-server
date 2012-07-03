package com.xitxer.uateam.notification.server.parser.sitesource;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface SiteSource {
	Document getRootPage() throws IOException;

	Document getSubPage(String subpath) throws IOException;
}
