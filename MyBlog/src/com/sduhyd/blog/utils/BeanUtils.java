package com.sduhyd.blog.utils;

import com.sduhyd.blog.bean.Essay;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BeanUtils {

    /**
     * 利用反射调用bean的所有set方法，并把这些实例返回
     * @param object
     * @param list DAO从数据库获得的数据
     * @return
     */
    public static List<Object> setAll(Object object,List<Map<String,Object>> list){
        List objects=new LinkedList();
        Class<?> aClass=object.getClass();
        Method[] methods=aClass.getMethods();
        for(Map<String,Object> map:list){
            try{
                object=aClass.newInstance();
                for(Method method:methods){
                    if(method.getName().charAt(0)=='s'){
                        method.invoke(object,map.get(method.getName().toLowerCase().substring(3)));
                    }
                }
                objects.add(object);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return objects;
    }
}
