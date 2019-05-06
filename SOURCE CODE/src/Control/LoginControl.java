/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.CHUCVUModel;
import Model.NHANVIENModel;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import View.Login;
import View.ManHinhChinh;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Minh Hieu
 */
public class LoginControl {

    private static Connection connection;
    private  ArrayList<NHANVIENModel> dsnv;
    private  ArrayList<CHUCVUModel> dscv;

    public void ketnoi(ActionEvent e, NHANVIENModel userModel) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        Login fr = (Login) SwingUtilities.getRoot(component);
        try {
            //kiem tra co manv vs dung mat khau khong
            if (requestLogin(userModel) == true) {
                try {
                    fr.dispose();
                    //truyền vào manhinhchinh với Mã Nhân viên và cấp bậc(quản lý 1, nhan vien 2) vào
                    ManHinhChinh manHinhChinh = new ManHinhChinh(userModel.getMaNV(), kiemtraquyenLogin(userModel));
                } catch (IOException ex) {
                    Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                JOptionPane.showMessageDialog(null, "Lỗi sai tài khoản hoặc mật khẩu");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void AnHienPass(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        Login fr = (Login) SwingUtilities.getRoot(component);
        if (fr.getCheckBox().isSelected()) {
            fr.getCheckBox().setText("Ẩn Mật Khẩu");
            fr.getPassword().setEchoChar((char) 0);
        } else {
            fr.getCheckBox().setText("HiệnMật Khẩu");
            fr.getPassword().setEchoChar('*');
        }

    }

    public boolean requestLogin(NHANVIENModel user) throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        try {
            NHANVIENModel nv = new NHANVIENModel();
            dsnv = new ArrayList<>();
            dsnv = nv.layThongtinnhanvien();
            for (NHANVIENModel tam : dsnv) {
                if ((tam.getMaNV().equals(user.getMaNV())) && tam.getMatKhau().equals(user.getMatKhau())) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.toString());
            connection.close();
        }
        return false;
    }

    public int kiemtraquyenLogin(NHANVIENModel user) throws SQLException, ClassNotFoundException {
        NHANVIENModel nv = new NHANVIENModel();
        dsnv = new ArrayList<>();
        dsnv = nv.layThongtinnhanvien();
        for (NHANVIENModel tam : dsnv) {
            if ((tam.getMaNV().equals(user.getMaNV())) && KiemTraQuanLy(tam.getMaChucVu()) == true) {
                return 1;
            }
        }

        return 2;
    }

    public boolean KiemTraQuanLy(String machucvu) throws SQLException, ClassNotFoundException {
        CHUCVUModel cv = new CHUCVUModel();
        dscv = new ArrayList<>();
        dscv = cv.layDanhsachchucvu();
        String tenChucVu = "";
        for (CHUCVUModel tam : dscv) {
            if (tam.getMACHUCVU().equals(machucvu)) {
                tenChucVu = tam.getTENCHUCVU();
            }

        }
        int kiemTra = tenChucVu.indexOf("Quản lý");
        if (kiemTra == 0) {
            return true;
        } else {
            return false;
        }
    }
}
