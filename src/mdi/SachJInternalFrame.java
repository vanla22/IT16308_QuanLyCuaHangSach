/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdi;

import EduSys.entity.CTPhieuNhap;
import EduSys.entity.DauSach;
import EduSys.entity.Sach;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import qlchs.dao.CTPNDAO;
import qlchs.dao.DauSachDAO;
import qlchs.dao.SachDAO;
import qlchs.utils.MsgBox;

/**
 *
 * @author Admin
 */
public class SachJInternalFrame extends javax.swing.JInternalFrame {
DauSachDAO daoDS = new DauSachDAO();
    List<DauSach> listDS = daoDS.selectAll();
    SachDAO daoS = new SachDAO();
    List<Sach> listSach = new ArrayList<>();
    CTPhieuNhap listCTPN = new CTPhieuNhap();
    CTPNDAO daoCTPN =  new CTPNDAO();
    /**
     * Creates new form SachJInternalFrame
     */
    public SachJInternalFrame() {
        initComponents();
          updateTT();
        loadCbb();
        fillTable();
    }

     public void updateTT(){
        listSach  = daoS.selectAll();
        for(Sach x : listSach){
            if(x.getSoLuong()==0){
                
                String ma = x.getMaSach();
                boolean a = false;
                daoS.updateTT(ma,a);
            }
        }
        
    }
    public void loadCbb() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbb.getModel();
        cbb.removeAllItems();

