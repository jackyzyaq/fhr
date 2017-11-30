package com.aqap.matrix.faurecia.utils;

import java.io.Serializable;

public class ValidatorUtils implements Serializable {/*

   private static final long serialVersionUID = -6218958903144553809L;
   private static final UrlValidator URL_VALIDATOR = new UrlValidator();
   private static final CreditCardValidator CREDIT_CARD_VALIDATOR = new CreditCardValidator();


   public static boolean isBlankOrNull(String value) {
      return value == null || value.trim().length() == 0;
   }

   public static boolean isValidLength(String str, int minLength, int maxLength) {
      if(str == null) {
         str = "";
      }

      if(minLength < 0) {
         minLength = 0;
      }

      if(maxLength < 0) {
         maxLength = Integer.MAX_VALUE;
      }

      int reInt = 0;
      char[] tempChar = str.toCharArray();

      for(int kk = 0; kk < tempChar.length; ++kk) {
         String s1 = String.valueOf(tempChar[kk]);
         byte[] b = (byte[])null;

         try {
            b = s1.getBytes("UTF-8");
         } catch (UnsupportedEncodingException var9) {
            ;
         }

         reInt += b.length;
         if(reInt > maxLength) {
            return false;
         }
      }

      return reInt >= minLength;
   }

   public static boolean matchRegexp(String value, String regexp) {
      if(isBlankOrNull(value)) {
         return false;
      } else if(isBlankOrNull(regexp)) {
         return false;
      } else {
         Pattern pattern = Pattern.compile(regexp);
         Matcher matcher = pattern.matcher(value);
         return matcher.matches();
      }
   }

   public static boolean isByte(String value) {
      return GenericTypeValidator.formatByte(value) != null;
   }

   public static boolean isShort(String value) {
      return GenericTypeValidator.formatShort(value) != null;
   }

   public static boolean isInt(String value) {
      return GenericTypeValidator.formatInt(value) != null;
   }

   public static boolean isLong(String value) {
      return GenericTypeValidator.formatLong(value) != null;
   }

   public static boolean isFloat(String value) {
      return GenericTypeValidator.formatFloat(value) != null;
   }

   public static boolean isDouble(String value) {
      return GenericTypeValidator.formatDouble(value) != null;
   }

   public static boolean isDate(String value, String datePattern, boolean strict) {
      return DateValidator.getInstance().isValid(value, datePattern, strict);
   }

   public static boolean isInRange(byte value, byte min, byte max) {
      return value >= min && value <= max;
   }

   public static boolean isInRange(int value, int min, int max) {
      return value >= min && value <= max;
   }

   public static boolean isInRange(float value, float min, float max) {
      return value >= min && value <= max;
   }

   public static boolean isInRange(short value, short min, short max) {
      return value >= min && value <= max;
   }

   public static boolean isInRange(long value, long min, long max) {
      return value >= min && value <= max;
   }

   public static boolean isInRange(double value, double min, double max) {
      return value >= min && value <= max;
   }

   public static boolean isCreditCard(String value) {
      return CREDIT_CARD_VALIDATOR.isValid(value);
   }

   public static boolean isEmail(String value) {
      return EmailValidator.getInstance().isValid(value);
   }

   public static boolean isUrl(String value) {
      return URL_VALIDATOR.isValid(value);
   }

   public static boolean maxLength(String value, int max) {
      return value.length() <= max;
   }

   public static boolean minLength(String value, int min) {
      return value.length() >= min;
   }

   public static boolean minValue(int value, int min) {
      return value >= min;
   }

   public static boolean minValue(long value, long min) {
      return value >= min;
   }

   public static boolean minValue(double value, double min) {
      return value >= min;
   }

   public static boolean minValue(float value, float min) {
      return value >= min;
   }

   public static boolean maxValue(int value, int max) {
      return value <= max;
   }

   public static boolean maxValue(long value, long max) {
      return value <= max;
   }

   public static boolean maxValue(double value, double max) {
      return value <= max;
   }

   public static boolean maxValue(float value, float max) {
      return value <= max;
   }

   public static boolean isValidParameter(String parameter) {
      if(!isBlankOrNull(parameter)) {
         String lowStr = parameter.toLowerCase().replaceAll("\r\n", "<br/>").replaceAll("\r", "<br/>").replaceAll("\n", "<br/>");
         if(lowStr.contains("script") || lowStr.contains("iframe") || lowStr.contains("document") || lowStr.contains("location") || lowStr.contains("<") || lowStr.contains(">") || lowStr.contains("--") || lowStr.contains("\'") || lowStr.contains("%") || lowStr.contains("||") || lowStr.contains("sys") || lowStr.contains("system") || lowStr.contains("dual") || lowStr.contains("tabs") || lowStr.contains("user_tables") || lowStr.contains("ascii") || lowStr.contains("chr(") || lowStr.contains("chr (") || lowStr.contains("where") || lowStr.contains(" and ") || lowStr.contains(" or ") || lowStr.contains("alter") || lowStr.contains("create") || lowStr.contains("truncate") || lowStr.contains("drop") || lowStr.contains("lock table") || lowStr.contains("insert") || lowStr.contains("update") || lowStr.contains("delete") || lowStr.contains("select") || lowStr.contains("char") || lowStr.contains("mid") || lowStr.contains("group") || lowStr.contains("[") || lowStr.contains("]") || lowStr.contains("{") || lowStr.contains("}") || lowStr.contains("grant") || lowStr.contains("exec") || lowStr.contains("union")) {
            return false;
         }
      }

      return true;
   }

   public static void main(String[] args) {
      System.out.println(isCreditCard("23423432"));
      System.out.println(isEmail("t@1.com"));
   }
*/}
