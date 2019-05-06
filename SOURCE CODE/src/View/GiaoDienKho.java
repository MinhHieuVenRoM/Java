/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.KhoControl;
import Control.MSSQLControl;
import Model.CHITIETNHAPHANGModel;
import Model.DONNHAPHANGModel;
import Model.HOADONModel;
import Model.LOAISANPHAMModel;
import Model.NHANVIENModel;
import Model.SANPHAMModel;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh Hieu
 */
public class GiaoDienKho extends JScrollPane {

    JFrame f = new JFrame();//Tao instance cua JFrame 
    DefaultTableModel dtm_SP_SanPham;
    private ArrayList<SANPHAMModel> list_SanPham;
    private JTextField jtf_SP_MaSP, jtf_SP_SLTon, jtf_SP_TenSP, jtf_SP_DonGiaNhap, jtf_SP_DonGiaBan, jtf_SP_DonVi;
    private JComboBox jcb_SP_Loai;
    private JTable jt_SP_SanPham;
    
    
    DefaultTableModel dtm_DH_DonHang, dtm_DH_CTDH;
    private JTextField jtf_DH_MaDH, jtf_DH_TongGiaTri, jtf_DH_NgayNhap;
    private JTable jt_DH_DonHang, jt_DH_CTDH;
    private ArrayList<DONNHAPHANGModel> list_DonHang;
    private ArrayList<CHITIETNHAPHANGModel> list_CTNH;
    private KhoControl controller;
    private JButton jb_DH_NhapHang, jb_DH_Tim;
    private JDateChooser jdc_DH_Date;

