
/*package pj4;
import java.io.*;
import java.util.*;

public class Store2 implements Serializable {
    private Seller seller;
    private List<Message> messages;
    private String storeName;
    private static final long serialVersionUID = 0;

    private static ArrayList<Store2> allStores;

    public Store2(Seller seller, String storeName) {

        this.seller = seller;
        this.messages = new ArrayList<>();
        this.storeName = storeName;
        newStore(storeName, seller);
    }

    public static void addToAllStores(Store2 s){
        allStores.add(s);
    }

    public static ArrayList<Store2> getAllStores(){
        return allStores;
    }

    public Seller getSeller() {
        return seller;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

   /* public void addMessages(Messenger2 message) {
        messages.add(message);
    }
    */
/*
    public int getNumMessages() {
        return messages.size();
    }


    public int getNumMessagesSentByCus(Customer customer) {
        int count = 0;
        for (Message message : messages) {
            // gets the number of messages sent by the customer
            if (message.getSender().equals(customer) || message.getReceiver().equals(customer)) {
                count++;
            }
        }
        return count;
    }

    public int getNumMessagesSentBySell(Seller seller) {
        int count = 0;
        for (Message message : messages) {
            //gets number of messages sent by seller
            if (message.getSender().equals(seller) ||message.getReceiver().equals(seller)) {
                count++;
            }
        }
        return count;
    }

    public List<Customer> getCustomers() {
        //not sure if needed but puts the customers into list --> might delete later
        List<Customer> customers = new ArrayList<>();
        for (Message message : messages) {
            if(message.getSender() instanceof Customer && !customers.contains(message.getSender())) {
                customers.add((Customer) message.getSender());
            } else if(message.getReceiver() instanceof Customer && !customers.contains(message.getSender())){
                customers.add((Customer) message.getReceiver());
            }
        }
        return customers;

    }


    public Map<String, Integer> getMostCommonWords() {
        Map<String, Integer> commonWordsSell = new HashMap<>();
        for (Message message : messages) {
            // there should be a getContent method in messages that stores the
            ArrayList<String> messageList = message.getMsgsInConvo();
            ArrayList<String> wordList = new ArrayList<String>();
            for (int i = 0; i < messageList.size(); i++) {
                String[] words = messageList.get(i).split(" ");
                for (String s : words) {
                    wordList.add(s);
                }
            }
            for (String word : wordList) {
                commonWordsSell.put(word, commonWordsSell.getOrDefault(word, 0) + 1);
            }
        }
        return commonWordsSell;
    }

    public Map<Customer, Integer> getNumCustomerMessages() {
        Map<Customer, Integer> numCustomerMessages = new HashMap<>();
        for (Message message : messages) {
            if (message.getSender() instanceof Customer) {
                numCustomerMessages.put((Customer) message.getSender(), numCustomerMessages.getOrDefault(message.getSender(), 0) + 1);
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

    /*public int getMessagesReceivedByStore() {
        int numMessagesReceived = 0;
        for (Messenger2 message : messages) {
            if (message.getSender() == this.getStoreName() && message.getRecipient() == message.getCustomer()) {
                numMessagesReceived++;
            }
        }
        return numMessagesReceived;
    }
     */
