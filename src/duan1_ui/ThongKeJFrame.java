/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duan1_ui;

import EduSys.entity.HoaDon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import qlchs.dao.HoaDonDAO;
import qlchs.dao.ThongKeDAO;

/**
 *
 * @author Admin
 */
public class ThongKeJFrame extends javax.swing.JFrame {

    HoaDonDAO hddao = new HoaDonDAO();
    ThongKeDAO tkdao = new ThongKeDAO();

    /**
     * Creates new form ThongKeJFrame
     */
    public ThongKeJFrame() {
        initComponents();
        fillcboDoanhThu();
        this.init();
        this.setLocationRelativeTo(null);
    }

    void fillcboDoanhThu() {

        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam.getModel();
        model.removeAllElements();
        for (Integer kh : hddao.selectYear()) {
            model.addElement(kh);
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
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblDoanhSo = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        cboNam1 = new javax.swing.JComboBox<>();
        pnlBieuDoDoanhSo = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel3)
                .addGap(53, 53, 53)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanelBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jPanelBD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(pnlBieuDoDoanhSo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
                .addContainerGap()
                .addComponent(TABS)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TABS)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamActionPerformed
        fillTableDoanhThu();

    }//GEN-LAST:event_cboNamActionPerformed

    private void cboNam1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNam1ActionPerformed
        int index = cboNam1.getSelectedIndex();
        if (index > 0) {
            fillTableDoanhSo();
        }
    }//GEN-LAST:event_cboNam1ActionPerformed

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
            java.util.logging.Logger.getLogger(ThongKeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongKeJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane TABS;
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
            int doanhSo = (Integer) row[2];
            barChartData.setValue(doanhSo, "Doanh Số", maSach);
        }
        JFreeChart barChart = ChartFactory.createAreaChart("Doanh Số",cboNam1.getSelectedItem().toString(), "Số lượng",
                barChartData, PlotOrientation.VERTICAL, false, true, true);
        CategoryPlot barchrt = barChart.getCategoryPlot();
        barchrt.setRangeGridlinePaint(Color.GREEN);
        ChartPanel barPanel = new ChartPanel(barChart);
        pnlBieuDoDoanhSo.removeAll();
        pnlBieuDoDoanhSo.add(barPanel, BorderLayout.CENTER);
        pnlBieuDoDoanhSo.validate();
    }
}
