package com.aqap.matrix.faurecia.utils;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.yq.authority.pojo.MenuInfo;
import com.yq.authority.pojo.UserInfo;
import com.yq.faurecia.pojo.AnnualLeave;
import com.yq.faurecia.pojo.DepartmentInfo;
import com.yq.faurecia.pojo.EmployeeInfo;
import com.yq.faurecia.pojo.EmployeeLeave;
import com.yq.faurecia.pojo.FlowInfo;

public class Util
{
  public static synchronized long getTimeLong()
  {
    return System.currentTimeMillis();
  }
  
  public static synchronized String getNumber()
  {
    try
    {
      Thread.sleep(1L);
    }
    catch (InterruptedException e)
    {
      e.printStackTrace();
    }
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
    
    int hour = cal.get(10);
    int minute = cal.get(12);
    int second = cal.get(13);
    int milli = cal.get(14);
    return 
      String.valueOf(sdf.format(cal.getTime()) + (
      
      hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 
      1000 + milli));
  }
  
  public static double formatDouble1(double d)
  {
    return Math.round(d * 1000.0D) / 1000.0D;
  }
  
  public static synchronized String getUUID()
  {
    UUID uuid = UUID.randomUUID();
    String str = uuid.toString();
    
    String temp = str.substring(0, 8) + str.substring(9, 13) + 
      str.substring(14, 18) + str.substring(19, 23) + 
      str.substring(24);
    return str + "," + temp;
  }
  
  public static String[] getUUID(int number)
  {
    if (number < 1) {
      return null;
    }
    String[] ss = new String[number];
    for (int i = 0; i < number; i++) {
      ss[i] = getUUID();
    }
    return ss;
  }
  
  public static boolean contains(String[] strs, String str)
  {
    if ((strs == null) || (strs.length == 0) || (str == null) || 
      (str.trim().equals(""))) {
      return false;
    }
    boolean isTrue = false;
    String[] arrayOfString = strs;int j = strs.length;
    for (int i = 0; i < j; i++)
    {
      String s = arrayOfString[i];
      if (s.trim().equals(str.trim()))
      {
        isTrue = true;
        break;
      }
    }
    return isTrue;
  }
  
  public static boolean containsIndexOf(String[] strs, String str)
  {
    if ((strs == null) || (strs.length == 0) || (str == null) || 
      (str.trim().equals(""))) {
      return false;
    }
    boolean isTrue = false;
    String[] arrayOfString = strs;int j = strs.length;
    for (int i = 0; i < j; i++)
    {
      String s = arrayOfString[i];
      if (str.trim().toUpperCase().indexOf(s.trim().toUpperCase()) > -1)
      {
        isTrue = true;
        break;
      }
    }
    return isTrue;
  }
  
  public static boolean containsDigit(String str)
  {
    boolean isTrue = false;
    if (StringUtils.isEmpty(str)) {
      return isTrue;
    }
    for (int i = 0; i < str.length(); i++) {
      if (Character.isDigit(str.charAt(i)))
      {
        isTrue = true;
        break;
      }
    }
    return isTrue;
  }
  
  public static boolean containsLetter(String str)
  {
    boolean isTrue = false;
    if (StringUtils.isEmpty(str)) {
      return isTrue;
    }
    for (int i = 0; i < str.length(); i++) {
      if (Character.isLetter(str.charAt(i)))
      {
        isTrue = true;
        break;
      }
    }
    return isTrue;
  }
  
  public static boolean contains(String strs, String str, String separator)
  {
    if ((StringUtils.isEmpty(strs)) || (StringUtils.isEmpty(str)) || (StringUtils.isEmpty(separator))) {
      return false;
    }
    return contains(strs.split(separator), str);
  }
  
  public static boolean containsList(List<String> strs, String str)
  {
    if ((strs == null) || (strs.size() == 0) || (str == null) || 
      (str.trim().equals(""))) {
      return false;
    }
    boolean isTrue = false;
    for (Object s : strs) {
      if (s.toString().trim().equals(str.trim()))
      {
        isTrue = true;
        break;
      }
    }
    return isTrue;
  }
  
