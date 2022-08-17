package com.tilen.Engine.Utils;

import com.tilen.Engine.Vector2D;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

public class MathUtils {

    public static final float random(float high) {
        Random random = new SecureRandom();
        return random.nextFloat(high);
    }

    // taken from processing foundation
    public static final float random(float low, float high) {
        if (low >= high) return low;
        float diff = high - low;
        float value = 0;
        do {
            value = random(diff) + low;
        } while (value == high);
        return value;
    }

    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static long dist_sq(long x1, long y1, long x2, long y2) {
        return ((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static long factorial(long n) {
        if(n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static long calcDistance(ArrayList<Vector2D> list, ArrayList<Integer> order) {
        long sum = 0;
        for (int i = 0; i < order.size() - 1; i++) {
            Vector2D cityA = list.get(order.get(i));
            Vector2D cityB = list.get(order.get(i + 1));
            // Because squrt is slow and I don't care for sqrts ;)
//            double d = dist(cityA.x, cityA.y, cityB.x, cityB.y);
            float d = dist_sq((long)cityA.x, (long)cityA.y, (long)cityB.x, (long)cityB.y);
            sum+=d;
        }
        return sum;
    }

    public static double constrain(double n, double low, double high) {
        return Math.max(Math.min(n, high), low);
    }

    public static double map(double n, double start1, double stop1, double start2, double stop2, boolean withinBounds) {
        double newval = (n - start1) / (stop1 - start1) * (stop2 - start2) + start2;
        if (!withinBounds) {
            return newval;
        }
        if (start2 < stop2) {
            return constrain(newval, start2, stop2);
        } else {
            return constrain(newval, stop2, start2);
        }
    }

    static public final int max(int a, int b) {
        return (a > b) ? a : b;
    }

    static public final float max(float a, float b) {
        return (a > b) ? a : b;
    }

    public static int floor(int a) {
        return (int) Math.floor(a);
    }

    static public final float pow(float n, float e) {
        return (float)Math.pow(n, e);
    }
}
