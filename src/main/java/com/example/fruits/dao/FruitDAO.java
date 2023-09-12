package com.example.fruits.dao;

import java.sql.Connection;
import java.util.List;
import com.example.fruits.bean.Fruits;
    public interface FruitDAO {

        //获取指定页码上的库存信息
        List<Fruits> getFruitsList(String keyword,Integer pageNo) throws Exception;

        List<Fruits> getkeyList(String keyword,Integer pageNo) throws Exception;


        Fruits getOneByID(int id) throws Exception;

        int insert(Fruits fruits) throws Exception;

        void deleteByID(int id) throws Exception;

        void update(Fruits fruits) throws Exception;

        List<Fruits> getAll() throws Exception;

        Long getCount(String keyword) throws Exception;

        Long getkeyCount(String keyword);



    }


