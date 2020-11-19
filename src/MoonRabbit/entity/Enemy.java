package MoonRabbit.entity;

import java.awt.Canvas;
import java.awt.Image;

public abstract class Enemy extends Animal{
	private Image img;
	
	private Canvas canvas;
	private boolean entered = false;
	private int moveIdxX;
	private int moveIdxY;

	private double crushPoint;
	
	private double cout;
	private int direction = 0;
	
	public Enemy() {
		this(0, 0);
	}
	
	public Enemy(double x, double y) {
		this(0,0,null);
	}
	public Enemy(double x, double y, Canvas instance) {
		super(x, y);
		canvas = instance;
		cout =0;
		img = getImage();

		setWidth(112);
		setHeight(129);
		
		moveIdxX = getImgMoveIndex_X();
		moveIdxY = getImgMoveIndex_Y();
		
		crushPoint =0;

	}
	public boolean isRabbitEntered(double rabbitX) {
		double w = getWidth();
		double enemyX1 = getX()- w/2;
		double enemyX2 = getX() + w/2;

		if(rabbitX < enemyX1 + w / 2+20)
			direction = 0;		// Enemy : Right
		else
			direction = 1;
		
		if(enemyX1 < rabbitX && rabbitX < enemyX2)
			return true;
		
		return false;
	}
	public boolean tigerIsCollision(double x) {
		return x>getX()-10 && x < getX()+10;
	}
	public void attack() {
	
	}

	public int getDirection() {
		return direction;
	}
	public double getCout() {
		return cout;
	}

	public void setCout(double cout) {
		this.cout = cout;
	}
	public boolean isEntered() {
		return entered;
	}
	public void setEntered(boolean entered) {
		this.entered = entered;
	}
	public double getCrushPoint() {
		return crushPoint;
	}

	public void setCrushPoint(double crushPoint) {
		this.crushPoint = crushPoint;
	}
}