    public GiaoDienKho(String MANV, int CapBac) throws SQLException, ClassNotFoundException {
        
        SANPHAMModel sanpham = new SANPHAMModel();
        list_SanPham = new ArrayList<>();
        list_SanPham = sanpham.layDanhSachSpDeban();
        
        f.getContentPane().setBackground(Color.WHITE); //Set mau nen cho ca frame
        f.setTitle("Quản lý kho");
        
        controller = new KhoControl();
        JButton quaylaiButton = new JButton("Quay lại");
        quaylaiButton.setBounds(10, 20, 80, 30);
        quaylaiButton.addActionListener( (ActionEvent e) -> {
            try {
                controller.QuayLai(e, MANV, CapBac, f);
            } catch (IOException ex) {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.lang.ClassCastException ex)
            {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        f.add(quaylaiButton);

        JLabel QLK = new JLabel("QUẢN LÝ KHO");
        QLK.setBounds(397, 14, 210, 30);//Toa do X, Y, rong toi da, cao toi da cua Label
        Font fontQLK = new Font("System", Font.BOLD, 30); //Font System, Bold, 30
        QLK.setFont(fontQLK);
        //============================================================================
        JPanel QLSP = new JPanel();
        QLSP.setBounds(0, 0, 965, 544);
        QLSP.setLayout(null);

        JPanel TimKiem = new JPanel();  // Khung tim kiem
        TimKiem.setBackground(Color.WHITE);  // Mau nen cho khung tim kiem

        //-------------------------------------------------
        // Label tim kiem
        JLabel timkiem = new JLabel("Tìm kiếm: "); // Chu Tim kiem: 
        Font fontTimKiem = new Font("System", Font.BOLD, 12);  //set font
        timkiem.setFont(fontTimKiem);
        timkiem.setBounds(14, 16, 58, 17);  // Dat vi tri cua label Tim kiem trong khung tim kiem

        TimKiem.add(timkiem);  //Them label Tim kiem vao Panel Tim kiem

        //-------------------------------------------------
        // textField tim kiem
        JTextField maSPTimKiem = new JTextField();
        maSPTimKiem.setBounds(80, 12, 259, 25);
        maSPTimKiem.setPreferredSize(new Dimension(259, 25));
        TimKiem.add(maSPTimKiem);

        //-------------------------------------------------
        // tao nut Tim
        JButton tim = new JButton("Tìm");
        tim.setBounds(351, 12, 76, 26);
        tim.setPreferredSize(new Dimension(76, 26));  //kich thuoc nut Tim
        TimKiem.add(tim);
        //-------------------------------------------------
        // tuy chinh khung tim kiem
        TimKiem.setLayout(null);  // set layout tuy chinh

        TimKiem.setPreferredSize(new Dimension(441, 48));
        Border TimKiemBorder = BorderFactory.createLineBorder(Color.darkGray); //Tao border cho khung tim kiem
        TimKiem.setBorder(TimKiemBorder); //set Border

        TimKiem.setBounds(0, 0, 441, 48);   //Set vi tri cua Panel tim kiem

        //===========================================================================
        dtm_SP_SanPham = new DefaultTableModel() ;
        
        dtm_SP_SanPham.addColumn("Mã SP");
        dtm_SP_SanPham.addColumn("Tên SP");
        dtm_SP_SanPham.addColumn("Đơn giá nhập");
        dtm_SP_SanPham.addColumn("Đơn giá bán");
        dtm_SP_SanPham.addColumn("Đơn vị");
        dtm_SP_SanPham.addColumn("Mã Loại");
        dtm_SP_SanPham.addColumn("SL tồn");
        dtm_SP_SanPham.addColumn("Trạng thái");

        controller.loadSanPham(list_SanPham, dtm_SP_SanPham);

        jt_SP_SanPham = new JTable();  //Tao bang (chi co noi dung bang)
        jt_SP_SanPham.setModel(dtm_SP_SanPham);

        tim.addActionListener( (ActionEvent e)->{
            try {
                controller.TimSanPham(list_SanPham, maSPTimKiem, jt_SP_SanPham, dtm_SP_SanPham);
            } catch (SQLException ex) {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        jt_SP_SanPham.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.ClickChonSanPham(e, jt_SP_SanPham, dtm_SP_SanPham, jtf_SP_MaSP, jtf_SP_TenSP, jtf_SP_DonGiaNhap, jtf_SP_DonGiaBan, jtf_SP_DonVi, jtf_SP_SLTon, jcb_SP_Loai);

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
//

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
        JScrollPane sp = new JScrollPane(jt_SP_SanPham);  //Them tieu de moi cot
        sp.setBounds(16, 73, 586, 457);
        QLSP.add(sp);

        //========================================================================
        JPanel ChinhSuaTT = new JPanel();
        ChinhSuaTT.setBounds(610, 0, 348, 544);
        ChinhSuaTT.setLayout(null);

        //---------------------------------------------------
        //Tieu de 
        JLabel Title = new JLabel("Chỉnh sửa thông tin sản phẩm");
        Title.setFont(new Font("System", Font.BOLD + Font.ITALIC, 15));
        Title.setBounds(14, 46, 214, 21);
        ChinhSuaTT.add(Title);

        //---------------------------------------------------
        //Bang chinh sua
        JPanel chinhsua = new JPanel();
        chinhsua.setBounds(14, 73, 320, 457);
        chinhsua.setBackground(Color.white);
        chinhsua.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        chinhsua.setLayout(null);

        //Label jtf_SP_MaSP
        JLabel MaSP = new JLabel("Mã SP:");
        MaSP.setFont(new Font("System", Font.BOLD, 12));
        MaSP.setBounds(19, 19, 40, 17);
        chinhsua.add(MaSP);
        //TextField jtf_SP_MaSP
        jtf_SP_MaSP = new JTextField();
        jtf_SP_MaSP.setBounds(62, 15, 87, 25);
        jtf_SP_MaSP.setEditable(false);
        chinhsua.add(jtf_SP_MaSP);

        //Label so luong ton
        JLabel SLT = new JLabel("Số lượng tồn:");
        SLT.setFont(new Font("System", Font.BOLD, 12));
        SLT.setBounds(169, 19, 78, 17);
        chinhsua.add(SLT);
        //TextField so luong ton
        jtf_SP_SLTon = new JTextField();
        jtf_SP_SLTon.setBounds(250, 15, 51, 25);
        chinhsua.add(jtf_SP_SLTon);

        //Label ten san pham
        JLabel TenSP = new JLabel("Tên SP:");
        TenSP.setFont(new Font("System", Font.BOLD, 12));
        TenSP.setBounds(18, 53, 43, 17);
        chinhsua.add(TenSP);
        //TextField ten san pham
        jtf_SP_TenSP = new JTextField();
        jtf_SP_TenSP.setBounds(62, 49, 240, 25);
        chinhsua.add(jtf_SP_TenSP);

        //Label don gia nhap
        JLabel DGNhap = new JLabel("Đơn giá nhập:");
        DGNhap.setFont(new Font("System", Font.BOLD, 12));
        DGNhap.setBounds(18, 88, 81, 17);
        chinhsua.add(DGNhap);
        //TextField don gia nhap
        jtf_SP_DonGiaNhap = new JTextField();
        jtf_SP_DonGiaNhap.setBounds(104, 84, 198, 25);
        chinhsua.add(jtf_SP_DonGiaNhap);

        //Label don gia ban
        JLabel DGBan = new JLabel("Đơn giá bán:");
        DGBan.setFont(new Font("System", Font.BOLD, 12));
        DGBan.setBounds(18, 123, 73, 17);
        chinhsua.add(DGBan);
        //TextField don gia ban
        jtf_SP_DonGiaBan = new JTextField();
        jtf_SP_DonGiaBan.setBounds(105, 120, 198, 25);
        chinhsua.add(jtf_SP_DonGiaBan);

        //Label Loai
        JLabel Loai = new JLabel("Loại:");
        Loai.setFont(new Font("System", Font.BOLD, 12));
        Loai.setBounds(18, 156, 28, 17);
        chinhsua.add(Loai);
        //ComboBox Loai
        String[] type = Layloaisanpham();
        jcb_SP_Loai = new JComboBox(type);
        jcb_SP_Loai.setBounds(54, 152, 150, 25);
        chinhsua.add(jcb_SP_Loai);

        JLabel donviban = new JLabel("Đơn vị bán");
        donviban.setFont(new Font("System", Font.BOLD, 12));
        donviban.setBounds(18, 200, 73, 17);
        chinhsua.add(donviban);
        //TextField don gia ban
        jtf_SP_DonVi = new JTextField();
        jtf_SP_DonVi.setBounds(105, 200, 198, 25);
        chinhsua.add(jtf_SP_DonVi);
        //Button Khôi phục
        JButton btnkhoiphuc = new JButton("Khôi Phục");
        btnkhoiphuc.setBounds(110, 397, 100, 46);
        chinhsua.add(btnkhoiphuc);
        btnkhoiphuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.KhoiPhucSanPham(jtf_SP_MaSP, dtm_SP_SanPham, jt_SP_SanPham, list_SanPham);
                } catch (SQLException ex) {
                    Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        });
        //Button Xoa
        JButton xoa = new JButton("Xoá");
        xoa.setBounds(18, 397, 58, 46);
        chinhsua.add(xoa);
        xoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.XoaSanPham(jtf_SP_MaSP, dtm_SP_SanPham, jt_SP_SanPham, list_SanPham);
                } catch (SQLException ex) {
                    Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        //Button Sua
        JButton sua = new JButton("Sửa");
        sua.setBounds(246, 397, 58, 46);
        sua.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.SuaSanPham(list_SanPham, jcb_SP_Loai, jtf_SP_SLTon, jtf_SP_TenSP, jtf_SP_DonGiaNhap, jtf_SP_DonGiaBan, jtf_SP_DonVi, jtf_SP_MaSP, dtm_SP_SanPham, jt_SP_SanPham);
            }
        });

        chinhsua.add(sua);
        //---------------------------------------------------

        ChinhSuaTT.add(chinhsua);
        QLSP.add(ChinhSuaTT);

        //------------------------------------------------------    
        JPanel topLeft = new JPanel();
        topLeft.add(TimKiem);
        topLeft.setBounds(16, 19, 586, 54);

        JButton ThemSP = new JButton("Thêm sản phẩm");
        ThemSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ThemSanPham(list_SanPham, jcb_SP_Loai, jtf_SP_SLTon, jtf_SP_TenSP, jtf_SP_DonGiaNhap, jtf_SP_DonGiaBan, jtf_SP_DonVi, jt_SP_SanPham, dtm_SP_SanPham);
            }
        });
        ThemSP.setBounds(455, 1, 129, 46);
        topLeft.add(ThemSP);

        topLeft.setPreferredSize(new Dimension(586, 54));
        topLeft.setLayout(null);
        QLSP.add(topLeft);  // Them Panel tim kiem vao khung QLSanPham
        //================================================================================================================================================================
        //================================================================================================================================================================    
        //================================================================================================================================================================

        JPanel TimKiemQLD = new JPanel();  // Khung tim kiem
        TimKiemQLD.setBackground(Color.WHITE);  // Mau nen cho khung tim kiem

        //-------------------------------------------------
        // Label tim kiem
        JLabel timkiemQLD = new JLabel("Tìm kiếm: "); // Chu Tim kiem: 
        Font fontTimKiemQLD = new Font("System", Font.BOLD, 12);  //set font
        timkiemQLD.setFont(fontTimKiemQLD);
        timkiemQLD.setBounds(13, 16, 58, 17);  // Dat vi tri cua label Tim kiem trong khung tim kiem

        TimKiemQLD.add(timkiemQLD);  //Them label Tim kiem vao Panel Tim kiem

        //-------------------------------------------------
        // date choose tim kiem
        jdc_DH_Date = new JDateChooser();
        jdc_DH_Date.setFont(new Font("System", Font.PLAIN, 12));
        jdc_DH_Date.setBounds(79, 12, 137, 26);
        TimKiemQLD.add(jdc_DH_Date);
        jdc_DH_Date.setDateFormatString("dd/MM/yyyy");

        //-------------------------------------------------
        // tao nut Tim
        jb_DH_Tim = new JButton("Tìm");
        jb_DH_Tim.setBounds(231, 12, 76, 26);
        jb_DH_Tim.setPreferredSize(new Dimension(76, 26));  //kich thuoc nut Tim
        TimKiemQLD.add(jb_DH_Tim);
        jb_DH_Tim.addActionListener((ActionEvent e)->{
            try {
                controller.TimDonHang(jdc_DH_Date, dtm_DH_DonHang);
            } catch (SQLException ex) {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //-------------------------------------------------
        // tuy chinh khung tim kiem
        TimKiemQLD.setLayout(null);  // set layout tuy chinh

        TimKiemQLD.setPreferredSize(new Dimension(320, 48));
        Border TimKiemBorderQLD = BorderFactory.createLineBorder(Color.darkGray); //Tao border cho khung tim kiem
        TimKiemQLD.setBorder(TimKiemBorderQLD); //set Border
        TimKiemQLD.setBounds(0, 3, 320, 48);

        //================================================================================================================
        JPanel qlDon = new JPanel();
        qlDon.setLayout(null);

        JPanel topLeftQLD = new JPanel();
        topLeftQLD.add(TimKiemQLD);
        topLeftQLD.setBounds(14, 14, 454, 53);

        jb_DH_NhapHang = new JButton("Nhập hàng");
        jb_DH_NhapHang.setBounds(333, 4, 102, 46);

        topLeftQLD.add(jb_DH_NhapHang);
        topLeftQLD.setLayout(null);
        qlDon.add(topLeftQLD);
        
        jb_DH_NhapHang.addActionListener((ActionEvent e)->{
            try {
                controller.NhapHang(e, f, MANV, CapBac);
            } catch (SQLException ex) {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //==================================================================
        dtm_DH_DonHang = new DefaultTableModel();
        
        dtm_DH_DonHang.addColumn("STT");
        dtm_DH_DonHang.addColumn("Mã đơn hàng");
        dtm_DH_DonHang.addColumn("Ngày nhập hàng");
        dtm_DH_DonHang.addColumn("Tổng giá trị");

        controller.loadDonHang(list_DonHang, dtm_DH_DonHang);
        jt_DH_DonHang = new JTable();  //Tao bang (chi co noi dung bang)
        jt_DH_DonHang.setModel(dtm_DH_DonHang);
        
        jt_DH_DonHang.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.ClickChonDonHang(e, jt_DH_DonHang, dtm_DH_DonHang, jtf_DH_MaDH, jtf_DH_NgayNhap, jtf_DH_TongGiaTri, dtm_DH_CTDH, jt_DH_CTDH);
                String madh = jtf_DH_MaDH.getText();
                try {
                    controller.loadChiTietNhapHang(list_SanPham, list_CTNH, dtm_DH_CTDH, madh);
                } catch (SQLException ex) {
                    Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
//

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
        
        JScrollPane dh = new JScrollPane(jt_DH_DonHang);  //Them tieu de moi cot
        dh.setBounds(14, 72, 436, 458);

        qlDon.add(dh);

        //=========================================================================
        JPanel TT = new JPanel();
        TT.setBounds(472, 34, 490, 509);
        TT.setLayout(null);

        JLabel title = new JLabel("Thông tin đơn hàng");
        title.setFont(new Font("System", Font.BOLD + Font.ITALIC, 15));
        title.setBounds(8, 40, 140, 21);
        TT.add(title);

        JPanel tt = new JPanel();
        tt.setLayout(null);
        tt.setBounds(4, 62, 473, 433);
        tt.setBackground(Color.white);
        tt.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        //Label ma don hang
        JLabel MaDH = new JLabel("Mã đơn hàng:");
        MaDH.setFont(new Font("System", Font.BOLD, 12));
        MaDH.setBounds(14, 16, 80, 17);
        tt.add(MaDH);
        //TextField ma don hang
        jtf_DH_MaDH = new JTextField();
        jtf_DH_MaDH.setBounds(100, 12, 107, 25);
        jtf_DH_MaDH.setEditable(false);
        tt.add(jtf_DH_MaDH);

        //Label Ngay nhap
        JLabel NgayNhap = new JLabel("Ngày nhập:");
        NgayNhap.setFont(new Font("System", Font.BOLD, 12));
        NgayNhap.setBounds(232, 16, 70, 17);
        tt.add(NgayNhap);
        //date choose Ngay nhap
        jtf_DH_NgayNhap = new JTextField();
        jtf_DH_NgayNhap.setFont(new Font("System", Font.PLAIN, 12));
        jtf_DH_NgayNhap.setBounds(308, 12, 160, 26);
        tt.add(jtf_DH_NgayNhap);

        //Label tong gia tri
        JLabel TongGT = new JLabel("Tổng giá trị:");
        TongGT.setFont(new Font("System", Font.BOLD, 12));
        TongGT.setBounds(14, 48, 70, 17);
        tt.add(TongGT);
        //TextField Tong gia tri
        jtf_DH_TongGiaTri = new JTextField();
        jtf_DH_TongGiaTri.setBounds(100, 44, 107, 25);
        tt.add(jtf_DH_TongGiaTri);

        //Label DS san pham
        JLabel list_SanPham = new JLabel("Danh sách sản phẩm:");
        list_SanPham.setFont(new Font("System", Font.BOLD, 12));
        list_SanPham.setBounds(14, 178, 125, 17);
        tt.add(list_SanPham);
        //table dssp
        
        dtm_DH_CTDH = new DefaultTableModel();
        
        dtm_DH_CTDH.addColumn("STT");
        dtm_DH_CTDH.addColumn("Mã SP");
        dtm_DH_CTDH.addColumn("Tên SP");
        dtm_DH_CTDH.addColumn("Loại");
        dtm_DH_CTDH.addColumn("Đơn giá");
        dtm_DH_CTDH.addColumn("Số lượng");
        dtm_DH_CTDH.addColumn("Thành tiền");
        
        
        jt_DH_CTDH = new JTable();  //Tao bang (chi co noi dung bang)
        jt_DH_CTDH.setModel(dtm_DH_CTDH);
        
        JScrollPane DSsp = new JScrollPane(jt_DH_CTDH);  //Them tieu de moi cot
        DSsp.setBounds(14, 203, 451, 218);
        tt.add(DSsp);

        TT.add(tt);
        qlDon.add(TT);

        JTabbedPane tabpane = new JTabbedPane();
        tabpane.add("Quản lý sản phẩm", QLSP);
        tabpane.add("Quản lý đơn nhập hàng", qlDon);
        tabpane.setBounds(17, 63, 965, 573);

        f.add(QLK); //Them Label Quan Ly Kho vao trong JFrame  
        f.add(tabpane);

        f.setSize(1015, 689);//Do rong la 1015 va chieu cao la 695
        f.setLayout(null);//Khong su dung Layout Manager  
        f.setVisible(true);//Tao Frame la co the nhin thay (visible)  
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
    }

    

    public String[] Layloaisanpham() throws SQLException, ClassNotFoundException {
        LOAISANPHAMModel loai = new LOAISANPHAMModel();
        loai.layDanhsachloaisanpham();
        String[] ds = new String[loai.demSoloaisanpham() + 1];
        ArrayList<LOAISANPHAMModel> Dsloai = new ArrayList<>();
        Dsloai = loai.getDsloai();
        int i = 0;
        for (LOAISANPHAMModel tam : Dsloai) {
            ds[i] = tam.getTENLOAI();
            i++;
        }
        return ds;
    }


    
    public void loadDonHang(ArrayList<DONNHAPHANGModel> list_DonHang, DefaultTableModel dtm_DH_DonHang) throws SQLException, ClassNotFoundException {
        DONNHAPHANGModel donhang = new DONNHAPHANGModel();
        list_DonHang = new ArrayList<>();
        list_DonHang = donhang.layDanhSachDonHang();
        for (DONNHAPHANGModel tam : list_DonHang) {
            Vector dataSP = new Vector();
            dataSP.add(tam.getMADH());
            dataSP.add(tam.getNgaygio());
            dataSP.add(String.valueOf(tam.getTONGGIATRI()));
            dtm_DH_DonHang.addRow(dataSP);
        }
    }
    

    public void dispose() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
