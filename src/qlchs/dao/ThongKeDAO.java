/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlchs.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import qlchs.utils.JDBCHelper;

/**
 *
 * @author Admin
 */
public class ThongKeDAO {
    private List<Object[]> getListofArray(String sql, String[] cols, Object...args){
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet rs = JDBCHelper.query(sql, args);
            while(rs.next()){
            Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i]=rs.getObject(cols[i]);                  
                }
                list.add(vals);
            }
            rs.getStatement().getConnection().close();
            return list;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } 
    
      public List<Object[]> getThongKeDoanhThu(int nam) {
        String sql = "{call sp_TKDT_TungThangTheoNam(?)}";
        String[] cols = {"Thang","Soluongkhachhang","Soluongsanphambanra","Doanhthu"};
        return this.getListofArray(sql, cols,nam);
    }
      public List<Object[]> getThongKeSPBanChay(int year) {
        String sql = "{call  sp_thongkespbanchay(?)}";
        String[] cols = {"maSach", "tenSach", "soLuongBan","thanhTien"};
        return this.getListofArray(sql, cols,year);

    }
}
