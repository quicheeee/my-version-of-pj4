# PJ04
CS180 project 4 repo

1. How to compile and run the Code.

	To run the code first you need to just download the files, open in intelliJ and press run from the Dashboard.java file.

2. Who submitted what
	
	Meha Kavoori - Submitted Report on Brightspace. Student 2 - Submitted Vocareum workspace.

3. Class Descriptions
	
  User Class
  
    This class is the parent class for all other types of users. The class implements the Serializable interface.
    The class has methods which allow the user to set and get the email address, name and 
    password. The methods also can check other blocked users, as well as write user data to a text file. The User class has been tested through integration
    with its child classes Customer and Seller.
    
    
  Customer Class
  
    The customer class represents a customer user and extends the User class. The customer class extends all of the User Class' methods and has additional 
    ones like a can message method which checks if the Customer can message another user. The customer has a private field store List which is a list of
    sellers that the customer can add to. This class has been tested through integration with the parent user class' methods.
    
  Seller Class
  
    The seller class represents a seller user and extends the User class. The seller class extends the customer class and has additional methods like the 
    Customer class. The Seller alo has a private field which is a list of Customers that it may add to and edit. The seller class also has been tested
    through integration with the extended methods in the User class.
    
  Dashboard Class
  
  	This class acts as the interactable graphical user interface for the user. The class houses the main method which excutes all of the called methods within the
	class. In one way or another, the Dashboard class interacts with all of the other classes and methods in some way. The dashboard class has been
	tested through integration with all other methods and classes to ensure correct functionality. These tests include multiple test cases. 
  
  Messenger Class
  
  	This class holds some of the methods to manipulate how and which messages are sent between users when it comes to manipulating files. It has methods such as writeMessages(), findRelatedMessages() and deleteMessagesForUser().
	These methods each deal with reading from a file because the class’ private variable messages are edited when the class is called and messages are written.message meant to edit existing messages. The Method existsUnreadMessagesForUser() checks if the 
	user has any unread message for example. The messenger class has been tested through test cases where messages were exchanged between users. The messenger class also implements the serializable class.
  
  Messenger2 Class
  
  	The Messenger2 class is separate from the Messenger Class and works directly with the Seller Class specifically. The constructor takes in the 
	private fields customer, seller and store. The class also has ArrayList objects which represent the individual messages sent between the customer
	and the seller. The class' methods include getters and setters for the private fields as well as additional methods such as editMessage, 
	custDeleteMessage and sellDeleteMessage. So this class more or less acts as a message editor for users. The class has been tested to see if it works together with the messenger class and it does allow messages to be edited and manipulate when prompted to. The Messenger2 class also implements
	
  Message Class
  	
	This class represents individual messages which are sent between users. The message class has private fields sender, receiver, messageOwner,
	message, createDate, and disappearing. The message class implements Serializable as well as Comparable. The message class has been tested through 
	test cases where messages were exchanged between users. Within this messenger class we also implanted a method which will convert messages between users into a .csv file so that they can be read in a spreadsheet format.
  
  Store Class
  
  	This class represents the Store object. Its constructor takes the parameters of the store name and the store's seller. it has methods that get
	how many customers have been in contact with it as well as void methods which print out the sorted values desired by the user. The store class
	implements Serializable.
  
  User Not Found exception
  
  	This exception is thrown when a searched user is not found. It has been tested in cases where the searched user name does not exist.
	
  Access Denied Exception
  
  	This exception is thrown when an attempted sign in lacks the correct password or email. It has been tested in cases where access cannot
	be granted due to incorrect password, incorrect email, or when both are incorrect.

  Test Cases
  The first test case creates a seller (Seller 1). 
  The second test case creates a customer (Customer 1). 
  The third test case creates a second seller (Seller 2). 
  The fourth test case creates a second customer (Customer 2). 
  The fifth test case has Seller 1 login, send a message ("hi") to Customer 2 via the selecting a customer option, and then exiting.
  The sixth test case has Customer 2 login, 




