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
import java.util.Scanner;
import java.io.*;

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
        
        //CritterWorld.stage1AddAlgaeCraig();
        
        /* TODO: Resolve issue where 3 Craigs and 5 algae 
         * don't show up every time
         */
        CritterWorld.stage1AddAlgaeCraig();
        
        // Critter.displayWorld();
        String userInput;
        String[] user;
        int userStepNum = 0;
        boolean userFlag = true;
        while(userFlag){
        	System.out.print("critters> ");
        	userInput = kb.nextLine();
        	user = userInput.split(" ");
        	if(user.length>1){
        		userStepNum=Integer.parseInt(user[1]);
        	}
        	userInput = user[0];
        	
        	if(userInput.equals("quit")){
        		// System.exit(0);
        		/* According to piazza, we shouldn't use System.exit()
        		 * to exit the program.
        		 */
        		break;
        	}else if(userInput.equals("show")){
        		Critter.displayWorld();
        	}else if(userInput.equals("step")){
        		if(user.length>1){
        			for(int i = 0; i < userStepNum; i++){
        				Critter.worldTimeStep();
        			}
        		}else{
        			Critter.worldTimeStep();
        		}
        	}else if(userInput.equals("seed")){
        		if(user.length>1){
        			Critter.setSeed(userStepNum);
        		}
        	}else if(userInput.equals("")){
        		
        	}else{
        		System.out.println("Incorrect input");
        	}
        	
        }

        //System.out.println("GLHF");

        /* Write your code above */
        System.out.flush();

    }
}
