package com.sduhyd.blog;

import com.mysql.jdbc.Connection.*;
import net.sf.json.JSONArray;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Utils {
    public  User register(Connection conn,String username,String password){
        try {
            //检查注册用户名与昵称是否重复
            String sql_check = "select username from BLOG_TB_USER where username = ?";
            PreparedStatement statement = conn.prepareStatement(sql_check);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return null;
            }else {
                //提交注册信息
                String sql ="insert into BLOG_TB_USER(username,password)values(?,?)";
                PreparedStatement preparedstatement= conn.prepareStatement(sql);
                preparedstatement.setString(1,username);
                preparedstatement.setString(2,password);
                try{
                    preparedstatement.executeUpdate();
                }finally{
                    preparedstatement.close();
                }
                //注册成功后封装为User类返回
                String sql_find="select *from BLOG_TB_USER where username = ?";
                PreparedStatement statement1 = conn.prepareStatement(sql_find);
                statement1.setString(1,username);
                ResultSet rs1= statement1.executeQuery();
                try {
                    if(rs1.first()) {
                        User user = new User();
                        Integer id=rs1.getInt("id");
                        String qusername = rs1.getString("username");
                        String qpassword = rs1.getString("password");
                        user.setId(id);
                        user.setUsername(qusername);
                        user.setPassword(qpassword);
                        return user;
                    }
                }finally{
                    rs.close();
                    rs1.close();
                    statement.close();
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  User login(Connection conn,String username,String password) {
        try {
            String sql = "select * from BLOG_TB_USER where username = ?";
            PreparedStatement statement= conn.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(rs.first()) {
                String qpassword = rs.getString("password");
                if(qpassword != null && qpassword.equals(password)) {
                    Integer id = rs.getInt("id");
                    User user = new User();
                    user.setPassword(qpassword);
                    user.setId(id);
                    user.setUsername(username);
                    return user;
                }
            }
            rs.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public  boolean createEssay(Connection conn,Integer user_id, String title, String article, Date modify_time,String username){

        java.sql.Date sql_date = new java.sql.Date(modify_time.getTime());
        try{
                String create_sql="insert into BLOG_TB_ESSAY(user_id,title,article,creation_time,modify_time,username,star,diss,comments,visitor)values(?,?,?,?,?,?,?,?,?,?) ";
                PreparedStatement statement = conn.prepareStatement(create_sql);
                statement.setInt(1, user_id);
                statement.setString(2, title);
                statement.setString(3, article);
                //创作日期和最开始日期相同
                statement.setDate(4,sql_date);
                statement.setDate(5,sql_date);
                statement.setString(6, username);
                statement.setInt(7, 0);
                statement.setInt(8, 0);
                statement.setInt(9, 0);
                statement.setInt(10, 0);
                statement.executeUpdate();
                System.out.println(username+"新增了博客");
                return true;
            }catch (SQLException e){
                e.printStackTrace();
            }

            return false;
    }
    public  Essay[] showEssay(Connection conn,Integer user_id){

        try {
            //统计用户的文章数

            String selectCountSql = "select count(*) from BLOG_TB_ESSAY where user_id=?";
            int count = 0;
            {
                PreparedStatement statement = conn.prepareStatement(selectCountSql);
                statement.setInt(1, user_id);
                ResultSet rs = statement.executeQuery();
                rs.first();
                count = rs.getInt(1);
                if (count < 1) {
                    rs.close();
                    statement.close();
                    return new Essay[0];
                }
            }
            //开始从文章表里提取文章
            String sql_show="select *from BLOG_TB_ESSAY where user_id=?";
            PreparedStatement statement = conn.prepareStatement(sql_show);
            statement.setInt(1,user_id);
            ResultSet rs= statement.executeQuery();

            Essay[] essays= new Essay[count];
            int essay_count=0;
            while(rs.next()){
                Essay essay=new Essay();
                essay.setId(rs.getInt("id"));
                essay.setUser_id(user_id);
                essay.setUsername(rs.getString("username"));
                essay.setTitle(rs.getString("title"));
                essay.setArticle(rs.getString("article"));
                essay.setCreation_time(rs.getDate("creation_time"));
                essay.setModify_time(rs.getDate("modify_time"));
                essays[essay_count]=essay;
                essay_count++;
            }
            rs.close();
            statement.close();
            System.out.println("user_id "+user_id+" 查看了自身文章");
            return essays;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Essay[0];
    }
    public void  updateEssay(Connection conn,String id,String title,String content){

            try{
                String sql_update="update BLOG_TB_ESSAY set title=?,article=? where id=?";
                PreparedStatement statement = conn.prepareStatement(sql_update);
                statement.setString(1,title);
                statement.setString(2,content);
                statement.setInt(3,Integer.parseInt(id));
                statement.executeUpdate();
                System.out.println("文章 "+id+" 更新成功");
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

    }
    public ArrayList<Essay> allEssay(Connection conn){

            ArrayList<Essay> arrayList = new ArrayList<>();
            try{
                String sql_all = "select *from BLOG_TB_ESSAY";
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql_all);
                while(rs.next()){
                    Essay essay = new Essay();
                    essay.setId(rs.getInt("id"));
                    essay.setUser_id(rs.getInt("user_id"));
                    essay.setUsername(rs.getString("username"));
                    essay.setTitle(rs.getString("title"));
                    essay.setArticle(rs.getString("article"));
                    essay.setCreation_time(rs.getDate("creation_time"));
                    essay.setModify_time(rs.getDate("modify_time"));
                    essay.setStar(rs.getInt("star"));
                    essay.setDiss(rs.getInt("diss"));
                    essay.setComments(rs.getInt("comments"));
                    essay.setVisitor(rs.getInt("visitor"));
                    arrayList.add(essay);
                }
                System.out.println("查看全部文章成功");
                rs.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }

            return arrayList;
    }
    public Essay visitor(Connection conn, int essay_id,Essay essay){

        try{
            String sql="select visitor from BLOG_TB_ESSAY where id=?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,essay_id);
            ResultSet rs=statement.executeQuery();
            Integer visitor=null;
            while(rs.next()){
                 visitor=rs.getInt("visitor");
                 visitor++;
                 essay.setVisitor(visitor);
                String sql1="update BLOG_TB_ESSAY set visitor=? where id=?";
                PreparedStatement statement1=conn.prepareStatement(sql1);
                statement1.setInt(1,visitor);
                statement1.setInt(2,essay_id);
                statement1.executeUpdate();
                statement1.close();
            }
            rs.close();
            statement.close();
            return essay;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return essay;

    }
    public Essay star(Connection conn, int essay_id,Essay essay){
        try{
            String sql="select star from BLOG_TB_ESSAY where id=?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,essay_id);
            ResultSet rs=statement.executeQuery();
            while(rs.next()){
                Integer star=rs.getInt("star");
                star++;
                essay.setStar(star);
                String sql1="update BLOG_TB_ESSAY set star=? where id=?";
            PreparedStatement statement1=conn.prepareStatement(sql1);
            statement1.setInt(1,star);
            statement1.setInt(2,essay_id);
            statement1.executeUpdate();
            statement1.close();
            }
            rs.close();
            statement.close();
           return essay;
        }catch(SQLException e){
            e.printStackTrace();
        }
               return essay;
    }
    public Essay diss(Connection conn, int essay_id,Essay essay){
        try{
            String sql="select diss from BLOG_TB_ESSAY where id=?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,essay_id);
            ResultSet rs=statement.executeQuery();
            Integer diss=0;
            while(rs.next()) {
                diss = rs.getInt("diss");
                diss++;
                essay.setDiss(diss);
                String sql1 = "update BLOG_TB_ESSAY set diss=? where id=?";
                PreparedStatement statement1 = conn.prepareStatement(sql1);
                statement1.setInt(1, diss);
                statement1.setInt(2, essay_id);
                statement1.executeUpdate();
                statement1.close();
                return essay;
            }
            rs.close();
            statement.close();

        }catch(SQLException e){
            e.printStackTrace();
        }
           return essay;
    }
    public void WriteComments(Connection conn,int essay_id,String comment,User user){
        try{
            String sql="insert into BLOG_TB_COMMENT(essay_id,star,diss,username,content,user_id,creation_time)values(?,?,?,?,?,?,?)";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,essay_id);
            statement.setInt(2,0);
            statement.setInt(3,0);
            statement.setString(4,user.getUsername());
            statement.setString(5,comment);
            statement.setInt(6,user.getId());
            java.sql.Date sql_date = new java.sql.Date(new Date().getTime());
            statement.setDate(7,sql_date);
            statement.executeUpdate();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public Comment[] getComments(Connection conn,int essay_id){
        Comment[]comments=null;
        try{
            String sql="select count(*) from BLOG_TB_COMMENT where essay_id=?";
            int count=0;
            {
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setInt(1, essay_id);
                ResultSet rs = statement.executeQuery();
                rs.first();
                count=rs.getInt(1);
                if ( count< 1) {
                    comments = new Comment[0];
                    rs.close();
                    statement.close();
                    return comments;
                }
             }
            String sql2="select *from BLOG_TB_COMMENT where essay_id=?";
            PreparedStatement statement = conn.prepareStatement(sql2);
            statement.setInt(1, essay_id);
            ResultSet rs = statement.executeQuery();
             comments=new Comment[count];
             int tmp=0;
             while(rs.next()){
                 Comment comment=new Comment();
                 comment.setId(rs.getInt("id"));
                 comment.setUser_id(rs.getInt("user_id"));
                 comment.setEssay_id(rs.getInt("essay_id"));
                 comment.setStar(rs.getInt("star"));
                 comment.setDiss(rs.getInt("diss"));
                 comment.setUsername(rs.getString("username"));
                 comment.setContent(rs.getString("content"));
                 comment.setCreation_time(rs.getDate("creation_time"));
                 comments[tmp]=comment;
                 tmp++;
             }
             rs.close();;
             statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return comments;
    }
    public Essay comments(Connection conn, int essay_id,Essay essay){
        try{
            String sql="select comments from BLOG_TB_ESSAY where id=?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,essay_id);
            ResultSet rs=statement.executeQuery();
            while(rs.next()) {
                Integer comments = rs.getInt("comments");
                comments++;
                essay.setComments(comments);
                String sql1 = "update BLOG_TB_ESSAY set comments=? where id=?";
                PreparedStatement statement1 = conn.prepareStatement(sql1);
                statement1.setInt(1, comments);
                statement1.setInt(2, essay_id);
                statement1.executeUpdate();
                rs.close();
                statement.close();
                statement1.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
           return essay;
    }
    public void starCom(Connection conn,Integer comment_id){
        try{
            String sql="select star from BLOG_TB_COMMENT where id=?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,comment_id);
            ResultSet rs=statement.executeQuery();
            Integer star=0;
            while(rs.next()){
                star=rs.getInt("star");
                star++;
                String sql1="update BLOG_TB_COMMENT set star=? where id=?";
                PreparedStatement statement1=conn.prepareStatement(sql1);
                statement1.setInt(1,star);
                statement1.setInt(2,comment_id);
                statement1.executeUpdate();
                statement1.close();
            }
            rs.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void disCom(Connection conn,Integer comment_id){
        try{
            String sql="select diss from BLOG_TB_COMMENT where id=?";
            PreparedStatement statement=conn.prepareStatement(sql);
            statement.setInt(1,comment_id);
            ResultSet rs=statement.executeQuery();
            Integer diss=0;
            while(rs.next()){
                diss=rs.getInt("diss");
                diss++;
                String sql1="update BLOG_TB_COMMENT set diss=? where id=?";
                PreparedStatement statement1=conn.prepareStatement(sql1);
                statement1.setInt(1,diss);
                statement1.setInt(2,comment_id);
                statement1.executeUpdate();
                statement1.close();
            }
            rs.close();
            statement.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }



}


