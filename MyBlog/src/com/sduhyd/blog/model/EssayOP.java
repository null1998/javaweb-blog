package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.Essay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EssayOP {
    SQL_JDBC sql_jdbc=new SQL_JDBC();
    public Essay favorite(Connection conn, int essay_id, int user_id, Essay essay){
        ResultSet rs=null;
        ArrayList list=new ArrayList();
        try{
            String sql="select favorite from BLOG_TB_ESSAY where id=?";
            list.add(essay_id);
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql,list);
            list.clear();
            while(rs.next()){
                Integer favorite=rs.getInt("favorite");
                EssayIDManger eim=new EssayIDManger();
                if(!eim.isFavorite(conn,essay_id,user_id)) {
                    favorite++;
                    String sql1 = "update BLOG_TB_ESSAY set favorite=? where id=?";
                    list.add(favorite);
                    list.add((essay_id));
                    sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
                    list.clear();
                    String sql2="insert into BLOG_TB_ESSAY_FAVORITE(user_id,essay_id)values(?,?)";
                    list.add(user_id);
                    list.add(essay_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql2,list);
                }else if(user_id!=0){
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
                essay.setFavorite(favorite);

            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return essay;
    }


}
