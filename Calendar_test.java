import javafx.scene.layout.*;
import javafx.scene.Group;
import java.time.LocalDateTime;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Calendar_test extends Application
{
   private BorderPane containerPane = new BorderPane();
   private LocalDateTime date = LocalDateTime.now();
   private String appointmentFile = "src/project4/appointmentFile.csv";
   private Scene scene;
   
   private StackPane mp [][] = new StackPane[7][7];
   
   private Text current;
   private FlowPane flowPaneY = new FlowPane();
   private FlowPane flowPaneB = new FlowPane();
   private HBox HBoxG = new HBox();

   private String [] allMonths = {"", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
   private String [] d = {"S","M","T","W","T","F","S"}; 
      
   boolean Twelve = false;

   //Use this stage if you decide to complete the extra credit
   
   private Stage appointmentStage = new Stage();
   private Scene appointmentScene;
   private VBox appointmentPane = new VBox(30);
   
   private Text app;

   @Override
   public void start(Stage primaryStage)
   {
      scene = new Scene(containerPane, 300, 100);
      containerPane.setStyle("-fx-background-color: whitesmoke;");
      setupTopPane();
      GridPane gp = setupMonthPane(date.getYear(), date.getMonthValue());
      containerPane.setCenter(gp);
        
      primaryStage.setTitle("Calendar");
      primaryStage.setMinWidth(600);
      primaryStage.setMinHeight(400);
      primaryStage.setScene(scene);
      primaryStage.show();
   
        
      //Use the following if you decide to complete the extra credit
     
      appointmentScene = new Scene(appointmentPane, 350, 250);
      setupAppointmentPane();
      appointmentStage.setTitle("Add Event");
      appointmentStage.setScene(appointmentScene);
     
   }
    
   public void setupTopPane()
   {
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS              
      containerPane.setTop(HBoxG);
      HBoxG.getChildren().addAll(flowPaneY, flowPaneB);      
   
      flowPaneY.setAlignment(Pos.CENTER_LEFT);
      flowPaneB.setAlignment(Pos.CENTER_RIGHT);
      
      flowPaneY.prefWidthProperty().bind(containerPane.widthProperty().divide(2)); 
      flowPaneB.prefWidthProperty().bind(containerPane.widthProperty().divide(2));
     
      flowPaneY.setPadding(new Insets(0,10,10,10));
      flowPaneB.setPadding(new Insets(0,10,10,10));
     
      current = new Text(" " + date.getMonth() + "   " + date.getYear());
      GridPane grid = new GridPane();
      grid.setHgap(10);
     
      Button left = new Button("<");
      Button year = new Button("Year");
      Button today = new Button("Today");
      Button right = new Button(">");
      grid.add(left, 0, 0);
      grid.add(year, 1, 0);     
      grid.add(today, 2, 0);
      grid.add(right, 3, 0);  
   
      flowPaneY.getChildren().add(current); 
      flowPaneB.getChildren().add(grid);
      left.setOnAction( 
         e ->
         {
            for (int i = 1; i < mp.length; i++)
            {
               for (int j = 0; j < mp[i].length; j++)
               {
                  if( mp[i][j].getChildren().size() == 1)
                     mp[i][j].getChildren().remove(0,1);
                  else if( mp[i][j].getChildren().size() == 2)
                     mp[i][j].getChildren().remove(0,2);
               }                      
            }
            date = date.minusMonths(1);
            current.setText(" " + allMonths[date.getMonthValue()] + "   " + date.getYear());
            GridPane mgp = new GridPane();
            fillUpMonth(mgp, date.getYear(), date.getMonthValue());
         }
         );
    
      today.setOnAction(
         e ->
         {          
            date = LocalDateTime.now();
            current.setText(" " + date.getMonth() + "   " + date.getYear());          
            containerPane.setCenter(setupMonthPane(date.getYear(), date.getMonthValue()));
         }
         ); 
     
      right.setOnAction( 
         e ->
         {
            for (int i = 1; i < mp.length; i++)
            {
               for (int j = 0; j < mp[i].length; j++)
               {
                  if( mp[i][j].getChildren().size() == 1)
                     mp[i][j].getChildren().remove(0,1);
                  else if( mp[i][j].getChildren().size() == 2)
                     mp[i][j].getChildren().remove(0,2);
               }                      
            }
            date = date.plusMonths(1);
            current.setText(" " + allMonths[date.getMonthValue()] + "   " + date.getYear());
            GridPane mgp = new GridPane();
            fillUpMonth(mgp, date.getYear(), date.getMonthValue());
         }
         );
    
      year.setOnAction( 
         e ->
         {
            Twelve = true;
            twelveMonthsPane();                  
            Twelve = false;
         }          
         );
   }
   
   public GridPane setupMonthPane(int yearValue, int monthValue)
   {
      GridPane monthPane = new GridPane(); 
      containerPane.setPadding(new Insets(15));
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
      for (int i = 0; i < 7; i++)
      {
         for (int j = 0; j < 7; j++)
         {  
            mp[i][j] = new StackPane();          
            if (i == 0)
            {              
               mp[i][j].setPrefSize(325,150);             
            }
            else
            {              
               mp[i][j].setPrefSize(325,225);
            }  
            if (Twelve != true) 
               mp[i][j].setStyle("-fx-border-color:black");
            
            monthPane.add(mp[i][j], j, i); 
         }
      }  
                          
      for (int i = 0; i < d.length; i++)
      {
         mp[0][i].getChildren().add(new Text(d[i]));  
      }
      
      fillUpMonth(monthPane, yearValue, monthValue);
      return monthPane;
   }
   
   public void fillUpMonth(GridPane monthGP, int yearValue, int monthValue)
   {           
      //Calendar calendar = Calendar.getInstance();
//       int firstOfMonth = 3;
//       calendar.set(yearValue, monthValue, firstOfMonth);
//       System.out.println(calendar.getTime(1)); 
               LocalDateTime forTwelve = LocalDateTime.now();   

      if (Twelve)
       {
        System.out.println(yearValue + " " + monthValue);
        forTwelve = forTwelve.withYear(yearValue);
        forTwelve = forTwelve.withMonth(monthValue);
        forTwelve = forTwelve.withDayOfMonth(1); 
        System.out.println(" im right over here!   " + forTwelve.getDayOfWeek()/*.getValue()*/);       
       }        
      
      Text random = new Text();
      Circle c = new Circle(10);
      c.setFill(Color.RED);
      int days = 0;
     
      if (monthValue == 1 || monthValue == 3 || monthValue == 5 || monthValue == 7 || monthValue == 8 || monthValue == 10 || monthValue == 12)
         days = 31;
      else if (monthValue == 4 || monthValue == 6 || monthValue == 9 || monthValue == 11)
         days = 30; 
      else if (isLeap(yearValue))
         days = 29;
      else
         days = 28; 
   
        
      LocalDateTime date2 = date.withDayOfMonth(1);
      LocalDateTime date3 = LocalDateTime.now();
      //date3 = date3.withMonth(monthValue);//
      //System.out.println(date2.getDayOfWeek().getValue());
      // if (Twelve)
//       date2 = date.withDayOfMonth(1);
//      i wanna use the monthValue and get the withDayOfMonth(1) so start will be a specific day.!!!!!!!!!
      boolean grey = false;
      int counter = 1;
       //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS

      for (int i = 1; i < mp.length; i++)
      {
         int start = 0;
         if( i == 1 )
            start = date2.getDayOfWeek().getValue(); // !!!!!!
         else if (Twelve)
          {
             start = forTwelve.getDayOfWeek().getValue();   
          }   
         for (int j = start; j < mp[i].length; j++)
         {          
              //mp[i][j] = new StackPane();                          
            if (date3.getDayOfMonth() == counter && date3.getMonthValue() == monthValue && date3.getYear() == yearValue)
               mp[i][j].getChildren().add(c);           
           
            if (counter == days+1)
            {
               counter = 1;
               grey = true;
            }               
           
            Text t = new Text(counter + "");
           
            if (grey)
               t.setOpacity(.5);
            else
            {
               StackPane st = mp[i][j];
               t.setOnMouseClicked( 
                  e ->
                  {                          
                   appointmentStage.show(); 
                   displayAppointments(st, app);           
                  }             
                  );                                                        
            } 
           
            mp[i][j].getChildren().add(t);
            counter++;           
         }           
      }                    
      monthValue--;      
      if (monthValue == 1 || monthValue == 3 || monthValue == 5 || monthValue == 7 || monthValue == 8 || monthValue == 10 || monthValue == 12)
         days = 31;
      else if (monthValue == 4 || monthValue == 6 || monthValue == 9 || monthValue == 11)
         days = 30; 
      else if (isLeap(yearValue))
         days = 29;
      else
         days = 28; 
   
      int start = date2.getDayOfWeek().getValue();
      for (int i = start-1; i >= 0; i--)
      {
         Text t = new Text(days + "");
         t.setOpacity(.5);
         mp[1][i].getChildren().add(t);
         days--;
      }  
   }
    
   public GridPane twelveMonthsPane()
   {
      GridPane twelve = new GridPane();
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
      current.setText(Integer.toString(date.getYear()));  
      containerPane.setCenter(twelve);
      twelve.setHgap(35);
      twelve.setVgap(35);
      int monthCounter = 1;
      for (int i = 0; i < 3; i++)
      {
         for (int j = 0; j < 4; j++)
         {                                   
            GridPane temporary = setupMonthPane(date.getYear(), monthCounter); //#2 prints every month starting with the same day. i think it might have to do with the fillUpMonth() method because the variable monthValue isnt used????????????????????????????????????             
            VBox indivudalMonth = new VBox(5);
            indivudalMonth.setAlignment(Pos.CENTER);
            indivudalMonth.getChildren().addAll(new Text(allMonths[monthCounter]), temporary);
            twelve.add(indivudalMonth, j, i);
            monthCounter++;
         }
      }     
      return twelve;
   }
 
   public boolean isLeap(int yearValue)
   {
      if ( (yearValue % 4 == 0) && (yearValue % 100 != 0) )
         return(true);    
      else if ( yearValue % 400 == 0 )
         return(true);
      else if ( (yearValue % 4 == 0) && (yearValue % 100 == 0) )
         return(false);
      else 
         return(false);
   }  
      
   //The following is for the extra credit
    
   public void setupAppointmentPane()
   {
        //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
      appointmentPane.setAlignment(Pos.CENTER);
      TextField thingToDo = new TextField();
      Label title = new Label("Title:", thingToDo);
      title.setContentDisplay(ContentDisplay.RIGHT); 
      //title.setPadding(new Insets(15));
      
      String [] hours = new String[24];
      String [] minutes = new String[60];
      String temp = "";
      for (int i = 0; i < 24; i++)
      {               
         if (i < 10)       
            temp += "0";
        
         temp += i+""; 
         hours[i] = temp;        
         temp = "";
      }
      for (int i = 0; i < 60; i++)
      {
         if (i < 10)       
            temp += "0";
        
         temp += i+""; 
         minutes[i] = temp;
         temp = "";
      }              
   
      ObservableList<String> hours2 = FXCollections.observableArrayList(hours); 
      ComboBox timeH = new ComboBox(hours2);
      
      ObservableList<String> minutes2 = FXCollections.observableArrayList(minutes);
      ComboBox timeM = new ComboBox(minutes2);
      
      timeH.setValue(date.getHour());
      timeM.setValue(date.getMinute());
      
      FlowPane flop = new FlowPane();
      flop.setHgap(15); 
      flop.setAlignment(Pos.CENTER);     
      Label time = new Label("Time:");
      time.setContentDisplay(ContentDisplay.RIGHT);
      flop.getChildren().addAll(time, timeH, timeM); 
      
      HBox clsb = new HBox(15);
      clsb.setAlignment(Pos.CENTER);
      Button cl = new Button("Clear");
      Button sb = new Button("Submit");
      clsb.getChildren().addAll(cl, sb);
      
      cl.setOnAction(
         e ->
         {    
            thingToDo.setText("");
            timeH.setValue(date.getHour());
            timeM.setValue(date.getMinute());
         }
         );
      sb.setOnAction( 
         e->
         {
            app = new Text("\n\n\n" + thingToDo.getText() + " " + timeH.getValue() + ":" + timeM.getValue());  
            appointmentStage.close(); 
               System.out.println("Submit clicked: " + app.getText());        

         }
         ); 
      
      appointmentPane.getChildren().addAll(title, flop, clsb);
                        
   }
      
   public void displayAppointments(StackPane dayoftheweek, Text ttt)
   {
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
      dayoftheweek.getChildren().add(ttt);
   
   
   }

   public void clear()
   {
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
      
      
      
   }

    
   public void storeAppointment(StackPane sp)
   {   
      //TO BE COMPLETED AS REQUIRED IN THE INSTRUCTIONS
    
   }

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args)
   {
      launch(args);
   }
}