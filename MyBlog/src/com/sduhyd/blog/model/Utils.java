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
        String sql_login = "select *from BLOG_TB_USER where username = ?";
        list.add(username);
        rs=sql_jdbc.prepareStatement("QUERY",conn,sql_login,list);
        try {
            if(rs.first()) {
                String qpassword = rs.getString("password");
                if(qpassword != null && qpassword.equals(password)) {
                    user = new User();
                    user.initUser(rs.getInt("id"),username,qpassword);
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
    //此函数用于得出visitor,star,diss,favorite,comments,starCom,dissCom的值
    public Integer getNum(Connection conn,int target_id,String TYPE){
        ResultSet rs=null;
        String sql="";
        ArrayList list=new ArrayList();
        if(TYPE.equals("visitor")||TYPE.equals("star")||TYPE.equals("diss")||TYPE.equals("favorite")||TYPE.equals("comments")){
            sql="select "+TYPE+" from BLOG_TB_ESSAY where id=?";
        }else if(TYPE.equals("starCom")){
            sql="select star from BLOG_TB_COMMENT where id=?";
        }else if(TYPE.equals("dissCom")){
            sql="select diss from BLOG_TB_COMMENT where id=?";
        }
        list.add(target_id);
        rs=sql_jdbc.prepareStatement("QUERY",conn,sql,list);
        try {
            while(rs.next()){
                return rs.getInt(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    public Integer getVisitorCount(Connection conn,int essay_id){
        return getNum(conn,essay_id,"visitor");
    }
    public Integer getStarCount(Connection conn,int essay_id){
        return getNum(conn,essay_id,"star");
    }
    public Integer getDissCount(Connection conn,int essay_id){
        return getNum(conn,essay_id,"diss");
    }
    public Integer getFavoriteCount(Connection conn,int essay_id){
        return getNum(conn,essay_id,"favorite");
    }
    public Integer getCommentsCount(Connection conn,int essay_id){
        return getNum(conn,essay_id,"comments");
    }

    public Integer getStarComCount(Connection conn,int comment_id){
        return getNum(conn,comment_id,"starCom");
    }
    public Integer getDissComCount(Connection conn,int comment_id){
        return getNum(conn,comment_id,"dissCom");
    }
    //此函数用于加一或减一
    public void updateSum(Connection conn,int target_id,int user_id,int updateCount,String TYPE,String MODE){
        ArrayList list=new ArrayList();
        String sql1="";
        String sql2="";
        //暂时没有blog_tb_essay_comments
        if(TYPE.equals("visitor")||TYPE.equals("star")||TYPE.equals("diss")||TYPE.equals("favorite")){
            sql1="update BLOG_TB_ESSAY set "+TYPE+"=? where id=?";
            if(MODE.equals("plus")){
                sql2="insert into blog_tb_essay_"+TYPE+"(user_id,essay_id)values(?,?)";
            }else if(MODE.equals("minus")){
                sql2="delete from blog_tb_essay_"+TYPE+" where user_id=? and essay_id=?";
            }
        }else if(TYPE.equals("starCom")){
            sql1="update BLOG_TB_COMMENT set star=? where id=?";
            if(MODE.equals("plus")){
                sql2="insert into blog_tb_comment_star(user_id,comment_id)values(?,?)";
            }else if(MODE.equals("minus")){
                sql2="delete from blog_tb_comment_star where user_id=? and comment_id=?";
            }
        }else if(TYPE.equals("dissCom")){
            sql1="update BLOG_TB_COMMENT set diss=? where id=?";
            if(MODE.equals("plus")){
                sql2="insert into blog_tb_comment_diss(user_id,comment_id)values(?,?)";
            }else if(MODE.equals("minus")){
                sql2="delete from blog_tb_comment_diss where user_id=? and comment_id=?";
            }
        }
        list.add(updateCount);
        list.add(target_id);
        sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
        list.set(0,user_id);//替换完继续用。
        sql_jdbc.prepareStatement("UPDATE",conn,sql2,list);
    }
    public void updateVisitorCountUp(Connection conn,int essay_id,int user_id,int updateCount){
        updateSum(conn,essay_id,user_id,updateCount,"visitor","plus");
    }
    public void updateEvaluateCountUp(Connection conn,int essay_id,int user_id,int updateCount,String evaluate){
        updateSum(conn,essay_id,user_id,updateCount,evaluate,"plus");
    }
    public void updateFavoriteCountUp(Connection conn,int essay_id,int user_id,int updateCount){
        updateSum(conn,essay_id,user_id,updateCount,"favorite","plus");
    }
    public void updateEvaluateComCountUp(Connection conn,int comment_id,int user_id,int updateCount,String evaluateCom){
        updateSum(conn,comment_id,user_id,updateCount,evaluateCom,"plus");
    }

    public void updateEvaluateCountDown(Connection conn,int essay_id,int user_id,int updateCount,String evaluate){
        updateSum(conn,essay_id,user_id,updateCount,evaluate,"minus");
    }
    public void updateFavoriteCountDown(Connection conn,int essay_id,int user_id,int updateCount){
        updateSum(conn,essay_id,user_id,updateCount,"favorite","minus");
    }
    public void updateEvaluateComCountDown(Connection conn,int comment_id,int user_id,int updateCount,String evaluateCom){
        updateSum(conn,comment_id,user_id,updateCount,evaluateCom,"minus");
    }

    public Essay visitor(Connection conn, int essay_id,int user_id,Essay essay){
        ArrayList list=new ArrayList();
        Integer visitorCount=getVisitorCount(conn,essay_id);
        EssayIDManger eim=new EssayIDManger();
        //如果此用户id没有访问过此essay_id，则更新两张表，两者以此建立联系
        if(!eim.isVisitor(conn,essay_id,user_id)) {
            visitorCount++;
            updateVisitorCountUp(conn,essay_id,user_id,visitorCount);
        }
        essay.setVisitor(visitorCount);
        return essay;
    }
    //evaluate的值是"star"或者"diss"
    public Essay evaluate(Connection conn, int essay_id,int user_id,Essay essay,String evaluate){
        ResultSet rs=null;
        boolean isOperate=true;
        Integer evaluateCount=null;
        ArrayList list =new ArrayList();
        EssayIDManger eim=new EssayIDManger();
        if(evaluate.equals("star")){
            evaluateCount=getStarCount(conn,essay_id);
            isOperate=eim.isStar(conn,essay_id,user_id);
        }else if(evaluate.equals("diss")){
            evaluateCount=getDissCount(conn,essay_id);
            isOperate=eim.isDiss(conn,essay_id,user_id);
        }
        if(!isOperate) {
            evaluateCount++;
            updateEvaluateCountUp(conn,essay_id,user_id,evaluateCount,evaluate);
        }else if(user_id!=0){
            evaluateCount--;
            updateEvaluateCountDown(conn,essay_id,user_id,evaluateCount,evaluate);
        }
        if(evaluate.equals("star")){
            essay.setStar(evaluateCount);
        }else if(evaluate.equals("diss")){
            essay.setDiss(evaluateCount);
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
        Integer commentsCount = getCommentsCount(conn,essay_id);
        if ( commentsCount< 1) {
            comments = new Comment[0];
            return comments;
        }
        try{
            String sql2="select *from BLOG_TB_COMMENT where essay_id=?";
            list.add(essay_id);
            rs=sql_jdbc.prepareStatement("QUERY",conn,sql2,list);
            comments=new Comment[commentsCount];
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
    //将该文章的comment加一并返回该文章
    public Essay comments(Connection conn, int essay_id,Essay essay){
        ArrayList list=new ArrayList();
        Integer commentsCount =getCommentsCount(conn,essay_id);
        commentsCount++;
        essay.setComments(commentsCount);
        String sql1 = "update BLOG_TB_ESSAY set comments=? where id=?";
        list.add(commentsCount);
        list.add(essay_id);
        sql_jdbc.prepareStatement("UPDATE",conn,sql1,list);
        return essay;
    }
    public Essay evaluateCom(Connection conn,Integer comment_id,Integer user_id,Essay essay,String evaluateCom) {
        ResultSet rs = null;
        boolean isOperate = true;
        ArrayList list = new ArrayList();
        Integer evaluateComCount = null;
        CommentIDManger cim = new CommentIDManger();
        if (evaluateCom.equals("starCom")) {
            isOperate = cim.isStar(conn, comment_id, user_id);
            evaluateComCount = getStarComCount(conn, comment_id);
        } else if (evaluateCom.equals("dissCom")) {
            isOperate = cim.isDiss(conn, comment_id, user_id);
            evaluateComCount = getDissComCount(conn, comment_id);
        }
        if (!isOperate) {
            evaluateComCount++;
            updateEvaluateComCountUp(conn,comment_id,user_id,evaluateComCount,evaluateCom);
        } else if (user_id != 0) {
            evaluateComCount--;
            updateEvaluateComCountDown(conn,comment_id,user_id,evaluateComCount,evaluateCom);
        }
        if(evaluateCom.equals("starCom")){
            essay.setStar(evaluateComCount);
        }else if(evaluateCom.equals("dissCom")){
            essay.setDiss(evaluateComCount);
        }
        return essay;
    }
    public Essay starCom(Connection conn,Integer comment_id,Integer user_id,Essay essay){
        return evaluateCom(conn,comment_id,user_id,essay,"starCom");
    }
    public Essay disCom(Connection conn,Integer comment_id,Integer user_id,Essay essay){
        return evaluateCom(conn,comment_id,user_id,essay,"dissCom");
    }
}


