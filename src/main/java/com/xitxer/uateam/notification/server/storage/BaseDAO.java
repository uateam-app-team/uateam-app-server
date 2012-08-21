package com.xitxer.uateam.notification.server.storage;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

public class BaseDAO {
	private DatastoreService datastoreService;

	protected BaseDAO() {
		datastoreService = DatastoreServiceFactory.getDatastoreService();
	}

	protected DatastoreService getDatastoreService() {
		return datastoreService;
	}

	protected PreparedQuery prepare(Query query) {
		return datastoreService.prepare(query);
	}

	protected Key putEntity(Entity entity) {
		return datastoreService.put(entity);
	}
}
