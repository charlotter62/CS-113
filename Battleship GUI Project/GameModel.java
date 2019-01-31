import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  


abstract class GameModel{

   abstract void takeTurn(int t1, int t2);        
   abstract boolean gameOverStatus();
   abstract int get(int r, int c); 
   abstract int getRows();
   abstract int getCols(); 
   abstract void display();      
   abstract String reportWinner();   
   
}// class