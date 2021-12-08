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
    final String INSERT_SQL = "INSERT INTO HOADON(MaNV,MaKH,NgayXuat,TongTien) values(?,?,?,?)";
    final String UPDATE_SQL = "UPDATE HOADON SET MaNV=?,MaKH=?,NgayXuat=?,TongTien=? WHERE MaHD=?";
    final String DELETE_SQL = "DELETE FROM HOADON WHERE MaHD=?";
    final String SELECT_ALL_SQL = "SELECT * FROM HOADON";
    final String SELECT_BY_ID_SQL = "SELECT * FROM HOADON WHERE MaHD= ?";
    
    @Override
    public void insert(HoaDon entity) {
        JDBCHelper.update(INSERT_SQL,entity.getMaNV(),entity.getMaKH(),entity.getNgayXuat(),entity.getTongTien());
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
                entity.setTongTien(rs.getFloat("TongTien"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
     public List<HoaDon> selectByKeyword(String keyword){
        String sql="SELECT * FROM HOADON WHERE MaHD LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
     public void updateTTien(float tt,String hd) {
        String sql ="UPDATE HOADON SET  TongTien=? WHERE MaHD=?";
        JDBCHelper.update(sql,  tt, hd);
    }
         public List<Integer> selectYear(){
    String sql ="Select distinct year(ngayxuat) nam from HOADON1 order by nam desc ";
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
         public List<Integer> selectMa(){
        String sql="select MAX(MaHD)   from HOADON1 ";
        List<Integer> listHD= new ArrayList<>();
        
        try {
            ResultSet rs = JDBCHelper.query(sql);
            while (rs.next()) {                
                listHD.add(rs.getInt(1));
            }
            
        } catch (Exception e) {
        }
        
        return listHD;
    }
}
