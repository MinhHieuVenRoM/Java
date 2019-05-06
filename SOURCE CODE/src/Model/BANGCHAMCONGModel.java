/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Nguyen Dang My Ngoc
 */

import Control.MSSQLControl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BANGCHAMCONGModel {
    private static Connection connection;
    private String MACC;
    private String Ngay;
    private String MANV;
    private String CaLam;
    private ArrayList<BANGCHAMCONGModel> DSCC;
    
    public String getMACC() {
        return MACC;
    }
    
    public String getMANV() {
        return MANV;
    }
    
    public String getNgay()
    {
        return Ngay;
    }
    
    public String getCaLam()
    {
        return CaLam;
    }
    
    public ArrayList<BANGCHAMCONGModel> getDSCC() {
        return DSCC;
    }

    public void setMACC(String MACC) {
        this.MACC = MACC;
    }

    public void setNgay(String Ngay)
    {
        this.Ngay = Ngay;
    }

    public void setMANV(String MANV) {
        this.MANV = MANV;
    }
    
    public void setCaLam(String CaLam)
    {
        this.CaLam = CaLam;
    }
    

    public void setDSCC(ArrayList<BANGCHAMCONGModel> DSCC) {
        this.DSCC = DSCC;
    }
    
    public ArrayList layDSBangCC(String MANV) throws SQLException, ClassNotFoundException
    {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String tam =  MSSQLControl.SELECT_BANGCHAMCONG ;
        String sql = tam+MANV+"'";
        System.out.println(sql);
        ResultSet resultSet = statement.executeQuery(sql);
        DSCC = new ArrayList<>();
        
        while (resultSet.next()) {
            BANGCHAMCONGModel bcc = new BANGCHAMCONGModel();
            bcc.setMACC(resultSet.getString("MaChamCong"));
            bcc.setNgay(resultSet.getString("Ngay"));
            bcc.setMANV(resultSet.getString("MANV"));
            bcc.setCaLam(resultSet.getString("CaLam"));
            DSCC.add(bcc);
        }
        connection.close();
        return DSCC;
    }
    
    public ArrayList layDSCCtrongthang(String MANV, int Thang, int Nam) throws SQLException, ClassNotFoundException
    {
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = "Select* from BANGCHAMCONG where MANV='" + MANV + "'and Thang='" + Thang + "'and Nam ='" + Nam + "' ";
        ResultSet resultSet = statement.executeQuery(sql);
        DSCC = new ArrayList<>();
        
//        while (resultSet.next()) {
//            BANGCHAMCONGModel bcc = new BANGCHAMCONGModel();
//            bcc.setMACC(resultSet.getString("MACC"));
//            bcc.setNgay(resultSet.getString("Ngay"));
//            bcc.setMANV(resultSet.getString("MANV"));
//            bcc.setCaLam(resultSet.getString("CaLam"));
//            DSCC.add(bcc);
//        }
        connection.close();
        return DSCC;
    
        
        
}
}
