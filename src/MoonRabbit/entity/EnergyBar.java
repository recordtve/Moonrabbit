package MoonRabbit.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import MoonRabbit.canvas.StoryFightCanvas;
import MoonRabbit.interfaces.EnergyListener;

public class EnergyBar {
	// ------------------------------------ Attribute ------------------------------------
	private Image img;
	private int width;
	private int height;
	private int x;
	private int y;
	private int z;
	private int cuts;
	private int enemyCuts;
	private EnergyListener listener;
	
	private boolean missileCut =false;
	// ------------------------------------ Constructor ------------------------------------
	public EnergyBar() {
		this(0,0,0);
	}
	
	public EnergyBar(int x, int y, int z) {
		Toolkit tk= Toolkit.getDefaultToolkit();
		img = tk.createImage("resProj/Item/Energy-Bar.png");
		this.x = x;
		this.y = y;
		this.z=z;
		this.width = 200;
		this.height =38;
		cuts= 0;
		enemyCuts=0;
	}
	// ------------------------------------ Functions ------------------------------------
	public void paint(Graphics g) {
		int dx1 = x+enemyCuts;
		int dy1 = y;
		int dx2 =x+width-cuts;
		int dy2 =y+height;
		int sx1 = 0+enemyCuts;
		int sy1 = z*height;
		int sx2 = width-cuts;
		int sy2  =sy1+ height;
		
		if(listener != null 
				&& ( dx2 <= 0 || dx1 >= 470))
			listener.onKnockdown();
		
		if(z == 2) {
			dx1 = x;
			dy1 = y;
			dx2 = dx1 + cuts;
			dy2 = dy1 + height;

			sx1 = 0;
			sy1 = z * height;
			sx2 = 0 + cuts;
			sy2 = sy1 + height;
		}
		
		g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2,StoryFightCanvas.instance);

	}
	public void cutBar() {
		cuts+=5;
		
		if(z == 2)
			cuts += 10;
	}
	public void enemyCutBar() {
		enemyCuts+=5;
		if(missileCut) {
			enemyCuts+=40;
		}
	}
	// ------------------------------------ Getters/Setters ------------------------------------
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	public int getCuts() {
		return cuts;
	}
	
	public void setCuts(int cuts) {
		this.cuts = cuts;
	}
	public void setListener(EnergyListener listener) {
		this.listener = listener;
	}
	public boolean isMissileCut() {
		return missileCut;
	}

	public void setMissileCut(boolean missileCut) {
		this.missileCut = missileCut;
	}
}
