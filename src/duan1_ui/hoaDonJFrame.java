/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duan1_ui;

import EduSys.entity.*;
import qlchs.dao.*;
import qlchs.utils.*;
import java.awt.Color;
import static java.awt.Color.white;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.DefaultTreeCellEditor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Admin
 */
public class hoaDonJFrame extends javax.swing.JFrame {
    int index = 0;
    nhanvienDAO nvdao = new nhanvienDAO();
    HoaDonDAO dao1 = new HoaDonDAO();
    HoaDonCTDAO dao = new HoaDonCTDAO();
    SachDAO sdao = new SachDAO();
    khachhangDAO khdao = new khachhangDAO();
     
    
            
    /**
     * Creates new form hoaDonJFrame
     */
    public hoaDonJFrame() {
        initComponents();
        init();
        loadToHoaDon();
//        loadToHoaDonct();
        loadcboSach();
//        loadcboHD();
        loadcboNhanVien();
        loadcboKhachHang();
        txtNgayXuat.setText(java.time.LocalDate.now() + "");
    }
    
    void init(){
        setLocationRelativeTo(null);
        setTitle("Quản lý hóa đơn");
    }
    void manv(){
       
//       String manv =  JOptionPane.showInputDialog(this, "mời bạn nhập Mã Nhân Viên","hệ thống",JOptionPane.DEFAULT_OPTION);
//       txtMaNV.setText(manv);
//        String mo = txtMaNV.getText();
//        List<NhanVien> l = nvdao.selectIdList(mo);
//        for (NhanVien nhanVien : l) {
//          
        
    }
    void loadcboKhachHang(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboKhachHang.getModel();
        model.removeAllElements();
        try {
            List<KhachHang> lst = khdao.selectAll();
            for (KhachHang khachHang : lst) {
                cboKhachHang.addItem(khachHang.getMaKH()+"");
            }
        } catch (Exception e) {
            
        }
    }
    void loadcboNhanVien(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNhanVien.getModel();
        model.removeAllElements();
        try {
            List<NhanVien> lst = nvdao.selectAll();
            for (NhanVien nv : lst) {
                cboNhanVien.addItem(nv.getMaNV());
            }
        } catch (Exception e) {
            MsgBox.alert(this, "lỗi truy vấn cbo nhân viên");
        }
    }
//
//    void loadcboHD(){
//        DefaultComboBoxModel model = (DefaultComboBoxModel) cboHoaDon.getModel();
//        model.removeAllElements();
//        try {
//           List<HoaDon> list = dao1.selectAll();
//            for (HoaDon hoaDon : list) {
//                cboHoaDon.addItem(hoaDon.getMaHD() +"");
//            }
//        } catch (Exception e) {
//        }
//    }
    void loadcboSach(){
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboSach.getModel();
        model.removeAllElements();
        try {
           List<Sach> lst = sdao.selectAll();
            for (Sach sach : lst) {
                cboSach.addItem(sach.getMaSach());
            }
        } catch (Exception e) {
            MsgBox.alert(this,"lỗi truy vấn cbo sách");
        }
    }
//    void selectcboSach(){
//        Sach sach = (Sach) cboSach.getSelectedItem();
//        txtTenSach.setText(sach.getTenSach());
//    }
    void loadToHoaDon(){
        DefaultTableModel model = (DefaultTableModel) tblView.getModel();
        model.setRowCount(0);
        try {
            String key = txtTimKiem.getText();
            List<HoaDon> lst = dao1.selectByKeyword(key);
            for (HoaDon hd : lst) {
                Object[] row = {
                    hd.getMaHD(),
                    hd.getMaNV(),
                    hd.getMaKH(),
                    XDate.toString( hd.getNgayXuat()),
                    hd.getTongTien()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this,"Lỗi truy vấn dữ liệu bảng hoa đơn");
        }
    }
    void setModelHD(HoaDon hoaDon){
        
        txtMaHD.setText(hoaDon.getMaHD()+"");
        
        cboNhanVien.setToolTipText(hoaDon.getMaNV());
        cboNhanVien.getModel().setSelectedItem(nvdao.selectById(hoaDon.getMaNV())); 
//        txtMaNV.setText(hoaDon.getMaNV());
        cboKhachHang.setToolTipText(hoaDon.getMaKH()+"");
        cboKhachHang.getModel().setSelectedItem(khdao.selectById(hoaDon.getMaKH()));
        txtNgayXuat.setText(XDate.toString(hoaDon.getNgayXuat(),"yyyy-MM-dd"));
        txtTongTien.setText(hoaDon.getTongTien()+"");
    }
    HoaDon getModelHd(){
        HoaDon hd = new HoaDon();
//        NhanVien nhanvien = (NhanVien) jComboBox1.getSelectedItem();
        hd.setMaHD(Integer.valueOf(txtMaHD.getText()));
        hd.setMaNV(cboNhanVien.getSelectedItem()+"");
//        hd.setMaNV(txtMaNV.getText());
        hd.setMaKH(Integer.valueOf(cboKhachHang.getSelectedItem().toString()));
        hd.setNgayXuat(XDate.toDate(txtNgayXuat.getText()));
        hd.setTongTien(Float.parseFloat(txtTongTien.getText()));
        
        return hd;
    }
    void setStatus() {
        boolean edit = this.index >= 0;
        boolean first = this.index == 0;
        boolean last = this.index == tblView.getRowCount() - 1;
        txtMaHD.setEditable(!edit);    
        btnThem.setEnabled(!edit);   
        btnSua.setEnabled(edit);  
     
       
        btnFirst.setEnabled(edit && !first);
        btnPev.setEnabled(edit && !first); 
        btnNext.setEnabled(edit && !last);  
        btnLast.setEnabled(edit && !last);  
    }
    public void setTrang(){
        txtMaHD.setBackground(white);
//        jComboBox1.setBackground(white);
//        txtMaNV.setBackground(white);
//        txtMaKH.setBackground(white);
        txtNgayXuat.setBackground(white);
        txtTongTien.setBackground(white);
    }
    void edit(){
        setTrang();
        try {
            int maHd =(int) tblView.getValueAt(this.index,0);
            HoaDon model = dao1.selectById(maHd);
            if (model!=null) {
                this.setModelHD(model);
                this.setStatus();
                tblView.setRowSelectionInterval(index, index);
            }
        } catch (Exception e) {
            MsgBox.alert(this,"lỗi truy vấn");
        }
    }
  void clear(){
      setTrang();
      this.setModelHD(new HoaDon());
      index = -1;
      this.setStatus();
  }
 void search(){
     this.loadToHoaDon();
    this.clear();
    this.index = -1;
    setStatus();
  }
   void inserthd(){
        HoaDon model = getModelHd();
            try {
               
                dao1.insert(model);
                this.loadToHoaDon();
//                loadcboHD();
                this.clear();   
                MsgBox.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Thêm mới thất bại!");
                e.printStackTrace();
            }
 }
    void updatehd(){
        HoaDon model = getModelHd();
            try {
                dao1.update(model);
                this.loadToHoaDon();
                this.clear();   
                MsgBox.alert(this, "Cập nhập thành công!");
            } catch (Exception e) {
                MsgBox.alert(this, "Cập nhập thất bại!");
                e.printStackTrace();
            }
 }
    
    
    void loadToHoaDonct(){
        DefaultTableModel model = (DefaultTableModel) tblCTHD.getModel();
        model.setRowCount(0);
        try {
             String key = txtSeach.getText();
            List<HoaDonCT> lst = dao.selectByKeyword(key);
            for (HoaDonCT hdct : lst) {
                Object[] row = {
                    hdct.getMaHDCT(),
                    hdct.getMaHD(),
                    hdct.getMaSach(),
                    hdct.getSoLuong(),
                    hdct.getGiaBan(),
                    hdct.getThanhTien(),
                    hdct.getTenSach()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            MsgBox.alert(this,"Lỗi truy vấn dữ liệu bảng hoa đơn chi tiết");
            e.printStackTrace();
        }
    }
    void setModelHDct(HoaDonCT hoaDonct){
        cboSach.setToolTipText(String.valueOf(hoaDonct.getMaSach()));    
        cboSach.getModel().setSelectedItem(sdao.selectById(hoaDonct.getMaSach())); 
         txtCTHD.setText(hoaDonct.getMaHDCT()+"");
//         cboHoaDon.setToolTipText(hoaDonct.getMaHD()+"");
//        cboHoaDon.getModel().setSelectedItem(khdao.selectById(hoaDonct.getMaHD()));
//        txtMahd.setText(hoaDonct.getMaHD()+"");
//        txtSach.setText(hoaDonct.getMaSach());
        txtMAHD.setText(hoaDonct.getMaHD()+"");
        txtSoLuong.setText(hoaDonct.getSoLuong()+"");
        txtGia.setText(hoaDonct.getGiaBan()+"");
        txtTien.setText(hoaDonct.getThanhTien()+"");
          txtTenSach.setText(hoaDonct.getTenSach());
       
       
    }
    HoaDonCT getModelHdct(){
        HoaDonCT hdct = new HoaDonCT();
//        Sach sach = (Sach) cboSach.getSelectedItem();
//        HoaDon hd = (HoaDon) cboHoaDon.getSelectedItem();
//        hdct.setMaHDCT(Integer.valueOf(txtCTHD.getText()));
//        hdct.setMaHD(Integer.valueOf(cboHoaDon.getSelectedItem().toString()));
            hdct.setMaHD(Integer.valueOf(txtMAHD.getText()));
        hdct.setMaSach(cboSach.getSelectedItem().toString());
        hdct.setSoLuong(Integer.valueOf(txtSoLuong.getText()));
        hdct.setGiaBan(Float.valueOf(txtGia.getText()));
        hdct.setThanhTien(Float.valueOf(txtTien.getText()));
        hdct.setTenSach(txtTenSach.getText());
        return hdct;
    }
    void setStatushdct() {
        boolean edit = this.index >= 0;
        boolean first = this.index == 0;
        boolean last = this.index == tblView.getRowCount() - 1;
           
        btnAdd.setEnabled(!edit);   
        btnUpdate.setEnabled(edit);  
        btnXuat.setEnabled(edit);  
       
        btnF.setEnabled(edit && !first);
        btnP.setEnabled(edit && !first); 
        btnN.setEnabled(edit && !last);  
        btnL.setEnabled(edit && !last);  
    }
    public void setTranghdct(){
        txtCTHD.setBackground(white);
//        txtMahd.setBackground(white);
        txtSoLuong.setText("0");
        txtGia.setText("0");
        txtTien.setText("0");
    }
    void edithdct(){
        setTranghdct();
        try {
            int maHdct =(int) tblCTHD.getValueAt(this.index,0);
            HoaDonCT model = dao.selectById(maHdct);
            if (model!=null) {
                this.setModelHDct(model);
                this.setStatushdct();
                tblCTHD.setRowSelectionInterval(index, index);
            }
        } catch (Exception e) {
            MsgBox.alert(this,"lỗi truy vấn");
        }
    }
  void clearhdct(){
      setTranghdct();
      this.setModelHDct(new HoaDonCT());
      index = -1;
      this.setStatushdct();
  }
  void addcthd(){
      HoaDonCT model = getModelHdct();
      try {
          dao.insert(model);
             //update số lượng sách
//        Sach sach = sdao.selectById(cboSach.getSelectedItem().toString());
//        int cc = sach.getSoLuong() + Integer.parseInt(txtSoLuong.getText());
//        sdao.updateSL(cc, cboSach.getSelectedItem().toString());
        // tính tổng tiền
        txtSeach.setText(txtMAHD.getText());
        loadToHoaDonct();
        float c = 0;
        for (int i = 0; i < tblCTHD.getRowCount(); i++) {
            
            float k = Float.parseFloat(tblCTHD.getValueAt(i, 5).toString());            
            c += k;            
        }
        
        dao1.updateTTien(c, txtMAHD.getText());
          this.loadToHoaDon();
          this.clear();
          this.clearhdct();
          MsgBox.alert(this,"thêm thành công");
      } catch (Exception e) {
          MsgBox.alert(this, "thêm thất bại");
          e.printStackTrace();     }
  }
  void updateCtHd(){
       HoaDonCT model = getModelHdct();
      try {
//          if (txtCTHD.getText().isEmpty()) {
//              MsgBox.alert(this,"dhct trống");
//              return;
//          }
          dao.update(model);
           this.loadToHoaDonct();
            float c = 0;
        for (int i = 0; i < tblCTHD.getRowCount(); i++) {
            
            float k = Float.parseFloat(tblCTHD.getValueAt(i, 5).toString());            
            c += k;            
        }
        dao1.updateTTien(c, txtMAHD.getText());
         loadToHoaDon();
          this.clearhdct();
          MsgBox.alert(this,"Cập nhập thành công");
      } catch (Exception e) {
          MsgBox.alert(this, "Cập nhập thất bại");
      }
  }
 void addct(){
//     HoaDon hd = new HoaDon();
//     try {
//     for(int index : tblView.getSelectedRows()){
//         HoaDonCT hdct = new HoaDonCT();
//         hdct.setMaHD(hd.getMaHD());
//         hdct.setMaSach("");
//         hdct.getMaHDCT();
//         hdct.setTenSach("");
//         hdct.setGiaBan(0);
//         hdct.setSoLuong(0);
//         hdct.setThanhTien(0);
//          hdct.setMaHD((Integer) tblView.getValueAt(index, 0));
//          dao.insert(hdct);
//     }
//      } catch (Exception e) {
//          MsgBox.alert(this, "thất bại");
//          e.printStackTrace();
//     }
//      loadToHoaDonct();
for(int index : tblView.getSelectedRows()){
     HoaDonCT hdct = new HoaDonCT();
     hdct.setMaHD((Integer) tblView.getValueAt(index, 0));
//     loadcboHD();
//     cboHoaDon.setToolTipText(hdct.getMaHD()+"");
//     cboHoaDon.getModel().setSelectedItem(khdao.selectById(hdct.getMaHD()));
          txtMAHD.setText(hdct.getMaHD()+"");
}
     tabs.setSelectedIndex(1);
 }

 void xuatHDCT(){
     XSSFWorkbook xs = new XSSFWorkbook();
     XSSFSheet sheet =xs.createSheet("HÓA ĐƠN CHI TIẾT");
        XSSFRow row = null;
        Cell cell = null;
        row = sheet.createRow(2);
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("STT");
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Mã Chi Tiết Hóa Đơn");
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Mã Hóa Đơn");
        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Mã Sách");
        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Số Lượng");
        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Giá Bán");
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Thành Tiền");
        cell = row.createCell(7,CellType.STRING);
        cell.setCellValue("Tên Sách");
        for (int i = 0; i < tblCTHD.getRowCount(); i++) {
            row = sheet.createRow(3 + i);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(i + 1);
            cell = row.createCell(1, CellType.STRING);
            String maCt = tblCTHD.getValueAt(i, 0).toString();
            cell.setCellValue(maCt);
            cell = row.createCell(2, CellType.STRING);
            String maHd = tblCTHD.getValueAt(i, 1).toString();
            cell.setCellValue(maHd);
            cell = row.createCell(3, CellType.STRING);
            String maSach = tblCTHD.getValueAt(i, 2).toString();
            cell.setCellValue(maSach);
            cell = row.createCell(4, CellType.STRING);
            
            String soLuong = tblCTHD.getValueAt(i, 3).toString();
            cell.setCellValue(soLuong);
            cell = row.createCell(5, CellType.STRING);
            String giaBan = tblCTHD.getValueAt(i, 4).toString();
            cell.setCellValue(giaBan);
             cell = row.createCell(6, CellType.STRING);
            String thanhTien = tblCTHD.getValueAt(i, 5).toString();
            cell.setCellValue(thanhTien);
             cell = row.createCell(7, CellType.STRING);
            String tenSach = tblCTHD.getValueAt(i, 6).toString();
            cell.setCellValue(tenSach);
             cell = row.createCell(8, CellType.STRING);
        }
        try {
//            FormChinhJDialog formchinh = new FormChinhJDialog();
//            String time = formchinh.getTime();
//            NewJFrame fam = new NewJFrame();
//            String time = fam.getTime();
            File file = new File("‪‪HoaDonCT " +  ".xlsx");
            FileOutputStream fos = new FileOutputStream(file);
            xs.write(fos);
            fos.close();
            MsgBox.alert(this, "Xuất file thành công!");
        } catch (Exception e) {
            e.printStackTrace();
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

        tabs = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        txtNgayXuat = new javax.swing.JTextField();
        txtTongTien = new javax.swing.JTextField();
        cboNhanVien = new javax.swing.JComboBox<>();
        cboKhachHang = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTimKiem = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblView = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnthemHD = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCTHD = new javax.swing.JTextField();
        txtSoLuong = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTien = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtTenSach = new javax.swing.JTextField();
        cboSach = new javax.swing.JComboBox<>();
        txtMAHD = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCTHD = new javax.swing.JTable();
        btnF = new javax.swing.JButton();
        btnP = new javax.swing.JButton();
        btnN = new javax.swing.JButton();
        btnL = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        txtSeach = new javax.swing.JTextField();
        btnSeach = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        btnXuat = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Cập nhập"));

        jLabel2.setText("MÃ HÓA ĐƠN :");

        jLabel3.setText("MÃ NHÂN VIÊN :");

        jLabel4.setText("MÃ KHÁCH HÀNG :");

        jLabel5.setText("NGÀY XUẤT :");

        jLabel6.setText("TỔNG TIỀN :");

        txtMaHD.setEditable(false);
        txtMaHD.setText("0");
        txtMaHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHDActionPerformed(evt);
            }
        });

        txtNgayXuat.setEditable(false);

        txtTongTien.setEditable(false);
        txtTongTien.setText("0.0");

        cboNhanVien.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cboKhachHang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNgayXuat))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTongTien))
                    .addComponent(txtMaHD)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(32, 32, 32))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboKhachHang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(40, 40, 40)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(cboNhanVien, 0, 111, Short.MAX_VALUE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(txtMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNgayXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtTongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách"));

        jLabel7.setText("MÃ HÓA ĐƠN :");

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
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblView.setModel(new javax.swing.table.DefaultTableModel(
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
        tblView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblViewMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblView);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(195, 195, 195))
        );

        btnThem.setBackground(new java.awt.Color(39, 56, 120));
        btnThem.setForeground(new java.awt.Color(240, 240, 240));
        btnThem.setText("THÊM");
        btnThem.setBorder(null);
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(39, 56, 120));
        btnSua.setForeground(new java.awt.Color(240, 240, 240));
        btnSua.setText("SỬA");
        btnSua.setBorder(null);
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
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

        btnthemHD.setBackground(new java.awt.Color(39, 56, 120));
        btnthemHD.setForeground(new java.awt.Color(255, 255, 255));
        btnthemHD.setText("THÊM CTHD");
        btnthemHD.setBorder(null);
        btnthemHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnthemHDActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnthemHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPev, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNew, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPev, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnFirst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnthemHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(77, Short.MAX_VALUE))
        );

        tabs.addTab("Hóa Đơn", jPanel1);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Cập nhập"));

        jLabel8.setText("MÃ CT HÓA ĐƠN :");

        jLabel9.setText("MÃ SÁCH :");

        jLabel10.setText("MÃ HÓA ĐƠN :");

        jLabel11.setText("SỐ LƯỢNG :");

        jLabel12.setText("GIÁ BÁN :");

        txtCTHD.setEditable(false);
        txtCTHD.setText("0");

        txtSoLuong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSoLuongKeyReleased(evt);
            }
        });

        txtGia.setEditable(false);
        txtGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGiaActionPerformed(evt);
            }
        });
        txtGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaKeyReleased(evt);
            }
        });

        jLabel1.setText("THÀNH TIỀN:");

        txtTien.setEditable(false);
        txtTien.setText("0");

        jLabel14.setText("TÊN SÁCH:");

        txtTenSach.setEditable(false);
        txtTenSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenSachActionPerformed(evt);
            }
        });

        cboSach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSachActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel14)
                                .addComponent(jLabel9))
                            .addComponent(jLabel1)
                            .addComponent(txtGia, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                            .addComponent(txtSoLuong)
                            .addComponent(txtCTHD)
                            .addComponent(txtTenSach)
                            .addComponent(txtTien)
                            .addComponent(cboSach, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel11)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtMAHD, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel9)
                .addGap(3, 3, 3)
                .addComponent(cboSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTenSach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCTHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtMAHD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel11)
                .addGap(4, 4, 4)
                .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Danh Sách"));

        tblCTHD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ CT HÓA ĐƠN", "MÃ HÓA ĐƠN", "MÃ SÁCH", "SỐ LƯỢNG", "GIÁ BÁN ", "THÀNH TIỀN", "TÊN SÁCH"
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

        txtSeach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSeachActionPerformed(evt);
            }
        });
        txtSeach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSeachKeyReleased(evt);
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

        jLabel13.setText("MÃ CT HÓA ĐƠN:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(34, 34, 34)
                .addComponent(txtSeach, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSeach, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSeach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSeach, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(btnF, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(btnP, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56)
                        .addComponent(btnN, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnL, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnF, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnP, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnN, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnL, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        btnLamMoi.setBackground(new java.awt.Color(39, 56, 120));
        btnLamMoi.setForeground(new java.awt.Color(240, 240, 240));
        btnLamMoi.setText("LÀM MỚI");
        btnLamMoi.setBorder(null);
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        btnXuat.setBackground(new java.awt.Color(39, 56, 120));
        btnXuat.setForeground(new java.awt.Color(240, 240, 240));
        btnXuat.setText("XUẤT HĐ");
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

        btnUpdate.setBackground(new java.awt.Color(39, 56, 120));
        btnUpdate.setForeground(new java.awt.Color(240, 240, 240));
        btnUpdate.setText("SỬA");
        btnUpdate.setBorder(null);
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabs.addTab("Hóa Đơn Chi Tiết", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabs)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabs))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        inserthd();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        // TODO add your handling code here:
         loadToHoaDon();
        clear();
        search();
    }//GEN-LAST:event_btnTimActionPerformed

    private void btnPevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPevActionPerformed
        // TODO add your handling code here:
        this.index --;
        this.edit();
    }//GEN-LAST:event_btnPevActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        int sl = Integer.parseInt(txtSoLuong.getText());
        try {
            if(sl<=0){
                MsgBox.alert(this, "So luong phai lon hon 0");
                return;
            }
        } catch (Exception e) {
             MsgBox.alert(this, "So luong phai la so");
                return;
        }
      addcthd();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPActionPerformed
        // TODO add your handling code here:
         this.index --;
        this.edithdct();
    }//GEN-LAST:event_btnPActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updatehd();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        clearhdct();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        updateCtHd();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void tblViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblViewMouseClicked
        // TODO add your handling code here:
          if (evt.getClickCount() == 2) {
            this.index = tblView.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.edit();
            }
        }
    }//GEN-LAST:event_tblViewMouseClicked

    private void btnthemHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnthemHDActionPerformed
        // TODO add your handling code here:
        addct();
       
    }//GEN-LAST:event_btnthemHDActionPerformed

    private void tblCTHDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTHDMouseClicked
        // TODO add your handling code here:
         if (evt.getClickCount() == 2) {
            this.index = tblCTHD.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.edithdct();
            }
        }
    }//GEN-LAST:event_tblCTHDMouseClicked

    private void txtMaHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMaHDActionPerformed

    private void cboSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSachActionPerformed
        // TODO add your handling code here:
