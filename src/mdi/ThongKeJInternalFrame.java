/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mdi;

import EduSys.entity.HoaDon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import qlchs.dao.HoaDonDAO;
import qlchs.dao.ThongKeDAO;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

/**
 *
 * @author Admin
 */
public class ThongKeJInternalFrame extends javax.swing.JInternalFrame {

    HoaDonDAO hddao = new HoaDonDAO();
    ThongKeDAO tkdao = new ThongKeDAO();
    DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
    List<Integer> nam = hddao.selectYear();

    /**
     * Creates new form ThongKeJInternalFrame
     */
    public ThongKeJInternalFrame() {
        initComponents();
          fillcboDoanhThu();
        this.init();
    }

    
     void fillcboDoanhThu() {
//        cboNam.setModel(cboModel);
        cboNam.removeAllItems();
        for (Integer kh : hddao.selectYear()) {
            cboNam.addItem(String.valueOf(kh));
        }

    }

    private void fillTableDoanhThu() {
        try {
            DefaultTableModel dtm = (DefaultTableModel) tblBangThu.getModel();
            dtm.setRowCount(0);
            int year = Integer.parseInt(cboNam.getSelectedItem().toString()); //
            List<Object[]> list = tkdao.getThongKeDoanhThu(year);
            for (Object[] objects : list) {
                dtm.addRow(objects);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        bieudoThongKeDoanhThu();

    }

    void bieudoThongKeDoanhThu() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String series3 = "Doanh Thu";
        String nam = cboNam.getSelectedItem().toString();
        for (int j = 0; j < tblBangThu.getRowCount(); j++) {
            String Thang = tblBangThu.getValueAt(j, 0).toString();
            int SlKh = Integer.parseInt(tblBangThu.getValueAt(j, 1).toString());
            int SlBan = Integer.parseInt(tblBangThu.getValueAt(j, 2).toString());
            double DoanhThu = Double.parseDouble(tblBangThu.getValueAt(j, 3).toString());

            dataset.addValue(DoanhThu, series3, Thang);
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Thống kê doanh thu" // chart title
                ,
                 nam, "Doanh thu(VND)" // domain axis label
                ,
                 dataset,// range axis label
                PlotOrientation.VERTICAL,// data
                true, // include legend
                true, // tooltips?
                false // URLs?
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        jPanelBD.removeAll();
        jPanelBD.add(chartPanel, BorderLayout.CENTER);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.getRenderer().setSeriesPaint(0, new Color(0x00, 0xFF, 0x00));
        plot.getRenderer().setSeriesPaint(1, new Color(0x00, 0x00, 0x00));
        jPanelBD.validate();

    }

    private void xuatExcel() throws FileNotFoundException, IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFCellStyle style = createStyleForTitle(workbook);

        int rownum = 0;
        Cell cell = null;
        Row row;
        row = sheet.createRow(rownum); // tạo dòng thứ nhất => rownum0;
        cell = row.createCell(0);
        cell = sheet.getRow(0).getCell(0);
        cell.setCellValue("Thống Kê Doanh Thu Cửa Hàng Sách \n"+
                "Năm :"+cboNam.getSelectedItem().toString());
        cell.setCellStyle(createStyleForChuyenDe(workbook));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
  
        // Tạo dòng title
        rownum++;
        row = sheet.createRow(rownum); // rownum1
        // set title       
        for (int i = 0; i < tblBangThu.getColumnCount(); i++) {
            sheet.setColumnWidth(i, 9000);
            cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(tblBangThu.getColumnName(i));
            cell.setCellStyle(style);
        }
        // set data
        ///////////////////////////////////////////
        /////////////////////////////////////////
        //////////////////////////////////////
        ////////////////////////////////////
        /////////////////////////////////
        ///////////////////////////////
        /////////////////////////////
        ///////////////////////////

        for (int i = 0; i < tblBangThu.getRowCount(); i++) {
            rownum++;
            row = sheet.createRow(rownum); // tạo ra dòng thứ 2 
            // bắt đầu đóng dữ liệu vào 
            for (int j = 0; j < tblBangThu.getColumnCount(); j++) {
                System.out.println("Value ở for J : " + tblBangThu.getValueAt(i, j).toString());
                cell = row.createCell(j, CellType.STRING); // ô thứ nhất
                cell.setCellValue(tblBangThu.getValueAt(i, j).toString()); // set cho cái mã khóa học 1
            }
        }
//        File file = new File("D:/demoExcel/employee.xls");
//        file.getParentFile().mkdirs();
//
//        FileOutputStream outFile = new FileOutputStream(file);
//        workbook.write(outFile);
//        System.out.println("Created file: " + file.getAbsolutePath());
        try {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                FileOutputStream outFile = new FileOutputStream(file.getAbsoluteFile() + ".xls");
                workbook.write(outFile);
                outFile.close();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.WHITE.getIndex());
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return style;
    }

    private HSSFCellStyle createStyleForChuyenDe(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(new Short("270"));
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        return style;
    }
void init() {
        fillCbbDoanhSo();
        fillTableDoanhSo();

    }

    public void selectTab(int index) {
        TABS.setSelectedIndex(index);
    }

    void fillCbbDoanhSo() {

        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam1.getModel();
        model.removeAllElements();
        for (Integer kh : hddao.selectYear()) {
            model.addElement(kh);
        }
    }

    void fillTableDoanhSo() {
        DefaultTableModel dtm = (DefaultTableModel) tblDoanhSo.getModel();
        dtm.setRowCount(0);
        Integer year = (Integer) cboNam1.getSelectedItem();

        List<Object[]> list = tkdao.getThongKeSPBanChay(year);
        for (Object[] objects : list) {
            dtm.addRow(objects);
        }

        DefaultCategoryDataset barChartData = new DefaultCategoryDataset();
        for (Object[] row : list) {
            String maSach = (String) row[0];
            String doanhThu = String.format("%.0f", row[3]);
            barChartData.setValue(Double.parseDouble(doanhThu), "Doanh Thu", maSach);
        }
        JFreeChart barChart = ChartFactory.createAreaChart("Doanh Thu", "Hàng năm", "VND",
                barChartData, PlotOrientation.VERTICAL, false, true, true);
        CategoryPlot barchrt = barChart.getCategoryPlot();
        barchrt.setRangeGridlinePaint(Color.GREEN);
        ChartPanel barPanel = new ChartPanel(barChart);
        pnlBieuDoDoanhSo.removeAll();
        pnlBieuDoDoanhSo.add(barPanel, BorderLayout.CENTER);
        pnlBieuDoDoanhSo.validate();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TABS = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblBangThu = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        cboNam = new javax.swing.JComboBox<>();
        jPanelBD = new javax.swing.JPanel();
        btnExcel = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblDoanhSo = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cboNam1 = new javax.swing.JComboBox<>();
        pnlBieuDoDoanhSo = new javax.swing.JPanel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);

        TABS.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tổng Hợp Thống  Kê", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 18))); // NOI18N

        tblBangThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tháng", "Số Lượng Khách Hàng Mua Hàng", "Số  Lượng Bán", "DoanhThu"
            }
        ));
        jScrollPane4.setViewportView(tblBangThu);

        jLabel3.setText("Năm");

        cboNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamActionPerformed(evt);
            }
        });

        jPanelBD.setLayout(new java.awt.BorderLayout());

        btnExcel.setText("In ra");
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnExcel)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jPanelBD, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(42, 42, 42)
                            .addComponent(jLabel3)
                            .addGap(53, 53, 53)
                            .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 851, Short.MAX_VALUE)))
                .addGap(0, 42, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(30, 30, 30)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBD, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnExcel))
        );

        TABS.addTab("Doanh Thu", jPanel1);

        tblDoanhSo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sách", "Sản Phẩm Bán Chạy", "Số Lượng  Bán Ra", "Doanh Thu"
            }
        ));
        jScrollPane6.setViewportView(tblDoanhSo);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 908, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel4.setText("Năm");

        cboNam1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNam1ActionPerformed(evt);
            }
        });

        pnlBieuDoDoanhSo.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(cboNam1, javax.swing.GroupLayout.PREFERRED_SIZE, 445, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlBieuDoDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboNam1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(pnlBieuDoDoanhSo, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        TABS.addTab("Doanh Số", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(TABS, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TABS, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamActionPerformed
        fillTableDoanhThu();
    }//GEN-LAST:event_cboNamActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        try {
            xuatExcel();
        } catch (IOException ex) {
            Logger.getLogger(ThongKeJInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnExcelActionPerformed

    private void cboNam1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNam1ActionPerformed
        int index = cboNam1.getSelectedIndex();
        if (index > 0) {
            fillTableDoanhSo();
        }
    }//GEN-LAST:event_cboNam1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TABS;
    private javax.swing.JButton btnExcel;
    private javax.swing.JComboBox<String> cboNam;
    private javax.swing.JComboBox<String> cboNam1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelBD;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel pnlBieuDoDoanhSo;
    private javax.swing.JTable tblBangThu;
    private javax.swing.JTable tblDoanhSo;
    // End of variables declaration//GEN-END:variables
}
