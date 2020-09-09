package com.sduhyd.blog.foundation.annotations;

import com.sduhyd.blog.foundation.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
	String USE_METHOD_NAME = "";
	String value() default USE_METHOD_NAME;
	RequestMethod method() default RequestMethod.ANY;
}
