package hust.cs.javacourse.ch32;

import java.sql.*;

public class SimpleTest {
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306/mysqlcrashcourse";
        Connection con = ConnectionFactory.create(url,"root","pkj123456");
        if (con != null) {
            try {
                String sql = "SELECT * FROM customers";
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    String custName = rs.getString("cust_name");
                    System.out.println(custName);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
