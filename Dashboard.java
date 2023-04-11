package pj4;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Dashboard {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the messaging platform!");
        boolean invalidinput = true;
        while(invalidinput){
            System.out.printf("Would you like to:\n1. Create an Account\n2. Sign Into an Account\n");
            try {
                int input = Integer.parseInt(scanner.nextLine());
                if(input == 1) {
                    invalidinput = createAccount(scanner);
                } else if (input == 2) {
                    //Sign In To an Account
                    invalidinput = false;
                    System.out.println("What is your email address?");
                    String email = scanner.nextLine();
                    System.out.println("What is your password?");
                    String password = scanner.nextLine();
                    User user = User.signIn(email, password);
                    if(user != null) {
                        if (Messenger.existsUnreadMessagesForUser(user))
                            System.out.println("You have NEW messages!!!");

                        boolean cont = true;
                        do {
                            System.out.println("\nWhat action would you like to take:");
                            if (user instanceof Customer) {
                                cont = customerMenu(scanner, (Customer) user);
                            } else if (user instanceof Seller) {
                                cont = sellerMenu(scanner, (Seller) user);
                            }
                        } while(cont);
                    } else if (user == null){
                        System.out.println("Incorrect user id or password");
                    }

                } else {
                    System.out.println("Invalid Input");
                    invalidinput = true;
                }
            } catch (Exception ex) {
                System.out.println("Invalid Input. Exiting...");
                System.out.println(ex.getMessage());
            }
        }
    }

    private static boolean sellerMenu(Scanner scanner, Seller seller) {
        System.out.printf("1. Send a new message\n2. View messages\n3. Block a User\n4. Export\n5. Import\n" +
                "6. Create a Store\n7. Delete Account\n8. Add Filters\n9. Exit\n");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: {
                sendNewMailSeller(scanner, seller);
                break;
            }
            case 2: {
                viewConversations(scanner, seller);
                break;
            }
            case 3: {
                blockUser(scanner, seller);
                break;
            }
            case 4: {//Export write code in method
                exportText(scanner, seller);
                break;
            }
            case 5 : {
                importText(scanner, seller);
                break;
            }
            case 6 : {
                addStore(scanner, seller);
                break;
            }
            case 7 : {
                boolean deleted = deleteAccount(scanner, seller);
                return !deleted;
            }
            case 8 : {
                addFilters(scanner, seller);
                break;
            }
            case 9: {
                return false;
                //break;
            }
            default: {
                System.out.println("Invalid input");
                break;
            }
        }
        return true;
    }

    public static boolean customerMenu(Scanner scanner, Customer c) {
        System.out.printf("1. Send a new message\n2. View conversations\n" + "3. Block a User\n" +
                "4. Export\n5. Import\n6. Delete Account\n7. Add Filters\n8. Exit\n");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: {
                sendNewMailCustomer(scanner, c);
                break;
            }
            case 2: {
                viewConversations(scanner, c);
                break;
            }
            case 3: {
                blockUser(scanner, c);
                break;
            }
            case 4: { //Export write code in method
                exportText(scanner, c);
                break;
            }
            case 5 : {
                importText(scanner, c);
                break;
            }
            case 6: {
                boolean deleted = deleteAccount(scanner, c);
                return !deleted;
                //break;
            }
            case 7 : {
                addFilters(scanner, c);
                break;
            }
            case 8: {
                return false;
                //break;
            }
            default: {
                System.out.println("Invalid input");
                break;
            }
        }
        return true;
    }

    private static void viewConversations(Scanner scanner, User user) {
        ArrayList<Conversation> convs = Messenger.getConversationsForUser(user);

        if (convs.size() == 0)
            System.out.println("There are no conversations to view");
        else {
            printConversationList(convs, user);

            while (true)
            {
                System.out.println("Which conversation would you like to view:");
                int i = scanner.nextInt();
                scanner.nextLine();
                if ((i > convs.size()) || (i <= 0)) {
                    System.out.println("Invalid Input");
                    continue;
                } else {
                    //System.out.println(convs.get(i-1).getMessageString());
                    ArrayList<Message> temp = Messenger.getMessagesForUser(convs.get(i - 1), user);
                    messageMenu(scanner, user, convs.get(i - 1), temp);
                    break;
                }
            }
        }
    }

    private static void messageMenu(Scanner scanner, User current, Conversation conversation, ArrayList<Message> messages) {
        if (messages.size() == 0)
            System.out.println("There are no messages to view");
        else {
            int count = 1;
            for (Message m : messages) {
                String temp = current.applyFilters(m.getMessage());
                System.out.println(count++ + "  [" + m.getSender().getName() + "] " + temp);
            }

            while (true)
            {
                int choice = getChosenNumber(scanner,
                        "\nActions:\n1. Edit a message\n2. Delete a message\n3. Back to menu", 3);
                switch (choice) {
                    case 1: { //Edit
                        int num = getChosenNumber(scanner, "Choose Message Number:", messages.size());
                        System.out.println("Please type your message:");
                        String message = scanner.nextLine();

                        Message temp = messages.get(num - 1);
                        if (current.equals(temp.getSender()))
                            Messenger.editMessage(conversation, temp, message.trim(), current);
                        else
                            System.out.println("You do not have permissions to edit that message.\n");
                        return;
                    }
                    case 2: { //Delete
                        int num = getChosenNumber(scanner, "Choose Message Number:", messages.size());
                        Message temp = messages.get(num - 1);
                        Messenger.deleteMessage(conversation, temp, current);
                        return;
                    }
                    default: {
                        return;
                    }
                }
            }
        }
    }

    private static void printConversationList(ArrayList<Conversation> conversations, User user) {
        int count = 1;
        System.out.printf("%3s %30s %30s %30s %3s\n", "Num", "Customer", "Seller", "Store", "New");
        for (Conversation x : conversations) {
            System.out.printf("%3d %30s %30s %30s %3s\n", count++, x.getCustomer().getName(),
                    x.getSeller().getName(), x.getStore().getStoreName(), x.hasUserRead(user) ? "N" : "Y");
        }
    }

