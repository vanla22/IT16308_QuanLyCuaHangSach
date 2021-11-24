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

    
    public static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static String url="jdbc:sqlserver://localhost:1433;databaseName=QLNS";
    public static String username="sa";
    public static String password="songlong";
    //nạp driver
    static{
        try {
            Class.forName(driver);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    
    }
    public static PreparedStatement getStm(String sql,Object...args ) throws SQLException{
        Connection con=DriverManager.getConnection(url,username,password);
         PreparedStatement pstm;
         if (sql.trim().startsWith("{")) {
            pstm=con.prepareCall(sql);
        }else{
             pstm=con.prepareStatement(sql);
         }     
         for (int i = 0; i < args.length; i++) {
            pstm.setObject(i+1, args[i]);
        }
         return pstm;
             
    }
    
    public static ResultSet query(String sql,Object...args) throws SQLException {
        PreparedStatement pstm=JDBCHelper.getStm(sql,args);
        return pstm.executeQuery();
        
    }
    
    public static Object value(String sql,Object...args) {
        try {
            ResultSet rs=JDBCHelper.query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static int update(String sql,Object...args) {
        try {
            PreparedStatement pstm=JDBCHelper.getStm(sql, args);
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
