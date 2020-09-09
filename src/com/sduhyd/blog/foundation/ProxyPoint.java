package com.sduhyd.blog.foundation;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

//代理链的起点
public class ProxyPoint {
	private Method targetMethod;
	private Class<?> targetClass;
	private Iterator<ProxyHandler> handlerIterator;
	private Object lastReturned ;
	public ProxyPoint(Method targetMethod, Class<?> targetClass, Collection<ProxyHandler> handlers) {
		this.targetMethod = targetMethod;
		this.targetClass = targetClass;
		handlerIterator = handlers.iterator();
	}

	/**
	 * 执行代理链
	 * @param arguments
	 * @return
	 * @throws Exception
	 */
	public Object proceed(Object[] arguments) throws Exception {
		if(this.handlerIterator.hasNext()) {
			lastReturned = this.handlerIterator.next().handle(arguments, this);
		}

		return lastReturned;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}
}
