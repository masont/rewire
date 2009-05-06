package edu.mit.rewire.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gdata.client.GoogleService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.extensions.EventEntry;
import com.google.gdata.data.extensions.EventFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class AgendaItem implements Item {

	private String title;
	private String header;
	private String body;
	
	public AgendaItem() {
		// Set up the URL and the object that will handle the connection:
		try {
			URL feedUrl = new URL("http://www.google.com/calendar/feeds/sa.rewire@gmail.com/private/full");
			GoogleService myService = new GoogleService("cl", "mit-rewire-1");
			myService.setUserCredentials("sa.rewire@gmail.com", "claytonrules");
			
			// Mark the feed as an Event feed:
			new EventFeed().declareExtensions(myService.getExtensionProfile());

			// Send the request and receive the response:
			EventFeed myFeed = myService.getFeed(feedUrl, EventFeed.class);
			
			List<EventEntry> events = myFeed.getEntries();
			title = "Agenda";
			DateTime now = DateTime.now();
			now.setValue(now.getValue() - (3600000*4));
			header = DateTime.now().toStringRfc822().substring(0,11);
			body = "";
			for (int i=events.size()-1;i>=0;i--) {
				EventEntry event = events.get(i);
				DateTime time = event.getTimes().get(0).getStartTime();
				time.setValue(time.getValue() - (3600000*4));
				body += time.toStringRfc822().substring(0,22) + "\n    "
					+ event.getTitle().getPlainText() + "\n";
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getBody() {
		return body;
	}

	public String getHeader() {
		return header;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return "todo";
	}

}
