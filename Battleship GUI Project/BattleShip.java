import java.util.*;
import java.io.*;

public class BattleShip extends GameModel{

 //attributes
   private int size;
   private int numButtonsInGrid;
   private int lengthShip1;
   private int lengthShip2;
   private int lengthShip3;
   private int numShipSquares;
   
   private ArrayList<Integer> playerShipSquares;  
    
   private ArrayList<Integer> computerShipSquares;
   private ArrayList<Integer> computerGuesses;
   
   private int playerScore;
   private int computerScore;
   private int numTurns;
   Random rand = new Random();
 

 
 
 //constructors
   BattleShip(int s){
      size = s;
      numButtonsInGrid = s*s;
      lengthShip1= 2;
      lengthShip2= 3;
      numShipSquares = (lengthShip1 + lengthShip2);
      playerScore = 0;
      computerScore = 0;
      numTurns = 0; 
      System.out.println("Hello");
      playerShipSquares = new ArrayList<Integer>();
      
      computerShipSquares = new ArrayList<Integer>();
      computerShipSquares.addAll(makeComputerShip(2));
      computerShipSquares.addAll(makeComputerShip(3));
      
      computerGuesses = new ArrayList<Integer>(); 
      System.out.println("did it");
           
   }
                  


 //functions
   public void takeTurn(int t1, int t2){
      numTurns ++;
   }
         
   public boolean gameOverStatus(){
      if (playerScore == numShipSquares|computerScore == numShipSquares){
         return(true);
      }
      else{
         return(false);
      }
   }
 
   public boolean checkIfHitShip(int c){
      ArrayList<Integer> otherShipSquares = computerShipSquares;
      if (numTurns%2 == 0){
         otherShipSquares = computerShipSquares;
      }
      else{
         otherShipSquares = playerShipSquares;
      } 
      boolean answer = false;
      for(int i = 0; i < numShipSquares; i++){
         if(otherShipSquares.get(i) == c){
            i = numShipSquares;
            answer = true;
            if (numTurns%2 == 0){
               playerScore ++;
            }
            else{
               computerScore ++;
            } 
         }
         else{
            answer = false;
         }
      }
      return(answer);
   }
   
   
   public ArrayList<Integer> makeComputerShip(int len){
      Random rand = new Random();
      int j;
      ArrayList<Integer> a = new ArrayList<Integer>();//individual ship array
      ArrayList<Integer> list = new ArrayList<Integer>();//random numbers generated so far
      for (int i = 0; i < len; i++){
         int ship = -1;
            //finding an appropriate random cell
         while(ship < 0){
            j = rand.nextInt(numButtonsInGrid - 1);
            if(list.contains(j) == false){
            list.add(j);
               if (i == 0){
                  if(computerShipSquares.contains(j)== false){
                     ship = j;}
               } 
               else{
                  int k = a.get(0);
                  int f = a.get(a.size()-1);
                  if (checkShipSpace(j,k,f) == true){
                  ship = j;
                  }

               }//if the value isnt the first
            }//if value hasnt already been randomized
         }//while ship Space hasn't been assigned
         a.add(ship);  
      }//do this for the length of the ship
      return(a);  
   }
   
   
   
   public boolean checkShipSpace(int space, int firstSpace, int lastSpace){ //In the set up, checks if the space is in line with other spaces in the ship
           
            boolean answer = false;
            if(computerShipSquares.contains(space)== false){
                  
                     if(space/size == firstSpace/size && firstSpace/size == lastSpace/size){           //if they are the same row
                        if(space%size == (firstSpace%size) + 1| space%size == (lastSpace%size) + 1){
                           answer = true;
                        }
                        else if(space%size == (firstSpace%size) -1|space%size == (lastSpace%size) -1){
                           answer = true;
                        }
                     }//if
                     
                     else if(space%size == firstSpace%size && space%size == lastSpace%size){       //if they are the same column 
                        if(space/size == (firstSpace/size) + 1| space/size ==(lastSpace/size) +1){
                           answer = true;
                        }
                        else if(space/size == (firstSpace/size) -1| space/size==(lastSpace/size)+1){
                           answer = true;
                        }
                     }//else if
                  
                  }// if the value isnt already in the array
            return(answer);
   }
   

   public void setShipSpace(int s){
      playerShipSquares.add(s);
      }
   
   public int getComputerGuess(){
      int guess = rand.nextInt(size*size - 1);
      while (computerGuesses.contains(guess)){
           guess = rand.nextInt(size*size - 1);  
            } 
      computerGuesses.add(guess);
   return(guess);
   }
   
   
   public String reportWinner(){
      if (playerScore == computerScore){
         return("Its a tie!");
      }
      else{
         if (playerScore > computerScore){
            return("You won!");
         }
         else{
            return("The enemy won!");
         }
      } 
   }//report Winner 
    
   public int get(int r, int c){
      return(1);
   } 
 
   public int getRows(){
      return(size);
   }
 
   public int getCols(){
      return(size);
   }
  
   public void display(){
   }
      
  

/*public boolean checkShipSetup(int p, int n){
return(true)
} get to later to prevent cheating*/ 

}
   
