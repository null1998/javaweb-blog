package com.sduhyd.blog.annotations;

import com.sduhyd.blog.foundation.annotations.ProxyAnnotation;
import com.sduhyd.blog.handlers.AuthCheckHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ProxyAnnotation(AuthCheckHandler.class)
public @interface AuthCheck {
    int CURRENT_USER_ID = 0;
    int value() default CURRENT_USER_ID;

}
