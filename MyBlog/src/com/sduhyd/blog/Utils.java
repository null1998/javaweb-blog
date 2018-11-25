package com.sduhyd.blog;

import com.mysql.jdbc.Connection.*;
import net.sf.json.JSONArray;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class Utils {
        volatile  static int essay_count;

        public  Connection connection() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/DB_BLOG?useUnicode=true&characterEncoding=utf-8&useSSL=false";
        String user = "test";
        String password = "12345";
        Connection conn=null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            System.out.println("Connecting to database...");
            conn = (Connection) DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
          System.out.println("Connect success!");
        return conn;
        }
    public  void releaseConnection(Connection conn){
        try {
            if(conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Disconnect success");
            }
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User register(Connection conn,String username,String password){
        if(conn==null){
            conn=connection();
        }
        try {
            //检查注册用户名与昵称是否重复
            String sql_check = "select username from BLOG_TB_USER where username = ?";
            PreparedStatement statement = conn.prepareStatement(sql_check);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if(rs.getFetchSize() == 0) {
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
                ResultSet rs1= statement.executeQuery();
                try {
                    if(rs1.first()) {
                        User user = new User();
                        Integer id = rs1.getInt("id");
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

    public User login(Connection conn,String username,String password) {
        if(conn==null){
            conn=connection();
        }
        try {
            String sql = "select * from BLOG_TB_USER where username = ?";
            if(conn==null)
                System.out.println("error");
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

    public Essay createEssay(Connection conn,Integer user_id, String title, String article, Date modify_time){
            if(conn==null){
                conn=connection();
            }
        java.sql.Date sql_date = new java.sql.Date(modify_time.getTime());
            Essay essay = new Essay();
        try{
                String create_sql="insert into essay(user_id,title,article,modify_time)values(?,?,?,?) ";
                PreparedStatement statement = conn.prepareStatement(create_sql);
                statement.setInt(1, user_id);
                statement.setString(2, title);
                statement.setString(3, article);
                statement.setDate(4,sql_date);
                statement.executeUpdate();
                essay.setUser_id(user_id);
                essay.setTitle(title);
                essay.setArticle(article);
                System.out.println(user_id+"新增了博客");
            }catch (SQLException e){
                e.printStackTrace();
            }

            return essay;
    }
    public  Essay[] showEssay(Connection conn,Integer user_id){
        if(conn==null){
            conn=connection();
        }
        try{
            String selectCountSql = "select count(*) from essay where user_id=?";
            int count = 0;
            {
                PreparedStatement statement = conn.prepareStatement(selectCountSql);
                statement.setInt(1, user_id);
                ResultSet rs = statement.executeQuery();
                rs.first();
                count = rs.getInt(1);
                if(count < 1)  {
                    return new Essay[0];
                }
                rs.close();
                statement.close();
            }
            String sql_show="select *from essay where user_id=?";
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
            return essays;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return new Essay[0];
    }
    public void  updateEssay(Connection conn,String id,String title,String content){
            if(conn==null){
                conn=connection();
            }
            try{
                String sql_update="update essay set title=?,article=? where id=?";
                PreparedStatement statement = conn.prepareStatement(sql_update);
                statement.setString(1,title);
                statement.setString(2,content);
                statement.setInt(3,Integer.parseInt(id));
                statement.executeUpdate();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

    }
    public ArrayList<Essay> allEssay(Connection conn){
            if(conn==null){
                conn=connection();
            }
            ArrayList<Essay> arrayList = new ArrayList<>();
            try{
                String sql_all = "select *from essay";
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery(sql_all);
                while(rs.next()){
                    Essay essay = new Essay();
                    essay.setId(rs.getInt("id"));
                    essay.setUser_id(rs.getInt("user_id"));
                    essay.setUsername(rs.getString("username"));
                    essay.setTitle(rs.getString("title"));
                    essay.setArticle(rs.getString("article"));
                    System.out.println(rs.getDate("creation_time").getTime());
                    essay.setCreation_time(rs.getDate("creation_time"));
                    essay.setModify_time(rs.getDate("modify_time"));
                    arrayList.add(essay);
                }
                rs.close();
                statement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
            return arrayList;
    }

    public static String  essaysToJsonArray( Essay[]essays){
            String jsonData="";
            for(int j=0;j<essays.length;j++){
                if(j==0){
                    jsonData+="[";
                }
                jsonData=jsonData+"{"+"\""+"title"+"\""+":"+"\""+essays[j].getTitle()+"\""+","+"\""+"article"+"\""+":"+"\""+essays[j].getArticle()+"\""+","
                    +"\""+"creation_time"+"\""+":"+"\""+essays[j].getCreation_time()+"\""+","+"\""+"modify_time"+"\""+":"+"\""+essays[j].getModify_time()+"\""
                    +"}";
                if(j!=essay_count-1){
                    jsonData+=",";
                }
                if(j==essay_count-1){
                    jsonData+="]";
                }
            }
            essay_count=0;
            return jsonData;
    }
}