  public static List<String> findNumForString(String str)
  {
    String s = "\\d+";
    Pattern pattern = Pattern.compile(s);
    Matcher ma = pattern.matcher(str);
    List<String> strResult = new ArrayList();
    while (ma.find()) {
      strResult.add(ma.group());
    }
    return strResult;
  }
  
  public static boolean isHasNum(String str)
  {
    boolean isTrue = false;
    if ((str != null) && (!str.trim().equals("")))
    {
      List<String> strResult = findNumForString(str);
      if ((strResult != null) && (strResult.size() > 0)) {
        isTrue = true;
      }
    }
    return isTrue;
  }
  
  public static List<String> replaceNum(List<String> strResult)
  {
    List<String> tempStrResult = new ArrayList();
    if ((strResult != null) && (strResult.size() > 0)) {
      for (String str : strResult) {
        if (isHasNum(str)) {
          tempStrResult.add(str);
        }
      }
    }
    return tempStrResult;
  }
  
  public static String replaceD(String str)
  {
    if (!StringUtils.isEmpty(str)) {
      str = str.replaceAll("\\d+", "").replace(".", "");
    }
    return str;
  }
  
  public static int containsForIndex(Object[] strs, Object value)
  {
    int index = -1;
    if ((strs == null) || (strs.length <= 0) || (value == null) || 
      (value.toString().length() <= 0)) {
      return index;
    }
    for (int i = 0; i < strs.length; i++)
    {
      Object v = strs[i];
      if (v.toString().trim().equals(value.toString().trim()))
      {
        index = i;
        break;
      }
    }
    return index;
  }
  
  public static Integer[] intToIntegerArray(int[] num)
  {
    if ((num == null) || (num.length <= 0)) {
      return null;
    }
    Integer[] toInteger = new Integer[num.length];
    for (int i = 0; i < num.length; i++) {
      toInteger[i] = Integer.valueOf(Integer.parseInt(String.valueOf(num[i])));
    }
    return toInteger;
  }
  
  public static boolean isNumeric(String str)
  {
    Pattern pattern = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }
  
  public static boolean isInt(String str)
  {
    Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }
  
  public static boolean authEmail(String email)
  {
    boolean isTrue = true;
    if ((email == null) || (email.trim().equals("")))
    {
      isTrue = false;
    }
    else
    {
      Pattern pattern = 
        Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
      Matcher matcher = pattern.matcher(email);
      isTrue = matcher.matches();
    }
    return isTrue;
  }
  
