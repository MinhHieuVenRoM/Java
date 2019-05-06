/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Control.MSSQLControl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Minh Hieu
 */
public class NHANVIENModel {

    
    private static Connection connection;
    private String MaNV;
    private String TenNV;
    private Date NgaySinh;
    private String GioiTinh;
    private String DiaChi;
    private String MaChucVu;
    private Date NgayVaoLam;
    private String SDT;
    private String MatKhau;
    private String GhiChu;
    private ArrayList<NHANVIENModel> DSNV;
    private NHANVIENModel NV;

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }
    
    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
    
    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public void setMaChucVu(String MaChucVu) {
        this.MaChucVu = MaChucVu;
    }

    public void setNgayVaoLam(Date NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getMaNV() {
        return MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public String getMaChucVu() {
        return MaChucVu;
    }
    
    public String getGhiChu() {
        return GhiChu;
    }

    public Date getNgayVaoLam() {
        return NgayVaoLam;
    }

    public String getSDT() {
        return SDT;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public NHANVIENModel(String ma, String pass) {
        this.MaNV = ma;
        this.MatKhau = pass;
    }

    public NHANVIENModel() {
    }

    public ArrayList layThongtinnhanvien() throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = "select * from NHANVIEN where TinhTrang != 0";
        ResultSet resultSet = statement.executeQuery(sql);
        DSNV = new ArrayList<>();
        while (resultSet.next()) {
            NHANVIENModel nv = new NHANVIENModel();
            nv.setMaNV(resultSet.getString("MANV"));
            nv.setTenNV(resultSet.getString("TENNV"));
            nv.setNgaySinh(resultSet.getDate("NGAYSINH"));
            nv.setGioiTinh(resultSet.getString("GIOITINH"));
            nv.setDiaChi(resultSet.getString("DIACHI"));
            nv.setMaChucVu(resultSet.getString("MACHUCVU"));
            nv.setNgayVaoLam(resultSet.getDate("NGAYVAOLAM"));
            nv.setSDT(resultSet.getString("SDT"));
            nv.setMatKhau(resultSet.getString("MATKHAU"));
            nv.setGhiChu(resultSet.getString("GHICHU"));
            DSNV.add(nv);
        }
        connection.close();
        return DSNV;
    }
    
    public NHANVIENModel laythongtincanhan(String a) throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = "select * from NHANVIEN where MaNV = '"+ a +"'";
        ResultSet resultSet = statement.executeQuery(sql);
        NV = new NHANVIENModel();
        while (resultSet.next()) {
            NHANVIENModel flag = new NHANVIENModel();
            flag.setMaNV(resultSet.getString("MANV"));
            flag.setTenNV(resultSet.getString("TENNV"));
            flag.setNgaySinh(resultSet.getDate("NGAYSINH"));
            flag.setGioiTinh(resultSet.getString("GIOITINH"));
            flag.setDiaChi(resultSet.getString("DIACHI"));
            flag.setMaChucVu(resultSet.getString("MACHUCVU"));
            flag.setNgayVaoLam(resultSet.getDate("NGAYVAOLAM"));
            flag.setSDT(resultSet.getString("SDT"));
            flag.setMatKhau(resultSet.getString("MATKHAU"));
            flag.setGhiChu(resultSet.getString("GHICHU"));
            NV = flag;
        }
        connection.close();
        return NV;
    }
    
    public void luuThongTinNhanVien(){
        
    }
    


    public int demSonhanvien() {
        int i = 0;
        for (NHANVIENModel nv : DSNV) {
            i++;
        }
        return i;
    }
}
