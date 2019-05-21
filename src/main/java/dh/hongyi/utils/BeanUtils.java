package dh.hongyi.utils;

import dh.hongyi.annotation.Column;
import dh.hongyi.annotation.FieldCheck;
import dh.hongyi.annotation.FieldMapping;
import dh.hongyi.exception.GenericBusinessException;
import dh.hongyi.exception.MappingException;
import dh.hongyi.pojo.ErrorEntity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 反射+注解知识运用实现操作实体字段的工具类
 * @author  HelloLight
 */
public class BeanUtils {

    /**
     * 初始化一个空集合对象
     */
    private static final List<ErrorEntity> EMPTY_LIST = Collections.emptyList();

    /**
     * 创建一个线程安全的SimpleDateFormat实例
     */
    private static ThreadLocal<DateFormat> LOCAL_DATE_FORMAT = new ThreadLocal<DateFormat>(){
        @Override
        protected SimpleDateFormat initialValue(){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    /**
     * 运用反射+注解进行字段赋值</br>
     * 将source对象中的字段根据@see FieldMapping 注解解析对应的字段名,然后将source字段值赋值给target对象字段名</br>
     * source对象字段必须与target对象字段一致,才能被封装上</br>
     * @param source 来源实体
     * @param target 目标实体
     * @throws GenericBusinessException
     * @throws MappingException
     */
    public static void copyProperties(Object source,Object target) throws GenericBusinessException, MappingException {
        isNull(source," source not allowed to be empty.");
        isNull(target," target not allowed to be empty.");

        Field[] sourceFields = source.getClass().getDeclaredFields();
        Field[] targetFields = target.getClass().getDeclaredFields();
        try {
            for (Field sourceField : sourceFields) {
                FieldMapping mapping = sourceField.getAnnotation(FieldMapping.class);
                if(mapping == null) continue;
                String fieldName = mapping.field();
                String description = mapping.description();
                sourceField.setAccessible(true);
                for (Field targetField : targetFields) {
                    targetField.setAccessible(true);
                    if(fieldName.equalsIgnoreCase(targetField.getName())
                            || fieldName.equalsIgnoreCase(getFieldColumnName(target, targetField))){
                        setFieldVal(source,sourceField,target,targetField,description);
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            ExceptionUtils.throwsGenericException(e);
        } catch (IllegalAccessException e) {
            ExceptionUtils.throwsGenericException(e);
        } catch (MappingException e) {
            ExceptionUtils.throwsMpapingException(e.getMessage());
        }
    }

    /**
     * 根据对象和指定字段获取{@link dh.hongyi.annotation.Column}注解上的name值
     * @param object 目标对象
     * @param targetField 指定字段
     * @return 字段加的Column注解name属性值
     * @throws GenericBusinessException
     */
    public static String getFieldColumnName(Object object,Field targetField) throws GenericBusinessException{
        if(Modifier.isFinal(targetField.getModifiers()) ||
                Modifier.isStatic(targetField.getModifiers())
                || "List".equalsIgnoreCase(targetField.getType().getSimpleName())) return "";
        String name = targetField.getName();
        Column column = targetField.getAnnotation(Column.class);
        if(column != null) return column.name();
        try {
            String fieldGetMethod = name.substring(0, 1).toUpperCase()+name.substring(1,name.length());
            Method method = object.getClass().getDeclaredMethod("get"+fieldGetMethod);
            column = method.getAnnotation(Column.class);
        }
        catch (SecurityException e) {
            ExceptionUtils.throwsGenericException(" Current environment is not safe: " + e);
        }
        catch (NoSuchMethodException e) {
            ExceptionUtils.throwsGenericException("Expected method not found: " + e);
        }
        return column == null ? "" : column.name();
    }

    /**
     * 字段赋值处理
     * @param source 原对象
     * @param sourceField  原对象字段
     * @param target  目标对象
     * @param targetField 目标对象字段
     * @param fieldDesc 字段描述
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws MappingException
     */
    private static void setFieldVal(Object source, Field sourceField,Object target, Field targetField, String fieldDesc) throws IllegalArgumentException, IllegalAccessException, MappingException {
        String fieldTypeName = targetField.getType().getName();
        String fieldObjStrVal = sourceField.get(source) == null ? "" : String.valueOf(sourceField.get(source)) ;
        if ("java.math.BigDecimal".equals(fieldTypeName)) {
            if(!checkStrIsNumber(fieldObjStrVal)){
                ExceptionUtils.throwsMpapingException(sourceField, fieldDesc, fieldObjStrVal,"非数字!");
            }
            targetField.set(target,strToBigdecimal(fieldObjStrVal));
        } else if ("java.lang.Long".equals(fieldTypeName) || "long".equals(fieldTypeName)) {
            if(!checkStrIsNumber(fieldObjStrVal)){
                ExceptionUtils.throwsMpapingException(sourceField, fieldDesc, fieldObjStrVal,"非数字!");
            }
            targetField.set(target, strToLong(fieldObjStrVal));
        } else if ("java.lang.Double".equals(fieldTypeName) || "double".equals(fieldTypeName)) {
            if(!checkStrIsNumber(fieldObjStrVal)){
                ExceptionUtils.throwsMpapingException(sourceField, fieldDesc, fieldObjStrVal,"非数字!");
            }
            targetField.set(target, strToDouble(fieldObjStrVal));
        } else if ("java.lang.Float".equals(fieldTypeName) || "float".equals(fieldTypeName)) {
            if(!checkStrIsNumber(fieldObjStrVal)){
                ExceptionUtils.throwsMpapingException(sourceField, fieldDesc, fieldObjStrVal,"非数字!");
            }
            targetField.set(target, strToFloat(fieldObjStrVal));
        } else if ("java.util.Date".equals(fieldTypeName)) {
            if(isEmpty(fieldObjStrVal)) return;
            try {
                Date date = LOCAL_DATE_FORMAT.get().parse(fieldObjStrVal);
                targetField.set(target, date);
            } catch (ParseException e) {
                ExceptionUtils.throwsMpapingException(sourceField, fieldDesc, fieldObjStrVal,"出现日期解析错误(正确格式为yyyy-MM-dd HH:mm:ss)!");
            }
        } else {
            targetField.set(target,fieldObjStrVal);
        }
    }

    /**
     * 校验传入的对象字段是否为空</br>
     * @see  {@link dh.hongyi.annotation.FieldCheck} 注解类
     * @param targetObj 需要校验的对象
     * @return 如果传入的对象字段为空,直接返回空集合对象,否则返回校验结果
     * @throws GenericBusinessException
     */
    public static List<ErrorEntity> checkFieldIsEmpty(Object targetObj) throws GenericBusinessException {

        isNull(targetObj," targetObj not allowed to be empty.");

        Field[] fields = targetObj.getClass().getDeclaredFields();
        if(fields == null || fields.length <= 0) {
            return EMPTY_LIST;
        }
        List<ErrorEntity> errorEntities = Collections.synchronizedList(new ArrayList<ErrorEntity>(fields.length));
        try {
            for (Field field:fields) {
                FieldCheck fieldCheck = field.getAnnotation(FieldCheck.class);
                if (fieldCheck == null || fieldCheck.isAllowEmpty()) continue;
                field.setAccessible(true);
                Object object = field.get(targetObj);
                if(object == null || "".equals(object)){
                    errorEntities.add(new ErrorEntity(field.getName(),fieldCheck.description()));
                }
            }
        }
        catch (IllegalAccessException e) {
            throw new GenericBusinessException(e.getMessage());
        }
        return errorEntities;
    }

    /**
     * 字符串转Long类型
     * @param str 参数值
     * @return 如果参数值为空则返回0L,否则返回转换后的Long值
     */
    public static Long strToLong(String str){
        return isEmpty(str) ? 0L : Long.valueOf(str);
    }
    /**
     * 字符串转Double类型
     * @param str 参数值
     * @return 如果参数值为空则返回0D,否则返回转换后的Double值
     */
    public static Double strToDouble(String str){
        return isEmpty(str) ? 0D : Double.valueOf(str);
    }
    /**
     * 字符串转Float类型
     * @param str 参数值
     * @return 如果参数值为空则返回0F,否则返回转换后的Float值
     */
    public static Float strToFloat(String str){
        return isEmpty(str) ? 0F : Float.valueOf(str);
    }
    /**
     * 字符串转BigDecimal类型
     * @param str 参数值
     * @return 如果参数值为空则返回值为0的BigDecimal对象,否则返回转换后的BigDecimal值
     */
    public static BigDecimal strToBigdecimal(String str){
        return isEmpty(str) ? BigDecimal.ZERO : new BigDecimal(str);
    }

    /**
     * 通过正则判断字符串是否为数字类型
     * @param str 参数值
     * @return 参数值为空或者不符合正则表达式返回true,否则返回false
     */
    public static boolean checkStrIsNumber(String str){
        if (StringTool.isEmpty(str))  return true;
        try {
            Float.parseFloat(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    /**
     * 判断参数值是否为空
     * @param str 参数值
     * @return 为空或者为null字符串返回true,否则返回false
     */
    public static boolean isEmpty(String str){
        return StringTool.isEmpty(str) || "null".equalsIgnoreCase(str) ? true : false;
    }

    /**
     * obj对象为空则抛异常
     * @param obj obj对象
     * @param msg 异常信息
     */
    public static void isNull(Object obj,String msg){
        if(obj == null || "".equals(obj) || "null".equals(obj)) {
            throw new IllegalArgumentException(msg);
        }
    }
}