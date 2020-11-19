package MoonRabbit.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import MoonRabbit.canvas.StoryFightCanvas;

public class Result {

	private double x;
	private double y;
	private int width;
	private int height;
	private static Image img;
	private int z;

	static {
		try {
			img = ImageIO.read(new File("resProj/Item/result.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Result() {
		this(0, 0, 0);
	}

	public Result(double x, double y, int z) {

		this.x = x;
		this.y = y;
		this.z = z;

		width = 793;
		height = 390;
		

	}

	public void paint(Graphics g) {

		int w = StoryFightCanvas.instance.getWidth();
		int h = StoryFightCanvas.instance.getHeight();
		
		int dx1 = (int) x;
		int dy1 = (int) y;
		int dx2 = dx1 + w/2;
		int dy2 = dy1 + h/2;
		
		int sx1=z*width/2;
		int sy1=0;
		int sx2 = sx1 + width/2;
		int sy2 = sy1 + h;

		g.drawImage(img, dx1, dy1, dx2, dy2, sx1,sy1,sx2,sy2, StoryFightCanvas.instance);

	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}
	
	

}
