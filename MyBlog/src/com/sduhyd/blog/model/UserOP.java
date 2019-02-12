package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;

public class UserOP {
     public ArrayList<Essay> myFavorite(Connection conn,int user_id){

        ArrayList<Essay> arrayList = new ArrayList<>();
        ArrayList<Integer> index=new ArrayList<>();
        try{
            String sql = "select *from BLOG_TB_ESSAY_FAVORITE where user_id=?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,user_id);
            ResultSet rs=statement.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt("essay_id"));
                index.add(rs.getInt("essay_id"));
            }
            rs.close();
            statement.close();
            for(int i=0;i<index.size();i++){
                String sql2="select *from BLOG_TB_ESSAY where id=?";
                PreparedStatement statement2=conn.prepareStatement(sql2);
                statement2.setInt(1,index.get(i));
                ResultSet rs2=statement2.executeQuery();
                while (rs2.next()){
                    Essay essay = new Essay();
                    essay.setId(rs2.getInt("id"));
                    essay.setUser_id(rs2.getInt("user_id"));
                    essay.setUsername(rs2.getString("username"));
                    essay.setTitle(rs2.getString("title"));
                    essay.setArticle(rs2.getString("article"));
                    essay.setCreation_time(rs2.getDate("creation_time"));
                    essay.setModify_time(rs2.getDate("modify_time"));
                    essay.setStar(rs2.getInt("star"));
                    essay.setDiss(rs2.getInt("diss"));
                    essay.setComments(rs2.getInt("comments"));
                    essay.setVisitor(rs2.getInt("visitor"));
                    essay.setFavorite(rs2.getInt("favorite"));
                    arrayList.add(essay);
                }
                rs2.close();
                statement2.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return arrayList;
    }
    public void deleteMyFavorite(Connection conn,int user_id,int essay_id){
        try{
            String sql="select favorite from BLOG_TB_ESSAY where id=?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,essay_id);
            ResultSet rs=statement.executeQuery();
            while(rs.next()){
                Integer favorite=rs.getInt("favorite");
                    favorite--;
                    String sql1 = "update BLOG_TB_ESSAY set favorite=? where id=?";
                    PreparedStatement statement1 = conn.prepareStatement(sql1);
                    statement1.setInt(1, favorite);
                    statement1.setInt(2, essay_id);
                    statement1.executeUpdate();
                    String sql2="delete from BLOG_TB_ESSAY_FAVORITE where user_id=? and essay_id=?";
                    PreparedStatement statement2=conn.prepareStatement(sql2);
                    statement2.setInt(1,user_id);
                    statement2.setInt(2,essay_id);
                    statement2.executeUpdate();
                    statement1.close();
                    statement2.close();
            }
            rs.close();
            statement.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
