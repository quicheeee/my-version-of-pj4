package pj4;
import java.io.*;
import java.util.*;
public class User implements Serializable {
    private String name;
    private String email;
    private String password;
    //private int userID; //?
    private ArrayList<User> blockedUsers;
    //private ArrayList<User> blockedBy;
    //private ArrayList<User> invisibleTo;
    //private ArrayList<Messenger2> conversations;

    //private static final long serialVersionUID = 0;
    private static ArrayList<User> allUsers = null;
    private static final long serialVersionUID = -5554757084812506737L;

    public User(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
        blockedUsers = new ArrayList<User>();
        //userID = getNumCreated() + 1;
        //blockedBy = new ArrayList<User>();
        //invisibleTo = new ArrayList<User>();
        //conversations = new ArrayList<Messenger2> ();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getBlockedUsers(){
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<User> blockedUsers){
        this.blockedUsers = blockedUsers;
    }

    public static User signIn (String email, String password) {
        ArrayList<User> users = getUsers();
        for(User u: users) {
            if(u.getEmail().equals(email) && u.getPassword().equals(password)){
                return u;
            }
            //else if(u.getEmail().equals(email)) {
            //    return -1;
            //}
        }
        return null;
    }

/*
    public static User findUser(int userID){
        try{
            File accounts = new File("accounts.ser");
            ArrayList<User> users = listUsers(accounts, getNumCreated());

            for(User u: users) {
                if(u.getUserID() == userID) {
                    return u;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
*/

    public static User findUserWithEmail(String email){
        try{
            ArrayList<User> users = getUsers();

            for(User u: users) {
                if(u.getEmail().equals(email)) {
                    return u;
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<User> searchSellerByUser(String search, User sender) {
        ArrayList<User> users = getUsers();
        ArrayList<User> results = new ArrayList<User>();

        for(User u: users) {
            if (u instanceof Seller) {
                if ((!(sender.getBlockedUsers().contains(u) || u.getBlockedUsers().contains(sender)))
                    && (u.getEmail().indexOf(search) != -1))
                    results.add(u);
            }
        }
        return results;
    }

    public static ArrayList<User> searchCustomerByUser(String search, User sender) {
        ArrayList<User> users = getUsers();
        ArrayList<User> results = new ArrayList<User>();

        for(User u: users) {
            if (u instanceof Customer) {
                if ((!(sender.getBlockedUsers().contains(u) || u.getBlockedUsers().contains(sender)))
                        && (u.getEmail().indexOf(search) != -1))
                    results.add(u);
            }
        }
        return results;
    }

    public static ArrayList<User> getCustomersByUser(User sender) {
        ArrayList<User> users = getUsers();
        ArrayList<User> results = new ArrayList<User>();

        for(User u: users) {
            if (u instanceof Customer) {
                if (!(sender.getBlockedUsers().contains(u) || u.getBlockedUsers().contains(sender)))
                    results.add(u);
            }
        }
        return results;
    }

    public static boolean newUser(String name, String emailAddress, String password, String storeName, int userType) {
        ArrayList<User> users = User.getUsers();
        for(User u: users) {
            if(u.getEmail().equals(emailAddress)){
                System.out.println("Email Taken");
                return false;
            }
        }

        User u;
        if(userType == 1) {
            //Customer Account
            u = new Customer(name, emailAddress, password);
            System.out.println("Customer Created");
        } else if (userType == 2) {
            u = new Seller(name, emailAddress, password);
            ((Seller) u).addStore(storeName);
            System.out.println("Seller Created");
        } else {
            return false;
        }

        User.allUsers.add(u);
        User.writeUsers(User.allUsers);
        return true;
    }

    public static void addNewStore(Seller seller, String storeName) {
        seller.addStore(storeName);
        User.writeUsers(User.allUsers);
    }

    private static void writeUsers(ArrayList<User> users){
        try{
            File f = new File("accounts.ser");
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for(User u1: users) {
                oos.writeObject(u1);
            }
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getUsers() {
        if (User.allUsers != null)
            return  User.allUsers;

        ArrayList<User> users = new ArrayList<>();
        try {
            File f = new File("accounts.ser");
            FileInputStream fis = new FileInputStream(f);
            ObjectInputStream ois;

            try {
                ois = new ObjectInputStream(fis);

                while (true) {
                    User u = (User) ois.readObject();
                    users.add(u);
                }

            } catch (EOFException ex) {
            }

//            for (int i = 0; i < fileLength; i++){
//                User u = (User) ois.readObject();
//                users.add(u);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        User.allUsers = users;
        return User.allUsers;
    }

    public void block(User user) {
        this.blockedUsers.add(user);
        writeUsers(User.getUsers());
    }

    public boolean equals(Object o){
        if(o instanceof User){
            User u = (User) o;
            if(u.getEmail().equals(email) && u.getPassword().equals(password) ) {
                return true;
            }
        }
        return false;
    }

    public static void deleteUser(User u) {
        Messenger.deleteConversationsForUser(u);

        ArrayList<User> temp = User.getUsers();
        temp.remove(u);
        writeUsers(temp);
    }

}
