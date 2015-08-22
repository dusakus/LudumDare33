/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.tools;

/**
 * @author dusakus
 */
public class numbarTools {
	public static boolean checkBetween(int inputValue, int minValue, int maxValue) {
		return inputValue >= minValue && inputValue <= maxValue && inputValue <= maxValue && inputValue >= minValue;
	}

	public static int clamp(int inputValue, int minValue, int maxValue) {
		if (inputValue < minValue) return minValue;
		if (inputValue > maxValue) return maxValue;
		return inputValue;
	}

	public static int mod(int i) {
		if (i >= 0) return i;
		return -i;
	}
}
