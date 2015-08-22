package dcode.games.uEngine2.tools;

import dcode.games.uEngine2.StData;

/**
 * Created by dusakus on 01.06.15.
 */
public class ExceptionHandler {
    public void BOOM(Exception e, String area, Object source) {
        StData.LOG.printerr(e, "Exception occured at " + area + ", and was caused by " + source, "E3");
    }
}
