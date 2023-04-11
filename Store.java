package pj4;

import java.io.*;
import java.util.*;
/**
 * Store Class
 *
 * The Store Class represents a Store object. The Store class has methods which allow users to
 * get, set, and access a list of Stores.
 *
 * @author Amelia Williams, Meha Kavoori, Anish Puri, Tyler Barnett
 * @version 4/10/2023
 *
 */
public class Store implements Serializable {
    private Seller seller; // the store's seller
    private List<Messenger2> messages; // the store's list of messages
    private String storeName; // the store's name
    // returns list of all store objects given all users
    public static ArrayList<Store> getAllStores() {
        ArrayList<Store> all = new ArrayList<Store>();
        for (User u : User.getUsers()) {
            if (u instanceof Seller) {
                all.addAll(((Seller) u).getListOfStores());
            }
        }
        return all;
    }
    // returns all store obects associated with inputted user
    public static ArrayList<Store> getAllStoresForUser (User u) {
        ArrayList<Store> all = new ArrayList<Store>();

        for (User temp : User.getUsers()) {
            if (temp instanceof Seller) {
                if (!(temp.getBlockedUsers().contains(u) || u.getBlockedUsers().contains(temp)))
                    all.addAll(((Seller) temp).getListOfStores());
            }
        }
        return all;
    }
    // Store constructor
    public Store(Seller seller, String storeName) {

        this.seller = seller;
        this.messages = new ArrayList<>();
        this.storeName = storeName;

    }
    // returns seller in Store
    public Seller getSeller() {
        return seller;
    }
    // returns store name
    public String getStoreName() {
        return storeName;
    }
    // sets the store name
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    // adds message to list of the Store's messages
    public void addMessages(Messenger2 message) {
        messages.add(message);
    }
    // returns the number of messages in store messages list
    public int getNumMessages() {
        return messages.size();
    }

    // returns number of messages sent by specified customer
    public int getNumMessagesSentByCus(Customer customer) {
        int count = 0;
        for (Messenger2 message : messages) {
            // gets the number of messages sent by the customer
            if (message.getCustomer() == customer) {
                count++;
            }
        }
        return count;
    }
    // returns number of messages sent by specified seller
    public int getNumMessagesSentBySell(Seller seller) {
        int count = 0;
        for (Messenger2 message : messages) {
            //gets number of messages sent by seller
            if (message.getSeller() == seller) {
                count++;
            }
        }
        return count;
    }
    // returns list of store;s customers
    public List<Customer> getCustomers() {
        //not sure if needed but puts the customers into list --> might delete later
        List<Customer> customers = new ArrayList<>();
        for (Messenger2 message : messages) {
            customers.add(message.getCustomer());
        }
        return customers;

    }
    // returns map of most common words said by customer
    public Map<String, Integer> getMostCommonWordsCust() {
        Map<String, Integer> commonWordsCust = new HashMap<>();
        for (Messenger2 message : messages) {
            // there should be a getContent method in messages that stores the
            //String[] words = message.getMessageList().split(" ");
            //for (String word : words) {
            //    commonWordsCust.put(word, commonWordsCust.getOrDefault(word, 0) + 1);
            //}
        }
        return commonWordsCust;
    }
    // returns most common words said by seller
    public Map<String, Integer> getMostCommonWordsSell() {
        Map<String, Integer> commonWordsSell = new HashMap<>();
        for (Messenger2 message : messages) {
            // there should be a getContent method in messages that stores the
            //String[] words = message.getMessageList().split(" ");
            //for (String word : words) {
            //    commonWordsSell.put(word, commonWordsSell.getOrDefault(word, 0) + 1);
            //}
        }
        return commonWordsSell;
    }
    // returns total customer messages
    public Map<Customer, Integer> getNumCustomerMessages() {
        Map<Customer, Integer> numCustomerMessages = new HashMap<>();
        for (Messenger2 message : messages) {
            Customer sender = message.getCustomer();
            if (sender instanceof Customer) {
                numCustomerMessages.put(sender, numCustomerMessages.getOrDefault(sender, 0) + 1);
            }
        }
        return numCustomerMessages;
    }


