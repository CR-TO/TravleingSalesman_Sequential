package com.tilen;

import com.tilen.Engine.Game;
import com.tilen.TravelingSalesman.TravelingSalesman;

public class Launcher {

    public static void main(String[] args) {
        Game game = new TravelingSalesman(25, 1000);
        game.start();
    }
}
