package com.example.fruits.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.fruits.util.ConnUtil;
import com.example.fruits.util.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;



    public abstract class BaseDAO<T> {

        private Class<T> clazz = null;
        protected Connection conn;

        //    public BaseDAO(){
//
//    }
        //静态代码块里不能调非静态代码
        {
            //获取当前BaseDAO的子类继承的父类中的泛型
            Type genericSuperclass=this.getClass().getGenericSuperclass();
            ParameterizedType parameterizedType=(ParameterizedType) genericSuperclass;
            Type[] typeArguments=parameterizedType.getActualTypeArguments();//获取了父类的泛型参数
            clazz=(Class<T>)typeArguments[0];//泛型的第一个参数 即BaseDAO<T>的<T>
        }
        public T getInstance( String sql, Object ...args) throws Exception{
                 conn = ConnUtil.getConn();
                QueryRunner runner=new QueryRunner();
                BeanHandler<T> handler=new BeanHandler<T>(clazz);
                T query = runner.query(conn, sql, handler, args);
                return query;

        }

        public List<T> getForList(String sql, Object...args) throws Exception {
                conn=ConnUtil.getConn();
                QueryRunner runner=new QueryRunner();
                BeanListHandler<T> handler=new BeanListHandler<>(clazz);
                List<T> list=runner.query(conn,sql,handler,args);
                return list;
        }
        public int updateOrder(String sql,Object...args) throws Exception {
                conn=ConnUtil.getConn();
                QueryRunner runner=new QueryRunner();
                int count= runner.update(conn,sql,args);
                return count;

        }
        public <E> E getValue(String sql,Object...args) throws Exception {
                conn=ConnUtil.getConn();
               PreparedStatement ps=conn.prepareStatement(sql);
                for(int i=0;i<args.length;i++){
                    ps.setObject(i+1,args[i]);
                }
               ResultSet rs =ps.executeQuery();
                if(rs.next()){
                    return (E) rs.getObject(1);
                }
                JDBCUtils.closeResource(null,ps,rs);
           return null;
        }
    }


