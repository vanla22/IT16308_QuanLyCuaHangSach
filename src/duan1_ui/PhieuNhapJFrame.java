/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duan1_ui;

import EduSys.entity.CTPhieuNhap;
import EduSys.entity.DauSach;
import EduSys.entity.NhaCungCap;
import EduSys.entity.NhanVien;
import EduSys.entity.PhieuNhap;
import EduSys.entity.Sach;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import qlchs.dao.CTPNDAO;
import qlchs.dao.DauSachDAO;
import qlchs.dao.SachDAO;
import qlchs.dao.nhaCungCapDao;
import qlchs.dao.phieuNhapDao;
import qlchs.utils.Auth;
import qlchs.utils.MsgBox;

/**
 *
 * @author Admin
 */
public class PhieuNhapJFrame extends javax.swing.JFrame {

    private int row = -1;
    private int row1 = -1;
    CTPNDAO daoCTPN = new CTPNDAO();
    SachDAO daoS = new SachDAO();

    phieuNhapDao daoPN = new phieuNhapDao();
    List<CTPhieuNhap> listCTPN = new ArrayList<CTPhieuNhap>();
    List<PhieuNhap> listPN = new ArrayList<PhieuNhap>();
    List<NhaCungCap> listNCC = new ArrayList<NhaCungCap>();
    List<Sach> listSach = new ArrayList<>();
    List<DauSach> listDS = new ArrayList<>();
    DauSachDAO daoDS = new DauSachDAO();
    nhaCungCapDao daoNCC = new nhaCungCapDao();
    int i;

    /**
     * Creates new form PhieuNhapJFrame
     */
    public PhieuNhapJFrame() {
        initComponents();
        setLocationRelativeTo(null);
        loadCbbNCC();
        loadCbb();
        txtNgayNhap.setText(java.time.LocalDate.now() + "");
        txtMaNV.setText(Auth.user.getMaNV());
        fillTablePN();
    }

    public void loadCbbNCC() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbNCC.getModel();
        cbbNCC.removeAllItems();
        listNCC = daoNCC.selectAll();

