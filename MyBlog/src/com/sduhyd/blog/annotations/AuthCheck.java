package com.sduhyd.blog.annotations;

import com.sduhyd.blog.foundation.annotations.ProxyAnnotation;
import com.sduhyd.blog.handlers.AuthCheckHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ProxyAnnotation(AuthCheckHandler.class) // 指定校验逻辑
public @interface AuthCheck {
    int CURRENT_USER_ID=0;
    int value() default CURRENT_USER_ID;
	// 可以添加一些参数， ProxyHandler实例可以接收到这些参数，然后处理响应的逻辑
}
