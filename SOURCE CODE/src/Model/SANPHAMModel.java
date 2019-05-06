/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Control.MSSQLControl;
import java.awt.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Minh Hieu
 */
public class SANPHAMModel {

    private static Connection connection;
    private String MASP;
    private String TENSP;
    private int DONGIANHAP;
    private int DONGIABAN;
    private String DONVI;
    private String MALOAI;
    private int SOLUONGTON;
    private boolean TinhTrang;

    public void setTinhTrang(boolean TinhTrang) {
        this.TinhTrang = TinhTrang;
    }

    private ArrayList<SANPHAMModel> DSSP;

    public boolean getTinhTrang() {
        return TinhTrang;
    }

    public String getMASP() {
        return MASP;
    }

    public String getTENSP() {
        return TENSP;
    }

    public int getDONGIANHAP() {
        return DONGIANHAP;
    }

    public int getDONGIABAN() {
        return DONGIABAN;
    }

    public String getDONVI() {
        return DONVI;
    }

    public String getMALOAI() {
        return MALOAI;
    }

    public int getSOLUONGTON() {
        return SOLUONGTON;
    }

    public ArrayList getDSSP() {
        return DSSP;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public void setTENSP(String TENSP) {
        this.TENSP = TENSP;
    }

    public void setDONGIANHAP(int DONGIANHAP) {
        this.DONGIANHAP = DONGIANHAP;
    }

    public void setDONGIABAN(int DONGIABAN) {
        this.DONGIABAN = DONGIABAN;
    }

    public void setDONVI(String DONVI) {
        this.DONVI = DONVI;
    }

    public void setMALOAI(String MALOAI) {
        this.MALOAI = MALOAI;
    }

    public void setSOLUONGTON(int SOLUONGTON) {
        this.SOLUONGTON = SOLUONGTON;
    }

    public void setDSSP(ArrayList DSSP) {
        this.DSSP = DSSP;
    }

    public ArrayList layDanhSachSpDeban() throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = MSSQLControl.SELECT_SANPHAM;
        ResultSet resultSet = statement.executeQuery(sql);
        DSSP = new ArrayList<>();
        while (resultSet.next()) {
            SANPHAMModel sp = new SANPHAMModel();
            sp.setMASP(resultSet.getString("MASP"));
            sp.setTENSP(resultSet.getString("TENSP"));
            sp.setDONGIANHAP(resultSet.getInt("DonGiaNhap"));
            sp.setDONGIABAN(resultSet.getInt("DonGiaBan"));
            sp.setDONVI(resultSet.getString("DONVI"));
            sp.setMALOAI(resultSet.getString("MALOAI"));
            sp.setSOLUONGTON(resultSet.getInt("SoLuongTon"));
            sp.setTinhTrang(resultSet.getBoolean("TinhTrang"));
            DSSP.add(sp);
        }
        connection.close();
        return DSSP;
    }

}
