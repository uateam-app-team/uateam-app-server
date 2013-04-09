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
import com.xitxer.uateam.notification.core.model.ReleaseEntry;
import com.xitxer.uateam.notification.core.parser.RecentReleasesParser;
import com.xitxer.uateam.notification.core.parser.sitesource.HttpSiteSource;
import com.xitxer.uateam.notification.core.util.UateamSiteUtil;
import com.xitxer.uateam.notification.server.helper.EmailHelper;
import com.xitxer.uateam.notification.server.storage.ReleasesDAO;

public class CronRecentReleaseParser extends HttpServlet {
	private static final long serialVersionUID = 6176989370903389159L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Map<String, List<ReleaseEntry>> releasesParsed = new HashMap<String, List<ReleaseEntry>>();
			RecentReleasesParser parser = new RecentReleasesParser(new HttpSiteSource(UateamSiteUtil.URL_BASE));
			for (ReleaseEntry releaseEntry : parser.get()) {
				String key = releaseEntry.getGroupLink();
				if (!releasesParsed.containsKey(key)) {
					releasesParsed.put(key, new ArrayList<ReleaseEntry>());
				}
				releasesParsed.get(key).add(releaseEntry);
			}

			Map<String, List<ReleaseEntry>> releasesToInform = new HashMap<String, List<ReleaseEntry>>();
			ReleasesDAO dao = new ReleasesDAO();
			for (Map.Entry<String, List<ReleaseEntry>> entry : releasesParsed.entrySet()) {
				Entity entity = dao.find(entry.getKey());
				ReleaseEntry releaseEntry = ReleasesDAO.make(entity);
				List<ReleaseEntry> entries = entry.getValue();
				if (releaseEntry != null) {
					if (entries.contains(releaseEntry)) {
						entries = entries.subList(0, entries.indexOf(releaseEntry));
					}
					if (entries.size() > 0) {
						releaseEntry = entries.get(0);
						releasesToInform.put(entry.getKey(), entries);
					}
				} else {
					releaseEntry = entries.get(0);
					releasesToInform.put(entry.getKey(), entries);
				}
				if (entity == null) {
					dao.putEntity(ReleasesDAO.make(releaseEntry));
				} else {
					dao.putEntity(ReleasesDAO.make(entity, releaseEntry));
				}
			}

			if (!releasesToInform.isEmpty()) {
				for (List<ReleaseEntry> entries : releasesToInform.values()) {
					parser.parseReleaseLinks(entries);
				}
				String subject = "New Releases", message = new Gson().toJson(releasesToInform);
				EmailHelper.sendEmailMe(subject, message);
				EmailHelper.sendEmail(EmailHelper.PARTNER_I_ADDRESS, EmailHelper.PARTNER_I_ADDRESS, subject, message);
				EmailHelper.sendEmail(EmailHelper.PARTNER_II_ADDRESS, EmailHelper.PARTNER_II_ADDRESS, subject, message);
			}
		} catch (Exception e) {
			EmailHelper.sendEmailException(e);
		}
	}
}
