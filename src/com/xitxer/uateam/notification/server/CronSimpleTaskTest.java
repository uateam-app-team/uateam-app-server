package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery.TooManyResultsException;
import com.google.appengine.api.datastore.Query;
import com.xitxer.uateam.notification.server.utils.CronUtils;

@SuppressWarnings("serial")
public class CronSimpleTaskTest extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");
		PrintWriter printWriter = resp.getWriter();
		DatastoreService datastoreService = DatastoreServiceFactory
				.getDatastoreService();
		Query query = new Query(CronUtils.ENTITY_NAME);
		try {
			Entity entity = datastoreService.prepare(query).asSingleEntity();
			if (entity == null) {
				printWriter.println("No data about cron task");
				return;
			}
			long timeLast = (Long) entity
					.getProperty(CronUtils.ENTITY_PROPERTY), timeNow = System
					.currentTimeMillis();
			printWriter.println("Last cron task performed at " + timeLast);
			printWriter.println(timeNow - timeLast + " milis ago");
			printWriter.println("Now is " + timeNow);
		} catch (TooManyResultsException e) {
			// Can not happen
			e.printStackTrace(printWriter);
		}
	}
}
