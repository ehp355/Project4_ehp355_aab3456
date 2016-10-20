package assignment4;
/**
 * @author Enrique
 *
 * Critter2 sets its initial direction to 4. Critter2 will only attempt to fight
 * other Critter2s as well as reproduce 1 child. If it encounters any other Critter
 * it will attempt to reproduce 2 children and return false for fight. Critter2 will
 * continuously run in one direction. The runStats method will return how many times each
 * Critter2 on the board has fought, and the total number of fights between all the
 * Critter2s that are alive.
 */
public class Critter2 extends  Critter {
	private int dir;
	private int fights;
	@Override
	public String toString(){return "2";}

	public Critter2(){
		dir = 4;
		fights = 0;
	}

	/**
	 * Critter2 only fight each other, and attempt to preserve their genome by reproducing at
	 * any encounter with any other Critter. If it faces another Critter2 it will reproduce once
	 * if it is a different Critter it will reproduce twice.
	 */
	public boolean fight(String critterType){
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

	@Override
	public void doTimeStep(){

		run(dir);

	}



	/**
	 * Prints out the number of times each individual Critter2 has fought, and the total
	 * number of fights between all living Critter2s.
	 *
	 * @param critter2
	 */
	public static void runStats(java.util.List<Critter> critter2){
		int totalFights=0;
		for(Object obj: critter2){
			Critter2 c = (Critter2) obj;
			System.out.println("This Critter2 has fought "+ c.fights +" times.");
			totalFights= c.fights+totalFights;
		}
		System.out.println("All Critter2s still standing have fought a total of "+ totalFights);
	}
}
