package com.aqap.matrix.faurecia.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MD5 {

   public static String encrypt(String src) {
      src = src.trim();
      String Digest = "";

      try {
         MessageDigest currentAlgorithm = MessageDigest.getInstance("md5");
         currentAlgorithm.reset();
         byte[] mess = src.getBytes();
         byte[] hash = currentAlgorithm.digest(mess);

         for(int i = 0; i < hash.length; ++i) {
            int v = hash[i];
            if(v < 0) {
               v += 256;
            }

            if(v < 16) {
               Digest = Digest + "0";
            }

            Digest = Digest + Integer.toString(v, 16).toUpperCase();
         }
      } catch (NoSuchAlgorithmException var7) {
         ;
      }

      return Digest;
   }

   public static void main(String[] args) {
      SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss:SSS");
      String date = sdf.format(new Date());
      String md5 = encrypt(date);
      String random = "1234";
      if(md5.length() > 4) {
         random = md5.substring(0, 4);
      }

      System.out.println(random);
   }
}
