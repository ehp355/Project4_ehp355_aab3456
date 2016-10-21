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

package assignment4;

/**
 * Critter4's behavior is based on "dice rolls". Critter4 will roll two
 * six-sided dice (starting from 1) at the beginning of every time step.
 * If the two dice add up to 4, it will run and reproduce. Otherwise, it will go
 * in the reverse direction that it did last time.
 *
 * In a fight, Critter4 will roll two dice. If the two dice add up to 8, it will
 * engage the enemy. If the two dice are not 8, it will attempt to walk forward
 * or backward based on whether the number rolled is even or odd.
 *
 * @author Aaron Babber
 */
public class Critter4 extends Critter {

	private int numFours = 0;
	private int numFights = 0;
	private int numRunAttempts = 0;
	private int prevDirection = 0;
	private int currDirection = 0;
	
	/**
	 * The metrics used for runStats (numFours, numFights, numRunAttempts)
	 * are initialized to zero when a new instance is created. The next 
	 * direction this Critter will go is chosen randomly.
	 */
	public Critter4()
	{
		numFours = 0;
		numFights = 0;
		numRunAttempts = 0;
		currDirection = Critter.getRandomInt(8);
		prevDirection = 0;
	}
	
	
	/**
	 * This Critter runs in a random directions and
	 * reproduces if it rolls a 4 using two dice, but it 
	 * backtracks to its previous position if it rolls 
	 * anything else.
	 */
	@Override
	public void doTimeStep()
	{
		int die1 = Critter.getRandomInt(6) + 1;
		int die2 = Critter.getRandomInt(6) + 1;
		int diceTotal = die1 + die2;

		if (diceTotal == 4) {
			numFours++;
			prevDirection = currDirection;
			currDirection = Critter.getRandomInt(8);
			run(currDirection);
			reproduce(new Critter4(), currDirection);
		}
		else {
			prevDirection = currDirection;
			currDirection = (prevDirection + 4) % 8;
			walk(currDirection);
		}
	}
	
	
	/**
	 * This Critter engages in a fight if it rolls an 8 using
	 * two dice, but it attempts to run away if it rolls anything 
	 * else. If Critter4 doesn't roll an 8, it tries to escape forward if the
	 * dice roll was even, and it tries to escape backward if the dice roll was
	 * odd.
	 */
	@Override
	public boolean fight(String opponent)
	{
		int die1 = Critter.getRandomInt(6) + 1;
		int die2 = Critter.getRandomInt(6) + 1;
		int diceTotal = die1 + die2;

		if (diceTotal == 8) {
			numFights++;
			return true;
		}
		else {
			numRunAttempts++;
			if (diceTotal % 2 == 0 && CritterWorld.checkForwardDirection(this)) {
				walk(2);
			}
			else if (diceTotal % 2 == 1 && CritterWorld.checkBackwardDirection(this)) {
				walk(6);
			}

			return false;
		}

	}
	
	
	/**
	 * Prints a "4" on the "view" whenever System.out.print()
	 * or System.out.println() are called
	 */
	public String toString()
	{
		return "4";
	}

	
	/**
	 * Prints the total number of fours rolled, run attempts, and fight
	 * attempts for the Critter4 instances on the board
	 *
	 * @param critterFours list of Critter4 instances
	 */
	public static void runStats(java.util.List<Critter> critterFours)
	{
		int foursTotal = 0;
		int runAttemptTotal = 0;
		int fightTotal = 0;

		for (Object obj : critterFours) {
			Critter4 c = (Critter4) obj;
			foursTotal = foursTotal + c.numFours;
			runAttemptTotal = runAttemptTotal + c.numRunAttempts;
			fightTotal = fightTotal + c.numFights;
		}

		System.out.println("Critter4 stats: fours rolled -- " + foursTotal + ", run attempts -- "
						   + runAttemptTotal + ", fights -- " + fightTotal);

		return;
	}

}
