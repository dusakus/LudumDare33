/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.ResourceManager;

/**
 * @author dusakus
 */
public class ResMan {

	public TexMan grf;
	public SampledSfxMan ssfx;
	public SampledMsxMan smsx;

	public ResMan() {
		grf = new TexMan();
		ssfx = new SampledSfxMan();
		smsx = new SampledMsxMan();
	}

}
