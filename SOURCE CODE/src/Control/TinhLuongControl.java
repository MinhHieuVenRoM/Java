/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.BANGCHAMCONGModel;
import View.CHINH_DOANHTHU;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import View.TinhLuong;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nguyen Dang My Ngoc
 */
public class TinhLuongControl {
    private static Connection connection;
    private ArrayList<BANGCHAMCONGModel> DSBCC;
    private DefaultTableModel defaultTableModelluong;
    
    
    public DefaultTableModel getDefaultTableModelluong() {
        return defaultTableModelluong;
    }

    public DefaultTableModel TaocotTableModel() {
        defaultTableModelluong = new DefaultTableModel();
        defaultTableModelluong.addColumn("Tổng giờ làm");
        defaultTableModelluong.addColumn("Hệ số lương");
        defaultTableModelluong.addColumn("Tiền thưởng");
        defaultTableModelluong.addColumn("TỔNG LƯƠNG");
        return defaultTableModelluong;
    }
    
    public void QuayLai(ActionEvent e, String NhanVien, int capbac) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        TinhLuong fr = (TinhLuong) SwingUtilities.getRoot(component);
        try {
            fr.dispose();
            CHINH_DOANHTHU manhinh = new CHINH_DOANHTHU(NhanVien, capbac);
        } catch (IOException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void TinhLuong(ActionEvent e, String MaNV, int Thang, int Nam) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();

        TinhLuong fr = (TinhLuong) SwingUtilities.getRoot(component);
        
        BANGCHAMCONGModel bcc = new BANGCHAMCONGModel();
        DSBCC = new ArrayList<>();
        DSBCC = bcc.layDSBangCC(MaNV);
        TaocotTableModel();
        Vector v = new Vector();
        
        String sql1 = "select * from CHUCVU C, NHANVIEN N WHERE C.MaChucVu = N.MaChucVu AND MaNV = '" + MaNV + "'";
        ResultSet rs = statement.executeQuery(sql1);
        String heso = "" ;
        while (rs.next())
        {
             heso = rs.getString("HeSo");
             System.out.println(heso); 
        }
        
        String sql2 = "select count(calam)*6 from BANGCHAMCONG WHERE MaNV = '" + MaNV + "' and MONTH(Ngay) = '" + Thang + "'and YEAR(Ngay) = '" + Nam +"'";
        ResultSet TongGioLam = statement.executeQuery(sql2);
        System.out.println("TongGioLam");
        
        float TongLuong = 0.0f;
        
        v.add(TongGioLam);
        v.add(heso);
        v.add(0);
        v.add(TongLuong);  
        
        defaultTableModelluong.addRow(v);
    }
}
