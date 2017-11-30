package com.aqap.matrix.faurecia.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class AppendFile {

   private static Logger log = Logger.getLogger(AppendFile.class);
   private String fileName = null;


   public AppendFile(String filepath, String filename, String initContent) throws Exception {
      this.fileName = filepath + File.separator + filename;
      File file = new File(this.fileName);
      if(!file.getParentFile().exists()) {
         file.getParentFile().mkdirs();
      }

      if(file.createNewFile()) {
         log.info("Create " + filename + " file successed");
         if(!StringUtils.isEmpty(initContent)) {
            this.method1(initContent);
         }
      }

   }

   public void method1(String conent) {
      BufferedWriter out = null;

      try {
         out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName, true)));
         out.write(conent);
      } catch (Exception var12) {
         var12.printStackTrace();
      } finally {
         try {
            if(out != null) {
               out.close();
            }
         } catch (IOException var11) {
            var11.printStackTrace();
         }

      }

   }

   public void method2(String content) {
      FileWriter writer = null;

      try {
         writer = new FileWriter(this.fileName, true);
         writer.write(content);
      } catch (IOException var12) {
         var12.printStackTrace();
      } finally {
         try {
            if(writer != null) {
               writer.close();
            }
         } catch (IOException var11) {
            var11.printStackTrace();
         }

      }

   }

   public void method3(String content) {
      RandomAccessFile randomFile = null;

      try {
         randomFile = new RandomAccessFile(this.fileName, "rw");
         long e = randomFile.length();
         randomFile.seek(e);
         randomFile.writeBytes(content);
      } catch (IOException var13) {
         var13.printStackTrace();
      } finally {
         if(randomFile != null) {
            try {
               randomFile.close();
            } catch (IOException var12) {
               var12.printStackTrace();
            }
         }

      }

   }

   public static void main(String[] args) {
      try {
         SimpleDateFormat e = new SimpleDateFormat("yyyy-MM-dd");
         Calendar c = Calendar.getInstance();
         String initContent = "emp_id\tcard_id\tinner_date\ttype\tcreate_date\r\n";
         AppendFile af = new AppendFile(System.getProperty("user.dir") + File.separator + "test", e.format(c.getTime()) + ".txt", initContent);
         af.method1("123\r\n");
         af.method2("123\r\n");
         af.method3("123\r\n");
      } catch (Exception var5) {
         System.out.println(var5);
      }

   }
}
