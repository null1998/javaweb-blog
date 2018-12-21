package com.sduhyd.blog;

import java.util.Date;

public class Comment {
    private Integer id;
    private String username;
    private Integer user_id;
    private Integer essay_id;
    private Integer star;
    private Integer diss;
    private String content;
    private Date creation_time;

    public Date getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Date creation_time) {
        this.creation_time = creation_time;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public Integer getEssay_id() {
        return essay_id;
    }

    public Integer getStar() {
        return star;
    }

    public Integer getDiss() {
        return diss;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setEssay_id(Integer essay_id) {
        this.essay_id = essay_id;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDiss(Integer diss) {
        this.diss = diss;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
