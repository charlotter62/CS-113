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

public class BattleShipGame2E3 extends Application {
   
   Label Instructions;
   Font appfont = new Font("Plantagenet Cherokee",18);
   Button [] PlayerButtons;
   Button [] ComputerButtons;
   Button Ok;
   Button reset;
   GridPane PlayerGrid;
   GridPane ComputerGrid;
   BattleShip game1;
   int choiceCount = 0;
   int shipCount = 0;
   int selection;
   int length = 2;
   Random rand = new Random();
   int size = 5;

   public static void main(String[] args)
   {
      // Launch the application.
      launch(args);
   }

   @Override
   public void start(Stage stage){
      
      game1 = new BattleShip(size);
   
      Label Title = new Label("Battle Ship!");
      Title.setFont(appfont);
      
      Instructions = new Label("Welcome, click 2 spots in a row to set your 1st ship's position");
      Instructions.setFont(appfont);
            
      Label playerBoardLabel = new Label("Your Board");
      playerBoardLabel.setFont(appfont);
      Label computerBoardLabel = new Label("Enemy Board");
      computerBoardLabel.setFont(appfont);
       
      HBox boardLabels = new HBox(playerBoardLabel, computerBoardLabel);
      boardLabels.setSpacing(230);
      boardLabels.setAlignment(Pos.CENTER);
      
      Ok = new Button("Ok");
      Ok.setFont(new Font("Plantagenet Cherokee",16));
      Ok.setOnAction(new OkHandler());
      
      reset = new Button("New Game");
      reset.setFont(new Font("Plantagenet Cherokee",16));
      reset.setOnAction(new resetHandler());
      
      VBox bottomButtons = new VBox(Ok, reset);
      bottomButtons.setAlignment(Pos.CENTER);
      bottomButtons.setSpacing(20);
   
      PlayerGrid = new GridPane();
      ComputerGrid = new GridPane();
   
      
      PlayerButtons = new Button[size*size];
      for (int i=0; i<(size*size); i++){
         PlayerButtons[i] = makeButton(new Image("File:water.jpeg"));
         PlayerGrid.add(PlayerButtons[i],i%size, i/size);
         PlayerButtons[i].setOnAction(new PlayerBoardHandler());
      }
   
      ComputerButtons = new Button[size*size];
      for (int i=0; i<(size*size); i++){
         ComputerButtons[i] = makeButton(new Image("File:water.jpeg"));
         ComputerGrid.add(ComputerButtons[i],i%size, i/size);
      }
      
          
      HBox TwoBoards = new HBox(PlayerGrid, ComputerGrid);
      TwoBoards.setAlignment(Pos.CENTER);
      TwoBoards.setSpacing(20);
   
      VBox vbox = new VBox(Title, Instructions,TwoBoards, boardLabels, bottomButtons);
      vbox.setAlignment(Pos.CENTER);
      vbox.setSpacing(20);
   
      Scene scene = new Scene(vbox,850,600);      
      stage.setScene(scene);    
      stage.setTitle("Battle Ship");
      stage.show();  
   }
   
   Button makeButton(Image img){
       // create a button with an image on it
      ImageView iView = new ImageView(img);
      iView.setFitWidth(40); 
      iView.setFitHeight(40);
      
      Button newButton =  new Button("",iView);
      return(newButton); 
   }
   