//        loadcboSach();
//                selectcboSach() ;
         List<Sach> lst =sdao.selectAll();
              int index = 0;
              index =   cboSach.getSelectedIndex();
              if (index>=0) {
            txtTenSach.setText(lst.get(index).getTenSach());
            txtGia.setText(lst.get(index).getGiaBan()+"");
            String mas = cboSach.getSelectedItem()+"";
        }
    }//GEN-LAST:event_cboSachActionPerformed

    private void btnFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFActionPerformed
        // TODO add your handling code here:
         this.index = 0;
        this.edithdct();
    }//GEN-LAST:event_btnFActionPerformed

    private void btnNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNActionPerformed
        // TODO add your handling code here:
         this.index ++;
        this.edithdct();
    }//GEN-LAST:event_btnNActionPerformed

    private void btnLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLActionPerformed
        // TODO add your handling code here:
         this.index = tblCTHD.getRowCount() - 1;
        this.edithdct();
    }//GEN-LAST:event_btnLActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        this.index = 0;
        this.edit();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        this.index ++;
        this.edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
         this.index = tblView.getRowCount() - 1;
        this.edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXuatMouseClicked
        // TODO add your handling code here:
//        for(int index : tblView.getSelectedRows()){
//           xuatHDCT();
//        }
    }//GEN-LAST:event_btnXuatMouseClicked

    private void btnXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatActionPerformed
        // TODO add your handling code here:
        for(int index : tblCTHD.getSelectedRows()){
            xuatHDCT();
        }
    }//GEN-LAST:event_btnXuatActionPerformed

    private void txtSoLuongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSoLuongKeyReleased
        // TODO add your handling code here:
          if (txtSoLuong.getText().isEmpty() == false && txtGia.getText().isEmpty() == false) {
            int sl = Integer.parseInt(txtSoLuong.getText());
            float gn = Float.parseFloat(txtGia.getText());
            float a = sl * gn;
            txtTien.setText(String.valueOf(a));
        }
    }//GEN-LAST:event_txtSoLuongKeyReleased

    private void txtGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaKeyReleased
        // TODO add your handling code here:
