/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.CHITIETNHAPHANGModel;
import Model.DONNHAPHANGModel;
import Model.LOAISANPHAMModel;
import Model.SANPHAMModel;
import View.GiaoDienKho;
import View.ManHinhChinh;
import View.NhapHang;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class KhoControl {
    
    private Connection connection;
    // Event nút Quay lại
    public void QuayLai(ActionEvent e, String NhanVien, int capbac, JFrame f) throws IOException {
        Component component = (Component) e.getSource();
        f.dispose();
        ManHinhChinh ManHinh = new ManHinhChinh(NhanVien, capbac);
    }
    
    
    public void TimSanPham (ArrayList<SANPHAMModel> list_SanPham, JTextField maSPTimKiem, JTable jt_SanPham, DefaultTableModel dtm_SanPham) throws SQLException, ClassNotFoundException{
        DefaultTableModel sanphamTableModel = new DefaultTableModel();
        sanphamTableModel.addColumn("Mã SP");
        sanphamTableModel.addColumn("Tên SP");
        sanphamTableModel.addColumn("Đơn giá nhập");
        sanphamTableModel.addColumn("Đơn giá bán");
        sanphamTableModel.addColumn("Đơn vị");
        sanphamTableModel.addColumn("Mã Loại");
        sanphamTableModel.addColumn("SL tồn");
        sanphamTableModel.addColumn("Trạng thái");
        int demthaydoi = 0;
        for (SANPHAMModel tam : list_SanPham) {
            String masptimkiem = maSPTimKiem.getText(), masp = tam.getMASP();
            if (masptimkiem.equals(masp)) {
                Vector dataSP = new Vector();
                dataSP.add(tam.getMASP());
                dataSP.add(tam.getTENSP());
                dataSP.add(String.valueOf(tam.getDONGIANHAP()));
                dataSP.add(String.valueOf(tam.getDONGIABAN()));
                dataSP.add(tam.getDONVI());
                dataSP.add(tam.getMALOAI());
                dataSP.add(String.valueOf(tam.getSOLUONGTON()));
                dataSP.add(String.valueOf(tam.getTinhTrang()));
                sanphamTableModel.addRow(dataSP);
                demthaydoi = 1;
            }
        }
        if (demthaydoi == 1) {
            jt_SanPham.setModel(sanphamTableModel);
        } else {
                loadSanPham(list_SanPham, dtm_SanPham);
                jt_SanPham.setModel(dtm_SanPham);
        }   
    }
    
    
    public void ClickChonSanPham(MouseEvent e, JTable jt_SanPham, DefaultTableModel dtm_SanPham, JTextField jtf_MaSP, JTextField jtf_TenSP, JTextField jtf_DonGiaNhap, JTextField jtf_DonGiaBan, JTextField jtf_DonVi, JTextField jtf_SLTon, JComboBox jcb_Loai)
    {
        int row = jt_SanPham.rowAtPoint(e.getPoint());
        int col = jt_SanPham.columnAtPoint(e.getPoint());
        int numcols = dtm_SanPham.getColumnCount();
        for (int i = 0; i < numcols; i++) {
            String tam = (String) dtm_SanPham.getValueAt(row, i);
            if (i == 0) {
                jtf_MaSP.setText(tam);
            }
            if (i == 1) {
                jtf_TenSP.setText(tam);
            }
            if (i == 2) {
                jtf_DonGiaNhap.setText(tam);
            }
            if (i == 3) {
                jtf_DonGiaBan.setText(tam);
            }
            if (i == 4) {
                jtf_DonVi.setText(tam);
            }
            if (i == 5) {
                LOAISANPHAMModel loaisp = new LOAISANPHAMModel();
                try {
                    loaisp.layDanhsachloaisanpham();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
                }
                ArrayList<LOAISANPHAMModel> Dsloai = new ArrayList<>();
                Dsloai = loaisp.getDsloai();
                int j = jcb_Loai.getItemCount();
                String tenloaisp = "";
                for (LOAISANPHAMModel Temp : Dsloai) {
                    if (Temp.getMALOAI().equals(tam) == true) {
                        tenloaisp = Temp.getTENLOAI();
                    }
                }
                for (int k = 0; k < j - 1; k++) {
                    if (jcb_Loai.getItemAt(k).toString().equals(tenloaisp) == true) {
                        {
                            jcb_Loai.setSelectedIndex(k);
                        }
                    }
                }
                
            }
            if (i == 6) {
                jtf_SLTon.setText(tam);
            }                  
        }
    }
    
    
    public void KhoiPhucSanPham(JTextField jtf_MaSP, DefaultTableModel dtm_SanPham, JTable jt_SanPham, ArrayList<SANPHAMModel> list_SanPham) throws SQLException, ClassNotFoundException
    {
        connection = MSSQLControl.getConnect();
        Statement statement = null;
        statement = connection.createStatement();
        String sql1 = "Update SanPham set TinhTrang='1' where MASP='" + jtf_MaSP.getText() + "'";
        statement.executeUpdate(sql1);
        dtm_SanPham.setRowCount(0);
        
        loadSanPham(list_SanPham, dtm_SanPham);
        
        jt_SanPham.setModel(dtm_SanPham);
    }
    
    
    public void XoaSanPham(JTextField jtf_MaSP, DefaultTableModel dtm_SanPham, JTable jt_SanPham, ArrayList<SANPHAMModel> list_SanPham) throws SQLException, ClassNotFoundException
    {
        connection = MSSQLControl.getConnect();
        Statement statement = null;
        statement = connection.createStatement();
        String sql1 = "Update SanPham set TinhTrang='0' where MASP='" + jtf_MaSP.getText() + "'";
        statement.executeUpdate(sql1);
        dtm_SanPham.setRowCount(0);
        
        loadSanPham(list_SanPham, dtm_SanPham);
        
        jt_SanPham.setModel(dtm_SanPham);
    }
    
    
    public void ThemSanPham(ArrayList<SANPHAMModel> list_SanPham, JComboBox jcb_Loai, JTextField jtf_SLTon, JTextField jtf_TenSP, JTextField jtf_DonGiaNhap, JTextField jtf_DonGiaBan, JTextField jtf_DonVi, JTable jt_SanPham, DefaultTableModel dtm_SanPham)
    {
        LOAISANPHAMModel lsp = new LOAISANPHAMModel();
                ArrayList<LOAISANPHAMModel> Dsloai = new ArrayList<>();
                try {
                    lsp.layDanhsachloaisanpham();
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
                }
                Dsloai = lsp.getDsloai();
                String MaLoaiSP = null;
                for (LOAISANPHAMModel tam : Dsloai) {
                    if (jcb_Loai.getSelectedItem().toString().equals(tam.getTENLOAI())) {
                        MaLoaiSP = tam.getMALOAI();
                    }
                }
                try {
                    if (Integer.parseInt(jtf_SLTon.getText()) >= 0) {
                        connection = MSSQLControl.getConnect();
                        String sql = "insert into SanPham values(?,?,?,?,?,?,?,?)";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, LoadMaSP());
                        ps.setString(2, "N'"+jtf_TenSP.getText()+"'");
                        ps.setString(3, jtf_DonGiaNhap.getText());
                        ps.setString(4, jtf_DonGiaBan.getText());
                        ps.setString(5, "N'"+jtf_DonVi.getText()+"'");
                        ps.setString(6, MaLoaiSP);
                        ps.setString(7, jtf_SLTon.getText());
                        ps.setString(8, "True");
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thành công");
                        jtf_TenSP.setText("");
                        jtf_DonGiaNhap.setText("");
                        jtf_DonGiaBan.setText("");
                        jtf_DonVi.setText("");
                        jtf_SLTon.setText("");
                        loadSanPham(list_SanPham, dtm_SanPham);
                        jt_SanPham.setModel(dtm_SanPham);
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm sản phẩm thất bại");
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Thêm sản phẩm thất bại");
                    Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public void SuaSanPham(ArrayList<SANPHAMModel> list_SanPham, JComboBox jcb_Loai, JTextField jtf_SLTon, JTextField jtf_TenSP, JTextField jtf_DonGiaNhap, JTextField jtf_DonGiaBan, JTextField jtf_DonVi, JTextField jtf_MaSP, DefaultTableModel dtm_SanPham, JTable jt_SanPham)
    {
        try {
            connection = MSSQLControl.getConnect();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
        }
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOAISANPHAMModel lsp = new LOAISANPHAMModel();
        ArrayList<LOAISANPHAMModel> Dsloai = new ArrayList<>();
        try {
            lsp.layDanhsachloaisanpham();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
        }
        Dsloai = lsp.getDsloai();
        String MaLoaiSP = null;
        for (LOAISANPHAMModel tam : Dsloai) {
            if (jcb_Loai.getSelectedItem().toString().equals(tam.getTENLOAI())) {
                MaLoaiSP = tam.getMALOAI();
            }
        }
        if (Integer.parseInt(jtf_SLTon.getText()) >= 0) {
            String Sql = "Update SanPham \n"
                    + "set TenSP=N'" + jtf_TenSP.getText() + "' ,\n"
                    + "SoLuongTon='" + jtf_SLTon.getText() + "',\n"
                    + "DonGiaNhap='" + jtf_DonGiaNhap.getText() + "',\n"
                    + "DonGiaBan='" + jtf_DonGiaBan.getText() + "',\n"
                    + "DonVi=N'" + jtf_DonVi.getText() + "',\n"
                    + "MaLoai='" + MaLoaiSP + "'\n"
                    + "where MASP='" + jtf_MaSP.getText() + "'";
            try {
                statement.executeUpdate(Sql);
            } catch (SQLException ex) {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            }
            dtm_SanPham.setRowCount(0);
            try {
                loadSanPham(list_SanPham, dtm_SanPham);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
            }
            jt_SanPham.setModel(dtm_SanPham);
        } else {
            JOptionPane.showMessageDialog(null, "Sửa sản phẩm thất bại");
        }
    }
    
    
    public void loadSanPham(ArrayList<SANPHAMModel> list_SanPham, DefaultTableModel dtm_SanPham) throws SQLException, ClassNotFoundException {
        SANPHAMModel sanpham = new SANPHAMModel();
        list_SanPham = new ArrayList<>();
        list_SanPham = sanpham.layDanhSachSpDeban();
        dtm_SanPham.getDataVector().removeAllElements();
        for (SANPHAMModel tam : list_SanPham) {
            Vector dataSP = new Vector();
            dataSP.add(tam.getMASP());
            dataSP.add(tam.getTENSP());
            dataSP.add(String.valueOf(tam.getDONGIANHAP()));
            dataSP.add(String.valueOf(tam.getDONGIABAN()));
            dataSP.add(tam.getDONVI());
            dataSP.add(tam.getMALOAI());
            dataSP.add(String.valueOf(tam.getSOLUONGTON()));
            dataSP.add(String.valueOf(tam.getTinhTrang()));
            dtm_SanPham.addRow(dataSP);
        }
    }
    
    public String loadcbb(String tam, JComboBox jcb_Loai) {
        LOAISANPHAMModel loaisp = new LOAISANPHAMModel();
        try {
            loaisp.layDanhsachloaisanpham();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(GiaoDienKho.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<LOAISANPHAMModel> Dsloai = new ArrayList<>();
        Dsloai = loaisp.getDsloai();
        int j = jcb_Loai.getItemCount();
        String tenloaisp = "";
        for (LOAISANPHAMModel Temp : Dsloai) {
            if (Temp.getMALOAI().equals(tam) == true) {
                tenloaisp = Temp.getTENLOAI();
            }
        }
        for (int k = 0; k < j - 1; k++) {
            if (jcb_Loai.getItemAt(k).toString().equals(tenloaisp) == true) {
                {
                    jcb_Loai.setSelectedIndex(k);
                }
            }
        }
        return null;
    }
    
    public String LoadMaSP() throws SQLException, ClassNotFoundException {
        SANPHAMModel sp = new SANPHAMModel();
        ArrayList<SANPHAMModel> danhsachtam = sp.layDanhSachSpDeban();

        int size = danhsachtam.size();
        String MASP = danhsachtam.get(size - 1).getMASP();
        String chuoisohdtam = MASP.substring(2, 5);//cắt chuỗi từ vị trí 2->(5-1)
        int temp = Integer.parseInt(chuoisohdtam);//lấy sohd cuối cùng
        String masp = "";
        if (temp < 9) {
            masp = ("SP00" + (temp + 1));
        }
        if (temp >= 9 && temp < 99) {
            masp = ("SP0" + (temp + 1));
        }
        if (temp >= 99) {
            masp = ("SP" + (temp + 1));
        }
        return masp;
    }
    
//    ======================================================================================================================================
//    ======================================================================================================================================
//    ======================================================================================================================================
    public void loadDonHang(ArrayList<DONNHAPHANGModel> list_DonHang, DefaultTableModel dtm_DonHang) throws SQLException, ClassNotFoundException {
        DONNHAPHANGModel donhang = new DONNHAPHANGModel();
        list_DonHang = new ArrayList<>();
        list_DonHang = donhang.layDanhSachDonHang();
        int stt = 0;
        dtm_DonHang.getDataVector().removeAllElements();
        for (DONNHAPHANGModel tam : list_DonHang) {
            stt++;
            Vector dataDH = new Vector();
            dataDH.add(stt);
            dataDH.add(tam.getMADH());
            dataDH.add(tam.getNgaygio());
            dataDH.add(String.valueOf(tam.getTONGGIATRI()));
            dtm_DonHang.addRow(dataDH);
        }
    }
    
    public void ClickChonDonHang(MouseEvent e, JTable jt_DH_DonHang, DefaultTableModel dtm_DH_DonHang, JTextField jtf_DH_MaDH, JTextField jtf_DH_NgayNhap, JTextField jtf_DH_TongGiaTri, DefaultTableModel dtm_CTNH, JTable jt_CTNH)
    {
        int row = jt_DH_DonHang.rowAtPoint(e.getPoint());
        int numcols = dtm_DH_DonHang.getColumnCount();
        for (int i = 1; i < numcols; i++) {
            String tam = (String) dtm_DH_DonHang.getValueAt(row, i);
            if (i == 1) {
                jtf_DH_MaDH.setText(tam);
            }
            if (i == 2) {
                jtf_DH_NgayNhap.setText(tam);
            }
            if (i == 3) {
                jtf_DH_TongGiaTri.setText(tam);
            }              
        }
        dtm_CTNH.getDataVector().removeAllElements();
        jt_CTNH.setModel(dtm_CTNH);
    }
    
        
    public void loadChiTietNhapHang(ArrayList<SANPHAMModel> list_SanPham, ArrayList<CHITIETNHAPHANGModel> list_CTNH, DefaultTableModel dtm_CTNH, String MaDH) throws SQLException, ClassNotFoundException {
        CHITIETNHAPHANGModel ctnh = new CHITIETNHAPHANGModel();
        list_CTNH = new ArrayList<>();
        list_CTNH = ctnh.layDSCTNH(MaDH);
        int stt = 0;
        ArrayList<LOAISANPHAMModel> list_Loai = new ArrayList();
        LOAISANPHAMModel loai = new LOAISANPHAMModel();
        SANPHAMModel SP = new SANPHAMModel();
        loai.layDanhsachloaisanpham();
        list_Loai = loai.getDsloai();
        list_SanPham = SP.layDanhSachSpDeban();
        
        dtm_CTNH.getDataVector().removeAllElements();
        for (CHITIETNHAPHANGModel CTtam : list_CTNH) {
            
            stt++;
            Vector dataCT = new Vector();
            dataCT.add(stt);
            
            String masp = CTtam.getMASP();
            dataCT.add(masp);
            
            SANPHAMModel sp = new SANPHAMModel();
            for (SANPHAMModel sptam : list_SanPham)
            {
                if (masp.equals(sptam.getMASP()))
                {
                    sp = sptam;
                    break;
                }
            }
            
            dataCT.add(sp.getTENSP());
            
            String maloai = sp.getMALOAI();
            LOAISANPHAMModel l = new LOAISANPHAMModel();
            for (LOAISANPHAMModel loaitam : list_Loai)
            {
                String maloaitam = loaitam.getMALOAI();
                if (maloai.equals(maloaitam))
                {
                    l = loaitam;
                    break;
                }
            }
            
            dataCT.add(l.getTENLOAI());
            
            Float dongianhap = CTtam.getDONGIANHAP();
            int soluong = CTtam.getSoLuong();
            Float thanhtien = dongianhap * soluong;
            
            dataCT.add(String.valueOf(dongianhap));
            dataCT.add(String.valueOf(soluong));
            dataCT.add(String.valueOf(soluong));
            dtm_CTNH.addRow(dataCT);
        }
    }
    
    public void NhapHang(ActionEvent e, JFrame f, String MaNV, int CapBac) throws SQLException, ClassNotFoundException
    {
        Component component = (Component) e.getSource();
        f.dispose();
        NhapHang nhaphang = new NhapHang(MaNV, CapBac);
    }
    
    public void TimDonHang(JDateChooser jdc_DH_NgayNhap, DefaultTableModel dtm_DH_DonHang) throws SQLException, SQLException, ClassNotFoundException, ClassNotFoundException, ClassNotFoundException, ClassNotFoundException, ClassNotFoundException, ClassNotFoundException
    {
        DONNHAPHANGModel donhang = new DONNHAPHANGModel();
        ArrayList<DONNHAPHANGModel> list_DonHang = new ArrayList<>();
        list_DonHang = donhang.layDanhSachDonHang();
        Date date_NgayNhap = jdc_DH_NgayNhap.getDate();
        SimpleDateFormat date_string = new SimpleDateFormat("yyyy-MM-dd");
        String ngaynhap = date_string.format(date_NgayNhap);
        dtm_DH_DonHang.getDataVector().removeAllElements();
        int stt = 0;
        
        for (DONNHAPHANGModel tam : list_DonHang) {
            String ngaytam = tam.getNgaygio();
            if (ngaynhap.equals(ngaytam)) {
                stt++;
                Vector dataDH = new Vector();
                dataDH.add(stt);
                dataDH.add(tam.getMADH());
                dataDH.add(ngaynhap);
                dataDH.add(tam.getTONGGIATRI());
                dtm_DH_DonHang.addRow(dataDH);
            }
        }   
    }
}
