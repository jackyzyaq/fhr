package com.aqap.matrix.faurecia.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServiceUtils {

   private static ApplicationContext context;


   static {
      System.out.println("初始化spring Application.........");
      context = new ClassPathXmlApplicationContext("classpath:META-INF/spring/spring-db.xml");
   }

   public static Object getService(String service) {
      return context.getBean(service);
   }
}
