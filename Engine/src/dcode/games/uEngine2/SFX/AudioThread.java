/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.SFX;

import dcode.games.uEngine.sound.parts.Note;
import dcode.games.uEngine2.StData;

/**
 * @author dusakus
 */
public class AudioThread extends Thread {
	private static int threadIteration = -1;


	public int LOOP_TPS = 60;
	public int LOOP_ticks = 0;
	public boolean LOOP_RUN = true;
	public boolean LOOP_Recalculate = true;
	private boolean disableSound = false;

	public AudioThread() {

		LOOP_TPS = StData.setup.TPS_MSX;
		if(StData.setup.TPS_MSX <= 0){
			LOOP_TPS = 2;
			disableSound = true;
		}
	}

	@Override
	public void run() {
		threadIteration++;

		//test
		StData.currentGC.currentAC.msxCH1.newLoop();
		StData.currentGC.currentAC.msxCH1.addNote(new Note(-1, 4, 30));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 9));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(-1, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 9));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(3, 4, 9));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(4, 4, 15));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 9));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(5, 4, 30));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(-1, 4, 60));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(-1, 4, 60));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(-1, 4, 60));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 5, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 5, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 5, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 5, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 5, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 5, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 5, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 5, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 4, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(2, 5, 2));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(-1, 4, 35));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(3, 6, 3));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(3, 5, 3));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(3, 4, 3));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(3, 3, 3));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(3, 2, 3));
		StData.currentGC.currentAC.msxCH1.addNote(new Note(3, 1, 3));

		StData.currentGC.currentAC.msxCH1.muted = false;
		StData.currentGC.currentAC.msxCH1.setRepeat(false);

		this.setName("Audio Thread ITER"+threadIteration);
		//Timming variables
		int timeStep = 1000000000 / LOOP_TPS;
		long currentTime = System.nanoTime();
		long nextTime = System.nanoTime() + timeStep;

		//FPS counter variables
		int FPS = 0;
		long lastFPSdraw = System.nanoTime();
		//LOOP
		while (StData.gameIsRunning && LOOP_RUN) {

			//waiting for next planned time and performing sidetasks
			while (currentTime < nextTime || StData.gameFreeze) {
				try {
					Thread.sleep(0, 500000);
					if(StData.gameFreeze){
						Thread.sleep(10);
						nextTime = System.nanoTime() + timeStep;
					}
					currentTime = System.nanoTime();
				} catch (InterruptedException ex) {
				}
			}

			nextTime += timeStep;


			if(!disableSound){

				StData.SE.tick();
				//TODO: sound system ]:I
				//Do the sound stuff

			}


			if (StData.setup.debug) {
				FPS++;
				if (System.nanoTime() - lastFPSdraw >= 1000000000L) {
					StData.LOG.println("[Audio Thread] Tick No." + LOOP_ticks + ", measured TPS: " + FPS, "D");
					FPS = 0;
					lastFPSdraw = System.nanoTime();
				}
			}
			if(StData.threadManager.AT != this){
				LOOP_RUN = false;
			}
			LOOP_ticks++;
			if (LOOP_Recalculate) {
				StData.LOG.println("[Audio Thread] Tick No. " + LOOP_ticks + ", setting new loop speed: " + LOOP_TPS, "D");
				timeStep = 1000000000 / LOOP_TPS;
				currentTime = System.nanoTime();
				nextTime = System.nanoTime() + timeStep;
				LOOP_Recalculate = false;
			}
		}
	}

}
