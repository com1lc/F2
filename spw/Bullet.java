package f4;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Bullet extends Sprite{
	
	private int step = 20; // SpeedOfShooting      
	
	public Bullet(int x, int y) {
		super(x, y, 10, 20);

	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
		
	}

	public void proceed(){
		y -= step;

	}
	
}
 
