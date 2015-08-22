package dcode.games.uEngine2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class DCoutputH {

    public boolean debug = false;
    public boolean DRAWONHUD = false;
    Date time;
    DateFormat timed;
    File logfile;
    PrintWriter FO;
    private boolean toFile = false;
    private boolean isLongMode = false;
    private LinkedList<String> buffer;

    public DCoutputH() {
        timed = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        time = new Date();
        logfile = new File(System.getProperty("user.dir") + "/DCODE/uEngine2/" + StData.setup.safeName + "/Log.dcl");
        buffer = new LinkedList<String>();
        logfileC();
    }

    public DCoutputH(boolean debugI) {
        timed = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        time = new Date();
        debug = debugI;
        logfile = new File(System.getProperty("user.dir") + "/DCODE/uEngine2/" + StData.setup.safeName + "/Log.dcl");
        buffer = new LinkedList<String>();
        logfileC();
    }

    public DCoutputH(boolean debugI, String where) {
        timed = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        time = new Date();
        debug = debugI;

        buffer = new LinkedList<String>();
        if (where != null) {
            logfile = new File(where);
            logfileC();
        }

    }

    private void logfileC() {
        println("Setting up log file as " + logfile.toString());

        try {
            logfile.getParentFile().mkdirs();
            if (logfile.exists()) {
                println(logfile.toString() + " already exist, clearing");
            } else {
                println("creating new log file");
                try {
                    logfile.createNewFile();
                } catch (IOException e) {
                    println("Failed, try this next time");
                }
            }
            if (logfile.canWrite()) {
                FO = new PrintWriter(logfile);
            } else {
                println("Whops, no write acces");
            }

            toFile = true;
            println("   >---[Begin of File log]---<");
        } catch (IOException e) {
            e.printStackTrace();
            toFile = false;
            println("logging to file disabled");
        }
    }

    public boolean println(String input) {
        if (StData.setup.debug) System.out.println(input);
        else buffer.add(input);
        return true;
    }

    public synchronized boolean println(String input, String mode) {
        if (mode == null) {
            println(input);
            return true;
        }
        if (mode.equals("D") && !debug) {
            return true;
        }
        if (mode.equals("N")) {
            this.println(input);

        } else if (mode.equals("E1")) {//for a little error
            this.println("[E1] Whops ...");
            this.println("[E1]=> " + input);
            this.println("[E1] of course it's still working :)");

        } else if (mode.equals("E2")) {
            this.println("[E2] Something went wrong ...");
            this.println("[E2]=> " + input);
            this.println("[E2] but it didn't crashed yet ;)");

        } else if (mode.equals("E3")) {
            this.println("[E3] I have very bad news");
            this.println("[E3]=> " + input);
            this.println("[E3] <Skips the problematic thingy>");

        } else if (mode.equals("E4")) {
            this.println("[E4] Oh, it hurts so much");
            this.println("[E4]=> " + input);
            this.println("[E4] i'm dying !");
            this.println("BZZt Bzzt bzz...");
            System.exit(2);

            this.println("[E5] Critical error ocured:");
            this.println("[E5]=> " + input);
            this.println("[E5] Killing in NOW");
            this.println("!!!ERROR!!!");
            System.exit(2);

            if (debug) {
                this.println("[Debug]=> " + input);
            }

        } else if (mode.equals("E5")) {
            this.println("[E5] Critical error ocured:");
            this.println("[E5]=> " + input);
            this.println("[E5] Killing in NOW");
            this.println("!!!ERROR!!!");
            System.exit(2);

            if (debug) {
                this.println("[Debug]=> " + input);
            }

        } else if (mode.equals("D")) {
            if (debug) {
                this.println("[Debug]=> " + input);
            }

        } else if (mode.equals("debug")) {
            if (debug) {
                this.println("[Debug]=> " + input);
            }

        } else if (mode.equals("E6S")) {
            this.println("[E6] Sorry, but " + input);
            this.println("[E6] You might try again ;)");

        } else {
            this.println(input);
        }
        return true;
    }

    //INTeger input = INTput
    public synchronized boolean printint(int INTput) {
        println(INTput + "");

        return true;
    }

    public synchronized void print(Exception input) {
        //System.out.println(input);
        println(input.getMessage());
//		println(input.getCause().getMessage());
        println(input.toString());
        StackTraceElement[] stel = input.getStackTrace();
        for (StackTraceElement ste : stel) {
            println(ste.toString());
        }
        if (toFile) {
            try {
                //input.printStackTrace(FO);
                FO.print(input.getMessage());
                FO.flush();
            } catch (Exception e) {
                toFile = false;
                this.printerr(e, "WARNING: FILE LOGGING ERROR, FILE LOG DISABLED", "E3");
            }
        }
    }

    public synchronized boolean printerr(Exception e, String input, String mode) {
        if (mode == null) {
            println(input);
            return true;
        }
        if (mode.equals("N")) {
            this.println(input);
            this.print(e);

        } else if (mode.equals("E1")) {//for a little error
            this.println("[E1] Whops ...");
            this.println("[E1]=> " + input);
            this.longMode("|E1 >--------------------------------------");
            this.print(e);
            this.longMode("|E1 >--------------------------------------");
            this.println("[E1] of course it's still working :)");

        } else if (mode.equals("E2")) {
            this.println("[E2] Something went wrong ...");
            this.println("[E2]=> " + input);
            this.longMode("|E2 >--------------------------------------");
            this.print(e);
            this.longMode("|E2 >--------------------------------------");
            this.println("[E2] but it didn't crashed yet ;)");

        } else if (mode.equals("E3")) {
            this.println("[E3] I have very bad news");
            this.println("[E3]=> " + input);
            this.longMode("|E3 >--------------------------------------");
            this.print(e);
            this.longMode("|E3 >--------------------------------------");
            this.println("[E3] <Skips the problematic thingy>");

        } else if (mode.equals("E4")) {
            this.println("[E4] Oh, it hurts so much");
            this.println("[E4]=> " + input);
            this.longMode("|E4 >--------------------------------------");
            this.print(e);
            this.longMode("|E4 >--------------------------------------");
            this.println("[E4] i'm dying !");
            this.println("BZZt Bzzt bzz...");
            System.exit(2);

            this.println("[E5] Critical error ocured:");
            this.println("[E5]=> " + input);
            this.longMode("|E5 >--------------------------------------");
            this.print(e);
            this.longMode("|E5 >--------------------------------------");
            this.println("[E5] Killing in NOW");
            this.println("!!!ERROR!!!");
            System.exit(2);

            if (debug) {
                this.println("[Debug]=> " + input);
                this.longMode("|Debug >--------------------------------------");
                this.print(e);
                this.longMode("|Debug >--------------------------------------");
            }

        } else if (mode.equals("E5")) {
            this.println("[E5] Critical error ocured:");
            this.println("[E5]=> " + input);
            this.longMode("|E5 >--------------------------------------");
            this.print(e);
            this.longMode("|E5 >--------------------------------------");
            this.println("[E5] Killing in NOW");
            this.println("!!!ERROR!!!");
            System.exit(2);

            if (debug) {
                this.println("[Debug]=> " + input);
                this.longMode("|Debug >--------------------------------------");
                this.print(e);
                this.longMode("|Debug >--------------------------------------");
            }

        } else if (mode.equals("D")) {
            if (debug) {
                this.println("[Debug]=> " + input);
                this.longMode("|Debug >--------------------------------------");
                this.print(e);
                this.longMode("|Debug >--------------------------------------");
            }

        } else if (mode.equals("debug")) {
            if (debug) {
                this.println("[Debug]=> " + input);
                this.longMode("|Debug >--------------------------------------");
                this.print(e);
                this.longMode("|Debug >--------------------------------------");
            }

        } else if (mode.equals("E6S")) {
            this.println("[E6] Sorry, but " + input);
            this.longMode("|E6 >--------------------------------------");
            this.print(e);
            this.println("|E6 >--------------------------------------");
            this.longMode("[E6] You might try again ;)");

        } else {
            this.print(e);
        }
        return true;
    }

    private void longMode(String text) {
        if (isLongMode) {
            //println("________________________________________________________________________");
            println("[" + timed.format(time) + "]___{" + text + "}________________/\\/");
            isLongMode = false;

        } else {
            isLongMode = true;
            println("[" + timed.format(time) + "]___{" + text + "}_________________/\\");
        }
    }

    public void END(String smthng) {
        println(smthng);
        println("   <===[ This is the end ]===>    ");
        this.dumpBuffer();
        if (toFile) {
            FO.close();
        }
    }

    public void dumpBuffer() {

        if (!buffer.isEmpty()) {
            Object[] arr = buffer.toArray();
            buffer.clear();

            for (Object i : arr) {
                String input = (String) i;
                if (DRAWONHUD) {
                    StData.lastMSG = input;
                }

                time = new Date();
                timed = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");

                if (!isLongMode) {
                    System.out.println("[" + timed.format(time) + "] " + input);
                } else {
                    System.out.println(input);
                }
                if (toFile) {
                    try {
                        if (!isLongMode) {
                            FO.println("[" + timed.format(time) + "] " + input);
                        } else {
                            FO.println(input);
                        }
                        FO.flush();
                    } catch (Exception e) {
                        System.out.println("[" + timed.format(time) + "] " + "WARNING: FILE LOGGING ERROR");
                        toFile = false;
                    }
                }
            }
        }
    }
}
