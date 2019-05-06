/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.DoanhThuControl;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh Hieu
 */
public class DoanhThu extends JFrame {

    private DefaultTableModel defaultTableModel;
    private JTextField jtngay;
    private JTable jtable;

    public JTable getJtable() {
        return jtable;
    }

    public JTextField getJtngay() {
        return jtngay;
    }

    DoanhThu(String tennhanvien, int capbac) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        Logger.getLogger(DoanhThu.class.getName()).log(Level.SEVERE, null, ex);
        }
        //JFRAME
        this.setTitle("Quản lý Doanh Thu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLayout(null);
        this.setResizable(false);

        //Controller
        DoanhThuControl controller = new DoanhThuControl();

        //button QUAY LAI
        JButton jbQuayLai = new JButton("Quay lại");
        jbQuayLai.setForeground(Color.decode("#00CC99"));
        jbQuayLai.setSize(107, 76);
        jbQuayLai.setLocation(17, 14);
        jbQuayLai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.QuayLai(e, tennhanvien, capbac);
                } catch (IOException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(CHINH_DOANHTHU.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.add(jbQuayLai);

        //BANG QUAN LY DOANH THU
        JLabel jlQLDT = new JLabel("BẢNG QUẢN LÝ DOANH THU");
        jlQLDT.setFont(new Font("TimesRoman", Font.BOLD, 29));
        jlQLDT.setLocation(328, 26);
        jlQLDT.setSize(403, 64);
        this.add(jlQLDT);

        JPanel QLDT = new JPanel();
        QLDT.setBounds(17, 121, 967, 485);
        QLDT.setLayout(null);
        QLDT.setBackground(Color.decode("#99CCCC"));

        //------------------------------------------------
        //Khung nhập
        JPanel QLDT_Nhap = new JPanel();
        QLDT_Nhap.setLayout(null);
        QLDT_Nhap.setBounds(21, 25, 920, 185);
        //QLHD_Nhap.setBackground(Color.decode("#99CCCC"));


        //label Từ ngày
        JLabel jltungay = new JLabel("Từ ngày");
        jltungay.setBounds(167, 78, 57, 38);
        QLDT_Nhap.add(jltungay);
        //date picker
        JDateChooser jdtn = new JDateChooser();
        jdtn.setDateFormatString("yyyy-MM-dd");
        jdtn.setBounds(229, 85, 174, 26);
        QLDT_Nhap.add(jdtn);

        //label Đến ngày
        JLabel jldenngay = new JLabel("Đến ngày");
        jldenngay.setBounds(506, 78, 64, 38);
        QLDT_Nhap.add(jldenngay);
        //date picker
        JDateChooser jddn = new JDateChooser();
        jddn.setBounds(579, 86, 174, 26);
        jddn.setDateFormatString("yyyy-MM-dd");
        QLDT_Nhap.add(jddn);

        //jtable
        JTable jtable = new JTable();
        defaultTableModel = controller.TaocotTableModel();
        jtable.setModel(defaultTableModel);
        JScrollPane jScrollPane = new JScrollPane(jtable);

        jScrollPane.setBounds(175, 244, 610, 169);
        QLDT.add(jScrollPane);

        //button Tìm kiếm
        JButton jbTimKiem = new JButton("Tìm Kiếm");
        jbTimKiem.setForeground(Color.decode("#009999"));
        jbTimKiem.setBounds(798, 71, 88, 52);
        QLDT_Nhap.add(jbTimKiem);
        jbTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
                    String ngaytu = df.format(jdtn.getDate());
                    String ngayden = df.format(jddn.getDate());

                    controller.thongkeDoanhthutheongay(e, ngaytu, ngayden);
                    defaultTableModel = controller.getDefaultTableModeldoanhthu();
                    jtable.setModel(defaultTableModel);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        QLDT.add(QLDT_Nhap);

        this.add(QLDT);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

}
