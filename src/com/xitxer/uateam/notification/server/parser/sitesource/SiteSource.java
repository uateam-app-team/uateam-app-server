package com.xitxer.uateam.notification.server.parser.sitesource;

import org.jsoup.nodes.Document;

public interface SiteSource {
	Document getRootPage() throws Exception;

	Document getSubPage(String subpath) throws Exception;
}
