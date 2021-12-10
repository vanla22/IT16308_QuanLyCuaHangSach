/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSys.entity;

import java.util.Date;

/**
 *
 * @author Đức Toàn
 */
public class PhieuNhap {
    private int MaPN;
    private String MaNV;
    private String MaNCC;
    private Date NgayNhap;
    private float TongTien;

    public PhieuNhap() {
    }

    
    public PhieuNhap(int MaPN, String MaNV, String MaNCC, Date NgayNhap, float TongTien) {
        this.MaPN = MaPN;
        this.MaNV = MaNV;
        this.MaNCC = MaNCC;
        this.NgayNhap = NgayNhap;
        this.TongTien = TongTien;
    }
   
    public int getMaPN() {
        return MaPN;
    }

    public void setMaPN(int MaPN) {
        this.MaPN = MaPN;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public Date getNgayNhap() {
        return NgayNhap;
    }

    public void setNgayNhap(Date NgayNhap) {
        this.NgayNhap = NgayNhap;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float TongTien) {
        this.TongTien = TongTien;
    }

}
