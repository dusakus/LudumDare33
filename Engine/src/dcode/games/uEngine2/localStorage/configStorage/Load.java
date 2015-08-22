package dcode.games.uEngine2.localStorage.configStorage;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.tools.READER;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by dusakus on 31.05.15.
 */
public class Load {

    public static final boolean LOG_PROCES = true;

    public static CContainer LoadFromFile(CContainer c, File f) {
        if(LOG_PROCES) StData.LOG.println("BEGINNING CONFIG LOADING FROM FILE "+f.getAbsolutePath(),"D");
        if (c == null) c = new CContainer();
        if (f.exists()) {
            c.fileLocation = f;
            READER r;
            try {
                if(LOG_PROCES) StData.LOG.println("CREATING READER","D");
                r = new READER(f);


                String line = r.nextLine();

                boolean header = true;
                boolean groupheader = false;
                int depth = 0;
                LinkedList<String> tree = new LinkedList<String>();
                CContainer.CBranch branch = c.getMaster();
                CContainer.CLeaf leaf = c.missingLeaf;
                String valComment = null;

                while (line != null) {
                    if (!header && line.trim().isEmpty()) {
                    } else {
                        if(LOG_PROCES) StData.LOG.println("NEXT LINE: "+line,"D");
                        line = line.substring(depth * 2, line.length());
                        if(LOG_PROCES) StData.LOG.println("REMOVING first "+depth*2 + " chars","D");
                        if (header) {
                            if(LOG_PROCES) StData.LOG.println("<<cHEADER","D");
                            if (line.trim().startsWith("========") || line.trim().startsWith(":") || line.trim().indexOf(":") == 1) header = false;
                            else if (c.configHeader == null || c.configHeader.isEmpty()) c.configHeader = line;
                            else c.configHeader += "¬" + line;
                        } else if (groupheader) {
                            if(LOG_PROCES) StData.LOG.println("<<gHEADER","D");
                            if (line.trim().startsWith("]#")) groupheader = false;
                            else if (branch.header == null || branch.header.isEmpty()) branch.header = line.substring(2, line.length());
                            else branch.header += "¬" + line.substring(2, line.length());
                        } else {
                            if (line.trim().startsWith("}") && depth > 0) {
                                if(LOG_PROCES) StData.LOG.println("GROUP CLOSED","D");
                                tree.pop();
                                depth--;
                            } else if (line.trim().startsWith("]#")) {
                                if(LOG_PROCES) StData.LOG.println("GROUP HEADER BEGINS","D");
                                groupheader = true;
                            } else if (line.trim().startsWith(":")) {
                                tree.push(line.replace(":", " ").replace("{", " ").trim());
                                branch = c.getBranch(constructLocation(null, tree), true);
                                depth++;
                                if(LOG_PROCES) StData.LOG.println("GROUP OPEN " + tree.peek(),"D");

                            } else if (line.startsWith(">#")) {
                                if(LOG_PROCES) StData.LOG.println("LINE COMMENT","D");
                                valComment = line.substring(3, line.length());
                            } else if (line.trim().indexOf(":") == 1 && line.indexOf("=") > 1) {
                                leaf = c.getLeaf(constructLocation(line.substring(line.indexOf(":") + 1, line.indexOf("=")),tree), true);
                                String val = line.substring(line.indexOf("=") + 1, line.length());
                                if(LOG_PROCES) StData.LOG.println("VALUE FIELD " + leaf.key,"D");
                                switch (line.trim().getBytes()[0]) {
                                    case 'I':
                                        if(LOG_PROCES) StData.LOG.println("PARSING INTEGER","D");
                                        leaf.VALUE_INT = Integer.parseInt(val);
                                        leaf.hasIntSet = true;
                                        if (valComment != null) {
                                            leaf.INT_comment = valComment;
                                            valComment = null;
                                        }
                                        break;
                                    case 'F':
                                        if(LOG_PROCES) StData.LOG.println("PARSING FLOAT","D");
                                        leaf.VALUE_FLOAT = Float.parseFloat(val);
                                        leaf.hasFloatSet = true;
                                        if (valComment != null) {
                                            leaf.FLOAT_comment = valComment;
                                            valComment = null;
                                        }
                                        break;
                                    case 'B':
                                        if(LOG_PROCES) StData.LOG.println("PARSING BOOLEAN","D");
                                        leaf.VALUE_BOOL = Boolean.parseBoolean(val);
                                        leaf.hasBoolSet = true;
                                        if (valComment != null) {
                                            leaf.BOOL_comment = valComment;
                                            valComment = null;
                                        }
                                        break;
                                    case 'S':
                                        if(LOG_PROCES) StData.LOG.println("READING STRING","D");
                                        leaf.VALUE_STR = val;
                                        leaf.hasStringSet = true;
                                        if (valComment != null) {
                                            leaf.STR_comment = valComment;
                                            valComment = null;
                                        }
                                        break;
                                }
                            } else {
                                StData.LOG.println("CONFIG FILE IS BROKEN, error in line \n => " + line, "E3");
                            }
                        }
                    }

                    line = r.nextLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedOperationException e) {
                e.printStackTrace();
            }

            if(LOG_PROCES) StData.LOG.println("FILE CLOSED","D");
        }
        return c;
    }

    private static String constructLocation(String fin, LinkedList<String> rest) {
        String s = "";
        if (!rest.isEmpty())
            for (int i = rest.size()-1; i >= 0; i--) {
                s += rest.get(i) + ".";
            }
        if (fin == null) {
            s = s.substring(0, s.length() - 1);
        } else
            s += fin;
        return s;
    }
}
