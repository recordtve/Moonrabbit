package MoonRabbit.entity;

import java.awt.Graphics;
import java.awt.Image;

public abstract class Item {
	// ------------------------------------ Attribute ------------------------------------
			private Image img;
			private double width;
			private double height;
			
			private double x;
			private double y;
			private double dx;
			private double dy;
			private double vx;
			private double vy;
			
			private int imgMoveIndex_Y = 0;
			private int imgMoveIndex_X = 0;
			private int moveTempo = 2;
			private int speed = 1;
			// ------------------------------------ Constructor ------------------------------------
			public Item() {
				this(0, 0);
			}
			
			public Item(double x, double y) {
				img = getImage();
				
				this.x = x;
				this.y = y;
			}
			
			// ------------------------------------ Functions ------------------------------------
			public abstract Image getImage();
			
			public abstract void paint(Graphics g);
			
			public abstract void update();
			
			public void moveTo(double dx, double dy) {
				this.dx = dx;
				this.dy = dy;
				
				double w = this.dx  - this.x;
				double h = this.dy  - this.y;
				double d = Math.sqrt(w*w + h*h);
				
				this.vx = w / d * speed;
				this.vy = h / d * speed;
			}
			
			// ------------------------------------ Getters/Setters ------------------------------------
			public double getX() {
				return x;
			}

			public void setX(double x) {
				this.x = x;
			}

			public double getY() {
				return y;
			}

			public void setY(double y) {
				this.y = y;
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

			public double getDx() {
				return dx;
			}

			public void setDx(double dx) {
				this.dx = dx;
			}

			public double getDy() {
				return dy;
			}

			public void setDy(double dy) {
				this.dy = dy;
			}

			public double getVx() {
				return vx;
			}

			public void setVx(double vx) {
				this.vx = vx;
			}

			public double getVy() {
				return vy;
			}

			public void setVy(double vy) {
				this.vy = vy;
			}

			public int getImgMoveIndex_Y() {
				return imgMoveIndex_Y;
			}

			public void setImgMoveIndex_Y(int imgMoveIndex_Y) {
				this.imgMoveIndex_Y = imgMoveIndex_Y;
			}

			public int getImgMoveIndex_X() {
				return imgMoveIndex_X;
			}

			public void setImgMoveIndex_X(int imgMoveIndex_X) {
				this.imgMoveIndex_X = imgMoveIndex_X;
			}

			public int getMoveTempo() {
				return moveTempo;
			}

			public void setMoveTempo(int moveTempo) {
				this.moveTempo = moveTempo;
			}

			public int getSpeed() {
				return speed;
			}

			public void setSpeed(int speed) {
				this.speed = speed;
			}
			
	}