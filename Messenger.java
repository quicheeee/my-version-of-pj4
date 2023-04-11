package pj4;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Messenger {
	private static final String FILENAME = "messages.ser";
	private static ArrayList<Conversation> conversations = null;

	public static ArrayList<Conversation> getConversations() {
		if (Messenger.conversations != null)
			return Messenger.conversations;

		ArrayList<Conversation> temp = new ArrayList<Conversation>();

		try {
			File f = new File(FILENAME);
			FileInputStream fis;
			ObjectInputStream ois;

			try {
				fis = new FileInputStream(f);
				ois = new ObjectInputStream(fis);

				while (true) {
					Conversation conversation = (Conversation) ois.readObject();
					temp.add(conversation);
				}
			} catch (EOFException ex) {
			} catch (FileNotFoundException ex2) {
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Messenger.conversations = temp;
		return Messenger.conversations;
	}

	public static void sendNewMessage(User sender, User receiver, String message, Boolean disappearing,
									  Customer customer, Store store) {
		Seller seller = store.getSeller();

		Conversation conv = findConversation(customer, seller, store);
		if (conv == null) {
			conv = new Conversation(customer, seller, store);
			ArrayList<Conversation> temp = Messenger.getConversations();
			temp.add(conv);
		}
		conv.addMessage(sender, receiver, message, disappearing);

		Messenger.writeMessages();
	}

	private static Conversation findConversation(Customer customer, Seller seller, Store store) {
		ArrayList<Conversation> temp = Messenger.getConversations();
		for (Conversation conv : temp) {
			if (conv.equals(new Conversation(customer, seller, store)))
				return conv;
		}
		return null;
	}

	public static void writeMessages() {
		try {
			File f = new File(Messenger.FILENAME);
			FileOutputStream fos = new FileOutputStream(f);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			for (Conversation conversation : Messenger.conversations) {
				oos.writeObject(conversation);
			}
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Conversation> getConversationsForUser(User u) {
		ArrayList<Conversation> temp = Messenger.getConversations();
		ArrayList<Conversation> results = new ArrayList<Conversation>();

		for (Conversation conversation : temp) {
			if (conversation.getCustomer().equals(u) || conversation.getSeller().equals(u)) {
				User other;
				if (conversation.getCustomer().equals(u))
					other = conversation.getCustomer();
				else
					other = conversation.getSeller();

				if (!(other.getBlockedUsers().contains(u) || u.getBlockedUsers().contains(other)))
					results.add(conversation);
			}
		}

		Comparator<Conversation> comparatorCustomer = new Comparator<Conversation>() {
			@Override
			public int compare(Conversation o1, Conversation o2) {
				if (o1.isReadCustomer() == o2.isReadCustomer())
					return 0;
				if (o1.isReadCustomer() && !o2.isReadCustomer())
					return 1;
				else
					return -1;
			}
		};
		Comparator<Conversation> comparatorSeller = new Comparator<Conversation>() {
			@Override
			public int compare(Conversation o1, Conversation o2) {
				if (o1.isReadSeller() == o2.isReadSeller())
					return 0;
				if (o1.isReadSeller() && !o2.isReadSeller())
					return 1;
				else
					return -1;
			}
		};

		if (u instanceof Customer)
			Collections.sort(results, comparatorCustomer);
		else
			Collections.sort(results, comparatorSeller);

		return results;
	}

	public static ArrayList<Message> getMessagesForUser(Conversation conversation, User user) {
		ArrayList<Message> temp =  conversation.getMessagesForUser(user);
		writeMessages(); //to write the read flags
		return temp;
	}

/*
	public static void editConversation(User sender, Conversation conversation, String newMessage,
								   boolean disappearing) {
		User receiver, 		conversation.addMessage();

		StringBuilder sb = new StringBuilder(conversation.getMessage());
		sb.append("\n");
		sb.append(newMessage);

//		Message rel = Messenger.findRelatedMessage(m);
//		m.setMessage(sb.toString());
		//m.setRead(true);
//		if (rel != null) {
//			rel.setMessage(sb.toString());
		//	rel.setRead(false);
//		}
		Messenger.writeMessages();
	}
*/

	public static void editMessage(Conversation conversation, Message m, String content, User user) {
		conversation.updateMessage(m, content, user);
		writeMessages();
	}

	public static void deleteMessage(Conversation conversation, Message m, User current) {
		conversation.deleteMessage(m, current);
		writeMessages();
	}

	public static void deleteConversationsForUser(User user) {
		ArrayList<Conversation> temp = Messenger.getConversations();
		ArrayList<Conversation> removeList = new ArrayList<Conversation>();

		for (Conversation m : temp) {
			if (m.getCustomer().equals(user) || m.getSeller().equals(user))
				removeList.add(m);
		}

		for (Conversation m : removeList) {
			temp.remove(m);
		}
		writeMessages();
	}

	public static boolean existsUnreadMessagesForUser(User user) {
		ArrayList<Conversation> temp = Messenger.getConversationsForUser(user);
		for (Conversation conversation : temp) {
			if (user instanceof Customer) {
				if (!conversation.isReadCustomer())
					return true;
			}
			if (user instanceof Seller) {
				if (!conversation.isReadSeller())
					return true;
			}
		}
		return false;
	}

}

