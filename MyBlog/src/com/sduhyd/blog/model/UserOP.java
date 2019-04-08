package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;

public class UserOP {
     SQL_JDBC sql_jdbc=new SQL_JDBC();
     public ArrayList<Essay> myFavorite(Connection conn,int user_id){
        ResultSet rs=null;
        ArrayList list=new ArrayList();
        ArrayList<Essay> arrayList = new ArrayList<>();
        ArrayList<Integer> index=new ArrayList<>();
        try{
            String sql = "select *from BLOG_TB_ESSAY_FAVORITE where user_id=?";
            list.add(user_id);
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql,list);
            list.clear();
            while(rs.next()){
                index.add(rs.getInt("essay_id"));
            }
            rs.close();
            for(int i=0;i<index.size();i++){
                String sql2="select *from BLOG_TB_ESSAY where id=?";
                list.add(index.get(i));
                ResultSet rs2=sql_jdbc.prepareStatement("QUERY",conn,sql2,list);
                list.clear();
                while (rs2.next()){
                    Essay essay = new Essay();
                    essay.initEssay(rs2.getInt("id"),rs2.getInt("user_id"),rs2.getString("username"),rs2.getString("title"),rs2.getString("article"),rs2.getDate("creation_time"),rs2.getDate("modify_time"),rs2.getInt("star"),rs2.getInt("diss"),rs2.getInt("comments"),rs2.getInt("visitor"),rs2.getInt("favorite"));
                    arrayList.add(essay);
                }
                rs2.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return arrayList;
    }
    public void deleteMyFavorite(Connection conn,int user_id,int essay_id){
        ResultSet rs=null;
        ArrayList list=new ArrayList();
        try{
            String sql="select favorite from BLOG_TB_ESSAY where id=?";
            list.add(essay_id);
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql,list);
            list.clear();
            while(rs.next()){
                Integer favorite=rs.getInt("favorite");
                    favorite--;
                    String sql1 = "update BLOG_TB_ESSAY set favorite=? where id=?";
                    list.add(favorite);
                    list.add(essay_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
                    list.clear();
                    String sql2="delete from BLOG_TB_ESSAY_FAVORITE where user_id=? and essay_id=?";
                    list.add(user_id);
                    list.add(essay_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql2,list);
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
