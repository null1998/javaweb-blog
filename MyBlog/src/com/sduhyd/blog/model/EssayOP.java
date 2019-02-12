package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.Essay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EssayOP {
    public Essay favorite(Connection conn, int essay_id, int user_id, Essay essay){
        try{
            String sql="select favorite from BLOG_TB_ESSAY where id=?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,essay_id);
            ResultSet rs=statement.executeQuery();
            while(rs.next()){
                Integer favorite=rs.getInt("favorite");
                EssayIDManger eim=new EssayIDManger();
                if(!eim.isFavorite(conn,essay_id,user_id)) {
                    favorite++;
                    String sql1 = "update BLOG_TB_ESSAY set favorite=? where id=?";
                    PreparedStatement statement1 = conn.prepareStatement(sql1);
                    statement1.setInt(1, favorite);
                    statement1.setInt(2, essay_id);
                    statement1.executeUpdate();
                    String sql2="insert into BLOG_TB_ESSAY_FAVORITE(user_id,essay_id)values(?,?)";
                    PreparedStatement statement2=conn.prepareStatement(sql2);
                    statement2.setInt(1,user_id);
                    statement2.setInt(2,essay_id);
                    statement2.executeUpdate();
                    statement1.close();
                    statement2.close();
                }else if(user_id!=0){
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
                essay.setFavorite(favorite);

            }
            rs.close();
            statement.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return essay;
    }


}
