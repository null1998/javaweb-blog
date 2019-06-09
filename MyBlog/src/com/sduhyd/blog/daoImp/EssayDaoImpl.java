package com.sduhyd.blog.daoImp;

import com.sduhyd.blog.utils.SQL_JDBC;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * 因为暂时用不到DAO的接口，所以先直接写实现类
 */
public class EssayDaoImpl{

    /**
     * CRUD的R
     */

    /**
     * where只有一个值的
     * @param attribute
     * @param value
     * @return
     */
    public static List<Map<String,Object>> read(String attribute, Object value){
        String sql = "select *from BLOG_TB_ESSAY where "+attribute+" = ?";
        List<Map<String,Object>> list=SQL_JDBC.prepareStatement(sql,value);
        return list;
    }
    /**
     * 没有where的
     * @return
     */
    public static List<Map<String,Object>> read(){
        String sql = "select *from BLOG_TB_ESSAY";
        List<Map<String,Object>> list=SQL_JDBC.prepareStatement(sql);
        return list;
    }

 /**
    CRUD的U
  */

    public void addStar(Integer essay_id,Integer...number){
        addOrSubAttr(true,"star",essay_id,number);
    }
    public void subStar(Integer essay_id,Integer...number){
        addOrSubAttr(false,"star",essay_id,number);
    }

    public void addDiss(Integer essay_id,Integer...number){
        addOrSubAttr(true,"diss",essay_id,number);
    }
    public void subDiss(Integer essay_id,Integer...number){
        addOrSubAttr(false,"diss",essay_id,number);
    }

    public static void subFavorite(Integer essay_id,Integer...number){
        addOrSubAttr(false,"favorite",essay_id,number);
    }
    public static void addVisitor(Integer essay_id,Integer...number){
        addOrSubAttr(true,"visitor",essay_id,number);
    }

    /**
     * 能让Essay这个表里的某一个属性的值增加或减少，where只有主键一个值。
     * @param isAdd
     * @param attribute
     * @param value
     * @param number
     */
    public static void addOrSubAttr(boolean isAdd,String attribute,Integer value,Integer...number){
        String sql="";
        if(isAdd){
            if(number==null){
                sql="update BLOG_TB_ESSAY set "+attribute+" = "+attribute+"+1 where id=?";
            }else{
                sql="update BLOG_TB_ESSAY set "+attribute+" = "+attribute+"+"+number+" where id=?";
            }
        }else{
            if(number==null){
                sql="update BLOG_TB_ESSAY set "+attribute+" = "+attribute+"-1 where id=?";
            }else{
                sql="update BLOG_TB_ESSAY set "+attribute+" = "+attribute+"-"+number+" where id=?";
            }
        }
        SQL_JDBC.prepareStatement(sql,value);
    }

    public static  boolean insert(Object...objects){
        if(objects.length==11){
            String sql="insert into BLOG_TB_ESSAY(user_id,title,article,creation_time,modify_time,username,star,diss,comments,visitor,favorite)values(?,?,?,?,?,?,?,?,?,?,?) ";
            SQL_JDBC.prepareStatement(sql,objects);
            return true;
        }
        return false;
    }

}
