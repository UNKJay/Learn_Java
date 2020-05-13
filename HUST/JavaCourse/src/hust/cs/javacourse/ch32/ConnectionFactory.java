package hust.cs.javacourse.ch32;

import java.sql.*;

public class ConnectionFactory {
    public static Connection create(String url , String root , String pwd) {
        Connection con = null;
        System.out.println("正在连接数据库");
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection(url,root,pwd);
            if (con != null) {
                System.out.println("数据库成功连接。");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("数据库驱动加载错误，" + e.toString());
        } catch (SQLException e) {
            System.out.println("数据库连接错误，" + e.toString());
        }
        return con;
    }
}


