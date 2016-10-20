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
	
	// static int maxCrittersInSpot = 100;

	// board is a 2d ArrayList where the 1st dimension is a position on the board 
	// and the 2nd dimension is a list of critters in that position
	// private static Critter[][] board = new Critter[Params.world_height*Params.world_width][maxCrittersInSpot];
	
	// DONE: Make a dynamic board array
	public static ArrayList<ArrayList<Critter>> boardList = new ArrayList<ArrayList<Critter>>();
	
	// List of all Critter instances with no particular ordering
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	
	// List of all babies (NOT USED)
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	
	// List of all Critters who have called walk() or run() in a time step
	private static List<Critter> haveMoved = new java.util.ArrayList<Critter>();
	
	public static void initialize() 
	{
		if (boardList.isEmpty()) {
			for (int i = 0; i < Params.world_height*Params.world_width; i++) {
				boardList.add(new ArrayList<Critter>());
			}
		}
	}
	
	public static void addCritter(Critter c, int x, int y)
	{
		initialize();
		population.add(c);
		addCritterToBoard(c, x, y);
	}
	
	public static List<Critter> getCritterPopulation()
	{
		return population;
	}
	
	public static List<Critter> getCritterBabies()
	{
		return babies;
	}
	
	/**
	 * This method adds a Critter to boardList. We don't need to worry
	 * about overwriting elements as boardList is an ArrayList of ArrayLists.
	 * 
	 * @param c the Critter instance to be added
	 */
	public static void addCritterToBoard(Critter c, int x, int y)
	{	
		initialize();
		int boardposition = cartesianToBoard(x,y);
		ArrayList<Critter> temp = boardList.get(boardposition);		// temp should be able to modify boardList
		temp.add(c);	// We don't need to add c to a specific index
		
		/*
		for(int i = 0; i < maxCrittersInSpot; i++){
			if(board[boardposition][i]==null){
				board[boardposition][i] = c;
				break;
			}
		}
		*/
		
		
	}
	
	/**
	 * Method that returns where in the boardList the Critter should 
	 * be placed at or removed from based on the (x, y) coordinate and 
	 * dimensions of the board
	 * 
	 * @param x the horizontal position
	 * @param y the vertical position
	 * @return
	 */
	public static int cartesianToBoard(int x, int y) 
	{
		initialize();
		int bpos;
		bpos = y * Params.world_width;
		bpos = bpos + x;
		return bpos;
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
		initialize();
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
		initialize();
		
		/* DONE: Determine whether this works with subclasses of Critter,
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
		initialize();
		
		haveMoved.clear();
	}
	
	/**
	 * A method to delete a Critter from boardList. It is used mostly in 
	 * walk() and run() to delete the old instance from boardList, which 
	 * is then placed back after the new x and y have been calculated.
	 * 
	 * @param c the Critter instance to be removed
	 * @param x the horizontal position
	 * @param y the vertical position
	 */
	public static void remove(Critter c, int x, int y){
		
		initialize();
		
		int bPos = cartesianToBoard(x, y);
		ArrayList<Critter> temp = boardList.get(bPos);
		
		// DONE: Test that remove() works properly
		temp.remove(c);	
		
		/*
		for(int i = 0; i < maxCrittersInSpot; i++){
			if(c == board[bPos][i]){
				board[bPos][i] = null;
				break;
			}
		}
		*/
	}
	
	/**
	 * Method to draw board, including boarders and critters
	 */
	public static void displayWorld()
	{
		initialize();
		
		int board_position = 0;
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++){
			System.out.print("-");
		}
		System.out.print("+");
		
		System.out.println();
		
		for(int i = 0; i < Params.world_height; i++){
			System.out.print("|");
			for(int j = 0; j < Params.world_width; j++){
				
				ArrayList<Critter> temp = boardList.get(board_position);
				
				if(!temp.isEmpty()) {
					System.out.print(temp.get(0));	// DONE: Seriously double check this
				}
				else {
					System.out.print(" ");
				}
				
				board_position++;
				
				/*
				if(board[board_position][0]!=null){
					System.out.print(board[board_position][0]);
				}else{
					System.out.print(" ");
				}
				*/
				
			}
			System.out.println("|");
		}
		
		System.out.print("+");
		for(int i = 0; i < Params.world_width;i++){
			System.out.print("-");
		}
		System.out.println("+");
	}
	
	public static void stage1AddAlgaeCraig()
	{
		//add 100 Algae to board
		for(int i = 0; i < 5; i++){
			try{
				Critter.makeCritter("Algae");
			}catch(Exception e){
				
			}
		}
		
		for(int i = 0; i < 3; i++){
			try{
				Critter.makeCritter("Craig");
			}catch(Exception e){
				
			}
		}
		
	}
	
	public static void clearBoard()
	{
		// Loop IS necessary
		for (ArrayList<Critter> list : boardList) {
			list.clear();
		}
		
		boardList.clear();
		
		/*
		int numSquares = Params.world_height*Params.world_width;
		
		for (int i = 0; i < numSquares; i++) {
			for (int j = 0; j < maxCrittersInSpot; j++) {
				board[i][j] = null;
			}
		}
		*/
		
	}
	
	public static boolean checkForwardDirection(Critter c) 
	{
		int position = 0;
		boolean found = false;
		for (ArrayList<Critter> list : boardList) {
			if (list.contains(c)) {
				found = true;
				position = boardList.indexOf(list);
				break;
			}
		}
		
		if (!found) {
			return false;
		}
		
		int newPosition = 0;
		if ((position - Params.world_width) < 0) {
			newPosition = position + ((Params.world_height - 1) * Params.world_width);
		}
		else {
			newPosition = position - Params.world_width;
		}
		
		return boardList.get(newPosition).isEmpty();
	}
	
	public static boolean checkBackwardDirection(Critter c) 
	{
		int position = 0;
		boolean found = false;
		for (ArrayList<Critter> list : boardList) {
			if (list.contains(c)) {
				found = true;
				position = boardList.indexOf(list);
				break;
			}
		}
		
		if (!found) {
			return false;
		}
		
		int newPosition = 0;
		if ((position + Params.world_width) > (boardList.size() - 1)) {
			newPosition = position - ((Params.world_height - 1) * Params.world_width);
		}
		else {
			newPosition = position + Params.world_width;
		}
		
		return boardList.get(newPosition).isEmpty();
	}

}
