package pj4;

import org.junit.Test;
import org.junit.After;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;
import org.junit.rules.Timeout;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.Test;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Summer 2022</p>
 *
 * @author Meha Kavoori
 * @version 4/10/2023
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Summer 2022</p>
     *
     * @author Meha Kavoori
     * @version 4/10/2023
     */


    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        @Test(timeout = 1000)
        public void testExpectedOne() {
            // Set the input
            String input = "1" + System.lineSeparator() +
                    "Bob " + System.lineSeparator() +
                    "bob@gmail.com" + System.lineSeparator() +
                    "bob123" + System.lineSeparator() +
                    "2" + System.lineSeparator() + "Bob's Burgers" +
                    System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to the messaging platform!" + System.lineSeparator() +
                    "Would you like to:\n1. Create an Account\n2. Sign Into an Account" + System.lineSeparator() +
                    "What is your name?" + System.lineSeparator() +
                    "What is your email address?" + System.lineSeparator() +
                    "What would you like your password to be?" + System.lineSeparator() +
                    "Would you like to be:\n1. A Customer\n2. A Seller" + System.lineSeparator() +
                    "What would you like your first store to be named?" + System.lineSeparator() +
                    "Seller Created" + System.lineSeparator();


            // Runs the program with the input values
            receiveInput(input);
            Dashboard.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("Make sure your store name is valid and all inputs are valid.",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testExpectedTwo() {
            // Set the input
            String input = "1" + System.lineSeparator() +
                    "Karen " + System.lineSeparator() +
                    "karen@gmail.com" + System.lineSeparator() +
                    "karen123" + System.lineSeparator() +
                    "1" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to the messaging platform!" + System.lineSeparator() +
                    "Would you like to:\n1. Create an Account\n2. Sign Into an Account" + System.lineSeparator() +
                    "What is your name?" + System.lineSeparator() +
                    "What is your email address?" + System.lineSeparator() +
                    "What would you like your password to be?" + System.lineSeparator() +
                    "Would you like to be:\n1. A Customer\n2. A Seller" + System.lineSeparator() +
                    "Customer Created" + System.lineSeparator();

            // Runs the program with the input values
            receiveInput(input);
            Dashboard.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("Make sure the email is not taken and  all inputs are valid.",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testExpectedThree() {
            // Set the input
            String input = "1" + System.lineSeparator() +
                    "Krusty " + System.lineSeparator() +
                    "krusty@gmail.com" + System.lineSeparator() +
                    "krusty123" + System.lineSeparator() +
                    "2" + System.lineSeparator() + "The Krusty Krab" +
                    System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to the messaging platform!" + System.lineSeparator() +
                    "Would you like to:\n1. Create an Account\n2. Sign Into an Account" + System.lineSeparator() +
                    "What is your name?" + System.lineSeparator() +
                    "What is your email address?" + System.lineSeparator() +
                    "What would you like your password to be?" + System.lineSeparator() +
                    "Would you like to be:\n1. A Customer\n2. A Seller" + System.lineSeparator() +
                    "What would you like your first store to be named?" + System.lineSeparator() +
                    "Seller Created" + System.lineSeparator();


            // Runs the program with the input values
            receiveInput(input);
            Dashboard.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("Make sure your store name is valid and all inputs are valid.",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testExpectedFour() {
            // Set the input
            String input = "1" + System.lineSeparator() +
                    "Spongebob " + System.lineSeparator() +
                    "spongebob@gmail.com" + System.lineSeparator() +
                    "spongebob123" + System.lineSeparator() +
                    "1" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = "Welcome to the messaging platform!" + System.lineSeparator() +
                    "Would you like to:\n1. Create an Account\n2. Sign Into an Account" + System.lineSeparator() +
                    "What is your name?" + System.lineSeparator() +
                    "What is your email address?" + System.lineSeparator() +
                    "What would you like your password to be?" + System.lineSeparator() +
                    "Would you like to be:\n1. A Customer\n2. A Seller" + System.lineSeparator() +
                    "Customer Created" + System.lineSeparator();

            // Runs the program with the input values
            receiveInput(input);
            Dashboard.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("Make sure the email is not taken and  all inputs are valid.",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testExpectedFive() {
            // Set the input
            String input = "2" + System.lineSeparator() +
                    "spongebob@gmail.com" + System.lineSeparator() +
                    "spongebob123" + System.lineSeparator() +
                    "1" + System.lineSeparator() +
                    "1" + System.lineSeparator() + "2" +
                    System.lineSeparator() + "Hello Mr. Krabs!";

            // Pair the input with the expected result
            String expected = "Welcome to the messaging platform!" + System.lineSeparator() +
                    "Would you like to:\n1. Create an Account\n2. Sign Into an Account" + System.lineSeparator() +
                    "What is your email address?" + System.lineSeparator() +
                    "What is your password?" + System.lineSeparator() +
                    "What action would you like to take:\n1. Send a new message\n2. View messages\n3. Edit message\n" +
                    "4. Delete message\n5. Block a User\n6. View Store Statistics\n7. Delete Account\n8. Exit" + System.lineSeparator() +
                    "Would you like to:\n1. Select a store to message\n2. Search for a seller to message" + System.lineSeparator() +
                    "Please select a store to message:" + System.lineSeparator() +
                    "What message would you like to send?" + System.lineSeparator() + "Your message is sent! Thank you." +
                    System.lineSeparator();


            // Runs the program with the input values
            receiveInput(input);
            Dashboard.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("Make sure that the correct store was selected",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testCaseSix() {
            String input = "2" + System.lineSeparator() +
                    "krusty@gmail.com" + System.lineSeparator() +
                    "krusty123" + System.lineSeparator() +
                    "2" + System.lineSeparator() +
                    "1" + System.lineSeparator() + "1" +
                    System.lineSeparator() + "2" + System.lineSeparator()
                    + "spongebob" + System.lineSeparator() + "1" +
                    System.lineSeparator() + "Greetings Spongebob!"
                    + System.lineSeparator() + "3" + System.lineSeparator()
                    + "1" + System.lineSeparator() + "Greetings Spongebob! ARGHHH!!!"
                    + System.lineSeparator() + "9";

            // Pair the input with the expected result
            String expected = "Welcome to the messaging platform!" + System.lineSeparator() +
                    "Would you like to:\n1. Create an Account\n2. Sign Into an Account" + System.lineSeparator() +
                    "What is your email address?" + System.lineSeparator() +
                    "What is your password?" + System.lineSeparator() + "You have NEW messages!!!" + System.lineSeparator() +
                    "What action would you like to take:\n1. Send a new message\n2. View messages\n3. Edit message\n4. Delete message\n" +
                    "5. Block a User\n6. View Store Statistics\n7. Create a Store\n8. Delete Account\n9. Exit" + System.lineSeparator() +
                    "Which message would you like to view:" + System.lineSeparator() +
                    "What action would you like to take:\n1. Send a new message\n2. View messages\n3. Edit message\n4. Delete message\n" +
                    "5. Block a User\n6. View Store Statistics\n7. Create a Store\n8. Delete Account\n9. Exit" + System.lineSeparator() +
                    "Would you like to:\n1. Select a customer to message\n2. Search for a customer to message" + System.lineSeparator() +
                    "Please enter customer you are searching for:" + System.lineSeparator() + "Please select a customer to message:" +
                    System.lineSeparator() + "What message would you like to send?" + System.lineSeparator() +
                    "Your message is sent! Thank you." + System.lineSeparator() + "What action would you like to take:\n" +
                    "1. Send a new message\n2. View messages\n3. Edit message\n4. Delete message\n" +
                    "5. Block a User\n6. View Store Statistics\n7. Create a Store\n8. Delete Account\n9. Exit" + System.lineSeparator() +
                    "Which message would you like to edit:" + System.lineSeparator() + "Please type your message:" + System.lineSeparator()
                    + "Your message has been edited!" + System.lineSeparator() + "What action would you like to take:\n" +
                    "1. Send a new message\n2. View messages\n3. Edit message\n4. Delete message\n" +
                    "5. Block a User\n6. View Store Statistics\n7. Create a Store\n8. Delete Account\n9. Exit" + System.lineSeparator();


            // Runs the program with the input values
            receiveInput(input);
            Dashboard.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n", "\n");
            output = output.replaceAll("\r\n", "\n");
            assertEquals("Make sure that the correct store was selected",
                    expected.trim(), output.trim());
        }
    }


}
