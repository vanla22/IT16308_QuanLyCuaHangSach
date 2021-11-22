/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlchs.dao;

import EduSys.entity.HoaDon;
import qlchs.utils.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HoaDonDAO extends QLNSDAO<HoaDon, Integer>{
    final String INSERT_SQL = "INSERT INTO HoaDon(MaHD,MaNV,MaKH,NgayXuat,TongTien) values(?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE HoaDon set MaNV=?,MaKH=?,NgayXuat=?,TongTien=? where MaHD=?";
    final String DELETE_SQL = "DELETE FROM HoaDon WHERE MaHD=?";
    final String SELECT_ALL_SQL = "SELECT * FROM HoaDon";
    final String SELECT_BY_ID_SQL = "SELECT * FROM HD WHERE MaHD= ?";
    @Override
    public void insert(HoaDon entity) {
        JDBCHelper.update(INSERT_SQL, entity.getMaHD(),entity.getMaNV(),entity.getMaKH(),entity.getNgayXuat(),entity.getTongTien());
    }

    @Override
    public void update(HoaDon entity) {
        JDBCHelper.update(UPDATE_SQL, entity.getMaNV(),entity.getMaKH(),entity.getNgayXuat(),entity.getTongTien(),entity.getMaHD());
    }

    @Override
    public void delete(Integer id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectById(Integer id) {
        List<HoaDon> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setNgayXuat(rs.getDate("NgayXuat"));
                entity.setTongTien(rs.getDouble("TongTien"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    
}
