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

import java.util.List;
public class CritterWorld {

	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	public static void addCritter(Critter c){
		population.add(c);
	}
	public static List<Critter> getCritterPopulation(){
		return population;
	}
	public static List<Critter> getCritterBabies(){
		return babies;
	}

}
