package com.tilen.TravelingSalesman;

import com.tilen.Engine.Game;

import java.awt.*;

public class TravelingSalesman extends Game {

    GAMain gaMulti;

    public TravelingSalesman(int totalCities, int popSize) {
        super(1500, 760, "Traveling salesman");
        gaMulti = new GAMain(this, totalCities, popSize);
    }

    @Override
    protected void setup() {
        System.out.println(this.getTitle());
        gaMulti.setup();
    }

    @Override
    protected void draw(Graphics2D g) {
        background(51,51,51);
        gaMulti.draw(this, g);
    }
}
