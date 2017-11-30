package com.aqap.matrix.faurecia.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.yq.faurecia.pojo.OverTimeInfo;
import com.yq.faurecia.pojo.OverTimeInfoHistory;

public class ReflectPOJO {

   public static void getAttrList(Object o, ArrayList<Object> attrList) {
      Field[] fields = o.getClass().getDeclaredFields();
      int i = 0;

      for(int len = fields.length; i < len; ++i) {
         String varName = fields[i].getName();
         attrList.add(varName);
      }

   }

   public static void getMethodList(Object o, List methodList) throws ClassNotFoundException {
      Class c = Class.forName(o.getClass().getName());
      Method[] methods = c.getDeclaredMethods();
      int i = 0;

      for(int len = methods.length; i < len; ++i) {
         Method method = methods[i];
         String varName = method.getName();
         methodList.add(varName);
      }

   }

   public static Object invokGetMethod(Object o, String attr) throws Exception {
	  if(attr.startsWith("serialVersio"))
		  return "";
	 
      Method method = o.getClass().getMethod("get" + attr.replaceFirst(attr.substring(0, 1), attr.substring(0, 1).toUpperCase()), new Class[0]);
      Object obj = method.invoke(o, new Object[0]);
      //System.out.println(attr+":"+o);
      return obj;
   }

   public static Object invokSetMethod(Object o, String attr, Object param) throws Exception {
      String methodName = "set" + attr.replaceFirst(attr.substring(0, 1), attr.substring(0, 1).toUpperCase());
      Class c = Class.forName(o.getClass().getName());
      Method[] methods = c.getDeclaredMethods();
      Method[] var9 = methods;
      int var8 = methods.length;

      for(int var7 = 0; var7 < var8; ++var7) {
         Method method = var9[var7];
         if(methodName.equals(method.getName())) {
            Class[] clazz = method.getParameterTypes();
            String type = clazz[0].getName().toString();
            if(type.equals("java.lang.String")) {
               method.invoke(o, new Object[]{(String)param});
            } else if(type.equals("java.util.Date")) {
               method.invoke(o, new Object[]{(Date)param});
            } else if(type.equals("java.lang.Integer")) {
               method.invoke(o, new Object[]{(Integer)param});
            } else if(type.equals("java.lang.Double")) {
               method.invoke(o, new Object[]{(Double)param});
            } else {
               method.invoke(o, new Object[]{(byte[])param});
            }
         }
      }

      return o;
   }

   public static Object alternateObject(Object o, Object sourceObj) throws Exception {
      ArrayList attrList = new ArrayList();
      getAttrList(o, attrList);
      ArrayList methodList = new ArrayList();
      getMethodList(sourceObj, methodList);
      Iterator var5 = attrList.iterator();

      while(var5.hasNext()) {
         String attr = (String)var5.next();
         Object objVal = invokGetMethod(o, attr);
         Object sourceObjVal = null;
         if(isMethodExist(sourceObj, "get" + attr.replaceFirst(attr.substring(0, 1), attr.substring(0, 1).toUpperCase()), methodList)) {
            sourceObjVal = invokGetMethod(sourceObj, attr);
         }

         if(Util.convertToString(objVal).trim().equals("")) {
            invokSetMethod(o, attr, sourceObjVal);
         }
      }

      attrList = null;
      methodList = null;
      return o;
   }

   public static Object copyObject(Object newObj, Object sourceObj) throws Exception {
      ArrayList attrList = new ArrayList();
      getAttrList(newObj, attrList);
      ArrayList methodList = new ArrayList();
      getMethodList(sourceObj, methodList);
      Iterator var5 = attrList.iterator();

      while(var5.hasNext()) {
         String attr = (String)var5.next();
         Object sourceObjVal = null;
         if(isMethodExist(sourceObj, "get" + attr.replaceFirst(attr.substring(0, 1), attr.substring(0, 1).toUpperCase()), methodList)) {
            sourceObjVal = invokGetMethod(sourceObj, attr);
            invokSetMethod(newObj, attr, sourceObjVal);
         }
      }

      attrList = null;
      methodList = null;
      return newObj;
   }

   public static boolean isMethodExist(Object o, String method, List methodList) throws Exception {
      boolean isTrue = false;
      if(methodList != null && methodList.size() > 0) {
         Iterator var5 = methodList.iterator();

         while(var5.hasNext()) {
            String m = (String)var5.next();
            if(!StringUtils.isEmpty(method) && m.trim().equals(method.trim())) {
               isTrue = true;
               break;
            }
         }
      }

      return isTrue;
   }

   public static String toString(Object o) throws Exception {
      if(o == null) {
         return "";
      } else {
         StringBuffer sb = new StringBuffer();
         String oVal = Util.convertToString(o);
         sb.append("{");
         if(StringUtils.isEmpty(oVal)) {
            ArrayList attrList = new ArrayList();
            getAttrList(o, attrList);
            Iterator var5 = attrList.iterator();

            while(var5.hasNext()) {
               String attr = (String)var5.next();
               sb.append("[" + attr + "||||" + Util.convertToString(invokGetMethod(o, attr)) + "||||" + attr + "]");
            }
         } else {
            sb.append("[" + oVal + "]");
         }

         sb.append("}");
         return sb.toString();
      }
   }

   public static void main(String[] args) throws Exception {
      OverTimeInfoHistory otih = new OverTimeInfoHistory();
      OverTimeInfo oti = new OverTimeInfo();

      try {
         alternateObject(otih, oti);
      } catch (Exception var4) {
         var4.printStackTrace();
      }

   }
}
