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
public class KeSach {

    private String MaKS;
    private String MaTL;
    private String TenSach;

    public KeSach(String MaKS, String MaTL, String TenSach) {
        this.MaKS = MaKS;
        this.MaTL = MaTL;
        this.TenSach = TenSach;
    }

    public String getTensach() {
        return TenSach;
    }

    public void setTensach(String tensach) {
        this.TenSach = tensach;
    }

    public KeSach() {
    }

    public String getMaKS() {
        return MaKS;
    }

    public void setMaKS(String MaKS) {
        this.MaKS = MaKS;
    }

    public String getMaTL() {
        return MaTL;
    }

    public void setMaTL(String MaTL) {
        this.MaTL = MaTL;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String TenSach) {
        this.TenSach = TenSach;
    }

}
