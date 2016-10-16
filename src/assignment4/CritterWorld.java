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
	static int maxCrittersInSpot = 10;
	//board is a 2d ArrayList where the 1st dimension is a position on the board and the 2nd dimension is a list of 
	// cirtters in that position
	private static Critter[][] board = new Critter[Params.world_height*Params.world_width][maxCrittersInSpot];

	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	

	public static void addCritter(Critter c, int x, int y){
		population.add(c);
		addCritterToBoard(c , x, y);
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
	public static void addCritterToBoard(Critter c,int x ,int y){
		
		int boardposition = cartesianToBoard(x,y);
		for(int i = 0; i < maxCrittersInSpot; i++){
			if(board[boardposition][i]==null){
				board[boardposition][i] = c;
				break;
			}
		}
		
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
	
	public static void remove(Critter c, int x, int y){
		int bPos = cartesianToBoard(x,y);
		for(int i = 0; i < maxCrittersInSpot; i++){
			if(c == (Critter)board[bPos][i]){
				board[bPos][i] = null;
				break;
			}
		}
	}
	
	public static void move(Critter c, int x, int y){
		int bPos = cartesianToBoard(x,y);
		for(int i = 0; i < maxCrittersInSpot; i++){
			if(board[bPos][i]== null){
				board[bPos][i] = c;
				break;
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
		System.out.print("+");
	}
	
	public static void stage1AddAlgaeCraig(){
		//add 100 Algae to board
		for(int i = 0; i < 100; i++){
			addCritter(new Algae(),Critter.getRandomInt(Params.world_width-1),Critter.getRandomInt(Params.world_height-1));
		}
		
		for(int i = 0; i < 25; i++){
			addCritter(new Craig(), Critter.getRandomInt(Params.world_width-1), Critter.getRandomInt(Params.world_height-1));
		}
		
	}

}
