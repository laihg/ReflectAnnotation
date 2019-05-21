package dh.hongyi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 实体字段映射,用于字段匹配赋值,省去多个字段调多次set方法赋值,</br>
 * 配合   {@link dh.hongyi.utils.BeanUtils#copyProperties(Object, Object)} 使用
 * @author HelloLight
 *
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMapping {
	/**
	 * 需要被映射的字段名称
	 * @return
	 */
	String field();
	/**
	 * 字段描述
	 * @return
	 */
	String description() default "";
}
