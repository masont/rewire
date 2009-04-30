package edu.mit.rewire.model;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import twitter4j.TwitterException;
import twitter4j.http.HttpClient;

public class WeatherItem implements Item {

    protected HttpClient http = null;

    private String baseURL = "http://www.google.com/ig/api";
    private String zip;
    
	public WeatherItem() {
        http = new HttpClient();
        this.zip = "02139";

		try {
    		SAXBuilder parser = new SAXBuilder();
			Document doc = parser.build(baseURL + "?weather="+zip);
			Element weather = doc.getRootElement().getChild("weather");
			Element current_conditions = weather.getChild("current_conditions");
			String condition = current_conditions.getChild("condition").getAttributeValue("data");
			String temp_f = current_conditions.getChild("temp_f").getAttributeValue("data");
			String humidity = current_conditions.getChild("humidity").getAttributeValue("data");
			String wind_condition = current_conditions.getChild("wind_condition").getAttributeValue("data");
			System.out.println("Weather in Cambridge, MA (02139)\n"
					+ "Condition: " + condition +"\n"
					+ "Temp: " + temp_f + "deg F\n"
					+ humidity +"\n"
					+ wind_condition);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHeader() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
    
    

}
