package com.example.fruits.impl.junit;

import com.example.fruits.bean.Fruits;
import com.example.fruits.dao.FruitDAO;
import com.example.fruits.impl.FruitDAOImpl;
import com.example.fruits.util.JDBCUtils;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.List;



class FruitDAOImplTest {
FruitDAO dao=new FruitDAOImpl();
    @Test
    void getFruitsList() {
        Connection conn=null;
        try {
             conn = JDBCUtils.getConnection();
            Integer page=1;
            String keyword="";
            List list= dao.getFruitsList(keyword,page);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Test
    void getkeyList(){
        Connection conn=null;
        try {
             conn = JDBCUtils.getConnection();
            Integer page=1;
            String keyword="好";
            List<Fruits> list = dao.getkeyList( keyword, page);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

}