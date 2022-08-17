package com.tilen.Engine.Utils;

import com.tilen.Engine.Vector2D;

public class VectorUtils {

    public static Vector2D createVector(double x, double y) {
        return new Vector2D(x, y);
    }

    public static Vector2D createRandomVector() {
        return Vector2D.fromAngle(Math.random() * (6.28318530718));
    }


    public static double dist(Vector2D one, Vector2D two) {
        return Math.sqrt((two.x-one.x)*(two.x-one.x) + (two.y-one.y)*(two.y-one.y));
    }
}
