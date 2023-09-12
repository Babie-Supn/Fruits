package com.example.fruits.biz;

import com.example.fruits.bean.Fruits;

import java.sql.Connection;
import java.util.List;

public interface FruitService {
    //获取指定页面库存列表信息
    List<Fruits> getFruitsList(String keyword, Integer pageNo) throws Exception;
    //添加库存记录信息
    void insert(Fruits fruits) throws Exception;
    //通过id查看指定的库存记录
    Fruits getOneByID(int id) throws Exception;
    //删除特定库存记录
    void deleteByID(int id) throws Exception;
    //获取总页数
    Long getPageCount(String keyword) throws Exception;
    //修改特定库存记录
    void updateFruit(Fruits fruits) throws Exception;
    Long getCount(String keyword) throws Exception;

}
