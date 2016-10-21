/* CRITTERS Critter.java
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

 /**
  * Critter2 sets its initial direction to 4. Critter2 will only attempt to fight
  * other Critter2s as well as reproduce 1 child. If it encounters any other Critter
  * it will attempt to reproduce 2 children and return false for fight. Critter2 will
  * continuously run in one direction. The runStats method will return how many times each
  * Critter2 on the board has fought, and the total number of fights between all the
  * Critter2s that are alive.
  * 
  * @author Enrique Perez-Osborne
  */

package assignment4;

public class Critter2 extends  Critter {
	private int dir;
	private int fights;
	
	/**
	 * Prints a "2" on the "view" whenever System.out.print()
	 * or System.out.println() are called
	 */
	@Override
	public String toString()
	{
		return "2";
	}
	
	
	/**
	 * Initializes a Critter2 instance to the "left" direction and sets the 
	 * fight counter to zero
	 */
	public Critter2()
	{
		dir = 4;
		fights = 0;
	}
	

	/**
	 * Critter2 only fight each other, and attempt to preserve their genome by reproducing at
	 * any encounter with any other Critter. If it faces another Critter2 it will reproduce once,
	 * but if it is a different Critter it will reproduce twice.
	 */
	public boolean fight(String critterType)
	{
		int cdir;
		if(dir >=0 && dir<=3){
			cdir = dir +2;
		}else{
			cdir = dir -2;
		}
		if(critterType.equals("Critter2")){
			reproduce(new Critter2(), dir);
			fights++;
			return true;
		}else{
			reproduce(new Critter2(), dir);
			reproduce(new Critter2(), cdir);
			return false;
		}
	}
	
	
	/**
	 * Critter2 will attempt to run continuously every
	 * consecutive time step. The behavior of its motion is described
	 * in the paragraph at the top.
	 */
	@Override
	public void doTimeStep()
	{
		run(dir);
	}


	/**
	 * The method prints out the number of times each individual Critter2 has fought, 
	 * and the total number of fights between all living Critter2 instances.
	 *
	 * @param critter2 a List of Critter2 instances
	 */
	public static void runStats(java.util.List<Critter> critter2)
	{
		int totalFights=0;
		for(Object obj: critter2){
			Critter2 c = (Critter2) obj;
			System.out.println("This Critter2 has fought "+ c.fights +" times.");
			totalFights= c.fights+totalFights;
		}
		System.out.println("All Critter2s still standing have fought a total of "+ totalFights);
	}
	
}
