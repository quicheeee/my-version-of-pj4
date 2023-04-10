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
                    "Would you like to:\n1. Create an Account\n2. Sign Into an Account\n" + System.lineSeparator() +
                    "What is your name?" + System.lineSeparator() +
                    "What is your email address?" + System.lineSeparator() +
                    "What would you like your password to be?" + System.lineSeparator() +
                    "Would you like to be:%n1. A Customer%n2. A Seller%n" + System.lineSeparator() +
                    "What would you like your first store to be named?" + System.lineSeparator() +
                    "Seller Created" + System.lineSeparator();
                    /*+
                    String.format(MAZE_VALUES,1) + System.lineSeparator() +
                    TREASURE_LOCATION + System.lineSeparator() +
                    READY + System.lineSeparator() +
                    String.format(CURRENT_POSITION,0,0) + System.lineSeparator() +
                    MOVE_SELECT + System.lineSeparator() +
                    "1. " + MOVES[0] + System.lineSeparator() +
                    "2. " + MOVES[1] + System.lineSeparator() +
                    "3. " + MOVES[2] + System.lineSeparator() +
                    "4. " + MOVES[3] + System.lineSeparator() +
                    String.format(CURRENT_POSITION,1,0) + System.lineSeparator() +
                    MOVE_SELECT + System.lineSeparator() +
                    "1. " + MOVES[0] + System.lineSeparator() +
                    "2. " + MOVES[1] + System.lineSeparator() +
                    "3. " + MOVES[2] + System.lineSeparator() +
                    "4. " + MOVES[3] + System.lineSeparator() +
                    TREASURE_FOUND + System.lineSeparator() +
                    FAREWELL + System.lineSeparator();

                     */

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
/*
        @Test(timeout = 1000)
        public void testExpectedTwo() {
            // Set the input
            String input = "1,1" + System.lineSeparator() +
                    "true" + System.lineSeparator() +
                    "0,0" + System.lineSeparator() +
                    "No" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = WELCOME + System.lineSeparator() +
                    INITIALIZE_MAZE + System.lineSeparator() +
                    MAZE_DIMENSIONS + System.lineSeparator() +
                    String.format(MAZE_VALUES,0) + System.lineSeparator() +
                    TREASURE_LOCATION + System.lineSeparator() +
                    READY + System.lineSeparator() +
                    FAREWELL + System.lineSeparator();

            // Runs the program with the input values
            receiveInput(input);
            MazeNavigator.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n","\n");
            output = output.replaceAll("\r\n","\n");
            assertEquals("Make sure players can exit before starting the game!",
                    expected.trim(), output.trim());
        }

        @Test(timeout = 1000)
        public void testExpectedThree() {
            // Set the input
            String input = "2,2" + System.lineSeparator() +
                    "true,false" + System.lineSeparator() +
                    "true,false" + System.lineSeparator() +
                    "1,0" + System.lineSeparator() +
                    "Yes" + System.lineSeparator() +
                    "Right" + System.lineSeparator() +
                    "Left" + System.lineSeparator() +
                    "Down" + System.lineSeparator();

            // Pair the input with the expected result
            String expected = WELCOME + System.lineSeparator() +
                    INITIALIZE_MAZE + System.lineSeparator() +
                    MAZE_DIMENSIONS + System.lineSeparator() +
                    String.format(MAZE_VALUES,0) + System.lineSeparator() +
                    String.format(MAZE_VALUES,1) + System.lineSeparator() +
                    TREASURE_LOCATION + System.lineSeparator() +
                    READY + System.lineSeparator() +
                    String.format(CURRENT_POSITION,0,0) + System.lineSeparator() +
                    MOVE_SELECT + System.lineSeparator() +
                    "1. " + MOVES[0] + System.lineSeparator() +
                    "2. " + MOVES[1] + System.lineSeparator() +
                    "3. " + MOVES[2] + System.lineSeparator() +
                    "4. " + MOVES[3] + System.lineSeparator() +
                    INVALID_MOVE + System.lineSeparator() +
                    String.format(CURRENT_POSITION,0,0) + System.lineSeparator() +
                    MOVE_SELECT + System.lineSeparator() +
                    "1. " + MOVES[0] + System.lineSeparator() +
                    "2. " + MOVES[1] + System.lineSeparator() +
                    "3. " + MOVES[2] + System.lineSeparator() +
                    "4. " + MOVES[3] + System.lineSeparator() +
                    INVALID_MOVE + System.lineSeparator() +
                    String.format(CURRENT_POSITION,0,0) + System.lineSeparator() +
                    MOVE_SELECT + System.lineSeparator() +
                    "1. " + MOVES[0] + System.lineSeparator() +
                    "2. " + MOVES[1] + System.lineSeparator() +
                    "3. " + MOVES[2] + System.lineSeparator() +
                    "4. " + MOVES[3] + System.lineSeparator() +
                    TREASURE_FOUND + System.lineSeparator() +
                    FAREWELL + System.lineSeparator();

            // Runs the program with the input values
            receiveInput(input);
            MazeNavigator.main(new String[0]);

            // Retrieves the output from the program
            String output = getOutput();

            // Trims the output and verifies it is correct.
            expected = expected.replaceAll("\r\n","\n");
            output = output.replaceAll("\r\n","\n");
            assertEquals("Make sure players can navigate the maze successfully, as well as handle invalid moves!",
                    expected.trim(), output.trim());
        }
    }

 */
    }

}


