/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlchs.dao;

import EduSys.entity.NhaXuatBan;
import EduSys.entity.TacGia;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import qlchs.utils.JDBCHelper;

/**
 *
 * @author Đức Toàn
 */
public class NhaXuatBanDAO extends QLNSDAO<NhaXuatBan, String>{
  
    String insertSql="INSERT NHAXUATBAN(MaNXB,TenNXB,DiaChi,Email,NamXuatBan,TrangThai) VALUES(?,?,?,?,?,?)";
    String updateSql="UPDATE NHAXUATBAN SET TenNXB=?,DiaChi=?,Email=?,NamXuatBan=?,TrangThai=? where MaNXB=?";
    String deleteSql="DELETE FROM NHAXUATBAN WHERE MaNXB=?";
    String selectAll="SELECT*FROM NHAXUATBAN";
    String selectById="SELECT*FROM NHAXUATBAN WHERE MaNXB=?";

    @Override
    public void insert(NhaXuatBan entity) {
        JDBCHelper.update(insertSql, entity.getMaNXB(),entity.getTenNXB(),entity.getDiaChi(),entity.getEmail(),entity.getNamXuatBan(),entity.isTrangThai());

    }

    @Override
    public void update(NhaXuatBan entity) {
        JDBCHelper.update(updateSql, entity.getTenNXB(),entity.getDiaChi(),entity.getEmail(),entity.getNamXuatBan(),entity.isTrangThai(),entity.getMaNXB());
       
    }

    @Override
    public void delete(String key) {
        JDBCHelper.update(deleteSql, key);
      
    }

    @Override
    public List<NhaXuatBan> selectAll() {
        return  this.selectBySql(selectAll);
       
    }

    @Override
    public NhaXuatBan selectById(String key) {
        List<NhaXuatBan> list = this.selectBySql(selectById, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhaXuatBan> selectBySql(String sql, Object... args) {
         List<NhaXuatBan> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {                
                NhaXuatBan entity= new NhaXuatBan();
                entity.setMaNXB(rs.getString("MaNXB"));
                entity.setTenNXB(rs.getString("TenNXB"));
                entity.setDiaChi(rs.getString("DiaChi"));
                entity.setEmail(rs.getString("Email"));
                entity.setNamXuatBan(rs.getDate("NamXuatBan"));
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
