/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dcode.games.uEngine.dcctools;

import java.util.Random;

/**
 *
 * @author dusakus
 */
public class RNG {
    private Random generator;
    
    public RNG(){
        generator = new Random();
    }
    public RNG(long seed){
        generator = new Random(seed);
    }
    
    public long rLong(){
        return generator.nextLong();
    }
    
    public int rInt(){
        int seed = generator.nextInt();
        return seed;
    }
    public int rInt(int max){
        int seed = generator.nextInt(max);
        return seed;
    }
    public int rInt(int min, int max){
        int seed = generator.nextInt(max-min);
        return seed+min;
    }
}
