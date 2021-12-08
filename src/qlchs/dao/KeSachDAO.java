/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qlchs.dao;

import EduSys.entity.KeSach;
import java.util.ArrayList;
import java.util.List;
import qlchs.utils.JDBCHelper;
import java.sql.*;

/**
 *
 * @author Admin
 */
public class KeSachDAO extends QLNSDAO<KeSach, String> {

    String insertSql = "insert into KeSach(makesach,vitrikesach) values (?,?)";
    String updateSql = "update kesach set  vitrikesach=? where makesach=?";
    String deleteSql = "delete from KeSach where makesach=?";
    String select_All_Sql = " select * from kesach";
    String select_sql_byID = "Select * from kesach where makesach=?";

    @Override
    public void insert(KeSach e) {
        JDBCHelper.update(insertSql, e.getMaKeSach(), e.getViTriKeSach());
    }

    @Override
    public void update(KeSach e) {
        JDBCHelper.update(updateSql, e.getViTriKeSach(), e.getMaKeSach());
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(deleteSql, id);
    }

    @Override
    public List<KeSach> selectAll() {
        return this.selectBySql(select_All_Sql);
    }

    @Override
    public KeSach selectById(String id) {
        List<KeSach> list = selectBySql(select_sql_byID, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KeSach> selectBySql(String sql, Object... args) {
        List<KeSach> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                KeSach ks = new KeSach();
                ks.setMaKeSach(rs.getString("makesach"));
                ks.setViTriKeSach(rs.getString("vitrikesach"));        
                list.add(ks);
            }
            rs.getStatement().getConnection().close();
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
            
        }      
    }

}
