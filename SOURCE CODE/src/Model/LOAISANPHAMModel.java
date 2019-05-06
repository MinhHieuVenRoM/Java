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
public class LOAISANPHAMModel {

    private String MALOAI;
    private String TENLOAI;
    private ArrayList<LOAISANPHAMModel> Dsloai;
    private static Connection connection;

    public String getMALOAI() {
        return MALOAI;
    }

    public String getTENLOAI() {
        return TENLOAI;
    }

    public ArrayList<LOAISANPHAMModel> getDsloai() {
        return Dsloai;
    }

    public void setMALOAI(String MALOAI) {
        this.MALOAI = MALOAI;
    }

    public void setTENLOAI(String TENLOAI) {
        this.TENLOAI = TENLOAI;
    }

    public void setDsloai(ArrayList<LOAISANPHAMModel> Dsloai) {
        this.Dsloai = Dsloai;
    }

    public void layDanhsachloaisanpham() throws SQLException, ClassNotFoundException {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = MSSQLControl.SELECT_LOAISANPHAM;
        ResultSet resultSet = statement.executeQuery(sql);
        Dsloai = new ArrayList<>();

        while (resultSet.next()) {
            LOAISANPHAMModel loai = new LOAISANPHAMModel();
            loai.setMALOAI(resultSet.getString("MALOAI"));
            loai.setTENLOAI(resultSet.getString("TENLOAI"));
            Dsloai.add(loai);
        }
        connection.close();
    }

    public int demSoloaisanpham() {
        int i = 0;
        for (LOAISANPHAMModel loai : Dsloai) {
            i++;
        }
        return i;
    }
}
