package MoonRabbit.entity;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import MoonRabbit.canvas.StoryCanvas;
import MoonRabbit.canvas.StoryFightCanvas;
import MoonRabbit.interfaces.JumpListener;
import MoonRabbit.thread.JumpThread;

public class Bear extends Enemy implements JumpListener {
	// ------------------------------------ Attribute ------------------------------------
	private Image img;

	private int moveIdxX;
	private int moveIdxY;
	//===============================
	private Canvas canvas;
	private boolean entered = false;


	private Random rand;
	private int timeoutForAttack = 60;
	private int timeoutForStop = 60;
	private int timeOutForMoving=60;

	private int jumpdirection=0; 
	private int jumpIdx=0;
	private int pLength;
	private double crushPoint;

	private double cout;
	// ------------------------------------ Constructor ------------------------------------
	public Bear() {
		this(0, 0,null);
	}
	public Bear(double x, double y, Canvas instance) {
		super(x, y, instance);
		canvas=instance;
		try {
			img = ImageIO.read(new File("resProj/Character/bear.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		rand =new Random();
		moveIdxX = getImgMoveIndex_X();
		moveIdxY = getImgMoveIndex_Y();
	}
	// ------------------------------------ Functions ------------------------------------
	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return img;
	}
	@Override
	public void update() {
		setCrushPoint(getX()+pLength);
		if(canvas == StoryFightCanvas.instance) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					if (entered!=true) {	
						if (cout < getX() && getX() - 150 < cout) {
							if (cout + 50 < getX()) {
							pLength = 0;
								moveIdxY = 0;
								moveIdxX = 0;
								leftMove();

							}

						} else if (cout > getX() && getX() + 150 > cout) {
							if (cout - 50 > getX()) {
							pLength = 0;
								moveIdxY = 3;
								moveIdxX = 0;
								rightMove();

							}
						}
						timeOutForMoving--;
						if(timeOutForMoving == 0) {
							if(cout<getX()&&getX()-400<cout) {
								moveIdxY = 0;
								moveIdxX = 0;
								//								setImgMoveIndex_Y(0);
								jumpdirection=-1;
								new JumpThread((JumpListener)Bear.this).start();

							}
							else if(cout>getX()&&getX()+400>cout) {
								moveIdxY = 3;
								moveIdxX = 0;
								//								setImgMoveIndex_Y(3);
								jumpdirection=1;
								new JumpThread((JumpListener)Bear.this).start();

							}
							else {
								jumpdirection=0;
							}try {
								Thread.sleep(1000);

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							timeOutForMoving = 60;
						}
					}
					else if (entered) {	// ���� ���� �䳢 ���� ��, ���� �ð� ���� �� ����
						timeoutForAttack--;
						if (timeoutForAttack == 0) {
							attack();
							try {
								Thread.sleep(250);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(getDirection()==0) {
								pLength = 0;
								moveIdxY = 0;
							}else if(getDirection()==1) {
								pLength = 0;
								moveIdxY = 3;
							}
							timeoutForAttack = rand.nextInt(45)+20;
						}
					}
				}
			}).start();
		}
	}
	public void rightMove() {
		int xmove = (int) getX();
		xmove += 1;
		setImgMoveIndex_Y(3);

		int indexmovex = getImgMoveIndex_X();
		indexmovex++;
		setImgMoveIndex_X(indexmovex % 3);
		setX(xmove);

	}
	public void leftMove() {
		int xmove = (int) getX();
		xmove -= 1;
		setImgMoveIndex_Y(0);

		int indexmovex = getImgMoveIndex_X();
		indexmovex++;
		setImgMoveIndex_X(indexmovex % 3);
		setX(xmove);
	}
	@Override
	public void attack() {
		// �������� ����
		int randomAttack = rand.nextInt(10);
		randomAttack %= 2;

		int direction = getDirection();

		switch (randomAttack) {
		case 0:		// ���� : ��ġ 
			switch (direction) {
			case 0:		// �������� ���� ����
				pLength =-56;
				moveIdxY = 1;
				moveIdxX = 0;

				break;
			case 1:		// ���������� ���� ����
				pLength =56;
				moveIdxY = 4;
				moveIdxX = 0;
				break;
			}
			break;
		case 1:		// ���� : ������
			switch (direction) {
			case 0:
				pLength =-56;
				moveIdxY = 2;
				moveIdxX = 0;
				break;
			case 1:
				pLength =56;
				moveIdxY = 5;
				moveIdxX = 0;
				break;
			}
			break;
		}

	}
	@Override
	public void paint(Graphics g) {
		Canvas observer = StoryCanvas.instance;
		int x = (int) getX();
		int y = (int) getY();

		int w = (int) getWidth();
		int h = (int) getHeight();

		int yoffset = getImgMoveIndex_Y() * h;
		int xoffset =getImgMoveIndex_X()  * w;

		int dx1 = x-w/2;
		int dy1 = y;
		int dx2 = x + w/2;
		int dy2 = y + h;
		//View front in story canvas
		int face=0;
		if(canvas ==StoryCanvas.instance) {
			face = 2*h;
		}
		//=====================
		int sx1 = 0 + xoffset;
		int sy1 =  h*3 + yoffset-face;
		int sx2 = w + xoffset;
		int sy2 =  h*4 + yoffset-face;
		
		g.drawImage(img,
				dx1, dy1, dx2, dy2,
				sx1, sy1, sx2, sy2, observer);

		setImgMoveIndex_Y(moveIdxY);
	}


	public void addX(int add) {
		setX((int)getX()+add);

	}
	public void addY(int add) {
		setY((int)getY()+add);
	}
	@Override
	public void jumpTimeArrived(int jumpIdx, int jumpy) {
		addX(getJumpdirection()*10);
		addY(jumpy);
		setJumpIdx(jumpIdx);
	}

	@Override
	public void jumpTimeEnded() {
		setJumpIdx(0);
	}
	// ------------------------------------ Getters/Setters ------------------------------------

	public int getJumpdirection() {
		return jumpdirection;
	}

	public void setJumpdirection(int jumpdirection) {
		this.jumpdirection = jumpdirection;
	}

	public int getJumpIdx() {
		return jumpIdx;
	}

	public void setJumpIdx(int jumpIdx) {
		this.jumpIdx = jumpIdx;
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
	//�浹 ���� ����Ÿ get/set
	public double getCrushPoint() {
		return crushPoint;
	}

	public void setCrushPoint(double crushPoint) {
		this.crushPoint = crushPoint;
	}

}
