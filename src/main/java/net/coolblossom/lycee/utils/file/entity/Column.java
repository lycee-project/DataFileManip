package net.coolblossom.lycee.utils.file.entity;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    // Data column order (first value is 1)
    int order() default 0;

    // column name
    String name() default "";

    // data mapping rule
    Class<? extends DataFileRule>[] rule() default {};

}
