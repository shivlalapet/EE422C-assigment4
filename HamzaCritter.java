package assignment4;
import assignment4.Critter.TestCritter;

public class HamzaCritter extends TestCritter {
    private int dir;
    private int Fights_num;
    private boolean hasMoved;

    public HamzaCritter(){
        dir = Critter.getRandomInt(8);
        hasMoved = false;
        Fights_num = 0;
    }


    @Override
    public boolean fight(String opponent) {
        if(opponent.equals("Algae"))
            return true;    //always try and fight Algae as its source of food
        else if(getEnergy() >= 40){
            Fights_num++;	//fight, if above low energy
            return true;
        }
        else if(getEnergy() < 40){
            walk(dir);	//walk away, if energy is less than min requirement
            return false;
        }
        return true;
    }

    @Override
    public void doTimeStep() {
        hasMoved = false;

        //Energy is low so run to find food faster
        if(getEnergy() < 40){
            run(dir);
            hasMoved = true;
        }
        //There is some energy so can run
        if(getEnergy() >= 40 && getEnergy() < 90){
            run(dir);
            hasMoved = true;
        }
        //Energy is enough so should produce offspring however not too much so only walk
        if(getEnergy() >= 90){
            MyCritter6 child = new MyCritter6();
            reproduce(child, Critter.getRandomInt(8));
            walk(dir);
            hasMoved = true;
        }
        //updated direction
        dir = Critter.getRandomInt(8);
        if(dir == 0)
            dir = 7; //adds to randomness of dir
    }

    @Override
    public String toString () {
        return "H";
    }

    public static void runStats(java.util.List<Critter> mycritter){
        int Fights = 0;
        for(Object obj : mycritter){
            HamzaCritter critter = (HamzaCritter) obj;
            Fights += critter.Fights_num;
        }
        System.out.println("Total number of HamzaCritter: " + mycritter.size());
        System.out.println("Total number of fights: " + Fights);
    }
}
