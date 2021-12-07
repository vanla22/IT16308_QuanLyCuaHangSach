/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package duan1_ui;

import EduSys.entity.NhanVien;
import static java.awt.Color.pink;
import static java.awt.Color.white;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import qlchs.dao.nhanvienDAO;
import qlchs.utils.Auth;
import qlchs.utils.MsgBox;
import qlchs.utils.XDate;

/**
 *
 * @author tachi
 */
public class QuanLyNhanVien extends javax.swing.JFrame {
    
    private nhanvienDAO dao = new nhanvienDAO();
    private int row = -1;
    private List<NhanVien> listNhanVien;

    /**
     * Creates new form QuanLyNhanVien
     */
    public QuanLyNhanVien() {
        initComponents();
        setTitle("QUẢN LÝ NHÂN VIÊN");
        setLocationRelativeTo(null);
        fillTable("1");
        
        btnKhoiPhuc.setVisible(false);
        btnNhanVienHT.setVisible(false);
        clearForm();
    }
    
    public void fillTable(String idlist) {
        DefaultTableModel model = (DefaultTableModel) tblNhaVien.getModel();
        model.setRowCount(0);
        try {
            listNhanVien = dao.selectIdList(idlist);
            for (NhanVien nhanVien : listNhanVien) {
                Object[] row = {
                    nhanVien.getMaNV(), nhanVien.getHoTen(), nhanVien.getMatKhau(), nhanVien.getNgaySinh(),
                    nhanVien.getSDT(), nhanVien.isVaiTro() ? "Chủ cửa hàng" : "Nhân Viên"
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Lỗi truy vấn");
            e.printStackTrace();
        }
    }
    
    public void setForm(NhanVien nv) {
        txtMaNv.setText(nv.getMaNV());
        txtHoTen.setText(nv.getHoTen());
        txtMatKhau.setText(nv.getMatKhau());
        txtNgaySinh.setText(XDate.toString(nv.getNgaySinh()));
        txtSDT.setText(nv.getSDT());
        if (nv.isVaiTro() == true) {
            rdoChuCuaHang.setSelected(true);
        } else {
            rdoNhanVien.setSelected(true);
        }
        
    }
    
    public NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNv.getText());
        nv.setHoTen(txtHoTen.getText());
        nv.setMatKhau(txtMatKhau.getText());
        nv.setNgaySinh(XDate.toDate(txtNgaySinh.getText()));
        nv.setSDT(txtSDT.getText());
        nv.setVaiTro(rdoChuCuaHang.isSelected());
        return nv;
    }
    
    public void clearForm() {
        txtMaNv.setText("");
        txtHoTen.setText("");
        txtMatKhau.setText("");
        txtNgaySinh.setText("");
        txtSDT.setText("");
        rdoChuCuaHang.setSelected(true);
        btnThem.setEnabled(true);
        txtMaNv.setBackground(white);
        txtHoTen.setBackground(white);
        txtMatKhau.setBackground(white);
        txtNgaySinh.setBackground(white);
        txtSDT.setBackground(white);
    }
    
    public void edit() {
        String manv = (String) tblNhaVien.getValueAt(row, 0);
        NhanVien nv = dao.selectById(manv);
        this.setForm(nv);
        txtMaNv.setEditable(false);
        tblNhaVien.setRowSelectionInterval(row, row);
        if (row == 0) {
            btnFirst.setEnabled(false);
            btnPrev.setEnabled(false);
            btnLast.setEnabled(true);
            btnNext.setEnabled(true);
        } else if (row == tblNhaVien.getRowCount() - 1) {
            btnLast.setEnabled(false);
            btnNext.setEnabled(false);
            btnFirst.setEnabled(true);
            btnPrev.setEnabled(true);
        }
    }
    
