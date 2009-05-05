package edu.mit.rewire.model;

import java.util.Properties;

import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class Inbox {

	static String protocol;
	static String host = null;
	static String user = null;
	static String password = null;
	static String mbox = null;
	
	private Message[] msgs;

	public Inbox() {
		protocol = "imaps";
		host = "imap.gmail.com";
		user = "sa.rewire";
		password = "claytonrules";

		try {
			// Get a Properties object
			Properties props = System.getProperties();

			// Get a Session object
			Session session = Session.getInstance(props, null);
			session.setDebug(false);
			
			// Get a Store object
			Store store = null;
			if (protocol != null)		
				store = session.getStore(protocol);
			else
				store = session.getStore();

			// Connect
			if (host != null || user != null || password != null)
				store.connect(host, -1, user, password);
			else
				store.connect();


			// Open the Folder

			Folder folder = store.getDefaultFolder();
			if (folder == null) {
				System.out.println("No default folder");
			}

			if (mbox == null)
				mbox = "INBOX";
			folder = folder.getFolder(mbox);
			if (folder == null) {
				System.out.println("Invalid folder");
			}

			// try to open read/write and if that fails try read-only
			try {
				folder.open(Folder.READ_WRITE);
			} catch (MessagingException ex) {
				folder.open(Folder.READ_ONLY);
			}
			int totalMessages = folder.getMessageCount();

			if (totalMessages == 0) {
				System.out.println("Empty folder");
				folder.close(false);
				store.close();
			}

			// Attributes & Flags for all messages ..
			msgs = folder.getMessages();

			// Use a suitable FetchProfile
			FetchProfile fp = new FetchProfile();
			fp.add(FetchProfile.Item.ENVELOPE);
			folder.fetch(msgs, fp);

		} catch (Exception ex) {
			System.out.println("Oops, got exception! " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public Message getEmail(int i) throws Exception {
		return msgs[msgs.length - 1 - i];
	}
}
