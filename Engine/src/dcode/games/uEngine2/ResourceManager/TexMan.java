/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.ResourceManager;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadBitmapFont;
import dcode.games.uEngine2.BGTasks.internalTasks.prepareScaledSubTexture;
import dcode.games.uEngine2.BGTasks.internalTasks.prepareScaledTexture;
import dcode.games.uEngine2.BGTasks.internalTasks.prepareString;
import dcode.games.uEngine2.ResourceManager.DSU.DSU_NODE;
import dcode.games.uEngine2.ResourceManager.DSU.DSU_OBJECT;
import dcode.games.uEngine2.ResourceManager.loaders.InternalTextureLoader;
import dcode.games.uEngine2.StData;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author dusakus
 */
public class TexMan {

    public InternalTextureLoader loader_internal = new InternalTextureLoader();
    public BufferedImage MISSINGTEX = null;
    DSU_NODE textureRegistry;

    private ScaledTexture[] scaledTextureBuffer;
    private int nextSTBwriteAt = 0;
    private ScaledSubTexture[] scaledSubTextureBuffer;
    private int nextSSTBwriteAt = 0;
    private StringContainer[] stringBuffer;
    private int nextSBwriteAt = 0;

    public TexMan() {
        textureRegistry = new DSU_NODE(0, false);

        MISSINGTEX = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
        Graphics G = MISSINGTEX.getGraphics();
        G.setColor(Color.BLACK);
        G.fillRect(0, 0, 32, 32);
        G.setColor(new Color(255, 0, 255));
        G.fillRect(16, 0, 16, 16);
        G.fillRect(0, 16, 16, 16);
        G.setColor(Color.BLACK);
        G.fillRect(23, 2, 2, 7);
        G.fillRect(23, 11, 2, 2);

        if (!StData.setup.debug) {
            MISSINGTEX = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }

        scaledTextureBuffer = new ScaledTexture[64];
        scaledSubTextureBuffer = new ScaledSubTexture[32];

        stringBuffer = new StringContainer[StData.setup.stringBufferSize];


        for (int i = 0; i < stringBuffer.length; i++) {
            stringBuffer[i] = new StringContainer();
        }
        for (int i = 0; i < scaledTextureBuffer.length; i++) {
            scaledTextureBuffer[i] = new ScaledTexture(MISSINGTEX.getScaledInstance(4 * (65 - i), 4 * (65 - i), BufferedImage.SCALE_REPLICATE), 4 * (65 - i), 4 * (65 - i), "missing");
        }
        for (int i = 0; i < scaledSubTextureBuffer.length; i++) {
            scaledSubTextureBuffer[i] = new ScaledSubTexture(MISSINGTEX.getScaledInstance(4 * (33 - i), 4 * (33 - i), BufferedImage.SCALE_REPLICATE), 4 * (33 - i), 4 * (33 - i), 0, 0, 32, 32, "missing");
        }
    }

    public BufferedImage getTexture(String key) {
        BufferedImage tex = null;
        try {
            tex = (BufferedImage) textureRegistry.getObject(key);
        } catch (Exception e) {
            e.printStackTrace();
            StData.LOG.printerr(e, "TEXTURE ACCES ERROR", "D");
        }
        if (tex == null) {
            tex = MISSINGTEX;
            StData.LOG.println("TEXTURE NOT IN THE REGISTRY [" + key + "]", "D");
        }
        return tex;
    }

    public BufferedImage getChar(String key, char c) {
        BufferedImage tex = null;
        try {
            tex = (BufferedImage) textureRegistry.getObject(key + "C" + LoadBitmapFont.charList.indexOf(c));
        } catch (Exception e) {
            e.printStackTrace();
            StData.LOG.printerr(e, "TEXTURE ACCES ERROR", "D");
        }
        if (tex == null) {
            tex = MISSINGTEX;
            StData.LOG.println("TEXTURE NOT IN THE REGISTRY [" + key + "C" + LoadBitmapFont.charList.indexOf(c) + "]", "D");
        }
        return tex;
    }

    public BufferedImage getString(String key, String content) {
        BufferedImage tex = null;

        for (StringContainer aStringBuffer : stringBuffer) {
            if (aStringBuffer.text.equals(content)) {
                if (aStringBuffer.texKey.equals(key)) {
                    tex = aStringBuffer.texture;
                    break;
                }
            }
        }

        if (tex == null) {
            stringBuffer[nextSBwriteAt].texKey = key;
            stringBuffer[nextSBwriteAt].text = content;
            stringBuffer[nextSBwriteAt].texture = MISSINGTEX;
            StData.generalBGT.LPTasks.add(new prepareString(stringBuffer[nextSBwriteAt]));
            tex = MISSINGTEX;
            nextSBwriteAt++;               //That fucking typo, ARGH
        }
        if (nextSBwriteAt == StData.setup.stringBufferSize) {
            nextSBwriteAt = 0;
            StData.LOG.println("WARNING: [Texture Manager] Ran out of string buffer, text might get bugged", "N");
        }
        return tex;
    }

