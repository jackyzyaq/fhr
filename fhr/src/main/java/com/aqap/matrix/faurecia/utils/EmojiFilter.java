package com.aqap.matrix.faurecia.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
	public class EmojiFilter {
	 
    /**
	     * 检测是否有emoji字符
     * @param source
     * @return 一旦含有就抛出
	     */
	    public static boolean containsEmoji(String source) {
	        if (StringUtils.isBlank(source)) {
	            return false;
	        }
	 
        int len = source.length();
	        for (int i = 0; i < len; i++) {
	            char codePoint = source.charAt(i);
	            if (isEmojiCharacter(codePoint)) {
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    public static void main(String[] args) {
	    	byte[] b = new byte[3];
	    	
			System.out.println(filterEmoji("红啊第三大阿萨德啊是大三大四的埃索达飒沓埃索达"));
		}
	 
	    private static boolean isEmojiCharacter(char codePoint) {
	        return (codePoint == 0x0) ||
	                (codePoint == 0x9) ||
	                (codePoint == 0xA) ||
	                (codePoint == 0xD) ||
	                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
	                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
	                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	    }
	    
	    
	    public static boolean isUTF8(byte[] rawtext) {
	    	   int score = 0;
	    	   int i, rawtextlen = 0;
	    	   int goodbytes = 0, asciibytes = 0;

	    	   rawtextlen = rawtext.length;
	    	   for (i = 0; i < rawtextlen; i++) {
	    	    if ((rawtext[i] & (byte) 0x7F) == rawtext[i]) { 
	    	    
	    	     asciibytes++;

	    	    } else if (-64 <= rawtext[i] && rawtext[i] <= -33
	    	      
	    	      && 
	    	      i + 1 < rawtextlen && -128 <= rawtext[i + 1]
	    	      && rawtext[i + 1] <= -65) {
	    	     goodbytes += 2;
	    	     i++;
	    	    } else if (-32 <= rawtext[i]
	    	      && rawtext[i] <= -17
	    	      && // Three bytes
	    	      i + 2 < rawtextlen && -128 <= rawtext[i + 1]
	    	      && rawtext[i + 1] <= -65 && -128 <= rawtext[i + 2]
	    	      && rawtext[i + 2] <= -65) {
	    	     goodbytes += 3;
	    	     i += 2;
	    	    }
	    	   }
	    	   if (asciibytes == rawtextlen) {
	    	    return false;
	    	   }
	    	   score = 100 * goodbytes / (rawtextlen - asciibytes);
	    	   if (score > 98) {
	    	    return true;
	    	   } else if (score > 95 && goodbytes > 30) {
	    	    return true;
	    	   } else {
	    	    return false;
	    	   }
	    	   
	    } 
	    
	    private static boolean isEmojiCharacter1(char codepoint){
	    	 Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]");
	    	 Matcher matcher = emoji.matcher(codepoint+"");
	    	 if(matcher.find()){
	    		return true; 
	    	 }
	    	 return false;
	    }
	    
	    
	 
	    /**
	     * 过滤emoji 或者 其他非文字类型的字符
	     * @param source
	     * @return
	     */
	    public static String filterEmoji(String source) {
	        String str = "";
	        if(StringUtils.isNotEmpty(source)){
	        	int len = source.length();
		        for (int i = 0; i < len; i++){
		            char codePoint = source.charAt(i);
		            if (isEmojiCharacter(codePoint)) {
		            	str += codePoint;
		            }
		        }
	        }
	        return str;
	    }
	}