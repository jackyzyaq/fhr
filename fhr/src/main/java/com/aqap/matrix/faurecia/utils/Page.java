package com.aqap.matrix.faurecia.utils;

import javax.persistence.Entity;

@Entity
public class Page {

   public static final int DEFAULT_PAGE_SIZE = 10;
   private int pageIndex;
   private int pageSize;
   private int totalCount;
   private int pageCount;
   private String sidx;
   private String sord;


   public String getSidx() {
      return this.sidx;
   }

   public void setSidx(String sidx) {
      this.sidx = sidx;
   }

   public String getSord() {
      return this.sord;
   }

   public void setSord(String sord) {
      this.sord = sord;
   }

   public Page(int pageIndex, int pageSize) {
      if(pageIndex < 1) {
         pageIndex = 1;
      }

      if(pageSize < 1) {
         pageSize = 10;
      }

      this.pageIndex = pageIndex;
      this.pageSize = pageSize;
   }

   public Page(int pageIndex) {
      this(pageIndex, 10);
   }

   public Page() {
      this(1, 10);
   }

   public int getPageIndex() {
      return this.pageIndex;
   }

   public void setPageIndex(int pageIndex) {
      this.pageIndex = pageIndex;
   }

   public int getPageSize() {
      return this.pageSize;
   }

   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }

   public int getPageCount() {
      return this.pageCount;
   }

   public int getTotalCount() {
      return this.totalCount;
   }

   public int getFirstResult() {
      return (this.pageIndex - 1) * this.pageSize;
   }

   public boolean getHasPrevious() {
      return this.pageIndex > 1;
   }

   public boolean getHasNext() {
      return this.pageIndex < this.pageCount;
   }

   public void setTotalCount(int totalCount) {
      if(totalCount < 0) {
         throw new IllegalArgumentException("Total count should not less than 0.");
      } else {
         this.totalCount = totalCount;
         this.pageCount = totalCount / this.pageSize + (totalCount % this.pageSize == 0?0:1);
         if(totalCount == 0) {
            this.pageIndex = 1;
         }

         if(this.pageIndex > this.pageCount) {
            this.pageIndex = this.pageCount;
         }

      }
   }

   public boolean isEmpty() {
      return this.totalCount == 0;
   }
}
