package com.example.fruits.impl;

import java.sql.Connection;
import java.util.List;
import com.example.fruits.bean.Fruits;
import com.example.fruits.dao.FruitDAO;
import com.example.fruits.dao.BaseDAO;




    public class FruitDAOImpl extends BaseDAO<Fruits> implements FruitDAO {

        @Override
        public List<Fruits> getFruitsList(String keyword, Integer pageNo) throws Exception {
            String sql="select * from fruits_inventory where name like ? or remark like ? limit ?,5";
            List list=getForList(sql,"%"+keyword+"%","%"+keyword+"%",(pageNo-1)*5);
            return list;
        }

        @Override
        public List<Fruits> getkeyList(String keyword, Integer pageNo) throws Exception {
           String sql="select * from fruits_inventory where name like ? or remark like ? limit ?,5";
           List<Fruits> list=getForList(sql,"%"+keyword+"%","%"+keyword+"%",pageNo);
            return list;
        }

        @Override
        public Fruits getOneByID(int id) throws Exception {
            String sql="select name,price,count,remark,id from fruits_inventory where id=?";
            Fruits fruits=getInstance(sql,id);
            return fruits;
        }


        @Override
        public int insert( Fruits fruits) throws Exception {
            String sql="insert into fruits_inventory(name,price,count,remark) values(?,?,?,?)";
           int count=updateOrder(sql,fruits.getName(),fruits.getPrice(),fruits.getCount(),fruits.getRemark());
           return count;
        }

        @Override
        public void deleteByID(int id) throws Exception {
            String sql="delete from fruits_inventory where id=?";
            updateOrder(sql,id);
        }

        @Override
        public void update(Fruits fruits) throws Exception {
            String sql="update fruits_inventory set name=?,price=?,count=?,remark=? where id=?";
            updateOrder(sql,fruits.getName(),fruits.getPrice(),fruits.getCount(),fruits.getRemark(),fruits.getId());

        }

        @Override
        public List<Fruits> getAll() throws Exception {
            String sql="select name,price,count,remark,id from fruits_inventory";
            List<Fruits> list=getForList(sql);
            return list;
        }

        @Override
        public Long getCount(String keyword) throws Exception {
            String sql="select count(*) from  fruits_inventory where name like ? or remark like ?";
            Long count=getValue(sql,"%"+keyword+"%","%"+keyword+"%");
            return count;
        }

        @Override
        public Long getkeyCount(String keyword) {

            return null;
        }

    }


