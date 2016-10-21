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
 * Critter3's behavior is based on "coin tosses". During each time step,
 * a Critter3 instance will flip a coin. If the coin is heads (where
 * an even number is heads), Critter3 will walk in an even direction (0, 2, 4, 6)
 * AND reproduce. If the coin is tails (where an odd number is tails), Critter3
 * will run in an odd direction (1, 3, 5, 7) but NOT reproduce. The directions loop
 * around: they will always progress as 0/1 -> 2/3 -> 4/5 -> 6/7 -> 0/1 regardless
 * of whether we have consecutive tosses of the same type. For example, if we
 * flip H, H, T, T in 4 time steps, the directions moved will be 0 -> 2 -> 1 -> 3.
 *
 * During a fight, Critter3 will engage its opponent if it flips 3 tails in a row.
 * Otherwise, it will not engage. These coin flips do not affect the direction it
 * will move in the next time step nor do they get counted for runStats. The fields
 * evenFlips and oddFlips are primarily used to keep a tally of direction(s) traveled.
 *
 * @author Aaron Babber
 */
public class Critter3 extends Critter {

	private int evenDirection = 0;
	private int oddDirection = 1;
	private int evenFlips = 0;
	private int oddFlips = 0;
	private int numFights = 0;
	
	/**
	 * The constructor sets all fields, save for oddDirection, to 0 when a Critter3
	 * is initialized. This is because the first odd direction angle is a "1".
	 * The fields evenFlips, oddFlips, and numFights are metrics used in runStats,
	 * whereas the other two are used to determine movement.
	 */
	public Critter3()
	{
		evenDirection = 0;
		oddDirection = 1;
		evenFlips = 0;
		oddFlips = 0;
		numFights = 0;
	}
	
	/**
	 * Critter3 moves at an even angle if it flips "heads" and 
	 * it moves at an odd angle if it flips "tails". Critter3 only reproduces in 
	 * time steps where it flips a "heads". Furthermore, Critter3 only runs 
	 * at odd angles and it only walks at even angles.
	 */
	@Override
	public void doTimeStep()
	{
		// Return a 0 or 1;
		int coinFlip = Critter.getRandomInt(2);

		if (coinFlip % 2 == 0) {
			evenFlips++;
			evenDirection = (evenDirection + 2) % 8;
			walk(evenDirection);
			reproduce(new Critter3(), evenDirection);

		}
		else {
			oddFlips++;
			oddDirection = (oddDirection + 2) % 9;
			run(oddDirection);
		}

		return;
	}
	
	/**
	 * Critter3 engages its opponent if 3 coin flips yield "tails", but
	 * it does not engage its opponent if any one of them is heads.
	 */
	@Override
	public boolean fight(String opponent)
	{
		int flip1 = Critter.getRandomInt(2);
		int flip2 = Critter.getRandomInt(2);
		int flip3 = Critter.getRandomInt(2);

		if ((flip1 % 2 == 1) && (flip2 % 2 == 1) && (flip3 % 2 == 1)) {
			numFights++;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Prints a "3" on the "view" whenever System.out.print()
	 * or System.out.println() are called
	 */
	public String toString()
	{
		return "3";
	}

	/**
	 * Prints the total number of even (heads) coin flips, odd (tails) coin flips, 
	 * and fights for the Critter3 instances on the board
	 *
	 * @param critterThrees list of Critter3 instances
	 */
	public static void runStats(java.util.List<Critter> critterThrees)
	{
		int evenFlipTotal = 0;
		int oddFlipTotal = 0;
		int fightTotal = 0;

		for (Object obj : critterThrees) {
			Critter3 c = (Critter3) obj;
			evenFlipTotal = evenFlipTotal + c.evenFlips;
			oddFlipTotal = oddFlipTotal + c.oddFlips;
			fightTotal = fightTotal + c.numFights;
		}

		System.out.println("Critter3 stats: # even movement -- " + evenFlipTotal + ", # odd movement -- "
						   + oddFlipTotal + ", fights -- " + fightTotal);

		return;
	}

}
