/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.MSSQLControl;
import Control.QLNV_ADControl;
import Model.CHUCVUModel;
import Model.NHANVIENModel;
import com.itextpdf.text.DocumentException;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh Hieu
 */
public class Quanlynhanvien extends JFrame {

    //event
    private QLNV_ADControl controller;
    //
    JPanel QLNVPanel;
    JPanel leftP;
    JLabel inflb;
    JLabel findlb;
    JTextField findtf;
    JPanel rightP;
    JLabel changelb;
    //
    JLabel id_stafflb;
    JTextField id_stafftf;
    //
    JLabel name_stafflb;
    JTextField name_stafftf;
    //
    JLabel birthdaylb;
    JDateChooser birthdaytf;
    //
    JLabel daytoworklb;
    JDateChooser daytoworktf;
    //
    JLabel positionlb;
    JTextField positiontf;
    //
    JLabel bonuslb;
    JLabel bonusf;
    //
    JLabel passlb;
    JTextField passtf;
    //
    JLabel sexlb;
    JTextField sextf;
    JLabel rulelb;
    //
    JTextField sefttf;
    //
    JTable table;
    DefaultTableModel defaultTableModelNV;
    JLabel adrlb, numlb;
    JButton exitbt, editbt, addbt, deletebt, findbt;
    JTextField adrtf, numtf;
    JLabel headerlb;
    //

    public Quanlynhanvien(String MANV,int Capbac) throws ClassNotFoundException, SQLException {
        this.setTitle("Quản lý Cửa hàng tiện lợi");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);//Khong su dung Layout Manager  
        this.setSize(1015, 660);//Do rong la 1015 va chieu cao la 695
        QLNVPanel = new JPanel();
        QLNVPanel.setLayout(null);
        headerlb = new JLabel("Quản lý Nhân viên");
        headerlb.setFont(new Font("TimesRoman", Font.BOLD, 24));
        headerlb.setForeground(Color.gray);
        ////

        leftP = new JPanel();
        leftP.setLayout(null);
        leftP.setBounds(20, 50, 400, 600);
        inflb = new JLabel("Thông tin nhân viên: ");
        inflb.setFont(new Font("TimesRoman", Font.BOLD, 16));
        inflb.setBounds(0, 0, 170, 20);
        inflb.setForeground(Color.red);
        //

