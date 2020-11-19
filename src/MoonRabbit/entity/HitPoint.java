package MoonRabbit.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import MoonRabbit.canvas.StoryFightCanvas;

public class HitPoint extends Item {
	private Image img;
	
	public HitPoint() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		img = tk.getImage("res/Character/hp.png");
	}

	public void update() {
		
	}
	@Override
	public void paint(Graphics g) {
		Canvas observer = StoryFightCanvas.instance;
		
		int x =50;
		int y =50;
		int width=100;
		int height=100;
		
		g.drawImage(img,x, y, width, height, observer);
	}

	@Override
	public Image getImage() {
		return img;
	}

	
	
}