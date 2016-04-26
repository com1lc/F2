package f4;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Item extends Sprite{

	private int step = 12;
	private boolean alive = true;
	
	public Item(int x, int y) {
		super(x, y, 25, 25);
		
	}

	@Override
	public void draw(Graphics2D g) {
		
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, width, height);
		
	}

	public void proceed(){
		y += step;
	}
	
	
	public boolean isAlive(){
		return alive;
	}
	public void itemdie(){
		alive = false;
	}
	
}