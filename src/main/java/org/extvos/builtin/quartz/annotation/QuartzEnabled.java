package org.extvos.builtin.quartz.annotation;

import java.lang.annotation.*;

/**
 * @author shenmc
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QuartzEnabled {
    String value() default "";
}
