package com.sduhyd.blog.dao;

import com.sduhyd.blog.utils.JdbcPool;
import com.sduhyd.blog.utils.SQL_JDBC;

import java.util.ArrayList;


/**
 * 想写一个通用的DAO，但是SQL太多太杂了，写不出来
 */
public class GenericDao {
    protected String tableName;
    protected ArrayList<String> PK;
    public void creation(String tableName,String...PK){
        this.tableName=tableName;
        if(PK!=null){
            for(int i=0;i<PK.length;i++){
                this.PK.add(PK[i]);
            }
        }
    }

    public void readByPK(String...conditions){
        String sql="selct *from"+tableName+"where ";
        if(conditions!=null){
            for(int i=0;i<conditions.length;i++){
                sql+=PK.get(i)+"=? ";
            }
        }

        SQL_JDBC.prepareStatement(sql,conditions);
    }
    public void updateByPK(){

    }
    public void deleteByPK(){

    }
}
