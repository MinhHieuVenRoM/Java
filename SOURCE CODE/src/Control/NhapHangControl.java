/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Model.DONNHAPHANGModel;
import Model.LOAISANPHAMModel;
import Model.SANPHAMModel;
import View.GiaoDienKho;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.ActionEvent;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ADMIN
 */
public class NhapHangControl {
    
    private Connection connection;
    private float TongGiaTri = 0;
    
    public void Huy(JFrame f, ActionEvent e, String MaNV, int CapBac) throws SQLException, ClassNotFoundException
    {
        f.dispose();
        Component component = (Component) e.getSource();
        GiaoDienKho kho = new GiaoDienKho(MaNV, CapBac);
    }
    
    public void NhapHang(JFrame f, ActionEvent e, String MaNV, int CapBac, JTextField jtf_MaDH, JDateChooser jdc_NgayNhap, DefaultTableModel dtm_SanPham) throws SQLException, ClassNotFoundException
    {
        connection = MSSQLControl.getConnect();
        Statement statement = null;
        statement = connection.createStatement();
        String sql1 = "insert into DONNHAPHANG values ('";
        
        String madh = jtf_MaDH.getText();
        Date date_ngaynhap = jdc_NgayNhap.getDate();
        SimpleDateFormat date_string = new SimpleDateFormat("yyyy-MM-dd");
        String ngaynhap = date_string.format(date_ngaynhap);
        String tonggiatri = TongGiaTri+"";
        
        sql1 = sql1 + madh + "','" + ngaynhap + "','" + tonggiatri + "')";
        
        statement.executeUpdate(sql1);
        
        int row = dtm_SanPham.getRowCount();
        for (int i = 0; i < row; i++)
        {
            String masp = (String) dtm_SanPham.getValueAt(i, 1);
            String sl = (String) dtm_SanPham.getValueAt(i,5);
            String dongia = (String) dtm_SanPham.getValueAt(i,4);
            String sql2 = "insert into CHITIETNHAPHANG values ('" + masp + "','" + madh + "','" + sl + "','" + dongia + "')";
            statement.executeUpdate(sql2);
        }
        
        
        f.dispose();
        Component component = (Component) e.getSource();
        GiaoDienKho kho = new GiaoDienKho(MaNV, CapBac);
    }
    
    public String LoadMaDH() throws SQLException, ClassNotFoundException {
        DONNHAPHANGModel dh = new DONNHAPHANGModel();
        ArrayList<DONNHAPHANGModel> danhsachtam = dh.layDanhSachDonHang();

        int size = danhsachtam.size();
        String MADH = danhsachtam.get(size - 1).getMADH();
        String chuoisodhtam = MADH.substring(2, 5);//cắt chuỗi từ vị trí 2->(5-1)
        int temp = Integer.parseInt(chuoisodhtam);//lấy sodh cuối cùng
        String madh = "";
        if (temp < 9) {
            madh = ("DH00" + (temp + 1));
        }
        if (temp >= 9 && temp < 99) {
            madh = ("DH0" + (temp + 1));
        }
        if (temp >= 99) {
            madh = ("DH" + (temp + 1));
        }
        return madh;
    }
    
