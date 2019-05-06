/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.MSSQLControl;
import Control.QLCaNhanControl;
import Model.CHUCVUModel;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
//import Model.CaNhanModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import Model.NHANVIENModel;
import com.itextpdf.text.DocumentException;

/**
 *
 * @author Minh Hieu
 */
public class Quanlytinthongcanhan extends JFrame{

    private JPanel WatchPanel, ChangePanel, ContactPanel, PassChangePanel;
    JTabbedPane PersInforTabbPane;
    JLabel wp_MaNVlb,pcp_RetypePWlb, wp_Namelb,pcp_NewPasslb,pcp_IDlb,pcp_IDif,cp_Daytoworklb,cp_Positonlb,cp_SDTlb, wp_adrlb,wp_Birthdaylb,wp_Daytoworklb,cp_Notelb, wp_Positionlb, wp_IDlb, wp_Notelb,ctp_adrlb,ctp_SDTlb, cp_IDlb, cp_Namelb, cp_Birthdaylb;
    JTextField wp_MaNVtf, wp_Nametf, wp_Positiontf, wp_IDtf, ctp_adrtf,ctp_SDTtf, cp_IDtf, cp_Nametf, cp_Positontf,cp_SDTtf;
    JTextArea wp_Noteta, cp_Noteta;
    JDateChooser wp_Birthdayf, wp_Daytoworkf, cp_Birthdayf, cp_Daytoworkf;
    JButton cp_Resetbt, cp_updatebt, pcp_CheckPassbt;
    JPasswordField pcp_NewPasspf, pcp_RetypePWpf;
    NHANVIENModel NV;
    QLCaNhanControl controller;

    public Quanlytinthongcanhan(String MANV, int Capbac) throws SQLException, ClassNotFoundException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setSize(1000, 650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);

        NV = new NHANVIENModel();
        controller = new QLCaNhanControl();
        NV = controller.inforNV(MANV);

        CHUCVUModel check = controller.LoadCV(NV.getMaChucVu());

        JLabel name = new JLabel("Quản Lý Thông Tin Cá Nhân");
        name.setBounds(400, 10, 250, 40);
        name.setForeground(Color.red);
        name.setFont(new Font("TimesRoman", Font.BOLD, 18));
        this.add(name);
        PersInforTabbPane = new JTabbedPane();
        /* create 2 JPanel, which is content of tabs */

        WatchPanel = new JPanel();
        WatchPanel.setLayout(null);

        wp_MaNVlb = new JLabel("*Mã Nhân viên:");
        wp_MaNVlb.setBounds(10, 10, 90, 30);

        wp_MaNVtf = new JTextField();
        wp_MaNVtf.setBounds(100, 10, 110, 25);
        wp_MaNVtf.setFont(new Font("TimesRoman", Font.BOLD, 15));
        wp_MaNVtf.setForeground(Color.red);
        wp_MaNVtf.setText(NV.getMaNV());
        wp_MaNVtf.setEditable(false);

        wp_Namelb = new JLabel("Họ và tên");
        wp_Namelb.setBounds(220, 10, 110, 30);

        wp_Nametf = new JTextField();
        wp_Nametf.setBounds(280, 10, 160, 25);
        wp_Nametf.setFont(new Font("TimesRoman", Font.ITALIC, 14));
        wp_Nametf.setForeground(Color.black);
        wp_Nametf.setText(NV.getTenNV());
        wp_Nametf.setEditable(false);

        wp_Birthdaylb = new JLabel("Ngày Sinh");
        wp_Birthdaylb.setBounds(10, 50, 100, 25);
        wp_Birthdayf = new JDateChooser();
        wp_Birthdayf.setBounds(100, 50, 150, 25);
        wp_Birthdayf.setDate(NV.getNgaySinh());

        wp_Daytoworklb = new JLabel("Ngày vào làm");
        wp_Daytoworklb.setBounds(10, 100, 100, 25);
        wp_Daytoworkf = new JDateChooser();
        wp_Daytoworkf.setBounds(100, 100, 150, 25);
        wp_Daytoworkf.setDate(NV.getNgayVaoLam());

