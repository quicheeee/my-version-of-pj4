package pj4;

import java.util.ArrayList;
import java.io.*;

public class Messenger2 implements Serializable {
    private Customer customer;
    private Seller seller;
    private Store store;

    ArrayList<Integer> deletedByCust;
    ArrayList<Integer> deletedBySell;
    ArrayList<String> cust2vend;
    ArrayList<String> vend2cust;

    public Messenger2 (Customer cust, Seller sell, Store store){
        this.customer = cust;
        this.seller = sell;
        this.store = store;
        vend2cust = new ArrayList<>();
        cust2vend = new ArrayList<>();
        deletedByCust = new ArrayList<>();
        deletedBySell = new ArrayList<>();
    }



    public Customer getCustomer() {
        return customer;
    }

    public Seller getSeller() {
        return seller;
    }

    public Store getStore() {
        return store;
    }

    public ArrayList<String> getCust2vend() {
        return cust2vend;
    }

    public ArrayList<String> getVend2cust() {
        return vend2cust;
    }

    public void editMessage(String old, String newMsg, Customer c, Store st, int idNum){
        if(idNum == 1){
            if(cust2vend.contains(old)){
                cust2vend.set(cust2vend.indexOf(old), newMsg);
            } else if(vend2cust.contains(old)) {
                System.out.println("You don't have access to this method!");
            } else{
                System.out.println("Message not found!");
            }
        } else {
            if(vend2cust.contains(old)){
                vend2cust.set(vend2cust.indexOf(old), newMsg);
            } else if(cust2vend.contains(old)){
                System.out.println("You don't have access to this method!");
            }
            else {
                System.out.println("Message not found!");
            }
        }
    }


    public void custDeleteMessage(String m, Customer c, Seller s) {
        if(cust2vend.contains(m)){
            deletedByCust.add(cust2vend.indexOf(m));
        } else if(vend2cust.contains(m)){
            deletedByCust.add(vend2cust.indexOf(m))
;       } else {
            System.out.println("Message not found!");
        }
    }

    public void sellDeleteMessage(String m, Customer c, Seller s) {
        if(cust2vend.contains(m)){
            deletedBySell.add(cust2vend.indexOf(m));
        } else if(vend2cust.contains(m)){
            deletedBySell.add(vend2cust.indexOf(m))
            ;       } else {
            System.out.println("Message not found!");
        }
    }

    public void cust2vendSend(String s, Customer c, Store st){
        File f = new File("conversations.ser");
        ArrayList<Messenger2> convos = listConversations(f, Messenger2.getNumCreated());
        for(Messenger2 m: convos){
            if(m.getCustomer().equals(c) && m.getStore().equals(st)){
                m.getCust2vend().add(s);
                m.getVend2cust().add("");
            }
        }
    }

    public int msgFromCustToStore(Customer c, Store s){
        Messenger2 m = Messenger2.getConversation(c, s);
        int count = 0;
        if(m!= null){
            for(String s1: m.getCust2vend()){
                if(!s.equals("")){
                    count++;
                }
            }
            return count;
        } else{
            throw new IllegalArgumentException();
        }
    }

    public int msgFromStoreToCust(Customer c, Store s){
        Messenger2 m = Messenger2.getConversation(c, s);
        int count = 0;
        if(m!= null){
            for(String s1: m.getVend2cust()){
                if(!s.equals("")){
                    count++;
                }
            }
            return count;
        } else{
            throw new IllegalArgumentException();
        }
    }

    public ArrayList<String> getMsgsInConvo(Customer c, Seller s){
        ArrayList<String> words = new ArrayList<>();
        for(Store s1: s.getListOfStores()){
            for(String s2: Messenger2.getConversation(c, s1).getVend2cust()){
                if(!s2.equals("")){
                    words.add(s2);
                }
            }
            for(String s2: Messenger2.getConversation(c, s1).getCust2vend()){
                if(!s2.equals("")){
                    words.add(s2);
                }
            }
        }
        return words;
    }
    public static Messenger2 getConversation(Customer c, Store s){
        File f = new File("conversations.ser");
        ArrayList<Messenger2> convos = listConversations(f, Messenger2.getNumCreated());
        for(Messenger2 m: convos) {
            if(m.getCustomer().equals(c) && m.getStore().equals(s)){
                return m;
            }
        }
        return null;
    }
    public void vend2custSend(String m2, Seller s, Customer c){
        File f = new File("conversations.ser");
        ArrayList<Messenger2> convos = listConversations(f, Messenger2.getNumCreated());
        for(Messenger2 m: convos){
            if(m.getCustomer().equals(c) && m.getSeller().equals(s)){
                m.getCust2vend().add("");
                m.getVend2cust().add(m2);
            }
        }
    }

    public void displayConvoForSeller(Customer c, Seller s, Store st){
        File f = new File("conversations.ser");
        for(Messenger2 m2: listConversations(f, getNumCreated())){
            if(m2.getSeller().equals(s) && m2.getCustomer().equals(c) && m2.getStore().equals(st)){
                for(int i = 0; i < cust2vend.size(); i++){
                    String s1 = cust2vend.get(i);
                    if(!s1.equals("") && !deletedBySell.contains(i)){
                        System.out.println(s);
                    }
                    s1 = vend2cust.get(i);
                    if(!s1.equals("") && !deletedBySell.contains(i)) {
                        System.out.println(s);
                    }
                }
            }
        }
    }

    public void displayConvoForCustomer(Customer c, Seller s, Store st){
        File f = new File("conversations.ser");
        for(Messenger2 m2: listConversations(f, getNumCreated())){
            if(m2.getSeller().equals(s) && m2.getCustomer().equals(c) && m2.getStore().equals(st)){
                for(int i = 0; i < cust2vend.size(); i++){
                    String s1 = cust2vend.get(i);
                    if(!s1.equals("") && !deletedByCust.contains(i)){
                        System.out.println(s);
                    }
                    s1 = vend2cust.get(i);
                    if(!s1.equals("") && !deletedByCust.contains(i)) {
                        System.out.println(s);
                    }
                }
            }
        }
    }


    public static ArrayList<Messenger2> listConversations (File f, int fileLength) {
            ArrayList<Messenger2> conversations = new ArrayList<>();
            try {
                FileInputStream fis = new FileInputStream(f);
                ObjectInputStream ois = new ObjectInputStream(fis);
                for (int i = 0; i < fileLength; i++){
                    Messenger2 conversation = (Messenger2) ois.readObject();
                    conversations.add(conversation);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return conversations;
    }

    public static boolean newConversation(String s, Customer cust, Seller sell, Store store) {
        File convos = new File("conversations.ser");
        ArrayList<Messenger2> convosList = Messenger2.listConversations(convos, Messenger2.getNumCreated());
        for(Messenger2 m: convosList) {
            if(m.getCustomer().equals(cust) && m.getStore().equals(store) && m.getSeller().equals(sell)){
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
    public static void writeConvos(ArrayList<Messenger2> convosList, File f){
        try{
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for(Messenger2 m1: convosList) {
                oos.writeObject(m1);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getNumCreated(){
        File f = new File("numMessagesCreated.txt");
        try{
            FileReader fr = new FileReader(f);
            BufferedReader bfr = new BufferedReader(fr);
            return Integer.parseInt(bfr.readLine());
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void setNumCreated(int i){
        File f = new File ("numMessagesCreated.txt");
        try{
            FileWriter fw = new FileWriter(f);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(i);
            pw.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
