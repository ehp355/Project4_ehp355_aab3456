/* CRITTERS CritterWorld.java
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

package assignment4;

import java.util.*;
import java.util.List;
public class CritterWorld {
	// TODO: To make it general, set maxCrittersInSpot to Params.world_height*Params.world_width
	static int maxCrittersInSpot = 100;
	// board is a 2d ArrayList where the 1st dimension is a position on the board and the 2nd dimension is a list of 
	// critters in that position
	private static Critter[][] board = new Critter[Params.world_height*Params.world_width][maxCrittersInSpot];
	
	
	// TODO: Find a way to make a dynamic board array

	// List of all Critter instances with no particular ordering
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	
	// List of all babies (not used)
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	
	// List of all Critters who have called walk() or run() in a time step
	private static List<Critter> haveMoved = new java.util.ArrayList<Critter>();
	

	public static void addCritter(Critter c, int x, int y){
		population.add(c);
		addCritterToBoard(c, x, y);
	}
	public static List<Critter> getCritterPopulation(){
		return population;
	}
	public static List<Critter> getCritterBabies(){
		return babies;
	}
	
	/**
	 * method that adds critter to board. Uses a for loop to ensure it does not overwrite 
	 * any critters that may already in that position
	 * @param c
	 */
	public static void addCritterToBoard(Critter c,int x ,int y)
	{	
		int boardposition = cartesianToBoard(x,y);
		for(int i = 0; i < maxCrittersInSpot; i++){
			if(board[boardposition][i]==null){
				board[boardposition][i] = c;
				break;
			}
		}
		
	}
	
	/**
	 * This method adds a Critter to the list haveMoved. It is
	 * called whenever a Critter instance calls walk() or run()
	 * in a time step.
	 * 
	 * @param critter Critter instance to be added to haveMoved
	 */
	public static void addCritterToMoved(Critter critter) 
	{
		haveMoved.add(critter);
	}
	
	/**
	 * This method checks if a given Critter has moved in a time step.
	 * We use a for-each loop rather than the ArrayList contains() 
	 * method, as that would require overriding the default equals() method
	 * for Critter (which is public).
	 * 
	 * @param critter the instance that is to be checked
	 * @return true if critter is in haveMoved and false otherwise
	 */
	public static boolean checkIfMoved(Critter critter)
	{
		/* TODO: Determine whether this works with subclasses of Critter,
		 * as casting might have an effect on the reference.
		 */
		for (Critter c : haveMoved) {
			if (c == critter) {		// Intentionally comparing references
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * This method clears the list haveMoved. It is to be called at the 
	 * end of a time step.
	 */
	public static void clearMoved()
	{
		haveMoved.clear();
	}
	
	/**
	 * method that returns where in the board array the critter should 
	 * be placed or removed from based on x y coordinate and dimensions of
	 * the board
	 * @param x
	 * @param y
	 * @return
	 */
	public static int cartesianToBoard(int x, int y){
		int bpos;
		bpos = y * Params.world_width;
		bpos = bpos + x;
		return bpos;
	}
	
	/**
	 * method to delete critter in 2d array. Used mostly in walk and run
	 * to delete old self in 2d array and then placed back in 2d array 
	 * after new x and y have been created
	 * @param c
	 * @param x
	 * @param y
	 */
	public static void remove(Critter c, int x, int y){
		int bPos = cartesianToBoard(x,y);
		for(int i = 0; i < maxCrittersInSpot; i++){
			if(c == board[bPos][i]){
				board[bPos][i] = null;
				break;
			}
		}
	}
	
	/**
	 * Checks for multiple critters in the same spot and call encounter
	 * 
	 * @param c
	 * @param x
	 * @param y
	 */
	public static void checkSharePosition(Critter c, int x, int y){
		int bPos = cartesianToBoard(x,y);
		for(int i = 0; i < maxCrittersInSpot; i++){
			if(board[bPos][i]!=null && board[bPos][i]!=c){
				Critter.encounter(c,board[bPos][i]);
			}
		}
	}
	
	/**
	 * method to draw board, including boarders and critters
	 */
	public static void displayWorld(){
		
		int board_position = 0;
		System.out.print("+");
		for(int i = 0; i < Params.world_width;i++){
			System.out.print("-");
		}
		System.out.print("+");
		
		System.out.println();
		
		for(int i = 0; i < Params.world_height; i++){
			System.out.print("|");
			for(int j = 0; j< Params.world_width; j++){
				if(board[board_position][0]!=null){
					System.out.print(board[board_position][0]);
				}else{
					System.out.print(" ");
				}
				board_position++;
			}
			System.out.println("|");
		}
		
		System.out.print("+");
		for(int i = 0; i < Params.world_width;i++){
			System.out.print("-");
		}
		System.out.println("+");
	}
	
	public static void stage1AddAlgaeCraig(){
		//add 100 Algae to board
		for(int i = 0; i < 5; i++){
			try{
				Critter.makeCritter("Algae");
			}catch(InvalidCritterException e){
				
			}
		}
		
		for(int i = 0; i < 3; i++){
			try{
				Critter.makeCritter("Craig");
			}catch(InvalidCritterException e){
				
			}
		}
		
	}

}