/*
    private static void deleteConversation(Scanner scanner, User user) {
        ArrayList<Message> msgs = Messenger.getConversationsForUser(user);

        if (msgs.size() == 0)
            System.out.println("There are no messages to view");
        else {
            printConversationList(msgs);

            while (true)
            {
                System.out.println("Which message would you like to delete:");
                int i = scanner.nextInt();
                scanner.nextLine();
                if ((i > msgs.size()) || (i <= 0)) {
                    System.out.println("Invalid Input");
                    continue;
                } else {
                    Messenger.deleteMessage(msgs.get(i - 1));
                    break;
                }
            }
        }
    }
*/

/*
    private static void editConversations(Scanner scanner, User user) {
        ArrayList<Conversation> convs = Messenger.getConversationsForUser(user);

        if (convs.size() == 0)
            System.out.println("There are no conversations to view");
        else {
            printConversationList(convs);

            while (true)
            {
                System.out.println("Which conversation would you like to edit:");
                int i = scanner.nextInt();
                scanner.nextLine();
                if ((i > convs.size()) || (i <= 0)) {
                    System.out.println("Invalid Input");
                    continue;
                } else {
                    System.out.println(convs.get(i - 1).getMessageString());

                    System.out.println("Please type your message:");
                    String message = scanner.nextLine();


                    Messenger.editMessage(user, convs.get(i - 1), message, false);
                    System.out.println("Your conversation has been edited!");
                    break;
                }
            }
        }
    }
*/

    private static void sendNewMailCustomer(Scanner scanner, User user) {
        int choice;
        System.out.println("Would you like to:");
        System.out.printf("1. Select a store to message\n2. Search for a seller to message\n");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: {
                ArrayList<Store> stores = Store.getAllStoresForUser(user);
                chooseStoreToMail(scanner, user, stores, null);
                break;
            }
            case 2: {
                System.out.println("Please enter seller you are searching for:");
                String sellerSearch = scanner.nextLine();
                ArrayList<User> users = User.searchSellerByUser(sellerSearch, user);
                if (users.size() == 0)
                    System.out.println("No sellers match");
                else {
                    int count = 1;
                    for (User temp : users)
                        System.out.println(count++ + ". " + temp.getEmail());

                    int num = getChosenNumber(scanner, "Please select a seller to message:", users.size());
                    ArrayList<Store> stores = ((Seller) users.get(num - 1)).getListOfStores();
                    chooseStoreToMail(scanner, user, stores, null);
                }
                break;
            }
            default: {
                System.out.println("Invalid Input");
            }
        }
    }

    private static void chooseStoreToMail(Scanner scanner, User user, ArrayList<Store> stores, Customer receiver) {
        if (stores.size() == 0)
            System.out.println("No stores exist to message");
        else {
            int count = 1;
            for (Store st : stores)
                System.out.println(count++ + ". " + st.getStoreName());

            int num = getChosenNumber(scanner, "Please select a store:", stores.size());

            System.out.println("What message would you like to send?");
            String message = scanner.nextLine();

            if (user instanceof Customer)
                Messenger.sendNewMessage(user, stores.get(num - 1).getSeller(),message, false,
                        (Customer) user,  stores.get(num - 1));
            else
                Messenger.sendNewMessage(user, receiver, message, false,
                        receiver,  stores.get(num - 1));
            System.out.println("Your message is sent! Thank you.");
        }
    }

    private static void sendNewMailSeller (Scanner scanner, User user) {
        int choice;
        System.out.println("Would you like to:");
        System.out.printf("1. Select a customer to message\n2. Search for a customer to message\n");
        choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1: {
                ArrayList<User> users = User.getCustomersByUser(user);
                if (users.size() == 0)
                    System.out.println("No customers exist to message");
                else {
                    int count = 1;
                    for (User u : users)
                        System.out.println(count++ + ". " + u.getEmail());

                    int num = getChosenNumber(scanner, "Please select a customer to message:", users.size());

                    ArrayList<Store> stores = ((Seller) user).getListOfStores();
                    chooseStoreToMail(scanner, user, stores, (Customer) users.get(num-1));
                }
                break;
            }
            case 2: {
                System.out.println("Please enter customer you are searching for:");
                String search = scanner.nextLine();
                ArrayList<User> users = User.searchCustomerByUser(search, user);
                if (users.size() == 0)
                    System.out.println("No customers match");
                else {
                    int count = 1;
                    for (User temp : users)
                        System.out.println(count++ + ". " + temp.getEmail());

                    int num = getChosenNumber(scanner, "Please select a customer to message:", users.size());
                    ArrayList<Store> stores = ((Seller) user).getListOfStores();
                    chooseStoreToMail(scanner, user, stores, (Customer) users.get(num-1));
                }
                break;
            }
            default: {
                System.out.println("Invalid Input");
            }
        }
    }

    private static boolean createAccount(Scanner scanner) {
        int input;
        boolean invalidinput;
        //Create an Account
        invalidinput = false;
        System.out.println("What is your name?");
        String name = scanner.nextLine();
        System.out.println("What is your email address?");
        String email = scanner.nextLine();
        System.out.println("What would you like your password to be?");
        String password = scanner.nextLine();
        System.out.printf("Would you like to be:%n1. A Customer%n2. A Seller%n");
        input = Integer.parseInt(scanner.nextLine());
        if(input == 1) {
            //Customer Account
            User.newUser(name, email, password, "",1);
        } else if (input == 2) {
            //Seller Account
            System.out.println("What would you like your first store to be named?");
            String storeName = scanner.nextLine();
            while(storeName.trim().equals("")){
                System.out.println("Please enter a valid store name.");
            }
            try {
                User.newUser(name, email, password, storeName,2);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            invalidinput = true;
        }
        return invalidinput;
    }

    private static void blockUser (Scanner scanner, User user) {
        ArrayList<User> users;

        System.out.println("Please search user you want to block:");
        String search = scanner.nextLine();
        if (user instanceof Customer)
            users = User.searchSellerByUser(search, user);
        else
            users = User.searchCustomerByUser(search, user);

        if (users.size() == 0)
            System.out.println("No users match");
        else {
            int count = 1;
            for (User temp : users)
                System.out.println(count++ + ". " + temp.getEmail());

            int num = getChosenNumber(scanner, "Please select a user to block:", users.size());

            user.block(users.get(num-1));
            System.out.println("The block was set!");
        }
    }

    private static int getChosenNumber(Scanner scanner, String prompt, int max) {
        while (true)
        {
            System.out.println(prompt);
            int i = scanner.nextInt();
            scanner.nextLine();

            if ((i > max) || (i <= 0)) {
                System.out.println("Invalid Input");
                continue;
            } else {
                return i;
            }
        }
    }

    private static boolean deleteAccount (Scanner scanner, User user) {
        while (true) {
            System.out.println("Are you sure you want to delete your account?");
            System.out.println("1. Yes\n2. No");

            int i = scanner.nextInt();
            scanner.nextLine();

            if (i == 1) {
                User.deleteUser(user);
                return true;
            } else if (i == 2) {
                return false;
            } else {
                System.out.println("Invalid Input");
            }
        }
    }

    private static void addStore(Scanner scanner, Seller seller) {
        System.out.println("What would you like your store to be named?");
        String storeName = scanner.nextLine();
        while (storeName.trim().equals("")) {
            System.out.println("Please enter a valid store name.");
        }
        try {
            User.addNewStore(seller, storeName.trim());
            System.out.println("Store added.");
            return;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return;
        }
    }

    private static void importText(Scanner scanner, User user) {
        ArrayList<Conversation> convs = Messenger.getConversationsForUser(user);

        if (convs.size() == 0)
            System.out.println("There are no conversations to import to.");
        else {
            printConversationList(convs, user);

            int i = getChosenNumber(scanner, "Which conversation would you like to import to:", convs.size());
            System.out.println("Please input the file path of the text file to import:");
            String input = scanner.nextLine();
            String message = readFile(input);
            if (message == null)
                System.out.println("File could not be read");
            else {
                Messenger.addMessageToConversation(convs.get(i - 1), user, message, false);
                System.out.println("Your message has been imported!");
            }
        }
    }

    private static String readFile(String fileName) {
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(fileName));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = bfr.readLine()) != null) {
                if (!sb.toString().isEmpty())
                    sb.append("\n");
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private static void exportText(Scanner scanner, User user) {
        ArrayList<Conversation> convs = Messenger.getConversationsForUser(user);

        if (convs.size() == 0)
            System.out.println("There are no conversations to import to.");
        else {
            printConversationList(convs, user);

            System.out.println("Which conversation would you like to export:");
            String convNums = scanner.nextLine();
            System.out.println("Please input a file path to write to ending in .csv");
            String input = scanner.nextLine();

            //Write your export code here

        }
    }

    private static void addFilters(Scanner scanner, User user)
    {
        ArrayList<String> filters = user.getFilters();

        if (filters.size() == 0)
            System.out.println("There are no filters.");
        else {
            int count = 1;
            System.out.printf("%3s %15s %15s\n", "Num", "Original", "Replacement");
            for (int i = 0; i < filters.size(); i += 2) {
                System.out.printf("%3d %15s %15s\n", count++, filters.get(i), filters.get(i + 1));
            }
        }

        System.out.println("1. Add a new filter\n2. Go back");
        int num = getChosenNumber(scanner, "Pick an option:", 2);

        if (num == 1)
        {
            String original = "";
            while (original.equals("")) {
                System.out.println("Please input what you want to filter:");
                original = scanner.nextLine();
            }
            System.out.println("Please enter the replacement string or hit enter to choose default (****):");
            String replacement = scanner.nextLine();

            if (replacement.equals(""))
                replacement = "****";
            user.addFilter(original, replacement);
        }
    }

}
