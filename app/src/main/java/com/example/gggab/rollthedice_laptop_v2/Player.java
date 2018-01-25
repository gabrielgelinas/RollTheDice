package com.example.gggab.rollthedice_laptop_v2;

import java.util.Random;

/**
 * Created by gggab on 2018-01-10.
 */

public class Player {
    private String name;
    public int rolls;
    private int total;

    public Player(String p_name) {
        name = p_name;
        rolls = 0;
        total = 0;
    }

    public String getName() {
        return name;
    }

    public int getTotal() {
        return total;
    }

    public int newRoll(){
        Random rand = new Random();
        int roll = rand.nextInt(6)+1;
        total += roll;
        rolls++;
        return roll;
    }
}

