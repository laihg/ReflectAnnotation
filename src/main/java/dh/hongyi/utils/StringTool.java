package dh.hongyi.utils;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 字符串处理工具
 * @author shenzhenguo
 * @version V1.0
 */
public class StringTool {

	/**
	 * 分隔字符  , 注意：如果前后有分隔符，String.split会多出来一个。
	 * 该方法自动去掉前后分隔符再调用 String.split
	 * 注意：特殊字符 $ % 等，需要使用 转义   $, 应改为 \\$ 
	 * aibo zeng 2009-06-09
	 * @param str 需要分割的字符串
	 * @param ch 分割标识符
	 * @return
	 */
	public static String[] split(String str , char ch){
		if(str == null){
			return null;
		}
		if(str.charAt(0) == ch){
			str = str.substring(1);
		}
		if(str.charAt(str.length()-1)==ch){
			str = str.substring(0,str.length()-1);
		}
		return str.split(ch+"");
	}
	
 

	/**
	 * 判断字符串是否为null或空字符串,在模糊查询的时候很有意义
	 * @return 空返回true;非空返回false;
	 */
	public static boolean isEmpty(String str){
		return str == null || str.length() == 0 || str.trim().length() == 0;
	}
	
	/**
	 * 判断字符串是否为null或空字符串,在模糊查询的时候很有意义
	 * @return 非空返回true;空返回false;
	 */
	public static boolean isNotEmpty(String str){
		return (str != null && !"".equals(str.trim()));
	}
	/**
	 * 返回Long类型数值是否不为空
	 * @param o
	 * @return 非空返回true;空返回false;
	 */
	public static boolean isNotEmpty(Long o){
		return (o != null);
	}
	/**
	 * 返回Integer类型的值是否不为空
	 * @param o
	 * @return 非空返回true;空返回false;
	 */
	public static boolean isNotEmpty(Integer o){
		return (o != null );
	}	
	/**
	 * 返回Date类型的值是否不为空
	 * @param o
	 * @return 非空返回true;空返回false;
	 */
	public static boolean isNotEmpty(Date o){
		return (o != null );
	}
	/**
	 * 返回BigDecimal类型的值是否不为空
	 * @param o
	 * @return 非空返回true;空返回false;
	 */
	public static boolean isNotEmpty(BigDecimal o){
		return (o != null );
	}
	/**
	 * 返回Object类型的值是否不为空
	 * @param o
	 * @return 非空返回true;空返回false;
	 */
	public static boolean isNotEmpty(Object o){
		return (o != null );
	}
}