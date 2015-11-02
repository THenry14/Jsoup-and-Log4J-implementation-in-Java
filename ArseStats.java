/*
Wojciech Szymczyk

Simple java program showing an implementation of jsoup parser and log4j logger
*/

import java.io.IOException;                                                 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;

public class ArseStats extends JFrame{                                      

    static Logger log = Logger.getLogger(ArseStats.class);                  //Logger initialization
    
public ArseStats() {                                   
    
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                         //this one ensures that program stoppes when I close it
    setSize(600, 750);                                                      

    Button0 = new JButton("EPL Table");                                     
    Button1 = new JButton("The 14/15 Squad");
    Button2 = new JButton("Some Arsenal Chants");

    t = new JTextArea(40, 40);                                              //Fixing text area, 
    t.setEditable(false);                                                   //non writable from the user's view
    t.setFont(new Font("Tahoma", Font.PLAIN, 11));                          
    MyPanel = new JPanel();                                                 
    display = new JPanel();
    t.setLayout(new GridLayout());                                          
    display.add(t);                                                         
    MyPanel.add(Button0);
    MyPanel.add(Button1);
    MyPanel.add(Button2);                  
    
    add(display, BorderLayout.NORTH);                                       
    add(MyPanel, BorderLayout.SOUTH);

    BasicConfigurator.configure();                                          //Configuration of Logger
    log.info("Entered to application");                                     //First log, says that we enter the application
  
    Button0.addActionListener(new ActionListener() {                        
        public void actionPerformed(ActionEvent event) {
            Button0pressed(event);
        }
    });

    Button1.addActionListener(new ActionListener() {         
        public void actionPerformed(ActionEvent event) {
            Button1pressed(event);
        }
    });

    Button2.addActionListener(new ActionListener() {            
        public void actionPerformed(ActionEvent event) {
            Button2pressed(event);
        }
    });
    }

    void Button0pressed(ActionEvent event) {                                //the action which takes place after hitting button
        
         try {
            t.setText(" ");                                                 
            Document doc = Jsoup.connect("http://www.arsenal.com").get();   
            String title = doc.title();                                     
            
            t.setText("Stats from " + title + "\n \n \n");                  
            t.setText(t.getText() + "Pos " + "Team " + "    P " + "    W " + "    D " + "   L " + "   F " + "    A " + "   +/- " + "   Pts " + "\n\n"); 
                                                                            //Column names
            doc = Jsoup.connect("http://www.arsenal.com/fixtures/first-team/tables?season=2014%2F15").get();    
                                                                            
            for (Element table : doc.select("table")){                      
                for (Element row : table.select("tr")){
                    Elements tds = row.select("td");
                    if (tds.size() > 19){
                        for(int i=0; i<3; i++){
                        t.setText(t.getText() + tds.get(i).text() + " | ");}
                        for(int i=13; i<20; i++){
                        t.setText(t.getText() + tds.get(i).text() + " | ");   
                        
                            if(i==19){
                                t.setText(t.getText() + "\n\n");            //I wanted to omit some pat of the table cuz it was useless for me, hence the two for loops above
                            }                                               
                        }
                    }
                }
            }
            log.info("User checked EPL Table.");                            //information for logger
        }
        
       catch (IOException a) {                                              //when not able to connect to the website, exception is being thrown
        a.printStackTrace();
        log.error("No internet connection or website does nor exist [**ERROR**]");  
        } 
    } 
    void Button1pressed(ActionEvent event) {

            t.setText(" ");
            Properties players = new Properties();
            Set positions;
            String str;                                                     

            players.put("Goalkeepers", "* Wojciech Szczesny,\n * David Ospina,\n * Emiliano Martinez");
            players.put("Centrebacks", "* Laurent Koscielny,\n * Per Mertesacker,\n * Gabriel,\n * Calum Chambers,\n * Nacho Monreal,\n * Mathieu Debuchy,\n * Kieran Gibbs,\n * Hector Bellerin");
            players.put("Midfielders", "* Mesut Ozil,\n * Tomas Rosicky,\n * Santiago Cazorla,\n * Alex Oxlade-Chamberlain,\n * Aaron Ramsey,\n * Jack Wilshere,\n * Mikel Arteta,\n * Francis Coquelin,\n * Mathieu Flamini,\n * Krystian Bielik");
            players.put("Forwards", "* Alexis Sanchez,\n * Olivier Giroud,\n * Theo Walcott,\n * Danny Welbeck,\n * Serge Gnabry");
            
                                                                            //above, properties pairs - keys and values

            positions = players.keySet();                                   //save the key into variable
            Iterator itr = positions.iterator();                            //iterate over each key
            while(itr.hasNext()) {                                          //if iterator can find another key which hasn't been found previously
                str = (String) itr.next();                                  //store this key
                t.setText(t.getText() + "The " + str + " are: \n \n " + players.getProperty(str) + "." + " \n \n");     
                                                                            //and prints it alongside its values
                
            }
            log.info("User checked The current Arsenal Squad.");            //info to logger
    } 
    

    void Button2pressed(ActionEvent event) {

        t.setText(" ");
        t.setText(" This one is just a decoy, it is for other logging purposes. ");
        log.info("A sample INFO log, *used before*");
        log.trace("A sample TRACE log");
        log.debug("A sample DEBUG log");
        log.error("A sample ERROR log, *used before*");
        log.fatal("A sample FATAL log");

        //this one's used only for logging purposes, just to show utilization of some logging methods like Fatal, Trace etc

    }

  public static void main(String[] args) {                                  

    new ArseStats().setVisible(true);                                       
    }

    JButton Button0;                                                        
    JButton Button1;
    JButton Button2;
    JPanel display;
    JPanel MyPanel;
    JTextArea t;

}