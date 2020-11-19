package MoonRabbit;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Event;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

import MoonRabbit.canvas.IntroCanvas;
import MoonRabbit.canvas.ManualCanvas;
import MoonRabbit.canvas.StoryCanvas;
import MoonRabbit.file.SaveFile;

public class GameFrame extends Frame {
	public static GameFrame instance;

	
	// ---------------------------------- Attribute ----------------------------------
	private IntroCanvas introCanvas;
	// ---------------------------------- Constructor ----------------------------------
	public GameFrame() {
		instance = this;
		introCanvas = new IntroCanvas();
		
		setSize(500, 500);
		setLocation(350, 100);
		setTitle("Moon Rabbit");
		setBackground(Color.white);
		setVisible(true);
		
		add(introCanvas);
		
		//Program Exit
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				Object[] options ={ "집 갈래..", "좀 더 싸울래!!" };
				int input = JOptionPane.showOptionDialog(GameFrame.this, "집에 갈래?", "토끼 짐 싸는 중",
				JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
				null, options, options[1]);
				// Save Characters' Status in File Before Closing Program
				try {
					new SaveFile().resetFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				if(input == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});
	}
	public void switchCanvas(Canvas oldCanvas, Class newCanvas) throws InstantiationException, IllegalAccessException {
		
		// Add New Canvas On Frame
		Canvas canvas =(Canvas) newCanvas.newInstance();
		add(canvas);
		
		// Stop Thread of Old Canvas
		if (oldCanvas instanceof IntroCanvas)
			((IntroCanvas) oldCanvas).setRunning(false);
		else if (oldCanvas instanceof StoryCanvas)
			((StoryCanvas) oldCanvas).setRunning(false);
		else if (oldCanvas instanceof ManualCanvas)
			((ManualCanvas) oldCanvas).setRunning(false);
		
		revalidate();
		
		//focus
		canvas.setFocusable(true);
		canvas.requestFocus();
		
		// Remove Old Canvas From Frame
		remove(oldCanvas);
	}
	
}