        //
        findlb = new JLabel("Tìm kiếm: ");
        findlb.setFont(new Font("TimesRoman", Font.ITALIC, 15));
        findlb.setBounds(0, 30, 80, 20);
        findlb.setForeground(Color.black);
        //
        findbt = new JButton("Tìm");
        findbt.setFont(new Font("TimesRoman", Font.BOLD, 15));
        findbt.setBounds(260, 25, 60, 30);
        findbt.setForeground(Color.black);
        //
        findbt.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                defaultTableModelNV = controller.themnvvaobangnv(findtf.getText());
                table.setModel(defaultTableModelNV);
                findtf.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    );
        //

        findtf = new JTextField("Họ và tên");
        findtf.setBounds(70, 30, 180, 25);
        findtf.addMouseListener(new MouseAdapter(){ 
        @Override 
        public void mouseClicked(MouseEvent e){ 
        findtf.setText(""); 
            } 
        });
        //
        defaultTableModelNV = new DefaultTableModel();  
        controller = new QLNV_ADControl();
        defaultTableModelNV = controller.themnvvaobangnv("");
        table = new JTable();
        table.setModel(defaultTableModelNV);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(5, 60, 350, 450);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = table.getSelectedRow();
                try {
                    NHANVIENModel a = controller.LoadNV(((String)(defaultTableModelNV.getValueAt(index, 0))));
                    CHUCVUModel b = controller.LoadCV(((String)(defaultTableModelNV.getValueAt(index, 2))));
                    id_stafftf.setText((String)(a.getMaNV()));
                    id_stafftf.setEditable(false);
                    name_stafftf.setText((String)(a.getTenNV()));
                    positiontf.setText((String)(a.getMaChucVu()));
                    birthdaytf.setDate(a.getNgaySinh());
                    daytoworktf.setDate(a.getNgayVaoLam());
                    adrtf.setText((String)(a.getDiaChi()));
                    numtf.setText((String)(a.getSDT()));
                    passtf.setText((String)(a.getMatKhau()));
                    bonusf.setText((String)(b.getHeSo()));
                    sextf.setText((String)(a.getGioiTinh()));
                    sefttf.setText((String)(a.getGhiChu()));
                } catch (SQLException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                }           
            }
        });
        leftP.add(inflb);
        leftP.add(findlb);
        leftP.add(findtf);
        leftP.add(findbt);
        leftP.add(sp);
        //findtf.setBorder(BorderFactory.createCompoundBorder());
        rightP = new JPanel();
        rightP.setLayout(null);
        rightP.setBounds(425, 50, 580, 600);
        changelb = new JLabel("Thay đổi thông tin:");
        changelb.setFont(new Font("TimesRoman", Font.BOLD, 16));
        changelb.setForeground(Color.red);
        changelb.setBounds(20, 0, 170, 20);
        //
        id_stafflb = new JLabel("*Mã nhân viên: ");
        id_stafflb.setFont(new Font("TimesRoman", Font.BOLD, 15));
        id_stafflb.setForeground(Color.black);
        id_stafflb.setBounds(5, 35, 110, 20);
        
        //
        id_stafftf = new JTextField();
        id_stafftf.setFont(new Font("TimesRoman", Font.BOLD, 14));
        id_stafftf.setForeground(Color.red);
        id_stafftf.setBounds(120, 30, 90, 27);
        //
        name_stafflb = new JLabel("Họ và tên: ");
        name_stafflb.setFont(new Font("TimesRoman", Font.BOLD, 15));
        name_stafflb.setForeground(Color.black);
        name_stafflb.setBounds(220, 35, 110, 20);
        //
        name_stafftf = new JTextField();
        name_stafftf.setFont(new Font("TimesRoman", Font.BOLD, 13));
        name_stafftf.setForeground(Color.black);
        name_stafftf.setBounds(310, 30, 180, 27);
        name_stafftf.addMouseListener(new MouseAdapter(){ 
        @Override 
        public void mouseClicked(MouseEvent e){ 
        name_stafftf.setText(""); 
            } 
        });
        
        //
        birthdaylb = new JLabel("Ngày sinh:");
        birthdaylb.setFont(new Font("TimesRoman", Font.ITALIC, 15));
        birthdaylb.setForeground(Color.black);
        birthdaylb.setBounds(5, 65, 110, 20);

        birthdaytf = new JDateChooser();
        birthdaytf.setFont(new Font("TimesRoman", Font.ITALIC, 15));
        birthdaytf.setForeground(Color.black);
        birthdaytf.setBounds(120, 65, 180, 27);
        //
        daytoworklb = new JLabel("Ngày vào làm:");
        daytoworklb.setFont(new Font("TimesRoman", Font.ITALIC, 15));
        daytoworklb.setForeground(Color.black);
        daytoworklb.setBounds(5, 95, 150, 20);

        daytoworktf = new JDateChooser();
        daytoworktf.setFont(new Font("TimesRoman", Font.ITALIC, 15));
        daytoworktf.setForeground(Color.black);
        daytoworktf.setBounds(120, 95, 150, 27);
        //
        positionlb = new JLabel("Mã chức vụ: ");
        positionlb.setFont(new Font("TimesRoman", Font.BOLD, 15));
        positionlb.setForeground(Color.black);
        positionlb.setBounds(300, 95, 110, 20);

        positiontf = new JTextField("Chức vụ");
        positiontf.setFont(new Font("TimesRoman", Font.ITALIC, 13));
        positiontf.setForeground(Color.black);
        positiontf.setBounds(400, 90, 80, 27);
        
        //
        bonuslb = new JLabel("*Hệ số lương: ");
        bonuslb.setFont(new Font("TimesRoman", Font.BOLD, 15));
        bonuslb.setForeground(Color.black);
        bonuslb.setBounds(5, 201, 150, 20);

        bonusf = new JLabel("Hệ số...");
        bonusf.setFont(new Font("TimesRoman", Font.ITALIC, 13));
        bonusf.setForeground(Color.black);
        bonusf.setBounds(120, 201, 70, 20);
        //

        //
        passlb = new JLabel("Mật khẩu: ");
        passlb.setFont(new Font("TimesRoman", Font.BOLD, 15));
        passlb.setForeground(Color.black);
        passlb.setBounds(190, 200, 150, 20);

        passtf = new JTextField("   password...");
        passtf.setFont(new Font("TimesRoman", Font.BOLD, 13));
        passtf.setForeground(Color.red);
        passtf.setBounds(260, 195, 180, 27);
        
    //
        numlb = new JLabel("Số điện thoại:");
        numlb.setFont(new Font("TimesRoman", Font.ITALIC, 15));
        numlb.setForeground(Color.black);
        numlb.setBounds(315, 65, 90, 20);

        numtf = new JTextField("   số điện thoại...");
        numtf.setFont(new Font("TimesRoman", Font.ITALIC, 13));
        numtf.setForeground(Color.black);
        numtf.setBounds(405, 60, 150, 25);
        //
        numtf.addMouseListener(new MouseAdapter(){ 
        @Override 
        public void mouseClicked(MouseEvent e){ 
        numtf.setText(""); 
            } 
        });
        //
        rulelb = new JLabel("(*) Không thể thay đổi thông tin");
        rulelb.setFont(new Font("TimesRoman", Font.ITALIC, 13));
        rulelb.setForeground(Color.red);
        rulelb.setBounds(5, 250, 200, 20);
        //
        sefttf = new JTextField("Sở trường, năng lực .....");
        sefttf.setFont(new Font("TimesRoman", Font.ITALIC, 15));
        sefttf.setForeground(Color.black);
        sefttf.setBounds(5, 270, 500, 200);
        //
        adrlb = new JLabel("Địa chỉ: ");
        adrlb.setFont(new Font("TimesRoman", Font.BOLD, 15));
        adrlb.setForeground(Color.black);
        adrlb.setBounds(5, 130, 200, 20);
        //
        adrtf = new JTextField("   địa chỉ...");
        adrtf.setFont(new Font("TimesRoman", Font.ITALIC, 13));
        adrtf.setForeground(Color.black);
        adrtf.setBounds(65, 125, 350, 27);
        
        adrtf.addMouseListener(new MouseAdapter(){ 
        @Override 
        public void mouseClicked(MouseEvent e){ 
        adrtf.setText(""); 
            } 
        });
    //
        sexlb = new JLabel("Giới tính: ");
        sexlb.setFont(new Font("TimesRoman", Font.BOLD, 15));
        sexlb.setBounds(5, 160, 200, 20);
        sexlb.setForeground(Color.black);
        
        sextf = new JTextField("Nam/Nữ ...");
        sextf.setFont(new Font("TimesRoman", Font.ITALIC, 12));
        sextf.setBounds(75, 157, 100, 27);
        sextf.setForeground(Color.black);
    //
        exitbt = new JButton("Null !");
        exitbt.setFont(new Font("TimesRoman", Font.BOLD, 13));
        exitbt.setForeground(Color.black);
        exitbt.setBounds(400, 500, 100, 40);
        exitbt.addMouseListener(new MouseAdapter(){ 
        @Override 
        public void mouseClicked(MouseEvent e){ 
            id_stafftf.setText(""); 
            id_stafftf.setEditable(true);
            name_stafftf.setText(""); 
            positiontf.setText(""); 
            adrtf.setText(""); 
            sextf.setText(""); 
            passtf.setText(""); 
            numtf.setText(""); 
            sefttf.setText(""); 
        
            } 
        });

        addbt = new JButton("Thêm !");
        addbt.setFont(new Font("TimesRoman", Font.BOLD, 13));
        addbt.setForeground(Color.red);
        addbt.setBounds(70, 500, 100, 40);
        //
        addbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    //                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String bdate = sdf.format(birthdaytf.getDate());
