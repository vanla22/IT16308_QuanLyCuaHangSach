/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlchs.dao;

import EduSys.entity.TacGia;
import EduSys.entity.TheLoai;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import qlchs.utils.JDBCHelper;

/**
 *
 * @author Đức Toàn
 */
public class TheLoaiDAO extends QLNSDAO<TheLoai, String>{
   
    String insertSql="INSERT THELOAI(MaTL,TenTL,TrangThai) VALUES(?,?,?)";
    String updateSql="UPDATE THELOAI SET TenTL=?,TrangThai=? where MaTL=?";
    String deleteSql="DELETE FROM THELOAI WHERE MaTL=?";
    String selectAll="SELECT*FROM THELOAI";
    String selectById="SELECT*FROM THELOAI WHERE MaTL=?";
      public List<Integer> selectTheLoai(){
    String sql ="Select MaTL from THELOAI  ";
    List<Integer> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql);
            while(rs.next()){
            list.add(rs.getInt(1));            
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    
    
    }
   
    @Override
    public void insert(TheLoai entity) {
        JDBCHelper.update(insertSql, entity.getMaTL(),entity.getTenTL(),entity.isTrangThai());
    }

    @Override
    public void update(TheLoai entity) {
        JDBCHelper.update(updateSql, entity.getTenTL(),entity.isTrangThai(),entity.getMaTL());
    }

    @Override
    public void delete(String key) {
        JDBCHelper.update(deleteSql, key);
    }

    @Override
    public List<TheLoai> selectAll() {
        return this.selectBySql(selectAll);
    }

    @Override
    public TheLoai selectById(String key) {
        List<TheLoai> list = this.selectBySql(selectById, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<TheLoai> selectBySql(String sql, Object... args) {
        List<TheLoai> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {                
                TheLoai entity= new TheLoai();
                entity.setMaTL(rs.getString("MaTL"));
                entity.setTenTL(rs.getString("TenTL"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                list.add(entity);

            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
