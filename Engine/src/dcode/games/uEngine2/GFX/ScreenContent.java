/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.GFX;

import dcode.games.uEngine2.GFX.postproc.IGrfPostProcessor;
import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.GFX.sprites.SpriteWrapper;
import dcode.games.uEngine2.StData;

import java.util.ArrayList;

/**
 * This file represents all the contents of game window
 *
 * @author dusakus
 */
public class ScreenContent {

	//background layer objects
	public ArrayList<ILayer> layers_Background;
	public int[] sprites_back;
	public SpriteWrapper[] spriteW_back;

	//central layer objects
	public ArrayList<ILayer> layers_Center;
	public int[] sprites_middle;
	public SpriteWrapper[] spriteW_middle;

	//front layer objects
	public ArrayList<ILayer> layers_Foreground;
	public int[] sprites_front;
	public SpriteWrapper[] spriteW_front;

	//overlay layers
	public ArrayList<ILayer> layers_Overlay;

	//base sprite list
	public Sprite[] sprites;

	//postProcessing sockets
	public IGrfPostProcessor[] postProcessors;

	public ScreenContent() {

		StData.LOG.println("Creating new ScreenContent container", "D");

		//construction of object storage
		layers_Background = new ArrayList<ILayer>();
		sprites_back = new int[StData.setup.spriteLayerSize];
		layers_Center = new ArrayList<ILayer>();
		sprites_middle = new int[StData.setup.spriteLayerSize];
		layers_Foreground = new ArrayList<ILayer>();
		sprites_front = new int[StData.setup.spriteLayerSize];
		layers_Overlay = new ArrayList<ILayer>();

		//if the game wants to use sprite wrappers prepare storage for them
		if (StData.setup.enableSpriteWrappers) {
			spriteW_back = new SpriteWrapper[StData.setup.spriteLayerSize];
			spriteW_middle = new SpriteWrapper[StData.setup.spriteLayerSize];
			spriteW_front = new SpriteWrapper[StData.setup.spriteLayerSize];

			for (int i = 0; i < spriteW_back.length; i++) {
				spriteW_back[i] = new SpriteWrapper();
				spriteW_middle[i] = new SpriteWrapper();
				spriteW_front[i] = new SpriteWrapper();
			}
		}


		sprites = new Sprite[StData.setup.spriteTableSize];

		postProcessors = new IGrfPostProcessor[StData.setup.postProcCount];
		StData.LOG.println("Creating new ScreenContent container: DONE", "D");

	}

}
