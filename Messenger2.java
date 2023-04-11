package pj4;

import java.util.ArrayList;
import java.io.*;

/**
 * Messnger2 class
 * <p>
 * This class is similar to the Messenger class but it handles individual instances
 * of editing. It has private fields customer, seller, and store.
 *
 * @author Amelia Williams, Meha Kavoori, Anish Puri, Tyler Barnett
 * @version 4/10/2023
 */
public class Messenger2 implements Serializable {
    private Customer customer; // Messenger2's customer field
    private Seller seller; // Messenger2's seller field
    private Store store; // Messenger2's store field

    ArrayList<Integer> deletedByCust; // arrList of messages deleted by Customers
    ArrayList<Integer> deletedBySell; // arrList of messages deleted by Sellers
    ArrayList<String> cust2vend; // arrlist of messenges sent to seller from customer
    ArrayList<String> vend2cust; // arrList of messenges sent customer from seller

    // require instance variables cust, sell and store
    public Messenger2(Customer cust, Seller sell, Store store) {
        this.customer = cust; // customer part of message
        this.seller = sell; // seller part of message
        this.store = store; // store part of message
        vend2cust = new ArrayList<>(); // new list of messeges sent to customer from seller
        cust2vend = new ArrayList<>(); // new list of messages sents to seller from customer
        deletedByCust = new ArrayList<>(); // new list of messages deleted by the customer
        deletedBySell = new ArrayList<>(); // new list of messages deleted by the seooer
    }


    //returns customer
    public Customer getCustomer() {
        return customer;
    }

    // returns Seller
    public Seller getSeller() {
        return seller;
    }

    /// returns store
    public Store getStore() {
        return store;
    }

    // get list of messages sent from customers to soo
    public ArrayList<String> getCust2vend() {
        return cust2vend;
    }

    // gets list of messages sent from seller to customer
    public ArrayList<String> getVend2cust() {
        return vend2cust;
    }

    // gven String of from an old message, a new message, , Stores, and user Id number
    public void editMessage(String old, String newMsg, Customer c, Store st, int idNum) {
        if (idNum == 1) {
            if (cust2vend.contains(old)) {
                cust2vend.set(cust2vend.indexOf(old), newMsg);
            } else if (vend2cust.contains(old)) {
                System.out.println("You don't have access to this method!");
            } else {
                System.out.println("Message not found!");
            }
        } else {
            if (vend2cust.contains(old)) {
                vend2cust.set(vend2cust.indexOf(old), newMsg);
            } else if (cust2vend.contains(old)) {
                System.out.println("You don't have access to this method!");
            } else {
                System.out.println("Message not found!");
            }
        }
    }

    // checks if requested message can be deleted by customer
    // if possible, when called will delete the message
    public void custDeleteMessage(String m, Customer c, Seller s) {
        if (cust2vend.contains(m)) {
            deletedByCust.add(cust2vend.indexOf(m));
        } else if (vend2cust.contains(m)) {
            deletedByCust.add(vend2cust.indexOf(m))
            ;
        } else {
            System.out.println("Message not found!");
        }
    }

    // checks if requested message can be deleted by seller
    // if possible, when called will delete the message
    public void sellDeleteMessage(String m, Customer c, Seller s) {
        if (cust2vend.contains(m)) {
            deletedBySell.add(cust2vend.indexOf(m));
        } else if (vend2cust.contains(m)) {
            deletedBySell.add(vend2cust.indexOf(m));
        } else {
            System.out.println("Message not found!");
        }
    }

    // creates new conversation from customer to seller given store, message sent and customer
    public void cust2vendSend(String s, Customer c, Store st) {
        File f = new File("conversations.ser");
        ArrayList<Messenger2> convos = listConversations(f, Messenger2.getNumCreated());
        for (Messenger2 m : convos) {
            if (m.getCustomer().equals(c) && m.getStore().equals(st)) {
                m.getCust2vend().add(s);
                m.getVend2cust().add("");
            }
        }
    }

