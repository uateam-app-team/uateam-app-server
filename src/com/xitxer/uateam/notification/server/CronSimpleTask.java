package com.xitxer.uateam.notification.server;

import java.io.IOException;

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
public class CronSimpleTask extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DatastoreService datastoreService = DatastoreServiceFactory
				.getDatastoreService();
		Entity entity = null;
		Query query = new Query(CronUtils.ENTITY_NAME);
		try {
			entity = datastoreService.prepare(query).asSingleEntity();
		} catch (TooManyResultsException e) {
			// Can not happen
			e.printStackTrace();
		}
		if (entity == null) {
			entity = new Entity(CronUtils.ENTITY_NAME);
		}
		entity.setProperty(CronUtils.ENTITY_PROPERTY,
				System.currentTimeMillis());
		datastoreService.put(entity);
	}
}
