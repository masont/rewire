package edu.mit.rewire.model;

import java.io.IOException;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;


public class EmailItem implements Item {

	private Message message;

	private String body;
	private String header;
	private String title;

	public EmailItem(Message m) {
		this.message = m;
		Address[] a;
		try {
			// FROM
			String from = "from";
			if ((a = m.getFrom()) != null) {
				for (int j = 0; j < a.length; j++) {
					InternetAddress ia = (InternetAddress)a[j];
					from = ia.getPersonal();
					if (from == null) {
						ia.toString();
					}
				}
			}
			// TO
			String to = "to";
			if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
				for (int j = 0; j < a.length; j++) {
					to = ("TO: " + a[j].toString());
				}
			}
			// SUBJECT
			String subject = m.getSubject();
			// DATE
			Date d = m.getSentDate();
			String date = d != null ? d.toString() : "unknown";
			// CONTENT
			String content = m.getContent().toString();

			this.title = subject;
			this.header = from;
			this.body = to + "\n" + content + "\n" + date;
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public EmailItem(String title, String header, String body) {
		this.title = title;
		this.header = header;
		this.body = body;
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
		return "email";
	}

	public Message getMessage() {
		return message;
	}

}
