/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.QuanLyHoaDonControl;
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
import javax.swing.JOptionPane;
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
public class QuanlyHoadon extends JFrame {

    private JButton jbQuayLai, jbXoa, jbxemcthd;
    private JLabel jlQLDT;
    private DefaultTableModel defaultTableModel;
    private JTextField jtngay;
    private JTable jtable;

    QuanlyHoadon(String tennhanvien, int capbac) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
        }
        //JFRAME
        this.setTitle("Quản lý Hóa đơn");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLayout(null);
        this.setResizable(false);

        //Controller
        QuanLyHoaDonControl controller = new QuanLyHoaDonControl();

        //button QUAY LAI
        jbQuayLai = new JButton("Quay lại");
        jbQuayLai.setForeground(Color.decode("#00CC99"));
        jbQuayLai.setSize(107, 76);
        jbQuayLai.setLocation(17, 14);
        jbQuayLai.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.QuayLai(e, tennhanvien, capbac);
                } catch (IOException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    new CHINH_DOANHTHU(tennhanvien, capbac);
                } catch (IOException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
        this.add(jbQuayLai);

        //BANG QUAN LY HÓA ĐƠN
        jlQLDT = new JLabel("BẢNG QUẢN LÝ HÓA ĐƠN");
        jlQLDT.setFont(new Font("TimesRoman", Font.BOLD, 29));
        jlQLDT.setLocation(328, 26);
        jlQLDT.setSize(403, 64);
        this.add(jlQLDT);

        JPanel QLHD = new JPanel();
        QLHD.setBounds(17, 121, 967, 485);
        QLHD.setLayout(null);
        QLHD.setBackground(Color.decode("#99CCCC"));

        //------------------------------------------------
        //Khung nhập
        JPanel QLHD_Nhap = new JPanel();
        QLHD_Nhap.setLayout(null);
        QLHD_Nhap.setBounds(21, 25, 920, 185);
        //QLHD_Nhap.setBackground(Color.decode("#99CCCC"));

        JLabel jlngay = new JLabel("Ngày/Tháng/Năm");
        jlngay.setBounds(280, 25, 150, 30);
        QLHD_Nhap.add(jlngay);
        jtngay = new JTextField();
        jtngay.setBounds(430, 25, 150, 30);
        jtngay.setPreferredSize(new Dimension(104, 25));
        QLHD_Nhap.add(jtngay);

        //label HOẶC
        JLabel jlhoac = new JLabel("HOẶC");
        jlhoac.setBounds(439, 78, 42, 38);
        QLHD_Nhap.add(jlhoac);

        //label Từ ngày
        JLabel jltungay = new JLabel("Từ ngày");
        jltungay.setBounds(164, 124, 64, 38);
        QLHD_Nhap.add(jltungay);
        //date picker
        JDateChooser jdtn = new JDateChooser();
        jdtn.setBounds(226, 131, 140, 26);
        jdtn.setDateFormatString("yyyy-MM-dd");
        QLHD_Nhap.add(jdtn);

        //label Đến ngày
        JLabel jldenngay = new JLabel("Đến ngày");
        jldenngay.setBounds(551, 124, 64, 38);
        QLHD_Nhap.add(jldenngay);
        //date picker
        JDateChooser jddn = new JDateChooser();
        jddn.setBounds(616, 131, 140, 26);
        jddn.setDateFormatString("yyyy-MM-dd");
        QLHD_Nhap.add(jddn);

        //Tạo bảng
        jtable = new JTable();
        jtable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.LayMAHD(e);
                int row = jtable.getSelectedRow();
                String mahoadon = jtable.getModel().getValueAt(row, 0).toString();
                try {
                    controller.ShowCTHD(mahoadon);
                } catch (SQLException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });

        defaultTableModel = controller.TaocoTableModel();
        jtable.setModel(defaultTableModel);
        JScrollPane jScrollPane = new JScrollPane(jtable);

        jScrollPane.setBounds(175, 244, 600, 169);
        QLHD.add(jScrollPane);

        //button Tìm kiếm
        JButton jbTimKiem = new JButton("Tìm Kiếm");
        jbTimKiem.setForeground(Color.decode("#009999"));
        jbTimKiem.setBounds(798, 71, 88, 52);
        QLHD_Nhap.add(jbTimKiem);
        jbTimKiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
                    String ngaytu = df.format(jdtn.getDate());
                    String ngayden = df.format(jddn.getDate());
                    controller.thongkeHoadontheongay(e, jtngay.getText(), ngaytu, ngayden);
                    defaultTableModel = controller.getDefaultTableModelhoadon();
                    jtable.setModel(defaultTableModel);
                    if (defaultTableModel.getRowCount() == 0) {
                        JOptionPane.showMessageDialog(null, "Không có hóa đơn trong thời gian này");

                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        QLHD.add(QLHD_Nhap);

        //button xoa
        jbXoa = new JButton("Xóa HD");
        jbXoa.setBounds(320, 430, 88, 52);
        jbXoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.xoaSanphamdachon(e);
                } catch (IOException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        QLHD.add(jbXoa);

        //button xem những hóa đơn đã xuất và được lưu trong máy nếu k có lưu thì sẽ k xem đc
        jbxemcthd = new JButton("In CTHD");
        jbxemcthd.setBounds(480, 430, 88, 52);
        jbxemcthd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.xemCTHD(e);
                } catch (IOException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(QuanlyHoadon.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        QLHD.add(jbxemcthd);
        this.add(QLHD);
        //-------------------------------------------------
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public JTable getJtable() {
        return jtable;
    }

    public JTextField getJtngay() {
        return jtngay;
    }

}
