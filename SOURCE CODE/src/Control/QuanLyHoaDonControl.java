/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.CTHDModel;
import Model.HOADONModel;
import Model.SANPHAMModel;
import View.ManHinhChinh;
import View.QuanlyHoadon;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh Hieu
 */
public class QuanLyHoaDonControl {

    private static Connection connection;
    private ArrayList<HOADONModel> DShoadon;
    private DefaultTableModel defaultTableModelhoadon;
    private int Rowselected = -1;

    public void LayMAHD(MouseEvent e) {

        Component component = (Component) e.getSource();
        QuanlyHoadon fr = (QuanlyHoadon) SwingUtilities.getRoot(component);
        int row = fr.getJtable().rowAtPoint(e.getPoint());
        Rowselected = row;
    }

    public void QuayLai(ActionEvent e, String NhanVien, int capbac) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        QuanlyHoadon fr = (QuanlyHoadon) SwingUtilities.getRoot(component);
        try {
            fr.dispose();
            ManHinhChinh manHinhChinh = new ManHinhChinh(NhanVien, capbac);
        } catch (IOException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public DefaultTableModel getDefaultTableModelhoadon() {
        return defaultTableModelhoadon;
    }

    public DefaultTableModel TaocoTableModel() {
        defaultTableModelhoadon = new DefaultTableModel();
        defaultTableModelhoadon.addColumn("Số hóa đơn");
        defaultTableModelhoadon.addColumn("Thời gian");
        defaultTableModelhoadon.addColumn("Nhân viên tính tiền");
        defaultTableModelhoadon.addColumn("Tổng tiền");
        return defaultTableModelhoadon;
    }

    public void thongkeHoadontheongay(ActionEvent e, String ngaynhap, String ngaytu, String ngayden) throws SQLException, ClassNotFoundException {
        Component component = (Component) e.getSource();
        QuanlyHoadon fr = (QuanlyHoadon) SwingUtilities.getRoot(component);
        TaocoTableModel();
        HOADONModel hoadon = new HOADONModel();
        DShoadon = new ArrayList<>();

        if (fr.getJtngay().getText().isEmpty() == true || fr.getJtngay().getText().equals("")) {
            DShoadon = hoadon.layDSHDtungaydenngay(ngaytu, ngayden);
            for (HOADONModel tam : DShoadon) {
                Vector v = new Vector();
                v.add(tam.getMAHD());
                v.add(tam.getNgaygio());
                v.add(tam.getMANV());
                v.add(tam.getTONGTIENHD());
                defaultTableModelhoadon.addRow(v);
            }

        } else {
            DShoadon = hoadon.layDanhsachhoadon();
            for (HOADONModel tam : DShoadon) {
                Vector v = new Vector();
                int i = tam.getNgaygio().indexOf(ngaynhap);
                if (i == 0) {
                    v.add(tam.getMAHD());
                    v.add(tam.getNgaygio());
                    v.add(tam.getMANV());
                    v.add(tam.getTONGTIENHD());
                    defaultTableModelhoadon.addRow(v);
                }

            }
        }

    }

    public void xoaSanphamdachon(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        QuanlyHoadon fr = (QuanlyHoadon) SwingUtilities.getRoot(component);
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        int array[];
        array = fr.getJtable().getSelectedRows();
        String mahd = (String) defaultTableModelhoadon.getValueAt(Rowselected, 0);
        float TongTien = (float) 0.0;
        TongTien = (Float) defaultTableModelhoadon.getValueAt(Rowselected, 3);
        if (TongTien == 0.0) {
            for (int i = 0; i < array.length; i++) {
                defaultTableModelhoadon.removeRow(array[i]);
            }
            System.out.println(mahd);
            String sql1 = "Delete From CHITIETHOADON where MAHD='" + mahd + "'";

            statement.executeUpdate(sql1);
            String sql2 = "Delete From HOADON where MAHD='" + mahd + "'";

            statement.executeUpdate(sql2);
        } else {
            JOptionPane.showMessageDialog(null, "Không được xóa hóa đơn có giá trị khác 0");
        }
    }

    public void xemCTHD(ActionEvent a) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) a.getSource();
        QuanlyHoadon fr = (QuanlyHoadon) SwingUtilities.getRoot(component);

        String mahd = (String) defaultTableModelhoadon.getValueAt(Rowselected, 0);
        File file = new File("src/HOADON/" + mahd + ".pdf");//đường dẫn file 
        Desktop dt = Desktop.getDesktop();
        dt.open(file);

    }

    public void ShowCTHD(String mahd) throws SQLException, ClassNotFoundException {
        Frame jf = new JFrame();
        jf = new JFrame("Chi tiết hóa đơn " + mahd);
        jf.setSize(600, 400);
        jf.setResizable(false);
        jf.setLayout(null);
        DefaultTableModel defaultCTHD = new DefaultTableModel();
        defaultCTHD.addColumn("Mã Sản Phẩm");
        defaultCTHD.addColumn("Tên Sản Phẩm");
        defaultCTHD.addColumn("Số lượng");
        defaultCTHD.addColumn("Giá");
        defaultCTHD.addColumn("Thành Tiền");
        ArrayList<CTHDModel> DSCTHD = new ArrayList<>();
        CTHDModel cthd = new CTHDModel();
        DSCTHD = cthd.layDSCTHD(mahd);
        for (CTHDModel ct : DSCTHD) {
            Vector v = new Vector();
            v.add(ct.getMASP());
            String tensanpham="";
            tensanpham=LoadTENSP(ct.getMASP());
            v.add(tensanpham);
            v.add(ct.getSoLuong());
            v.add(ct.getDONGIAHIENTAI());
            v.add(ct.getThanhTien());
            defaultCTHD.addRow(v);
        }
        JTable tbCTHD = new JTable(defaultCTHD);
        JScrollPane jScrollPane = new JScrollPane(tbCTHD);

        jScrollPane.setBounds(0, 0, 600, 400);
        jf.add(jScrollPane);

        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }

    public String LoadTENSP(String masp) throws SQLException, ClassNotFoundException {
        String tensp = "";
        ArrayList<SANPHAMModel> DSSP = new ArrayList<>();
        SANPHAMModel Sanpham = new SANPHAMModel();
        DSSP = Sanpham.layDanhSachSpDeban();
        for (SANPHAMModel sp : DSSP) {
            if (sp.getMASP().equals(masp)) {
                tensp = sp.getTENSP();
            }

        }

        return tensp;
    }
}
