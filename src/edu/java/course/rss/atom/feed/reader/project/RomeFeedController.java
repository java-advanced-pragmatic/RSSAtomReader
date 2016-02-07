package edu.java.course.rss.atom.feed.reader.project;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.io.XmlReader;

public class RomeFeedController implements FeedController {

	File file = new File("src/main/resources/Feeds.txt");
	File tempFile = new File("src/main/resources/TempFeeds.txt");

	public String modifyFeedLink(Channel feed) {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(file));
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {

				if (currentLine.contains(feed.getLink()))
					return currentLine;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return feed.getLink();

	}

	public void AddFeedSource(String Url) {
		try {
			FileWriter writer = new FileWriter(file.getAbsoluteFile(), true);
			writer.write(Url);
			writer.write("\r\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("New source was added successful");
	}

	public void RemoveFeedSource(Channel feed) {

		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
			String modifyFeedLink = modifyFeedLink(feed);
			String currentLine;

			while ((currentLine = reader.readLine()) != null) {

				if (currentLine.equals(modifyFeedLink)) {
					String trimmedLine = currentLine.trim();
				} else {
					writer.write(currentLine);
					writer.write("\r\n");
				}
			}

			writer.close();
			reader.close();

			if (!file.delete()) {
				System.out.println("Could not rename file1");
				return;
			}
			if (!tempFile.renameTo(file))
				System.out.println("Could not rename file");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Channel> ReturnAllFeedSources() {

		List<Channel> sources = new ArrayList<Channel>();
		FileReader fileReader;
		SyndFeedInput syndFeedInput = new SyndFeedInput();
		try {
			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				Channel feed = new Channel();
				feed.setLink(line);
				URL url = new URL(line);
				SyndFeed syndFeed = syndFeedInput.build(new XmlReader(url));
				feed.setTitle(syndFeed.getTitle());
				feed.setDescription(syndFeed.getDescription());
				sources.add(feed);
			}
			fileReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		}

		return sources;
	}

	public List<NewsItem> ReturnAllNews(Channel feed) {
		List<NewsItem> news = new ArrayList<NewsItem>();
		SyndFeedInput syndFeedInput = new SyndFeedInput();
		SyndFeed syndFeed;
		String modifyFeedLink = modifyFeedLink(feed);
		try {
			URL url = new URL(modifyFeedLink);
			syndFeed = syndFeedInput.build(new XmlReader(url));
			for (Object object : syndFeed.getEntries()) {
				NewsItem newsItem = new NewsItem();
				SyndEntry syndEntry = (SyndEntry) object;
				newsItem.setLink(syndEntry.getLink());
				newsItem.setPubDate(syndEntry.getPublishedDate());
				newsItem.setTitle(syndEntry.getTitle());
				boolean add = news.add(newsItem);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return news;
	}

	public List<NewsItem> ReturnUnreadNews() {
		List<NewsItem> unreadNews = new ArrayList<NewsItem>();
		FileReader fileReader;
		SyndFeedInput syndFeedInput = new SyndFeedInput();
		SyndFeed syndFeed;
		try {
			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				URL url = new URL(line);
				syndFeed = syndFeedInput.build(new XmlReader(url));
				for (Object object : syndFeed.getEntries()) {
					NewsItem newsItem = new NewsItem();
					SyndEntry syndEntry = (SyndEntry) object;
					if (!newsItem.isRead()) {
						newsItem.setTitle(syndEntry.getTitle());
						newsItem.setLink(syndEntry.getLink());
						newsItem.setPubDate(syndEntry.getPublishedDate());
						boolean add = unreadNews.add(newsItem);
					}

				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		}

		return unreadNews;

	}

	public String newsLink(NewsItem item) {
		String link = item.getLink();
		return link;
	}

}
