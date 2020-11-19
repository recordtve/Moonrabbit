package MoonRabbit.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import MoonRabbit.GameFrame;
import MoonRabbit.entity.Animal;
import MoonRabbit.entity.Background;
import MoonRabbit.entity.Bear;
import MoonRabbit.entity.Button;
import MoonRabbit.entity.Enemy;
import MoonRabbit.entity.EnergyBar;
import MoonRabbit.entity.Item;
import MoonRabbit.entity.Missile;
import MoonRabbit.entity.Music;
import MoonRabbit.entity.Rabbit;
import MoonRabbit.entity.Result;
import MoonRabbit.entity.Tiger;
import MoonRabbit.entity.Vs;
import MoonRabbit.entity.Wolf;
import MoonRabbit.file.SaveFile;
import MoonRabbit.interfaces.AttackListener;
import MoonRabbit.interfaces.ManualListener;
import MoonRabbit.interfaces.MissileListener;
import MoonRabbit.interfaces.RabbitKeyListener;

public class StoryFightCanvas extends Canvas{
	public static Canvas instance;

	// ------------------------------------ Attribute ------------------------------------
	private Music music;

	// Background
	private Background background;
	private Background blackBg;
	// Item : Animal, EnergyBar, Missile
	private Item[] items;
	private int itemSize;

	// Before Starting fight
	private int gameCount;
	private Background[] before;
	private int beforeSize;
	private Background num1;
	private Background num2;
	private Background num3;
	private Background start;

	// Aniaml
	private Animal[] animals;
	private int animalSize;

	private Rabbit rabbit;
	private Wolf wolf;
	private Bear bear;
	private Tiger tiger;
	private Enemy enemy;

	// EnergyBar : HP(Hit Point)
	private EnergyBar[] bars;
	private EnergyBar rabbitBar;
	private EnergyBar enemyBar;
	private EnergyBar barCTRL;

	// Rabbit's Carrot Missile
	private Missile[] missiles;
	private Missile missile;
	private int missileSize;

	private Vs vs;

	//占썸돌
	private boolean isCollision;
	private boolean tigerIsCollision;
	private boolean missilelsCollision;

	private int timeOutForAttect=10;

	// Run Sub Thread
	private boolean running = true;
	// Game Result
	private Result[] results;
	private int resultSize = 0;
	private Button resultBackButton;

	private boolean rabbitKnockdown = false;
	private boolean enemyKnockdown = false;
	private boolean gameResult = false;
	private boolean rabbitWin = false;