        wp_Positionlb = new JLabel("*Chức vụ");
        wp_Positionlb.setBounds(260, 100, 110, 30);
        wp_Positiontf = new JTextField();
        wp_Positiontf.setBounds(320, 102, 130, 25);
        wp_Positiontf.setForeground(Color.red);
        wp_Positiontf.setText(check.getTENCHUCVU());
        wp_Positiontf.setEditable(false);

        wp_IDlb = new JLabel("*Tên đăng nhập");
        wp_IDlb.setBounds(10, 160, 110, 30);
        wp_IDtf = new JTextField();
        wp_IDtf.setBounds(110, 160, 130, 25);
        wp_IDtf.setText(NV.getMaNV());
        wp_IDtf.setEditable(false);

        wp_Notelb = new JLabel("Chú thích");
        wp_Notelb.setBounds(10, 220, 110, 30);

        wp_Noteta = new JTextArea();
        wp_Noteta.setBounds(10, 250, 500, 250);
        wp_Noteta.setText(NV.getGhiChu());
        wp_Noteta.setEditable(false);

        ContactPanel = new JPanel();
        ContactPanel.setLayout(null);

        JLabel NamelbContact = new JLabel("Thông tin liên lạc");
        NamelbContact.setBounds(100, 5, 150, 30);
        NamelbContact.setFont(new Font("TimesRoman", Font.BOLD, 15));
        NamelbContact.setForeground(Color.red);

//        JLabel manvt = new JLabel("*Mã Nhân viên:");
//        manvt.setBounds(5, 50, 100, 30);
//        JTextField textmanvt = new JTextField();
//        textmanvt.setBounds(100, 50, 100, 25);
        ctp_adrlb = new JLabel("Địa chỉ:");
        ctp_adrlb.setBounds(5, 100, 100, 30);
        ctp_adrtf = new JTextField();
        ctp_adrtf.setBounds(70, 100, 180, 25);
        ctp_adrtf.setText(NV.getDiaChi());
        ctp_adrtf.setEditable(false);

        ctp_SDTlb = new JLabel("Số điện thoại");
        ctp_SDTlb.setBounds(5, 150, 100, 30);
        ctp_SDTtf = new JTextField();
        ctp_SDTtf.setBounds(100, 150, 150, 25);
        ctp_SDTtf.setText(NV.getSDT());
        ctp_SDTtf.setEditable(false);


        ContactPanel.add(NamelbContact);
        ContactPanel.add(ctp_adrlb);
        ContactPanel.add(ctp_SDTlb);
        ContactPanel.add(ctp_SDTtf);
        ContactPanel.add(ctp_adrtf);
        ContactPanel.setBounds(650, 10, 300, 300);
        ContactPanel.setBackground(Color.PINK);
        WatchPanel.add(ContactPanel);

        WatchPanel.setBackground(Color.decode("#99CCCC"));
        WatchPanel.add(wp_IDlb);
        WatchPanel.add(wp_Notelb);
        WatchPanel.add(wp_IDtf);
        WatchPanel.add(wp_MaNVlb);
        WatchPanel.add(wp_MaNVtf);
        WatchPanel.add(wp_Namelb);
        WatchPanel.add(wp_Nametf);
        WatchPanel.add(wp_Birthdaylb);
        WatchPanel.add(wp_Birthdayf);
        WatchPanel.add(wp_Daytoworklb);
        WatchPanel.add(wp_Daytoworkf);
        WatchPanel.add(wp_Positionlb);
        WatchPanel.add(wp_Positiontf);
        WatchPanel.add(wp_Noteta);

        ChangePanel = new JPanel();
        ChangePanel.setLayout(null);

        cp_IDlb = new JLabel("*Mã Nhân viên:");
        cp_IDlb.setBounds(10, 10, 90, 30);
        cp_IDtf = new JTextField();
        cp_IDtf.setBounds(100, 10, 110, 25);
        cp_IDtf.setFont(new Font("TimesRoman", Font.BOLD, 14));
        cp_IDtf.setForeground(Color.red);
        cp_IDtf.setText(NV.getMaNV());
        cp_IDtf.setEditable(false);

