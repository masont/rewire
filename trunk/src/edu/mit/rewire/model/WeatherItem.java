package edu.mit.rewire.model;

import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import twitter4j.http.HttpClient;

public class WeatherItem implements Item {

    protected HttpClient http = null;

    private String baseURL = "http://www.google.com/ig/api";
    private String zip;
    
    private String body;
    private String header;
    private String title;
    
	@SuppressWarnings("unchecked")
	public WeatherItem() {
        http = new HttpClient();
        this.zip = "02139";

		try {
    		SAXBuilder parser = new SAXBuilder();
			Document doc = parser.build(baseURL + "?weather="+zip);
			Element weather = doc.getRootElement().getChild("weather");
			
			Element forecast_information = weather.getChild("forecast_information");
			String city = forecast_information.getChild("city").getAttributeValue("data");
			
			Element current_conditions = weather.getChild("current_conditions");
			String condition = current_conditions.getChild("condition").getAttributeValue("data");
			String temp_f = current_conditions.getChild("temp_f").getAttributeValue("data");
			String humidity = current_conditions.getChild("humidity").getAttributeValue("data");
			String wind_condition = current_conditions.getChild("wind_condition").getAttributeValue("data");

			List<Element> forecast_conditions = weather.getChildren("forecast_conditions");
			
			title = temp_f + "\u00B0F " + condition;
			header = city;
			body = "Today: " + temp_f + "\u00B0F. " + condition + ". " +
				humidity + ". " + wind_condition + ".";
			
			for (Element forecast : forecast_conditions) {
				body += "\n";
				body += forecast.getChild("day_of_week").getAttributeValue("data") + ": ";
				body += forecast.getChild("high").getAttributeValue("data") + "\u00B0F / ";
				body += forecast.getChild("low").getAttributeValue("data") + "\u00B0F. ";
				body += forecast.getChild("condition").getAttributeValue("data") + ".";
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
		return "weather";
	}
    
    

}
