package com.example.fruits.biz.impl;

import com.example.fruits.bean.Fruits;
import com.example.fruits.biz.FruitService;
import com.example.fruits.dao.FruitDAO;
import com.example.fruits.util.ConnUtil;

import java.sql.Connection;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private FruitDAO fruitDAO=null;
    @Override
    public List<Fruits> getFruitsList(String keyword, Integer pageNo) throws Exception {
        System.out.println("getFruitList->"+ ConnUtil.getConn());
        return fruitDAO.getFruitsList(keyword,pageNo);
    }

    @Override
    public void insert(Fruits fruits) throws Exception {

        fruitDAO.insert(fruits);
    }

    @Override
    public Fruits getOneByID(int id) throws Exception {

        return fruitDAO.getOneByID(id);
    }

    @Override
    public void deleteByID(int id) throws Exception {
         fruitDAO.deleteByID(id);
    }

    @Override
    public Long getPageCount(String keyword) throws Exception {
        System.out.println("getPageCount->"+ConnUtil.getConn());
        Long count = fruitDAO.getCount(keyword);
        Long pageCount=(count+5-1)/5;
        return pageCount;
    }

    @Override
    public void updateFruit(Fruits fruits) throws Exception {
        fruitDAO.update(fruits);
    }

    @Override
    public Long getCount(String keyword) throws Exception {
        System.out.println("getCount->"+ConnUtil.getConn());
        return fruitDAO.getCount(keyword);
    }


}
