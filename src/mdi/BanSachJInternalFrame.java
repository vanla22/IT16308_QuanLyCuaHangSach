/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdi;

import EduSys.entity.HoaDon;
import EduSys.entity.HoaDonCT;
import EduSys.entity.KhachHang;
import EduSys.entity.PhieuNhap;
import EduSys.entity.Sach;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import qlchs.dao.HoaDonCTDAO;
import qlchs.dao.HoaDonDAO;
import qlchs.dao.SachDAO;
import qlchs.dao.khachhangDAO;
import qlchs.dao.nhanvienDAO;
import qlchs.utils.Auth;
import qlchs.utils.MsgBox;
import qlchs.utils.XDate;
/**
 *
 * @author Admin
 */
public class BanSachJInternalFrame extends javax.swing.JInternalFrame {

    int index = 0;
    nhanvienDAO nvdao = new nhanvienDAO();
    HoaDonDAO DAOHD = new HoaDonDAO();
    HoaDonCTDAO DAOHDCT = new HoaDonCTDAO();
    SachDAO DAOSACH = new SachDAO();
    khachhangDAO DAOKH = new khachhangDAO();

    List<HoaDon> listHD;
    List<Sach> listS;
    List<HoaDonCT> listHDCT;
    List<KhachHang> listKH;
    /**
     * Creates new form BanSachJInternalFrame
     */
    public BanSachJInternalFrame() {
        initComponents();
         txtNgayNhap.setText(java.time.LocalDate.now() + "");

        fillHD();

        fillSach();
        txtMaNV.setText(Auth.user.getMaNV());
    }
    
    
    public void fillHD() {
        DefaultTableModel table = (DefaultTableModel) tblHD.getModel();
        table.setRowCount(0);
        String key = txtTimKiemHD.getText();
        listHD = DAOHD.selectByKeyword(key);

        for (HoaDon x : listHD) {

            Object row[] = {
                x.getMaHD(), x.getMaNV(), x.getMaKH(), x.getNgayXuat(), x.getTongTien()
            };
            table.addRow(row);
        }

    }

    public void fillSach() {
        DefaultTableModel table = (DefaultTableModel) tblTK.getModel();
        table.setRowCount(0);
        String key = txtTimKiemSach.getText();
        listS = DAOSACH.selectByKeyword(key);

        for (Sach x : listS) {

            Object row[] = {
                x.getMaSach(), x.getTenSach(), x.getGiaBan(), x.getSoLuong()
            };
            table.addRow(row);
        }
    }

