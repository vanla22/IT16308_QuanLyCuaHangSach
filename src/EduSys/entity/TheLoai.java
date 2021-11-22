/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EduSys.entity;

/**
 *
 * @author Đức Toàn
 */
public class TheLoai {
    private String MaTL;
    private String TenTL;
    private boolean TrangThai;

    public TheLoai() {
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    public TheLoai(String MaTL, String TenTL, boolean TrangThai) {
        this.MaTL = MaTL;
        this.TenTL = TenTL;
        this.TrangThai = TrangThai;
    }

    public String getMaTL() {
        return MaTL;
    }

    public void setMaTL(String MaTL) {
        this.MaTL = MaTL;
    }

    public String getTenTL() {
        return TenTL;
    }

    public void setTenTL(String TenTL) {
        this.TenTL = TenTL;
    }

    
}
