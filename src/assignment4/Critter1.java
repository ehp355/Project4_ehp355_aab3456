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
  * Critter1 starts with a random direction, when it walks, it will walk back and forth
  * from its original position and an adjacent position. Craig is the natural predator to
  * Critter1 so when fight is called it will choose not to fight Craig but instead will reproduce
  * a child in an adjacent space that is perpendicular to the parent's movement. Critter1 is the
  * natural predator Critter2 so it will automatically return true when it is set to fight Critter2.
  * With any other versions of Critter its random decision for Critter1. The runStats methods
  * will print out how many children each living Critter1 has reproduced.
  * 
  * @author Enrique Perez-Osborne
  */

package assignment4;

public class Critter1 extends Critter {
	private int dir;
	private int children;

	
	/**
	 * Prints a "1" on the "view" whenever System.out.print()
	 * or System.out.println() are called
	 */
	@Override
	public String toString(){return "1";}

	/**
	 * Initializes the direction that Critter1 will move in when
	 * constructed
	 */
	public Critter1(){
		dir = Critter.getRandomInt(8);
	}

	/**
	 * Craig is the natural predator to Critter1, so Critter1 will run away if
	 * if faced against Craig. Critter1 is the natural predator to Critter2, so it will
	 * always fight if faced against Critter2. Anything else will be based
	 * on a random roll. 1 says it fights 0 says it runs.
	 */
	public boolean fight(String critterType){
		if(critterType.equals("Craig")){
			int cdir;
			if(dir >=0 && dir<=3){
				cdir = dir +2;
			}else{
				cdir = dir -2;
			}
			reproduce(new Critter1(),cdir);
			children++;
			return false;
		}else if(critterType.equals("Critter2")){
			return true;
		}else{
			int rand = Critter.getRandomInt(1);
			return rand==1;
		}


	}
	
	/**
	 * This method calls the CalculateWalk() and walk() methods to move Critter1 for
	 * a single time step. The movement behavior is described in the CalculateWalk()
	 * comments.
	 */
	@Override
	public void doTimeStep(){
		CalculateWalk();
		walk(dir);
	}

	/**
	 * This method forces Critter1 to walk back and forth. Its children will also go 
	 * back and forth but in the direction at 90 degrees to its parent's direction.
	 */
	public void CalculateWalk(){
		if(dir>=0 && dir<=3){
			dir = dir + 4;
		}else{
			dir = dir - 4;
		}
	}

	/**
	 * Prints out how manys kids each Critter1 on the board has had
	 *
	 * @param critter1 a List of Critter1 instances
	 */
	public static void runStats(java.util.List<Critter> critter1){

		for(Object obj : critter1){
			Critter1 c = (Critter1) obj;
			System.out.println("This Critter1 had "+ c.children+" kids.");
		}
	}
}
