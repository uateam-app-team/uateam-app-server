package com.xitxer.uateam.notification.server.parser.sitesource;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface SiteSource {
	Document getDocument() throws IOException;
}