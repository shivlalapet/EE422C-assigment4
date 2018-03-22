package assignment4;
import assignment4.Critter.TestCritter;

public class HamzaCritter1 extends TestCritter {
    private int direction;
    private int Fights_num;
    private boolean hasMoved;

    public HamzaCritter1(){
        direction = Critter.getRandomInt(8);
        hasMoved = false;
        Fights_num = 0;
    }


    @Override
    public boolean fight(String opponent) {
        if(opponent.equals("Algae"))
            return true;    //always fight and eat algae
        else{
            Fights_num++;
            return true;                // Always fight regardless of energy
        }
    }

    @Override
    public void doTimeStep() {
        hasMoved = false;

        //low on energy
        if(getEnergy() < 40){
            run(direction);
            hasMoved = true;
        }

        //a lot of energy so reproduce and also run
        if(getEnergy() >= 120){
            MyCritter6 child = new MyCritter6();
            reproduce(child, Critter.getRandomInt(8));
            run(direction);
            hasMoved = true;
        }
        //new direction
        direction = Critter.getRandomInt(8);
        if(direction == 0)
            direction = 7; //making direction more random
    }


    @Override
    public String toString () {
        return "#";
    }

    public static void runStats(java.util.List<Critter> mycritter){
        int Fights = 0;
        for(Object obj : mycritter){
            HamzaCritter1 critter = (HamzaCritter1) obj;
            Fights += critter.Fights_num;
        }
        System.out.println("Total number of HamzaCritter1: " + mycritter.size());
        System.out.println("Total number of fights: " + Fights);
    }
}
