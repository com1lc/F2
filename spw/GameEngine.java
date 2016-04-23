package f4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener{
	GamePanel gp;
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private SpaceShip v;	
	private Timer timer;
	private double difficulty = 0.1;
	
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*390), 30);
		gp.sprites.add(e);
		enemies.add(e);
	}

	private void process(){
		
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed();
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
			}
		}
		
		Iterator<Bullet> b_iter = bullets.iterator();
		while(b_iter.hasNext()){
			Bullet b = b_iter.next();
			b.proceed();
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}
		
		gp.updateGameUI();
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double br;
		for(Enemy e : enemies){
			er = e.getRectangle();
			if(er.intersects(vr)){
				die();
				return;
			}
			for(Bullet b : bullets){
				br = b.getRectangle();
				if(br.intersects(er)){
					gp.sprites.remove(e);
					e.enemydie();
				}
			}
			
		}
		
	} //end process
	
	public void die(){
		timer.stop();
	}
	public void fire(){
		Bullet b = new Bullet((v.x) + (v.width/3), (v.y));
		gp.sprites.add(b);
		bullets.add(b);
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		
		case KeyEvent.VK_LEFT: 
				v.move(-1); 
				break;
								
		case KeyEvent.VK_RIGHT: 
				v.move(1); 
				break;
								
		case KeyEvent.VK_D: 
				difficulty += 0.1;
				break;	
				
		case KeyEvent.VK_SPACE:
				fire();
				break;
		}
	}
	

	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}	
}