	private int gameConut = 0;
	private Background[] starts;
	// ------------------------------------ Constructor ------------------------------------
	public StoryFightCanvas() {
		instance = this;
		this.setSize(500, 500);
		this.setBackground(Color.pink);
		music = new Music("resProj/Sound/Battle.wav","resProj/Sound/Attack.wav");

		isCollision = false;
		tigerIsCollision = false;

		background = new Background("resProj/Background/fight.png", instance);
		blackBg = new Background("resProj/Background/setbackground.png", instance);
		// Item : Animal, EnergyBar, Missile
		items = new Item[100];
		itemSize =0; 
		// Before Starting fight
		starts = new Background[4];
		starts[0] = new Background("resProj/Background/three.png", instance);
		starts[1] = new Background("resProj/Background/two.png", instance);
		starts[2] = new Background("resProj/Background/one.png", instance);
		starts[3] = new Background("resProj/Background/start.png", instance);
		//		before [3] = start = new Background("resProj/Background/Start.jpg",instance);


		bars = new EnergyBar[4];
		bars[0] = new EnergyBar(10,10,0);
		bars[1] = new EnergyBar(10,10,1);
		bars[2] = new EnergyBar(270,10,0);
		bars[3] = new EnergyBar(270,10,1);
		rabbitBar=bars[1];
		enemyBar = bars[3];


		// Ctrl Bar
		barCTRL = new EnergyBar(10, 50, 2);

		//vs
		items[itemSize++] =vs = new Vs(200,0);
		//Animal

		items[itemSize++] =rabbit=new Rabbit(150, 275,instance);

		int datas[];
		int currentEnemy = 0;
		try {
			datas = new SaveFile().loadFile();
			currentEnemy = datas[6];
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		switch(currentEnemy) {
		case 0:
			wolf = new Wolf(350, 240, instance);
			items[itemSize++] = enemy = wolf;
			break;
		case 1:
			bear = new Bear(350, 240, instance);
			items[itemSize++] = enemy = bear;
			break;
		case 2:
			tiger = new Tiger(350, 240, instance);
			items[itemSize++] = enemy = tiger;
			break;
		}

		animalSize = 2;
		animals = new Animal[animalSize];
		items[itemSize++] = animals[0] = rabbit;
		items[itemSize++] = animals[1] = enemy;

		missiles = new Missile[10];

		missile=new Missile(-600,0,-1);
		missileSize = 0;

		// Result
		results = new Result[2];
		results[resultSize++] = new Result(120, 500, 1);
		results[resultSize++] = new Result(120, 100, 0);

		resultBackButton = new Button(220, 300, "resProj/Button/back.png");
		resultBackButton.setWidth(512);
		resultBackButton.setHeight(512);
		// ------------------------------------ Interfaces ------------------------------------
		// Interfaces : Set Width/Height of Button Whose Width/Height is Fixed
		resultBackButton.setListener(new ManualListener() {
			int width = (int) resultBackButton.getWidth();
			int height = (int) resultBackButton.getHeight();

			@Override
			public int onAdjustMWidth() {
				return width / 10;
			}

			@Override
			public int onAdjustMHeight() {
				return height / 10;
			}
		});
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				boolean check = false;

				if (resultBackButton.isSelected(x, y))
					check = true;

				if(check) {
					try {
						if(rabbitWin)
							rabbit.setResult(true);
						else
							rabbit.setResult(false);

						int[] datas = new SaveFile().loadFile();
						int enemy = datas[6];
						if (enemy < 3) {
							music.musicOff();
							GameFrame.instance.switchCanvas(StoryFightCanvas.this, StoryCanvas.class);
						}
						else {
							music.musicOff();
							GameFrame.instance.switchCanvas(StoryFightCanvas.this, EndingCanvas.class);
						}
					} catch (IOException | InstantiationException | IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}

			}

		});
		//KeyListener
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				if(key==e.VK_LEFT||key==e.VK_RIGHT)
					rabbit.setJumpDirection(0);
				rabbit.initKey(key);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (rabbit.isJumping()) {
					return;
				}


				if(gameResult==false) {
					rabbit.move(key);
				}

				rabbit.setAttackListener(new AttackListener() {

					private int rabbitDirection = rabbit.getRabbitDirection();
					private int pLength;

					@Override
					public void onPunch() {
						//						music.playEffect();
						//						if(rabbitDirection==1) {
						//							pLength=56;
						//							rabbit.setImgMoveIndex_Y(7);
						//							rabbit.setpLength(pLength);
						//
						//						}
						//						else if(rabbitDirection == -1) {
						//							pLength=-56;
						//							rabbit.setImgMoveIndex_Y(4);
						//							rabbit.setpLength(pLength);
						//						}

					}

					@Override
					public void onKick() {
						if(rabbitDirection==1) {
							pLength=56;
							rabbit.setImgMoveIndex_Y(8);
							rabbit.setpLength(pLength);

						}

						else if(rabbitDirection == -1) {
							pLength=-56;
							rabbit.setImgMoveIndex_Y(5);
							rabbit.setpLength(pLength);
						}
					}
				});