   void guiSetShipSpace(int s){
      int row = s/size; 
      int col = s%size;
      PlayerButtons[s] = makeButton(new Image("File:ship.jpeg"));
      PlayerGrid.add(PlayerButtons[s], col, row); 
   }
   
  
   class PlayerBoardHandler implements EventHandler<ActionEvent>{
      @Override
      public void handle(ActionEvent event){
      
         if(shipCount < 2){
         
         //get what was clicked...
            for(int i=0;i<(size*size);i++){
               if(event.getSource().equals(PlayerButtons[i])){
                  selection = i;
               }  
            } 
         
         //do stuff with it...       
         
            if (choiceCount == 0){             //first ship space
               game1.setShipSpace(selection);
               guiSetShipSpace(selection);
               choiceCount++;
            } 
            
            else if(choiceCount < length -1){ //middle ship spaces
               game1.setShipSpace(selection);
               guiSetShipSpace(selection);
               choiceCount++;
            }
            
            else{                             //last ship space of ship
               game1.setShipSpace(selection);
               guiSetShipSpace(selection);
               shipCount++;
               choiceCount = 0;
               if (shipCount < 2){
                  if (shipCount ==1){
                     length = 3;
                  }  
                  Instructions.setText("Ship number " + shipCount + " is set! Click " + length + " boxes in a row to set ship " + (shipCount+1));
               }
               
               else{
                  Instructions.setText("All your ships are set. Click on the enemy board to fire!");
                  for(int i=0; i< size*size; i++){
                     PlayerButtons[i].setOnAction(null);
                     ComputerButtons[i].setOnAction(new ComputerBoardHandler());
                  }
               }
            }
         
         }//if shipCount
      
      } 
   }//player handler
  
      
   

      
   class ComputerBoardHandler implements EventHandler<ActionEvent>{
      @Override
      public void handle(ActionEvent event){
      
         
         for(int i=0;i<size*size;i++){
            if(event.getSource().equals(ComputerButtons[i])){
               int row = i/size; 
               int col = i%size;
               if(game1.checkIfHitShip(i)){
                  ComputerButtons[i] = makeButton(new Image("File:bomb.jpeg"));
                  Instructions.setText("Nice! You hit a ship! Click 'Ok' to see where the enemy fired");
               }
               else{
                  ComputerButtons[i] = makeButton(new Image("File:miss.jpeg"));
                  Instructions.setText("Missed! Click 'Ok' to see where the enemy fired");
               }
               ComputerGrid.add(ComputerButtons[i], col, row);
               game1.takeTurn(col, row);
               
               for (int j =0; j <(size*size); j++){ //disable until player clicks ok
                    ComputerButtons[j].setOnAction(null);
               }
               Ok.setOnAction(new OkHandler());
                                   
            }
         }
            
       //where the computer turn used to be                                   
      }          
   }
   
   class OkHandler implements EventHandler<ActionEvent>{
      @Override
       public void handle(ActionEvent event){
       
         if(game1.gameOverStatus()==false){
         
            System.out.println("hello");
            Random rand = new Random();
            int i = game1.getComputerGuess();      
            int row = i/size; 
            int col = i%size;
            if(game1.checkIfHitShip(i)){
               PlayerButtons[i] = makeButton(new Image("File:bomb.jpeg"));
               Instructions.setText("The enemy hit your ship!");
            }
            else{
               PlayerButtons[i] = makeButton(new Image("File:miss.jpeg"));
               Instructions.setText("The enemy missed!");
            }
            PlayerGrid.add(PlayerButtons[i], col, row);
            game1.takeTurn(col, row);
            Ok.setOnAction(null);//disable until player clicks enemy/computer board
            for (int j =0; j <(size*size); j++){ //disable until player clicks ok
                ComputerButtons[j].setOnAction(new ComputerBoardHandler());
                }

         }
         else{      
            Instructions.setText(game1.reportWinner());
            for (int j =0; j <(size*size); j++){
               ComputerButtons[j].setOnAction(null);
               PlayerButtons[j].setOnAction(null);
               //Ok.setOnAction(null);
            }
         }//else   
       
      }
   }//Ok handler
   
   class resetHandler implements EventHandler<ActionEvent>{
   @Override
       public void handle(ActionEvent event){
       choiceCount = 0;
       shipCount = 0;
       length = 2;
       size = 5;
       game1 = new BattleShip(size); 
       
       for (int i=0; i<(size*size); i++){
         PlayerButtons[i] = makeButton(new Image("File:water.jpeg"));
         PlayerGrid.add(PlayerButtons[i],i%size, i/size);
         PlayerButtons[i].setOnAction(new PlayerBoardHandler());
         
         ComputerButtons[i] = makeButton(new Image("File:water.jpeg"));
         ComputerGrid.add(ComputerButtons[i],i%size, i/size);  
       }
       
       Instructions.setText("Welcome, click 2 spots to set your 1st ship's position");
       
      }
   }//resetHandler
   
   
}
