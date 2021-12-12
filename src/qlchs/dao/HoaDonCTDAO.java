/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlchs.dao;

import EduSys.entity.HoaDonCT;
import qlchs.utils.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class HoaDonCTDAO extends QLNSDAO<HoaDonCT, Integer>{
    final String INSERT_SQL = "INSERT INTO HOADONCHITIET(MaHD,MaSach,SoLuong,GiaBan,ThanhTien,TenSach) values(?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE HOADONCHITIET set MaHD=?,MaSach=?,SoLuong=?,GiaBan=?,ThanhTien=?,TenSach=? WHERE MaHDCT=?";
    final String DELETE_SQL = "DELETE FROM HOADONCHITIET WHERE MaHDCT=?";
    final String SELECT_ALL_SQL = "SELECT * FROM HOADONCHITIET";
    final String SELECT_BY_ID_SQL = "SELECT * FROM HOADONCHITIET WHERE MaHDCT= ?";
   
    @Override
    public void insert(HoaDonCT entity) {
        JDBCHelper.update(INSERT_SQL, entity.getMaHD(),entity.getMaSach(),
                entity.getSoLuong(),entity.getGiaBan(),entity.getThanhTien(),entity.getTenSach());
    }

    @Override
    public void update(HoaDonCT entity) {
        JDBCHelper.update(UPDATE_SQL,entity.getMaHD(),entity.getMaSach(),entity.getSoLuong(),
                entity.getGiaBan(),entity.getThanhTien(), entity.getMaHDCT(),entity.getTenSach());
    }

    @Override
    public void delete(Integer id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDonCT> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDonCT selectById(Integer id) {
        List<HoaDonCT> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDonCT> selectBySql(String sql, Object... args) {
        List<HoaDonCT> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                HoaDonCT entity = new HoaDonCT();
                entity.setMaHDCT(rs.getInt("MaHDCT"));
                entity.setMaHD(rs.getInt("MaHD"));
                entity.setMaSach(rs.getString("MaSach"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setGiaBan(rs.getFloat("GiaBan"));
                entity.setThanhTien(rs.getFloat("ThanhTien"));
                entity.setTenSach(rs.getString("TenSach"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
     public List<HoaDonCT> selectByKeyword(String keyword){
        String sql="SELECT * FROM HOADONCHITIET WHERE MaHD = ?";
        return this.selectBySql(sql, keyword);
    }
       
}
