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
import Model.BANGCHAMCONGModel;
import Model.NHANVIENModel;
import View.CHINH_DOANHTHU;
import View.DoanhThu;
import View.GioLamViec;
import View.ManHinhChinh;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
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
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class GioLamViecControl extends JFrame {

    private static Connection connection;
    private ArrayList<BANGCHAMCONGModel> DShoadon;
    private DefaultTableModel defaultTableModelGioLV, themgiolvvaobang;
    private int Rowselected = -1;
    private ArrayList<NHANVIENModel> DSnhanvien;
    private ArrayList<BANGCHAMCONGModel> DSBCC;

    public DefaultTableModel getDefaultTableModelGioLV() {
        return defaultTableModelGioLV;
    }

    public void QuayLai(ActionEvent e, String NhanVien, int capbac) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        GioLamViec fr = (GioLamViec) SwingUtilities.getRoot(component);
        try {
            fr.dispose();
            CHINH_DOANHTHU manhinh = new CHINH_DOANHTHU(NhanVien, capbac);
        } catch (IOException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String[] layManhanvien() throws SQLException, ClassNotFoundException {
        NHANVIENModel nv = new NHANVIENModel();
        nv.layThongtinnhanvien();
        String[] ds = new String[nv.demSonhanvien() + 1];
        DSnhanvien = nv.layThongtinnhanvien();
        int i = 0;
//        ds[0] = "TẤT CẢ";
        for (NHANVIENModel tam : DSnhanvien) {

            ds[i] = tam.getMaNV();
            i++;
        }
        return ds;
    }

//    public String loadmanv(ItemEvent e) throws IOException, ClassNotFoundException, SQLException {
//        Component component = (Component) e.getSource();
//        GioLamViec fr = (GioLamViec) SwingUtilities.getRoot(component);
//        String manv = "TẤT CẢ";
//        for (NHANVIENModel nv : DSnhanvien) {
//            if (fr.getjcmanv().getSelectedItem().toString().equals(l())) {
//                maloai = loai.getMALOAI();
//            }
//        }
//        themspvaobangsp("", maloai);
//        return maloai;
//    }
    public void themthuoctinhbanggiolv() {
        defaultTableModelGioLV = new DefaultTableModel();
        defaultTableModelGioLV.addColumn("Mã NV");
        defaultTableModelGioLV.addColumn("Ngày");
        defaultTableModelGioLV.addColumn("Ca làm");
    }

    public DefaultTableModel themgiolvvaobang(String manv) throws SQLException, ClassNotFoundException {
        themthuoctinhbanggiolv();
        BANGCHAMCONGModel bcc = new BANGCHAMCONGModel();
        DSBCC = new ArrayList<>();
        System.out.println(manv);
        DSBCC = bcc.layDSBangCC(manv);

////        NHANVIENModel nhanvien = new NHANVIENModel();
////        DSnhanvien = nhanvien.layThongtinnhanvien(); 
        defaultTableModelGioLV.setRowCount(0);
        for (BANGCHAMCONGModel tam : DSBCC) {
            Vector v = new Vector();
            if (tam.getMANV().equals(manv)) {
                v.add(tam.getMANV());
                v.add(tam.getNgay());
                v.add(tam.getCaLam());
                defaultTableModelGioLV.addRow(v);
            }
        }
        return defaultTableModelGioLV;
    }

    public void themca(ActionEvent e, String manv, String ca, String NgayLam) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();

        GioLamViec fr = (GioLamViec) SwingUtilities.getRoot(component);

        BANGCHAMCONGModel bcc = new BANGCHAMCONGModel();
        ArrayList<BANGCHAMCONGModel> danhsachtam = bcc.layDSBangCC(manv);

        int size = danhsachtam.size();
        String MACC = danhsachtam.get(size - 1).getMACC();
        System.out.println(MACC);
        String chuoisocctam = MACC.substring(2, 5);//cắt chuỗi từ vị trí 2->(5-1)
        int temp = Integer.parseInt(chuoisocctam);//lấy macc cuối cùng00
        String MACCTAM = "";
        if (temp < 9) {
            MACCTAM = "CC00" + (temp + 1);
        }
        if (temp >= 9 && temp < 99) {
            MACCTAM = "CC0" + (temp + 1);
        }
        if (temp >= 99) {
            MACCTAM = "CC" + (temp + 1);
        }
        System.out.println(MACCTAM);
        String sql1 = "insert into BANGCHAMCONG values ('" + MACCTAM + "', '" + manv + "','" + NgayLam + " ',N'" + ca + "')";
        System.out.println(sql1);
        statement.executeUpdate(sql1);

    }
}
