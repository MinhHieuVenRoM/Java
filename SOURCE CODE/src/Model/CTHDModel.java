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
 * @author Minh Hieu
 */
public class CTHDModel {

    private static Connection connection;
    private String MAHD;
    private String MASP;
    private int SoLuong;
    private Float DONGIAHIENTAI;
    private Float ThanhTien;
    ArrayList<CTHDModel> DSCTHD;

    public ArrayList layDSCTHD(String MAHD) throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = MSSQLControl.SELECT_CTHD+MAHD+"'";
        ResultSet resultSet = statement.executeQuery(sql);
        DSCTHD = new ArrayList<>();

        while (resultSet.next()) {
            CTHDModel ct = new CTHDModel();
            ct.setMAHD(resultSet.getString("MAHD"));
            ct.setMASP(resultSet.getString("MASP"));
            ct.setSoLuong(resultSet.getInt("SOLUONG"));
            ct.setDONGIAHIENTAI(resultSet.getFloat("DONGIAHIENTAI"));
            ct.setThanhTien(resultSet.getFloat("THANHTIEN"));
            DSCTHD.add(ct);
        }
        connection.close();
        return DSCTHD;
    }

    public String getMAHD() {
        return MAHD;
    }

    public String getMASP() {
        return MASP;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public Float getDONGIAHIENTAI() {
        return DONGIAHIENTAI;
    }

    public Float getThanhTien() {
        return ThanhTien;
    }

    public ArrayList<CTHDModel> getDSCTHD() {
        return DSCTHD;
    }

    public void setMAHD(String MAHD) {
        this.MAHD = MAHD;
    }

    public void setMASP(String MASP) {
        this.MASP = MASP;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public void setDONGIAHIENTAI(Float DONGIAHIENTAI) {
        this.DONGIAHIENTAI = DONGIAHIENTAI;
    }

    public void setThanhTien(Float ThanhTien) {
        this.ThanhTien = ThanhTien;
    }

    public void setDSCTHD(ArrayList<CTHDModel> DSCTHD) {
        this.DSCTHD = DSCTHD;
    }

}