    public boolean isTextureAviable(String key) {
        return textureRegistry.getObject(key) != null;
    }

    public Image getScaledTexture(String key, int width, int height) {
        for (ScaledTexture aScaledTextureBuffer : scaledTextureBuffer) {
            if (aScaledTextureBuffer.key.equals(key)) {
                if (aScaledTextureBuffer.width == width && aScaledTextureBuffer.height == height) {
                    return aScaledTextureBuffer.texture;
                }
            }
        }
        scaledTextureBuffer[nextSTBwriteAt].height = height;
        scaledTextureBuffer[nextSTBwriteAt].width = width;
        scaledTextureBuffer[nextSTBwriteAt].key = key;
        scaledTextureBuffer[nextSTBwriteAt].texture = MISSINGTEX;

        StData.threadManager.BGT.addTask(new prepareScaledTexture(scaledTextureBuffer[nextSTBwriteAt]));

        nextSTBwriteAt++;
        if (nextSTBwriteAt >= scaledTextureBuffer.length) {
            nextSTBwriteAt = 0;
            StData.LOG.println("WARNING: [Texture Manager] Ran out of scaling buffer, performance might get low", "N");
        }

        return getTexture(key).getScaledInstance(width, height, BufferedImage.SCALE_REPLICATE);
    }

    public Image getPartTexture(String key, int startX, int startY, int width, int height) {
        return getTexture(key).getSubimage(startX, startY, width, height);
    }

    public Image getScaledPartTexture(String key, int Scaled_width, int Scaled_height, int startX, int startY, int width, int height) {
        for (ScaledSubTexture aScaledSubTextureBuffer : scaledSubTextureBuffer) {
            if (aScaledSubTextureBuffer.key.equals(key)) {
                if (aScaledSubTextureBuffer.width == Scaled_width && aScaledSubTextureBuffer.height == Scaled_height && aScaledSubTextureBuffer.cropHeight == height && aScaledSubTextureBuffer.cropWidth == width && aScaledSubTextureBuffer.posX == startX && aScaledSubTextureBuffer.posY == startY) {
                    return aScaledSubTextureBuffer.texture;
                }
            }
        }

        scaledSubTextureBuffer[nextSSTBwriteAt].cropHeight = height;
        scaledSubTextureBuffer[nextSSTBwriteAt].cropWidth = width;
        scaledSubTextureBuffer[nextSSTBwriteAt].posX = startX;
        scaledSubTextureBuffer[nextSSTBwriteAt].posY = startY;
        scaledSubTextureBuffer[nextSSTBwriteAt].width = Scaled_width;
        scaledSubTextureBuffer[nextSSTBwriteAt].height = Scaled_height;
        scaledSubTextureBuffer[nextSSTBwriteAt].key = key;

        StData.threadManager.BGT.addTask(new prepareScaledSubTexture(scaledSubTextureBuffer[nextSSTBwriteAt]));

        nextSSTBwriteAt++;
        if (nextSSTBwriteAt >= scaledSubTextureBuffer.length) {
            nextSSTBwriteAt = 0;
            StData.LOG.println("WARNING: [Texture Manager] Ran out of cropping/scaling buffer, performance might get low", "N");
        }

        return getPartTexture(key, startX, startY, width, height);
    }

    public void registerTexture(BufferedImage BI, String identifier) {
        textureRegistry.storeObject(new DSU_OBJECT(identifier, BI));
    }

    public void unload(String rd) {
        textureRegistry.removeObject(rd);
    }

    public class ScaledTexture {

        public Image texture;
        public int width;
        public int height;
        public String key;

        public ScaledTexture(Image texture, int width, int height, String key) {
            this.texture = texture;
            this.width = width;
            this.height = height;
            this.key = key;
        }
    }

    public class ScaledSubTexture {

        public Image texture;
        public int width;
        public int height;
        public int posX;
        public int posY;
        public int cropWidth;
        public int cropHeight;
        public String key;

        public ScaledSubTexture(Image texture, int width, int height, int posX, int posY, int cropWidth, int cropHeight, String key) {
            this.texture = texture;
            this.width = width;
            this.height = height;
            this.posX = posX;
            this.posY = posY;
            this.cropWidth = cropWidth;
            this.cropHeight = cropHeight;
            this.key = key;
        }
    }

    public class StringContainer {

        public BufferedImage texture = null;
        public String text = "ERROR_MISSING_TEXT";
        public String texKey = "NULL";
    }
}
