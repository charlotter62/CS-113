import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color; 
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.util.*; 

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



//Project 2 Front end (works without the game part)

public class FrontEndP2G extends Application {

   //Font appFont = new Font("Times New Roman",36);
   Label Welcome;
   Label Instructions;
   Label playerMsgs;
   Label player1Score;
   Label player2Score;
   final int SIZE = 4; 
   Button [] buttons;    
   GridPane grid; 
   VBox vbox; 
   Image frontImage = new Image("file:Anenome.png");
   Image [] backImages; 
   Concentration cg;
   Font appFont = new Font("Bangla MN",12);
   int choice1 = -1;
   int choice1ButtonPlace;
   int choice2 = -1;
   int choice2ButtonPlace;
   int[] hiddenShuffled = shuffle();
   
            
   public static void main(String[] args)
   {
      // Launch the application.
      launch(args);
   }

   @Override
   public void start(Stage stage)
   {//s
      cg = new Concentration(SIZE);
      backImages = new Image[(SIZE*SIZE)];
      for (int i = 0; i<(SIZE*SIZE); i++){
         int numImage;
         if (hiddenShuffled[i] < 8){
            numImage = hiddenShuffled[i];
         }
         else{
            numImage = hiddenShuffled[i]-8;
         }
         backImages[i] = new Image("file:background"+ numImage + ".jpg");   
      }
      
      
      // make Buttons list with front image
      buttons = new Button[SIZE*SIZE];
      for (int i=0; i<(SIZE*SIZE); i++)
         buttons[i] = makeButton(frontImage);     
    
      grid = new GridPane();
      grid.setAlignment(Pos.CENTER); 
      grid.setVgap(10);
       
      for(int i =0;i<(SIZE*SIZE);i++){
         grid.add(buttons[i],i%SIZE,i/SIZE);
         // Register the event handler.
         buttons[i].setOnAction(new ButtonHandler());
      }
          
      // Create Labels 
      Label Welcome = new Label("Nemo Memory Game");
      Font HeadingFont = new Font("Bangla MN",20);
      Welcome.setFont(HeadingFont);
      
      Instructions = new Label("Player 1, Click 2 cards to look for a match!");  
      
      Label space1 = new Label(" ");
      Label space2 = new Label(" "); 
      playerMsgs = new Label("");   
      player1Score = new Label("Player 1 Matches: 0"); 
      player1Score.setFont(appFont);
      player2Score = new Label("Player 2 Matches: 0");
      player2Score.setFont(appFont);
      Button reset = new Button("Reset");
      
      HBox playerScores = new HBox(80);
      playerScores.getChildren().addAll(player1Score, player2Score);
      playerScores.setAlignment(Pos.CENTER);
      
      
      VBox vbox = new VBox(Welcome,Instructions,playerMsgs,space1,grid,space2,playerScores);
      vbox.setAlignment(Pos.CENTER);
      //vbox.setSpacing(8);
     
      // Create a Scene with the GridPane as its root node.
      Scene scene = new Scene(vbox,800,1200);      
      // Add the Scene to the Stage.
      stage.setScene(scene);    
      // Set the stage title.
      stage.setTitle("Memory Game ");
      // Show the window.
      stage.show();
   }

   Button makeButton(Image img){
       // create a button with an image on it
      // Create an ImageView component to decorate Button
      ImageView iView = new ImageView(img);
      iView.setFitWidth(100); 
      iView.setFitHeight(100);
      
      Button newButton =  new Button("",iView);
      return(newButton); 
   }

   public int[] shuffle(){
      int[] array = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
      Random rgen = new Random();  			
      for (int i=0; i<array.length; i++) {
         int randomPosition = rgen.nextInt(array.length);
         int temp = array[i];
         array[i] = array[randomPosition];
         array[randomPosition] = temp;
      }
      return array;
   }
      
   class ButtonHandler implements EventHandler<ActionEvent>{
      @Override
      public void handle(ActionEvent event){
               
         
         if(choice1 == -1){ 
               
            for(int i=0;i<(SIZE*SIZE);i++){
               if(event.getSource().equals(buttons[i])){
                   //get rows, columns
                  int row = i/SIZE; 
                  int col = i%SIZE;
                  choice1 = hiddenShuffled[cg.get(row,col)];  
                  choice1ButtonPlace = i;  
                  //flip card image to that one
                  buttons[i] = makeButton(backImages[i]);
                  grid.add(buttons[i], col, row);
                  buttons[i].setOnAction(new ButtonHandler2());          
               }
            }
         } 
            
         else {
            if (choice1 != -1){
               
               for(int i=0;i<(SIZE*SIZE);i++){
                  if(event.getSource().equals(buttons[i])){
                     //get rows, columns
                     int row = i/SIZE; 
                     int col = i%SIZE;
                     choice2 = hiddenShuffled[cg.get(row,col)];
                     choice2ButtonPlace = i;                
                     //flip card image to that one
                     buttons[i] = makeButton(backImages[i]);
                     grid.add(buttons[i], col, row); 
                     buttons[i].setOnAction(new ButtonHandler2());         
                  }
               }
               cg.takeTurn(choice1,choice2);
               
               boolean isItMatch = cg.isMatch(choice1,choice2);
               if(!isItMatch){
                  buttons[choice1ButtonPlace].setOnAction(new ButtonHandler2());
                  buttons[choice2ButtonPlace].setOnAction(new ButtonHandler2());
               }
               else{
                  buttons[choice1ButtonPlace].setOnAction(null);
                  buttons[choice2ButtonPlace].setOnAction(null); 
               }
               
               String playerMessages = cg.reportMatch(isItMatch); 
               playerMsgs.setText(playerMessages);
               playerMsgs.setFont(appFont);
               
               player1Score.setText("Player 1 Matches: " + cg.p1Matches);
               player2Score.setText("Player 2 Matches: " + cg.p2Matches);
               
               choice1 = -1;
               choice2 = -1;
                  
               String Player;
               if (cg.numTurn%2 ==0)
                  Player = "Player 2";
               else Player = "Player 1";
               Instructions.setText(Player + ", click 2 cards to look for a match!");
               Instructions.setFont(appFont);
                  
               if (cg.gameOverStatus()){ //tested & if you get all the pairs it will report it was a tie...we cant finish w/out cards flipping over
                  playerMsgs.setText(cg.reportWinner());
                  cg.resetGame(); 
               } 
            }
         }
         
              
      }//handle ActionEvent
         
   }//class button handler
   
   class ButtonHandler2 implements EventHandler<ActionEvent>{
      @Override
      public void handle(ActionEvent event){
         for(int i=0;i<(SIZE*SIZE);i++){
            if(event.getSource().equals(buttons[i])){
               int row = i/SIZE; 
               int col = i%SIZE;
            
               buttons[i] = makeButton(frontImage);
               grid.add(buttons[i], col, row);
               buttons[i].setOnAction(new ButtonHandler());
               
            }
         }// for 
      }//handle actionEvent
   }//class button handler2

}//class frontend2

//https://stackoverflow.com/questions/13227809/displaying-changing-values-in-javafx-label