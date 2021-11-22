/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package qlchs.dao;




import EduSys.entity.TacGia;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import qlchs.utils.JDBCHelper;

/**
 *
 * @author Đức Toàn
 */
public class TacGiaDAO extends QLNSDAO<TacGia, String>{
    String insertSql="INSERT TACGIA(MaTG,TenTG) VALUES(?,?)";
    String updateSql="UPDATE TACGIA SET TenTG=? where MaTG=?";
    String deleteSql="DELETE FROM TACGIA WHERE MaTG=?";
    String selectAll="SELECT*FROM TACGIA";
    String selectById="SELECT*FROM TACGIA WHERE MaTG=?";

    @Override
    public void insert(TacGia entity) {
        JDBCHelper.update(insertSql, entity.getMaTG(),entity.getTenTG());
    }

    @Override
    public void update(TacGia entity) {
        JDBCHelper.update(updateSql, entity.getMaTG(),entity.getTenTG());
    }

    @Override
    public void delete(String key) {
        JDBCHelper.update(deleteSql, key);
    }

    @Override
    public List<TacGia> selectAll() {
        return  this.selectBySql(selectAll);
    }

    @Override
    public TacGia selectById(String key) {
        List<TacGia> list = this.selectBySql(selectById, key);
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<TacGia> selectBySql(String sql, Object... args) {
         List<TacGia> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {                
                TacGia entity= new TacGia();
                entity.setMaTG(rs.getString("MaTG"));
                entity.setTenTG(rs.getString("TenTG"));
                
                list.add(entity);

            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
        
    }

    
    
}
