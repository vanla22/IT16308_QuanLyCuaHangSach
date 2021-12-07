/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlchs.dao;

import EduSys.entity.NhaCungCap;
import java.util.List;
import qlchs.utils.JDBCHelper;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author tachi
 */
public class nhaCungCapDao extends QLNSDAO<NhaCungCap, String>{

     final String INSERT_SQL = "INSERT INTO NHACUNGCAP(MaNCC,TenNCC,DiaChi,SDT) values (?,?,?,?)";
    final String UPDATE_SQL = " UPDATE NHACUNGCAP set TenNCC=?, DiaChi=?,SDT=? WHERE MaNCC=?";
    final String DELETE_SQL = "DELETE FROM NHACUNGCAP WHERE MaNCC=?";
    final String SELECT_ALL_SQL = "SELECT * FROM NHACUNGCAP";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NHACUNGCAP WHERE MaNCC= ?";
    @Override
    public void insert(NhaCungCap entity) {
        JDBCHelper.update(INSERT_SQL, entity.getMaNCC(),entity.getTenNCC(),entity.getDiaChi(),entity.getSDT());
    }

    @Override
    public void update(NhaCungCap entity) {
        JDBCHelper.update(UPDATE_SQL,entity.getTenNCC(),entity.getDiaChi(),entity.getSDT(), entity.getMaNCC());
    }

    @Override
    public void delete(String id) {
       JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhaCungCap> selectAll() {
       return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhaCungCap selectById(String id) {
       List<NhaCungCap> list= selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhaCungCap> selectBySql(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet rs = new JDBCHelper().query(sql, args);
            while (rs.next()) {                
                NhaCungCap entity = new NhaCungCap();
                entity.setMaNCC(rs.getString("MaNCC"));
                entity.setTenNCC(rs.getString("TenNCC"));
                entity.setDiaChi(rs.getString("DiaChi"));
               
                entity.setSDT(rs.getString("SDT"));
                list.add(entity);
            }
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
        return list;
    }
     public List<NhaCungCap> selectByKeyword(String keyword){
        String sql="SELECT * FROM NHACUNGCAP WHERE MaNCC LIKE ?";
        return this.selectBySql(sql, "%"+keyword+"%");
    }
}