    /* public int getMessagesSentToStore() {
        int numMessagesSent = 0;

        for (Messenger2 m : messages) {
            numMessagesSent = m.msgFromCustToStore(User.findUserWithEmail(customer.getEmail()), getStoreName());

        }
        return numMessagesSent;
    }
     */
    // returns the messages that have recieved by desired store
    public int getMessagesReceivedByStore() {
        int numMessagesReceived = 0;
        for (Messenger2 message : messages) {
            //if (message.getSender() == this.getStoreName() && message.getRecipient() == message.getCustomer()) {
            //    numMessagesReceived++;
            //}
        }
        return numMessagesReceived;

    }
    // returns a map of the number of seller messages
    public Map<Seller, Integer> getNumSellerMessages() {
        Map<Seller, Integer> numSellerMessages = new HashMap<>();
        for (Messenger2 message : messages) {
            Seller sender = message.getSeller();
            if (sender instanceof Seller) {
                numSellerMessages.put(sender, getNumSellerMessages().getOrDefault(sender, 0) + 1);
            }
        }
        return numSellerMessages;
    }
    // returns list of customers based off of sorted messages
    public List<Customer> CustomersSortedNumMessages() {
        List<Customer> customers = new ArrayList<>();
        for (Messenger2 message : messages) {
            Customer sender = message.getCustomer();
            if (sender instanceof Customer && !customers.contains(sender)) {
                customers.add(sender);
            }
        }
        Collections.sort(customers, Collections.reverseOrder());
        //customers.sort((c1, c2) -> getNumCustomerMessages().get(c2) - getNumCustomerMessages().get(c1));
        return customers;
    }
    // returns sorted String list of Customers' common words
    public List<String> SortedCustCommonWords() {
        List<String> commonWords = new ArrayList<>((Collection) getMostCommonWordsCust());
        Collections.sort(commonWords, Collections.reverseOrder());
        return commonWords;
    }
    // given a specifc customer will print that customer's statistics in a dashboard format
    public void printCustomerDashboard(Customer customer) {
        //  Map<Customer, Integer> customers = getNumCustomerMessages();
        // Map<String, Integer> commonWords = getMostCommonWordsSell();
        // getStoreName method needs to be created that gets the name of each specific Store
        //for (Store s : seller.getListOfStores()) {
        System.out.println("Dashboard for customer");
        System.out.println("----------------");
        //TODO
        //List<Store> storesByMessagesReceived = new ArrayList<>(Customer.storeList);
        List<Store> storesByMessagesReceived = new ArrayList<Store>();
        List<Store> storesByMessagesSent = new ArrayList<>();
        System.out.println("List of stores by messages received:");
        for (Store store : storesByMessagesReceived) {
            int numMessagesReceived = store.getMessagesReceivedByStore();
            if (numMessagesReceived > 0) {
                System.out.println(store.getSeller() + "(" + store.getStoreName() +
                        "):" + numMessagesReceived + "messages");
            }
        }
        System.out.println();
        System.out.println("List of stores by number of messages sent by customer: ");
        for (Messenger2 m : messages) {
            int numMessagesSent = m.msgFromCustToStore((Customer) User.findUserWithEmail(customer.getEmail()), this);
            if (numMessagesSent > 0) {
                System.out.println(getSeller() + "(" + getStoreName() +
                        ")" + numMessagesSent + "messages");
            }
        }

    }
    // given a specific customer, will print that customer's statistics in a sorted format.
    public void printCustomerDashboardSorted(Customer customer) {
        List<Customer> customers = CustomersSortedNumMessages();
        Map<String, Integer> commonWords = getMostCommonWordsSell();
        // getStoreName method needs to be created that gets the name of each specific Store
        System.out.println("Dashboard for store " + getStoreName());
        System.out.println("----------------");
        System.out.println("Customers by message count: ");

        // for (Customer customer : customers) {
        // int messageCount =

        // }
    }
    // given a specific seller, a seller's statistic's will be printed in a deashboard format
    public void printSellerDashboard(Seller seller) {
        Map<Customer, Integer> customers = getNumCustomerMessages();
        Map<String, Integer> commonWords = getMostCommonWordsSell();
        for (Store s : seller.getListOfStores()) {
            System.out.println("Store Dashboard - " + s.getStoreName());
            System.out.println("----------------");
            System.out.println("Total messages: " + s.getStoreName());
            System.out.println("List of customers: " + getCustomers());
            System.out.println("Customer Message Counts:");
            Map<Customer, Integer> customerMessagecounts = getNumCustomerMessages();
            List<Map.Entry<Customer, Integer>> customerMessageEntries = new ArrayList<>(customerMessagecounts.entrySet());
            //sorts the Message entries from the greatest amount to least amount
            //customerMessageEntries.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
            for (Map.Entry<Customer, Integer> entry : customerMessageEntries) {
                //System.out.println(entry.getKey().getUserID() + ":" + entry.getValue());
            }
            System.out.println("Most Common Words: ");
            Map<String, Integer> commonWordsCust = getMostCommonWordsCust();
            for (Map.Entry<String, Integer> entry : commonWordsCust.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }
    // given a specific seller, a seller's sorted statistic's will be printed in a dashboard format
    public void printSellerDashboardSorted(Seller seller) {
        List<Customer> customers = CustomersSortedNumMessages();
        Map<String, Integer> commonWords = getMostCommonWordsSell();
        for (Store s : seller.getListOfStores()) {
            System.out.println("Store Dashboard - " + s.getStoreName());
            System.out.println("----------------");
            System.out.println("Total messages: " + s.getNumMessages());
            System.out.println("List of customers: " + s.getCustomers());
            System.out.println("Customer Message Counts:");
            Map<Customer, Integer> customerMessagecounts = getNumCustomerMessages();
            List<Map.Entry<Customer, Integer>> customerMessageEntries = new ArrayList<>(customerMessagecounts.entrySet());
            //sorts the Message entries from the greatest amount to least amount
            customerMessageEntries.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
            for (Map.Entry<Customer, Integer> entry : customerMessageEntries) {
                System.out.println(entry.getKey().getEmail() + ":" + entry.getValue());
            }
            System.out.println("Most Common Words: ");
            Map<String, Integer> commonWordsCust = getMostCommonWordsCust();
            for (Map.Entry<String, Integer> entry : commonWordsCust.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }
    // given the inputted name and seller, method checks if store name already exists
    public static boolean newStore(String name, Seller seller) {
        File list = new File("stores.ser");
        ArrayList<Store> stores = Store.listStores(list, Store.getNumStoresCreated());
        for(Store s: stores) {
            if(s.getStoreName().equals(name)){
                System.out.println("Store Name Taken");
                return false;
            }
        }
        Store store = new Store(seller, name);
        stores.add(store);
        Store.setNumStoresCreated(Store.getNumStoresCreated() + 1);
        Store.writeStores(stores, list);
        return true;
    }
    // given the inputted file and filelength, method returns an ArrayList of Stores
    public static ArrayList<Store> listStores(File f, int fileLength) {
        ArrayList<Store> stores = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            for (int i = 0; i < fileLength; i++){
                Store st = (Store) ois.readObject();
                stores.add(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stores;
    }
    // method reads numStoresCreated.txt file to get the number of stores created
    public static int getNumStoresCreated(){
        File f = new File("numStoresCreated.txt");
        try{
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            return Integer.parseInt(bfr.readLine());
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    // method sets the number in the nmStoresCreated.txt file
    public static void setNumStoresCreated(int i){
        File f = new File ("numStoresCreated.txt");
        try{
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(i);
            pw.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    // given arrList of stores, the method will write to a file the list of the stores.
    public static void writeStores(ArrayList<Store> stores, File f){
        try{
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for(Store s1: stores) {
                oos.writeObject(s1);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
