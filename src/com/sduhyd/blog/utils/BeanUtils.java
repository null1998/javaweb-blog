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
     * @param clazz
     * @param list DAO从数据库获得的数据
     * @return
     */
    public static List<Object> setAll(Class<?> clazz,List<Map<String,Object>> list){
        List objects=new LinkedList();
        Method[] methods=clazz.getMethods();
        for(Map<String,Object> map:list){
            try{
                Object instance = clazz.newInstance();
                for(Method method:methods){
                    if(method.getName().charAt(0)=='s'){
                        method.invoke(instance,map.get(method.getName().toLowerCase().substring(3)));
                    }
                }
                objects.add(instance);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return objects;
    }
}
