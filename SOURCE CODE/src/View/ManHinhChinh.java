/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Minh Hieu
 */
public class ManHinhChinh {

    private JPanel panel1, panel2;

    public ManHinhChinh(String MaNV, int capbac) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);
        MyIcon icon1 = new MyIcon("src/images/ic_user_male_24.png");
        MyIcon icon2 = new MyIcon("src/images/ic_signout.png");
        MyIcon icon3 = new MyIcon("src/images/nhanvien.png");
        MyIcon icon4 = new MyIcon("src/images/ic_client.png");
        MyIcon icon5 = new MyIcon("src/images/ic_money_manager.png");
        MyIcon icon6 = new MyIcon("src/images/home_kavel_divers.png");
        MyIcon icon7 = new MyIcon("src/images/order.jpg");
        MyIcon icon8 = new MyIcon("src/images/dangxuat.png");
        MyIcon icon9 = new MyIcon("src/images/1.png");
        JFrame jf = new JFrame("Quản lý cửa hàng tiện lợi");
        jf.setSize(700, 600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLayout(null);
        //Panel  Hiện tên nhân viên và buton thoát
        panel1 = new JPanel();
        panel1.setLayout(new GridLayout(1, 2));
        JLabel tennv = new JLabel(MaNV, icon1.icon, JLabel.RIGHT);
        panel1.add(tennv);

        JButton logout = new JButton("Logout");
        logout.setIcon(icon2.icon);
        logout.setForeground(Color.BLACK);
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                try {
                    new Login();
                } catch (IOException ex) {
                    Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel1.add(logout);
        panel1.setBounds(500, 10, 195, 30);

        //Panel 2 chưa các nút dẫn đến chưc năng
        panel2 = new JPanel(new GridLayout(2, 3));
        JButton order = new JButton("Bán Hàng");
        order.setVerticalTextPosition(AbstractButton.BOTTOM);
        order.setHorizontalTextPosition(AbstractButton.CENTER);
        order.setForeground(Color.decode("#00CC99"));
        order.setFont(new Font("TimesRoman", Font.BOLD, 14));
        order.setIcon(icon7.icon);
        order.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                try {
                    new BanHang(tennv.getText(), capbac);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        panel2.add(order);
        JButton Nhanvien = new JButton("Nhân Viên");
        Nhanvien.setIcon(icon4.icon);
        Nhanvien.setVerticalTextPosition(AbstractButton.BOTTOM);
        Nhanvien.setHorizontalTextPosition(AbstractButton.CENTER);
        Nhanvien.setForeground(Color.decode("#00CC99"));
        Nhanvien.setFont(new Font("TimesRoman", Font.BOLD, 14));
        Nhanvien.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                try {
                    new Quanlytinthongcanhan(MaNV, capbac);
                } catch (SQLException ex) {
                    Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        panel2.add(Nhanvien);
        JButton quanlythuchi = new JButton("Quản Lý Thu Chi");
        quanlythuchi.setIcon(icon5.icon);
        quanlythuchi.setVerticalTextPosition(AbstractButton.BOTTOM);
        quanlythuchi.setHorizontalTextPosition(AbstractButton.CENTER);
        quanlythuchi.setForeground(Color.decode("#00CC99"));
        quanlythuchi.setFont(new Font("TimesRoman", Font.BOLD, 14));
        quanlythuchi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jf.dispose();
                    new CHINH_DOANHTHU(MaNV, capbac);
                } catch (IOException ex) {
                    Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        if (capbac == 1) {
            panel2.add(quanlythuchi);
        }

        JButton quanlykho = new JButton("Quản Lý Kho");
        quanlykho.setIcon(icon6.icon);
        quanlykho.setVerticalTextPosition(AbstractButton.BOTTOM);
        quanlykho.setHorizontalTextPosition(AbstractButton.CENTER);
        quanlykho.setForeground(Color.decode("#00CC99"));
        quanlykho.setFont(new Font("TimesRoman", Font.BOLD, 14));
        quanlykho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                try {
                    new GiaoDienKho(MaNV, capbac);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel2.add(quanlykho);

        JButton admin = new JButton("Quản Lý");
        admin.setVerticalTextPosition(AbstractButton.BOTTOM);
        admin.setHorizontalTextPosition(AbstractButton.CENTER);
        admin.setForeground(Color.decode("#00CC99"));
        admin.setFont(new Font("TimesRoman", Font.BOLD, 14));
        admin.setIcon(icon3.icon);
        admin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                try {
                    new Quanlynhanvien(MaNV, capbac);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ManHinhChinh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        if (capbac == 1) {
            panel2.add(admin);
        }

        JButton thoat = new JButton("Thoát");
        thoat.setIcon(icon8.icon);
        thoat.setVerticalTextPosition(AbstractButton.BOTTOM);
        thoat.setHorizontalTextPosition(AbstractButton.CENTER);
        thoat.setForeground(Color.decode("#00CC99"));
        thoat.setFont(new Font("TimesRoman", Font.BOLD, 14));
        thoat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        panel2.add(thoat);

        panel2.setBounds(50, 60, 600, 500);

        jf.add(panel1);
        jf.add(panel2);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);

    }

}
