package f4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;

public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private SpaceShip v;
	private double difficulty = 0.1;
	private long score = 0;	
	private int c_m_bullet = 0;
	
	private Timer timer;
	private Timer timer_multiBullet;
	
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
		
		timer_multiBullet = new Timer(400, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				multiFire();
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
	
	private void generateItem(){
		Item i = new Item((int)(Math.random()*390), 30);
		gp.sprites.add(i);
		items.add(i);
	}

	private void process(){
		if(v.getMultiBullet()){
			timer_multiBullet.start();
		}else{
			timer_multiBullet.stop();
		}
		
		if(c_m_bullet > 0){
			c_m_bullet--;
		}else{
			v.stopMultiBullet();
			gp.itemActive = false;
		}
		
		if(Math.random() < difficulty){
			generateEnemy();
		}
		
		if(Math.random() < difficulty/2){
			generateItem();
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
		
		Iterator<Item> i_iter = items.iterator();
		while(i_iter.hasNext()){
			Item i = i_iter.next();
			i.proceed();
			
			if(!i.isAlive()){
				i_iter.remove();
				gp.sprites.remove(i);
			}
		}
		//gp.updateGameUI(this);
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		Rectangle2D.Double br;
		Rectangle2D.Double ir;
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
					score += 10;
				}
			}	
			
		} //ending rule of enemy intersects
		for(Item i: items){
			ir = i.getRectangle();
			if(ir.intersects(vr)){
				gp.sprites.remove(i);
				gp.itemActive = true;
				i.itemdie();
				v.activeMultiBullet();
				c_m_bullet = 200;
				multiFire();
				score += 20;
			}
		}
		
		gp.updateGameUI(this);
		
	} //end process
	
	public void die(){
		timer.stop();
	}

	public void fire(){
		Bullet b = new Bullet((v.x) + (v.width/3), (v.y));
		gp.sprites.add(b);
		bullets.add(b);
	}
	
	private void multiFire(){
		Bullet a = new Bullet((v.x) + (v.width ), (v.y));
		Bullet b = new Bullet((v.x) + (v.width/2), (v.y));
		Bullet c = new Bullet((v.x), (v.y));
		Bullet d = new Bullet((v.x) - (v.width/2), (v.y));
		
		gp.sprites.add(a);
		gp.sprites.add(b);
		gp.sprites.add(c);
		gp.sprites.add(d);
		
		bullets.add(a);	
		bullets.add(b);
		bullets.add(c);
		bullets.add(d);
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT: 
					v.move(-1, 0); 
					break;
			case KeyEvent.VK_RIGHT: 
					v.move(1, 0); 
					break;
			case KeyEvent.VK_UP:
					v.move(0, -1);
			case KeyEvent.VK_DOWN:
					v.move(0, 1);
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
	public long getScore(){
		return score; 
	}
	@Override
	public long getItem(){
		return (int)(c_m_bullet/20);
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