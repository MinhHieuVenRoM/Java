/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;


import Control.GioLamViecControl;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Minh Hieu
 */
public class GioLamViec extends JFrame {
    GioLamViecControl controller;
    private JComboBox jcmanv;
    private JTable jtable;
    private DefaultTableModel defaultTableModelGioLV;
    
    public GioLamViec(String tennhanvien,int capbac) throws SQLException, ClassNotFoundException
    {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GioLamViec.class.getName()).log(Level.SEVERE, null, ex);
        }
        //JFRAME
        this.setTitle("Quản lý Doanh Thu");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.setLayout(null);
        this.setResizable(false);
        
         controller= new GioLamViecControl();
        
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
        
        //BANG QUAN LY GIỜ LÀM VIỆC
        JLabel jlQLGLV = new JLabel("BẢNG QUẢN LÝ GIỜ LÀM VIỆC");
        jlQLGLV.setFont(new Font("TimesRoman", Font.BOLD, 29));
        jlQLGLV.setLocation(328, 26);
        jlQLGLV.setSize(453, 64);
        this.add(jlQLGLV);

        JPanel QLGLV = new JPanel();
        QLGLV.setBounds(17, 121, 967, 485);
        QLGLV.setLayout(null);
        QLGLV.setBackground(Color.decode("#99CCCC"));
        
        //------------------------------------------------
        //Khung nhập
        JPanel QLGLV_Nhap = new JPanel();
        QLGLV_Nhap.setLayout(null);
        QLGLV_Nhap.setBounds(20, 25, 513, 385);
        //QLHD_Nhap.setBackground(Color.decode("#99CCCC"));
        
        
        
        JLabel jlmanv = new JLabel("Mã NV");
        jlmanv.setBounds(37, 42, 55, 38);
        QLGLV_Nhap.add(jlmanv);
        jcmanv = new JComboBox(controller.layManhanvien());
        jcmanv.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    defaultTableModelGioLV = controller.themgiolvvaobang(jcmanv.getSelectedItem().toString());
                    jtable.setModel(defaultTableModelGioLV);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        
        jcmanv.setBounds(92,48,104,25);
        QLGLV_Nhap.add(jcmanv);
        
        //label Ngày
        JLabel jlngay = new JLabel("Ngày");
        jlngay.setBounds(264, 43, 52, 38);
        QLGLV_Nhap.add(jlngay);
        //date picker
        JDateChooser jdngay = new JDateChooser();
        jdngay.setBounds(316, 48, 174, 26);
        jdngay.setDateFormatString("yyyy-MM-dd");
        QLGLV_Nhap.add(jdngay);
        
        //label giờ bắt đầu + combobox Ca
        JLabel jlgbd = new JLabel("Ca làm");
        jlgbd.setBounds(165, 104, 60, 38);
        QLGLV_Nhap.add(jlgbd);
        JComboBox jcca = new JComboBox();
        jcca.addItem("Ca sáng");
        jcca.addItem("Ca chiều");
        jcca.addItem("Ca tối");
        jcca.addItem("Ca khuya");
        jcca.setBounds(225,110,104,25);
        QLGLV_Nhap.add(jcca);
        
        
        //button Thêm
        JButton jbThem = new JButton("THÊM");
        jbThem.setFont(new Font("TimesRoman", Font.BOLD, 17));
        jbThem.setForeground(Color.decode("#009999"));
        jbThem.setBounds(151, 184, 174, 77);
        jbThem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String manv = (String) jcmanv.getSelectedItem();
                String ca = (String) jcca.getSelectedItem();
                DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
                    
                String ngaylam = df.format(jdngay.getDate());
                System.out.println(manv+ca+ngaylam);
                try {
                    controller.themca(e, manv, ca, ngaylam);

                } catch (IOException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(GioLamViec.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        QLGLV_Nhap.add(jbThem);

        QLGLV.add(QLGLV_Nhap);
        
        //-------------------------------------------------
        //Tạo bảng
                
        jtable = new JTable();
        
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        jtable.setModel(defaultTableModel);
        JScrollPane jScrollPane = new JScrollPane(jtable);
        
        jScrollPane.setBounds(555, 25, 389, 385);
        QLGLV.add(jScrollPane);
        
        
        
        this.add(QLGLV);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

    public JComboBox getjcmanv() {
        return jcmanv;
    }
    
}