        cp_Namelb = new JLabel("Họ và tên");
        cp_Namelb.setBounds(220, 10, 110, 30);
        cp_Nametf = new JTextField();
        cp_Nametf.setFont(new Font("TimesRoman", Font.BOLD, 14));
        cp_Nametf.setForeground(Color.black);
        cp_Nametf.setBounds(280, 10, 160, 25);
        cp_Nametf.setText(NV.getTenNV());

        cp_Birthdaylb = new JLabel("Ngày Sinh");
        cp_Birthdaylb.setBounds(10, 50, 100, 25);
        cp_Birthdayf = new JDateChooser();
        cp_Birthdayf.setBounds(100, 50, 150, 25);
        cp_Birthdayf.setDate(NV.getNgaySinh());

        cp_Daytoworklb = new JLabel("Ngày vào làm");
        cp_Daytoworklb.setBounds(10, 100, 100, 25);
        cp_Daytoworkf = new JDateChooser();
        cp_Daytoworkf.setBounds(100, 100, 150, 25);
        cp_Daytoworkf.setDate(NV.getNgayVaoLam());

        cp_Positonlb = new JLabel("*Chức vụ");
        cp_Positonlb.setBounds(260, 100, 110, 30);
        cp_Positontf = new JTextField();
        cp_Positontf.setBounds(320, 100, 130, 25);
        cp_Positontf.setFont(new Font("TimesRoman", Font.ITALIC, 14));
        cp_Positontf.setForeground(Color.red);
        cp_Positontf.setText(check.getTENCHUCVU());
        cp_Positontf.setEditable(false);

        cp_SDTlb = new JLabel("Số điện thoại");
        cp_SDTlb.setBounds(10, 160, 110, 30);
        cp_SDTtf = new JTextField();
        cp_SDTtf.setBounds(100, 160, 130, 25);
        cp_SDTtf.setText(NV.getSDT());

        cp_Notelb = new JLabel("Chú thích");
        cp_Notelb.setBounds(10, 220, 110, 30);

        cp_Resetbt = new JButton("Reset");
        cp_Resetbt.setBounds(100, 500, 100, 30);

        cp_updatebt = new JButton("Update");
        cp_updatebt.setBounds(250, 500, 100, 30);
        //

        //
        cp_Noteta = new JTextArea();
        cp_Noteta.setBounds(10, 250, 500, 220);
        cp_Noteta.setText(NV.getGhiChu());

        PassChangePanel = new JPanel();
        PassChangePanel.setLayout(null);

        JLabel cp_NameChangePasslb = new JLabel("Thay đổi mật khẩu:");
        cp_NameChangePasslb.setBounds(110, 5, 150, 30);
        cp_NameChangePasslb.setFont(new Font("TimesRoman", Font.BOLD, 15));
        cp_NameChangePasslb.setForeground(Color.red);

        pcp_IDlb = new JLabel("*Tên đăng nhập:");
        pcp_IDlb.setBounds(5, 50, 100, 30);
        pcp_IDif = new JLabel();
        pcp_IDif.setBounds(110, 53, 100, 25);
        pcp_IDif.setFont(new Font("TimesRoman", Font.BOLD, 14));
        pcp_IDif.setForeground(Color.BLACK);
        pcp_IDif.setText(NV.getMaNV());

        pcp_NewPasslb = new JLabel("Mật khẩu mới");
        pcp_NewPasslb.setBounds(5, 100, 120, 30);
        pcp_NewPasslb.setFont(new Font("TimesRoman", Font.BOLD, 14));
        pcp_NewPasspf = new JPasswordField();
        pcp_NewPasspf.setBounds(110, 100, 120, 25);

        pcp_RetypePWlb = new JLabel("Nhập lại");
        pcp_RetypePWlb.setBounds(5, 130, 100, 30);
        pcp_RetypePWlb.setFont(new Font("TimesRoman", Font.BOLD, 14));
        pcp_RetypePWpf = new JPasswordField();
        pcp_RetypePWpf.setBounds(110, 130, 120, 25);

