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
public class CTPhieuNhap {

    private int MaCTPN;
    private String MaPN;
    private String MaSach;
    private int SoLuong;
    private float GiaNhap;
    private float ThanhTien;

    public CTPhieuNhap(int MaCTPN, String MaPN, String MaSach, int SoLuong, float GiaNhap, float ThanhTien) {
        this.MaCTPN = MaCTPN;
        this.MaPN = MaPN;
        this.MaSach = MaSach;
        this.SoLuong = SoLuong;
        this.GiaNhap = GiaNhap;
        this.ThanhTien = ThanhTien;
    }

    public float getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(float ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    public int getMaCTPN() {
        return MaCTPN;
    }

    public void setMaCTPN(int MaCTPN) {
        this.MaCTPN = MaCTPN;
    }

    public String getMaPN() {
        return MaPN;
    }

    public void setMaPN(String MaPN) {
        this.MaPN = MaPN;
    }

    public String getMaSach() {
        return MaSach;
    }

    public void setMaSach(String MaSach) {
        this.MaSach = MaSach;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public float getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(float GiaNhap) {
        this.GiaNhap = GiaNhap;
    }

  

    

    public CTPhieuNhap() {
    }

}
