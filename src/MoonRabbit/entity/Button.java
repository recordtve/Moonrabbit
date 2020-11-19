package MoonRabbit.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import MoonRabbit.canvas.IntroCanvas;
import MoonRabbit.interfaces.ManualListener;

public class Button {
	// ------------------------------------ Attribute ------------------------------------
	private Image img;
	private double x;
	private double y;
	private double width;
	private double height;
	private int select;

	// Interface
	private ManualListener listener;
	// ------------------------------------ Constructor ------------------------------------
	public Button() {
		this(0, 0, null);
	}

	public Button(double x, double y, String filename) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		img = tk.getImage(filename);

		this.x = x;
		this.y = y;

		width = 200;
		height = 56;
		select = 0;
	}

	// ------------------------------------ Functions ------------------------------------
	public void paint(Graphics g) {
		Canvas observer = IntroCanvas.instance;
		int w = (int) this.width;
		int h = (int) this.height;

		int x = (int) this.x;
		int y = (int) this.y;

		int dx1 = x;
		int dy1 = y;
		int dx2 = dx1 + w;
		int dy2 = dy1 + h;

		if(listener != null) {
			dx2 = dx1 + listener.onAdjustMWidth();
			dy2 = dy1 + listener.onAdjustMHeight();
		}

		int sx1 = 0+select;
		int sy1 = 0;
		int sx2 = w+select;
		int sy2 = h;

		g.drawImage(img,
				dx1, dy1, dx2, dy2,
				sx1, sy1, sx2, sy2, observer);
	}

	public boolean isSelected(int x, int y) {
		int x1 = (int) this.x;
		int y1 = (int) this.y;
		int x2 = x1 + (int) width ;
		int y2 = y1 + (int) height;

		// In ManualCanvas, Adjust Button Image's Width/Height
		if (listener != null) {
			x2 = x1 + listener.onAdjustMWidth();
			y2 = y1 + listener.onAdjustMHeight();
		}
		// Clicked Mouse Cursor Is Between Button's Start Point To End Point

		if ((x1 <= x && x < x2) && (y1 <= y && y <= y2))
			return true;

		return false;
	}
	public int getSelect() {
		return select;
	}
	
	public void setSelect(int select) {
		this.select = select;
	}
	
	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setListener(ManualListener listener) {
		this.listener = listener;
	}

}
