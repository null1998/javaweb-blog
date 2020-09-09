package com.sduhyd.blog.service;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.daoImp.EssayDaoImpl;
import com.sduhyd.blog.utils.BeanUtils;

import java.io.IOException;
import java.util.*;

public class MainPage {
    /**
     * 按时间倒序取出所有文章
     * @return
     * @throws IOException
     */
    public static List<Object> reorderShowEssays()throws IOException {
        List<Map<String,Object>> list= EssayDaoImpl.read();
        List<Object> essayList= BeanUtils.setAll(Essay.class,list);
        Collections.reverse(essayList);
        return essayList;
    }

    /**
     * 根据点赞数取出文章并排序
     * @param essays
     * @param nums 取前nums篇文章
     * @return
     * @throws IOException
     */
    public static List<Object> showEssayOrderByStar(List<Object> essays, int nums) throws IOException {
        List<Essay> list = new ArrayList<>();
        for (Object object: essays) {
            list.add((Essay) object);
        }
        Collections.sort(list, new Comparator<Essay>() {
            public int compare(Essay essay1, Essay essay2) {
                return essay2.getStar().compareTo(essay1.getStar());
            }
        });
        nums = Math.min(list.size(), nums);
        List<Object> topEssays = new ArrayList<>();
        for (int i = 0; i < nums; i++) {
            topEssays.add(list.get(i));
        }
        return topEssays;
    }

}
