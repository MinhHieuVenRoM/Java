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

/**
 *
 * @author ADMIN
 */
public class CHITIETNHAPHANGModel {
    private static Connection connection;
    private String MADH;
    private String MASP;
    private int SoLuong;
    private Float DONGIANHAP;
    ArrayList<CHITIETNHAPHANGModel> list_DSCTDH;
    
    public String getMADH() {
        return MADH;
    }

    public String getMASP() {
        return MASP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public Float getDONGIANHAP() {
        return DONGIANHAP;
    }

    public ArrayList<CHITIETNHAPHANGModel> getlist_DSCTDH() {
        return list_DSCTDH;
    }

    public void setMADH(String MADH) {
        this.MADH = MADH;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public void setDONGIANHAP(Float DONGIANHAP) {
        this.DONGIANHAP = DONGIANHAP;
    }

    public void setlist_DSCTDH(ArrayList<CHITIETNHAPHANGModel> list_DSCTDH) {
        this.list_DSCTDH = list_DSCTDH;
    }
    
    public ArrayList layDSCTNH(String MADH) throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = MSSQLControl.SELECT_CHITIETNHAPHANG+MADH+"'";
        ResultSet resultSet = statement.executeQuery(sql);
        list_DSCTDH = new ArrayList<>();

        while (resultSet.next()) {
            CHITIETNHAPHANGModel ct = new CHITIETNHAPHANGModel();
            ct.setMASP(resultSet.getString("MASP"));
            ct.setMADH(resultSet.getString("MaDonNhapHang"));
            ct.setSoLuong(resultSet.getInt("SoLuong"));
            ct.setDONGIANHAP(resultSet.getFloat("DonGiaNhap"));
            list_DSCTDH.add(ct);
        }
        connection.close();
        return list_DSCTDH;
    }
}
