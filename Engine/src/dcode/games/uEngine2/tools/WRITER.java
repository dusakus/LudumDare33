package dcode.games.uEngine2.tools;

import java.io.*;

/**
 * @author dusakus
 */
public class WRITER {

	File F;
	BufferedWriter br;

	public WRITER(File f) throws IOException {
		F = f;
		br = new BufferedWriter(new FileWriter(F));
	}

	public void nextLine(String s) throws IOException {
		br.write(s);
		br.newLine();
		br.flush();
	}

	public void FINISH() throws IOException {
		br.close();
	}
}
