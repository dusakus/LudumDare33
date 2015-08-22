package dcode.games.uEngine2.tools;

import java.io.*;

/**
 * @author dusakus
 */
public class READER {

	File F;
	BufferedReader br;

	public READER(File f) throws FileNotFoundException {
		F = f;
		br = new BufferedReader(new FileReader(F));
	}

	public String nextLine() throws IOException {
		if (br.ready()) {
			return br.readLine();
		}
		return null;
	}

	public void Reset() throws FileNotFoundException {
		br = new BufferedReader(new FileReader(F));
	}
}
