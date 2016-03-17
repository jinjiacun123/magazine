package com.jim.magazine.help;

import java.text.NumberFormat;

public class NumUtils {
	  public static int computePercent(double paramDouble1, double paramDouble2)
	  {
	    double d = paramDouble1 / paramDouble2;
	    String str = NumberFormat.getPercentInstance().format(d);
	    return Integer.parseInt(str.substring(0, -1 + str.length()));
	  }
}
