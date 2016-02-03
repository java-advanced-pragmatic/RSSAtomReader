package edu.java.course.rss.atom.feed.reader.project;

import java.util.List;

public interface FeedController {

	public void AddFeedSource(String URL);
	
	public void RemoveFeedSource(Channel feed);
	
	public List<Channel> ReturnAllFeedSources();
	
	public List<NewsItem> ReturnAllNews(Channel feed);
	
	public List<NewsItem> ReturnUnreadNews();
	
	public void ShowNewsContent(NewsItem item);
}
