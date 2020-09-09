package com.sduhyd.blog.daoImp;

import com.sduhyd.blog.utils.SQL_JDBC;

import java.util.List;
import java.util.Map;

public class CommentDaoImpl {

    public static void addStar(Integer comment_id,Integer number){
        addOrSubAttr(true,"star",comment_id,number);
    }
    public static void subStar(Integer comment_id,Integer number){
        addOrSubAttr(false,"star",comment_id,number);
    }

    public static void addDiss(Integer comment_id,Integer number){
        addOrSubAttr(true,"diss",comment_id,number);
    }
    public static void subDiss(Integer comment_id,Integer number){
        addOrSubAttr(false,"diss",comment_id,number);
    }

    /**
     * 能让Comment这个表里的某一个属性的值增加或减少，where只有主键一个值。
     * @param isAdd
     * @param attribute
     * @param value
     * @param number
     */
    public static void addOrSubAttr(boolean isAdd,String attribute,Integer value,Integer number){
        String sql="";
        if(isAdd){
            if(number==null){
                sql="update BLOG_TB_COMMENT set "+attribute+" = "+attribute+"+1 where id=?";
            }else{
                sql="update BLOG_TB_COMMENT set "+attribute+" = "+attribute+"+"+number+" where id=?";
            }
        }else{
            if(number==null){
                sql="update BLOG_TB_COMMENT set "+attribute+" = "+attribute+"-1 where id=?";
            }else{
                sql="update BLOG_TB_COMMENT set "+attribute+" = "+attribute+"-"+number+" where id=?";
            }
        }
        SQL_JDBC.prepareStatement(sql,value);
    }

    public static List<Map<String,Object>> read(String attribute, Object value){
        String sql="select *from BLOG_TB_COMMENT where "+attribute+"=?";
        List<Map<String,Object>> list= SQL_JDBC.prepareStatement(sql,value);
        return list;
    }

    public static  boolean insert(Object...objects){
        if(objects.length==7){
            String sql="insert into BLOG_TB_COMMENT(user_id,essay_id,star,diss,username,content,creation_time)values(?,?,?,?,?,?,?) ";
            SQL_JDBC.prepareStatement(sql,objects);
            return true;
        }
        return false;
    }
}
