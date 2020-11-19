package MoonRabbit.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {
	private Image img;
	// ------------------------------------ Attribute ------------------------------------
	private Canvas canvas;
	private double x;
	private double y;

	private int move = 0;

	// ------------------------------------ Constructor ------------------------------------
	public Background() {
		this(null, null);
	}

	public Background(String filename, Canvas instance) {
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		canvas = instance;
		
		this.x = 0;
		this.y = 0;
	}

	// ------------------------------------ Functions ------------------------------------
	public void paint(Graphics g) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		
		int x = (int) this.x;
		int y = (int) this.y;
		g.drawImage(img, x, y, width, height, canvas);
		
	}

	public void update() {
		
	}
	// ------------------------------------ Getters/Setters ------------------------------------
		public int getMove() {
			return move;
		}

		public void setMove(int move) {
			this.move = move;
		}
	
}