        for (DauSach x : listDS) {
            cbb.addItem(x.getMaDauSach());
        }
    }

    public Sach getForm() {
        Sach cd = new Sach();
        cd.setMaDauSach(cbb.getSelectedItem().toString());

        cd.setMaSach(txtMaSach.getText());
        cd.setGiaBan(Float.parseFloat(txtGia.getText()));
        cd.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        cd.setTenSach(txtTen.getText());
        cd.setGhiChu(txtGhiChu.getText());
        boolean a = true;
        
        cd.setTrangThai(true);

        return cd;
    }

    public void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblSach.getModel();
        model.setRowCount(0);
        try {
            String keyword = txtTenSach.getText();
            listSach = daoS.selectByKeyword(keyword);
            for (Sach s : listSach) {
                Object row[] = {
                    s.getMaSach(), s.getTenSach(), s.getMaDauSach(), s.getSoLuong(), s.getGiaBan(), s.getGhiChu(), s.isTrangThai() == true ? "Con Hang" : "Het Hang"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi truy vấn");
            e.printStackTrace();
        }
        if (listSach == null) {
            JOptionPane.showMessageDialog(this, "Không có người học có tên: " + txtTenSach.getText());
        }
    }

//    public void insert() {
//        if (txtMaSach.getText().isEmpty() || txtTen.getText().isEmpty()
//                || txtGhiChu.getText().isEmpty()) {
//            MsgBox.alert(this, "Text trống!");
//            return;
//        } 
//
//        CTPhieuNhap m  = daoCTPN.selectGNById(txtMaSach.getText());
//        int gn = Integer.parseInt(txtGia.getText().trim());
//        
//        if(gn<=m.getGiaNhap()){
//            MsgBox.alert(this, "Giá bán phải hơn giá nhập");
//            return;
//        }
//
//            
//    }
    public void update() throws SQLException{
        
        if (txtMaSach.getText().isEmpty() || txtTen.getText().isEmpty()
                || txtGhiChu.getText().isEmpty()||txtGia.getText().isEmpty()||txtSoLuong.getText().isEmpty()) {
            MsgBox.alert(this, "Text trống!");
            return;
        } 
        int sl = Integer.parseInt(txtSoLuong.getText());
        try {
            if (sl <= 0) {
                MsgBox.alert(this, "So luong phai lon hon 0");
                return;
            }
        } catch (Exception e) {
            MsgBox.alert(this, "So luong phai la so");
            return;
        }
        float gia = Float.parseFloat(txtGia.getText());
        try {
            if (gia <= 0) {
                MsgBox.alert(this, "Gia nhap phai lon hon 0");
                return;
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Gia nhap khong phai la so");
            return;
        }
        

        
        List<Float> listGN = daoCTPN.selectGNMAX(txtMaSach.getText());
        CTPhieuNhap m  = daoCTPN.selectGNById(txtMaSach.getText());
        float gn = Float.parseFloat(txtGia.getText().trim());
        for (Float Float : listGN) {
            
        float c = Float.parseFloat(Float.toString());
        
        if(c >= gn ){
            MsgBox.alert(this, "Giá bán phải hơn giá nhập");
            return;
        }
        
        }
            Sach s = getForm();
        daoS.update(s);
        fillTable();

    }
    public void filist(int a){
        tblSach.setRowSelectionInterval(a, a);
        int i = tblSach.getSelectedRow();
        txtMaSach.setText(tblSach.getValueAt(i, 0).toString());
        txtTen.setText(tblSach.getValueAt(i, 1).toString());
        txtSoLuong.setText(tblSach.getValueAt(i, 3).toString());
        txtGia.setText(tblSach.getValueAt(i, 4).toString());
        txtGhiChu.setText(tblSach.getValueAt(i, 5).toString());
        
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblText = new javax.swing.JLabel();
        pnlCapNhap = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        btnSua = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        cbb = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        pnlDanhSach = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSach = new javax.swing.JTable();
        btnfisrt = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();

        lblText.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblText.setForeground(new java.awt.Color(0, 51, 204));
        lblText.setText("QUẢN LÝ SÁCH");

        pnlCapNhap.setBackground(new java.awt.Color(255, 255, 255));
        pnlCapNhap.setBorder(javax.swing.BorderFactory.createTitledBorder("Cập nhập"));
        pnlCapNhap.setToolTipText("");
        pnlCapNhap.setName(""); // NOI18N

        jLabel2.setText("MÃ SÁCH:");

        jLabel3.setText("TÊN SÁCH:");

        jLabel4.setText("MÃ ĐẦU SÁCH:");

        jLabel5.setText("SỐ LƯỢNG:");

        jLabel6.setText("GIÁ BÁN:");

        btnSua.setBackground(new java.awt.Color(39, 56, 120));
        btnSua.setForeground(new java.awt.Color(240, 240, 240));
        btnSua.setText("SỬA");
        btnSua.setBorder(null);
        btnSua.setMaximumSize(new java.awt.Dimension(75, 23));
        btnSua.setMinimumSize(new java.awt.Dimension(75, 23));
        btnSua.setPreferredSize(new java.awt.Dimension(75, 23));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(39, 56, 120));
        btnLamMoi.setForeground(new java.awt.Color(240, 240, 240));
        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.setBorder(null);
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        jLabel10.setText("GHI CHÚ:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane2.setViewportView(txtGhiChu);

        cbb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbActionPerformed(evt);
            }
        });

        jLabel15.setText("TRẠNG THÁI :");

        javax.swing.GroupLayout pnlCapNhapLayout = new javax.swing.GroupLayout(pnlCapNhap);
        pnlCapNhap.setLayout(pnlCapNhapLayout);
        pnlCapNhapLayout.setHorizontalGroup(
            pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhapLayout.createSequentialGroup()
                .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCapNhapLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(46, 46, 46)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlCapNhapLayout.createSequentialGroup()
                        .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCapNhapLayout.createSequentialGroup()
                                .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addGap(34, 34, 34))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlCapNhapLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)))
                        .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addComponent(cbb, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtGia)))
                    .addGroup(pnlCapNhapLayout.createSequentialGroup()
                        .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(39, 39, 39)
                        .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMaSach, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
                            .addComponent(txtTen)))
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
            .addGroup(pnlCapNhapLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        pnlCapNhapLayout.setVerticalGroup(
            pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCapNhapLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCapNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9))
        );

        pnlDanhSach.setBackground(new java.awt.Color(255, 255, 255));
        pnlDanhSach.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách"));
        pnlDanhSach.setPreferredSize(new java.awt.Dimension(343, 383));
        pnlDanhSach.setRequestFocusEnabled(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setText("TÊN SÁCH:");

        txtTenSach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenSachKeyReleased(evt);
            }
        });

        btnTim.setBackground(new java.awt.Color(39, 56, 120));
        btnTim.setForeground(new java.awt.Color(240, 240, 240));
        btnTim.setText("TÌM");
        btnTim.setBorder(null);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addGap(55, 55, 55)
                .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnTim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ SÁCH", "TÊN SÁCH", "MÃ ĐẦU SÁCH", "SỐ LƯỢNG", "GIÁ BÁN", "Ghi Chú", "TRẠNG THÁI"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSach);

        btnfisrt.setBackground(new java.awt.Color(39, 56, 120));
        btnfisrt.setForeground(new java.awt.Color(255, 255, 255));
        btnfisrt.setText("|<<");
        btnfisrt.setBorder(null);
        btnfisrt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfisrtActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(39, 56, 120));
        btnPrev.setForeground(new java.awt.Color(204, 255, 255));
        btnPrev.setText("<<");
        btnPrev.setBorder(null);
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(39, 56, 120));
        btnLast.setForeground(new java.awt.Color(240, 240, 240));
        btnLast.setText(">>|");
        btnLast.setBorder(null);
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(39, 56, 120));
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText(">>");
        btnNext.setBorder(null);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDanhSachLayout = new javax.swing.GroupLayout(pnlDanhSach);
        pnlDanhSach.setLayout(pnlDanhSachLayout);
        pnlDanhSachLayout.setHorizontalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDanhSachLayout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(btnfisrt, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDanhSachLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDanhSachLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDanhSachLayout.setVerticalGroup(
            pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDanhSachLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnfisrt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblText)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlCapNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlDanhSach, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblText)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDanhSach, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
                    .addComponent(pnlCapNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            update();
        } catch (SQLException ex) {
            Logger.getLogger(SachJInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here
        txtGia.setText("");
        txtGhiChu.setText("");
        txtMaSach.setText("");
        txtTen.setText("");
        txtSoLuong.setText("");
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void cbbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbActionPerformed

    private void txtTenSachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenSachKeyReleased
        // TODO add your handling code here:
        fillTable();
    }//GEN-LAST:event_txtTenSachKeyReleased

    private void tblSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSachMouseClicked
        // TODO add your handling code here:
        int i = tblSach.getSelectedRow();

        txtMaSach.setText(tblSach.getValueAt(i, 0).toString());
        txtTen.setText(tblSach.getValueAt(i, 1).toString());
        cbb.setSelectedItem(tblSach.getValueAt(i, 2).toString());
        txtSoLuong.setText(tblSach.getValueAt(i, 3).toString());
        txtGia.setText(tblSach.getValueAt(i, 4).toString());
        txtGhiChu.setText(tblSach.getValueAt(i, 5).toString());
        txtSoLuong.setEditable(true);
        txtGia.setEditable(true);
    }//GEN-LAST:event_tblSachMouseClicked

    private void btnfisrtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfisrtActionPerformed
        // TODO add your handling code here:
        filist(0);
    }//GEN-LAST:event_btnfisrtActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        int i = tblSach.getSelectedRow();
        i--;

        if(i<=-1){
            MsgBox.alert(this, "Min");
            return;
        }else{
            filist(i);
        }
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        filist(tblSach.getRowCount()-1);
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        int i = tblSach.getSelectedRow();
        System.out.println(i);
        if(i>=tblSach.getRowCount()-1){
            MsgBox.alert(this, "Max");
            return;
        }else{
            filist(i+1);
        }
    }//GEN-LAST:event_btnNextActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnfisrt;
    private javax.swing.JComboBox<String> cbb;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblText;
    private javax.swing.JPanel pnlCapNhap;
    private javax.swing.JPanel pnlDanhSach;
    private javax.swing.JTable tblSach;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenSach;
    // End of variables declaration//GEN-END:variables
}
