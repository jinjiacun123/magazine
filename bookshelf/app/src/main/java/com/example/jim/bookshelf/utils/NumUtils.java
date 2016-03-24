package com.example.jim.bookshelf.utils;

import java.text.NumberFormat;

/**
 * Created by jim on 16-3-19.
 */
public class NumUtils
{
    public static int computePercent(double paramDouble1, double paramDouble2)
    {
        double d = paramDouble1 / paramDouble2;
        String str = NumberFormat.getPercentInstance().format(d);
        return Integer.parseInt(str.substring(0, -1 + str.length()));
    }
}
