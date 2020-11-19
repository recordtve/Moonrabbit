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
import MoonRabbit.entity.Background;
import MoonRabbit.entity.Button;
import MoonRabbit.entity.Item;
import MoonRabbit.entity.Moon;
import MoonRabbit.entity.Music;
import MoonRabbit.entity.Rabbit;
import MoonRabbit.file.SaveFile;
import MoonRabbit.interfaces.ManualListener;

public class EndingCanvas extends Canvas {
	public static Canvas instance;
	
	// ------------------------------------ Attribute ------------------------------------
	private boolean running =true;
	private Music music;
	
	private Background background;
	private Background moonRabbit;
	
	private Item[] items;
	private int itemSize;
	private Moon moon;
	private Rabbit rabbit;
	
	private Button backButton;
	// ------------------------------------ Constructor ------------------------------------
	public EndingCanvas() {
		instance = this;
		this.setSize(500, 500);
		this.setBackground(Color.pink);
		
		music = new Music("resProj/Sound/Ending.wav");
		
		background = new Background("resProj/Background/Ending.jpg", instance);
		moonRabbit = new Background("resProj/Background/moonrabbit.jpg", instance);
		
		itemSize = 0;
		items = new Item[2];
		items[itemSize++] = moon = new Moon(200, 30, instance);
		items[itemSize++] = rabbit = new Rabbit(250, 350, instance);
		
		backButton = new Button(400, 400, "resProj/Button/back.png");
		backButton.setWidth(512);
		backButton.setHeight(512);
		
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
			}
		});

		// Interfaces : Set Width/Height of Button Whose Width/Height is Fixed
		backButton.setListener(new ManualListener() {
			int width = (int) backButton.getWidth();
			int height = (int) backButton.getHeight();

			@Override
			public int onAdjustMWidth() {
				return width / 10;
			}

			@Override
			public int onAdjustMHeight() {
				return height / 10;
			}
		});
		// Interfaces : When Button Clicked, Switch Canvas To Intro Canvas
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				if (backButton.isSelected(x, y))
					try {
						music.musicOff();
						try {
							new SaveFile().resetFile();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						GameFrame.instance.switchCanvas(EndingCanvas.this, IntroCanvas.class);
					} catch (InstantiationException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
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
					
					for(int i=0; i<  itemSize; i++)
						items[i].update();
					
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
	
	public void stop() {
		running = false;
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
		
		for(int i=0; i<  itemSize; i++)
			items[i].paint(bufferGraphics);
		
		if(rabbit.getY() <= 50) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			moonRabbit.paint(bufferGraphics);
			backButton.paint(bufferGraphics);
		}
		g.drawImage(buffer, 0, 0, this);
		
	}
	// ------------------------------------ Getters/Setters ------------------------------------
			public boolean isRunning() {
				return running;
			}

			public void setRunning(boolean running) {
				this.running = running;
			}
}
