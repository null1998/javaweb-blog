package com.sduhyd.blog.service;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.daoImp.CommentDaoImpl;
import com.sduhyd.blog.daoImp.EssayDaoImpl;
import com.sduhyd.blog.daoImp.UserDaoImpl;
import com.sduhyd.blog.daoImp.UserEssayRelationDaoImpl;
import com.sduhyd.blog.utils.BeanUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.util.*;

public class UserAction{
    public static User login(String username,String password)throws IOException {
        List<Map<String,Object>> list= UserDaoImpl.read("username",username);
        if(!list.get(0).get("password").equals(password)){
            return null;
        }
        return (User)BeanUtils.setAll(new User(),list).get(0);
    }
    public  static boolean register(String username,String password)throws IOException{
        List<Map<String,Object>> list= UserDaoImpl.read("username",username);
        if(!list.isEmpty()){
            return false;
        }else{
           UserDaoImpl.insert(username,password);
        }
        return true;
    }
    public static List<Object> showFavorite(Integer current_user_id)throws ServletException,IOException{
        List<Map<String,Object>> list= UserEssayRelationDaoImpl.readFavorite("user_id",current_user_id);//包含所有收藏的essay_id
        List<Object> essayList=new LinkedList<>();
        for(Map<String,Object> map:list){
            List<Map<String,Object>> essay= EssayDaoImpl.read("essay_id",map.get("essay_id"));//一个essay的所有数据
            essayList.addAll( BeanUtils.setAll(new Essay(),essay));
        }
        return essayList;

    }
    public static void deleteFavorite(Integer current_user_id,Integer essay_id){
        EssayDaoImpl.subFavorite(essay_id);
        UserEssayRelationDaoImpl.deleteFavorite(current_user_id,essay_id);
    }
    public static void createEssay(User current_user,String title,String article){
        Integer current_user_id=current_user.getId();
        String username = current_user.getUsername();
        EssayDaoImpl.insert(current_user_id,title,article,new Date(),new Date(),username,0,0,0,0,0);
    }
    public static void visitor(Integer current_user_id,Integer essay_id){
        List<Map<String,Object>> list=UserEssayRelationDaoImpl.readVisitor(current_user_id,essay_id);
        if(list.isEmpty()){
            System.out.println("访问加一");
            UserEssayRelationDaoImpl.insert(current_user_id,essay_id);
            EssayDaoImpl.addVisitor(essay_id);
        }
    }
    public static void writeComments(Integer essay_id,String comment, User current_user){
        CommentDaoImpl.insert(current_user.getId(),current_user.getUsername(),essay_id,new Date(),comment,0,0);
    }

}
