package assignment4;
import assignment4.Critter.TestCritter;

public class ShivCritter1 extends TestCritter {
    private int direction;
    private int numFights;
    private boolean hasMoved;

    public ShivCritter1(){
        direction = Critter.getRandomInt(8);
        hasMoved = false;
        numFights = 0;
    }

    @Override
    public void doTimeStep() {
        hasMoved = false;
        //low on energy...urgent to find food
        if(getEnergy() < 25){
            walk(direction);
            hasMoved = true;
        }
        //some energy...look to find food
        if(getEnergy() >= 25 && getEnergy() < 75){
            run(direction);
            hasMoved = true;
        }
        //a ton of energy... reproduce, don't need to move
        if(getEnergy() >= 75){
            MyCritter6 child = new MyCritter6();
            reproduce(child, Critter.getRandomInt(8));
        }
        //new direction
        direction = Critter.getRandomInt(8) % 8;
    }

    @Override
    public boolean fight(String opponent) {
        if(opponent.equals("Algae"))
            return true;    //always fight and eat algae
        if(!hasMoved){
            if(getEnergy() >= 100){
                numFights++;	//fight, if approached with 100+ energy
                return true;
            }
            else if(getEnergy() < 100){
                run(direction);	//run, if approached with less than 100 energy
                return false;
            }
        }
        numFights ++;	//fight always if approach another critter
        return true;
    }

    @Override
    public String toString () {
        return "S";
    }

    public static void printStats(java.util.List<Critter> mycritter){
        int totalFights = 0;
        for(Object obj : mycritter){
            ShivCritter1 critter = (ShivCritter1) obj;
            totalFights += critter.numFights;
        }
        System.out.println("Total number of ShivCritter1: " + mycritter.size());
        System.out.println("Total number of fights: " + totalFights);
    }
}
