package com.aqap.matrix.faurecia.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Maps;

public class SearchFilter
{
  public String fieldName;
  public Object value;
  public NewOperator operator;

  public SearchFilter(String fieldName, NewOperator operator, Object value)
  {
    this.fieldName = fieldName;
    this.value = value;
    this.operator = operator;
  }

  public static Map<String, SearchFilter> parse(Map<String, Object> searchParams)
  {
    Map filters = Maps.newHashMap();

    for (Map.Entry entry : searchParams.entrySet())
    {
      String key = (String)entry.getKey();
      Object value = entry.getValue();
      if (!StringUtils.isBlank((String)value))
      {
        String[] names = StringUtils.split(key, "_");
        if (names.length != 2) {
          throw new IllegalArgumentException(key + " is not a valid search filter name");
        }
        String filedName = names[1];
        NewOperator operator = NewOperator.valueOf(names[0]);

        SearchFilter filter = new SearchFilter(filedName, operator, value);
        filters.put(key, filter);
      }
    }
    return filters;
  }

  public static enum NewOperator
  {
    EQ, LIKE, GT, LT, GTE, LTE,NOTEQ;
  }
}