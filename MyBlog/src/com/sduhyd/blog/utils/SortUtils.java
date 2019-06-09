package com.sduhyd.blog.utils;

import com.sduhyd.blog.bean.Comment;
import com.sduhyd.blog.bean.Essay;

import java.util.ArrayList;
import java.util.List;

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
//    public Essay[] getTop(List<Object> objects){
//        for(int i=1;i<objects.size();i++){
//            for(int j=0;j<objects.size()-i;j++){
//                if((Essay)objects.get(j).getStar()<objects.get(j+1).getStar()){
//                    Essay tmp=objects.get(j);
//                    Essay tmp2=essays.get(j+1);
//                    essays.set(j,tmp2);
//                    essays.set(j+1,tmp);
//                }
//            }
//        }
//        Essay[] tmp;
//        if(essays.size()<3){
//            tmp=new Essay[essays.size()];
//            for(int i=0;i<essays.size();i++){
//                tmp[i]=essays.get(i);
//            }
//        }else{
//            tmp=new Essay[3];
//            for(int i=0;i<3;i++){
//                tmp[i]=essays.get(i);
//            }
//        }
//        return tmp;
//    }
    public Object[] reverse(List<Object> objects){
        Object[] tmp=new Object[objects.size()];
        for(int i=0;i<objects.size();i++){
            tmp[i]=objects.get(objects.size()-1-i);
        }
        return tmp;
    }
}
