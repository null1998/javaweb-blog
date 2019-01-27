package com.sduhyd.blog.model;

import com.sduhyd.blog.bean.Comment;
import com.sduhyd.blog.bean.Essay;

import java.util.ArrayList;

public class SortUtils {
    //简单的冒泡排序，以后再优化
    public Comment[] sortCom(Comment[]comments){
        for(int i=1;i<comments.length;i++){
            for(int j=0;j<comments.length-i;j++){
                if(comments[j].getStar()<comments[j+1].getStar()){
                    Comment tmp=comments[j];
                    comments[j]=comments[j+1];
                    comments[j+1]=tmp;
                }
            }
        }
        return comments;
    }
    public Essay[] sortEssay(ArrayList<Essay>essays){
        for(int i=1;i<essays.size();i++){
            for(int j=0;j<essays.size()-i;j++){
                if(essays.get(j).getStar()<essays.get(j+1).getStar()){
                    Essay tmp=essays.get(j);
                    Essay tmp2=essays.get(j+1);
                    essays.set(j,tmp2);
                    essays.set(j+1,tmp);
                }
            }
        }
        Essay[] tmp;
        if(essays.size()<3){
            tmp=new Essay[essays.size()];
            for(int i=0;i<essays.size();i++){
                tmp[i]=essays.get(i);
            }
        }else{
            tmp=new Essay[3];
            for(int i=0;i<3;i++){
                tmp[i]=essays.get(i);
            }
        }
        return tmp;
    }
    public Essay[] reverseEssay(ArrayList<Essay> essays){
        Essay[] tmp=new Essay[essays.size()];
        for(int i=0;i<essays.size();i++){
            tmp[i]=essays.get(essays.size()-1-i);
        }
        return tmp;
    }
}
