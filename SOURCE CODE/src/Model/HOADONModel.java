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
public class HOADONModel {

    private static Connection connection;
    private String MAHD;
    private String Ngaygio;
    private String MANV;
    private Float TONGTIENHD;
    private ArrayList<HOADONModel> DSHD;

    public String getMAHD() {
        return MAHD;
    }

    public String getNgaygio() {
        return Ngaygio;
    }

    public String getMANV() {
        return MANV;
    }

    public Float getTONGTIENHD() {
        return TONGTIENHD;
    }

    public ArrayList<HOADONModel> getDSHD() {
        return DSHD;
    }

    public void setMAHD(String MAHD) {
        this.MAHD = MAHD;
    }

    public void setNgaygio(String Ngaygio) {
        this.Ngaygio = Ngaygio;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    }

    public void setTONGTIENHD(Float TONGTIENHD) {
        this.TONGTIENHD = TONGTIENHD;
    }

    public void setDSHD(ArrayList<HOADONModel> DSHD) {
        this.DSHD = DSHD;
    }

    public ArrayList layDanhsachhoadon() throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = MSSQLControl.SELECT_HOADON;
        ResultSet resultSet = statement.executeQuery(sql);
        DSHD = new ArrayList<>();

        while (resultSet.next()) {
            HOADONModel hd = new HOADONModel();
            hd.setMAHD(resultSet.getString("MAHD"));
            hd.setNgaygio(resultSet.getString("NgayGio"));
            hd.setMANV(resultSet.getString("MANV"));
            hd.setTONGTIENHD(resultSet.getFloat("TONGTIENHD"));
            DSHD.add(hd);
        }
        connection.close();
        return DSHD;
    }

    public ArrayList layDSHDtungaydenngay(String ngaytu, String ngayden) throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = "Select* from HOADON where Ngaygio>='" + ngaytu + "'and Ngaygio<='" + ngayden + "'";
        ResultSet resultSet = statement.executeQuery(sql);
        DSHD = new ArrayList<>();

        while (resultSet.next()) {
            HOADONModel hd = new HOADONModel();
            hd.setMAHD(resultSet.getString("MAHD"));
            hd.setNgaygio(resultSet.getString("NgayGio"));
            hd.setMANV(resultSet.getString("MANV"));
            hd.setTONGTIENHD(resultSet.getFloat("TONGTIENHD"));
            DSHD.add(hd);
        }
        connection.close();
        return DSHD;
    }

}
