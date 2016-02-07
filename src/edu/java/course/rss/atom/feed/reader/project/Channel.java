package edu.java.course.rss.atom.feed.reader.project;

import java.net.MalformedURLException;
import java.net.URL;
import javafx.beans.property.SimpleStringProperty;

public class Channel {

	private SimpleStringProperty title = new SimpleStringProperty("");;
	private String description;
	private URL link;
	 
	public String getTitle() {
		return title.get();
	}
	
	public void setTitle(String title) {
		this.title.set(title);
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
