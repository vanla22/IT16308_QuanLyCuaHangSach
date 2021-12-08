/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdi;

import EduSys.entity.KeSach;
import EduSys.entity.Sach;
import EduSys.entity.TheLoai;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import qlchs.dao.KeSachDAO;
import qlchs.dao.SachDAO;
import qlchs.dao.TheLoaiDAO;
import qlchs.utils.MsgBox;

/**
 *
 * @author Admin
 */
public class KeSach1JInternalFrame extends javax.swing.JInternalFrame {

    SachDAO sahdao = new SachDAO();
    KeSachDAO dao = new KeSachDAO();
    int row = -1;
    int raw = -1;
    TheLoaiDAO tldao = new TheLoaiDAO();
    List<KeSach> list = new ArrayList<>();
    DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
    DefaultTableModel dtm = new DefaultTableModel();
    static String maksStatic;
    /**
     * Creates new form KeSach1JInternalFrame
     */
    public KeSach1JInternalFrame() {
        initComponents();
         filltablekesach();
    }

    
    public void filltablekesach() {
        DefaultTableModel dtm = (DefaultTableModel) tblKeSach.getModel();
        dtm.setRowCount(0);
        try {
            List<KeSach> list = dao.selectAll();
            for (KeSach ks : list) {
                Object[] row = {
                    ks.getMaKeSach(),
                    ks.getViTriKeSach()};
                dtm.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Loi truy van du lieu !");
            e.printStackTrace();
        }

    }

    void setForm(KeSach model) {
        txtMaks.setText(model.getMaKeSach());
        txtViTri.setText(model.getViTriKeSach());
    }

    KeSach getForm() {
        KeSach ks = new KeSach();
        ks.setMaKeSach(txtMaks.getText());
        ks.setViTriKeSach(txtViTri.getText());
        return ks;
    }

    void edit() {
        String maks = (String) tblKeSach.getValueAt(this.row, 0);
        KeSach ks = dao.selectById(maks);
        this.setForm(ks);
    }

    void clearForm() {
        txtMaks.setText("");
        txtViTri.setText("");
    }

    void insert() {
        KeSach model = getForm();
        if (dao.selectById(txtMaks.getText()) != null) {
            MsgBox.alert(this, "Không được trùng mã kệ sách");
            return;
        } else if (txtMaks.getText().equals("")) {
            MsgBox.alert(this, "Không được để trống mã kệ sách");
            return;

        } else if (txtViTri.getText().equals("")) {
            MsgBox.alert(this, "Không được để trống vị trí kệ sách");
            return;

        } else {
            try {
                dao.insert(model);
                this.filltablekesach();
                this.clearForm();
                MsgBox.alert(this, "Them thanh cong !!!");
            } catch (Exception e) {
                MsgBox.alert(this, "Them that bai !!!");
                e.printStackTrace();
            }

        }
    }

    void update() {

        KeSach model = getForm();
        if (txtMaks.getText().equals("")) {
            MsgBox.alert(this, "Không được để trống mã kệ sách");
            return;

        } else if (txtViTri.getText().equals("")) {
            MsgBox.alert(this, "Không được để trống vị trí kệ sách");
            return;

        } else {
            try {
                dao.update(model);
                this.filltablekesach();
                MsgBox.alert(this, "Cap nhap thanh cong !!!");
            } catch (Exception e) {
                MsgBox.alert(this, "Cap nhap that bai !!!");

                e.printStackTrace();
            }
        }
    }

    void delete() {
        String maks = txtMaks.getText();
        if (MsgBox.confirm(this, "Ban chắc muốn xóa Kệ Sách Này????")) {
            try {
                dao.delete(maks);
                this.filltablekesach();
                this.clearForm();
                MsgBox.alert(this, "Xoa thanh cong !!!");
            } catch (Exception e) {
                MsgBox.alert(this, "Xoa that bai !!!");
                e.printStackTrace();
            }
        }
    }

    void first() {

        this.row = 0;
        this.edit();
        tblKeSach.setRowSelectionInterval(0, 0);
    }

    void prev() {

        if (this.row > 0) {
            this.row--;
            tblKeSach.setRowSelectionInterval(row, row);
            this.edit();
        }
    }

    void next() {
        if (this.row < tblKeSach.getRowCount() - 1) {
            this.row++;
            this.edit();
            tblKeSach.setRowSelectionInterval(row, row);
        }
    }

    void last() {
        this.row = tblKeSach.getRowCount() - 1;
        tblKeSach.setRowSelectionInterval(row, row);
        this.edit();
    }

    void first1() {

        this.raw = 0;
        this.edit();
        tblChitietKeSach.setRowSelectionInterval(0, 0);
    }

    void prev1() {

        if (this.raw > 0) {
            this.raw--;
            tblChitietKeSach.setRowSelectionInterval(raw, raw);
            this.edit();
        }
    }

    void next1() {
        if (this.raw < tblChitietKeSach.getRowCount() - 1) {
            this.raw++;
            this.edit();
            tblChitietKeSach.setRowSelectionInterval(raw, raw);
        }
    }

    void last1() {
        this.raw = tblChitietKeSach.getRowCount() - 1;
        tblChitietKeSach.setRowSelectionInterval(raw, raw);
        this.edit();
    }

    public void filltableChitietMaKeSach() {
        DefaultTableModel dtm = (DefaultTableModel) tblChitietKeSach.getModel();
        dtm.setRowCount(0);
        row = tblKeSach.getSelectedRow();
        if (row > -1) {
            String mks = tblKeSach.getValueAt(row, 0).toString();
            try {
                List<Sach> listsah = sahdao.selctbymaks(mks);
                for (Sach ks : listsah) {
                    Object[] row = {
                        ks.getMaSach(),
                        ks.getTenSach(),
                        ks.getMaKeSach()};
                    dtm.addRow(row);
                }
            } catch (Exception e) {
                MsgBox.alert(this, "Loi truy van du lieu !");
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        pnlBieuDoDoanhSo = new javax.swing.JPanel();
        pnlDanhSachNV2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        btnFirstks = new javax.swing.JButton();
        btnPrevks = new javax.swing.JButton();
        btnNextks = new javax.swing.JButton();
        btnLastks = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblKeSach = new javax.swing.JTable();
        txtMaks = new javax.swing.JTextField();
        lblMaSV = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        txtViTri = new javax.swing.JTextField();
        pnlDanhSachNV = new javax.swing.JPanel();
        ChiTietKeSach = new javax.swing.JLabel();
        btnFirstctks = new javax.swing.JButton();
        btnPrevctks = new javax.swing.JButton();
        btnNextctks = new javax.swing.JButton();
        btnLastctks = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblChitietKeSach = new javax.swing.JTable();
        btnXoaCHITIET = new javax.swing.JButton();
        btnThemSachVaoKe = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        pnlBieuDoDoanhSo.setLayout(new java.awt.BorderLayout());

        pnlDanhSachNV2.setBackground(new java.awt.Color(255, 255, 255));
        pnlDanhSachNV2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlDanhSachNV2.setPreferredSize(new java.awt.Dimension(550, 44));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setText("Kệ Sách");

        btnFirstks.setBackground(new java.awt.Color(39, 56, 120));
        btnFirstks.setForeground(new java.awt.Color(255, 255, 255));
        btnFirstks.setText("|<");
        btnFirstks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstksActionPerformed(evt);
            }
        });

        btnPrevks.setBackground(new java.awt.Color(39, 56, 120));
        btnPrevks.setForeground(new java.awt.Color(255, 255, 255));
        btnPrevks.setText("<<");
        btnPrevks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevksActionPerformed(evt);
            }
        });

        btnNextks.setBackground(new java.awt.Color(39, 56, 120));
        btnNextks.setForeground(new java.awt.Color(255, 255, 255));
        btnNextks.setText(">>");
        btnNextks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextksActionPerformed(evt);
            }
        });

        btnLastks.setBackground(new java.awt.Color(39, 56, 120));
        btnLastks.setForeground(new java.awt.Color(255, 255, 255));
        btnLastks.setText(">|");
        btnLastks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastksActionPerformed(evt);
            }
        });

        tblKeSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Kệ Sách", "Vi tri"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKeSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKeSachMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblKeSachMousePressed(evt);
            }
        });
        jScrollPane4.setViewportView(tblKeSach);

        lblMaSV.setText("Mã Kệ Sách");

        lblMatKhau.setText("Ví Trị Kệ Sách");

        btnThem.setBackground(new java.awt.Color(39, 56, 120));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(39, 56, 120));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(39, 56, 120));
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("Mới");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(39, 56, 120));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDanhSachNV2Layout = new javax.swing.GroupLayout(pnlDanhSachNV2);
        pnlDanhSachNV2.setLayout(pnlDanhSachNV2Layout);
        pnlDanhSachNV2Layout.setHorizontalGroup(
            pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachNV2Layout.createSequentialGroup()
                .addGroup(pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDanhSachNV2Layout.createSequentialGroup()
                        .addGap(207, 207, 207)
                        .addComponent(jLabel6))
                    .addGroup(pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDanhSachNV2Layout.createSequentialGroup()
                            .addComponent(btnFirstks, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnPrevks, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(48, 48, 48)
                            .addComponent(btnNextks, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnLastks, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDanhSachNV2Layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(pnlDanhSachNV2Layout.createSequentialGroup()
                                    .addGap(27, 27, 27)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(21, 21, 21)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(36, 36, 36)
                                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDanhSachNV2Layout.createSequentialGroup()
                                    .addGroup(pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblMaSV)
                                        .addComponent(lblMatKhau))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addGroup(pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtMaks, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDanhSachNV2Layout.setVerticalGroup(
            pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachNV2Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addGroup(pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaks, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMaSV))
                .addGap(18, 18, 18)
                .addGroup(pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMatKhau)
                    .addComponent(txtViTri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem)
                    .addComponent(btnSua)
                    .addComponent(btnNew)
                    .addComponent(btnXoa))
                .addGap(13, 13, 13)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDanhSachNV2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstks)
                    .addComponent(btnPrevks)
                    .addComponent(btnNextks)
                    .addComponent(btnLastks))
                .addGap(10, 10, 10))
        );

        pnlDanhSachNV.setBackground(new java.awt.Color(255, 255, 255));
        pnlDanhSachNV.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlDanhSachNV.setPreferredSize(new java.awt.Dimension(550, 44));

        ChiTietKeSach.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ChiTietKeSach.setText("Chi  Tiết Kệ Sách");

        btnFirstctks.setBackground(new java.awt.Color(39, 56, 120));
        btnFirstctks.setForeground(new java.awt.Color(255, 255, 255));
        btnFirstctks.setText("|<");
        btnFirstctks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstctksActionPerformed(evt);
            }
        });

        btnPrevctks.setBackground(new java.awt.Color(39, 56, 120));
        btnPrevctks.setForeground(new java.awt.Color(255, 255, 255));
        btnPrevctks.setText("<<");
        btnPrevctks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevctksActionPerformed(evt);
            }
        });

        btnNextctks.setBackground(new java.awt.Color(39, 56, 120));
        btnNextctks.setForeground(new java.awt.Color(255, 255, 255));
        btnNextctks.setText(">>");
        btnNextctks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextctksActionPerformed(evt);
            }
        });

        btnLastctks.setBackground(new java.awt.Color(39, 56, 120));
        btnLastctks.setForeground(new java.awt.Color(255, 255, 255));
        btnLastctks.setText(">|");
        btnLastctks.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastctksActionPerformed(evt);
            }
        });

        tblChitietKeSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " Mã Sách", "Tên Sách", "Mã Kệ Sách"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblChitietKeSach);

        btnXoaCHITIET.setBackground(new java.awt.Color(39, 56, 120));
        btnXoaCHITIET.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaCHITIET.setText("Lấy Sách Ra Khỏi Kệ");
        btnXoaCHITIET.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaCHITIETActionPerformed(evt);
            }
        });

        btnThemSachVaoKe.setBackground(new java.awt.Color(39, 56, 120));
        btnThemSachVaoKe.setForeground(new java.awt.Color(255, 255, 255));
        btnThemSachVaoKe.setText("Sắp Sách Vào Kệ");
        btnThemSachVaoKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemSachVaoKeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDanhSachNVLayout = new javax.swing.GroupLayout(pnlDanhSachNV);
        pnlDanhSachNV.setLayout(pnlDanhSachNVLayout);
        pnlDanhSachNVLayout.setHorizontalGroup(
            pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachNVLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ChiTietKeSach)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachNVLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(pnlDanhSachNVLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnThemSachVaoKe, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDanhSachNVLayout.createSequentialGroup()
                        .addComponent(btnFirstctks, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPrevctks, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDanhSachNVLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(btnXoaCHITIET, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDanhSachNVLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btnNextctks, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnLastctks, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDanhSachNVLayout.setVerticalGroup(
            pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachNVLayout.createSequentialGroup()
                .addComponent(ChiTietKeSach)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoaCHITIET)
                    .addComponent(btnThemSachVaoKe))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirstctks)
                    .addComponent(btnLastctks)
                    .addComponent(btnPrevctks)
                    .addComponent(btnNextctks))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlBieuDoDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(pnlDanhSachNV2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlDanhSachNV, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDanhSachNV, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addComponent(pnlDanhSachNV2, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlBieuDoDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstksActionPerformed
        first();
    }//GEN-LAST:event_btnFirstksActionPerformed

    private void btnPrevksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevksActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevksActionPerformed

    private void btnNextksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextksActionPerformed
        next();
    }//GEN-LAST:event_btnNextksActionPerformed

    private void btnLastksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastksActionPerformed
        last();
    }//GEN-LAST:event_btnLastksActionPerformed

    private void tblKeSachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKeSachMouseClicked
        filltableChitietMaKeSach();
    }//GEN-LAST:event_tblKeSachMouseClicked

    private void tblKeSachMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKeSachMousePressed
        if (evt.getClickCount() == 1) {
            this.row = tblKeSach.rowAtPoint(evt.getPoint());
            this.edit();
        }
    }//GEN-LAST:event_tblKeSachMousePressed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnFirstctksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstctksActionPerformed
        first1();
    }//GEN-LAST:event_btnFirstctksActionPerformed

    private void btnPrevctksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevctksActionPerformed
        prev1();
    }//GEN-LAST:event_btnPrevctksActionPerformed

    private void btnNextctksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextctksActionPerformed
        next1();
    }//GEN-LAST:event_btnNextctksActionPerformed

    private void btnLastctksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastctksActionPerformed
        last1();
    }//GEN-LAST:event_btnLastctksActionPerformed

    private void btnXoaCHITIETActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaCHITIETActionPerformed
        try {
            int raw = tblChitietKeSach.getSelectedRow();
            sahdao.deleteKeSach(tblChitietKeSach.getValueAt(raw, 0).toString());
            MsgBox.alert(this, "Xóa Khỏi Kệ Sách Thành Công!!");
            filltableChitietMaKeSach();
        } catch (Exception e) {
            MsgBox.alert(this, "Xoa that bai !!!");
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnXoaCHITIETActionPerformed

    private void btnThemSachVaoKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemSachVaoKeActionPerformed
        String mks = txtMaks.getText();
        KeSach2JInternalFrame ks2 = new KeSach2JInternalFrame();
        maksStatic = txtMaks.getText();
        ks2.setVisible(true);
    }//GEN-LAST:event_btnThemSachVaoKeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ChiTietKeSach;
    private javax.swing.JButton btnFirstctks;
    private javax.swing.JButton btnFirstks;
    private javax.swing.JButton btnLastctks;
    private javax.swing.JButton btnLastks;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNextctks;
    private javax.swing.JButton btnNextks;
    private javax.swing.JButton btnPrevctks;
    private javax.swing.JButton btnPrevks;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThemSachVaoKe;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaCHITIET;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblMaSV;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JPanel pnlBieuDoDoanhSo;
    private javax.swing.JPanel pnlDanhSachNV;
    private javax.swing.JPanel pnlDanhSachNV2;
    private javax.swing.JTable tblChitietKeSach;
    private javax.swing.JTable tblKeSach;
    private javax.swing.JTextField txtMaks;
    private javax.swing.JTextField txtViTri;
    // End of variables declaration//GEN-END:variables
}
