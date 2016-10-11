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

