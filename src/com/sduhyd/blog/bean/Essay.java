package com.sduhyd.blog.bean;
import java.util.Date;
public class Essay {
    private Integer id;
    private Integer user_id;
    private String username;
    private String title;
    private String article;
    private Date creation_time;
    private Date modify_time;
    private Integer star;
    private Integer diss;
    private Integer comments;
    private Integer visitor;
    private Integer favorite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public Date getCreation_time() {
        return creation_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }

    public Integer getStar() {
        return star;
    }

    public Integer getDiss() {
        return diss;
    }

    public Integer getComments() {
        return comments;
    }

    public Integer getVisitor() {
        return visitor;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public void setDiss(Integer diss) {
        this.diss = diss;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public void setVisitor(Integer visitor) {
        this.visitor = visitor;
    }

    public Integer getFavorite() {
        return favorite;
    }

    public void setFavorite(Integer favorite) {
        this.favorite = favorite;
    }

    public void initEssay(Integer id,Integer user_id,String username,String title,String article,Date creation_time,Date modify_time,Integer star,Integer diss,Integer comments,Integer visitor,Integer favorite){
        setId(id);
        setUser_id(user_id);
        setUsername(username);
        setTitle(title);
        setArticle(article);
        setCreation_time(creation_time);
        setModify_time(modify_time);
        setStar(star);
        setDiss(diss);
        setComments(comments);
        setVisitor(visitor);
        setFavorite(favorite);
    }
}
