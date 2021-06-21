package plus.extvos.builtin.quartz.annotation;

import java.lang.annotation.*;

/**
 * @author Mingcai SHEN
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuartzEnabled {
    String value() default "";
}
