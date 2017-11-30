package com.aqap.matrix.faurecia.utils;

import java.util.Calendar;

public class IdCard {

   public final int CHINA_ID_MIN_LENGTH = 15;
   public final int CHINA_ID_MAX_LENGTH = 18;


   public static int getAgeByIdCard(String idCard) {
      int iAge = 0;

      try {
         Calendar e = Calendar.getInstance();
         String year = idCard.substring(6, 10);
         int iCurrYear = e.get(1);
         iAge = iCurrYear - Integer.valueOf(year).intValue();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      return iAge;
   }

   public static String getBirthByIdCard(String idCard) {
      return idCard.substring(6, 14);
   }

   public static Short getYearByIdCard(String idCard) {
      return Short.valueOf(idCard.substring(6, 10));
   }

   public static Short getMonthByIdCard(String idCard) {
      return Short.valueOf(idCard.substring(10, 12));
   }

   public static Short getDateByIdCard(String idCard) {
      return Short.valueOf(idCard.substring(12, 14));
   }

   public static String getGenderByIdCard(String idCard) {
      String sGender = "未知";

      try {
         String e = idCard.substring(16, 17);
         if(Integer.parseInt(e) % 2 != 0) {
            sGender = "1";
         } else {
            sGender = "2";
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return sGender;
   }

   public static void main(String[] a) {
      String idcard = "0";
      String sex = getGenderByIdCard(idcard);
      System.out.println("性别:" + sex);
      int age = getAgeByIdCard(idcard);
      System.out.println("年龄:" + age);
      Short nian = getYearByIdCard(idcard);
      Short yue = getMonthByIdCard(idcard);
      Short ri = getDateByIdCard(idcard);
      System.out.print(nian + "年" + yue + "月" + ri + "日");
      String sr = getBirthByIdCard(idcard);
      System.out.println("生日:" + sr);
   }
}
