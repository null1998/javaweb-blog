package com.sduhyd.blog.foundation;

import java.lang.annotation.Annotation;

public interface ProxyHandler<T extends Annotation> {
    Object handle(Object[] arguments, ProxyPoint point) throws Exception;
    void setProxyAnnot(T annot);

    //因为实现接口的类是抽象类，所以该类不用实现接口中的所有方法。又因为该类为抽象内部类，所以自带外部接口的抽象的handle方法
    abstract class Abstract<T extends Annotation> implements ProxyHandler<T> {
        protected T annot;
        @Override
        public void setProxyAnnot(T annot) {
            this.annot = annot;
        }
    }
}
