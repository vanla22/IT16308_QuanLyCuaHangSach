/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlchs.dao;


import EduSys.entity.Sach;
import qlchs.utils.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class SachDAO extends QLNSDAO<Sach, String>{
 final String INSERT_SQL = "INSERT INTO Sach(MaSach,TenSach,GiaBan,SoLuong,TrangThai,MaDauSach,GhiChu) values(?,?,?,?,?,?.?)";
    final String UPDATE_SQL = "UPDATE Sach set TenSach=?,GiaBan=?,SoLuong=?,TrangThai=?,MaDauSach=?,GhiChu=? where MaSach=?";
    final String DELETE_SQL = "DELETE FROM Sach WHERE MaSach=?";
    final String SELECT_ALL_SQL = "SELECT * FROM Sach";
    final String SELECT_BY_ID_SQL = "SELECT * FROM Sach WHERE MaSach= ?";

    @Override
    public void insert(Sach entity) {
        JDBCHelper.update(INSERT_SQL, entity.getMaSach(),entity.getTenSach(),
                entity.getGiaBan(),entity.getSoLuong(),entity.isTrangThai(),entity.getMaDauSach(),entity.getGhiChu()
        
        );
    }

    @Override
    public void update(Sach entity) {
JDBCHelper.update(UPDATE_SQL, entity.getTenSach(),
                entity.getGiaBan(),entity.getSoLuong(),entity.isTrangThai(),entity.getMaDauSach(),entity.getGhiChu(),entity.getMaSach()
);
        }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<Sach> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public Sach selectById(String id) {
        List<Sach> list= selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<Sach> selectBySql(String sql, Object... args) {
        List<Sach> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                
                Sach entity = new Sach();
                entity.setMaSach(rs.getString("MaSach"));
                entity.setTenSach(rs.getString("TenSach"));
                
                entity.setGiaBan(rs.getDouble("GiaBan"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setTrangThai(rs.getBoolean("TrangThai"));
                entity.setMaDauSach(rs.getString("MaDauSach"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (Exception e) {
           throw new RuntimeException(e);
        }
        return list;
    }
    public List<Sach> selectByKeyword(String keyword){
        String sql="SELECT * FROM SACH WHERE TenSach LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
    public void updateSL(int a,String b){
        String sql="UPDATE Sach set SoLuong=? where MaSach=?";
        JDBCHelper.update(sql,a,b);
    }
    public List<Sach> selectByKeyword1(String keyword){
        String sql="SELECT * FROM SACH WHERE TenSach = ?";
        return this.selectBySql(sql, keyword);
    }
    public void updateTT(String ma , boolean tt) {
        String sql ="UPDATE Sach set TrangThai=? where MaSach=?";
JDBCHelper.update(sql, tt,ma);
        }
}
