package assignment4;
/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Shiv Lalapet
 * EID: SL39596
 * Unique No: 15495
 * <Student2 Name>
 * <Student2 EID>
 * Unique No: 15495
 * Spring 2018
 */

import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */


public abstract class Critter {
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
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
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private boolean hasMoved;
	private boolean isFighting;

	private final int moveX(int steps){
		if((x_coord + steps) < 0){
			return Params.world_width - steps;
		}
		else if(x_coord + steps > Params.world_width - 1){
			return steps - 1;
		}
		else
			return x_coord += steps;
	}

	private final int moveY(int steps){
		if((y_coord + steps) < 0){
			return Params.world_height - steps;
		}
		else if(y_coord + steps > Params.world_height - 1){
			return steps - 1;
		}
		else
			return y_coord += steps;
	}

	protected final void walk(int direction) {
		if(this.hasMoved){return;}
		switch (direction){
			case 0:
				y_coord = moveY(-1); //move north
			case 1:
				x_coord = moveX(1);
				y_coord = moveY(-1); //move northeast
			case 2:
				x_coord = moveX(1); //move east
			case 3:
				x_coord = moveX(1);
				y_coord = moveY(1); //move southeast
			case 4:
				y_coord = moveY(1); //move south
			case 5:
				x_coord = moveX(-1);
				y_coord = moveY(1); //move southwest
			case 6:
				x_coord = moveX(-1); //move west
			case 7:
				x_coord = moveX(-1);
				y_coord = moveY(-1); //move northwest
		}
		energy -= Params.walk_energy_cost;
		this.hasMoved = true;
	}
	
