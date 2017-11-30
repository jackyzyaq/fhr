package com.aqap.matrix.faurecia.utils;

import java.awt.Color;

public class OperaColor extends Color {

   public OperaColor(int r, int g, int b) {
      super(r, g, b);
   }

   public String getHex() {
      return toHex(this.getRed(), this.getGreen(), this.getBlue());
   }

   public static String toHex(int r, int g, int b) {
      return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
   }

   private static String toBrowserHexValue(int number) {
      StringBuilder builder = new StringBuilder(Integer.toHexString(number & 255));

      while(builder.length() < 2) {
         builder.append("0");
      }

      return builder.toString().toUpperCase();
   }
}
