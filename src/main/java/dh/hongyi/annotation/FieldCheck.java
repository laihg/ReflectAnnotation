package dh.hongyi.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记字段是否可为空,不为空加此注解在校验时才会生效。
 * @author HelloLight
 */
@Documented
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldCheck {

    /**
     * 是否允许字段为空
     * @return 默认为false,表示不允许为空,为true表示允许为空
     */
    boolean isAllowEmpty() default false;

    /**
     * @return 字段描述
     */
    String description() default "";
}
