package com.xitxer.uateam.notification.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.google.gson.Gson;
import com.xitxer.uateam.notification.server.helpers.EmailHelper;
import com.xitxer.uateam.notification.server.model.ReleaseEntry;
import com.xitxer.uateam.notification.server.parser.RecentReleasesParser;
import com.xitxer.uateam.notification.server.parser.sitesource.HttpSiteSource;
import com.xitxer.uateam.notification.server.storage.ReleasesDAO;
import com.xitxer.uateam.notification.server.utils.UateamSiteUtils;

@SuppressWarnings("serial")
public class CronRecentReleaseParser extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			ReleasesDAO dao = new ReleasesDAO();
			Map<String, List<ReleaseEntry>> releasesParsed = new HashMap<String, List<ReleaseEntry>>();
			for (ReleaseEntry releaseEntry : new RecentReleasesParser(
					new HttpSiteSource(UateamSiteUtils.URL_BASE)).get()) {
				String key = releaseEntry.getGroupLink();
				if (!releasesParsed.containsKey(key)) {
					releasesParsed.put(key, new ArrayList<ReleaseEntry>());
				}
				releasesParsed.get(key).add(releaseEntry);
			}
			Map<String, List<ReleaseEntry>> releasesToInform = new HashMap<String, List<ReleaseEntry>>();
			for (Map.Entry<String, List<ReleaseEntry>> entry : releasesParsed
					.entrySet()) {
				Entity entity = dao.find(entry.getKey());
				ReleaseEntry releaseEntry = ReleasesDAO.make(entity);
				List<ReleaseEntry> entries = entry.getValue();
				if (releaseEntry != null) {
					entries = entries.subList(0, entries.indexOf(releaseEntry));
					if (entries.size() > 0) {
						releaseEntry = entries.get(0);
						releasesToInform.put(entry.getKey(), entries);
					}
				} else {
					releaseEntry = entries.get(0);
					releasesToInform.put(entry.getKey(), entries);
				}
				if (entity == null) {
					dao.put(ReleasesDAO.make(releaseEntry));
				} else {
					dao.put(ReleasesDAO.make(entity, releaseEntry));
				}
			}
			if (!releasesToInform.isEmpty()) {
				String subject = "New Releases", message = new Gson()
						.toJson(releasesToInform);
				EmailHelper.sendEmailMe(subject, message);
				EmailHelper.sendEmail(EmailHelper.PARTNER_ADDRESS,
						EmailHelper.PARTNER_ADDRESS, subject, message);
			}
		} catch (Exception e) {
			EmailHelper.sendEmailException(e);
		}
	}
}
