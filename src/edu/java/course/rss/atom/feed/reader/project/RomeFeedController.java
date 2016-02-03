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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.sun.syndication.io.XmlReader;

public class RomeFeedController implements FeedController {
	
	File file = new File("resources/Feeds.txt");
	File tempFile = new File("resources/TempFeeds.txt");
	
	public void AddFeedSource(String Url) {
		try {
			FileWriter writer = new FileWriter(file.getAbsoluteFile(), true);
			writer.write(Url);
			writer.write("\r\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("New source was added successful");
	}
	
	public void RemoveFeedSource(Channel feed) {
		
		try {
			BufferedReader reader;
			reader = new BufferedReader(new FileReader(file));
			BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

			String currentLine;

			while ((currentLine = reader.readLine()) != null) {

				if (currentLine.equals(feed.getLink())) {
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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sources;
	}
	
	public List<NewsItem> ReturnAllNews(Channel feed) {
		return null;
	}
	
	public List<NewsItem> ReturnUnreadNews() {
		return null;
	}
	
	public void ShowNewsContent(NewsItem item) {
	}

}
