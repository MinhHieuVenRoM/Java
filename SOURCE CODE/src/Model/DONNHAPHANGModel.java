package Model;

import Control.MSSQLControl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ADMIN
 */
public class DONNHAPHANGModel {
    private static Connection connection;
    private String MADH;
    private String Ngaygio;
    private Float TONGGIATRI;
    private ArrayList<DONNHAPHANGModel> list_DSDH;
    
    public String getMADH() {
        return MADH;
    }

    public String getNgaygio() {
        return Ngaygio;
    }

    public Float getTONGGIATRI() {
        return TONGGIATRI;
    }

    public ArrayList<DONNHAPHANGModel> getDSDH() {
        return list_DSDH;
    }

    public void setMADH(String MADH) {
        this.MADH = MADH;
    }

    public void setNgaygio(String Ngaygio) {
        this.Ngaygio = Ngaygio;
    }


    public void setTONGGIATRI(Float TONGGIATRI) {
        this.TONGGIATRI = TONGGIATRI;
    }

    public void setDSDH(ArrayList<DONNHAPHANGModel> DSDH) {
        this.list_DSDH = DSDH;
    }
    
    
    public ArrayList layDanhSachDonHang() throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = MSSQLControl.SELECT_DONNHAPHANG;
        ResultSet resultSet = statement.executeQuery(sql);
        list_DSDH = new ArrayList<>();

        while (resultSet.next()) {
            DONNHAPHANGModel dh = new DONNHAPHANGModel();
            dh.setMADH(resultSet.getString("MaDonNhapHang"));
            dh.setNgaygio(resultSet.getString("NgayNhap"));
            dh.setTONGGIATRI(resultSet.getFloat("TongGiaTri"));
            list_DSDH.add(dh);
        }
        connection.close();
        return list_DSDH;
    }
    
    public ArrayList layDSDNHtungaydenngay(String ngaytu, String ngayden) throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = "Select* from DONNHAPHANG where NgayNhap>='" + ngaytu + "'and NgayNhap<='" + ngayden + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        list_DSDH = new ArrayList<>();

        while (resultSet.next()) {
            DONNHAPHANGModel DNH = new DONNHAPHANGModel();
            DNH.setMADH(resultSet.getString("MaDonNhapHang"));
            DNH.setNgaygio(resultSet.getString("NgayNhap"));
            DNH.setTONGGIATRI(resultSet.getFloat("TongGiaTri"));
            list_DSDH.add(DNH);
        }
        connection.close();
        return list_DSDH;
    }
}
