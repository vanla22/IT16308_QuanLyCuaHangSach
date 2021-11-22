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

    private String MaCTPN;
    private String MaPN;
    private String MaSach;
    private int SoLuong;
    private double GiaNhap;

    public CTPhieuNhap() {
    }

    public CTPhieuNhap(String MaCTPN, String MaPN, String MaSach, int SoLuong, double GiaNhap) {
        this.MaCTPN = MaCTPN;
        this.MaPN = MaPN;
        this.MaSach = MaSach;
        this.SoLuong = SoLuong;
        this.GiaNhap = GiaNhap;
    }

    public String getMaCTPN() {
        return MaCTPN;
    }

    public String getMaPN() {
        return MaPN;
    }

    public String getMaSach() {
        return MaSach;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public double getGiaNhap() {
        return GiaNhap;
    }

    public void setMaCTPN(String MaCTPN) {
        this.MaCTPN = MaCTPN;
    }

    public void setMaPN(String MaPN) {
        this.MaPN = MaPN;
    }

    public void setMaSach(String MaSach) {
        this.MaSach = MaSach;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public void setGiaNhap(double GiaNhap) {
        this.GiaNhap = GiaNhap;
    }

}
