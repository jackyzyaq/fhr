package com.aqap.matrix.faurecia.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogUtils {

   public static final int DEBUG = 0;
   public static final int INFO = 1;
   public static final int WARN = 2;
   public static final int ERROR = 3;
   public static final int FATAL = 4;
   private static Logger logger;
   private static LogUtils logutils = null;


   static {
      getInstance();
   }

   public static synchronized LogUtils getInstance() {
      if(logutils == null) {
         logutils = new LogUtils();
      }

      return logutils;
   }

   public static void log(Class className, Object message, Throwable throwable, int level) {
      logger = Logger.getLogger(className);
      switch(level) {
      case 0:
         logger.debug(message, throwable);
         break;
      case 1:
         logger.info(message, throwable);
         break;
      case 2:
         logger.warn(message, throwable);
         break;
      case 3:
         logger.error(message, throwable);
         break;
      case 4:
         logger.fatal(message, throwable);
         break;
      default:
         logger.debug(message, throwable);
      }

   }

   public static void log(Class className, Object message) {
      log(className, message, (Throwable)null, 0);
   }

   public static void log(Class className, Object message, int level) {
      log(className, message, (Throwable)null, level);
   }

   public static void log(Class className, Throwable throwable, int level) {
      log(className, (Object)null, throwable, level);
   }

   public static void initialize(String logPropertiesFile) {
      try {
         PropertyConfigurator.configureAndWatch(logPropertiesFile);
      } catch (Exception var2) {
         var2.printStackTrace();
      }
   }

}
