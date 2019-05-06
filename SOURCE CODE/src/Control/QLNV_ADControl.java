/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.CHUCVUModel;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import Model.NHANVIENModel;
import View.BanHang;
import View.ManHinhChinh;
import View.Quanlynhanvien;
import View.Quanlytinthongcanhan;
import com.itextpdf.text.DocumentException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
/**
 *
 * @author Thinh
 */
public class QLNV_ADControl {
    private DefaultTableModel defaultTableModelNV;
    private NHANVIENModel NVflag;
    private CHUCVUModel CVflag;
    private ArrayList<NHANVIENModel> DSNV;
    private ArrayList<CHUCVUModel> DSCV;
    
    public void inforNV() throws SQLException, ClassNotFoundException{
        DSNV = new ArrayList<>();
        NHANVIENModel NV = new NHANVIENModel();
        DSNV = NV.layThongtinnhanvien();
    }
    
    public void inforCV() throws SQLException, ClassNotFoundException{
        DSCV = new ArrayList<>();
        CHUCVUModel cv = new CHUCVUModel();
        DSCV = cv.layDanhsachchucvu();
    }
    public void themthuoctinhbangnhanvien() {
        defaultTableModelNV = new DefaultTableModel();
        defaultTableModelNV.addColumn("Mã NV");
        defaultTableModelNV.addColumn("Tên NV");
        defaultTableModelNV.addColumn("Chức Vụ");
    }
    public DefaultTableModel themnvvaobangnv(String a) throws SQLException, ClassNotFoundException {
        themthuoctinhbangnhanvien();
        inforNV();
        defaultTableModelNV.setRowCount(0);
        for (NHANVIENModel i_NV : DSNV) {
            
            Vector v = new Vector();
            if(a.equals("") == true){
                v.add(i_NV.getMaNV());
                v.add(i_NV.getTenNV());
                v.add(i_NV.getMaChucVu());
                defaultTableModelNV.addRow(v);
            }
            else
            {
                if(i_NV.getTenNV().indexOf(a) != -1){
                    v.add(i_NV.getMaNV());
                    v.add(i_NV.getTenNV());
                    v.add(i_NV.getMaChucVu());
                    defaultTableModelNV.addRow(v);
                }
            }
            }
        return defaultTableModelNV;
    }
    
    public NHANVIENModel  LoadNV(String a) throws SQLException, ClassNotFoundException {
        NVflag = new NHANVIENModel();
        inforNV();
        for (NHANVIENModel i_NV : DSNV) {
            if(i_NV.getMaNV().indexOf(a) != -1){
                NVflag = i_NV;
                break;
            }
        }
        return NVflag;
    }
    
    public CHUCVUModel LoadCV(String a)throws SQLException, ClassNotFoundException {
        CVflag = new CHUCVUModel();
        inforCV();
        for (CHUCVUModel i_CV : DSCV) {
            if(i_CV.getMACHUCVU().indexOf(a) != -1){
                CVflag = i_CV;
                break;
            }
        }
        return CVflag;
    }
    
    public void SaveNV(ActionEvent e) throws IOException, ClassNotFoundException, SQLException, DocumentException {
        Component component = (Component) e.getSource();
        Quanlynhanvien fr = (Quanlynhanvien) SwingUtilities.getRoot(component);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String bdate = sdf.format(fr.getNgaySinh().getDate());
        String wdate = sdf.format(fr.getNgayVaoLam().getDate());
        String query = "insert into NHANVIEN values ('" + fr.getMaNV().getText()+ "',N'" + fr.getTen().getText()+ "','" + bdate +"','" + fr.getGioiTinh().getText()+"','" + fr.getDiaChi().getText()+"','" + fr.getMaChucVu().getText()+"','" + wdate + "','" + fr.getSDT().getText()+"','" + fr.getMatKhau().getText()+"','" + 1 +"','" + fr.getGhiChu().getText()+"')";
        MSSQLControl kn = new MSSQLControl();
        try {
            Connection cnn = (Connection) kn.getConnect();
            int st = cnn.createStatement().executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Thêm thành công");
        } catch (SQLException ex) {
            Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Sai dữ liệu");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public void EditNV(ActionEvent e) throws IOException, ClassNotFoundException, SQLException, DocumentException {
        Component component = (Component) e.getSource();
        Quanlynhanvien fr = (Quanlynhanvien) SwingUtilities.getRoot(component);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String bdate = sdf.format(fr.getNgaySinh().getDate());
        String wdate = sdf.format(fr.getNgayVaoLam().getDate());
        String query = "Update NHANVIEN set TenNV = N'"+ fr.getTen().getText()+"',NgaySinh = '"+bdate+"',DiaChi = '"+fr.getDiaChi().getText()+"',MaChucVu = '"+fr.getMaChucVu().getText()+"',GioiTinh = '"+fr.getGioiTinh().getText()+"',NgayVaoLam = '"+wdate+"',SDT = '"+fr.getSDT().getText()+"',MatKhau = '"+fr.getMatKhau().getText()+"',GhiChu = '"+fr.getGhiChu().getText()+"' where MaNV = '"+fr.getMaNV().getText()+"'";
        MSSQLControl kn = new MSSQLControl();
        try {
            Connection cnn = (Connection) kn.getConnect();
            int st = cnn.createStatement().executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Chỉnh sửa thành công");
        } catch (SQLException ex) {
            Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Sai dữ liệu");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //
        public void DeleteNV(ActionEvent e) throws IOException, ClassNotFoundException, SQLException, DocumentException {
        Component component = (Component) e.getSource();
        Quanlynhanvien fr = (Quanlynhanvien) SwingUtilities.getRoot(component);
        String query = "update NHANVIEN set TinhTrang = '" +0+ "' Where MaNV ='"+ fr.getMaNV().getText()+"'";
        MSSQLControl kn = new MSSQLControl();
        try {
            Connection cnn = (Connection) kn.getConnect();
            int st = cnn.createStatement().executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Xóa thành công");
        } catch (SQLException ex) {
            Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Sai dữ liệu");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //
    
    public void QuayLai(ActionEvent e) throws IOException, ClassNotFoundException, SQLException, DocumentException {
        Component component = (Component) e.getSource();
        Quanlynhanvien fr = (Quanlynhanvien) SwingUtilities.getRoot(component);
            fr.dispose();
        }
}

