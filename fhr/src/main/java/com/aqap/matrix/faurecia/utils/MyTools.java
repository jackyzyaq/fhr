package com.aqap.matrix.faurecia.utils;

import com.yq.faurecia.pojo.EmployeeInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyTools {

   public static void setAllComponentsName(Object f) {
      Field[] fields = f.getClass().getDeclaredFields();
      int e = 0;

      for(int method1 = fields.length; e < method1; ++e) {
         String obj = fields[e].getName();

         try {
            boolean ex = fields[e].isAccessible();
            fields[e].setAccessible(true);
            Object o = fields[e].get(f);
            System.out.println("传入的对象中包含一个如下的变量：" + obj + " = " + o);
            fields[e].setAccessible(ex);
         } catch (IllegalArgumentException var8) {
            var8.printStackTrace();
         } catch (IllegalAccessException var9) {
            var9.printStackTrace();
         }
      }

      try {
         Method var10 = f.getClass().getMethod("setEmp_code", new Class[]{String.class});
         var10.invoke(f, new Object[]{"nijian"});
         Method var11 = f.getClass().getMethod("getEmp_code", new Class[0]);
         Object var12 = var11.invoke(f, new Object[0]);
         System.out.println(var12.toString());
      } catch (Exception var7) {
         var7.printStackTrace();
      }

   }

   public static void main(String[] args) {
      String attr = "nijian";
      System.out.println(attr.replaceFirst(attr.substring(0, 1), attr.substring(0, 1).toUpperCase()));
      setAllComponentsName(new EmployeeInfo());
   }
}
