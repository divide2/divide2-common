package com.divide2.search.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create by bvvy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})

public @interface Conditioner {


    SearchWay way() default SearchWay.LIKE;

    String startName() default "";

    String endName() default "";

}
