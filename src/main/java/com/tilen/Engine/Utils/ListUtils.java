package com.tilen.Engine.Utils;

import static com.tilen.Engine.Utils.MathUtils.*;

import java.util.ArrayList;
import java.util.Random;


public class ListUtils {


    public static <T> ArrayList<T> cloneList(ArrayList<T> a) {
        return new ArrayList<>(a);
    }

    public static <T> ArrayList<T> splice(ArrayList<T> a, int startIndex) {
        ArrayList<T> out = new ArrayList<>();
        for (int i = startIndex; i < a.size(); i++) {
            out.add(a.get(i));
            a.remove(i);
        }
        return out;
    }

    public static <T> ArrayList<T> splice(ArrayList<T> a, int startIndex, int endIndex) {
        ArrayList<T> out = new ArrayList<>();
        System.out.println(startIndex +"    " + endIndex);
        if (startIndex == endIndex) {
            return out;
        }
        for (int i = startIndex; i < endIndex; i++) {
            out.add(a.get(i));
            a.remove(i);
        }
        return out;
    }

    public static <T> ArrayList<T> reverse(ArrayList<T> a) {
        ArrayList<T> na = new ArrayList<>();
        for (int i = a.size()-1; i >= 0; i--) {
            na.add(a.get(i));
        }
        return na;
    }

    public static <T> void swap(ArrayList<T> list, int i, int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    public static <T> ArrayList<T> shuffle(ArrayList<T> list) {

        Random r = new Random();
        int num = list.size();
        while (num > 1) {
            int value = r.nextInt(num);
            num--;
            T temp = list.get(num);
            list.set(num, list.get(value));
            list.set(value, temp);
        }

        return list;
    }

    public static int[] toIntArray(ArrayList<Integer> a) {
        int[] out = new int[a.size()];
        for (int i = 0; i< a.size(); i++) {
            out[i] = a.get(i);
        }
        return  out;
    }

    public static <T> ArrayList<T> pick(ArrayList<ArrayList<T>> list,  ArrayList<Float> prob) {
        int index = 0;
        float r = random(1);
        while(r > 0) {
            // this modulo is because i used to get an out-of-bounds error it fixes it, hopefully without any bugs
            r = r - prob.get(index % prob.size());
            index++;
        }
        index--;
        // just so that it does not underflow, because it does
        index = max(index, 0);

        // this modulo is because i used to get an out-of-bounds error it fixes it, hopefully without any bugs
        return cloneList(list.get(index % list.size()));
    }

    public static <T> ArrayList<T> pickRand(ArrayList<ArrayList<T>> list,  ArrayList<Float> prob) {
        int ia = (int) random(list.size());
        return new ArrayList<>(list.get(ia));
    }

}
