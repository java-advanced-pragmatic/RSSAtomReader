package edu.java.course.rss.atom.feed.reader.project;

import java.net.MalformedURLException;
import java.net.URL;

public class Channel {

	private String title;
	private String description;
	private URL link;
	 
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLink() {
		return link.getPath();
	}
	
	public void setLink(String url) {
		if(link == null){
			try {
				link = new URL(url);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		
	}
	 
}
