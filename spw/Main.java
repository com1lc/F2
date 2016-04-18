package f4;

import javax.swing.*;		  
import javax.swing.*;
import java.awt.*;		  
import java.awt.*;
  		  
public class Main{
  	public static void main(String args[]){		
  		
  		SpaceShip v = new SpaceShip(180, 550, 20, 20);
		GamePanel gp = new GamePanel();
		GameEngine engine = new GameEngine(gp, v);	
		
	 	JFrame f = new JFrame("Space War");
	 	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		  		
		f.setSize(400,650);
		f.getContentPane().add(gp, BorderLayout.CENTER);
		f.setVisible(true);
		
		
		
		engine.start();
			
  	}		  	
  } 		  