package f4;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	public boolean itemActive = false;
	private BufferedImage bi;	
	Graphics2D big;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(400, 650, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.BLACK);
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 400, 650);
		
		big.setColor(Color.WHITE);
		big.setFont(new Font("Corier", Font.BOLD, 12));
		big.drawString(String.format("%08d", reporter.getScore()), 300, 20);
		if(itemActive){	
			big.setColor(Color.ORANGE);
	        big.setFont(new Font("Courier", Font.BOLD, 12));
	        big.drawString(String.format(" MultiBullet %02d", reporter.getItem()), 0, 35);
		}
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}
}