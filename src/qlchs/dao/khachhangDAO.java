/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlchs.dao;

import EduSys.entity.KhachHang;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import qlchs.utils.JDBCHelper;

/**
 *
 * @author tachi
 */
public class khachhangDAO extends QLNSDAO<KhachHang, Integer> {

    final String INSERT_SQL = "INSERT INTO KHACHHANG (TenKH,SDT) values(?,?)";
    final String UPDATE_SQL = "UPDATE KHACHHANG set TenKH=?, SDT = ? WHERE MaKH=?";
    final String DELETE_SQL = "DELETE FROM KHACHHANG WHERE MaKH=?";
    final String SELECT_ALL_SQL = "SELECT * FROM KHACHHANG";
    final String SELECT_BY_ID_SQL = "SELECT * FROM KHACHHANG WHERE MaKH= ?";

    @Override
    public void insert(KhachHang entity) {
        JDBCHelper.update(INSERT_SQL, entity.getTenKH(), entity.getSDT());
    }

    @Override
    public void update(KhachHang entity) {
        JDBCHelper.update(UPDATE_SQL, entity.getTenKH(), entity.getSDT(), entity.getMaKH());
    }

     @Override
    public void delete(Integer id) {
      JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<KhachHang> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectById(Integer id) {
          List<KhachHang> list = selectBySql(SELECT_BY_ID_SQL,id);
        if(list.isEmpty()){
            return  null;
        } 
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
       List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getInt("MaKH"));
                entity.setTenKH(rs.getString("TenKH"));
                entity.setSDT(rs.getString("SDT"));
               

                list.add(entity);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
public List<KhachHang> selectByKeyword(String keyword){
        String sql="SELECT * FROM KHACHHANG WHERE MaKH LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
public List<KhachHang> selectBySDT(String keyword){
        String sql=" select *from KHACHHANG where SDT = ?";
        return this.selectBySql(sql, keyword);
    }
}
