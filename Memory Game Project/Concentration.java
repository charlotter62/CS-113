import java.util.*;
import java.io.*;

public class Concentration extends GameModel{
   
   //attributes
   public int numCards = 16;
   public int SIZE = (int)Math.sqrt(numCards);
   public int matches = 0;
   public int p1Matches = 0;
   public int p2Matches = 0;
   public int numTurn = 1;
   public boolean turnOverStatus = false;
   int choice = -1;//integer and function for determining turn
   int [] shown;
   
   //constructors
   Concentration(){
      numCards = 16;
   }
   
   Concentration(int SIZE){ 
      numCards = SIZE*SIZE;
   }

   //functions
   public int getRows(){
      return (SIZE);
   }
   
   public int getCols(){
      return (SIZE);
   }
      
   public boolean gameOverStatus(){
      if (matches == 8){
         return(true);}
      else{
         return(false);}
   }
   
   public void resetGame(){
   matches = 0;
   p1Matches = 0;
   p2Matches = 0;
   numTurn = 0;
   }
   
   public String reportWinner(){
      if (p1Matches > p2Matches){
         return("Player 1 wins!");
      }
      if (p2Matches > p1Matches){
         return("Player 2 wins!");
      }
      else{
         return("It's a tie! Everyone wins because everyone had fun.");
      }
   }
    
   public void display(){
   }
  
   public int get(int r, int c){
      int numInGrid = r*SIZE + c; 
      return(numInGrid); //number in backImages list that the card is
   }
   
   
  
   
   public void takeTurn(int firstCard, int secondCard){ // do we need the firstCard secondCard input??
      numTurn++;           
   }
   
   public boolean isMatch(int firstCard, int secondCard){
      if (firstCard < secondCard){
         if (firstCard + 8 == secondCard){
            matches ++;
            if (numTurn%2 == 0){
               p1Matches ++;}
            else{
               p2Matches ++;}
            return(true);
         }          
         else{
            return(false);
         }
      }
      
      else{
         if(firstCard - 8 == secondCard){
            matches ++;
            if (numTurn%2 == 0){
               p1Matches ++;}
            else{
               p2Matches ++;}
            return(true);
         }          
         else{
            return(false);
         }     
      }
   }
   
   public String reportMatch(boolean isMatch){
   if (isMatch){
   return("That is a match!");
   }
   else{
   return("That is not a match. Please click the cards again to flip them over.");
   }
   }
   
   
}