				// When Missile Fired By Rabbit
				rabbit.setRabbitKeyListener(new RabbitKeyListener() {

					@Override
					public void onSpacePressed() {
						int ctrlCnt = rabbit.getCtrlCount();
						if (10 < ctrlCnt) {
							missile = rabbit.fire();
							// ------------------------------- Interfaces -------------------------------
							// Inteface : When Missile Is Out Of Canvas
							// Put Missile In Arrays of Items And Missiles
							missiles[missileSize++] = missile;
							items[itemSize++] = missile;
							missile.setMListener(new MissileListener() {

								@Override
								public void onOut(Missile missile) {
									// -------------------- items[i] == missile --------------------

									int i;
									for (i = 0; i < itemSize; i++) {
										if (items[i] == missile)
											break;
									}

									for (int j = 0; j < itemSize - (i + 1); j++)
										items[i + j] = items[i + j + 1];
									itemSize--;
									missile.setX(600);
									// -------------------- missiles[i] == missile --------------------
									missile=null;
									//									for (i = 0; i < missileSize; i++) {
									//										if (missiles[i] == missile)
									//											break;
									//									}
									//
									//									for (int j = 0; i < missileSize - (i + 1); i++)
									//										missiles[i + j] = missiles[i + j + 1];
									//									missileSize--;
								}

							});

							// Reset Rabbit's CtrlCount = 0
							rabbit.setCtrlCount(0);
							barCTRL.setCuts(0);
						}
					}
					// For Shootin Carrot Missile, Press Ctrl Key 20 times
					@Override
					public void onCtrlPressed() {
						int ctrlCnt = rabbit.getCtrlCount();
						ctrlCnt++;
						rabbit.setCtrlCount(ctrlCnt);

						barCTRL.cutBar();						
					}
				});
			}
		});
		// When Rabbit Knockdown
		rabbitBar.setListener(() -> {
			gameResult = true;
			rabbitWin = false;
			rabbitKnockdown = true;
		});

		// When Enemy Knockdown
		enemyBar.setListener(() -> {
			gameResult = true;
			rabbitWin = true;
			enemyKnockdown = true;
		});
		start();
	}

	// ------------------------------------ Sub Thread ------------------------------------
	public void start() {
		Runnable sub = new Runnable() {

			@Override
			public void run() {
				while(running) {
					background.update();

					if(gameResult==false&&gameConut>=4) {
						for(int i=0; i < itemSize; i++)
							items[i].update();
					}
					// Give Rabbit's Location To Enemy
					double rabbitX = rabbit.getX();
					// If Rabbit Is On Enemy's Range, Enemy Attack Rabbit
					if(enemy.isRabbitEntered(rabbitX))
						enemy.setEntered(true);
					else
						enemy.setEntered(false);
					// Game Result : Win
					if(rabbitKnockdown == false && enemyKnockdown == true) {
						gameResult = true;
						results[1].setZ(0);
					}
					// Game Result : Lose
					else if(rabbitKnockdown == true && enemyKnockdown == false) {
						gameResult = true;
						results[1].setZ(1);
					}
					// The others
					repaint();


					try {
						Thread.sleep(17);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

			}
		};

		Thread thread = new Thread(sub);
		thread.start();
	}

	// ------------------------------------ Functions ------------------------------------
	@Override
	public void update(Graphics g) {
		if (gameResult == false) {
			timeOutForAttect--;
			if (timeOutForAttect == 0) {
				isCollision = rabbit.isCollision(enemy.getCrushPoint());
				tigerIsCollision = enemy.tigerIsCollision(rabbit.getCrushPoint());
				missilelsCollision = missile.missileIsCollision(enemy.getCrushPoint());

				if(missilelsCollision) {
					music.playEffect();
					enemyBar.setMissileCut(true);
					enemyBar.enemyCutBar();
				}
				if (isCollision) {
					music.playEffect();
					rabbitBar.cutBar();
				}
				if (tigerIsCollision) {
					music.playEffect();
					enemyBar.setMissileCut(false);
					enemyBar.enemyCutBar();
				}
				timeOutForAttect = 5;
			}
			enemy.setCout(rabbit.getX());
		}
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		Image buffer = this.createImage(this.getWidth(), this.getHeight());
		Graphics bufferGraphics = buffer.getGraphics();

		background.paint(bufferGraphics);

		for(int i=0; i <  itemSize; i++)
			items[i].paint(bufferGraphics);
		for(int i=0;i<4;i++)
			bars[i].paint(bufferGraphics);

		barCTRL.paint(bufferGraphics);

		if (gameResult) {
			blackBg.paint(bufferGraphics);
			for (int i = 0; i < resultSize; i++)
				results[i].paint(bufferGraphics);

			resultBackButton.paint(bufferGraphics);
		}
		if(gameConut < 4) {
			starts[gameConut].paint(bufferGraphics);
		}

		g.drawImage(buffer, 0, 0, this);

		if(gameConut < 4) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			gameConut++;
		}
	}

	// ------------------------------------ Getters/Setters ------------------------------------
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

}
