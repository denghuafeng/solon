package org.noear.solon.annotation;

import org.noear.solon.extend.data.around.CacheInvokeHandler;

import java.lang.annotation.*;

/**
 * 缓存注解器
 *
 * @author noear
 * @since 1.0.21
 * */
@XAround(value = CacheInvokeHandler.class, index = -8)
@Inherited //要可继承
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XCache {
    /**
     * 缓存服务
     * */
    @XNote("缓存服务")
    String service() default "";

    /**
     * 0表示采用cache service的默认是境
     * */
    @XNote("缓存时间，0表示缓存服务的默认时间")
    int seconds() default 0;

    /**
     * 例：user_${user_id} ，user_id 为参数
     * */
    @XNote("缓存标签，多个以逗号隔开")
    String tags() default "";
}