    public void insert() {
        NhanVien nv = getForm();
        NhanVien nv2 = dao.selectById(nv.getMaNV().toString());
        if (nv2 != null) {
            if (nv2.isTinhTrang() == false) {
                MsgBox.alert(this, "Mã nhân viên đã có trong danh sách đã xóa");
                return;
            }
        }
        
        if (nv2 != null) {
            MsgBox.alert(this, "Trùng mã nhân viên");
            return;
        }
        
        try {
            dao.insert(nv);
            this.fillTable("1");
            this.clearForm();
            MsgBox.alert(this, "Thêm mới thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Thêm mới thất bại");
            e.printStackTrace();
        }
    }
    
    public void update() {
        NhanVien nv = getForm();
        NhanVien nv2 = dao.selectById(nv.getMaNV().toString());
        try {
            dao.update(nv);
            this.fillTable("1");
            this.clearForm();
            MsgBox.alert(this, "Cập nhật thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Cập nhật thất bại");
            e.printStackTrace();
        }
    }
    
    public void deleteIDlist() {
        String manv = txtMaNv.getText();
        if (manv.equals(Auth.user.getMaNV())) {
            MsgBox.alert(this, "Bạn không được xóa chính mình");
            return;
        } else if (MsgBox.confirm(this, "Bạn có thực sự muốn xóa"));
        try {
            dao.xoaTamThoi("0", manv);
            this.fillTable("1");
            this.clearForm();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (this.row < tblNhaVien.getRowCount() - 1) {
            this.row++;
            this.edit();
        }
    }
    
    public void last() {
        this.row = tblNhaVien.getRowCount() - 1;
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        pnlThongTinNhanVien = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblMaNV = new javax.swing.JLabel();
        lblHoTen = new javax.swing.JLabel();
        lblMatKhau = new javax.swing.JLabel();
        lblNgaySinh = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        lblVaiTro = new javax.swing.JLabel();
        txtMaNv = new javax.swing.JTextField();
        txtHoTen = new javax.swing.JTextField();
        txtNgaySinh = new javax.swing.JTextField();
        txtSDT = new javax.swing.JTextField();
        rdoChuCuaHang = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnXoaForm = new javax.swing.JButton();
        txtMatKhau = new javax.swing.JPasswordField();
        pnlDanhSachNV = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblNhaVien = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnKhoiPhuc = new javax.swing.JToggleButton();
        btnNhanVienHT = new javax.swing.JToggleButton();
        btnNhanVienDaXoa = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(972, 520));

        pnlThongTinNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        pnlThongTinNhanVien.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("THÔNG TIN NHÂN VIÊN");

        lblMaNV.setText("Mã Nhân Viên");

        lblHoTen.setText("Họ Tên");

        lblMatKhau.setText("Mật Khẩu");

        lblNgaySinh.setText("Ngày Sinh");

        lblSDT.setText("SDT");

        lblVaiTro.setText("Vai Trò");

        buttonGroup1.add(rdoChuCuaHang);
        rdoChuCuaHang.setText("Chủ Cửa Hàng");

        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setText("Nhân Viên");

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

        btnXoa.setBackground(new java.awt.Color(39, 56, 120));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnXoaForm.setBackground(new java.awt.Color(39, 56, 120));
        btnXoaForm.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaForm.setText("Làm Mới");
        btnXoaForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaFormActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongTinNhanVienLayout = new javax.swing.GroupLayout(pnlThongTinNhanVien);
        pnlThongTinNhanVien.setLayout(pnlThongTinNhanVienLayout);
        pnlThongTinNhanVienLayout.setHorizontalGroup(
            pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinNhanVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77))
            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblMaNV)
                        .addComponent(lblHoTen, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblMatKhau, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblNgaySinh, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblSDT, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lblVaiTro, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaForm, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 31, Short.MAX_VALUE))
                    .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addComponent(rdoChuCuaHang, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdoNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongTinNhanVienLayout.createSequentialGroup()
                        .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaNv, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHoTen, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNgaySinh, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSDT, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(31, 31, 31))))
        );
        pnlThongTinNhanVienLayout.setVerticalGroup(
            pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongTinNhanVienLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel1)
                .addGap(42, 42, 42)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMaNV)
                    .addComponent(txtMaNv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHoTen)
                    .addComponent(txtHoTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblMatKhau)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNgaySinh)
                    .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSDT)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVaiTro)
                    .addComponent(rdoChuCuaHang)
                    .addComponent(rdoNhanVien))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addGroup(pnlThongTinNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa)
                    .addComponent(btnSua)
                    .addComponent(btnThem)
                    .addComponent(btnXoaForm))
                .addGap(69, 69, 69))
        );

        pnlDanhSachNV.setBackground(new java.awt.Color(255, 255, 255));
        pnlDanhSachNV.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pnlDanhSachNV.setPreferredSize(new java.awt.Dimension(550, 44));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("DANH SÁCH NHÂN VIÊN");

        tblNhaVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Họ Tên", "Mật Khẩu", "Ngày Sinh", "SDT", "Vai Trò"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNhaVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNhaVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblNhaVien);

        btnFirst.setBackground(new java.awt.Color(39, 56, 120));
        btnFirst.setForeground(new java.awt.Color(255, 255, 255));
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(39, 56, 120));
        btnPrev.setForeground(new java.awt.Color(255, 255, 255));
        btnPrev.setText("<<");
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(39, 56, 120));
        btnNext.setForeground(new java.awt.Color(255, 255, 255));
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(39, 56, 120));
        btnLast.setForeground(new java.awt.Color(255, 255, 255));
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        btnKhoiPhuc.setText("Khôi phục nhân viên");
        btnKhoiPhuc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhoiPhucActionPerformed(evt);
            }
        });

        btnNhanVienHT.setText("Nhân viên hiện tại");
        btnNhanVienHT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienHTActionPerformed(evt);
            }
        });

        btnNhanVienDaXoa.setText("Nhân viên đã xóa");
        btnNhanVienDaXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhanVienDaXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDanhSachNVLayout = new javax.swing.GroupLayout(pnlDanhSachNV);
        pnlDanhSachNV.setLayout(pnlDanhSachNVLayout);
        pnlDanhSachNVLayout.setHorizontalGroup(
            pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachNVLayout.createSequentialGroup()
                .addGroup(pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachNVLayout.createSequentialGroup()
                        .addContainerGap(13, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDanhSachNVLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachNVLayout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDanhSachNVLayout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(btnKhoiPhuc)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNhanVienHT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(btnNhanVienDaXoa)
                .addContainerGap())
        );
        pnlDanhSachNVLayout.setVerticalGroup(
            pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDanhSachNVLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addGap(50, 50, 50)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(btnPrev)
                    .addComponent(btnNext)
                    .addComponent(btnLast))
                .addGap(18, 18, 18)
                .addGroup(pnlDanhSachNVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnKhoiPhuc)
                    .addComponent(btnNhanVienHT)
                    .addComponent(btnNhanVienDaXoa))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(pnlThongTinNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDanhSachNV, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlThongTinNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlDanhSachNV, javax.swing.GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 857, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (txtMaNv.getText().trim().length() == 0) {
            MsgBox.alert(this, "Không để trống mã nhân viên");
            txtMaNv.requestFocus();
            return;
        }
        if (txtHoTen.getText().trim().length() == 0) {
            MsgBox.alert(this, "Không để trống họ tên nhân viên");
            txtHoTen.requestFocus();
            return;
        } else if (!txtHoTen.getText().matches("^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{3,25}$")) {
            MsgBox.alert(this, "Họ tên phải là tên tiếng việt hoặc không dấu(từ 3-25 kí tự)");
            txtHoTen.requestFocus();
            return;            
        }
        
        if (txtMatKhau.getText().trim().length() == 0) {
            MsgBox.alert(this, "Không để trống mật khẩu");
            txtMatKhau.requestFocus();
            return;
        }
        if (txtNgaySinh.getText().trim().length() == 0) {
            MsgBox.alert(this, "Không để trống ngày sinh");
            txtNgaySinh.requestFocus();
            return;
        } else {
            try {
                XDate.toDate(txtNgaySinh.getText());
            } catch (Exception e) {
                MsgBox.alert(this, "Định dạng là dd-MM-yyyy");
                txtNgaySinh.requestFocus();
                return;
            }
        }
        if (txtSDT.getText().trim().length() == 0) {
            MsgBox.alert(this, "Không để trống SDT ");
            txtSDT.requestFocus();
            return;
        } else if (!txtSDT.getText().matches("0[0-9]{9}")) {
            MsgBox.alert(this, "Số điện thoại 10 số");
            txtSDT.requestFocus();
            return;
        }
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        
        if (txtHoTen.getText().trim().length() == 0) {
            MsgBox.alert(this, "Không để trống họ tên nhân viên");
            txtHoTen.requestFocus();
            return;
        } else if (!txtHoTen.getText().matches("^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]{3,25}$")) {
            MsgBox.alert(this, "Họ tên phải là tên tiếng việt hoặc không dấu(từ 3-25 kí tự)");
            txtHoTen.requestFocus();
            return;            
        }
        
        if (txtMatKhau.getText().trim().length() == 0) {
            MsgBox.alert(this, "Không để trống mật khẩu");
            txtMatKhau.requestFocus();
            return;
        }
        if (txtNgaySinh.getText().trim().length() == 0) {
            MsgBox.alert(this, "Không để trống ngày sinh");
            txtNgaySinh.requestFocus();
            return;
        } else {
            try {
                XDate.toDate(txtNgaySinh.getText());
            } catch (Exception e) {
                MsgBox.alert(this, "Định dạng là dd-MM-yyyy");
                txtNgaySinh.requestFocus();
                return;
            }
        }
        if (txtSDT.getText().trim().length() == 0) {
            MsgBox.alert(this, "Không để trống SDT ");
            txtSDT.requestFocus();
            return;
        } else if (!txtSDT.getText().matches("0[0-9]{9}")) {
            MsgBox.alert(this, "Số điện thoại 10 số");
            txtSDT.requestFocus();
            return;
        }
        update();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        if (txtMaNv.getText().length() == 0) {
            MsgBox.alert(this, "Không để trống mã nhân viên");
            txtMaNv.requestFocus();
            return;
        }
        deleteIDlist();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnXoaFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaFormActionPerformed
        clearForm();
        txtMaNv.setEditable(true);
    }//GEN-LAST:event_btnXoaFormActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        first();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnKhoiPhucActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhoiPhucActionPerformed
        int dong = tblNhaVien.getSelectedRow();
        if (dong < 0) {
            MsgBox.alert(this, "Chọn nhân viên cần khôi phục");
            return;
        }
        NhanVien nv = listNhanVien.get(dong);
        dao.xoaTamThoi("1", nv.getMaNV());
        fillTable("1");
        btnKhoiPhuc.setVisible(false);
        btnNhanVienHT.setVisible(false);
    }//GEN-LAST:event_btnKhoiPhucActionPerformed

    private void btnNhanVienHTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienHTActionPerformed
        fillTable("1");
        btnKhoiPhuc.setVisible(false);
        btnNhanVienHT.setVisible(false);

    }//GEN-LAST:event_btnNhanVienHTActionPerformed

    private void btnNhanVienDaXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhanVienDaXoaActionPerformed
        fillTable("0");
        btnNhanVienHT.setVisible(true);
        btnKhoiPhuc.setVisible(true);
    }//GEN-LAST:event_btnNhanVienDaXoaActionPerformed

    private void tblNhaVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNhaVienMouseClicked
        row = tblNhaVien.getSelectedRow();
        NhanVien nv = listNhanVien.get(row);
        if (evt.getClickCount() == 2) {
            setForm(nv);
            txtMaNv.setEditable(false);
            btnThem.setEnabled(false);
        }
    }//GEN-LAST:event_tblNhaVienMouseClicked

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
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(QuanLyNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new QuanLyNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFirst;
    private javax.swing.JToggleButton btnKhoiPhuc;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JToggleButton btnNhanVienDaXoa;
    private javax.swing.JToggleButton btnNhanVienHT;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXoaForm;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblHoTen;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblNgaySinh;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JPanel pnlDanhSachNV;
    private javax.swing.JPanel pnlThongTinNhanVien;
    private javax.swing.JRadioButton rdoChuCuaHang;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JTable tblNhaVien;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNv;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