//                String wdate = sdf.format(daytoworktf.getDate());
//                NHANVIENModel flag = new NHANVIENModel();
//                flag.setMaNV(id_stafftf.getText());
//                flag.setTenNV(name_stafftf.getText());
//                flag.setNgaySinh(birthdaytf.getDate());
//                flag.setGioiTinh(sextf.getText());
//                flag.setDiaChi(adrtf.getText());
//                flag.setMaChucVu(positiontf.getText());
//                flag.setNgayVaoLam(daytoworktf.getDate());
//                flag.setSDT(numtf.getText());
//                flag.setMatKhau(passtf.getText());
//                flag.setGhiChu(sefttf.getText());
                controller.SaveNV(e);
                } catch (IOException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                }
//                String query = "insert into NHANVIEN values ('" + id_stafftf.getText() + "',N'" + name_stafftf.getText()+ "','" + bdate +"','" + sextf.getText()+"','" + adrtf.getText()+"','" + positiontf.getText()+"','" + wdate + "','" + numtf.getText()+"','" + passtf.getText()+"','" + 1 +"','" + sefttf.getText()+"')";
//                MSSQLControl kn = new MSSQLControl();
//                try {
//                    Connection cnn = (Connection) kn.getConnect();
//                    int st = cnn.createStatement().executeUpdate(query);
//                    JOptionPane.showMessageDialog(null, "Thêm thành công");
//                } catch (SQLException ex) {
//                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
//                    JOptionPane.showMessageDialog(null, "Sai dữ liệu");
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
//                }
                try {
                defaultTableModelNV = controller.themnvvaobangnv("");
                table.setModel(defaultTableModelNV);
                findtf.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
        });
        //

        editbt = new JButton("Sửa !");
        editbt.setFont(new Font("TimesRoman", Font.BOLD, 13));
        editbt.setForeground(Color.red);
        editbt.setBounds(180, 500, 100, 40);
        
        editbt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                String bdate = sdf.format(birthdaytf.getDate());
