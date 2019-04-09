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
        Integer favoriteCount=null;
        ArrayList list=new ArrayList();
                favoriteCount=new Utils().getFavoriteCount(conn,essay_id);
                EssayIDManger eim=new EssayIDManger();
                if(!eim.isFavorite(conn,essay_id,user_id)) {
                    favoriteCount++;
                    new Utils().updateFavoriteCountUp(conn,essay_id,user_id,favoriteCount);
                }else if(user_id!=0){
                    favoriteCount--;
                    new Utils().updateFavoriteCountDown(conn,essay_id,user_id,favoriteCount);
                }
                essay.setFavorite(favoriteCount);
        return essay;
    }


}
