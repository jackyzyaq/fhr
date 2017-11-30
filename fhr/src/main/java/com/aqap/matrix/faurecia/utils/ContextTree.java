package com.aqap.matrix.faurecia.utils;

import org.apache.log4j.Logger;

public class ContextTree {

   private static Logger log = Logger.getLogger(ContextTree.class);
   private int id;
   private int parentId;
   private String name;
   private String content;


   public ContextTree(int id, int parentId, String name, String content) throws Exception {
      this.id = id;
      this.parentId = parentId;
      this.name = name;
      this.content = content;
   }

   public ContextTree() {}

   public static Logger getLog() {
      return log;
   }

   public static void setLog(Logger log) {
      log = log;
   }

   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public int getParentId() {
      return this.parentId;
   }

   public void setParentId(int parentId) {
      this.parentId = parentId;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getContent() {
      return this.content;
   }

   public void setContent(String content) {
      this.content = content;
   }
}
