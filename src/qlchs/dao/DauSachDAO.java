/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlchs.dao;

import EduSys.entity.DauSach;
import EduSys.entity.NhaXuatBan;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import qlchs.utils.JDBCHelper;

/**
 *
 * @author Đức Toàn
 */
public class DauSachDAO extends QLNSDAO<DauSach, String> {

    String insertSql = "INSERT DAUSACH(MaDauSach,TenDauSach,MaTG,MaTL,MaNXB) VALUES(?,?,?,?,?)";
    String selectAll="SELECT*FROM DAUSACH";
    String selectById="SELECT*FROM DAUSACH WHERE MaDauSach=?";
    String updateSql="UPDATE DAUSACH SET TenDauSach=?,MaTG=?,MaTL=? ,MaNXB=? where MaDauSach=?";
    String deleteSql="DELETE FROM DAUSACH WHERE MaDauSach=?";
    @Override
    public void insert(DauSach entity) {
        JDBCHelper.update(insertSql, entity.getMaDauSach(), entity.getTenDauSach(), entity.getMaTG(), entity.getMaTL(), entity.getMaNXB());

    }

    @Override
    public void update(DauSach entity) {
         JDBCHelper.update(updateSql, entity.getTenDauSach(), entity.getMaTG(), entity.getMaTL(), entity.getMaNXB(), entity.getMaDauSach());
    }

    @Override
    public void delete(String key) {
        JDBCHelper.update(deleteSql, key);
    }

    @Override
    public List<DauSach> selectAll() {
        return  this.selectBySql(selectAll);

    }

    @Override
    public DauSach selectById(String key) {
        List<DauSach> list = this.selectBySql(selectById, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<DauSach> selectBySql(String sql, Object... args) {
        List<DauSach> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                DauSach entity = new DauSach();
                entity.setMaDauSach(rs.getString("MaDauSach"));
                entity.setTenDauSach(rs.getString("TenDauSach"));
                entity.setMaTG(rs.getString("MaTG"));
                entity.setMaTL(rs.getString("MaTL"));
                entity.setMaNXB(rs.getString("MaNXB"));
                list.add(entity);
                System.out.println(list.size());

            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
