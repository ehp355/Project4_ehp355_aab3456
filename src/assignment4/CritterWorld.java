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

	// A "dynamic board" keeping track of every position and the Critter on them
	public static ArrayList<ArrayList<Critter>> boardList = new ArrayList<ArrayList<Critter>>();

	// List of all Critter instances with no particular ordering
	private	static List<Critter> population = new java.util.ArrayList<Critter>();

	// List of all babies (NOT USED)
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// List of all Critters who have called walk() or run() in a time step
	private static List<Critter> haveMoved = new java.util.ArrayList<Critter>();
	
	/**
	 * This method determines whether boardList has been initialized, and does
	 * so if it hasn't been. It is a workaround to using a static initialization 
	 * block as that approach fails in some test cases. As such, it needs to be 
	 * called at the beginnning of the appropriate methods.
	 */
	public static void initialize()
	{
		if (boardList.isEmpty()) {
			for (int i = 0; i < Params.world_height*Params.world_width; i++) {
				boardList.add(new ArrayList<Critter>());
			}
		}
	}
	
	
	/**
	 * This method adds a Critter c to the boardList, dynamically keeping track
	 * of every position while simultaneously updating the population.
	 * 
	 * @param c Critter to add to boardList 
	 * @param x x coordinate of Critter c
	 * @param y y coordinate of Critter c
	 */
	public static void addCritter(Critter c, int x, int y)
	{
		initialize();
		population.add(c);
		addCritterToBoard(c, x, y);
	}
	
	
	/**
	 * Getter method for the CritterWorld field population
	 * 
	 * @return the List population
	 */
	public static List<Critter> getCritterPopulation()
	{
		return population;
	}
	
	
	/**
	 * Getter method for the CritterWorld field babies
	 * 
	 * @return the List babies
	 */
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
	}
	

	/**
	 * This method returns where in the boardList the Critter should
	 * be placed at or removed from based on the (x, y) coordinate and
	 * dimensions of the board.
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
	 * This method adds a Critter to the List haveMoved. It is
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
	public static void remove(Critter c, int x, int y)
	{
		initialize();
		int bPos = cartesianToBoard(x, y);
		ArrayList<Critter> temp = boardList.get(bPos);
		temp.remove(c);

	}
	

	/**
	 * This method implements the rudimentary "view" of our architecture
	 * by using "+" and "-" Strings to denote borders and by calling 
	 * the toString() method of various Critters to show where they 
	 * are on the board. Note: toString() isn't used explicitly, but rather, 
	 * when we make a call to System.out.print() or System.out.println().
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
					System.out.print(temp.get(0));	
				}
				else {
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
	
	
	/**
	 * This method "empties" the 2D ArrayList representing the board by going through
	 * each ArrayList reference in boardList and calling clear() on it. After 
	 * this has been done, boardList itself is cleared. This makes sure that 
	 * all references are properly erased.
	 */
	public static void clearBoard()
	{
		// Loop IS necessary
		for (ArrayList<Critter> list : boardList) {
			list.clear();
		}

		boardList.clear();
	}
	
	
	/**
	 * This method is primarily used by Critter4 to make sure the 
	 * position in front of it (direction 2) is unoccupied when it tries to escape.
	 * As we know the "wrapping" will occur in row 0, we simply need to add
	 * (Params.world_height - 1) * Params.world_width to the board position when 
	 * the Critter goes "out of bounds".
	 * 
	 * @param c the Critter whose movement we want to check
	 * @return true if the space in direction 2 of this Critter is empty
	 */
	public static boolean checkForwardDirection(Critter c)
	{
		initialize();
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
	
	
	/**
	 * This method is primarily used by Critter4 to make sure the 
	 * position behind it (direction 6) is unoccupied when it tries to escape.
	 * As we know the "wrapping" will occur in row (world_height - 1), we simply 
	 * need to subtract (Params.world_height - 1) * Params.world_width from the  
	 * board position when the Critter goes "out of bounds".
	 * 
	 * @param c the Critter whose movement we want to check
	 * @return true if the space in direction 6 of this Critter is empty
	 */
	public static boolean checkBackwardDirection(Critter c)
	{
		initialize();
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
