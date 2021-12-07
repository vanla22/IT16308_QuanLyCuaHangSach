/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdi;

import duan1_ui.*;
import EduSys.entity.NhanVien;
import java.awt.Color;
import qlchs.dao.nhanvienDAO;
import qlchs.utils.Auth;
import qlchs.utils.MsgBox;

/**
 *
 * @author user
 */
public class DangNhapJDialog extends javax.swing.JDialog {

    /**
     * Creates new form DangNhapJDialog
     */
    public DangNhapJDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.star();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTenTaiKhoan = new javax.swing.JTextField();
        btnDangNhap = new javax.swing.JButton();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(500, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(400, 350));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
        jLabel1.setText("ĐĂNG NHẬP");

        txtTenTaiKhoan.setBackground(new java.awt.Color(240, 240, 240));
        txtTenTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtTenTaiKhoan.setForeground(new java.awt.Color(153, 153, 153));
        txtTenTaiKhoan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(39, 56, 120)));
        txtTenTaiKhoan.setPreferredSize(new java.awt.Dimension(85, 24));
        txtTenTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenTaiKhoanActionPerformed(evt);
            }
        });

        btnDangNhap.setBackground(new java.awt.Color(39, 56, 120));
        btnDangNhap.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDangNhap.setForeground(new java.awt.Color(240, 240, 240));
        btnDangNhap.setText("ĐĂNG NHẬP");
        btnDangNhap.setBorder(null);
        btnDangNhap.setPreferredSize(new java.awt.Dimension(60, 20));
        btnDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangNhapActionPerformed(evt);
            }
        });

        txtMatKhau.setBackground(new java.awt.Color(240, 240, 240));
        txtMatKhau.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(39, 56, 120)));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Tên Đăng Nhập");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Mật Khẩu ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                        .addComponent(txtMatKhau))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenTaiKhoan, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addComponent(btnDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangNhapActionPerformed
       // this.check();
        this.dangNhap();
    }//GEN-LAST:event_btnDangNhapActionPerformed

    private void txtTenTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenTaiKhoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenTaiKhoanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DangNhapJDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DangNhapJDialog dialog = new DangNhapJDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangNhap;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTenTaiKhoan;
    // End of variables declaration//GEN-END:variables

    private void star() {
        this.setLocationRelativeTo(null);

    }
    nhanvienDAO dao = new nhanvienDAO();

    void dangNhap() {
//        String maNV = txtTenTaiKhoan.getText();
//        String matKhau = new String(txtMatKhau.getPassword());
//        NhanVien nv = dao.selectById(maNV);
//        if (nv == null) {
//            MsgBox.alert(this, "Tên tài khoản không tồn tại!");
//            this.txtTenTaiKhoan.setBackground(Color.yellow);
//            this.txtTenTaiKhoan.requestFocus();
//        }if (!matKhau.equals(nv.getMatKhau())) {
//            MsgBox.alert(this, "Sai mật khầu!");
//            this.txtMatKhau.setBackground(Color.yellow);
//            this.txtMatKhau.requestFocus();
//        } else {
//            Auth.user = nv;
//            this.txtMatKhau.setBackground(Color.white);
//            this.txtTenTaiKhoan.setBackground(Color.white);
//            this.dispose();
//            
//        }
String tdn = this.txtTenTaiKhoan.getText();
        String mk =new String(this.txtMatKhau.getPassword());
        if(tdn.length()==0){
            MsgBox.alert(this, "Tên đăng nhập không được để trống!");
            this.txtTenTaiKhoan.setBackground(Color.yellow);
            this.txtTenTaiKhoan.requestFocus();
            return;
        }else{
            this.txtTenTaiKhoan.setBackground(Color.white);
        }
        
        if(mk.equals("")){
            MsgBox.alert(this, "Mật khẩu không được để trống!");
            this.txtMatKhau.setBackground(Color.yellow);
            this.txtMatKhau.requestFocus();
            return;
        }else{
            this.txtMatKhau.setBackground(Color.white);
        }
           nhanvienDAO dao=new nhanvienDAO();
           NhanVien nv=dao.selectById(tdn);
        try {
        
            if (nv == null) {
                MsgBox.alert(this, "Tên tài khoản không tồn tại!");
                this.txtTenTaiKhoan.setBackground(Color.yellow);
                this.txtTenTaiKhoan.requestFocus();
                return;
            }else{
                this.txtTenTaiKhoan.setBackground(Color.white);
            }
            if(!mk.equals(nv.getMatKhau())){
               MsgBox.alert(this, "Mật khẩu không chính xác!");
                this.txtMatKhau.setBackground(Color.yellow);
                this.txtMatKhau.requestFocus();
                return;
            }else{
                this.txtMatKhau.setBackground(Color.white);
            }
           
        } catch (Exception ex) {
            MsgBox.alert(this, "Lỗi kết nối máy chủ !");
            return;
        }
        MsgBox.alert(this, "Đăng nhập thành công!");
        Auth.user=nv;
        this.dispose();
       
        
    }

//    void check() {
//        String maNV = txtTenTaiKhoan.getText();
//        String matKhau = new String(txtMatKhau.getPassword());
//        if (maNV.length() == 0) {
//            MsgBox.alert(this, "Tên tài khoản không được để trống!");
//            this.txtTenTaiKhoan.setBackground(Color.yellow);
//            this.txtTenTaiKhoan.requestFocus();
//        }
//        if (matKhau.length() == 0) {
//            MsgBox.alert(this, "Mật khẩu không được để trống!");
//            this.txtMatKhau.setBackground(Color.yellow);
//            this.txtMatKhau.requestFocus();
//        }
//    }
}