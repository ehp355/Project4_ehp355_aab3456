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

import java.lang.Math;
import java.util.*;

import java.lang.reflect.Modifier;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	
	// We do NOT use the population list in Critter
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	
	// We do use the babies list in Critter
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}

	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}


	/* A one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

	private int energy = 0;
	protected int getEnergy() { return energy; }

	private int x_coord;
	private int y_coord;

	protected final void walk(int direction) 
	{	
		// DONE: Does NOT throw a ConcurrentModificationException if called from fight()
		if (!CritterWorld.checkIfMoved(this)) {
			CritterWorld.remove(this, this.x_coord, this.y_coord);
			stepper(1, direction);
			CritterWorld.addCritterToBoard(this, this.x_coord, this.y_coord);
			CritterWorld.addCritterToMoved(this);
		}
		
		this.energy = this.energy - Params.walk_energy_cost;
	}

	protected final void run(int direction) 
	{
		// DONE: Does NOT throw a ConcurrentModificationException if called from fight()
		if (!CritterWorld.checkIfMoved(this)) {
			CritterWorld.remove(this, this.x_coord, this.y_coord);
			stepper(2, direction);
			CritterWorld.addCritterToBoard(this, x_coord, y_coord);
			CritterWorld.addCritterToMoved(this);
		}
		
		this.energy = this.energy - Params.run_energy_cost;
	}
	
	private void stepper(int step, int direction){
		switch(direction){
		case 0: x_coord = x_coord+step;
				break;
		case 1: x_coord = x_coord+step;
				y_coord = y_coord-step;
				break;
		case 2: y_coord = y_coord-step;
				break;
		case 3: x_coord = x_coord-step;
				y_coord = y_coord-step;
				break;
		case 4: x_coord = x_coord-step;
				break;
		case 5: x_coord = x_coord-step;
				y_coord = y_coord+step;
				break;
		case 6: y_coord = y_coord+step;
				break;
		case 7: x_coord = x_coord+step;
				y_coord = y_coord+step;
		}

		/* If statements to check if the critter
		 * needs to be wrapped around the map
		 */
		if(y_coord<0){
			y_coord = Params.world_height+y_coord;
		}else if(y_coord>Params.world_height-1){
			y_coord = y_coord-Params.world_height;
		}

		if(x_coord<0){
			x_coord = Params.world_width+x_coord;
		}else if(x_coord>Params.world_width-1){
			x_coord = x_coord-Params.world_width;
		}
	}

	protected final void reproduce(Critter offspring, int direction) {
		
		if(this.energy>= Params.min_reproduce_energy){
			
			/* Set the offspring's coordinates to its
			 * parent's coordinates initially
			 */
			offspring.x_coord = this.x_coord;
			offspring.y_coord = this.y_coord;
		
			// Use stepper() to initialize offspring position
			offspring.stepper(1, direction);
			
			// Set offspring energy 
			offspring.energy = this.energy / 2;
			
			// Reassign the parent so that it has 1⁄2 of its energy (rounding fraction up).
			// Because energy is an integer, the only fraction we'll deal with is 0.5
			double argument = (double)this.energy/(double)2.0;
			this.energy = (int)Math.ceil(argument);
			babies.add(offspring);
		
		}else{
			return;
		
		}
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String opponent);

	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name
	 * @throws InvalidCritterException
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException
	{
		Class<?> critterClass = null;
		String packageClass = myPackage + "." + critter_class_name;
		
		/* We must throw an InvalidCritterException when catching any other
		 * exception in this method.
		 * 
		 * DONE: Double-check throwing InvalidCritterException here
		 */
		try {
			// Need fully-qualified name
			critterClass = Class.forName(packageClass);
		} 
		catch (ClassNotFoundException e) {
			// Use empty strings for InvalidCritterException (check Piazza)
			throw new InvalidCritterException("");
		}
		
		// newInstance() returns an object of type Object
		Object newCritter;
		
		try {
			newCritter = critterClass.newInstance();
		} 
		catch (InstantiationException e) {
			throw new InvalidCritterException("");
		} 
		catch (IllegalAccessException e) {
			throw new InvalidCritterException("");
		}	
		
		if(!(newCritter instanceof Critter)){
			throw new InvalidCritterException("");
		}
		
		// DONE: Test for abstract vs. concrete classes
		if (Modifier.isAbstract(critterClass.getModifiers())) {
			throw new InvalidCritterException("");
		}
		
		Critter c = (Critter)newCritter;
		// c.x_coord = Critter.getRandomInt(Params.world_width - 1);
		c.x_coord = Critter.getRandomInt(Params.world_width);
		// c.y_coord = Critter.getRandomInt(Params.world_height - 1);
		c.y_coord = Critter.getRandomInt(Params.world_height);
		c.energy = Params.start_energy;
		
		// TODO: Double check use of initialize()
		CritterWorld.initialize();
		CritterWorld.addCritter(c, c.x_coord, c.y_coord);
	}

	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException
	{
		List<Critter> result = new java.util.ArrayList<Critter>();
		List<Critter> population = CritterWorld.getCritterPopulation();
		Class<?> critterClass = null;
		String packageClass = myPackage + "." + critter_class_name;
		
		// DONE: Double-check throwing ICE here
		try {
			critterClass = Class.forName(packageClass);
		} 
		catch (ClassNotFoundException e) {
			throw new InvalidCritterException("");
		}
		
		/* The isAssignableFrom method determines if the class or interface represented by 
		 * the Critter.class object is either the same as, or is a superclass or superinterface of, 
		 * the class or interface represented by critterClass.
		 */
		if (!Critter.class.isAssignableFrom(critterClass)) {
			throw new InvalidCritterException("");
		}
		
		// DONE: Test for abstract vs. concrete classes
		if (Modifier.isAbstract(critterClass.getModifiers())) {
			throw new InvalidCritterException("");
		}
		
		/* The class Class's isInstance() method is the dynamic equivalent of the
		 * instanceof operator. It returns true if the specified argument is an  
		 * instance of the represented class (or of any of its subclasses). We use it
		 * because the instanceof operator does not work for types evaluated at run
		 * time.
		 */
		for (Critter critter : population) {
			if (critterClass.isInstance(critter)) {
				result.add(critter);
			}
		}
		
		return result;
	}

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		
		// DONE: Figure out how this is working
		
		/* Because the critter_count Map is using the String representations
		 * of Critters as key values, we'll get an entry for each TYPE of 
		 * Critter in the Map. As such, we won't just get an entry for each
		 * Critter in critters.
		 */
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}

	/* the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here.
	 *
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		/* A static nested class cannot refer directly to instance variables or 
		 * methods defined in its enclosing class: it can use them only 
		 * through an object reference. In our case, that object reference
		 * is "super".
		 */
		protected void setX_coord(int new_x_coord) 
		{
			CritterWorld.remove(this, super.x_coord, super.y_coord);
			super.x_coord = new_x_coord;
			
			/* Writing the method as:
			 * 
			 * CritterWorld.addCritterToBoard(this, x_coord, y_coord);
			 * 
			 * gives the error:
			 * "Cannot make a static reference to the non-static field x_coord"
			 */
			
			CritterWorld.addCritterToBoard(this, super.x_coord, super.y_coord);
		}

		protected void setY_coord(int new_y_coord) 
		{
			CritterWorld.remove(this, super.x_coord, super.y_coord);
			super.y_coord = new_y_coord;
			CritterWorld.addCritterToBoard(this, super.x_coord, super.y_coord);
		}

		protected int getX_coord() 
		{
			return super.x_coord;
		}

		protected int getY_coord() 
		{
			return super.y_coord;
		}


		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return CritterWorld.getCritterPopulation();
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			/* We don't call CritterWorld.getCritterBabies() 
			 * because we're using the babies list in Critter
			 */
			return babies;	
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() 
	{
		// DONE: Write this function
		babies.clear();
		// This should return the reference of the private variable
		List<Critter> population = CritterWorld.getCritterPopulation();
		population.clear();		
		CritterWorld.clearMoved();
		CritterWorld.clearBoard();
	}

	public static void worldTimeStep() {
		// Gets list of current critters on the board
		List<Critter> pop = CritterWorld.getCritterPopulation();
		
		// for loop to go through each Critter in population
		// and call their individual doTimeStep
		for(Critter c : pop){
			c.doTimeStep();
		}
		
		// Check for encounters (Critters in the same spot)
		for(Critter c : pop){
			checkSharePosition(c, c.x_coord, c.y_coord);
		}
		
		// Check Piazza (@267) for order of worldTimeStep here 
		for(int i = 0; i < pop.size(); i++){
			Critter c = pop.get(i);
			c.energy = c.energy - Params.rest_energy_cost;
		}
		
		// Refresh Algae
		// DONE: Shouldn't need to handle ICE here
		for(int i = 0; i < Params.refresh_algae_count;i++){
			try{
				makeCritter("Algae");
			}catch(Exception e){
				/* As Algae is a concrete class of Critter, we should never reach
				 * this block.
				 */		
			}
		}

		// Remove critters from pop whose energy<=0
		Iterator<Critter> it = pop.iterator();
		while (it.hasNext()) {
			Critter c = it.next();
			if (c.getEnergy() <= 0) {
				CritterWorld.remove(c, c.x_coord, c.y_coord);
				it.remove();	// Removes element from pop
			}
		}
		
		// Adds babies to general population
		for(int i = 0; i < babies.size(); i++){
			CritterWorld.addCritter(babies.get(i), babies.get(i).x_coord, babies.get(i).y_coord);
		}
		
		// Clear the list of Critters that have moved
		CritterWorld.clearMoved();
		
		// Clear the list of babies
		babies.clear();

	}
	
	private static void encounter(Critter a, Critter b)
	{
		boolean aFights = a.fight(b.toString());
		boolean bFights = b.fight(a.toString());
		int aFightNumber;
		int bFightNumber;
		
		// Checks if both have energy left and are in the still in the same position
		if((a.energy > 0 && b.energy > 0) && (CritterWorld.cartesianToBoard(a.x_coord, a.y_coord) == 
				CritterWorld.cartesianToBoard(b.x_coord,b.y_coord)))
		{
			if(aFights){
				aFightNumber = getRandomInt(a.energy);
			}
			else{
				aFightNumber = 0;
			}
			
			if(bFights){
				bFightNumber = getRandomInt(b.energy);
			}
			else{
				bFightNumber = 0;
			}
			
			if(aFightNumber == bFightNumber){
				resolveFight(a,b);
			}else if(aFightNumber > bFightNumber){
				resolveFight(a,b);
			}else if(aFightNumber < bFightNumber){
				resolveFight(b,a);
			}
		}
		
	}
	
	private static void checkSharePosition(Critter c, int x, int y)
	{
		CritterWorld.initialize();
		
		int bPos = CritterWorld.cartesianToBoard(x,y);
		ArrayList<Critter> temp = CritterWorld.boardList.get(bPos);
		Iterator<Critter> it = temp.iterator();
		
		/* Need iterator and while loop for cases when the Critter c
		 * runs away during a fight.
		 */
		while (it.hasNext() && temp.contains(c)) {
			Critter test = it.next();
			if (test != c) {
				Critter.encounter(c, test);
			}
		}
		
		/*
		for(int i = 0; i < maxCrittersInSpot; i++){
			if(board[bPos][i]!=null && board[bPos][i]!=c){
				Critter.encounter(c,board[bPos][i]);
			}
		}
		*/
		
	}
	
	/**
	 * Method to resolve who won the fight
	 * 
	 * @param winner the winning Critter in a fight
	 * @param loser the losing Critter in a fight
	 */
	private static void resolveFight(Critter winner, Critter loser)
	{	
		winner.energy = winner.energy + (loser.energy/2);
		loser.energy = 0;	
	}

	public static void displayWorld() 
	{
		CritterWorld.displayWorld();
	}
}
