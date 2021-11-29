/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlchs.dao;

import EduSys.entity.PhieuNhap;
import java.util.ArrayList;
import java.util.List;
import qlchs.utils.JDBCHelper;
import java.sql.ResultSet;

/**
 *
 * @author tachi
 */
public class phieuNhapDao extends QLNSDAO<PhieuNhap, String> {

    final String INSERT_SQL = "INSERT INTO PHIEUNHAP(MaPN,MaNV,MaNCC,NgayNhap,TongTien) values (?,?,?,?,?)";
    final String UPDATE_SQL = " UPDATE PHIEUNHAP set MaNV=?, MaNCC=?,NgayNhap=?,TongTien=? WHERE MaPN=?";
    final String DELETE_SQL = "DELETE FROM PHIEUNHAP WHERE MaPN=?";
    final String SELECT_ALL_SQL = "SELECT * FROM PHIEUNHAP";
    final String SELECT_BY_ID_SQL = "SELECT * FROM PHIEUNHAP WHERE MaPN= ?";

    @Override
    public void insert(PhieuNhap entity) {
        JDBCHelper.update(INSERT_SQL, entity.getMaPN(), entity.getMaNV(), entity.getMaNCC(), entity.getNgayNhap(), entity.getTongTien());
    }

    @Override
    public void update(PhieuNhap entity) {
        JDBCHelper.update(UPDATE_SQL, entity.getMaNV(), entity.getMaNCC(), entity.getNgayNhap(), entity.getTongTien(), entity.getMaPN());
    }

    @Override
    public void delete(String id) {
       JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<PhieuNhap> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhieuNhap selectById(String id) {
       List<PhieuNhap> list= selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhieuNhap> selectBySql(String sql, Object... args) {
       List<PhieuNhap> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {                
                PhieuNhap entity = new PhieuNhap();
                entity.setMaPN(rs.getString("MaPN"));
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMaNCC(rs.getString("MaNCC"));
                entity.setNgayNhap(rs.getDate("NgayNhap"));
                entity.setTongTien(rs.getDouble("TongTien"));
                list.add(entity);
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
public List<PhieuNhap> selectByKeyword(String keyword){
        String sql="SELECT * FROM PHIEUNHAP WHERE MaPN LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
 public void updateTTien(float tt,String pn) {
        String sql ="UPDATE PHIEUNHAP set  TongTien=? WHERE MaPN=?";
        JDBCHelper.update(sql,  tt, pn);
    }
}