//         if (txtGia.getText().isEmpty() == false) {
//            int sl = Integer.parseInt(txtSoLuong.getText());
//            float gn = Float.parseFloat(txtGia.getText());
//            float a = sl * gn;
//            txtTien.setText(String.valueOf(a));
//        }
    }//GEN-LAST:event_txtGiaKeyReleased

    private void txtSeachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSeachKeyReleased
        // TODO add your handling code here:
       loadToHoaDonct();
    }//GEN-LAST:event_txtSeachKeyReleased

    private void btnSeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeachActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btnSeachActionPerformed

    private void txtSeachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSeachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSeachActionPerformed

    private void txtTenSachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenSachActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenSachActionPerformed

    private void txtGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGiaActionPerformed

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
            java.util.logging.Logger.getLogger(hoaDonJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(hoaDonJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(hoaDonJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(hoaDonJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new hoaDonJFrame().setVisible(true);
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
    private javax.swing.JButton btnSeach;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXuat;
    private javax.swing.JButton btnthemHD;
    private javax.swing.JComboBox<String> cboKhachHang;
    private javax.swing.JComboBox<String> cboNhanVien;
    private javax.swing.JComboBox<String> cboSach;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JTable tblCTHD;
    private javax.swing.JTable tblView;
    private javax.swing.JTextField txtCTHD;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMAHD;
    private javax.swing.JTextField txtMaHD;
    private javax.swing.JTextField txtNgayXuat;
    private javax.swing.JTextField txtSeach;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenSach;
    private javax.swing.JTextField txtTien;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtTongTien;
    // End of variables declaration//GEN-END:variables
}
