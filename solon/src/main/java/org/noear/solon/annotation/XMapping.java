package org.noear.solon.annotation;

import org.noear.solon.core.XMethod;

import java.lang.annotation.*;


//:: /xxx/*
//:: /xxx/*.js
//:: /xxx/**
//:: /xxx/**/$*
//:: /xxx/{b_b}/{ccc}.js

/**
 * 路径印射
 *
 * 一般附加在控制器和动作上
 *
 * @author noear
 * @since 1.0
 * */
@Inherited //要可继承
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface XMapping {
    String value() default "";
    XMethod[] method() default {XMethod.HTTP};
    String produces() default "";

    /**
     * @XInterceptor 的类，以下才有效
     * */
    int index() default 0;//顺序位
    boolean before() default false;
    boolean after() default false;
}