    public void ThemSanPham(JTextField jtf_TenSP, JTextField jtf_MaSP, JTextField jtf_SoLuong, JTextField jtf_DonGiaNhap, DefaultTableModel dtm_SanPham, JLabel jl_TongGiaTri) throws SQLException, ClassNotFoundException
    {
        String TenSP = jtf_TenSP.getText();
        String MaSP = jtf_MaSP.getText();
        String SoLuong = jtf_SoLuong.getText();
        String DonGiaNhap = jtf_DonGiaNhap.getText();
        
        jtf_MaSP.setText("");
        jtf_TenSP.setText("");
        jtf_SoLuong.setText("");
        jtf_DonGiaNhap.setText("");
        
        int stt = dtm_SanPham.getRowCount()+1;
        
        for (int i = 0; i < stt-1; i++)
        {
            String masptam = (String) dtm_SanPham.getValueAt(i, 1);
            if (masptam.equals(MaSP))
            {
                JOptionPane.showMessageDialog(null, "Phải xoá sản phẩm này ở danh sách sản phẩm nhập hàng trước khi thêm lại sản phẩm");
                return;
            }
        }
        
        
        Vector dataSP = new Vector();
        dataSP.add(stt);
        dataSP.add(MaSP);
        dataSP.add(TenSP);
        
        ArrayList<LOAISANPHAMModel> list_Loai = new ArrayList<>();
        ArrayList<SANPHAMModel> list_SanPham = new ArrayList<>();
        LOAISANPHAMModel loai = new LOAISANPHAMModel();
        SANPHAMModel SP = new SANPHAMModel();
        loai.layDanhsachloaisanpham();
        list_Loai = loai.getDsloai();
        list_SanPham = SP.layDanhSachSpDeban();
        
        for (SANPHAMModel sptam : list_SanPham)
        {
            String masp = sptam.getMASP();
            if (masp.equals(MaSP))
            {
                SP = sptam;
                break;
            }
        }
        String MaLoai = SP.getMALOAI();
        for (LOAISANPHAMModel loaisptam : list_Loai)
        {
            String maloai = loaisptam.getMALOAI();
            if (maloai.equals(MaLoai))
            {
                loai = loaisptam;
                break;
            }
        }
        
        dataSP.add(loai.getTENLOAI());
        dataSP.add(DonGiaNhap);
        dataSP.add(SoLuong);
        Float dongianhap = parseFloat(DonGiaNhap);
        int soluong = parseInt(SoLuong);
        float thanhtien = dongianhap * soluong;
        dataSP.add(thanhtien);
        dtm_SanPham.addRow(dataSP);
        TongGiaTri = TongGiaTri + thanhtien;
        jl_TongGiaTri.setText(TongGiaTri+"");
    }
    
    public void TuDongNhapTenSP(JTextField jtf_TenSP, JTextField jtf_MaSP) throws SQLException, ClassNotFoundException
    {
        String masp = jtf_MaSP.getText();
        ArrayList<SANPHAMModel> list_SanPham = new ArrayList<>();
        SANPHAMModel SP = new SANPHAMModel();
        list_SanPham = SP.layDanhSachSpDeban();
        boolean CoSP = false;
        for (SANPHAMModel sptam : list_SanPham)
        {
            String ma = sptam.getMASP();
            if (ma.equals(masp))
            {
                SP = sptam;
                CoSP = true;
                break;
            }
        }
        if (CoSP)
            jtf_TenSP.setText(SP.getTENSP());
        else JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm");
    }
    
    public void TuDongNhapMaSP(JTextField jtf_TenSP, JTextField jtf_MaSP) throws SQLException, ClassNotFoundException
    {
        String tensp = jtf_TenSP.getText();
        ArrayList<SANPHAMModel> list_SanPham = new ArrayList<>();
        SANPHAMModel SP = new SANPHAMModel();
        list_SanPham = SP.layDanhSachSpDeban();
        boolean CoSP = false;
        for (SANPHAMModel sptam : list_SanPham)
        {
            String ten = sptam.getTENSP();
            if (ten.equals(tensp))
            {
                SP = sptam;
                CoSP = true;
                break;
            }
        }
        if (CoSP)
            jtf_MaSP.setText(SP.getMASP());
        else JOptionPane.showMessageDialog(null, "Không tìm thấy sản phẩm");
    }
    
    public void XoaSP(DefaultTableModel dtm_SanPham, JTable jt_SanPham, JLabel jl_TongGiaTri)
    {
        int row[] = jt_SanPham.getSelectedRows();
        int n = row.length;
        
        for (int i = 0; i < n; i++)
        {
            float thanhtien = (float) dtm_SanPham.getValueAt(row[i], 6);
            TongGiaTri = TongGiaTri - thanhtien;
            dtm_SanPham.removeRow(row[i]);
        }
        jl_TongGiaTri.setText(TongGiaTri+"");
    }
}
