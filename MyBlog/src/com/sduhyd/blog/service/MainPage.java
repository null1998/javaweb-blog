package com.sduhyd.blog.service;

import com.sduhyd.blog.bean.Essay;
import com.sduhyd.blog.daoImp.EssayDaoImpl;
import com.sduhyd.blog.utils.BeanUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainPage {
    public static List<Object> getAllEssays()throws IOException {
        List<Map<String,Object>> list= EssayDaoImpl.read();
        List<Object> essayList= BeanUtils.setAll(new Essay(),list);
        return essayList;
    }
}
