/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlchs.dao;

import EduSys.entity.CTPhieuNhap;
import EduSys.entity.Sach;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import qlchs.utils.JDBCHelper;

/**
 *
 * @author Đức Toàn
 */
public class CTPNDAO extends QLNSDAO<CTPhieuNhap, String> {

    final String INSERT_SQL = "INSERT INTO CTPHIEUNHAP(MaPN,MaSach,SoLuong,GiaNhap,ThanhTien) values (?,?,?,?,?)";
    final String UPDATE_SQL = " UPDATE CTPHIEUNHAP set MaPN=?,MaSach=?, SoLuong=?,GiaNhap=?,ThanhTien=? WHERE MaCTPN=?";
    final String DELETE_SQL = "DELETE FROM CTPHIEUNHAP WHERE MaCTPN=?";
    final String SELECT_ALL_SQL = "SELECT * FROM CTPHIEUNHAP";
    final String SELECT_BY_ID_SQL = "SELECT * FROM CTPHIEUNHAP WHERE MaCTPN= ?";

    @Override
    public void insert(CTPhieuNhap entity) {
        JDBCHelper.update(INSERT_SQL,entity.getMaPN(), entity.getMaSach(),  entity.getSoLuong(), entity.getGiaNhap(),entity.getThanhTien());
    }

    @Override
    public void update(CTPhieuNhap entity) {
        JDBCHelper.update(UPDATE_SQL, entity.getMaPN(), entity.getMaSach(),  entity.getSoLuong(), entity.getGiaNhap(),entity.getThanhTien(), entity.getMaCTPN());
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<CTPhieuNhap> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public CTPhieuNhap selectById(String id) {
        List<CTPhieuNhap> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<CTPhieuNhap> selectBySql(String sql, Object... args) {
        List<CTPhieuNhap> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {

                CTPhieuNhap entity = new CTPhieuNhap();
                entity.setMaCTPN(rs.getInt("MaCTPN"));
                entity.setMaPN(rs.getString("MaPN"));
                entity.setMaSach(rs.getString("MaSach"));
                entity.setSoLuong(rs.getInt("SoLuong"));
                entity.setGiaNhap(rs.getFloat("GiaNhap"));
                entity.setThanhTien(rs.getFloat("ThanhTien"));

                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    public List<CTPhieuNhap> selectByKeyword(String keyword){
        String sql="SELECT * FROM CTPHIEUNHAP WHERE MaPN LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
    public CTPhieuNhap selectGNById(String id) {
        String sql = "SELECT * FROM CTPHIEUNHAP WHERE MaSach=?";
        List<CTPhieuNhap> list = selectBySql(sql, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
    public List<Float> selectGNMAX( String ma){
        String sql="select MAX(GiaNhap)   from CTPHIEUNHAP where MaSach=?";
        List<Float> listGN= new ArrayList<>();
        
        try {
            ResultSet rs = JDBCHelper.query(sql,ma);
            while (rs.next()) {                
                listGN.add(rs.getFloat(1));
            }
            
        } catch (Exception e) {
        }
        
        return listGN;

  
    }

}
