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
public class NhanVien {
    private String MaNV;
    private String TenNV;
    private String SDT;
    private Date NgaySinh;
    private boolean VaiTro;

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public boolean isVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(boolean VaiTro) {
        this.VaiTro = VaiTro;
    }

    public NhanVien() {
    }

    public NhanVien(String MaNV, String TenNV, String SDT, Date NgaySinh, boolean VaiTro) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.SDT = SDT;
        this.NgaySinh = NgaySinh;
        this.VaiTro = VaiTro;
    }

    
}
