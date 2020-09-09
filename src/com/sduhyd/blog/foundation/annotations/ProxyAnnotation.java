package com.sduhyd.blog.foundation.annotations;

import com.sduhyd.blog.foundation.ProxyHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProxyAnnotation {
	Class<? extends ProxyHandler> value();
}
