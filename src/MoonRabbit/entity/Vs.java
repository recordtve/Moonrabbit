package MoonRabbit.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import MoonRabbit.canvas.StoryFightCanvas;

public class Vs extends Item{

	private double x;
	private double y;
	private double width;
	private double height;
	private static Image img;
	
	static {
		try {
			img = ImageIO.read(new File("resProj/Item/vs.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Vs() {
		this(0, 0);
	}

	public Vs(double x, double y) {
		this.x = x;
		this.y = y;
		
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
		
	}
	
	public void paint(Graphics g) {
		int x1= (int)x;
		int x2 = x1 +(int)width;
		int y1 = (int)y;
		int y2 = y1 + (int)height;
		
		int sx1 = 0;
		int sy1 = 0;
		int sx2 = sx1 + (int)width;
		int sy2 = sy1 + (int)height;
		
		
		
		g.drawImage(img,x1,y1,x2,y2,sx1,sy1,sx2,sy2, StoryFightCanvas.instance);
		
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
