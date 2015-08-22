package dcode.games.uEngine2.localStorage.configStorage;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.tools.BadThings;
import dcode.games.uEngine2.tools.Shortcuts;
import dcode.games.uEngine2.tools.WRITER;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by dusakus on 31.05.15.
 */
public class Save {

    public static boolean saveToFile(CContainer c) {
        try {
            Shortcuts.registerOneTimeBGTask(new ConfigSaver(c, c.fileLocation), true);
        } catch (Exception e){
            StData.LOG.println("[Config save] BG task is not aviable, saving from current thread...");
            try {
                new ConfigSaver(c, c.fileLocation).perform();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return false;
    }

    private static class ConfigSaver extends PBGTask {

        private final CContainer c;
        private final File fileLocation;

        private WRITER w;
        private int depth = -1;


        public ConfigSaver(CContainer c, File fileLocation) {

            this.c = c;
            this.fileLocation = fileLocation;

            StData.LOG.println("[uGConf] Configuration file save requested for " + fileLocation.getName());
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void perform() throws IOException {
            StData.LOG.println("[uGConf] Starting to write configuration file for " + fileLocation.getName());
            StData.LOG.println("[uGConf] exact location:  " + fileLocation.getAbsolutePath(), "D");
            File target = new File(fileLocation.getAbsolutePath());

            if (fileLocation.exists()) {
                if (new File(fileLocation.getAbsolutePath() + "_prev").exists()) {
                    new File(fileLocation.getAbsolutePath() + "_prev").delete();
                }
                fileLocation.renameTo(new File(fileLocation.getAbsolutePath() + "_prev"));
            }
            target.createNewFile();
            w = new WRITER(fileLocation);

            if (c.configHeader != null && !c.configHeader.equals("")) writeHeader("#" + c.configHeader);
            writeBranch(c.getMaster());
            StData.LOG.println("[uGConf] finished writing configuration file for " + fileLocation.getName());
        }

        private void writeHeader(String s) throws IOException {
            if (s == null || s.equals("")) return;
            String[] lines = s.split(Pattern.quote("¬"));
            for (int i = 0; i < lines.length; i++) {
                w.nextLine(lines[i]);
            }
            w.nextLine("========================================================================================================================");
            w.nextLine("");
        }

        private void writeBranch(CContainer.CBranch branch) throws IOException {
            w.nextLine("");
            if (depth > -1)
                w.nextLine(BadThings.repeatString("==", depth) + ":" + branch.key + "{");
            depth++;
            String s = branch.header;
            if (s != null && !s.equals("")) {
                String[] lines = s.split(Pattern.quote("¬"));
                w.nextLine(BadThings.repeatString("==", depth) + "]#");
                for (int i = 0; i < lines.length; i++) {
                    w.nextLine(BadThings.repeatString("  ", depth) + " #" + lines[i]);
                }
                w.nextLine(BadThings.repeatString("==", depth) + "]#");
                w.nextLine("");
            }

            CContainer.CBranch branches[] = branch.getBranches();
            CContainer.CLeaf leaves[] = branch.getLeaves();

            if (leaves[0] != null)
                for (int i = 0; i < leaves.length; i++) {
                    writeLeaf(leaves[i]);
                }
            if (branches[0] != null)
                for (int i = 0; i < branches.length; i++) {
                    writeBranch(branches[i]);
                }

            if (depth > 0) w.nextLine(BadThings.repeatString("==", depth) + "}");
            w.nextLine("");
            depth--;
        }

        private void writeLeaf(CContainer.CLeaf leaf) throws IOException {
            if (leaf.hasIntSet) {
                if (leaf.INT_comment != null) {
                    writeComment(leaf.INT_comment);
                }
                w.nextLine(BadThings.repeatString("  ", depth) + "I:" + leaf.key + "=" + leaf.VALUE_INT);
            }
            if (leaf.hasBoolSet) {
                if (leaf.BOOL_comment != null) {
                    writeComment(leaf.BOOL_comment);
                }
                w.nextLine(BadThings.repeatString("  ", depth) + "B:" + leaf.key + "=" + leaf.VALUE_BOOL);
            }
            if (leaf.hasFloatSet) {
                if (leaf.FLOAT_comment != null) {
                    writeComment(leaf.FLOAT_comment);
                }
                w.nextLine(BadThings.repeatString("  ", depth) + "F:" + leaf.key + "=" + leaf.VALUE_FLOAT);
            }
            if (leaf.hasStringSet) {
                if (leaf.STR_comment != null) {
                    writeComment(leaf.STR_comment);
                }
                w.nextLine(BadThings.repeatString("  ", depth) + "S:" + leaf.key + "=" + leaf.VALUE_STR);
            }
        }

        private void writeComment(String c) throws IOException {
            w.nextLine("");
            w.nextLine(BadThings.repeatString("==", depth) + "># " + c);
        }
    }
}