	protected final void run(int direction) {
		if(this.hasMoved){return;}
		switch (direction){
			case 0:
				y_coord = moveY(-2); //move north
			case 1:
				x_coord = moveX(2);
				y_coord = moveY(-2); //move northeast
			case 2:
				x_coord = moveX(2); //move east
			case 3:
				x_coord = moveX(2);
				y_coord = moveY(2); //move southeast
			case 4:
				y_coord = moveY(2); //move south
			case 5:
				x_coord = moveX(-2);
				y_coord = moveY(2); //move southwest
			case 6:
				x_coord = moveX(-2); //move west
			case 7:
				x_coord = moveX(-2);
				y_coord = moveY(-2); //move northwest
		}
		energy -= Params.walk_energy_cost;
		this.hasMoved = true;
	}
	
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy){return;}

		offspring.energy = this.energy / 2;
		this.energy = (int)Math.ceil(this.energy / 2);
		switch (direction){
			case 0:
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.moveY(-1); //move north
			case 1:
				offspring.x_coord = this.moveX(1);
				offspring.y_coord = this.moveY(-1); //move northeast
			case 2:
				offspring.x_coord = this.moveX(1); //move east
				offspring.y_coord = this.y_coord;
			case 3:
				offspring.x_coord = this.moveX(1);
				offspring.y_coord = this.moveY(1); //move southeast
			case 4:
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.moveY(1); //move south
			case 5:
				offspring.x_coord = this.moveX(-1);
				offspring.y_coord = this.moveY(1); //move southwest
			case 6:
				offspring.x_coord = this.moveX(-1); //move west
				offspring.y_coord = this.y_coord;
			case 7:
				offspring.x_coord = this.moveX(-1);
				offspring.y_coord = this.moveY(-1); //move northwest
		}
		babies.add(offspring);
	}

	public abstract void doTimeStep();
	public abstract boolean fight(String oponent);
	
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
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		try{
			//create critter
			Critter c = (Critter)Class.forName(myPackage + "." + critter_class_name).newInstance();
			Critter.population.add(c);
			//initialize
			c.hasMoved = false;
			c.isFighting = false;
			c.energy = Params.start_energy;
			c.x_coord = Critter.getRandomInt(Params.world_width);
			c.y_coord = Critter.getRandomInt(Params.world_height);
		}
		catch (InstantiationException e){
			throw new InvalidCritterException(critter_class_name);
		}
		catch (IllegalAccessException e){
			throw new InvalidCritterException(critter_class_name);
		}
		catch (ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}
	}
	
	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		Class<?> critterClass = null;
		try{
			critterClass = Class.forName(myPackage + "." + critter_class_name);
		}
		catch (ClassNotFoundException e){
			throw new InvalidCritterException(critter_class_name);
		}

		List<Critter> result = new java.util.ArrayList<Critter>();
		for(Critter c : population){
			if(critterClass.isInstance(c)){
				result.add(c);
			}
		}
		return result;
	}
	
	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
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
		
		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
		
		protected int getX_coord() {
			return super.x_coord;
		}
		
		protected int getY_coord() {
			return super.y_coord;
		}
		

		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}
		
		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population 
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		for(Critter c : population){
			population.remove(c);
		}
		for(Critter c : babies){
			babies.remove(c);
		}
	}

	/**
	 * Executes one world step (i.e. each Critter gets one turn)
	 */
	public static void worldTimeStep() {
		for (Critter c : population) {
			c.doTimeStep();
		}
		//check for critters in same spot
		for (int i=0; i < population.size(); i++) {
			Critter crit1 = population.get(i);
			if (crit1.getEnergy() <= 0) {
				continue;
			}
			//second critter loop
			for (int j=i+1; j < population.size(); j++) {
				if (crit1.energy <= 0) {
					break;
				}
				Critter crit2 = population.get(j);
				if (crit2.energy <= 0) {
					continue;
				}
				//if critters are in same spot, fight
				if (crit1.x_coord == crit2.x_coord && crit1.y_coord == crit2.y_coord) {
					crit1.isFighting = true;
					crit2.isFighting = true;

					//check if critters want to fight
					boolean c1 = crit1.fight(crit2.toString());
					boolean c2 = crit2.fight(crit1.toString());

					//reset fighting booleans to false
					crit1.isFighting = false;
					crit2.isFighting = false;

					//check if the critters are still alive after fighting
					if (crit1.energy >= 0 && crit2.energy >= 0) {
						//if they're both alive and in same spot
						if (crit1.x_coord == crit2.x_coord && crit1.y_coord == crit2.y_coord) {
							//roll the dice
							int r1 = 0;
							int r2 = 0;
							if (c1)
								r1 = Critter.getRandomInt(crit1.energy + 1);
							if (c2)
								r2 = Critter.getRandomInt(crit2.energy + 1);

							//which critter won fight
							if (r1 > r2) {
								crit1.energy += crit2.energy / 2;
								crit2.energy = 0;
							} else {
								crit2.energy += crit1.energy / 2;
								crit1.energy = 0;
							}
						}
					}
				}
			}
		}
		for (int j = 0; j < Params.refresh_algae_count; j++) {
			try {
				makeCritter("Algae");
			} catch (InvalidCritterException e){}
		}

		for (Critter c : babies){
			population.add(c);
		}
		babies.clear();
		int popSize = population.size();
		int index = 0;
		//scan for dead critters
		for(int k=0; k < popSize; k++){
			population.get(index).energy -= Params.rest_energy_cost;
			if(population.get(index).energy <= 0)
				population.remove(population.get(index));
			else
				index++;
		}

		//reset movement boolean
		for(Critter c : population){
			c.hasMoved = false;
		}
	}

	/**
	 * Displays the world to the screen
	 */
	public static void displayWorld() {
		String[][] world = new String[Params.world_height +2][Params.world_width +2]; //+2 for the borders

		for(int r=0; r < Params.world_height +2; r++){
			for(int c=0; c < Params.world_width +2; c++){
				if(r == 0 || r == Params.world_height +1){
					if(c == 0 || c == Params.world_width +1)
						world[r][c] = "+";
					else
						world[r][c] = "-";
				}
				else if(c == 0 || c == Params.world_width +1)
					world[r][c] = "|";
				else
					world[r][c] = " ";
			}
		}

		//add critters
		for(Critter c : population){
			if(c.y_coord >= 0 && c.y_coord <= Params.world_height && c.x_coord >= 0 && c.x_coord <= Params.world_width)
				world[c.y_coord +1][c.x_coord +1] = c.toString();
		}
		//and print the world
		for(int r=0; r < Params.world_height +2; r++){
			for(int c = 0; c < Params.world_width +2; c++){
				System.out.print(world[r][c]);
			}
			System.out.println();
		}
	}
}
