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
public class KhachHang {
    private int MaKH;
    private String TenKH;
    private String SDT;
    

    public KhachHang() {
    }

    public KhachHang(int MaKH, String TenKH, String SDT) {
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.SDT = SDT;
       
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

   
    @Override
    public String toString(){
        return MaKH +"" ;
    }
    
    
    
}
