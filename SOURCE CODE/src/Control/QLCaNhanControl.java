/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.CHUCVUModel;
//import Model.CaNhanModel;
import Model.NHANVIENModel;
import View.Quanlynhanvien;
import View.Quanlytinthongcanhan;
import com.itextpdf.text.DocumentException;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Thinh
 */
public class QLCaNhanControl {
        private NHANVIENModel NV;
        private ArrayList<CHUCVUModel> DSCV;
        private CHUCVUModel CVflag;
        
        public NHANVIENModel inforNV(String a) throws SQLException, ClassNotFoundException{
            NV = new Model.NHANVIENModel();
            NHANVIENModel CaNhan = new NHANVIENModel();
            NV = CaNhan.laythongtincanhan(a);
            return NV;
        }
        
        public void inforCV() throws SQLException, ClassNotFoundException{
        DSCV = new ArrayList<>();
        CHUCVUModel cv = new CHUCVUModel();
        DSCV = cv.layDanhsachchucvu();
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
        
        
        public void QuayLai(ActionEvent e) throws IOException, ClassNotFoundException, SQLException, DocumentException {
        Component component = (Component) e.getSource();
        Quanlytinthongcanhan fr = (Quanlytinthongcanhan) SwingUtilities.getRoot(component);
        fr.dispose();
    }
        
        
        public void Update(ActionEvent e) throws IOException, ClassNotFoundException, SQLException, DocumentException {
            Component component = (Component) e.getSource();
            Quanlytinthongcanhan fr = (Quanlytinthongcanhan) SwingUtilities.getRoot(component);
            if (fr.getMatKhauMoi().getText().equals(fr.getNhapLai().getText()) == true & fr.getMatKhauMoi().getText().isEmpty()!=true& fr.getNhapLai().getText().isEmpty()!=true) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String bdate = sdf.format(fr.getNgaySinh().getDate());
                String wdate = sdf.format(fr.getNgayVaoLam().getDate());
                String query = "Update NHANVIEN set TenNV = N'"+ fr.getTen().getText()+"',NgaySinh = '"+bdate+"',NgayVaoLam = '"+wdate+"',SDT = '"+fr.getSDT().getText()+"',MatKhau = '"+fr.getMatKhauMoi().getText()+"',GhiChu = N'"+fr.getGhiChu().getText()+"' where MaNV = '"+fr.getMaNV().getText()+"'";
                MSSQLControl kn = new MSSQLControl();
                try {
                    Connection cnn = (Connection) kn.getConnect();
                    int st = cnn.createStatement().executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Chỉnh sửa thông tin cá nhân thành công");
                } catch (SQLException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Sai dữ liệu");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                }             
            } else {
                    if(fr.getMatKhauMoi().getText().equals(fr.getNhapLai().getText()) != true){
                        JOptionPane.showMessageDialog(null, "Mật khẩu nhập lại không đúng");
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Không được để trống mật khẩu");
                    }
                }
        }
}