    public HoaDon getFormHD() {
        HoaDon hd = new HoaDon();
//        hd.setMaHD(txtMaHD.getText());
        hd.setMaNV(txtMaNV.getText());

        listKH = DAOKH.selectAll();
        boolean a = false;
        for (KhachHang c : listKH) {
            if (txtSDT.getText().trim().equals(c.getSDT())) {
                a = true;
                hd.setMaKH(c.getMaKH());
            }
        }
        if (a == false) {
            KhachHang kh = new KhachHang();
            kh.setTenKH(txtTenKH.getText().trim());
            kh.setSDT(txtSDT.getText().trim());

            DAOKH.insert(kh);
            listKH = DAOKH.selectBySDT(txtSDT.getText().trim());
            for (KhachHang x : listKH) {
                hd.setMaKH(x.getMaKH());
            }
        }

        try {
            SimpleDateFormat sp = new SimpleDateFormat();
            sp.applyPattern("yyyy-MM-dd");
            Date date = sp.parse(txtNgayNhap.getText());
            hd.setNgayXuat(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hd.setTongTien(Float.parseFloat(txtTongTien.getText()));

        return hd;
    }

    public void clearHD() {
//        txtMaHD.setText("");
        txtSDT.setText("");
        txtTenKH.setText("");
    }

    public HoaDonCT getFormCTHD() {
        HoaDonCT a = new HoaDonCT();
        a.setMaSach(txtMaSach.getText());
        a.setMaHD(Integer.parseInt(txtMaHoaDon.getText()));
        a.setTenSach(txtTenSach.getText());
        a.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        a.setGiaBan(Float.parseFloat(txtGia.getText()));
        a.setThanhTien(Float.parseFloat(txtTien.getText()));

        return a;
    }

    public void fillHDCT(String a) {
        DefaultTableModel table = (DefaultTableModel) tblCTHD.getModel();
        table.setRowCount(0);

        listHDCT = DAOHDCT.selectByKeyword(a);

        for (HoaDonCT x : listHDCT) {

            Object row[] = {
                x.getMaHDCT(), x.getMaHD(), x.getMaSach(), x.getTenSach(), x.getSoLuong(), x.getGiaBan(), x.getThanhTien()
            };
            table.addRow(row);
        }
    }

    public void clearHDCT() {
        txtMaSach.setText("");
        txtTenSach.setText("");
        txtSoLuong.setText("");
        txtGia.setText("");
        txtTien.setText("0");
    }
//    public void updateHDCT(){
//        HoaDonCT a = new HoaDonCT();
//        a.setMaSach(txtMaSach.getText());
//        a.setMaHD(txtMaHoaDon.getText());
//        a.setTenSach(txtTenSach.getText());
//        a.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
//        a.setGiaBan(Float.parseFloat(txtGia.getText()));
//        a.setThanhTien(Float.parseFloat(txtTien.getText()));
//        int t = tblCTHD.getSelectedRow();
//        a.setMaHDCT(Integer.parseInt(tblCTHD.getValueAt(t, 0).toString()));
//        DAOHDCT.update(a);
//        fillHDCT(txtMaHoaDon.getText());
//
//        float c = 0;
//        for (int i = 0; i < tblCTHD.getRowCount(); i++) {
//
//            float k = Float.parseFloat(tblCTHD.getValueAt(i, 6).toString());
//            c += k;
//        }
//        DAOHD.updateTTien(c, txtMaHoaDon.getText());
//        fillHD();
//        clearHDCT();
//    }

    public void xuatHDCT() {
//        XSSFWorkbook xs = new XSSFWorkbook();
//        XSSFSheet sheet = xs.createSheet("HÓA ĐƠN CHI TIẾT");
//        XSSFRow row = null;
//        Cell cell = null;
//        row = sheet.createRow(2);
//        cell = row.createCell(0, CellType.STRING);
//        cell.setCellValue("STT");
//        cell = row.createCell(1, CellType.STRING);
//        cell.setCellValue("Mã Chi Tiết Hóa Đơn");
//        cell = row.createCell(2, CellType.STRING);
//        cell.setCellValue("Mã Hóa Đơn");
//        cell = row.createCell(3, CellType.STRING);
//        cell.setCellValue("Mã Sách");
//        cell = row.createCell(4, CellType.STRING);
//        cell.setCellValue("Tên Sách");
//        cell = row.createCell(5, CellType.STRING);
//        cell.setCellValue("Số Lượng");
//        cell = row.createCell(6, CellType.STRING);
//        cell.setCellValue("Giá Bán");
//        cell = row.createCell(7, CellType.STRING);
//        cell.setCellValue("Thành Tiền");
//        cell = row.createCell(8, CellType.STRING);
//
//        for (int i = 0; i < tblCTHD.getRowCount(); i++) {
//            row = sheet.createRow(3 + i);
//            cell = row.createCell(0, CellType.STRING);
//            cell.setCellValue(i + 1);
//            cell = row.createCell(1, CellType.STRING);
//            String maCt = tblCTHD.getValueAt(i, 0).toString();
//            cell.setCellValue(maCt);
//            cell = row.createCell(2, CellType.STRING);
//            String maHd = tblCTHD.getValueAt(i, 1).toString();
//            cell.setCellValue(maHd);
//            cell = row.createCell(3, CellType.STRING);
//            String maSach = tblCTHD.getValueAt(i, 2).toString();
//            cell.setCellValue(maSach);
//            cell = row.createCell(4, CellType.STRING);
//
//            String soLuong = tblCTHD.getValueAt(i, 3).toString();
//            cell.setCellValue(soLuong);
//            cell = row.createCell(5, CellType.STRING);
//            String giaBan = tblCTHD.getValueAt(i, 4).toString();
//            cell.setCellValue(giaBan);
//            cell = row.createCell(6, CellType.STRING);
//            String thanhTien = tblCTHD.getValueAt(i, 5).toString();
//            cell.setCellValue(thanhTien);
//            cell = row.createCell(7, CellType.STRING);
//            String tenSach = tblCTHD.getValueAt(i, 6).toString();
//            cell.setCellValue(tenSach);
//            cell = row.createCell(8, CellType.STRING);
//        }
//        try {
////            FormChinhJDialog formchinh = new FormChinhJDialog();
////            String time = formchinh.getTime();
////            NewJFrame fam = new NewJFrame();
////            String time = fam.getTime();
//
//            String maHd = tblCTHD.getValueAt(0, 1).toString();
//
//            File file = new File(maHd + ".xlsx");
//            FileOutputStream fos = new FileOutputStream(file);
//            xs.write(fos);
//            fos.close();
//            MsgBox.alert(this, "Xuất file thành công!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
  String path = "";
        JFileChooser j = new JFileChooser();
        j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int x = j.showSaveDialog(this);
        if(x== JFileChooser.APPROVE_OPTION){
            path = j.getSelectedFile().getPath();
        }
        Document doc = new Document();
        
        try {
            PdfWriter.getInstance( doc, new FileOutputStream(path+" "+"HD"+txtMaHoaDon.getText()+".pdf"));
            doc.open();
             doc.add(new com.itextpdf.text.Paragraph("Hóa"+"đơn:"+txtMaHoaDon.getText()+"\n Ngày : " + java.time.LocalDate.now()+"\n" +"\n"));

           PdfPTable tbl = new PdfPTable(6);
           
           tbl.addCell("MaHD");
           tbl.addCell("MaSach");
           tbl.addCell("TenSach");
           tbl.addCell("SoLuong");
           tbl.addCell("DonGia");
           tbl.addCell("Thanh tien");
           for(int  i =0 ; i< tblCTHD.getRowCount();i++){
               
               String MaS = tblCTHD.getValueAt(i,1).toString();
               String TenS = tblCTHD.getValueAt(i,2).toString();
               String SL = tblCTHD.getValueAt(i,3).toString();
               String DG = tblCTHD.getValueAt(i,4).toString();
               String TT = tblCTHD.getValueAt(i,5).toString();
               String TT1 = tblCTHD.getValueAt(i,6).toString();
               
               tbl.addCell(MaS);
               tbl.addCell(TenS);
               tbl.addCell(SL);
               tbl.addCell(DG);
               tbl.addCell(TT);
               tbl.addCell(TT1);
           }
           doc.add(tbl);
            System.out.println("Thành công");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BanSach.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(BanSach.class.getName()).log(Level.SEVERE, null, ex);
        }
             
           
        
       
         doc.close();
        
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
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtNgayNhap = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTimKiemHD = new javax.swing.JTextField();
        btnTimKiemHoaDon = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHD = new javax.swing.JTable();
        btnFirst = new javax.swing.JButton();
        btnPev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtTongTien = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        txtTenKH = new javax.swing.JTextField();
        txtMaNV = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTien = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        txtMaSach = new javax.swing.JTextField();
        txtMaHoaDon = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        btnF = new javax.swing.JButton();
        btnP = new javax.swing.JButton();
        btnN = new javax.swing.JButton();
        btnL = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnXuat = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtTimKiemSach = new javax.swing.JTextField();
        btnTimKiemSach = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTK = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa đơn"));

        jLabel3.setText("MÃ NHÂN VIÊN :");

        jLabel4.setText("TÊN KHÁCH HÀNG :");

        jLabel5.setText("NGÀY NHẬP :");

        jLabel6.setText("TỔNG TIỀN :");

        txtNgayNhap.setEditable(false);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách hóa đơn"));

        jLabel7.setText("MÃ HÓA ĐƠN :");

        txtTimKiemHD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemHDKeyReleased(evt);
            }
        });

