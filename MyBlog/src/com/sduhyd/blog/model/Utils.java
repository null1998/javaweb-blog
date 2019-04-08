package com.sduhyd.blog.model;
import com.sduhyd.blog.bean.Comment;
import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.bean.User;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Utils {
    SQL_JDBC sql_jdbc=new SQL_JDBC();
    public User register(Connection conn, String username, String password){
        ResultSet rs=null;
        ArrayList list=new ArrayList();
        String  sql_check = "select username from BLOG_TB_USER where username = ?";
        list.add(username);
        //检查注册用户名与昵称是否重复并返回结果
        rs=sql_jdbc.prepareStatement("QUERY",conn,sql_check,list);
        try {
            if(rs.next()){
                return null;
            }else {
                //插入注册信息
                String sql_register ="insert into BLOG_TB_USER(username,password)values(?,?)";
                list.add(password);
                sql_jdbc.prepareStatement("UPDATE",conn,sql_register,list);
                //将该用户的所有信息返回并封装。
                String sql_find="select id,username,password from BLOG_TB_USER where username = ?";
                list.remove(1);
                rs=sql_jdbc.prepareStatement("QUERY",conn,sql_find,list);
                if(rs.first()) {
                    User user = new User();
                    user.initUser(rs.getInt("id"),rs.getString("username"),rs.getString("password"));
                    return user;
                }
                rs.close();
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  User login(Connection conn,String username,String password) {
        User user=null;
        ResultSet rs=null;
        ArrayList list=new ArrayList();
        String sql_login = "select password from BLOG_TB_USER where username = ?";
        list.add(username);
        rs=sql_jdbc.prepareStatement("QUERY",conn,sql_login,list);
        try {
            if(rs.first()) {
                String qpassword = rs.getString("password");
                if(qpassword != null && qpassword.equals(password)) {
                    user = new User();
                    user.initUser(rs.getInt(1),username,qpassword);
                    return user;
                }
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    public  void createEssay(Connection conn,Integer user_id, String title, String article, Date modify_time,String username){
        ArrayList list=new ArrayList();
        String sql_create_essay="insert into BLOG_TB_ESSAY(user_id,title,article,creation_time,modify_time,username,star,diss,comments,visitor,favorite)values(?,?,?,?,?,?,?,?,?,?,?) ";
        list.add(user_id);
        list.add(title);
        list.add(article);
        list.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(modify_time));
        list.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(modify_time));
        list.add(username);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        sql_jdbc.prepareStatement("UPDATE",conn,sql_create_essay,list);
    }

    public  Essay[] showEssay(Connection conn, Integer user_id){
        ResultSet rs=null;
        //统计用户的文章数
        int count = 0;
        ArrayList list=new ArrayList();
        String selectCountSql = "select count(*) from BLOG_TB_ESSAY where user_id=?";
        list.add(user_id);
        rs=sql_jdbc.prepareStatement("QUERY",conn,selectCountSql,list);
        try {
                rs.first();
                count = rs.getInt(1);
                if (count < 1) {
                    rs.close();
                    return new Essay[0];
                }
            //开始从文章表里提取文章
            String sql_show="select *from BLOG_TB_ESSAY where user_id=?";
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql_show,list);
            Essay[] essays= new Essay[count];
            int essay_count=0;
            while(rs.next()){
                Essay essay=new Essay();
                essay.initEssay(rs.getInt("id"),user_id,rs.getString("username"),rs.getString("title"),rs.getString("article"),rs.getDate("creation_time"),rs.getDate("modify_time"),rs.getInt("star"),rs.getInt("diss"),rs.getInt("comments"),rs.getInt("visitor"),rs.getInt("favorite"));
                essays[essay_count]=essay;
                essay_count++;
            }
            rs.close();
            return essays;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Essay[0];
    }
    public void  updateEssay(Connection conn,String id,String title,String content){
            ArrayList list=new ArrayList();
            String sql_update="update BLOG_TB_ESSAY set title=?,article=? where id=?";
            list.add(title);
            list.add(content);
            list.add(id);
            sql_jdbc.prepareStatement("UPDATE",conn,sql_update,list);
    }
    public ArrayList<Essay> allEssay(Connection conn){
            ResultSet rs=null;
            ArrayList<Essay> essayArrayList = new ArrayList<>();
            String sql_all_essays = "select *from BLOG_TB_ESSAY";
            try{
                rs=sql_jdbc.prepareStatement("QUERY",conn,sql_all_essays,null);//普通的查询，没有插入数据
                while(rs.next()){
                    Essay essay = new Essay();
                    essay.initEssay(rs.getInt("id"),rs.getInt("user_id"),rs.getString("username"),rs.getString("title"),rs.getString("article"),rs.getDate("creation_time"),rs.getDate("modify_time"),rs.getInt("star"),rs.getInt("diss"),rs.getInt("comments"),rs.getInt("visitor"),rs.getInt("favorite"));
                    essayArrayList.add(essay);
                }
                rs.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            return essayArrayList;
    }
    public Essay visitor(Connection conn, int essay_id,int user_id,Essay essay){
        ResultSet rs=null;
        ArrayList list=new ArrayList();
        String sql_visitor="select visitor from BLOG_TB_ESSAY where id=?";
        list.add(essay_id);
        rs=sql_jdbc.prepareStatement("QUERY",conn,sql_visitor,list);
        try{
            while(rs.next()){
                Integer visitor=rs.getInt("visitor");
                EssayIDManger eim=new EssayIDManger();
                 //如果此用户id没有访问过此essay_id，则更新两张表，两者以此建立联系
                 if(!eim.isVisitor(conn,essay_id,user_id)) {
                     visitor++;
                     String sql1 = "update BLOG_TB_ESSAY set visitor=? where id=?";
                     list.clear();
                     list.add(visitor);
                     list.add(essay_id);
                     sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
                     String sql2="insert into BLOG_TB_VISITE(user_id,essay_id)values(?,?)";
                     list.clear();
                     list.add(user_id);
                     list.add(essay_id);
                     sql_jdbc.prepareStatement("UPDATE",conn,sql2,list);
                 }
                 essay.setVisitor(visitor);
            }
            rs.close();
            return essay;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return essay;

    }
    //evaluate的值是"star"或者"diss"
    public Essay evaluate(Connection conn, int essay_id,int user_id,Essay essay,String evaluate){
        ResultSet rs=null;
        boolean isOperate=true;
        try{
            String sql="select "+evaluate+" from BLOG_TB_ESSAY where id=?";
            ArrayList list =new ArrayList();
            list.add(essay_id);
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql,list);
            list.clear();
            while(rs.next()){
                Integer evaluateCount=null;
                EssayIDManger eim=new EssayIDManger();
                if(evaluate.equals("star")){
                    evaluateCount=rs.getInt("star");
                    isOperate=eim.isStar(conn,essay_id,user_id);
                }else if(evaluate.equals("diss")){
                    evaluateCount=rs.getInt("diss");
                    isOperate=eim.isDiss(conn,essay_id,user_id);
                }
                if(!isOperate) {
                    evaluateCount++;
                    String sql1 = "update blog_tb_essay set "+evaluate+"=? where id=?";
                    list.add(evaluateCount);
                    list.add(essay_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
                    list.clear();
                    String sql2="insert into blog_tb_essay_"+evaluate+"(user_id,essay_id)values(?,?)";
                    list.add(user_id);
                    list.add(essay_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql2,list);
                    list.clear();
                }else if(user_id!=0){
                    evaluateCount--;
                    String sql1 = "update blog_tb_essay set "+evaluate+"=? where id=?";
                    list.add(evaluateCount);
                    list.add(essay_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
                    list.clear();
                    String sql2="delete from BLOG_TB_ESSAY_"+evaluate+" where user_id=? and essay_id=?";
                    list.add(user_id);
                    list.add(essay_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql2,list);
                }
                if(evaluate.equals("star")){
                    essay.setStar(evaluateCount);
                }else if(evaluate.equals("diss")){
                    essay.setDiss(evaluateCount);
                }
            }
            rs.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return essay;
    }
    public Essay star(Connection conn, int essay_id,int user_id,Essay essay){
        return evaluate(conn,essay_id,user_id,essay,"star");
    }
    public Essay diss(Connection conn, int essay_id,int user_id,Essay essay){
        return evaluate(conn,essay_id,user_id,essay,"diss");
    }
    public void WriteComments(Connection conn,int essay_id,String comment,User user){
            ArrayList list=new ArrayList();
            String sql="insert into BLOG_TB_COMMENT(essay_id,star,diss,username,content,user_id,creation_time)values(?,?,?,?,?,?,?)";
            list.add(essay_id);
            list.add(0);
            list.add(0);
            list.add(user.getUsername());
            list.add(comment);
            list.add(user.getId());
            list.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            sql_jdbc.prepareStatement("UPDATE",conn,sql,list);
    }
    public Comment[] getComments(Connection conn, int essay_id){
        Comment[]comments=null;
        ResultSet rs=null;
        ArrayList list=new ArrayList();
        try{
            String sql="select count(*) from BLOG_TB_COMMENT where essay_id=?";
            list.add(essay_id);
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql,list);
            int count=0;
            rs.first();
            count=rs.getInt(1);
            if ( count< 1) {
                comments = new Comment[0];
                rs.close();
                return comments;
            }
            String sql2="select *from BLOG_TB_COMMENT where essay_id=?";
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql2,list);
            comments=new Comment[count];
            int tmp=0;
            while(rs.next()){
                Comment comment=new Comment();
                comment.initComment(rs.getInt("id"),rs.getString("username"),rs.getInt("user_id"),rs.getInt("essay_id"),rs.getInt("star"),rs.getInt("diss"),rs.getString("content"),rs.getDate("creation_time"));
                comments[tmp]=comment;
                tmp++;
            }
            rs.close();;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return comments;
    }
    public Essay comments(Connection conn, int essay_id,Essay essay){
        ResultSet rs=null;
        ArrayList list=new ArrayList();
        try{
            String sql="select comments from BLOG_TB_ESSAY where id=?";
            list.add(essay_id);
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql,list);
            list.clear();
            while(rs.next()) {
                Integer comments = rs.getInt("comments");
                comments++;
                essay.setComments(comments);
                String sql1 = "update BLOG_TB_ESSAY set comments=? where id=?";
                list.add(comments);
                list.add(essay_id);
                sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
                rs.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
           return essay;
    }
    public void evaluateCom(Connection conn,Integer comment_id,Integer user_id,String evaluateCom){
        ResultSet rs=null;
        boolean isOperate=true;
        ArrayList list=new ArrayList();
        try{
            String sql="select "+evaluateCom+" from blog_tb_comment where id=?";
            list.add(comment_id);
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql,list);
            list.clear();
            while(rs.next()){
                Integer evaluateComCount=rs.getInt(evaluateCom);
                CommentIDManger cim=new CommentIDManger();
                if(evaluateCom.equals("star")){
                    isOperate=cim.isStar(conn,comment_id,user_id);
                }else if(evaluateCom.equals("diss")){
                    isOperate=cim.isDiss(conn,comment_id,user_id);
                }
                if(!cim.isStar(conn,user_id,comment_id)) {
                    evaluateComCount++;
                    String sql1 = "update blog_tb_comment set "+evaluateCom+"=? where id=?";
                    list.add(evaluateComCount);
                    list.add(comment_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
                    list.clear();
                    String sql2="insert into blog_tb_comment_"+evaluateCom+"(user_id,comment_id)values(?,?)";
                    list.add(user_id);
                    list.add(comment_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql2,list);
                    list.clear();
                }else if(user_id!=0){
                    evaluateComCount--;
                    String sql1 = "update blog_tb_comment set "+evaluateCom+"=? where id=?";
                    list.add(evaluateComCount);
                    list.add(comment_id);
                    sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
                    list.clear();
                    String sql2="delete from blog_tb_comment_"+evaluateCom+" where user_id=? and comment_id=?";
                    list.add(user_id);
                    list.add(comment_id);
                   sql_jdbc.prepareStatement("UPDATE",conn,sql2,list);
                }
            }
            rs.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void starCom(Connection conn,Integer comment_id,Integer user_id){
        evaluateCom(conn,comment_id,user_id,"star");
    }
    public void disCom(Connection conn,Integer comment_id,Integer user_id){
        evaluateCom(conn,comment_id,user_id,"diss");
    }
}