    // returns number of messages sent from desired customer to desired store
    public int msgFromCustToStore(Customer c, Store s) {
        Messenger2 m = Messenger2.getConversation(c, s);
        int count = 0;
        if (m != null) {
            for (String s1 : m.getCust2vend()) {
                if (!s.equals("")) {
                    count++;
                }
            }
            return count;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // returns number of messages sent from desired store to desired customer
    public int msgFromStoreToCust(Customer c, Store s) {
        Messenger2 m = Messenger2.getConversation(c, s);
        int count = 0;
        if (m != null) {
            for (String s1 : m.getVend2cust()) {
                if (!s.equals("")) {
                    count++;
                }
            }
            return count;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // returns messages in longer Arraylist conversations when given specific customer and seller
    public ArrayList<String> getMsgsInConvo(Customer c, Seller s) {
        ArrayList<String> words = new ArrayList<>();
        for (Store s1 : s.getListOfStores()) {
            for (String s2 : Messenger2.getConversation(c, s1).getVend2cust()) {
                if (!s2.equals("")) {
                    words.add(s2);
                }
            }
            for (String s2 : Messenger2.getConversation(c, s1).getCust2vend()) {
                if (!s2.equals("")) {
                    words.add(s2);
                }
            }
        }
        return words;
    }

    // returns conversation from given customer and seller
    public static Messenger2 getConversation(Customer c, Store s) {
        File f = new File("conversations.ser");
        ArrayList<Messenger2> convos = listConversations(f, Messenger2.getNumCreated());
        for (Messenger2 m : convos) {
            if (m.getCustomer().equals(c) && m.getStore().equals(s)) {
                return m;
            }
        }
        return null;
    }

    // creates conversation.ser file based off of the inputted message which is sent to customer from seller
    public void vend2custSend(String m2, Seller s, Customer c) {
        File f = new File("conversations.ser");
        ArrayList<Messenger2> convos = listConversations(f, Messenger2.getNumCreated());
        for (Messenger2 m : convos) {
            if (m.getCustomer().equals(c) && m.getSeller().equals(s)) {
                m.getCust2vend().add("");
                m.getVend2cust().add(m2);
            }
        }
    }

    // creates conversation.ser file which displays conversation between Customer and Seller from Seller point of view
    public void displayConvoForSeller(Customer c, Seller s, Store st) {
        File f = new File("conversations.ser");
        for (Messenger2 m2 : listConversations(f, getNumCreated())) {
            if (m2.getSeller().equals(s) && m2.getCustomer().equals(c) && m2.getStore().equals(st)) {
                for (int i = 0; i < cust2vend.size(); i++) {
                    String s1 = cust2vend.get(i);
                    if (!s1.equals("") && !deletedBySell.contains(i)) {
                        System.out.println(s);
                    }
                    s1 = vend2cust.get(i);
                    if (!s1.equals("") && !deletedBySell.contains(i)) {
                        System.out.println(s);
                    }
                }
            }
        }
    }

    // creates conversation.ser file which displays conversation between Customer and Seller from Customer point of view
    public void displayConvoForCustomer(Customer c, Seller s, Store st) {
        File f = new File("conversations.ser");
        for (Messenger2 m2 : listConversations(f, getNumCreated())) {
            if (m2.getSeller().equals(s) && m2.getCustomer().equals(c) && m2.getStore().equals(st)) {
                for (int i = 0; i < cust2vend.size(); i++) {
                    String s1 = cust2vend.get(i);
                    if (!s1.equals("") && !deletedByCust.contains(i)) {
                        System.out.println(s);
                    }
                    s1 = vend2cust.get(i);
                    if (!s1.equals("") && !deletedByCust.contains(i)) {
                        System.out.println(s);
                    }
                }
            }
        }
    }

    // method lists all conversations
    public static ArrayList<Messenger2> listConversations(File f, int fileLength) {
        ArrayList<Messenger2> conversations = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            for (int i = 0; i < fileLength; i++) {
                Messenger2 conversation = (Messenger2) ois.readObject();
                conversations.add(conversation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversations;
    }

    // method checks if conversations exists already between two parties
    public static boolean newConversation(String s, Customer cust, Seller sell, Store store) {
        File convos = new File("conversations.ser");
        ArrayList<Messenger2> convosList = Messenger2.listConversations(convos, Messenger2.getNumCreated());
        for (Messenger2 m : convosList) {
            if (m.getCustomer().equals(cust) && m.getStore().equals(store) && m.getSeller().equals(sell)) {
                System.out.println("Conversation Already Started");
                return false;
            }
        }
        Messenger2 m = new Messenger2(cust, sell, store);
        //Messenger2.setNumCreated(User.getNumCreated() + 1);
        convosList.add(m);
        Messenger2.writeConvos(convosList, convos);
        return true;

    }

    // method writes the conversations stored in conversation list to file f
    public static void writeConvos(ArrayList<Messenger2> convosList, File f) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Messenger2 m1 : convosList) {
                oos.writeObject(m1);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // gets number messages created
    public static int getNumCreated() {
        File f = new File("numMessagesCreated.txt");
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            return Integer.parseInt(bfr.readLine());
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // sets number messages created
    public static void setNumCreated(int i) {
        File f = new File("numMessagesCreated.txt");
        try {
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(i);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}