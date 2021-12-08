/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlchs.dao;

import EduSys.entity.Sach;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import qlchs.utils.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static qlchs.utils.JDBCHelper.dburl;
import static qlchs.utils.JDBCHelper.password;
import static qlchs.utils.JDBCHelper.username;

/**
 *
 * @author Admin
 */
public class SachDAO extends QLNSDAO<Sach, String> {

    final String INSERT_SQL = "INSERT INTO Sach(MaSach,TenSach,GiaBan,SoLuong,TrangThai,MaDauSach,GhiChu,MaKeSach) values(?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE Sach set TenSach=?,GiaBan=?,SoLuong=?,TrangThai=?,MaDauSach=?,GhiChu=?,MaKeSach where MaSach=?";
    final String DELETE_SQL = "DELETE FROM Sach WHERE MaSach=?";
    final String SELECT_ALL_SQL = "SELECT * FROM Sach";
    final String SELECT_BY_ID_SQL = "SELECT * FROM Sach WHERE MaSach= ?";

    public List<Sach> selctbymaks(String mks) {
        String sql = "select * from sach where makesach =? ";
        return this.selectBySql(sql, mks);
    }

    @Override
    public void insert(Sach entity) {
        JDBCHelper.update(INSERT_SQL, entity.getMaSach(), entity.getTenSach(),
                entity.getGiaBan(), entity.getSoLuong(), entity.isTrangThai(), entity.getMaDauSach(), entity.getGhiChu(),entity.getMaKeSach()
        );
    }

    @Override
    public void update(Sach entity) {
        JDBCHelper.update(UPDATE_SQL, entity.getTenSach(),
                entity.getGiaBan(), entity.getSoLuong(), entity.isTrangThai(), entity.getMaDauSach(), entity.getGhiChu(),entity.getMaKeSach(), entity.getMaSach()
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
        List<Sach> list = selectBySql(SELECT_BY_ID_SQL, id);
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
                entity.setMaKeSach(rs.getString("MaKeSach"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Sach> selectByKeyword(String keyword) {
        String sql = "SELECT * FROM SACH WHERE TenSach LIKE ?";
        return this.selectBySql(sql, "%" + keyword + "%");
    }

    public void updateSL(int a, String b) {
        String sql = "UPDATE Sach set SoLuong=? where MaSach=?";
        JDBCHelper.update(sql, a, b);
    }
    public void updateKeSach(String makesach, String masach){
        String sql = "Update sach set makesach = ? where masach= ?";
        JDBCHelper.update(sql, makesach,masach);
    }
     public void deleteKeSach( String masach){
        try {
            String sql = "Update sach set makesach = ? where masach= ?";
            JDBCHelper.update(sql,null,masach);
            Connection con = DriverManager.getConnection(dburl, username, password);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setNull(1, Types.NULL);
            pstmt.setString(2, masach);
            System.out.println(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SachDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
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
