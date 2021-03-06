package f4;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{
	
	int step = 12;
	private boolean m_bullet = false;
	
	public SpaceShip(int x, int y, int width, int height){
		super(x, y, width, height);
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);
	}
	
	public boolean getMultiBullet(){
		return m_bullet;
	}
	public void stopMultiBullet(){
		m_bullet = false;
	}
	public void activeMultiBullet(){
		m_bullet = true;
	}
	
	public void move(int x_direction, int y_direction){
		x += (step * x_direction);
		if(x < 0) 
		   x = 0;
		if(x > 400 - width) 
		   x = 400 - width;
		
		y += (step * y_direction);
		if(y < 0) 
		   y = 0;
		if(y > 600 - height) 
		   y = 600 - height;
		
	}
	
	

}