package pj4;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.*;
/**
 * Messenger Class
 *
 * This class represents a Messenger object. The class is used to write, edit
 * and search messages in messager.ser file.
 *
 * @authoer Amelia Williams, Meha Kavoori, Anish Puri, Tyler Barnett
 *
 * @version 04/10/2023
 */
public class Messenger {
    private static final String FILENAME = "messages.ser"; // filename for file that holds messages
    private static ArrayList<Message> messages = null; // arrList of messages
    // method scans through contents of messages ArrayList and looks for when they aren't null
    // where they are not null, message objects from the messages arrList will be wrtten to the messages.ser file
    public static ArrayList<Message> getMessages() {
        if (Messenger.messages != null)
            return Messenger.messages;

        ArrayList<Message> temp = new ArrayList<Message>();

        try {
            File f = new File(FILENAME);
            FileInputStream fis;
            ObjectInputStream ois;

            try {
                fis = new FileInputStream(f);
                ois = new ObjectInputStream(fis);

                while (true) {
                    Message m = (Message) ois.readObject();
                    temp.add(m);
                }
            } catch (EOFException ex) {
            } catch (FileNotFoundException ex2) {
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        Messenger.messages = temp;
        return Messenger.messages;
    }
    // writes additional date and time steps to message.ser file
    public static void sendNewMessage(User sender, User receiver, String message, Boolean disappearing) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String dateString = dtf.format(LocalDateTime.now());

        Message m1 = new Message(sender, sender, receiver, message, disappearing, dateString);
        m1.setRead(true);
        Message m2 = new Message(receiver, sender, receiver, message, disappearing, dateString);

        ArrayList<Message> temp = Messenger.getMessages();
        temp.add(m1);
        temp.add(m2);
        Messenger.writeMessages();
    }
    // writes messages from messages array to Messenger file
    public static void writeMessages() {
        try {
            File f = new File(Messenger.FILENAME);
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Message m : Messenger.messages) {
                oos.writeObject(m);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // returns list of messages based on inputted user
    public static ArrayList<Message> getMessagesForUser(User u) {
        ArrayList<Message> temp = Messenger.getMessages();
        ArrayList<Message> results = new ArrayList<Message>();
        for (Message m : temp) {
            if (m.getMessageOwner().equals(u)) {
                User other;
                if (!m.getSender().equals(u))
                    other = m.getSender();
                else
                    other = m.getReceiver();

                if (!(other.getBlockedUsers().contains(u) || u.getBlockedUsers().contains(other)))
                    results.add(m);
            }
        }
        return results;
    }
    // method edits inputted message to be inputted String
    public static void editMessage(Message m, String newMessage) {
        StringBuilder sb = new StringBuilder();
        //sb.append(m.getMessage());
        //sb.append("\n");
        sb.append(newMessage);

        Message rel = Messenger.findRelatedMessage(m);
        m.setMessage(sb.toString());
        m.setRead(true);
        if (rel != null) {
            rel.setMessage(sb.toString());
            rel.setRead(false);
        }
        Messenger.writeMessages();
    }
    // method searches through messages arrList to find related messages
    private static Message findRelatedMessage(Message m) {
        ArrayList<Message> temp = Messenger.getMessages();
        for (Message rel : temp) {
            if (rel.related(m))
                return rel;
        }
        return null;
    }
    // method removes message from arrList and overwrites messages file
    public static void deleteMessage(Message m) {
        ArrayList<Message> temp = Messenger.getMessages();
        temp.remove(m);
        writeMessages();
    }
    // allows inputted user's messages to be deleted
    public static void deleteMessagesForUser(User user) {
        ArrayList<Message> temp = Messenger.getMessages();
        ArrayList<Message> removeList = new ArrayList<Message>();

        for (Message m : temp) {
            if (m.getSender().equals(user) || m.getReceiver().equals(user))
                removeList.add(m);
        }

        for (Message m : removeList) {
            temp.remove(m);
        }
        writeMessages();
    }
    // checks if inputted user has unreadmessages
    public static boolean existsUnreadMessagesForUser(User user) {
        ArrayList<Message> temp = Messenger.getMessages();
        for (Message m : temp) {
            if (!m.isRead())
                return true;
        }
        return false;
    }
    // this method takes given user's messages and adds headers, and adds them to a String[]
    public static List<String[]> createCsvData(User u) {
        List<String[]> list = new ArrayList<>();
        String [] headers = {"Timestamp", "Sender", "Receiver", "Message"};
        list.add(headers);
        for(Message m: getMessagesForUser(u)){
            String [] msg = {m.getCreateDate(), m.getSender().getName(), m.getReceiver().getName(), m.getMessage()};
            list.add(msg);
        }
        return list;
    }
    // method takes given customer and seller data and adds it to a String[] with headers
    public static List<String[]> createCsvDataForConvo(Customer c, Seller s){
        List<String[]> list = new ArrayList<>();
        String [] headers = {"Timestamp", "Sender", "Receiver", "Message"};
        list.add(headers);
        for(Message m: getMessagesForUser(c)){
            if(m.getSender().equals(s) || m.getReceiver().equals(s)) {
                String[] msg = {m.getCreateDate(), m.getSender().getName(), m.getReceiver().getName(), m.getMessage()};
                list.add(msg);
            }
        }
        return list;
    }
    // This method takes user data and creates a new CSV file with said data
    public static void convertToCSV(String fileName, User u){
        File csvOutput = new File(fileName);
        try{
            FileWriter fw = new FileWriter(csvOutput);
            PrintWriter pw = new PrintWriter(fw);
            for(String[] s: createCsvData(u)){
                int count = 1;
                for(String s1: s){
                    if(count != 4) {
                        pw.print(s1);
                        pw.print(",");
                        count++;
                    } else{
                        pw.println(s1);
                    }
                }
            }
            pw.close();
        } catch (FileNotFoundException fnfe){
        } catch(IOException ioe){

        }
    }
    // method converts desired file to a csv file
    public static void convertToCSVForConvo (String fileName, Customer c, Seller s2){
        File csvOutput = new File(fileName);
        try{
            FileWriter fw = new FileWriter(csvOutput);
            PrintWriter pw = new PrintWriter(fw);
            for(String[] s: createCsvDataForConvo(c, s2)){
                int count = 1;
                for(String s1: s){
                    if(count != 4) {
                        pw.print(s1);
                        pw.print(",");
                        count++;
                    } else{
                        pw.println(s1);
                    }
                }
            }
            pw.close();
        } catch (FileNotFoundException fnfe){
        } catch(IOException ioe){

        }
    }

}

//	public Messenger(Customer cust, Seller vendor) throws UserNotFoundException {
//
//		//verify exists
//
//		//check blocked
//
//		this.cust = cust;
//		this.vendor = vendor;
//		cust2vend = new ArrayList<String>();
//		vend2cust = new ArrayList<String>();
//
//	}
//
//	public void send(String message, User name) throws AccessDeniedException {
//
//		if (name.equals(cust)) {
//
//		} else if (name.equals(vendor)) {
//
//		} else
//			throw new AccessDeniedException("The user does not have access to this conversation.");
//
//	}
//
//	public void edit(String message, User name) throws AccessDeniedException {
//
//		if (name.equals(cust)) {
//
//		} else if (name.equals(vendor)) {
//
//		} else
//			throw new AccessDeniedException("The user does not have access to this conversation.");
//
//	}
//
//	public void delete(String message, User name) throws AccessDeniedException {
//
//		if (name.equals(cust)) {
//
//		} else if (name.equals(vendor)) {
//
//		} else
//			throw new AccessDeniedException("The user does not have access to this conversation.");
//
//	}
//
//	public Customer getCustomer(){
//		return cust;
//	}
//
//	public Seller getSeller(){
//		return vendor;
//	}
//
//	public String toString(User name) /*throws AccessDeniedException {
//
//		if (name.equals(cust)) {
//
//		} else if (name.equals(vendor)) {
//
//		} /*else
//			throw new AccessDeniedException("The user does not have access to this conversation log.");
//		return "to be implemented";
//	}
//
//	public ArrayList<String> getMessageList(User name) /*throws AccessDeniedException {
//
//		if (name.equals(cust)) {
//
//		} else if (name.equals(vendor)) {
//
//		} /*else
//			throw new AccessDeniedException("The user does not have access to this conversation log.");
//		return new ArrayList<String>();
//	}
//}
//