  public static String getIpAddr(HttpServletRequest request)
  {
    String ipAddress = null;
    
    ipAddress = request.getHeader("x-forwarded-for");
    if ((ipAddress == null) || (ipAddress.length() == 0) || 
      ("unknown".equalsIgnoreCase(ipAddress))) {
      ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if ((ipAddress == null) || (ipAddress.length() == 0) || 
      ("unknown".equalsIgnoreCase(ipAddress))) {
      ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }
    if ((ipAddress == null) || (ipAddress.length() == 0) || 
      ("unknown".equalsIgnoreCase(ipAddress)))
    {
      ipAddress = request.getRemoteAddr();
      if (ipAddress.equals("127.0.0.1"))
      {
        InetAddress inet = null;
        try
        {
          inet = InetAddress.getLocalHost();
        }
        catch (UnknownHostException e)
        {
          e.printStackTrace();
        }
        ipAddress = inet.getHostAddress();
      }
    }
    if ((ipAddress != null) && (ipAddress.length() > 15)) {
      if (ipAddress.indexOf(",") > 0) {
        ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
      }
    }
    return ipAddress;
  }
  
  public static String convertToString(Object o)
  {
    if (o == null) {
      o = "";
    }
    if ((o instanceof Date))
    {
      SimpleDateFormat sdf = new SimpleDateFormat(
        "yyyy-MM-dd HH:mm:ss");
      o = sdf.format((Date)o);
    }
    else if ((o instanceof Byte))
    {
      o = ((Byte)o).toString();
    }
    else if ((o instanceof Integer))
    {
      o = ((Integer)o).toString();
    }
    else if ((o instanceof Long))
    {
      o = ((Long)o).toString();
    }
    else if ((o instanceof Float))
    {
      o = ((Float)o).toString();
    }
    else if ((o instanceof Double))
    {
      o = formatDouble1(((Double)o).doubleValue());
    }
    else if ((o instanceof String))
    {
      o = ((String)o).toString();
    }
    else if ((o instanceof Boolean))
    {
      o = ((Boolean)o).toString();
    }
    else if ((o instanceof BigDecimal))
    {
      o = ((BigDecimal)o).toString();
    }
    else if (!(o instanceof byte[]))
    {
      if (o.getClass().isArray())
      {
        String str_ = "";
        Object[] arrayOfObject;
        int j = (arrayOfObject = (Object[])o).length;
        for (int i = 0; i < j; i++)
        {
          Object ob = arrayOfObject[i];
          str_ = str_ + convertToString(ob) + ",";
        }
        if (str_.endsWith(",")) {
          str_ = str_.substring(0, str_.length() - 1);
        }
        o = str_;
      }
      else
      {
        o = "";
      }
    }
    return 
    
      o.toString().trim().replace("\r", "<br/>").replace("\n", "<br/>").replace("\"", "��").replace("'", "��");
  }
  
  public static String alternateZero(int id)
  {
    String sId = String.valueOf(id);
    int limit = 6;
    int id_limit = sId.length();
    if (id_limit < limit) {
      for (int i = 0; i < limit - id_limit; i++) {
        sId = "0" + sId;
      }
    }
    return sId;
  }
  
  public static String alternateZero(int id, int limit)
  {
    String sId = String.valueOf(id);
    int id_limit = sId.length();
    if (id_limit < limit) {
      for (int i = 0; i < limit - id_limit; i++) {
        sId = "0" + sId;
      }
    }
    return sId;
  }
  
  public static String getDeptAllNameById(Integer id, Map<Integer, DepartmentInfo> result)
    throws Exception
  {
    DepartmentInfo am = (DepartmentInfo)result.get(id);
    String allName = (am == null) || (am.getDept_name() == null) ? "" : am.getDept_name();
    while (am != null)
    {
      am = (DepartmentInfo)result.get(am.getParent_id());
      if (am == null) {
        break;
      }
      allName = am.getDept_name() + " >> " + allName;
    }
    return allName;
  }
  
  public static String getMenuAllNameById(Integer id, Map<Integer, MenuInfo> result)
    throws Exception
  {
    MenuInfo am = (MenuInfo)result.get(id);
    String allName = (am == null) || (am.getMenu_name() == null) ? "" : am.getMenu_name();
    while (am != null)
    {
      am = (MenuInfo)result.get(am.getParent_id());
      if (am == null) {
        break;
      }
      allName = am.getMenu_name() + " >> " + allName;
    }
    return allName;
  }
  
  public static String getMenuAllIdsById(Integer id, Map<Integer, MenuInfo> result)
    throws Exception
  {
    MenuInfo am = (MenuInfo)result.get(id);
    String allIds = am == null ? "" : am.getId().toString();
    while (am != null)
    {
      am = (MenuInfo)result.get(am.getParent_id());
      if (am == null) {
        break;
      }
      allIds = am.getId().toString() + "," + allIds;
    }
    return allIds;
  }
  
  public static boolean containsKey(String str, String key)
  {
    if ((StringUtils.isEmpty(str)) || (StringUtils.isEmpty(key))) {
      return false;
    }
    if (str.indexOf(key) >= 0) {
      return true;
    }
    return false;
  }
  
  public static boolean containsKeys(String str, String[] keys)
  {
    boolean isTrue = false;
    if ((StringUtils.isEmpty(str)) || (keys.length == 0)) {
      return isTrue;
    }
    String[] arrayOfString;
    int j = (arrayOfString = keys).length;
    for (int i = 0; i < j; i++)
    {
      String key = arrayOfString[i];
      if (containsKey(str, key))
      {
        isTrue = true;
        break;
      }
    }
    return isTrue;
  }
  
  public static Map<Integer, String> convertToMap(FlowInfo flowInfo)
  {
    if (flowInfo == null) {
      return null;
    }
    Map<Integer, String> map = new HashMap();
    String[] arrayOfString;
    int j = (arrayOfString = StringUtils.defaultIfEmpty(flowInfo.getStep_info(), "").split("]")).length;
    for (int i = 0; i < j; i++)
    {
      String step = arrayOfString[i];
      int emp_id = Integer.parseInt(step.split(",")[1].split("\\|")[0]);
      map.put(Integer.valueOf(emp_id), step.replace("[", ""));
    }
    return map;
  }
  
  public static long getDaySub(String beginDateStr, String endDateStr)
  {
    long day = 0L;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      Date beginDate = format.parse(beginDateStr);
      Date endDate = format.parse(endDateStr);
      day = (endDate.getTime() - beginDate.getTime()) / 86400000L + 1L;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return day;
  }
  
  public static long getDaySub(Date beginDate, Date endDate)
  {
    long day = 0L;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try
    {
      day = (format.parse(format.format(endDate)).getTime() - format.parse(format.format(beginDate)).getTime()) / 86400000L + 1L;
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return day;
  }
  
  public static boolean hasLeave(Date innerDate, String try_state, int try_month)
  {
    if ((innerDate == null) || (StringUtils.isEmpty(try_state)) || (try_state.equals(Global.try_state[0]))) {
      return false;
    }
    Calendar c = Calendar.getInstance();
    Date now = c.getTime();
    
    c.setTime(innerDate);
    c.add(2, try_month);
    if ((now.getTime() > c.getTimeInMillis()) && (try_state.equals(Global.try_state[1]))) {
      return true;
    }
    return false;
  }
  
  public static int serialDays(Long begin, List<Long> dates)
  {
    int count = 1;
    if ((dates == null) || (begin == null)) {
      return count;
    }
    try
    {
      long max = 0L;long min = 0L;
      for (int i = 0; i < dates.size(); i++)
      {
        if (min > ((Long)dates.get(i)).longValue()) {
          min = ((Long)dates.get(i)).longValue();
        }
        if (max < ((Long)dates.get(i)).longValue()) {
          max = ((Long)dates.get(i)).longValue();
        }
      }
      for (long i = begin.longValue() + getTimeInMillis(1.0D, "d"); i <= max; i += getTimeInMillis(1.0D, "d"))
      {
        if (!dates.contains(Long.valueOf(i))) {
          break;
        }
        count++;
      }
      for (long i = begin.longValue() - getTimeInMillis(1.0D, "d"); i >= min; i -= getTimeInMillis(1.0D, "d"))
      {
        if (!dates.contains(Long.valueOf(i))) {
          break;
        }
        count++;
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return count;
  }
  
  public static Map<String, Double> getAnnualLeaveDays(EmployeeInfo ei)
  {
    String annualStr = "annualDays";
    String companyStr = "companyDays";
    String totalStr = "totalDays";
    String nowYearStr = "nowYear";
    String empIdStr = "emp_id";
    double companyDays = 0.0D;double annualDays = 0.0D;double totalDays = 0.0D;
    int nowYear = 0;
    int emp_id = 0;
    if ((ei != null) && (ei.getId() != null) && (ei.getId().intValue() > 0))
    {
      emp_id = ei.getId().intValue();
      Calendar c = Calendar.getInstance();
      Date now = c.getTime();
      nowYear = c.get(1);
      c.setTime(ei.getEmp06());
      int firstYear = c.get(1);
      int shgl = nowYear - firstYear;
      if (shgl >= 0)
      {
        List<AnnualLeave> list = (List<AnnualLeave>)Global.annualLeaveMap.get(Global.hrStatusMap.get(ei.getHr_status_id()));
		for (AnnualLeave al : list) {
          if ((shgl >= al.getWork_down().intValue()) && (shgl < al.getWork_up().intValue()))
          {
            annualDays = al.getLeave01().intValue();
            companyDays = al.getLeave02().intValue();
            totalDays = al.getLeave03().intValue();
          }
        }
        c.set(nowYear, 0, 1);
        Date firstDay = c.getTime();
        c.set(nowYear, 11, 31);
        Date lastDay = c.getTime();
        if ((ei.getState().intValue() == 1) && 
          (ei.getEmp08().getTime() > firstDay.getTime()))
        {
          annualDays = floor(getDaySub(ei.getEmp08(), lastDay) / (getDaysForYear(now) * 1.0D) * annualDays);
          totalDays = round(getDaySub(ei.getEmp08(), lastDay) / (getDaysForYear(now) * 1.0D) * totalDays);
          companyDays = totalDays - annualDays;
        }
      }
    }
    Map<String, Double> map = new HashMap();
    map.put(empIdStr, Double.valueOf(emp_id * 1.0D));
    map.put(annualStr, Double.valueOf(annualDays < 0.0D ? 0.0D : annualDays));
    map.put(companyStr, Double.valueOf(companyDays < 0.0D ? 0.0D : companyDays));
    map.put(totalStr, Double.valueOf(totalDays < 0.0D ? 0.0D : totalDays));
    map.put(nowYearStr, Double.valueOf(nowYear * 1.0D));
    return map;
  }
  
  public static int getMaxDay(int year, int month)
    throws ParseException
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    c.setTime(sdf.parse(year + "-" + month + "-01"));
    return c.getActualMaximum(5);
  }
  
  public static boolean isFullYear(Date date)
    throws Exception
  {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance(Locale.CHINA);
    Date now = format.parse(format.format(c.getTime()));
    c.setTime(date);
    c.add(1, 1);
    Date finalDate = format.parse(format.format(c.getTime()));
    if (finalDate.getTime() <= now.getTime()) {
      return true;
    }
    return false;
  }
  
  public static int getDaysForYear(Date date)
  {
    Calendar c = Calendar.getInstance(Locale.CHINA);
    c.setTime(date);
    c.set(c.get(1), 11, 31);
    return c.get(6);
  }
  
  public static double round(double d)
  {
    DecimalFormat df = new DecimalFormat("######0.0");
    String[] dStr = Double.valueOf(d).toString().split("\\.");
    if (dStr.length > 1)
    {
      int right = Integer.parseInt(dStr[1].substring(0, 1));
      if (right >= 5) {
        dStr[1] = "5";
      } else {
        dStr[1] = "0";
      }
    }
    double da = d;
    try
    {
      da = df.parse(dStr[0] + "." + dStr[1]).doubleValue();
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return da;
  }
  
  public static double floor(double d)
  {
    return Math.floor(d);
  }
  
  public static double ceil(double d)
  {
    return Math.ceil(d);
  }
  
  public static EmployeeLeave getEmployeeLeave(Map<String, Double> annualMap)
  {
    EmployeeLeave employeeLeave = null;
    if ((annualMap != null) && (annualMap.size() > 0))
    {
      employeeLeave = new EmployeeLeave();
      employeeLeave.setState(Integer.valueOf(1));
      employeeLeave.setEmp_id(Integer.valueOf(((Double)annualMap.get("emp_id")).intValue()));
      employeeLeave.setYear(Integer.valueOf(((Double)annualMap.get("nowYear")).intValue()));
      employeeLeave.setAnnualDays((Double)annualMap.get("annualDays"));
      employeeLeave.setCompanyDays((Double)annualMap.get("companyDays"));
      employeeLeave.setTotalDays((Double)annualMap.get("totalDays"));
    }
    return employeeLeave;
  }
  
  public static Date parseDateStr(String date)
  {
    if (StringUtils.isEmpty(date)) {
      return null;
    }
    SimpleDateFormat sdf = null;
    if (date.trim().indexOf(":") > -1) {
      sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    } else {
      sdf = new SimpleDateFormat("yyyy-MM-dd");
    }
    Date d = null;
    try
    {
      d = sdf.parse(date);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return d;
  }
  
  public static int getYearOrMonthOrDay(Date date, String type)
  {
    if ((date == null) || (StringUtils.isEmpty(type))) {
      return -1;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    if (type.toUpperCase().equals("y".toUpperCase())) {
      return cal.get(1);
    }
    if (type.toUpperCase().equals("m".toUpperCase())) {
      return cal.get(2) + 1;
    }
    if (type.toUpperCase().equals("d".toUpperCase())) {
      return cal.get(5);
    }
    return -1;
  }
  
  public static long getTimeInMillis(double d, String type)
  {
    long millis = 0L;
    if (!StringUtils.isEmpty(type)) {
      if (type.equals("d")) {
        millis = (long) (d * 24.0D * 60.0D * 60.0D * 1000.0D);
      } else if (type.equals("h")) {
        millis = (long) (d * 60.0D * 60.0D * 1000.0D);
      } else if (type.equals("m")) {
        millis = (long) (d * 60.0D * 1000.0D);
      } else if (type.equals("s")) {
        millis = (long) (d * 1000.0D);
      }
    }
    return millis;
  }
  
  public static Date addDate(Date date, String type, int v)
  {
    if ((date == null) || (StringUtils.isEmpty(type))) {
      return null;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    if (type.toUpperCase().equals("y".toUpperCase())) {
      cal.add(1, v);
    } else if (type.toUpperCase().equals("m".toUpperCase())) {
      cal.add(2, v);
    } else if (type.toUpperCase().equals("d".toUpperCase())) {
      cal.add(5, v);
    }
    return cal.getTime();
  }
  
  public static List<String> getWeekDays(Date date)
    throws ParseException
  {
    List<String> weeDays = new ArrayList();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal = Calendar.getInstance();
    Date time = date;
    cal.setTime(time);
    
    int dayWeek = cal.get(7);
    if (1 == dayWeek) {
      cal.add(5, -1);
    }
    cal.setFirstDayOfWeek(2);
    
    int day = cal.get(7);
    cal.add(5, cal.getFirstDayOfWeek() - day);
    weeDays.add(sdf.format(cal.getTime()));
    cal.add(5, 1);
    weeDays.add(sdf.format(cal.getTime()));
    cal.add(5, 1);
    weeDays.add(sdf.format(cal.getTime()));
    cal.add(5, 1);
    weeDays.add(sdf.format(cal.getTime()));
    cal.add(5, 1);
    weeDays.add(sdf.format(cal.getTime()));
    cal.add(5, 1);
    weeDays.add(sdf.format(cal.getTime()));
    cal.add(5, 1);
    weeDays.add(sdf.format(cal.getTime()));
    return weeDays;
  }
  
  public static double getHourByTimeInMillis(long millis)
  {
    DecimalFormat df = new DecimalFormat("######0.0");
    double hour = 0.0D;
    try
    {
      hour = df.parse(String.valueOf(millis * 1.0D / 60.0D / 60.0D / 1000.0D)).doubleValue();
    }
    catch (ParseException e)
    {
      e.printStackTrace();
    }
    return hour;
  }
  
  public static String getEmpStateName(int state)
  {
    String name = "";
    switch (state)
    {
    case 1: 
      name = Global.employee_state[0];
      break;
    case 2: 
      name = Global.employee_state[1];
      break;
    case 3: 
      name = Global.employee_state[2];
      break;
    }
    return name;
  }
  
  public static int getAge(Date birthDate)
  {
    if (birthDate == null) {
      throw 
        new RuntimeException("��������������null");
    }
    int age = 0;
    
    Date now = new Date();
    
    SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
    SimpleDateFormat format_M = new SimpleDateFormat("MM");
    
    String birth_year = format_y.format(birthDate);
    String this_year = format_y.format(now);
    
    String birth_month = format_M.format(birthDate);
    String this_month = format_M.format(now);
    
    age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);
    if (this_month.compareTo(birth_month) < 0) {
      age--;
    }
    if (age < 0) {
      age = 0;
    }
    return age;
  }
  
  public static String getHrClose(String roleCodes, String url)
  {
    roleCodes = StringUtils.defaultIfEmpty(roleCodes, "");
    url = StringUtils.defaultIfEmpty(url, "");
    StringBuffer sb = new StringBuffer("");
    if ((roleCodes.indexOf(Global.default_role[1]) > -1) && (url.length() > 0))
    {
      sb.append("{");
      sb.append("\ttext:'����',");
      sb.append("\ticonCls:'icon-cut',");
      sb.append("\thandler:function(){");
      sb.append("\t\tvar rows = _dataGrid.datagrid('getSelections');");
      sb.append("\t\tif(rows.length==0){");
      sb.append("\t\t\tparent.showInfo('������������������');");
      sb.append("\t\t\treturn false;");
      sb.append("\t\t}");
      sb.append("\t\tif (confirm('����������')) {");
      sb.append("\t\tvar param = {};");
      sb.append("\t\tfor(var i=0;i<rows.length;i++){");
      sb.append("\t\t\tparam['id']=rows[i].id;");
      sb.append("\t\t\tajaxUrlFalse(ctx+'" + url + "',param,function(json){");
      sb.append("\t\t\t\tif(json.flag=='0'){");
      sb.append("\t\t\t    \t\tparent.showMsgInfo(json.msg);");
      sb.append("\t\t\t\t}else{");
      sb.append("\t\t\t\t}");
      sb.append("\t\t\t});");
      sb.append("\t\t}");
      sb.append("\t\tdocument.location.reload(true);");
      sb.append("\t\t}");
      sb.append("\t}");
      sb.append("},");
    }
    return sb.toString();
  }
  
  public static boolean isNullForList(List<Object> list)
  {
    boolean isTrue = false;
    if ((list == null) || (list.size() == 0)) {
      isTrue = true;
    } else {
      for (Object o : list) {
        if (o == null)
        {
          isTrue = true;
          break;
        }
      }
    }
    return isTrue;
  }
  
  public static Map<Integer, Object> sortMapByKey(Map<Integer, Object> oriMap)
  {
    if ((oriMap == null) || (oriMap.isEmpty())) {
      return null;
    }
    Map<Integer, Object> sortedMap = new TreeMap<Integer, Object>(new Comparator<Object>()
    {
      public int compare(Integer key1, Integer key2)
      {
        int intKey1 = 0;int intKey2 = 0;
        try
        {
          intKey1 = key1.intValue();
          intKey2 = key2.intValue();
        }
        catch (Exception e)
        {
          intKey1 = 0;
          intKey2 = 0;
        }
        return intKey1 - intKey2;
      }

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		return 0;
	}
    });
    sortedMap.putAll(oriMap);
    return sortedMap;
  }
  
  public static String getOperator(UserInfo user)
  {
    if ((user != null) && (user.getId().intValue() > 0)) {
      return user.getZh_name() + "(ID:" + user.getName() + ")";
    }
    return "";
  }
  
  private static int getInt(String str)
  {
    int i = 0;
    try
    {
      Pattern p = Pattern.compile("^\\d+");
      Matcher m = p.matcher(str);
      if (m.find()) {
        i = Integer.valueOf(m.group()).intValue();
      }
    }
    catch (NumberFormatException e)
    {
      e.printStackTrace();
    }
    return i;
  }
  
  public static Map<Integer, Object> getMonthObject(Map<Integer, Object> monthData)
  {
    if (monthData == null) {
      monthData = new TreeMap();
    }
    for (int i = 1; i <= 12; i++) {
      monthData.put(Integer.valueOf(i), new Object());
    }
    return monthData;
  }
  
  public static Map<Integer, Object> getDayObject(Map<Integer, Object> dayData)
  {
    return getDayObject(dayData, null);
  }
  
  public static Map<Integer, Object> getDayObject(Map<Integer, Object> dayData, Date date)
  {
    if (dayData == null) {
      dayData = new TreeMap();
    }
    int day = 31;
    if (date != null)
    {
      Calendar c = Calendar.getInstance();
      c.setTime(date);
      day = c.getActualMaximum(5);
    }
    for (int i = 1; i <= day; i++) {
      dayData.put(Integer.valueOf(i), new Object());
    }
    return dayData;
  }
  
  public static Map<Integer, Object> getDay24HourObject(Map<Integer, Object> day24HourData)
  {
    if (day24HourData == null) {
      day24HourData = new TreeMap();
    }
    int hour = 24;
    for (int i = 0; i < hour; i++) {
      day24HourData.put(Integer.valueOf(i), new Object());
    }
    return day24HourData;
  }
  
  public static Map<Integer, Object> get7To7HourObject(Map<Integer, Object> day24HourData)
  {
    if (day24HourData == null) {
      day24HourData = new LinkedHashMap();
    }
    for (int i = 7; i <= 23; i++) {
      day24HourData.put(Integer.valueOf(i), new Object());
    }
    for (int i = 0; i < 7; i++) {
      day24HourData.put(Integer.valueOf(i), new Object());
    }
    return day24HourData;
  }
  
  public static Date getIntervalDate(String md)
    throws Exception
  {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    int nowYear = c.get(1);
    if (c.getTimeInMillis() > sdf.parse(nowYear + "-" + md).getTime()) {
      return sdf.parse(nowYear + 1 + "-" + md);
    }
    return sdf.parse(nowYear + "-" + md);
  }
  
  public static String subStr(String str, String split)
  {
    if (str == null) {
      return "";
    }
    int index = str.indexOf(split);
    return index > -1 ? str.substring(0, index) : str;
  }
  
  public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",  
      "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",  
      "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",  
      "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",  
      "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",  
      "W", "X", "Y", "Z" };  


	public static String generateShortUuid() {  
	StringBuffer shortBuffer = new StringBuffer();  
	String uuid = UUID.randomUUID().toString().replace("-", "");  
	for (int i = 0; i < 8; i++) {  
	  String str = uuid.substring(i * 4, i * 4 + 4);  
	  int x = Integer.parseInt(str, 16);  
	  shortBuffer.append(chars[x % 0x3E]);  
	}  
	  return shortBuffer.toString();  
	}  
  
  public static void main(String[] arg)
  {
   /* System.out.println(formatDouble1(0.1234523D));
    System.out.println(OperaColor.toHex(228, 50, 43));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar c = Calendar.getInstance();
    Long begin = Long.valueOf(1483545600000L + getTimeInMillis(1.0D, "d"));
    c.setTimeInMillis(begin.longValue());System.out.println(sdf.format(c.getTime()));
    List<Long> dates = new ArrayList();
    dates.add(Long.valueOf(begin.longValue() + getTimeInMillis(1.0D, "d")));
    c.setTimeInMillis(((Long)dates.get(0)).longValue());System.out.println(sdf.format(c.getTime()));
    dates.add(Long.valueOf(begin.longValue() + getTimeInMillis(2.0D, "d")));
    c.setTimeInMillis(((Long)dates.get(1)).longValue());System.out.println(sdf.format(c.getTime()));
    dates.add(Long.valueOf(begin.longValue() + getTimeInMillis(3.0D, "d")));
    c.setTimeInMillis(((Long)dates.get(2)).longValue());System.out.println(sdf.format(c.getTime()));*/
    
    System.out.println(generateShortUuid());
    
    StringBuilder str=new StringBuilder();//定义变长字符串
    Random random=new Random();
    //随机生成数字，并添加到字符串
    for(int i=0;i<10;i++){
        str.append(random.nextInt(10));
    }
    //将字符串转换为数字并输出
    System.out.println("FII"+str);//Faurecia Improvement Idea 
    
    Random ran=new Random();
    int a=ran.nextInt(99999999);
    int b=ran.nextInt(99999999);
    long l=a*10000000L+b; 
    System.out.println("FII"+String.valueOf(l));

  }
}