        btnTimKiemHoaDon.setBackground(new java.awt.Color(39, 56, 120));
        btnTimKiemHoaDon.setForeground(new java.awt.Color(240, 240, 240));
        btnTimKiemHoaDon.setText("TÌM");
        btnTimKiemHoaDon.setBorder(null);
        btnTimKiemHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemHoaDonActionPerformed(evt);
            }
        });

        tblHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ HÓA ĐƠN", "MÃ NHÂN VIÊN", "MÃ  KHÁCH HÀNG", "NGÀY XUẤT", "TỔNG TIỀN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHD);

        btnFirst.setBackground(new java.awt.Color(39, 56, 120));
        btnFirst.setForeground(new java.awt.Color(240, 240, 240));
        btnFirst.setText("|<");
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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnTimKiemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPev, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(44, 44, 44)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(btnTimKiemHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPev, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        btnThem.setBackground(new java.awt.Color(39, 56, 120));
        btnThem.setForeground(new java.awt.Color(240, 240, 240));
        btnThem.setText("Add");
        btnThem.setBorder(null);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
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

        jLabel8.setText("SDT :");

        txtTongTien.setBackground(new java.awt.Color(255, 255, 255));
        txtTongTien.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtTongTien.setForeground(new java.awt.Color(255, 0, 51));
        txtTongTien.setText("0");

        txtSDT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSDTKeyReleased(evt);
            }
        });

        txtTenKH.setEditable(false);

        txtMaNV.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNV.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtMaNV.setForeground(new java.awt.Color(255, 0, 51));
        txtMaNV.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(28, 28, 28)
                        .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTenKH))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(89, 89, 89))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNgayNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Hóa đơn chi tiết"));

        jLabel9.setText("MÃ SÁCH :");

        jLabel10.setText("MÃ HÓA ĐƠN :");

        jLabel11.setText("SỐ LƯỢNG :");

        jLabel12.setText("GIÁ BÁN :");

        txtSoLuong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSoLuongActionPerformed(evt);
            }
        });
        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });

        jLabel1.setText("THÀNH TIỀN:");

        txtTien.setEditable(false);

        jLabel14.setText("TÊN SÁCH:");

        txtTenSach.setEditable(false);

        txtMaHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHoaDonActionPerformed(evt);
            }
        });
        txtMaHoaDon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMaHoaDonKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(txtTien, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaSach, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(29, 29, 29))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(txtTenSach)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtGia))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(123, 123, 123))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh sách hóa đơn chi tiết"));

        tblCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ CT HÓA ĐƠN", "MÃ HÓA ĐƠN", "MÃ SÁCH", "TÊN SÁCH", "SỐ LƯỢNG", "ĐƠN GIÁ", "THÀNH TIỀN"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTHD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCTHDMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCTHD);

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
            .addComponent(jScrollPane2)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnF, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(btnP, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnN, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnL, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnF, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnN, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnL, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(132, 132, 132))
        );

        btnAdd.setBackground(new java.awt.Color(39, 56, 120));
        btnAdd.setForeground(new java.awt.Color(240, 240, 240));
        btnAdd.setText("THÊM");
        btnAdd.setBorder(null);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnXuat.setBackground(new java.awt.Color(39, 56, 120));
        btnXuat.setForeground(new java.awt.Color(240, 240, 240));
        btnXuat.setText("In HD");
        btnXuat.setBorder(null);
        btnXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnXuatMouseClicked(evt);
            }
        });
        btnXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatActionPerformed(evt);
            }
        });

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm Kiếm Sách"));

        jLabel13.setText("TÊN SÁCH:");

        txtTimKiemSach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKiemSachKeyReleased(evt);
            }
        });

        btnTimKiemSach.setBackground(new java.awt.Color(39, 56, 120));
        btnTimKiemSach.setForeground(new java.awt.Color(240, 240, 240));
        btnTimKiemSach.setText("TÌM");
        btnTimKiemSach.setBorder(null);
        btnTimKiemSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemSachActionPerformed(evt);
            }
        });

        tblTK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MaSach", "TenSach", "GiaBan", "SoLuong"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTKMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblTK);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTimKiemSach, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnTimKiemSach, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(116, Short.MAX_VALUE))
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(btnTimKiemSach, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnDelete.setBackground(new java.awt.Color(39, 56, 120));
        btnDelete.setForeground(new java.awt.Color(240, 240, 240));
        btnDelete.setText("XÓA");
        btnDelete.setBorder(null);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnLamMoi.setBackground(new java.awt.Color(39, 56, 120));
        btnLamMoi.setForeground(new java.awt.Color(240, 240, 240));
        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.setBorder(null);
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseClicked(evt);
            }
        });
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(29, 29, 29))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(107, 107, 107)
                .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(111, 111, 111))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 7, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTimKiemHDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemHDKeyReleased
        // TODO add your handling code here:
        fillHD();
    }//GEN-LAST:event_txtTimKiemHDKeyReleased

    private void btnTimKiemHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemHoaDonActionPerformed

    private void tblHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDMouseClicked
        // TODO add your handling code here:
        int i = tblHD.getSelectedRow();
        //        txtMaHD.setText(tblHD.getValueAt(i, 0).toString());
        txtMaHoaDon.setText(tblHD.getValueAt(i, 0).toString());
        txtMaHoaDon.setEditable(false);

        txtMaNV.setText(tblHD.getValueAt(i, 1).toString());

        txtNgayNhap.setText(tblHD.getValueAt(i, 3).toString());
        txtTongTien.setText(tblHD.getValueAt(i, 4).toString());
        fillHDCT(tblHD.getValueAt(i, 0).toString());
    }//GEN-LAST:event_tblHDMouseClicked

    private void btnPevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPevActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btnPevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        if ( txtSDT.getText().trim().isEmpty() || txtMaNV.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Text Trong");
            return;
        }
        //        HoaDon hd = DAOHD.selectById1(txtMaHD.getText().trim());
        //        if (hd != null) {
            //            MsgBox.alert(this, "Trùng MAHD");
            //            return;
            //        }
        HoaDon a = getFormHD();
        DAOHD.insert(a);
        fillHD();
        MsgBox.alert(this, "Them Thanh Cong");
        List<Integer> txt = DAOHD.selectMa();
        for(Integer x : txt){
            txtMaHoaDon.setText(x.toString());
        }

        clearHD();

    }//GEN-LAST:event_btnThemActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        clearHD();
        txtTongTien.setText("0");
    }//GEN-LAST:event_btnNewActionPerformed

    private void txtSDTKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSDTKeyReleased
        // TODO add your handling code here:
        String key = txtSDT.getText().trim();
        listKH = DAOKH.selectAll();
        for (KhachHang c : listKH) {
            if (txtSDT.getText().trim().equals(c.getSDT()) == false) {
                txtTenKH.setText("");
                txtTenKH.setEditable(true);
            }
        }
        listKH = DAOKH.selectBySDT(key);

        for (KhachHang x : listKH) {
            txtTenKH.setText(x.getTenKH());
            txtTenKH.setEditable(false);
        }
    }//GEN-LAST:event_txtSDTKeyReleased

    private void txtSoLuongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSoLuongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSoLuongActionPerformed

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
        // TODO add your handling code here:
        try {
            if(txtGia.getText().trim().isEmpty()){
                MsgBox.alert(this, "Chọn sản phẩm ");
                return;
            }
            if (txtSoLuong.getText().isEmpty()) {
                txtTien.setText("0");
            }
            if (txtSoLuong.getText().isEmpty() == false) {
                int sl = Integer.parseInt(txtSoLuong.getText());
                float gn = Float.parseFloat(txtGia.getText());
                float a = sl * gn;
                txtTien.setText(String.valueOf(a));
            }
        } catch (Exception e) {
            MsgBox.alert(this, "Gia nhap phai la so va >0");
            return;
        }
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void txtMaHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHoaDonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHoaDonActionPerformed

    private void txtMaHoaDonKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaHoaDonKeyReleased
        // TODO add your handling code here:
        fillHDCT(txtMaHoaDon.getText().trim());
    }//GEN-LAST:event_txtMaHoaDonKeyReleased

    private void tblCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTHDMouseClicked
        // TODO add your handling code here:
        int i = tblCTHD.getSelectedRow();
        txtMaHoaDon.setText(tblCTHD.getValueAt(i, 1).toString());
        txtMaSach.setText(tblCTHD.getValueAt(i, 2).toString());
        txtTenSach.setText(tblCTHD.getValueAt(i, 3).toString());
        txtSoLuong.setText(tblCTHD.getValueAt(i, 4).toString());
        txtGia.setText(tblCTHD.getValueAt(i, 5).toString());
        txtTien.setText(tblCTHD.getValueAt(i, 6).toString());
    }//GEN-LAST:event_tblCTHDMouseClicked

    private void btnFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFActionPerformed

    private void btnPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPActionPerformed

    private void btnNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNActionPerformed

    private void btnLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        if (txtSoLuong.getText().trim().isEmpty() || txtMaSach.getText().trim().isEmpty()) {
            MsgBox.alert(this, "Text trong");
            return;
        }

        int tt = tblTK.getSelectedRow();
        int sl = Integer.parseInt(txtSoLuong.getText().trim());
        String mas = tblTK.getValueAt(tt, 0).toString();
        Sach sach = DAOSACH.selectById(mas);
        if (sl > sach.getSoLuong()) {
            MsgBox.alert(this, "Cửa hàng không đủ sách ");
            return;
        }

        HoaDonCT a = getFormCTHD();
        DAOHDCT.insert(a);

        fillHDCT(txtMaHoaDon.getText());
        int updateSL = sach.getSoLuong() - sl;
        DAOSACH.updateSL(updateSL, mas);
        if (updateSL == 0) {
            DAOSACH.updateTT(mas, false);
        }
        fillSach();
        clearHDCT();
        // tính tổng tiền

        float c = 0;
        for (int i = 0; i < tblCTHD.getRowCount(); i++) {

            float k = Float.parseFloat(tblCTHD.getValueAt(i, 6).toString());
            c += k;
        }

        DAOHD.updateTTien(c, txtMaHoaDon.getText());
        fillHD();

        for (int m = 0; m < tblHD.getRowCount(); m++) {
            System.out.println(tblHD.getValueAt(m, 0).toString());
            System.out.println(txtMaHoaDon.getText().trim());
            if (tblHD.getValueAt(m, 0).toString().equals(txtMaHoaDon.getText())) {
                tblHD.setRowSelectionInterval(m, m);
                txtTongTien.setText(tblHD.getValueAt(m, 4).toString());
                return;
            }
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXuatMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXuatMouseClicked

    private void btnXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatActionPerformed
        // TODO add your handling code here:

        xuatHDCT();
    }//GEN-LAST:event_btnXuatActionPerformed

    private void btnLamMoiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoiMouseClicked

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearHDCT();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtTimKiemSachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemSachKeyReleased
        // TODO add your handling code here:
        fillSach();
    }//GEN-LAST:event_txtTimKiemSachKeyReleased

    private void btnTimKiemSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemSachActionPerformed

    private void tblTKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTKMouseClicked
        // TODO add your handling code here:
        int i = tblTK.getSelectedRow();
        txtMaSach.setText(tblTK.getValueAt(i, 0).toString());
        txtTenSach.setText(tblTK.getValueAt(i, 1).toString());
        txtGia.setText(tblTK.getValueAt(i, 2).toString());
        if (evt.getClickCount() == 2) {
            String sl = JOptionPane.showInputDialog("Nhập số lượng :");
        }
    }//GEN-LAST:event_tblTKMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int i = tblCTHD.getSelectedRow();
        if (i == -1) {
            JOptionPane.showMessageDialog(this, "Ban chua chon!", "Loi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int xacNhan = JOptionPane.showConfirmDialog(this, "Ban muon xoa?", "xn", JOptionPane.YES_NO_OPTION);
        if (xacNhan == 1) {
            return;
        }
        int a = Integer.parseInt(tblCTHD.getValueAt(i, 0).toString());
        String ma = txtMaSach.getText().trim();
        Sach sach = DAOSACH.selectById(ma);
        int sl = Integer.parseInt(tblCTHD.getValueAt(i, 4).toString());
        int updateSL = sach.getSoLuong() + sl;
        DAOSACH.updateSL(updateSL, ma);
        DAOSACH.updateTT(ma, true);
        DAOHDCT.delete(a);
        fillHDCT(txtMaHoaDon.getText().trim());

        float c = 0;
        for (int t = 0; t < tblCTHD.getRowCount(); t++) {

            float k = Float.parseFloat(tblCTHD.getValueAt(t, 6).toString());
            c += k;
        }
        DAOHD.updateTTien(c, txtMaHoaDon.getText());
        fillHD();
        clearHDCT();
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
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
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTimKiemHoaDon;
    private javax.swing.JButton btnTimKiemSach;
    private javax.swing.JButton btnXuat;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblCTHD;
    private javax.swing.JTable tblHD;
    private javax.swing.JTable tblTK;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaHoaDon;
    private javax.swing.JLabel txtMaNV;
    private javax.swing.JTextField txtMaSach;
    private javax.swing.JTextField txtNgayNhap;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTien;
    private javax.swing.JTextField txtTimKiemHD;
    private javax.swing.JTextField txtTimKiemSach;
    private javax.swing.JLabel txtTongTien;
    // End of variables declaration//GEN-END:variables
}
