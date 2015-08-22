package dcode.games.uEngine.dcctools;

import java.util.ArrayList;

/**
 * @author dusakus
 */
public class MultiString {

    public ArrayList<String> line = new ArrayList<String>();
    public int current = 0;

    public MultiString(String fLine) {
        line.add(0, fLine);
    }

    public void addL(String lineI) {
        line.add(lineI);
    }

    public void reset() {
        current = 0;
    }

    public String getNext() {
        return line.get(current++);
    }
}
