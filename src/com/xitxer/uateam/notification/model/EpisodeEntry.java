package com.xitxer.uateam.notification.model;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class EpisodeEntry implements Comparable<EpisodeEntry> {
	public EpisodeEntry() {
		super();
	}

	public EpisodeEntry(EpisodeEntry that) {
		super();
		setSeriesTitle(that.seriesTitle);
		setEpisodeTitle(that.episodeTitle);
		setSeasonNumber(that.seasonNumber);
		setEpisodeNumber(that.episodeNumber);
		setDetails(that.details);
		setTorrent(that.torrent);
		setTorrentHd(that.torrentHd);
		setWatchOnline(that.watchOnline);
		setCategory(that.category);
	}

	private String seriesTitle;
	private String episodeTitle;
	private int seasonNumber;
	private int episodeNumber;

	private String category;
	private String details;
	private String torrent;
	private String torrentHd;
	private String watchOnline;

	public String getSeriesTitle() {
		return seriesTitle;
	}

	public void setSeriesTitle(String seriesTitle) {
		this.seriesTitle = seriesTitle;
	}

	public String getEpisodeTitle() {
		return episodeTitle;
	}

	public void setEpisodeTitle(String episodeTitle) {
		this.episodeTitle = episodeTitle;
	}

	public int getSeasonNumber() {
		return seasonNumber;
	}

	public void setSeasonNumber(int seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	public int getEpisodeNumber() {
		return episodeNumber;
	}

	public void setEpisodeNumber(int episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getTorrent() {
		return torrent;
	}

	public void setTorrent(String torrent) {
		this.torrent = torrent;
	}

	public String getTorrentHd() {
		return torrentHd;
	}

	public void setTorrentHd(String torrentHd) {
		this.torrentHd = torrentHd;
	}

	public String getWatchOnline() {
		return watchOnline;
	}

	public void setWatchOnline(String watchOnline) {
		this.watchOnline = watchOnline;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EpisodeEntry))
			return false;
		EpisodeEntry that = (EpisodeEntry) obj;
		return Objects.equal(this.seriesTitle, that.seriesTitle)
				&& Objects.equal(this.seasonNumber, that.seasonNumber)
				&& Objects.equal(this.episodeTitle, that.episodeTitle)
				&& Objects.equal(this.episodeNumber, that.episodeNumber)
				&& Objects.equal(this.category, that.category)
				&& Objects.equal(this.details, that.details)
				&& Objects.equal(this.torrent, that.torrent)
				&& Objects.equal(this.torrentHd, that.torrentHd)
				&& Objects.equal(this.watchOnline, that.watchOnline);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(seriesTitle, episodeTitle, seasonNumber,
				episodeNumber, category, details, torrent, torrentHd,
				watchOnline);
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("Series title", seriesTitle)
				.add("Episode title", episodeTitle)
				.add("Season number", seasonNumber)
				.add("Episode Number", episodeNumber).add("Category", category)
				.add("Details", details).add("Torrent", torrent)
				.add("Torrent HD", torrentHd).add("Watch online", watchOnline)
				.toString();
	}

	@Override
	public int compareTo(EpisodeEntry o) {
		if (this == o)
			return 0;
		if (o == null)
			return 1;
		return ComparisonChain.start().compare(seriesTitle, o.seriesTitle)
				.compare(episodeTitle, o.episodeTitle)
				.compare(seasonNumber, o.seasonNumber)
				.compare(episodeNumber, o.episodeNumber)
				.compare(category, o.category).compare(details, o.details)
				.compare(torrent, o.torrent).compare(torrentHd, o.torrentHd)
				.compare(watchOnline, o.watchOnline).result();
	}
}
