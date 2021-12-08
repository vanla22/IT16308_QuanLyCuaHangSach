/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlchs.utils;

import java.sql.*;

/**
 *
 * @author tachi
 */
public class JDBCHelper {

  public static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String dburl ="jdbc:sqlserver://LAPTOP-2SQU9GT2:1433;databaseName=QuanLyCuaHangSach";
    public static String username = "sa";
    public static String password = "123456789";
    static {
        try {
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
        Connection con = DriverManager.getConnection(dburl, username, password);
        PreparedStatement pstmt = null;
        try {
            if (sql.trim().startsWith("{")) {
                pstmt = con.prepareCall(sql);
            } else {
                pstmt = con.prepareStatement(sql);
            }

            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pstmt;
    }

    
    public static ResultSet query(String sql, Object... args) {
        try {

            PreparedStatement pstm = JDBCHelper.getStmt(sql, args);
            return pstm.executeQuery();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int update(String sql, Object... args) {
        try {
            PreparedStatement pstm = JDBCHelper.getStmt(sql, args);
            try {
                return pstm.executeUpdate();
            } finally {
                pstm.getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
