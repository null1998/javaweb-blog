package com.sduhyd.blog;

import java.util.ArrayList;

public class SortUtils {
    //简单的冒泡排序，以后再优化
    public Comment[] sortCom(Comment[]comments){
        for(int i=1;i<comments.length;i++){
            for(int j=0;j<comments.length-i;j++){
                if(comments[j].getStar()<comments[j+1].getStar()){
                    Integer tmp=comments[j].getStar();
                    comments[j].setStar(comments[j+1].getStar());
                    comments[j+1].setStar(tmp);
                }
            }
        }
        return comments;
    }
    public Essay[] sortEssay(ArrayList<Essay>essays){
        for(int i=1;i<essays.size();i++){
            for(int j=0;j<essays.size()-i;j++){
                if(essays.get(j).getStar()<essays.get(j+1).getStar()){
                    Integer tmp=essays.get(j).getStar();
                    essays.get(j).setStar(essays.get(j+1).getStar());
                    essays.get(j+1).setStar(tmp);
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
