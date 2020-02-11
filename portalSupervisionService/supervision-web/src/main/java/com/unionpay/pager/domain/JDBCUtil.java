package com.unionpay.pager.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
/**
 *jdbc工具类连接wcm数据库
 * @author jinzhao
 * @date 2019-08-05
 */
@Component
public class JDBCUtil {


    static{
        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取连接
    public static Connection getConnction(String url,String username,String password) {

        try {
            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        } catch (SQLException e) {
            throw new RuntimeException("获取连接失败" + e);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    //关闭资源
    public static void close(Connection con, Statement stat, ResultSet rs){

        try {
            if(rs!=null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(stat!=null)
                stat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if(con!=null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
