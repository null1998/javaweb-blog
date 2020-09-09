package com.sduhyd.blog.service;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;
import com.sduhyd.blog.daoImp.*;
import com.sduhyd.blog.utils.BeanUtils;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.*;

public class UserAction{
    public static User login(String username,String password)throws IOException {
        List<Map<String,Object>> list= UserDaoImpl.read("username",username);
        if(list.size() == 0 || !list.get(0).get("password").equals(password)){
            return null;
        }
        return (User)BeanUtils.setAll(User.class,list).get(0);
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

    /**
     * 展示特定用户的所有收藏的文章
     * @param current_user_id
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public static List<Object> showFavorite(Integer current_user_id)throws ServletException,IOException{
        List<Map<String,Object>> list= UserEssayDaoImpl.read("FAVORITE","user_id",current_user_id);//包含所有收藏的essay_id
        List<Object> essayList=new LinkedList<>();
        for(Map<String,Object> map:list){
            List<Map<String,Object>> essay= EssayDaoImpl.read("id",map.get("essay_id"));//一个essay的所有数据
            essayList.addAll( BeanUtils.setAll(Essay.class,essay));
        }
        return essayList;

    }

    /**
     * 特定用户删除其收藏文章列表中的特定一篇文章
     * @param current_user_id
     * @param essay_id
     */
    public static void deleteFavorite(Integer current_user_id,Integer essay_id){
        EssayDaoImpl.subFavorite(essay_id, 1);
        UserEssayDaoImpl.delete("FAVORITE",current_user_id,essay_id);
    }

    /**
     * 特定用户写文章
     * @param current_user
     * @param title
     * @param article
     */
    public static void writeEssay(User current_user,String title,String article){
        Integer current_user_id=current_user.getId();
        String username = current_user.getUsername();
        EssayDaoImpl.insert(current_user_id,title,article,new Date(),new Date(),username,0,0,0,0,0);
    }

    /**
     * 特定用户访问特定文章
     * @param essay_id
     * @param current_user_id
     */
    public static void visitorEssay(Integer essay_id, Integer current_user_id){
        List<Map<String,Object>> list= UserEssayDaoImpl.read("VISITOR","user_id",current_user_id);
        for (Map map: list) {
            if (map.get("essay_id").equals(essay_id)) {
                return;
            }
        }
        UserEssayDaoImpl.insert("VISITOR", current_user_id, essay_id);
        EssayDaoImpl.addVisitor(essay_id, 1);
    }

    /***
     * 特定用户对特定文章的点赞，踩，收藏操作
     */
    public static void starEssay(Integer essay_id, Integer current_user_id) {
        List<Map<String,Object>> list= UserEssayDaoImpl.read("STAR","user_id",current_user_id);
        for (Map map: list) {
            if (map.get("essay_id").equals(essay_id)) {
                UserEssayDaoImpl.delete("STAR",current_user_id, essay_id);
                EssayDaoImpl.subStar(essay_id,1);
                return;
            }
        }
        UserEssayDaoImpl.insert("STAR", current_user_id, essay_id);
        EssayDaoImpl.addStar(essay_id, 1);
    }
    public static void dissEssay(Integer essay_id, Integer current_user_id) {
        List<Map<String,Object>> list= UserEssayDaoImpl.read("DISS","user_id",current_user_id);
        for (Map map: list) {
            if (map.get("essay_id").equals(essay_id)) {
                UserEssayDaoImpl.delete("DISS",current_user_id, essay_id);
                EssayDaoImpl.subDiss(essay_id,1);
                return;
            }
        }
        UserEssayDaoImpl.insert("DISS", current_user_id, essay_id);
        EssayDaoImpl.addDiss(essay_id, 1);
    }
    public static void favoriteEssay(Integer essay_id, Integer current_user_id) {
        List<Map<String,Object>> list= UserEssayDaoImpl.read("FAVORITE","user_id",current_user_id);
        for (Map map: list) {
            if (map.get("essay_id").equals(essay_id)) {
                UserEssayDaoImpl.delete("FAVORITE",current_user_id, essay_id);
                EssayDaoImpl.subFavorite(essay_id,1);
                return;
            }
        }
        UserEssayDaoImpl.insert("FAVORITE", current_user_id, essay_id);
        EssayDaoImpl.addFavorite(essay_id, 1);
    }

    /**
     * 特定用户对特定文章撰写评论
     * @param essay_id
     * @param comment
     * @param current_user
     */
    public static void commentEssay(Integer essay_id,String comment, User current_user){
        CommentDaoImpl.insert(current_user.getId(), essay_id, 0, 0, current_user.getUsername(), comment, new Date());
        EssayDaoImpl.addComments(essay_id, 1);

    }

    /**
     * 特定用户对特定评论的点赞、踩操作
     * @param comment_id
     * @param current_user_id
     */
    public static void starComment(Integer comment_id, Integer current_user_id) {
        List<Map<String,Object>> list= UserCommentDaoImpl.read("STAR","user_id",current_user_id);
        for (Map map: list) {
            if (map.get("comment_id").equals(comment_id)) {
                UserCommentDaoImpl.delete("STAR",current_user_id, comment_id);
                CommentDaoImpl.subStar(comment_id,1);
                return;
            }
        }
        UserCommentDaoImpl.insert("STAR", current_user_id, comment_id);
        CommentDaoImpl.addStar(comment_id, 1);
    }
    public static void dissComment(Integer comment_id, Integer current_user_id) {
        List<Map<String,Object>> list= UserCommentDaoImpl.read("DISS","user_id",current_user_id);
        for (Map map: list) {
            if (map.get("comment_id").equals(comment_id)) {
                UserCommentDaoImpl.delete("DISS",current_user_id, comment_id);
                CommentDaoImpl.subDiss(comment_id,1);
                return;
            }
        }
        UserCommentDaoImpl.insert("DISS", current_user_id, comment_id);
        CommentDaoImpl.addDiss(comment_id, 1);
    }

}
