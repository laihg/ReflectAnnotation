package dh.hongyi.utils;

import dh.hongyi.exception.GenericBusinessException;
import dh.hongyi.exception.MappingException;

import java.lang.reflect.Field;

/**
 * 异常工具
 */
public class ExceptionUtils {

    public static void throwsMpapingException(Field sourceField, String fieldDesc, String fieldObjStrVal, String errorDesc) throws MappingException {
        throwsMpapingException(new StringBuilder(fieldDesc).append("字段[").append(sourceField.getName()).append("]的值").append(fieldObjStrVal).append(errorDesc));
    }

    /**
     * 抛出解析异常
     * @param exception
     * @throws MappingException
     */
    public static void throwsMpapingException(Exception exception) throws MappingException {
        throw new MappingException(exception.getMessage());
    }
    /**
     * 抛出解析指定错误内容
     * @param exceptionMsg
     * @throws MappingException
     */
    public static void throwsMpapingException(String exceptionMsg) throws MappingException{
        throw new MappingException(exceptionMsg);
    }

    public static void throwsMpapingException(StringBuilder exceptionMsg) throws MappingException{
        throwsMpapingException(exceptionMsg.toString());
    }

    /**
     * 抛出业务逻辑异常
     * @param exception
     * @throws GenericBusinessException
     */
    public static void throwsGenericException(Exception exception) throws GenericBusinessException {
        throw new GenericBusinessException(exception.getMessage());
    }

    /**
     * 抛出业务逻辑指定错误内容
     * @param exceptionMsg
     * @throws GenericBusinessException
     */
    public static void throwsGenericException(String exceptionMsg) throws GenericBusinessException{
        throw new GenericBusinessException(exceptionMsg);
    }
    public static void throwsGenericException(StringBuilder exceptionMsg) throws GenericBusinessException{
        throwsGenericException(exceptionMsg.toString());
    }
}
