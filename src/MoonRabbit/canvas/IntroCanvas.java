package MoonRabbit.canvas;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.zip.InflaterOutputStream;

import javax.swing.JOptionPane;

import MoonRabbit.GameFrame;
import MoonRabbit.entity.Background;
import MoonRabbit.entity.Button;
import MoonRabbit.entity.Music;
import MoonRabbit.file.SaveFile;

public class IntroCanvas extends Canvas {
	public static Canvas instance;

	// ------------------------------------ Attribute ------------------------------------

	private Music music;

	private Background background;
	private Background title;
	private Button[] buttons;
	private boolean running=true;

	// ------------------------------------ Constructor ------------------------------------
	public IntroCanvas() {
		instance = this;
		this.setSize(500, 500);
		this.setBackground(Color.pink);

		// BGM====================================//
		music = new Music("resProj/Sound/intro.wav");
		// ======================================= //

		// Background
		background = new Background("resProj/Background/intro.png", instance);
		title = new Background("resProj/Background/title.png",instance);
		// Buttons
		buttons = new Button[4];
		buttons[0] = new Button(145, 160, "resProj/Button/story.png");
		buttons[1] = new Button(145, 230, "resProj/Button/load.png");
		buttons[2] = new Button(145, 300, "resProj/Button/menual.png");
		buttons[3] = new Button(145, 370, "resProj/Button/exit.png");

		// ------------------------------------ Interface ------------------------------------
		// Interface : For Button Hovered
		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
				int y = e.getY(); 
				for (int i = 0; i < 4; i++) {
					if (buttons[i].isSelected(x, y)) {
						buttons[i].setSelect(200);
					}
					else
						buttons[i].setSelect(0);
				}
			}
		});
		// Interface : For Button Clicked
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int selected = 0;

				for (int i = 0; i < 4; i++)
					if (buttons[i].isSelected(x, y)) 
						selected = i + 1;

				// IntroCanvas : mouseListener

				try {
					switch (selected) {
					case 1:
						try {
							new SaveFile().resetFile();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						GameFrame.instance.switchCanvas(IntroCanvas.this, StoryCanvas.class);
						music.musicOff();
						break;
					case 2:
						GameFrame.instance.switchCanvas(IntroCanvas.this, StoryCanvas.class);
						music.musicOff();
						break;
					case 3:
						GameFrame.instance.switchCanvas(IntroCanvas.this, ManualCanvas.class);
						music.musicOff();
						break;
					case 4 :
						try {
							new SaveFile().resetFile();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						int input = JOptionPane.showConfirmDialog(IntroCanvas.this
								, "정말로 종료하시겠습니까?"
								, "게임 종료"
								, JOptionPane.OK_CANCEL_OPTION);

						if(input == JOptionPane.OK_OPTION)
							System.exit(0);
					}
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
		title.paint(bufferGraphics);

		for(int i =0 ; i < buttons.length; i++)
			buttons[i].paint(bufferGraphics);

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
