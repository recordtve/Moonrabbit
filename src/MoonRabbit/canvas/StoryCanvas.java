package MoonRabbit.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
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
import MoonRabbit.entity.MovBackground;
import MoonRabbit.entity.Music;
import MoonRabbit.entity.Rabbit;
import MoonRabbit.entity.Tiger;
import MoonRabbit.entity.Wolf;
import MoonRabbit.file.SaveFile;
import MoonRabbit.interfaces.ManualListener;
import MoonRabbit.interfaces.RabbitKeyListener;
import MoonRabbit.interfaces.RabbitListener;

public class StoryCanvas extends Canvas {
	public static Canvas instance;
	// ------------------------------------ Attribute ------------------------------------
	private MovBackground background;
	private boolean running =true;

	private Animal[] animals;
	private int animalSize;

	private Enemy[] enemies;
	private Enemy currentEnemy;

	private Rabbit rabbit;
	private Bear bear;
	private Wolf wolf;
	private Tiger tiger;


	private Music music;
	SaveFile dataFile;
	private Button saveButton;
	// ------------------------------------ Constructor ------------------------------------
	public StoryCanvas() throws IOException {
		instance = this;
		this.setSize(500, 500);
		music = new Music("resProj/Sound/Story.wav");

		dataFile = new SaveFile();
		int[] datas = dataFile.loadFile();
		int rabbitX = datas[0];
		int rabbitY = datas[1];
		int wolfY = datas[2];
		int bearY = datas[3];
		int tigerY = datas[4];
		int bgMove = datas[5];
		int currentEnemyIdx = datas[6];		// 0 = wolf, 1 = bear, 2 = tiger


		// Background
		background = new MovBackground("resProj/Background/story.jpg", instance);
		background.setMove(bgMove);

		saveButton = new Button(380, 10, "resProj/Background/exit.png");
		saveButton.setWidth(200);
		saveButton.setHeight(200);
		saveButton.setListener(new ManualListener() {
			int width = (int) saveButton.getWidth();
			int height = (int) saveButton.getHeight();
			
			@Override
			public int onAdjustMWidth() {
				return width/3;
			}
			
			@Override
			public int onAdjustMHeight() {
				return height/3;
			}
		});
		
		rabbit = new Rabbit(rabbitX, rabbitY,instance);
		wolf = new  Wolf(250, wolfY,instance);
		bear = new Bear(250, bearY,instance);
		tiger = new Tiger(250, tigerY,instance);

		// Aniaml = Rabbit + Enemies
		animalSize = 4;
		animals = new Animal[animalSize];
		animals[0] = wolf;
		animals[1] = bear;
		animals[2] = tiger;
		animals[3] = rabbit;

		// Enemies = Wolf + Bear + Tiger
		enemies = new Enemy[animalSize-1];
		enemies[0] = wolf;
		enemies[1] = bear;
		enemies[2] = tiger;

		// Check CurrentEnemyIdx Which Is Saved In DataFile.txt
		switch (currentEnemyIdx) {
		// currentEnemy = Wolf, Check Game Result Before Player played
		case 0: // wolf
			currentEnemy =enemies[0];
			break;
			// currentEnemy = Bear, Check Game Result Before Player played
		case 1:// bears


			currentEnemy = enemies[1];
			break;
			// currentEnemy = Tiger, Check Game Result Before Player played
		case 2:// tiger

			currentEnemy = enemies[2];
			break;
		}
		// ------------------------------------ Interfaces ------------------------------------
		// Interfaces : When Rabbit Move Up,
		// Items(Background, Enemies)' Y value Will Be Changed
		rabbit.setRabbitListener(new RabbitListener() {
			@Override
			public void onMoveUp() {
				int bgMove = (int) background.getMove();
				int[] enemiesMove = new int[animalSize - 1];

				if (-1 < currentEnemy.getY() && currentEnemy.getY()  <= 2) {
					rabbit.setArrived(true);
				} 
				else {
					bgMove+=2;
					background.setMove(bgMove);

					// enemies
					for (int i = 0; i < animalSize - 1; i++) {
						enemiesMove[i] = (int) enemies[i].getY();
						enemiesMove[i]+=2;
						enemies[i].setY(enemiesMove[i]);
					}
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				if (saveButton.isSelected(x, y))
					try {
						music.musicOff();
						GameFrame.instance.switchCanvas(StoryCanvas.this, IntroCanvas.class);
						dataFile.saveFIle((int) rabbit.getX(), (int)rabbit.getY(),
								(int)wolf.getY(), (int)bear.getY(), (int)tiger.getY(), background.getMove(),
								datas[6]);
					} catch (InstantiationException | IllegalAccessException | IOException e1) {
						e1.printStackTrace();
					}
			}
		});
		// Interface : When Player/User Pressed Key
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();

				rabbit.initKey(key);

			}

			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				rabbit.move(key);
				// Interface : Enter/Go To Story Fight Canvas
				rabbit.setRabbitKeyListener(new RabbitKeyListener() {

					@Override
					public void onSpacePressed() {
						boolean checkEntered = false;
						checkEntered = rabbit.isEntered();
						if(checkEntered)
							try {
								music.musicOff();
								GameFrame.instance.switchCanvas(StoryCanvas.this, StoryFightCanvas.class);
								dataFile.saveFIle((int) rabbit.getX(), (int)rabbit.getY(),
										(int)wolf.getY(), (int)bear.getY(), (int)tiger.getY(), background.getMove(),
										datas[6]);
							} catch (InstantiationException | IllegalAccessException | IOException e1) {
								e1.printStackTrace();
							}
					}

					@Override
					public void onCtrlPressed() {}
				});

			}
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

					for(int i=0; i < animalSize; i++)
						animals[i].update();

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
		paint(g);
	}

	@Override
	public void paint(Graphics g) {
		Image buffer = this.createImage(this.getWidth(), this.getHeight());
		Graphics bufferGraphics = buffer.getGraphics();

		background.paint(bufferGraphics);
		for(int i=0; i < animals.length; i++)
			animals[i].paint(bufferGraphics);

		saveButton.paint(bufferGraphics);
		g.drawImage(buffer, 0, 0, this);
	}
	// ------------------------------------ Getters/Setters ------------------------------------
	// Check Whether Sub Thread is Running
	public boolean isRunning() {
		return running;
	}
	// Set Sub Thread Running As True/False
	public void setRunning(boolean running) {
		this.running = running;
	}
}
