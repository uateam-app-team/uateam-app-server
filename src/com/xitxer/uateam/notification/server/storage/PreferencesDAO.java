package com.xitxer.uateam.notification.server.storage;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.xitxer.uateam.notification.server.model.PreferenceEntry;

public class PreferencesDAO extends BaseDAO {

	private static final String ENTITY_PREFERENCE = "Preference";
	private static final String PROPERTY_KEY = "Key";
	private static final String PROPERTY_VALUE = "Value";

	protected static final String PREFERENCE_WAS_EXCEPTION = "was_exception";
	protected static final String PREFERENCE_LAST_EXCEPTION = "last_exception";

	public PreferencesDAO() {
		super();
	}

	protected Entity getEntity(String key) {
		return prepare(
				new Query(ENTITY_PREFERENCE).addFilter(PROPERTY_KEY,
						FilterOperator.EQUAL, key)).asSingleEntity();
	}

	protected PreferenceEntry getPrefence(String key) {
		return make(getEntity(key));
	}

	protected String getValue(String key) {
		return getPrefence(key).getValue();
	}

	protected Key putPreference(PreferenceEntry entry) {
		return putEntity(make(entry));
	}

	protected Key putPreference(String key, String value) {
		return putPreference(new PreferenceEntry(key, value));
	}

	protected String get(String key) {
		return getValue(key);
	}

	protected void set(String key, String value) {
		putPreference(key, value);
	}

	protected static Entity make(Entity entity, PreferenceEntry propertyEntry) {
		if (entity == null || propertyEntry == null) {
			return null;
		}
		entity.setProperty(PROPERTY_KEY, propertyEntry.getKey());
		entity.setProperty(PROPERTY_VALUE, propertyEntry.getValue());
		return entity;
	}

	protected static Entity make(PreferenceEntry propertyEntry) {
		return make(new Entity(ENTITY_PREFERENCE), propertyEntry);
	}

	protected static PreferenceEntry make(Entity entity) {
		PreferenceEntry entry = new PreferenceEntry();
		entry.setKey((String) entity.getProperty(PROPERTY_KEY));
		entry.setValue((String) entity.getProperty(PROPERTY_VALUE));
		return entry;
	}
}