/*
    public Map<Seller, Integer> getNumSellerMessages() {
        Map<Seller, Integer> numSellerMessages = new HashMap<>();
        for (Message message : messages) {
            if (message.getSender() instanceof Seller) {
                numSellerMessages.put((Seller) message.getSender(), getNumSellerMessages().getOrDefault(message.getSender(), 0) + 1);
            }
        }
        return numSellerMessages;
    }

    public List<Customer> CustomersSortedNumMessages() {
        List<Customer> customers = new ArrayList<>();
        for (Message message : messages) {
            User sender = message.getSender();
            if (sender instanceof Customer && !customers.contains(sender)) {
                customers.add((Customer) sender);
            }
        }
        Collections.sort(customers, Collections.reverseOrder());
        //customers.sort((c1, c2) -> getNumCustomerMessages().get(c2) - getNumCustomerMessages().get(c1));
        return customers;
    }

    public List<String> SortedCustCommonWords() {
        List<String> commonWords = new ArrayList<>((Collection) getMostCommonWords());
        Collections.sort(commonWords, Collections.reverseOrder());
        return commonWords;
    }

    public void printCustomerDashboard(Customer customer) {

        System.out.println("Dashboard for customer");
        System.out.println("----------------");
        List<Store> storesByMessagesReceived = new ArrayList<>(customer.getStoreList());
        List<Store> storesByMessagesSent = new ArrayList<>();
        System.out.println("List of stores by messages received:");
        for (Store store : storesByMessagesReceived) {
            //  int numMessagesReceived = store.getMessagesReceivedByStore();
            String convo = Conversation.getConversation(store,customer);
            int numMessagesReceived = Conversation.getSellerMsgsInConvo(convo);
            if (numMessagesReceived > 0) {
                System.out.println(store.getSeller() + "(" + store.getStoreName() +
                        "): " + numMessagesReceived + "messages");
            }
        }
        System.out.println();
        System.out.println("List of stores by number of messages sent by customer: ");
        for (Message m : messages) {
            int numMessagesSent = m.getCustomerMsgsInConvo();
            if (numMessagesSent > 0) {
                System.out.println(getSeller() + "(" + getStoreName() +
                        ")" + numMessagesSent + "messages");
            }
        }

    }

    public void printCustomerDashboardSorted(Customer customer) {
        //List<Customer> customers = CustomersSortedNumMessages();
        //Map<String, Integer> commonWords = getMostCommonWords();
        List<Integer> messagesReceivedSorted = new ArrayList<>();
        List<Integer> messagesSentSorted = new ArrayList<>();
        System.out.println("Dashboard for customer");
        System.out.println("----------------");
        System.out.println("List of stores by messages received: ");
       /* for (int i = 0; i < customer.getStoreList().size(); i++) {
            String convo = Conversation.getConversation(customer.getStoreList().get(i), customer);
            int numMessagesReceived = Conversation.getSellerMsgsInConvo(convo);
            messagesReceivedSorted.add(numMessagesReceived);
            messagesReceivedSorted.sort(Comparator.reverseOrder());
            if (numMessagesReceived > 0) {
                System.out.println(store.getSeller() + "(" + store.getStoreName() + "): " +
                        messagesReceivedSorted.get(messagesReceivedSorted.size() - 1));
            }
        } */
        /*for (Store store : customer.getStoreList()) {
            String convo = Conversation.getConversation(store, customer);
            int numMessagesReceived = Conversation.getSellerMsgsInConvo(convo);
            messagesReceivedSorted.add(numMessagesReceived);
            messagesReceivedSorted.sort(Comparator.reverseOrder());
            if (numMessagesReceived > 0) {
                System.out.println(store.getSeller() + "(" + store.getStoreName() + "): " +
                        messagesReceivedSorted.get(messagesReceivedSorted.size() - 1));
            }
        }
        System.out.println();
        System.out.println("List of stores by number of messages sent by customer: ");
        for (Message m : messages) {
            int numMessagesSent = m.getCustomerMsgsInConvo();
            messagesSentSorted.add(numMessagesSent);
            messagesSentSorted.sort(Comparator.reverseOrder());
            if (numMessagesSent > 0) {
                System.out.println(getSeller() + "(" + getStoreName() + "): " +
                        messagesSentSorted.get(messagesSentSorted.size() - 1));
            }
        }


    }

    public void printSellerDashboard(Seller seller) {
        Map<Customer, Integer> customers = getNumCustomerMessages();
        Map<String, Integer> commonWords = getMostCommonWords();
        for (Store2 s : seller.getListOfStores()) {
            System.out.println("Store Dashboard - " + s.getStoreName());
            System.out.println("----------------");
            System.out.println("Total messages: " + s.getNumCustomerMessages());
            System.out.println("List of customers: " + getCustomers());
            System.out.println("Customer Message Counts:");
            Map<Customer, Integer> customerMessagecounts = getNumCustomerMessages();
            List<Map.Entry<Customer, Integer>> customerMessageEntries = new ArrayList<>(customerMessagecounts.entrySet());
            //sorts the Message entries from the greatest amount to least amount
            //customerMessageEntries.sort(Map.Entry.comparingByValue(Collections.reverseOrder()));
            for (Map.Entry<Customer, Integer> entry : customerMessageEntries) {
                System.out.println(entry.getKey().getName() + ":" + entry.getValue());
            }
            System.out.println("Most Common Words: ");
            Map<String, Integer> commonWordsCust = getMostCommonWords();
            for (Map.Entry<String, Integer> entry : commonWordsCust.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }

    public void printSellerDashboardSorted(Seller seller) {
        List<Customer> customers = CustomersSortedNumMessages();
        Map<String, Integer> commonWords = getMostCommonWords();
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
            Map<String, Integer> commonWordsCust = getMostCommonWords();
            for (Map.Entry<String, Integer> entry : commonWordsCust.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue());
            }
        }
    }

    public boolean newStore(String name, Seller seller) {
        File list = new File("stores.ser");
        ArrayList<Store> stores = Store.listStores(list, Store.getNumStoresCreated());
        for (Store s : stores) {
            if (s.getStoreName().equals(name)) {
                System.out.println("Store Name Taken");
                return false;
            }
        }
        stores.add(this);
        Store.setNumStoresCreated(Store.getNumStoresCreated() + 1);
        Store.writeStores(stores, list);
        return true;
    }

    public static Store findStoreWithName(String name){
        try{
            File stores = new File("stores.ser");
            ArrayList<Store> stores2 = listStores(stores, getNumStoresCreated());

            for(Store s: stores2) {
                if(s.getStoreName().equals(name)) {
                    return s;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static boolean removeStore(String name, Seller seller) {
        File list = new File("stores.ser");
        ArrayList<Store> stores = Store.listStores(list, Store.getNumStoresCreated());
        for (Store s : stores) {
            if (s.getStoreName().equals(name)) {
                stores.remove(s);
                Store.setNumStoresCreated(Store.getNumStoresCreated() - 1);
                Store.writeStores(stores, list);
                return true;
            } else {
                System.out.println("The store you want to remove does not exist!");
                return false;
            }
        }
        return true;
    }

    public static ArrayList<Store> listStores(File f, int fileLength) {
        ArrayList<Store> stores = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fis);
            for (int i = 0; i < fileLength; i++) {
                Store st = (Store) ois.readObject();
                stores.add(st);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stores;
    }

    public static int getNumStoresCreated() {
        File f = new File("numStoresCreated.txt");
        try {
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            int i = Integer.parseInt(bfr.readLine());
            bfr.close();
            fr.close();
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setNumStoresCreated(int i) {
        File f = new File("numStoresCreated.txt");
        try {
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(i);
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeStores(ArrayList<Store> stores, File f) {
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for (Store s1 : stores) {
                oos.writeObject(s1);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

         */
