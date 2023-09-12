package com.example.fruits.controller;

import com.example.fruits.bean.Fruits;
import com.example.fruits.biz.FruitService;
import com.example.fruits.util.JDBCUtils;
import com.example.fruits.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;
public class FruitsController{
   private FruitService fruitService=null;

    private String update(Integer fid,String fname,double fprice,Integer fcount,String fremark) {

        try {
            Fruits fruits = new Fruits(fname, fprice, fcount, fremark, fid);

            fruitService.updateFruit(fruits);
            return "redirect:fruit.do";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String index(String oper,String keyword,Integer pageNo,HttpServletRequest req) {

        try {
            HttpSession session=req.getSession();

            if(pageNo==null){
                pageNo=1;
            }
            if(StringUtils.isNotEmpty(oper) && "search".equals(oper)){
                if(StringUtils.isEmpty(keyword)){
                    keyword="";
                }
                session.setAttribute("keyword",keyword);
            }else {
                Object keyobj = session.getAttribute("keyword");
                if(keyobj!=null){
                    keyword=(String) keyobj;
                }else {
                    keyword="";
                }
            }
            Long fcount= fruitService.getCount(keyword);
            int acount=Long.valueOf(fcount).intValue();
            Integer pageCount=(acount+4)/5;
            session.setAttribute("pageNo",pageNo);
            session.setAttribute("pageCount",pageCount);
            List<Fruits> fruitsList=fruitService.getFruitsList(keyword,pageNo);
            session.setAttribute("fruitsList", fruitsList);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String add(String fname,double fprice,Integer fcount,String fremark){

        try {
            Fruits fruits = new Fruits(fname, fprice, fcount, fremark);
            fruitService.insert(fruits);
            return "redirect:add.html";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private String del(Integer fid) {

        try {
            if(fid!=null) {

                fruitService.deleteByID(fid);
                return "redirect:fruit.do";
            }else {
                System.out.println("获取id为空，不可删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String edit(Integer fid,HttpServletRequest req) {
        try {
            if(fid!=null) {
                Fruits fruits=fruitService.getOneByID(fid);
                req.setAttribute("fruit",fruits);
                return "edit";
            } else if (fid==null) {
                System.out.println("传入edit的id为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
