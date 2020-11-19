package MoonRabbit.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MovBackground {
	// ------------------------------------ Attribute ------------------------------------
	private Image img;
	private Canvas canvas;
	private double x;
	private double y;

	private int move = 0;
	
	// ------------------------------------ Constructor ------------------------------------
	public MovBackground() {
		this(null, null);
	}

	public MovBackground(String filename, Canvas instance) {
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		canvas = instance;
		
		this.x = 0;
		this.y =  0;
	}

	// ------------------------------------ Functions ------------------------------------
	public void update() {
		
	}
	
	public void paint(Graphics g) {
		int width = canvas.getWidth();
		int height = img.getHeight(null);

		double scrollY = img.getHeight(null) - canvas.getHeight();
		
		int x = (int) this.x;
		int y = (int) (- scrollY + move + this.y);
		
		g.drawImage(img, x, y, width, height, canvas);
	}

	// ------------------------------------ Getters/Setters ------------------------------------
	

	public int getMove() {
		return move;
	}
	
	public void setMove(int move) {
		this.move = move;
	}
	
}