//                String wdate = sdf.format(daytoworktf.getDate());
                NHANVIENModel flag = new NHANVIENModel();
                flag.setTenNV(name_stafftf.getText());
                flag.setNgaySinh(birthdaytf.getDate());
                flag.setGioiTinh(sextf.getText());
                flag.setDiaChi(adrtf.getText());
                flag.setMaChucVu(positiontf.getText());
                flag.setNgayVaoLam(daytoworktf.getDate());
                flag.setSDT(numtf.getText());
                flag.setMatKhau(passtf.getText());
                flag.setGhiChu(sefttf.getText());
                flag.setMaNV(id_stafftf.getText());
                try {
                    controller.EditNV(e);
                } catch (IOException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                }
//                String query = "Update NHANVIEN set TenNV = N'"+ name_stafftf.getText() +"',NgaySinh = '"+bdate+"',DiaChi = '"+adrtf.getText()+"',MaChucVu = '"+positiontf.getText()+"',GioiTinh = '"+sextf.getText()+"',NgayVaoLam = '"+wdate+"',SDT = '"+numtf.getText()+"',MatKhau = '"+passtf.getText()+"',GhiChu = '"+sefttf.getText()+"' where MaNV = '"+id_stafftf.getText()+"'";
//                MSSQLControl kn = new MSSQLControl();
//                try {
//                    Connection cnn = (Connection) kn.getConnect();
//                    int st = cnn.createStatement().executeUpdate(query);
//                    JOptionPane.showMessageDialog(null, "Chỉnh sửa thành công");
//                } catch (SQLException ex) {
//                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
//                    JOptionPane.showMessageDialog(null, "Sai dữ liệu");
//                } catch (ClassNotFoundException ex) {
//                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
//                }
                try {
                    defaultTableModelNV = controller.themnvvaobangnv("");
                    table.setModel(defaultTableModelNV);
                    findtf.setText("");
                } catch (SQLException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
            
        });    


        deletebt = new JButton("Xóa !");
        deletebt.setFont(new Font("TimesRoman", Font.BOLD, 13));
        deletebt.setForeground(Color.red);
        deletebt.setBounds(290, 500, 100, 40);
        //
        deletebt.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String query = "update NHANVIEN set TinhTrang = '" +0+ "' Where MaNV ='"+ id_stafftf.getText() +"'";
                MSSQLControl kn = new MSSQLControl();
                try {
                    Connection cnn = (Connection) kn.getConnect();
                    int st = cnn.createStatement().executeUpdate(query);
                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                    id_stafftf.setText(""); 
                    id_stafftf.setEditable(true);
                    name_stafftf.setText(""); 
                    positiontf.setText(""); 
                    adrtf.setText(""); 
                    sextf.setText(""); 
                    passtf.setText(""); 
                    numtf.setText(""); 
                    sefttf.setText(""); 
                } catch (SQLException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                defaultTableModelNV = controller.themnvvaobangnv("");
                table.setModel(defaultTableModelNV);
                findtf.setText("");
            } catch (SQLException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            
        });    
        //

        rightP.add(changelb);
        rightP.add(id_stafflb);
        rightP.add(id_stafftf);
        rightP.add(name_stafflb);
        rightP.add(name_stafftf);
        rightP.add(birthdaylb);
        rightP.add(birthdaytf);
        rightP.add(daytoworklb);
        rightP.add(daytoworktf);
        rightP.add(positionlb);
        rightP.add(positiontf);
        rightP.add(bonuslb);
        rightP.add(bonusf);
        rightP.add(passlb);
        rightP.add(passtf);
        rightP.add(rulelb);
        rightP.add(sefttf);
        rightP.add(adrlb);
        rightP.add(adrtf);
        rightP.add(exitbt);
        rightP.add(addbt);
        rightP.add(editbt);
        rightP.add(deletebt);
        rightP.add(numlb);
        rightP.add(numtf);
        rightP.add(sexlb);
        rightP.add(sextf);
        ////

        JButton quaylaiButton = new JButton("Quay lại");
        quaylaiButton.setBounds(10, 20, 80, 30);
        quaylaiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.QuayLai(e);
                    new ManHinhChinh(MANV,Capbac);
                } catch (IOException ex) {
                    Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                } catch (DocumentException ex) {
                    Logger.getLogger(Quanlynhanvien.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        

        headerlb.setBounds(400, 0, 300, 40);
        headerlb.setBackground(Color.GREEN);
        QLNVPanel.add(headerlb);
        QLNVPanel.add(leftP);
        QLNVPanel.add(rightP);
        QLNVPanel.setBounds(0, 0, 1015, 660); 
        QLNVPanel.add(quaylaiButton);
        this.add(QLNVPanel);
        this.setVisible(true);//Tao Frame la co the nhin thay (visible)  
        this.setLocationRelativeTo(null);
    }
    
    public JTextField getMaNV(){
        return id_stafftf;
    }
    public JTextField getTen(){
    return name_stafftf;
    }
    public JTextField getGioiTinh(){
        return sextf;
    }
    public JDateChooser getNgayVaoLam(){
        return daytoworktf;
    }
    public JDateChooser getNgaySinh(){
        return birthdaytf;
    }
    public JTextField getSDT(){
        return numtf;
    }
    public JTextField getGhiChu(){
        return sefttf;
    }
    public JTextField getMaChucVu(){
        return positiontf;
    }
    public JTextField getDiaChi(){
        return adrtf;
    }
    public JTextField getMatKhau(){
        return passtf;
    }
    
    public JLabel getHeSo(){
        return bonusf;
    }
    
    public DefaultTableModel getBangNV(){
        return defaultTableModelNV;
    }
    
                
}