        pcp_CheckPassbt = new JButton("Check pass");
        pcp_CheckPassbt.setBounds(100, 230, 100, 30);
        //
        pcp_CheckPassbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (pcp_NewPasspf.getText().equals(pcp_RetypePWpf.getText()) != true) {
                        JOptionPane.showMessageDialog(null, "Mật khẩu nhập lại không đúng");
                    pcp_NewPasspf.setText("");
                    pcp_RetypePWpf.setText("");
                } else {
                    if(pcp_NewPasspf.getText().isEmpty() != true){
                        JOptionPane.showMessageDialog(null, "OK!");
                    }else{
                        JOptionPane.showMessageDialog(null, "Không được để trống mật khẩu");
                    }
                }
            }
        });
        //
        cp_updatebt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.Update(e);
                } catch (IOException ex) {
                    Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
                }
                    pcp_NewPasspf.setText("");
                    pcp_RetypePWpf.setText("");
            }
        });
        
        
        cp_Resetbt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pcp_NewPasspf.setText("");
                pcp_RetypePWpf.setText("");
                cp_SDTtf.setText("");
                cp_Nametf.setText("");
                cp_Noteta.setText("");
            }
        });
        //
        JLabel rulelb = new JLabel("(*)Không thể thay đổi");
        rulelb.setBounds(650, 320, 200, 30);

        PassChangePanel.add(pcp_RetypePWlb);
        PassChangePanel.add(pcp_RetypePWpf);
        PassChangePanel.add(cp_NameChangePasslb);
        PassChangePanel.add(pcp_IDlb);
        PassChangePanel.add(pcp_IDif);
        PassChangePanel.add(pcp_NewPasslb);
        PassChangePanel.add(pcp_NewPasspf);
        PassChangePanel.add(pcp_CheckPassbt);
        PassChangePanel.setBounds(650, 10, 300, 300);
        PassChangePanel.setBackground(Color.PINK);
        ChangePanel.add(PassChangePanel);

        ChangePanel.setBackground(Color.decode("#99CCCC"));
        ChangePanel.add(cp_SDTlb);
        ChangePanel.add(cp_Notelb);
        ChangePanel.add(cp_SDTtf);
        ChangePanel.add(cp_IDlb);
        ChangePanel.add(cp_IDtf);
        ChangePanel.add(cp_Namelb);
        ChangePanel.add(cp_Nametf);
        ChangePanel.add(cp_Birthdaylb);
        ChangePanel.add(cp_Birthdayf);
        ChangePanel.add(cp_Daytoworklb);
        ChangePanel.add(cp_Daytoworkf);
        ChangePanel.add(cp_Positonlb);
        ChangePanel.add(cp_Positontf);
        ChangePanel.add(cp_Noteta);
        ChangePanel.add(cp_Resetbt);
        ChangePanel.add(cp_updatebt);
        ChangePanel.add(rulelb);
        /* add three tab with three JPanel */
        PersInforTabbPane.addTab("Thông tin cá nhân", null, WatchPanel, "click to show panel xem thông tin");
        PersInforTabbPane.addTab("Thay đổi thông tin", null, this.ChangePanel, "click to show panel đổi thông tin");
        JPanel x = new JPanel();
        x.setLayout(new BorderLayout());
        x.add(PersInforTabbPane, BorderLayout.CENTER);
        x.setBounds(2, 50, 990, 600);

        JButton quaylaiButton = new JButton("Quay lại");
        quaylaiButton.setBounds(10, 20, 80, 30);
        quaylaiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.QuayLai(e);
                    new ManHinhChinh(MANV, Capbac);
                } catch (IOException ex) {
                    Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.add(quaylaiButton);
        this.add(x);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JTextField getMaNV(){
        return cp_IDtf;
    }
    public JTextField getTen(){
    return cp_Nametf;
    }
    public JDateChooser getNgaySinh(){
        return cp_Birthdayf;
    }
    public JDateChooser getNgayVaoLam(){
        return cp_Daytoworkf;
    }
    
    public JTextField getSDT(){
        return cp_SDTtf;
    }
    
    public JTextArea getGhiChu(){
        return cp_Noteta;
    }
    
    public JPasswordField getMatKhauMoi(){
        return pcp_NewPasspf;
    }
    
    public JPasswordField getNhapLai(){
        return pcp_RetypePWpf;
    }
    
}