        for (NhaCungCap x : listNCC) {
            cbbNCC.addItem(x.getTenNCC());

        }
    }

    public void loadCbb() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cbbDS.getModel();
        cbbDS.removeAllItems();
        listDS = daoDS.selectAll();

        for (DauSach x : listDS) {
            cbbDS.addItem(x.getTenDauSach());

        }
    }

    public void filltxtMaSach() {
        String key = txtTen.getText().trim();
        listSach = daoS.selectAll();
        for (Sach c : listSach) {
            if (txtTen.getText().trim().equals(c.getTenSach()) == false) {
                txtMaSach.setText("");
                txtMaSach.setEditable(true);
            }
        }
        listSach = daoS.selectByKeyword1(key);
        for (Sach x : listSach) {
            txtMaSach.setText(x.getMaSach());
            txtMaSach.setEditable(false);

        }

    }

    public void fillTablePN() {
        DefaultTableModel table = (DefaultTableModel) tblView.getModel();
        table.setRowCount(0);
        String key = txtPNTK.getText().trim();
        listPN = daoPN.selectByKeyword(key);

        for (PhieuNhap x : listPN) {

            Object row[] = {
                x.getMaPN(), x.getMaNV(), x.getMaNCC(), x.getNgayNhap(), x.getTongTien()
            };
            table.addRow(row);
        }

    }

    public CTPhieuNhap getFormCT() {
        CTPhieuNhap ct = new CTPhieuNhap();
        ct.setMaPN(Integer.parseInt(txtMapn.getText().trim()));
        ct.setMaSach(txtMaSach.getText());
        ct.setGiaNhap(Float.parseFloat(txtGiaNhap.getText().trim()));
        ct.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
        ct.setThanhTien(Float.parseFloat(txtTien.getText().trim()));

        return ct;

    }

    public void fillTableCTPN(String key) {
        DefaultTableModel table = (DefaultTableModel) tblCTPN.getModel();
        table.setRowCount(0);

        listCTPN = daoCTPN.selectByKeyword(key);
        for (CTPhieuNhap x : listCTPN) {

            Object row[] = {
                x.getMaCTPN(), x.getMaSach(), x.getMaPN(), x.getSoLuong(), x.getGiaNhap(), x.getThanhTien()
            };
            table.addRow(row);
        }

    }

    public void clearCTPN() {

        txtGiaNhap.setText("");
        txtMaSach.setText("");
        txtTien.setText("");
        txtSoLuong.setText("");
    }

    public PhieuNhap getFormPN() {
        PhieuNhap pn = new PhieuNhap();

        pn.setMaNV(txtMaNV.getText().trim());
        listNCC = daoNCC.selectByTENNCC(cbbNCC.getSelectedItem().toString());
        for (NhaCungCap x : listNCC) {
            pn.setMaNCC(x.getMaNCC());
        }

        try {
            SimpleDateFormat sp = new SimpleDateFormat();
            sp.applyPattern("yyyy-MM-dd");
            Date date = sp.parse(txtNgayNhap.getText().trim());
            pn.setNgayNhap(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        tblCTPN.getColumnName(1);
        pn.setTongTien(Float.parseFloat(txtTongTien.getText().trim()));
        return pn;

    }

    public void filist(int a) {
        tblCTPN.setRowSelectionInterval(a, a);
        int i = tblCTPN.getSelectedRow();
        txtMaSach.setText(tblCTPN.getValueAt(i, 1).toString());
        txtMapn.setText(tblCTPN.getValueAt(i, 2).toString());
        txtSoLuong.setText(tblCTPN.getValueAt(i, 3).toString());
        txtGiaNhap.setText(tblCTPN.getValueAt(i, 4).toString());
        txtTien.setText(tblCTPN.getValueAt(i, 5).toString());

    }
//    public void UpdateCTPN(){
//        CTPhieuNhap ct = new CTPhieuNhap();
//        ct.setMaPN(txtMapn.getText());
//        ct.setMaSach(txtMaSach.getText());
//        ct.setGiaNhap(Float.parseFloat(txtGiaNhap.getText()));
//        ct.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
//        ct.setThanhTien(Float.parseFloat(txtTien.getText()));
//        int a = tblCTPN.getSelectedRow();
//        ct.setMaCTPN(Integer.parseInt(tblCTPN.getValueAt(a, 0).toString()));
//        daoCTPN.update(ct);
//
//        txtPNCTTK.setText(txtMapn.getText());
//        fillTableCTPN(txtMapn.getText());
//        float c = 0;
//        for (int i = 0; i < tblCTPN.getRowCount(); i++) {
//
//            float k = Float.parseFloat(tblCTPN.getValueAt(i, 5).toString());
//            c += k;
//        }
//
//        daoPN.updateTTien(c, txtMapn.getText());
//
//        fillTablePN();
//        clearCTPN();
//        txtPNCTTK.setText("");
//    }

    public void setForm(PhieuNhap nv) {

        txtMaNV.setText(nv.getMaNV());

        txtNgayNhap.setText(nv.getNgayNhap() + "");
        txtTongTien.setText(nv.getTongTien() + "");

    }

    public void setFormCT(CTPhieuNhap nv) {

        txtMaSach.setText(nv.getMaSach());

        txtSoLuong.setText(nv.getSoLuong() + "");
        txtGiaNhap.setText(nv.getGiaNhap() + "");
        txtTien.setText(nv.getThanhTien() + "");

    }

    public void edit() {
        String mapn = (String) tblView.getValueAt(row, 0);
        PhieuNhap pn = daoPN.selectById(mapn);
        this.setForm(pn);
        tblView.setRowSelectionInterval(row, row);
    }

    public void first() {
        this.row = 0;
        this.edit();
    }

    public void prev() {
        if (this.row > 0) {
            this.row--;
            this.edit();
        }

    }

    public void next() {
        if (this.row < tblView.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }

    public void last() {
        this.row = tblView.getRowCount() - 1;
        this.edit();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNgayNhap = new javax.swing.JTextField();
        btnThem = new javax.swing.JButton();
        btnXuat = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        txtTongTien = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JLabel();
        cbbNCC = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtPNTK = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblView = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtMaSach = new javax.swing.JTextField();
        txtMapn = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtGiaNhap = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cbbDS = new javax.swing.JComboBox<>();
        txtTien = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        txtPNCTTK = new javax.swing.JTextField();
        btnSeach = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCTPN = new javax.swing.JTable();
        btnF = new javax.swing.JButton();
        btnP = new javax.swing.JButton();
        btnN = new javax.swing.JButton();
        btnL = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cập nhập"));

        jLabel3.setText("MÃ NHÂN VIÊN :");

        jLabel4.setText("MÃ NHÀ CUNG CẤP :");

        jLabel5.setText("NGÀY NHẬP :");

        jLabel6.setText("TỔNG TIỀN :");

        txtNgayNhap.setEditable(false);

        btnThem.setBackground(new java.awt.Color(39, 56, 120));
        btnThem.setForeground(new java.awt.Color(240, 240, 240));
        btnThem.setText("THÊM");
        btnThem.setBorder(null);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXuat.setBackground(new java.awt.Color(39, 56, 120));
        btnXuat.setForeground(new java.awt.Color(240, 240, 240));
        btnXuat.setText("XÓA");
        btnXuat.setBorder(null);
        btnXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatActionPerformed(evt);
            }
        });

        btnNew.setBackground(new java.awt.Color(39, 56, 120));
        btnNew.setForeground(new java.awt.Color(240, 240, 240));
        btnNew.setText("LÀM MỚI");
        btnNew.setBorder(null);
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        txtTongTien.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 0, 0));
        txtTongTien.setText("0");

        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtMaNV.setForeground(new java.awt.Color(255, 0, 0));
        txtMaNV.setText("0");

        cbbNCC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbbNCC, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbbNCC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách"));

        jLabel7.setText("MÃ PHIẾU NHẬP :");

        txtPNTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPNTKKeyReleased(evt);
            }
        });

        btnTim.setBackground(new java.awt.Color(39, 56, 120));
        btnTim.setForeground(new java.awt.Color(240, 240, 240));
        btnTim.setText("TÌM");
        btnTim.setBorder(null);
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(txtPNTK, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPNTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ PHIẾU NHẬP", "MÃ NHÂN VIÊN", "MÃ  NHÀ CUNG CẤP", "NGÀY NHẬP", "TỔNG TIỀN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblViewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblView);

        btnFirst.setBackground(new java.awt.Color(39, 56, 120));
        btnFirst.setForeground(new java.awt.Color(240, 240, 240));
        btnFirst.setText("|<<");
        btnFirst.setBorder(null);
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPev.setBackground(new java.awt.Color(39, 56, 120));
        btnPev.setForeground(new java.awt.Color(240, 240, 240));
        btnPev.setText("<<");
        btnPev.setBorder(null);
        btnPev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(39, 56, 120));
        btnNext.setForeground(new java.awt.Color(240, 240, 240));
        btnNext.setText(">>");
        btnNext.setBorder(null);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnPev, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPev, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabs.addTab("Phiếu nhập", jPanel1);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Cập nhập"));

        jLabel9.setText("MÃ SÁCH :");

        jLabel10.setText("MÃ PHIẾU NHẬP :");

        jLabel11.setText("SỐ LƯỢNG NHẬP :");

        jLabel12.setText("GIÁ NHẬP :");

        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyTyped(evt);
            }
        });

        txtGiaNhap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaNhapKeyReleased(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(39, 56, 120));
        btnAdd.setForeground(new java.awt.Color(240, 240, 240));
        btnAdd.setText("THÊM");
        btnAdd.setBorder(null);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnRemove.setBackground(new java.awt.Color(39, 56, 120));
        btnRemove.setForeground(new java.awt.Color(240, 240, 240));
        btnRemove.setText("XÓA");
        btnRemove.setBorder(null);
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
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

        jLabel1.setText("THÀNH TIỀN:");

        txtTen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenKeyReleased(evt);
            }
        });

        jLabel8.setText("TÊN SÁCH:");

        jLabel14.setText("MÃ ĐẦU SÁCH:");

        cbbDS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbbDS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbDSActionPerformed(evt);
            }
        });

        txtTien.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtTien.setForeground(new java.awt.Color(255, 0, 0));
        txtTien.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(txtTien, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(59, 59, 59)))
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addGap(35, 35, 35)
                                .addComponent(cbbDS, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel12)
                            .addComponent(jLabel1)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel8))
                                .addGap(67, 67, 67)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtTen, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                    .addComponent(txtMaSach, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                    .addComponent(txtMapn)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel14))
                        .addGap(0, 21, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtMapn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbbDS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(33, 33, 33)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jLabel12))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtGiaNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel1))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTien, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách"));

        txtPNCTTK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPNCTTKKeyReleased(evt);
            }
        });

        btnSeach.setBackground(new java.awt.Color(39, 56, 120));
        btnSeach.setForeground(new java.awt.Color(240, 240, 240));
        btnSeach.setText("TÌM");
        btnSeach.setBorder(null);
        btnSeach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeachActionPerformed(evt);
            }
        });

        jLabel13.setText("MÃ PHIẾU NHẬP :");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(34, 34, 34)
                .addComponent(txtPNCTTK, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSeach, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPNCTTK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeach, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap())
        );

        tblCTPN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ CT PHIẾU NHẬP", "MÃ SÁCH", "MÃ PHIẾU NHẬP", "SỐ LƯỢNG", "GIÁ BÁN ", "THÀNH TIỀN"
            }
        ));
        tblCTPN.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTPNMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCTPN);

        btnF.setBackground(new java.awt.Color(39, 56, 120));
        btnF.setForeground(new java.awt.Color(240, 240, 240));
        btnF.setText("|<<");
        btnF.setBorder(null);
        btnF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFActionPerformed(evt);
            }
        });

        btnP.setBackground(new java.awt.Color(39, 56, 120));
        btnP.setForeground(new java.awt.Color(240, 240, 240));
        btnP.setText("<<");
        btnP.setBorder(null);
        btnP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPActionPerformed(evt);
            }
        });

        btnN.setBackground(new java.awt.Color(39, 56, 120));
        btnN.setForeground(new java.awt.Color(240, 240, 240));
        btnN.setText(">>");
        btnN.setBorder(null);
        btnN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNActionPerformed(evt);
            }
        });

        btnL.setBackground(new java.awt.Color(39, 56, 120));
        btnL.setForeground(new java.awt.Color(240, 240, 240));
        btnL.setText(">>|");
        btnL.setBorder(null);
        btnL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(btnF, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnP, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnN, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnL, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnL, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(98, 98, 98))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
        );

        tabs.addTab("Chi Tiết Phiếu Nhập", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabs)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        PhieuNhap a = getFormPN();
        daoPN.insert(a);
        fillTablePN();

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnPevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPevActionPerformed
        // TODO add your handling code here:
        prev();

    }//GEN-LAST:event_btnPevActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if (txtMaSach.getText().trim().isEmpty() || txtMapn.getText().trim().isEmpty() || txtSoLuong.getText().trim().isEmpty() || txtGiaNhap.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Text trong !");
            return;
        }
        int sl = Integer.parseInt(txtSoLuong.getText().trim());
        try {
            if (sl <= 0) {
                MsgBox.alert(this, "So luong phai lon hon 0");
                return;
            }
        } catch (Exception e) {
            MsgBox.alert(this, "So luong phai la so");
            return;
        }
        float gn = Float.parseFloat(txtGiaNhap.getText().trim());
        try {
            if (gn <= 0) {
                MsgBox.alert(this, "Gia nhap phai lon hon 0");
                return;
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Gia nhap khong phai la so");
            return;
        }
        boolean ds = false;

        listSach = daoS.selectCheck(txtTen.getText().trim(), txtMaSach.getText().trim());

        if (listSach.isEmpty()) {
            Sach sc = daoS.selectById(txtMaSach.getText().trim());
            if (sc != null) {
                MsgBox.alert(this, "Trùng mã Sách");
                return;
            }
            Sach c = new Sach();
            listDS = daoDS.selectByTEN(cbbDS.getSelectedItem().toString());
            for (DauSach x : listDS) {
                c.setMaDauSach(x.getMaDauSach());
            }

            c.setMaSach(txtMaSach.getText().trim());
            c.setTenSach(txtTen.getText().trim());
            c.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
            c.setGiaBan(0);
            c.setGhiChu("null");
            c.setMaKeSach("Null");
            daoS.insert(c);
        }
        if (listSach != null) {
            Sach sach = daoS.selectById(txtMaSach.getText().trim());
            int cc = sach.getSoLuong() + Integer.parseInt(txtSoLuong.getText().trim());
            daoS.updateSL(cc, txtMaSach.getText().trim());
        }

        Sach test = daoS.selectById(txtMaSach.getText().trim());

        CTPhieuNhap v = getFormCT();
        daoCTPN.insert(v);

        // tính tổng tiền
        txtPNCTTK.setText(txtMapn.getText().trim());

        fillTableCTPN(txtMapn.getText().trim());
        float cong = 0;
        for (int i = 0; i < tblCTPN.getRowCount(); i++) {

            float k = Float.parseFloat(tblCTPN.getValueAt(i, 5).toString());
            cong += k;
        }

        daoPN.updateTTien(cong, txtMapn.getText().trim());
        MsgBox.alert(this, "Thêm  thành công");

        fillTablePN();
        clearCTPN();
        txtPNCTTK.setText("");

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeachActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnSeachActionPerformed

    private void btnPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPActionPerformed
        // TODO add your handling code here:
        int i = tblCTPN.getSelectedRow();
        i--;

        if (i <= -1) {
            MsgBox.alert(this, "Min");
            return;
        } else {
            filist(i);
        }
    }//GEN-LAST:event_btnPActionPerformed

    private void txtGiaNhapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaNhapKeyReleased
        // TODO add your handling code here:
        try {
            if (txtGiaNhap.getText().trim().isEmpty() == false) {
                int sl = Integer.parseInt(txtSoLuong.getText());
                float gn = Float.parseFloat(txtGiaNhap.getText());
                float a = sl * gn;
                txtTien.setText(String.valueOf(a));
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Gia nhap phai la so va >0");

        }
    }//GEN-LAST:event_txtGiaNhapKeyReleased

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        // TODO add your handling code here:
        int c = tblCTPN.getSelectedRow();
        if (c == -1) {
            JOptionPane.showMessageDialog(this, "Ban chua chon!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int xacNhan = JOptionPane.showConfirmDialog(this, "Ban muon xoa?", "xn", JOptionPane.YES_NO_OPTION);
        if (xacNhan == 1) {
            return;
        }
        String ma = tblCTPN.getValueAt(c, 0).toString();
        daoCTPN.delete(ma);
        fillTableCTPN(txtMapn.getText().trim());
        float cong = 0;
        for (int i = 0; i < tblCTPN.getRowCount(); i++) {

            float k = Float.parseFloat(tblCTPN.getValueAt(i, 5).toString());
            cong += k;
        }

        daoPN.updateTTien(cong, txtMapn.getText().trim());
        fillTableCTPN(txtMapn.getText().trim());
        fillTablePN();
        Sach sach = daoS.selectById(txtMaSach.getText().trim());
        int sl = sach.getSoLuong() - Integer.parseInt(txtSoLuong.getText().trim());

        daoS.updateSL(sl, txtMaSach.getText().trim());
        clearCTPN();

// Tim Max(Gia nhap )
//        try {
//
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String Url = "jdbc:sqlserver://localhost:1433;databaseName=QLNS",
//                    Username = "toan", Password = "23112002";
//            Connection con = DriverManager.getConnection(Url, Username, Password);
//            String sql = "select MAX(GiaNhap)  from CTPHIEUNHAP where MaSach='" + txtMaSach.getText() + "'";
//            Statement st = con.createStatement();
//            ResultSet rs = st.executeQuery(sql);
//            while (rs.next()) {
//                txtPNCTTK.setText(rs.getString(1));
//
//            }
//            con.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void tblCTPNMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTPNMouseClicked
        // TODO add your handling code here:
        int i = tblCTPN.getSelectedRow();
        txtMaSach.setText(tblCTPN.getValueAt(i, 1).toString());
        txtMapn.setText(tblCTPN.getValueAt(i, 2).toString());
        txtSoLuong.setText(tblCTPN.getValueAt(i, 3).toString());
        txtGiaNhap.setText(tblCTPN.getValueAt(i, 4).toString());

        txtTien.setText(tblCTPN.getValueAt(i, 5).toString());
        Sach sach = daoS.selectById(tblCTPN.getValueAt(i, 1).toString());
        txtTen.setText(sach.getTenSach());
    }//GEN-LAST:event_tblCTPNMouseClicked

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
        // TODO add your handling code here:
        try {

            if (txtSoLuong.getText().trim().isEmpty() == false && txtGiaNhap.getText().trim().isEmpty() == false) {
                int sl = Integer.parseInt(txtSoLuong.getText().trim());
                float gn = Float.parseFloat(txtGiaNhap.getText().trim());
                float a = sl * gn;
                txtTien.setText(String.valueOf(a));
            }
        } catch (Exception e) {
            MsgBox.alert(this, "So luong phai la so va >0");

        }
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearCTPN();
        txtPNCTTK.setText("");

    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtPNCTTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPNCTTKKeyReleased
        // TODO add your handling code here:
        fillTableCTPN(txtPNCTTK.getText().trim());
    }//GEN-LAST:event_txtPNCTTKKeyReleased

    private void txtSoLuongKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongKeyTyped

    private void txtPNTKKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPNTKKeyReleased
        // TODO add your handling code here:
        fillTablePN();

    }//GEN-LAST:event_txtPNTKKeyReleased

    private void btnXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatActionPerformed
        // TODO add your handling code here:
        int i = tblView.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Ban chua chon!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int xacNhan = JOptionPane.showConfirmDialog(this, "Ban muon xoa?", "xn", JOptionPane.YES_NO_OPTION);
        if (xacNhan == 1) {
            return;
        }

        daoPN.delete(tblView.getValueAt(i, 0).toString());
        fillTablePN();


    }//GEN-LAST:event_btnXuatActionPerformed

    private void tblViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblViewMouseClicked
        // TODO add your handling code here:
        int i = tblView.getSelectedRow();

        txtMaNV.setText(tblView.getValueAt(i, 1).toString());
        cbbNCC.setSelectedItem(tblView.getValueAt(i, 2).toString());
        txtNgayNhap.setText(tblView.getValueAt(i, 3).toString());
        txtTongTien.setText(tblView.getValueAt(i, 4).toString());
        if (evt.getClickCount() == 2) {
            tabs.setSelectedIndex(1);
            txtMapn.setText(tblView.getValueAt(i, 0).toString());
            txtMapn.setEditable(false);
            fillTableCTPN(txtMapn.getText().trim());
        }
    }//GEN-LAST:event_tblViewMouseClicked

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:

        txtMaNV.setText("");
        txtTongTien.setText("0");

    }//GEN-LAST:event_btnNewActionPerformed

    private void cbbDSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbDSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbDSActionPerformed

    private void txtTenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenKeyReleased
        // TODO add your handling code here:
        filltxtMaSach();
    }//GEN-LAST:event_txtTenKeyReleased

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFActionPerformed
        // TODO add your handling code here:
        filist(0);
    }//GEN-LAST:event_btnFActionPerformed

    private void btnNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNActionPerformed
        // TODO add your handling code here:
        int i = tblCTPN.getSelectedRow();

        if (i >= tblCTPN.getRowCount() - 1) {
            MsgBox.alert(this, "Max");
            return;
        } else {
            filist(i + 1);
        }
    }//GEN-LAST:event_btnNActionPerformed

    private void btnLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLActionPerformed
        // TODO add your handling code here:
        filist(tblCTPN.getRowCount() - 1);
    }//GEN-LAST:event_btnLActionPerformed

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
            java.util.logging.Logger.getLogger(PhieuNhapJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PhieuNhapJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PhieuNhapJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PhieuNhapJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new PhieuNhapJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnF;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnL;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnN;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnP;
    private javax.swing.JButton btnPev;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSeach;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXuat;
    private javax.swing.JComboBox<String> cbbDS;
    private javax.swing.JComboBox<String> cbbNCC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabs;
    private javax.swing.JTable tblCTPN;
    private javax.swing.JTable tblView;
    private javax.swing.JTextField txtGiaNhap;
    private javax.swing.JLabel txtMaNV;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtMapn;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtPNCTTK;
    private javax.swing.JTextField txtPNTK;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTen;
    private javax.swing.JLabel txtTien;
    private javax.swing.JLabel txtTongTien;
    // End of variables declaration//GEN-END:variables
}
