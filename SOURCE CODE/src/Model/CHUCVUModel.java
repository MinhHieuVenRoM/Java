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
public class CHUCVUModel {

    private static Connection connection;
    private String MACHUCVU;
    private String TENCHUCVU;
    private String HeSo;
    private ArrayList<CHUCVUModel> DSCV;

    public String getHeSo() {
        return HeSo;
    }

    public void setHeSo(String HeSo) {
        this.HeSo = HeSo;
    }

    public String getMACHUCVU() {
        return MACHUCVU;
    }

    public String getTENCHUCVU() {
        return TENCHUCVU;
    }

    public void setMACHUCVU(String MACHUCVU) {
        this.MACHUCVU = MACHUCVU;
    }

    public void setTENCHUCVU(String TENCHUCVU) {
        this.TENCHUCVU = TENCHUCVU;
    }

    public ArrayList layDanhsachchucvu() throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = "select * from CHUCVU";
        ResultSet resultSet = statement.executeQuery(sql);
        DSCV = new ArrayList<>();

        while (resultSet.next()) {
            CHUCVUModel cv = new CHUCVUModel();
            cv.setMACHUCVU(resultSet.getString("MACHUCVU"));
            cv.setTENCHUCVU(resultSet.getString("TENCHUCVU"));
            cv.setHeSo(resultSet.getString("HESO"));
            DSCV.add(cv);
        }
        connection.close();
        return DSCV;
    }
}
