package MoonRabbit.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class SaveFile {
	// Load Data File
	public int[] loadFile() throws IOException {
		FileInputStream fis = new FileInputStream("resProj/StoryData.txt");
		Scanner scan = new Scanner(fis);
		
		int dataSize = 7;
		int[] datas = new int[dataSize];

		String line = scan.nextLine();
		String[] tokens = line.split(",");

		/*
		 * datas[0] : rabbitX
		 * datas[1] : rabbitY
		 * datas[2] : wolf
		 * datas[3] : bear
		 * datas[4] : tiger
		 * 
		 * datas[5] : BackgroundMove
		 * datas[6] : currentEnemy			// 0 = wolf
		 * datas[7] : result
		 */
		for (int i = 0; i < dataSize; i++)
			datas[i] = Integer.parseInt(tokens[i]);
		
		scan.close();
		fis.close();
		
		return datas;
	}

	public void saveFIle(int rabbitX, int rabbitY, int wolfY, int bearY, int tigerY, int bgMove, int currentEnemy) throws IOException {
		FileOutputStream fos = new FileOutputStream("resProj/StoryData.txt");
		PrintStream out = new PrintStream(fos, true, "UTF-8");
		
		int[] datas = new int[]{rabbitX, rabbitY,
											wolfY, bearY, tigerY, bgMove,
											currentEnemy};
		
		for (int i = 0; i < datas.length; i++)
			if (i == datas.length - 1)
				out.print(datas[i]);
			else
				out.print(datas[i] + ",");
		
		out.close();
		fos.close();
	}
	
	public void resetFile() throws IOException {
		saveFIle(250,400,-100,-500,-900,0,0);
	}

}
