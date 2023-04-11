package pj4;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * Seller class
 *
 * The Seller class represents a seller object which is a type of user that interacts with
 * users of its type and customers. An intance of the class is created with attributes name, 
 * email and password.
 *
 * @author Amelia Williams, Meha Kavoori, Anish Puri, Tyler Barnett
 * @version 4/10/2023
 *
 */
public class Seller extends User {
    private Customer[] customerList; // Seller's array of Customers

    private ArrayList<Store> listOfStores; // ArrayList of Seller's stores

    private static final long serialVersionUID = 6387730094462016452L; 
    private String storeName;  // name for Seller's store


// the constructor uses super for some of its fields because it extends User
// User already contains some of these fields
    public Seller(String name, String email, String password) {
        super(name, email, password);
        listOfStores = new ArrayList<Store>();
    }

    // following method allows for a seller to add a store using a String of the store name
    public void addStore(String storeName) throws IllegalArgumentException {
        ArrayList<Store> allStores = Store.getAllStores();

        // iterates through a list all the current stores that exist under a seller
        // if the name of the store they want to create already exists (equals another store name), it
        // throws a descriptive exception that there is a duplicate store name.
        // Afterwards, the storename is set to the most recent store name

        for (Store temp : allStores) {
            if (temp.getStoreName().equals(storeName))
                throw new IllegalArgumentException("Duplicate Store Name");
        }
        this.storeName = storeName;

        Store st = new Store(this, storeName);
        listOfStores.add(st);
    }

// following method sets the customer list as a list
    public void setCustomerList(Customer[] customerList) {
        this.customerList = customerList;
    }

    //following method gets the current store name
    public String getStoreName() {
        return storeName;
    }

  //following method sets the store name using a String store name
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    // following method gets an array list of all the stores that exist
    public ArrayList<Store> getListOfStores() {
        return listOfStores;
    }

    // following method adds a customer by creating an array list that sets the customer list
    // into each element of the array list. A new customer is added to the arraylist then the entire
    // array list of customers is returned with the next for loop
    public void addCustomer(Customer c) {
        ArrayList<Customer> arr = new ArrayList<>();
        for (int i = 0; i < customerList.length; i++) {
            arr.set(i, this.customerList[i]);
        }

        arr.add(c);

        for (int i = 0; i < arr.size(); i++) {
            this.customerList[i] = arr.get(i);
        }
    }

    //following method create a conversation between a customer and store by creating a new messenger2 object
    public Messenger2 createConversation(Customer c1, Store st) {
        Messenger2 c = new Messenger2(c1, this, st);
        return c;
    }

    // following method gets the customer at a certain index from a file named "accounts.ser"
    public Customer getCustomer(int index) {
        File f = new File("accounts.ser");
        int count = 1;
        return null;
    }


    // following method displays the customers that exist from the file "accounts.ser" but if the user is blocked it does not
    // get the blocked custoomer
    public void displayCustomers() {
        File f = new File("accounts.ser");
        int count = 1;
        for (User u : getUsers()) {
            if (u instanceof Customer) {
                if (!this.getBlockedUsers().contains(u)) {

                }
            }
        }
    }

    // following method checks if an object is an instance of a seller before creating a seller object that returns true
    // if the seller's email and password equals that of a seller that currently exists and returns false if it is not equal
    public boolean equals(Object o) {
        if (o instanceof Seller) {
            Seller seller = (Seller) o;
            if (seller.getEmail().equals(this.getEmail()) && seller.getPassword().equals(this.getPassword())) {
                return true;
            }
        }
        return false;
    }

    // following method checks if the seller can message a customer by checking if an object is either an instance of 
    // a seller or customer then returns true if one of them are, then false if both are untrue
    public boolean canMessage(Object o) {
        if (o instanceof Seller || o instanceof Customer) {
            return true;
        }
        return false;
    }


}
