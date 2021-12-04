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

    private String MaKeSach;
    private String ViTriKeSach;

    public KeSach() {
    }

    public KeSach(String MaKeSach, String ViTriKeSach) {
        this.MaKeSach = MaKeSach;
        this.ViTriKeSach = ViTriKeSach;
    }

    public String getMaKeSach() {
        return MaKeSach;
    }

    public void setMaKeSach(String MaKeSach) {
        this.MaKeSach = MaKeSach;
    }

    public String getViTriKeSach() {
        return ViTriKeSach;
    }

    public void setViTriKeSach(String ViTriKeSach) {
        this.ViTriKeSach = ViTriKeSach;
    }
   
   
}
