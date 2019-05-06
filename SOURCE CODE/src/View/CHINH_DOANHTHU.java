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
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Minh Hieu
 */
public class CHINH_DOANHTHU {
      /**
     * @param args the command line arguments
     */
    public CHINH_DOANHTHU(String tennhanvien,int capbac) throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CHINH_DOANHTHU.class.getName()).log(Level.SEVERE, null, ex);
        }
        MyIcon icon1 = new MyIcon("src/images/quanlyhoadon.png");
        MyIcon icon2 = new MyIcon("src/images/chithu.png");
        MyIcon icon3 = new MyIcon("src/images/tinhluong.png");
        MyIcon icon4 = new MyIcon("src/images/giolam.png");
        JFrame jf = new JFrame("Home");
        jf.setSize(700, 600);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.setLayout(null);
        
        JPanel panel2 = new JPanel(new GridLayout(2, 2));
        
        JButton quanlyHoaDon = new JButton("Quản Lý Hóa Đơn");
        quanlyHoaDon.setVerticalTextPosition(AbstractButton.BOTTOM);
        quanlyHoaDon.setHorizontalTextPosition(AbstractButton.CENTER);
        quanlyHoaDon.setForeground(Color.decode("#00CC99"));
        quanlyHoaDon.setFont(new Font("TimesRoman", Font.BOLD, 14));
        quanlyHoaDon.setIcon(icon1.icon);
        quanlyHoaDon.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new QuanlyHoadon(tennhanvien,capbac);
            }
        });
        panel2.add(quanlyHoaDon);
        
        JButton quanlyDoanhThu = new JButton("Quản Lý Doanh Thu");
        quanlyDoanhThu.setVerticalTextPosition(AbstractButton.BOTTOM);
        quanlyDoanhThu.setHorizontalTextPosition(AbstractButton.CENTER);
        quanlyDoanhThu.setForeground(Color.decode("#00CC99"));
        quanlyDoanhThu.setFont(new Font("TimesRoman", Font.BOLD, 14));
        quanlyDoanhThu.setIcon(icon2.icon);
        quanlyDoanhThu.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new DoanhThu(tennhanvien,capbac);
            }
        });
        panel2.add(quanlyDoanhThu);
        
        JButton quanlyTinhLuong = new JButton("Quản Lý Tính Lương");
        quanlyTinhLuong.setVerticalTextPosition(AbstractButton.BOTTOM);
        quanlyTinhLuong.setHorizontalTextPosition(AbstractButton.CENTER);
        quanlyTinhLuong.setForeground(Color.decode("#00CC99"));
        quanlyTinhLuong.setFont(new Font("TimesRoman", Font.BOLD, 14));
        quanlyTinhLuong.setIcon(icon3.icon);
        quanlyTinhLuong.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                new TinhLuong(tennhanvien,capbac);
            }
        });
        panel2.add(quanlyTinhLuong);
        
        JButton quanlyGLV = new JButton("Quản Lý Giờ Làm Việc");
        quanlyGLV.setVerticalTextPosition(AbstractButton.BOTTOM);
        quanlyGLV.setHorizontalTextPosition(AbstractButton.CENTER);
        quanlyGLV.setForeground(Color.decode("#00CC99"));
        quanlyGLV.setFont(new Font("TimesRoman", Font.BOLD, 14));
        quanlyGLV.setIcon(icon4.icon);
        quanlyGLV.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
                try {
                    new GioLamViec(tennhanvien,capbac);
                } catch (SQLException ex) {
                    Logger.getLogger(CHINH_DOANHTHU.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(CHINH_DOANHTHU.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel2.add(quanlyGLV);
        
        //button Thoát
        JButton jbQuayLai = new JButton("Quay lại");
        jbQuayLai.setForeground(Color.decode("#009999"));
        jbQuayLai.setBounds(0, 10, 105, 40);
        jf.add(jbQuayLai);
        jbQuayLai.addActionListener(new ActionListener(){
         @Override
            public void actionPerformed(ActionEvent e) {
             try {
                 jf.dispose();
                 new ManHinhChinh(tennhanvien,1);
             } catch (IOException ex) {
                 Logger.getLogger(CHINH_DOANHTHU.class.getName()).log(Level.SEVERE, null, ex);
             }
            }
        
        
        });

        
        panel2.setBounds(50, 60, 600, 500);
        

        jf.add(panel2);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
    }
    
  
    
}
