package com.sduhyd.blog.controller;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.model.Action;
import com.sduhyd.blog.model.UserAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 *在init()里利用反射将所有用得到的Class和Method存在数组里，这样每个请求都可以使用该servlet实例里公有的成员变量，
 * 不用每次请求都反射，除非该servlet生命周期结束，重新初始化。
 * 把路径写成类似/BaseServlet/UserActionProxy/login 这种类名+方法名的格式，把BaseServlet的url-pattern 改为/BaseServlet/*这样可以匹配
 * 各种类和方法。利用getPathInfo()地址，处理后再利用反射执行各种方法。
 */
@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {
    Class []classes=new Class[100];
    Method []methods=new Method[100];
    int count=0;//数组大小


    private int getClassAndMethodIndex(String className,String methodName){
        int i;
        for(i=0;i<count;i++){
            if(classes[i].getName().equals(className)&&methods[i].getName().equals(methodName)){
                System.out.println("选中"+classes[i].getName()+" "+methods[i].getName());
                return i;
            }
        }
        return -1;
    }
    @Override

    public void init(){
        String [] classNames={"com.sduhyd.blog.model.UserActionProxy","com.sduhyd.blog.model.MainPage","com.sduhyd.blog.model.EssayPage","com.sduhyd.blog.model.NullUser"};
        for(String className:classNames ){
            try{
                Class c=Class.forName(className);
                Method[] ms=c.getDeclaredMethods();
                for(Method m:ms){
                    classes[count]=c;
                    methods[count]=m;
                    count++;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] cmd=request.getPathInfo().split("/");
        String className="com.sduhyd.blog.model."+cmd[1];
        String methodName=cmd[2];

        int index = getClassAndMethodIndex(className, methodName);
        if (index != -1) {
            try {
                Class c=classes[index];
                Object object;
                if(c.getName().equals("com.sduhyd.blog.model.UserActionProxy")){
                    Constructor constructor=classes[index].getConstructor(Action.class);
                    object=constructor.newInstance(new UserAction());
                }else{
                    object=c.newInstance();
                }
                methods[index].invoke(object,request,response,getServletConfig());
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }catch (InvocationTargetException e){
                e.printStackTrace();
            }catch(InstantiationException e){
                e.printStackTrace();
            }catch (NoSuchMethodException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("找不到该类或方法");
        }
    }

}
