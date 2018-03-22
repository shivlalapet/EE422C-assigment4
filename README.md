
# EE422C-assigment4

Class
  1. Critter
  
    a. Fields
      - myPackage : String
      - population : List<Critter>
      - babies : List<Critter>
      - rand : Random
      - energy : int
      - x_coord : int
      - y_coord : int
      - hasMoved : boolean
      - isFighting : boolean
  
    b. Methods
      - getRandomInt(int) : int
      - setSeed(long) : void
      - toString : String
      - getEnergy : int
      - moveX(int) : int
      - moveY(int) : int
      - walk(int) : void
      - run(int) : void
      - reproduce(Critter,int) : void
      - doTimeStep : void
      - fight(String) : boolean
      - makeCritter(String) : void
      - getInstances(String) : List<Critter>
      - runStats(List<Critter>) : void
      - clearWorld : void
      - worldTimeStep : void
      - displayWorld : void
 
  2. Main
  
    a. Fields
      - kb : Scanner
      - inputFile : String
      - testOutputString : ByteArrayOutputStream
      - myPackage : String
      - DEBUG : boolean
      - old : PrintStream
    
    b. Methods
      - main(String[]) : void
      - runController(Scanner) : void
      - printInvalidCommand(String[]) : void
      - printError(String[]) : void
      
  3. ShivCritter1 & ShivCritter2
  
    a. Fields
      - direction : int
      - numFights : int
      - hasMoved : boolean
    
    b. Methods
      - ShivCritter1 : constructor
      - ShivCritter2 : constructor
      - doTimeStep : void
      - fight(String) : boolean
      - toString : String
      - runStats(List<Critter>) : void
   
  4. HamzaCritter1 & HamzaCritter2 
    
