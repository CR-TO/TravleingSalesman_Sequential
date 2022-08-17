package com.tilen.Engine.Utils;

public class StringUtils {

    public static String numberFormat(int i, double n) {
        return String.format("%."+i+"f", n);
    }

}
