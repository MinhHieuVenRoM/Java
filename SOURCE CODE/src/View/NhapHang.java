/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.NhapHangControl;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class NhapHang {

    private JFrame f;

    private JTextField jtf_MaDH, jtf_TenSP, jtf_MaSP, jtf_DonGiaNhap, jtf_SoLuong;
    private JDateChooser jdc_NgayNhap;
    private JTable jt_SanPham;
    private DefaultTableModel dtm_SanPham;
    private JButton jb_ThemSP, jb_XoaSP, jb_Huy, jb_NhapHang;
    private NhapHangControl controller;
    private JLabel jl_TongGiaTri;

    public NhapHang(String MANV, int CapBac) throws SQLException, ClassNotFoundException {
        controller = new NhapHangControl();
        f = new JFrame();
        f.getContentPane().setBackground(Color.WHITE); //Set mau nen cho ca frame
        f.setTitle("Nhập hàng");
        f.setLayout(null);//Khong su dung Layout Manager  
        f.setSize(894, 438);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel NhapHang = new JLabel("NHẬP HÀNG");
        NhapHang.setBounds(373, 14, 138, 34);
        Font fontNhapHang = new Font("System", Font.BOLD, 22);
        NhapHang.setFont(fontNhapHang);

        //-------------------------------------------------------------------------
        //Label ma don hang
        JLabel jl_MaDH = new JLabel("Mã đơn hàng:");
        jl_MaDH.setFont(new Font("System", Font.BOLD, 12));
        jl_MaDH.setBounds(205, 86, 82, 17);
        //TextField ma don hang
        jtf_MaDH = new JTextField("");
        jtf_MaDH.setBounds(291, 82, 173, 25);
        jtf_MaDH.setEditable(false);
        String madh = controller.LoadMaDH();
        jtf_MaDH.setText(madh);

        //Label Ngay nhap
        JLabel jl_NgayNhap = new JLabel("Ngày nhập:");
        jl_NgayNhap.setFont(new Font("System", Font.BOLD, 12));
        jl_NgayNhap.setBounds(479, 86, 72, 17);
        //date choose Ngay nhap
        jdc_NgayNhap = new JDateChooser();
        jdc_NgayNhap.setFont(new Font("System", Font.PLAIN, 12));
        jdc_NgayNhap.setBounds(565, 82, 173, 26);
        jdc_NgayNhap.setDateFormatString("dd/MM/yyyy");

        //-------------------------------------------------------------------------
        //Label bang SP
        JLabel jl_DSSP = new JLabel("Danh sách sản phẩm");
        jl_DSSP.setFont(new Font("System", Font.BOLD, 12));
        jl_DSSP.setBounds(19, 119, 122, 17);

        dtm_SanPham = new DefaultTableModel();

        dtm_SanPham.addColumn("STT");
        dtm_SanPham.addColumn("Mã SP");
        dtm_SanPham.addColumn("Tên SP");
        dtm_SanPham.addColumn("Loại");
        dtm_SanPham.addColumn("Đơn giá");
        dtm_SanPham.addColumn("SL");
        dtm_SanPham.addColumn("Thành tiền");

        jt_SanPham = new JTable();
        jt_SanPham.setModel(dtm_SanPham);

        JScrollPane jsp_SanPham = new JScrollPane(jt_SanPham);
        jsp_SanPham.setBounds(19, 144, 446, 198);

        //-------------------------------------------------------------------------
        //Label them sp
        JLabel jl_ThemSP = new JLabel("Thêm sản phẩm");
        jl_DSSP.setFont(new Font("System", Font.BOLD, 12));
        jl_DSSP.setBounds(479, 119, 130, 17);

        JPanel jp_ThemSP = new JPanel();
        jp_ThemSP.setBounds(478, 144, 383, 198);
        jp_ThemSP.setLayout(null);
        jp_ThemSP.setBackground(Color.white);
        jp_ThemSP.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        //------------------------------------------------------
        //Label maSP
        JLabel jl_MaSP = new JLabel("Mã SP:");
        jl_MaSP.setFont(new Font("System", Font.BOLD, 12));
        jl_MaSP.setBounds(259, 28, 42, 17);
        jp_ThemSP.add(jl_MaSP);
        //TextField maSP
        jtf_MaSP = new JTextField("");
        jtf_MaSP.setBounds(302, 24, 54, 25);
        jp_ThemSP.add(jtf_MaSP);
        jtf_MaSP.addActionListener((ActionEvent e) -> {
            try {
                controller.TuDongNhapTenSP(jtf_TenSP, jtf_MaSP);
            } catch (SQLException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Label so luong
        JLabel jl_SL = new JLabel("Số lượng:");
        jl_SL.setFont(new Font("System", Font.BOLD, 12));
        jl_SL.setBounds(243, 65, 57, 17);
        jp_ThemSP.add(jl_SL);
        //TextField so luong
        jtf_SoLuong = new JTextField("");
        jtf_SoLuong.setBounds(302, 61, 54, 25);
        jp_ThemSP.add(jtf_SoLuong);

        //Label ten san pham
        JLabel jl_TenSP = new JLabel("Tên SP:");
        jl_TenSP.setFont(new Font("System", Font.BOLD, 12));
        jl_TenSP.setBounds(19, 28, 43, 17);
        jp_ThemSP.add(jl_TenSP);
        //TextField ten san pham
        jtf_TenSP = new JTextField("");
        jtf_TenSP.setBounds(64, 24, 173, 25);
        jp_ThemSP.add(jtf_TenSP);
        jtf_TenSP.addActionListener((ActionEvent e) -> {
            try {
                controller.TuDongNhapMaSP(jtf_TenSP, jtf_MaSP);
            } catch (SQLException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Label don gia nhap
        JLabel jl_DonGiaNhap = new JLabel("Đơn giá nhập:");
        jl_DonGiaNhap.setFont(new Font("System", Font.BOLD, 12));
        jl_DonGiaNhap.setBounds(19, 65, 84, 17);
        jp_ThemSP.add(jl_DonGiaNhap);
        //TextField don gia nhap
        jtf_DonGiaNhap = new JTextField("");
        jtf_DonGiaNhap.setBounds(101, 61, 135, 25);
        jp_ThemSP.add(jtf_DonGiaNhap);

        //Button them
        jb_ThemSP = new JButton("Thêm");
        jb_ThemSP.setBounds(295, 143, 70, 33);
        jp_ThemSP.add(jb_ThemSP);

        jb_ThemSP.addActionListener((ActionEvent e) -> {
            try {
                controller.ThemSanPham(jtf_TenSP, jtf_MaSP, jtf_SoLuong, jtf_DonGiaNhap, dtm_SanPham, jl_TongGiaTri);
            } catch (SQLException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        //Button xoa
        jb_XoaSP = new JButton("Xoá");
        jb_XoaSP.setBounds(19, 143, 70, 33);
        jp_ThemSP.add(jb_XoaSP);
        jb_XoaSP.addActionListener((ActionEvent e) -> {
            controller.XoaSP(dtm_SanPham, jt_SanPham, jl_TongGiaTri);

        });

        //-------------------------------------------------------------------------
        //Button Nhap hang
        jb_NhapHang = new JButton("Nhập hàng");
        jb_NhapHang.setBounds(765, 355, 96, 33);
        jb_NhapHang.addActionListener((ActionEvent e) -> {
            try {
                controller.NhapHang(f, e, MANV, CapBac, jtf_MaDH, jdc_NgayNhap, dtm_SanPham);
            } catch (SQLException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Button Huy
        jb_Huy = new JButton("Huỷ");
        jb_Huy.setBounds(19, 355, 79, 33);
        jb_Huy.addActionListener((ActionEvent e) -> {
            try {
                controller.Huy(f, e, MANV, CapBac);
            } catch (SQLException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NhapHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //-------------------------------------------------------------------------
        //Label tong gia tri
        JLabel jl_TGT = new JLabel("Tổng giá trị:");
        jl_TGT.setFont(new Font("System", Font.BOLD, 14));
        jl_TGT.setBounds(479, 361, 85, 20);

        jl_TongGiaTri = new JLabel("0.0");
        jl_TongGiaTri.setFont(new Font("System", Font.PLAIN, 14));
        jl_TongGiaTri.setBounds(564, 361, 200, 20);

        f.add(NhapHang);

        f.add(jl_MaDH);
        f.add(jtf_MaDH);

        f.add(jl_NgayNhap);
        f.add(jdc_NgayNhap);

        f.add(jsp_SanPham);

        f.add(jp_ThemSP);

        f.add(jb_NhapHang);
        f.add(jb_Huy);

        f.add(jl_TGT);
        f.add(jl_TongGiaTri);

        f.setVisible(true);//Tao Frame la co the nhin thay (visible)  
        f.setLocationRelativeTo(null);
    }
}
