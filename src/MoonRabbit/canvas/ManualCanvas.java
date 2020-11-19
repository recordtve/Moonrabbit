package MoonRabbit.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import MoonRabbit.GameFrame;
import MoonRabbit.entity.Background;
import MoonRabbit.entity.Button;
import MoonRabbit.entity.Music;
import MoonRabbit.interfaces.ManualListener;

public class ManualCanvas extends Canvas {
	public static Canvas instance;

	// ------------------------------------ Attribute ------------------------------------
	private Background backgrouund;
	private Music music;
	
	private Button backButton;
	private boolean running =true;

	private Background manual;
	// ------------------------------------ Constructor ------------------------------------
	public ManualCanvas() {
		instance = this;
		this.setSize(500, 500);
		this.setBackground(Color.pink);
		music = new Music("resProj/Sound/Manual.wav");
		// Background
		backgrouund = new Background("resProj/Background/intro.png", instance);
		manual = new Background("resProj/Background/manual.png", instance);
		// Button : Back To IntroCanvas
		backButton = new Button(420, 410, "resProj/Button/back.png");
		backButton.setWidth(512);
		backButton.setHeight(512);
		// ------------------------------------ Interfaces ------------------------------------
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
						GameFrame.instance.switchCanvas(ManualCanvas.this, IntroCanvas.class);
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
					backgrouund.update();
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

		backgrouund.paint(bufferGraphics);
		manual.paint(bufferGraphics);
		backButton.paint(bufferGraphics);
		
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
