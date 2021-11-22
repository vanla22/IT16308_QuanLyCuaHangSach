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
public class TacGia {
    private String MaTG;
    private String TenTG;
    

    public TacGia() {
    }

    public String getMaTG() {
        return MaTG;
    }

    public void setMaTG(String MaTG) {
        this.MaTG = MaTG;
    }

    public String getTenTG() {
        return TenTG;
    }

    public void setTenTG(String TenTG) {
        this.TenTG = TenTG;
    }

    

    public TacGia(String MaTG, String TenTG ) {
        this.MaTG = MaTG;
        this.TenTG = TenTG;
        
    }
}
