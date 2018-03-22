package assignment4;
/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Shiv Lalapet
 * EID: SL39596
 * Unique No: 15495
 * <Student2 Name>
 * <Student2 EID>
 * Unique No: 15495
 * Spring 2018
 */

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

        //add 100 Algae and 25 Craig critters when world starts
        for(int i=0; i < 100; i++) {
            try {
                Critter.makeCritter("Algae");
            }
            catch (InvalidCritterException e) {
                System.out.println("Error - Algae initialization");
            }
        }
        for(int j=0; j < 25; j++){
            try{
                Critter.makeCritter("Craig");
            }
            catch (InvalidCritterException e){
                System.out.println("Error - Craig initialization");
            }
        }
        runController(kb);
        System.out.flush();
    }

    /**
     * This is the method that runs the controller until the user quits the program.
     */
    public static void runController(Scanner kb){
        while(true){
            System.out.println("critters> "); //prompt user
            String[] input = kb.nextLine().split(" "); //net line of input in string array
            if(input.length > 0){

                //quit command
                if(input[0].equals("quit")){
                    if(input.length == 1)
                        break;
                    else
                        printInvalidCommand(input);
                }

                //show command
                else if(input[0].equals("show")){
                    if(input.length == 1)
                        Critter.displayWorld();
                    else
                        printInvalidCommand(input);
                }

                //step command
                else if(input[0].equals("step")){
                    if(input.length == 1)
                        Critter.worldTimeStep(); //no number, do once
                    else {
                        int count = 0;
                        try{
                            count = Integer.parseInt(input[1]); //gets the int
                        }
                        catch(NumberFormatException e){
                            printError(input);
                            continue;
                        }
                        for (int i=0; i < count; i++) {
                            Critter.worldTimeStep();
                        }
                    }
                }

                //seed command
                else if(input[0].equals("seed")){
                    if(input.length != 2)
                        printInvalidCommand(input);
                    else {
                        int number = 0;
                        try{
                            number = Integer.parseInt(input[1]);
                        }
                        catch(NumberFormatException e){
                            printError(input);
                            continue;
                        }
                        Critter.setSeed(number);
                    }
                }

                //make command
                else if(input[0].equals("make")){
                   if(input.length > 1 && input.length < 4){
                        int count = 1;
                        String name = input[1];
                        if(input.length == 3) {
                            try {
                                count = Integer.parseInt(input[2]); //should get the next int
                            } catch (NumberFormatException e) {
                                printError(input);
                                continue;
                            }
                        }
                        for(int i=0; i < count; i++) {
                            try {
                                Critter.makeCritter(name);
                            }
                            catch (InvalidCritterException e) {
                                printError(input);
                                break;
                            }
                        }
                    }
                    else
                        printInvalidCommand(input);
                }

                //stats command
                else if(input[0].equals("stats")){
                    if(input.length == 2) {
                        String class_name = input[1];
                        java.util.List<Critter> instance_List = null;
                        try {
                            instance_List = Critter.getInstances(class_name);
                        }
                        catch (InvalidCritterException e) {
                            printError(input);
                            break;
                        }
                        Critter.runStats(instance_List);
                    }
                    else
                        printInvalidCommand(input);
                }

                //invalid command
                else
                    printInvalidCommand(input);
            }
            //no command given
            else
                printInvalidCommand(input);
        }
    }

    /**
     * This error method is called when the user inputs an invalid command
     * @param input ArrayList<String> user input
     */
    public static void printInvalidCommand(String[] input){
        System.out.print("invalid command: ");
        for(int i=0; i < input.length; i++){
            System.out.print(input[i] + " ");
        }
        System.out.println();
    }

    /**
     * This error method is called when the user inputs an invalid number or name
     * @param input ArrayList<String> user input
     */
    public static void printError(String[] input){
        System.out.print("error processing: ");
        for(int i=0; i < input.length; i++){
            System.out.print(input[i] + " ");
        }
        System.out.println();

    }
}