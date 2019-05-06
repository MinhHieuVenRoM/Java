/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

/**
 *
 * @author Nguyen Dang My Ngoc
 */

import Model.DONNHAPHANGModel;
import Model.HOADONModel;
import View.CHINH_DOANHTHU;
import View.DoanhThu;
import View.ManHinhChinh;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class DoanhThuControl {
    private static Connection connection;
    private ArrayList<HOADONModel> DShoadon;
    private ArrayList<DONNHAPHANGModel> list_DonHang;
    private DefaultTableModel defaultTableModeldoanhthu;
    private int Rowselected = -1;
    
    public DefaultTableModel getDefaultTableModeldoanhthu() {
        return defaultTableModeldoanhthu;
    }
    
    public DefaultTableModel TaocotTableModel() {
        defaultTableModeldoanhthu = new DefaultTableModel();
        defaultTableModeldoanhthu.addColumn("Tổng thu");
        defaultTableModeldoanhthu.addColumn("Chi mua hàng");
        defaultTableModeldoanhthu.addColumn("Tổng lợi nhuận");
        return defaultTableModeldoanhthu;
    }
    
    public void QuayLai(ActionEvent e, String NhanVien, int capbac) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        DoanhThu fr = (DoanhThu) SwingUtilities.getRoot(component);
        try {
            fr.dispose();
            CHINH_DOANHTHU manhinh = new CHINH_DOANHTHU(NhanVien, capbac);
        } catch (IOException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    
    public void thongkeDoanhthutheongay(ActionEvent e, String ngaytu, String ngayden) throws SQLException, ClassNotFoundException {
        Component component = (Component) e.getSource();
        DoanhThu jf = (DoanhThu) SwingUtilities.getRoot(component);
        DShoadon = new ArrayList<>();
        HOADONModel hoadon = new HOADONModel();
        float TongTien = 0.0f;
        float TongTienNhap = 0.0f;
        float LoiNhuan = 0.0f;
        TaocotTableModel();
        Vector v = new Vector();
        
            DShoadon = hoadon.layDSHDtungaydenngay(ngaytu, ngayden);
            for (HOADONModel tam : DShoadon) {
                TongTien = TongTien + tam.getTONGTIENHD();
            }
        
        list_DonHang = new ArrayList<>();
        DONNHAPHANGModel donnhap = new DONNHAPHANGModel();
        
        list_DonHang = donnhap.layDSDNHtungaydenngay(ngaytu,ngayden);
        for(DONNHAPHANGModel tam : list_DonHang)
        {
            TongTienNhap = TongTienNhap + tam.getTONGGIATRI();
        }
           
        LoiNhuan = TongTien - TongTienNhap;    
        v.add(TongTien);
        v.add(0);
        v.add(LoiNhuan);   
        System.out.println(v);
        defaultTableModeldoanhthu.addRow(v);
        
    }
}
