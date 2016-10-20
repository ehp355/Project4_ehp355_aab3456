/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Aaron Babber
 * aab3456
 * 16480
 * Enrique Perez-Osborne
 * ehp355
 * 16465
 * Slip days used: <0>
 * Fall 2016
 */

package assignment4; // cannot be in default package
import java.util.List;
import java.util.Scanner;
import java.io.*;
import java.lang.reflect.Method;

/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name,
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
        if (args.length != 0) {
            try {
                inputFile = args[0];
                kb = new Scanner(new File(inputFile));
            } catch (FileNotFoundException e) {
                System.out.println("USAGE: java Main OR java Main <input file> <test output>");
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("USAGE: java Main OR java Main <input file>  <test output>");
            }
            if (args.length >= 2) {
                if (args[1].equals("test")) { // if the word "test" is the second argument to java
                    // Create a stream to hold the output
                    testOutputString = new ByteArrayOutputStream();
                    PrintStream ps = new PrintStream(testOutputString);
                    // Save the old System.out.
                    old = System.out;
                    // Tell Java to use the special stream; all console output will be redirected here from now
                    System.setOut(ps);
                }
            }
        } else { // if no arguments to main
            kb = new Scanner(System.in); // use keyboard and console
        }

        /* Do not alter the code above for your submission. */
        /* Write your code below. */
        
        // The world should start empty
        
        String fullInput = null;
        String command;
        String[] user;
        int countNum = 0;
        int seedNum = 0;
        boolean invalidCommand = false;
        boolean exceptionLikeCommand = false;
        
        // DONE: Catch any exception in while loop body
        while(true){
        	
        	try {
	        	System.out.print("critters> ");
	        	fullInput = kb.nextLine();
	        	invalidCommand = false;
	        	exceptionLikeCommand = false;
	        	
	        	/* Divides the input into parts and stores the results in an array.
	    	     * The regex "\\s+" splits the input String using any whitespace 
	    	     * character (the \\s part) as a delimiter. The '+' character is a 
	    	     * quantifier for "one or more times".
	    	     */
	        	user = fullInput.split("\\s+");
	        	command = user[0];
	        	
	        	if (command.equals("quit")) {
	        		if (user.length == 1) {
		        		// System.exit(0);
		        		/* According to piazza, we shouldn't use System.exit()
		        		 * to exit the program.
		        		 */
		        		break;
	        		}
	        		else {
	        			exceptionLikeCommand = true;
	        		}
	        	}
	        	
	        	else if (command.equals("show")) {
	        		if (user.length == 1) {
	        			Critter.displayWorld();
	        		}
	        		else {
	        			exceptionLikeCommand = true;
	        		}
	        	}
	        	
	        	else if (command.equals("step")) {
	        		if (user.length == 1) {
	        			Critter.worldTimeStep();
	        		}
	        		else if (user.length == 2) {
	        			countNum = Integer.parseInt(user[1]);
	        			for (int i = 0; i < countNum; i++) {
	        				Critter.worldTimeStep();
	        			}	
	        		}
	        		else {
	        			exceptionLikeCommand = true;
	        		}
	        	}
	        	
	        	// TODO: Ask how to handle missing command arguments
	        	else if (command.equals("seed")) {
	        		if (user.length == 2) {
	        			seedNum = Integer.parseInt(user[1]);
	        			Critter.setSeed(seedNum);
	        		}
	        		else {
	        			exceptionLikeCommand = true;
	        		}
	        	}
	        	
	        	else if (command.equals("make")) {
	        		if (user.length == 2) {
	        			String className = user[1];
	        			Critter.makeCritter(className);
	        		}
	        		else if (user.length == 3) {
	        			String className = user[1];
	        			countNum = Integer.parseInt(user[2]);
	        			for (int i = 0; i < countNum; i++) {
	        				Critter.makeCritter(className);
	        			}
	        		}
	        		else {
	        			exceptionLikeCommand = true;
	        		}
	        	}
	        	
	        	/* DONE: If subclass doesn't implement runStats, super's runStats 
	        	 * gets invoked
	        	 */
	        	else if (command.equals("stats")) {
	        		if (user.length == 2) {
	        			Class<?> critterClass = null;
	        			String className = user[1];		// Must be concrete (test in getInstances())
	        			String fullName = myPackage + "." + className;
	        			
	        			critterClass = Class.forName(fullName);		// Step 1
	        			List<Critter> critInstances = Critter.getInstances(className);	// Step 2
	        			Class<?>[] types = {List.class};	// Step 3 (?)
	        			Method runStatsMethod = critterClass.getMethod("runStats", types);	// Step 4
	        			runStatsMethod.invoke(null, critInstances);		// Step 5
	        		}
	        		else {
	        			exceptionLikeCommand = true;
	        		}	
	        	}
	        	
	        	/* The else statement handles invalid commands and also the 
	        	 * case when the user enters nothing. Something illegal is 
	        	 * either "exception like" or "invalid", but not both.
	        	 */
	        	else{
	        		invalidCommand = true;
	        	}
	        	
	        	if (invalidCommand) {
	        		System.out.println("invalid command: " + fullInput);
	        	}
	        	else if (exceptionLikeCommand) {
	        		System.out.println("error processing: " + fullInput);
	        	}
        	}
        	
        	catch (Exception e) {
        		System.out.println("error processing: " + fullInput);
        	}
        	
        }

        //System.out.println("GLHF");

        /* Write your code above */
        System.out.flush();

    }
}
