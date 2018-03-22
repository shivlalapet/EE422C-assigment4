package assignment4;
import assignment4.Critter.TestCritter;

public class ShivCritter2 extends TestCritter {
    private int direction;
    private int numFights;
    private boolean hasMoved;

    public ShivCritter2(){
        direction = Critter.getRandomInt(8);
        hasMoved = false;
        numFights = 0;
    }

    @Override
    public void doTimeStep() {
        hasMoved = false;
        //low on energy...urgent to find food
        if(getEnergy() < 30){
            run(direction);
            hasMoved = true;
        }
        //some energy...look to find food...can afford to run
        if(getEnergy() >= 30 && getEnergy() < 100){
            run(direction);
            hasMoved = true;
        }
        //a ton of energy... reproduce and move
        if(getEnergy() >= 100){
            MyCritter6 child = new MyCritter6();
            reproduce(child, Critter.getRandomInt(8));
            walk(direction);
            hasMoved = true;
        }
        //new direction
        direction = Critter.getRandomInt(8);
        if(direction == 0)
            direction = 7; //adds to randomness of direction
    }

    @Override
    public boolean fight(String opponent) {
        if(opponent.equals("Algae"))
            return true;    //always fight and eat algae
        else if(getEnergy() >= 25){
            numFights++;	//fight, if above low energy
            return true;
        }
        else if(getEnergy() < 25){
            walk(direction);	//walk, if energy is low
            return false;
        }
        numFights ++;	//fight always if approach another critter
        return true;
    }

    @Override
    public String toString () {
        return "2";
    }

    public static void printStats(java.util.List<Critter> mycritter){
        int totalFights = 0;
        for(Object obj : mycritter){
            ShivCritter2 critter = (ShivCritter2) obj;
            totalFights += critter.numFights;
        }
        System.out.println("Total number of ShivCritter2: " + mycritter.size());
        System.out.println("Total number of fights: " + totalFights);
    }
}
