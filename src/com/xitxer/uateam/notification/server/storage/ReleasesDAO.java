package com.xitxer.uateam.notification.server.storage;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.xitxer.uateam.notification.server.model.ReleaseEntry;

public class ReleasesDAO {

	public static final String ENTITY_RELEASE = "Release";
	public static final String PROPERTY_GROUP_NAME = "groupName";
	public static final String PROPERTY_RELEASE_NAME = "releaseName";
	public static final String PROPERTY_SEASON_NUMBER = "seasonNumber";
	public static final String PROPERTY_EPISODE_NUMBER = "episodeNumber";
	public static final String PROPERTY_GROUP_LINK = "groupLink";
	public static final String PROPERTY_DETAILS_LINK = "detailsLink";
	public static final String PROPERTY_TORRENT_LINK = "torrentLink";
	public static final String PROPERTY_TORRENT_HD_LINK = "torrentHdLink";
	public static final String PROPERTY_WATCH_ONLINE_LINK = "watchOnlineLink";

	protected DatastoreService datastoreService;

	public ReleasesDAO() {
		datastoreService = DatastoreServiceFactory.getDatastoreService();
	}

	public Key put(Entity entity) {
		return datastoreService.put(entity);
	}

	public Key put(ReleaseEntry releaseEntry) {
		return put(make(releaseEntry));
	}

	public Entity find(String groupLink) {
		Query query = new Query(ENTITY_RELEASE);
		query.addFilter(PROPERTY_GROUP_LINK, FilterOperator.EQUAL, groupLink);
		return datastoreService.prepare(query).asSingleEntity();
	}

	public static Entity make(Entity entity, ReleaseEntry entry) {
		if (entry == null || entity == null) {
			return null;
		}
		entity.setProperty(PROPERTY_GROUP_NAME, entry.getGroup());
		entity.setProperty(PROPERTY_RELEASE_NAME, entry.getRelease());
		entity.setProperty(PROPERTY_SEASON_NUMBER, entry.getSeason());
		entity.setProperty(PROPERTY_EPISODE_NUMBER, entry.getEpisode());
		entity.setProperty(PROPERTY_GROUP_LINK, entry.getGroupLink());
		entity.setProperty(PROPERTY_DETAILS_LINK, entry.getDetailsLink());
		entity.setProperty(PROPERTY_TORRENT_LINK, entry.getTorrentLink());
		entity.setProperty(PROPERTY_TORRENT_HD_LINK, entry.getTorrentHdLink());
		entity.setProperty(PROPERTY_WATCH_ONLINE_LINK,
				entry.getWatchOnlineLink());
		return entity;
	}

	public static Entity make(ReleaseEntry entry) {
		return make(new Entity(ENTITY_RELEASE), entry);
	}

	public static ReleaseEntry make(Entity entity) {
		if (entity == null) {
			return null;
		}
		ReleaseEntry entry = new ReleaseEntry();
		entry.setGroup((String) entity.getProperty(PROPERTY_GROUP_NAME));
		entry.setRelease((String) entity.getProperty(PROPERTY_RELEASE_NAME));
		entry.setSeason((Long) entity.getProperty(PROPERTY_SEASON_NUMBER));
		entry.setEpisode((Long) entity.getProperty(PROPERTY_EPISODE_NUMBER));
		entry.setGroupLink((String) entity.getProperty(PROPERTY_GROUP_LINK));
		entry.setDetailsLink((String) entity.getProperty(PROPERTY_DETAILS_LINK));
		entry.setTorrentLink((String) entity.getProperty(PROPERTY_TORRENT_LINK));
		entry.setTorrentHdLink((String) entity
				.getProperty(PROPERTY_TORRENT_HD_LINK));
		entry.setWatchOnlineLink((String) entity
				.getProperty(PROPERTY_WATCH_ONLINE_LINK));
		return entry;
	}
}
