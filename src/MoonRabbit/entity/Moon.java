package MoonRabbit.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import MoonRabbit.canvas.EndingCanvas;

public class Moon extends Item {
	private Image img;
	private Canvas canvas;
	
	public Moon() {
		
	}

	public Moon(double x, double y,Canvas instance) {
		super(x, y);

		try {
			img = ImageIO.read(new File("resProj/Item/moon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		canvas = instance;
		
	}

	@Override
	public Image getImage() {
		return img;
	}

	@Override
	public void update() {
		
	}
	
	@Override
	public void paint(Graphics g) {
		int x = (int) getX();
		int y = (int) getY();
		int width = 100;
		int height = 100;
		Canvas observer = EndingCanvas.instance;
		
		g.drawImage(img, x, y, width, height, observer);
	}

}
