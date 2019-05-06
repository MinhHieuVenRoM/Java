/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.LoginControl;
import Model.NHANVIENModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Minh Hieu
 */
public class Login extends JFrame {

    private JPanel panel2;
    protected JButton buttonDangnhap;
    protected JCheckBox checkpass;
    protected JTextField userid;
    protected JPasswordField password;
    private LoginControl controller;

    public Login() throws IOException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.setTitle("Đăng Nhập");
        this.setIconImage(new ImageIcon("src\\images\\1.png").getImage());
        this.setSize(300, 370);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        java.awt.Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
//      jf.getContentPane().setBackground(Color.decode("#FAFAFA"));
//      panel1 = new ImagePanel(new ImageIcon("C:\\Users\\User\\Desktop\\1.png").getImage());
        controller = new LoginControl();
        panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3, 2));
        JLabel name = new JLabel("User name:");
        panel2.add(name);
        name.setFont(new Font("TimesRoman", Font.BOLD, 15));
        userid = new JTextField();
        panel2.add(userid);
        JLabel pass = new JLabel("Password");
        pass.setFont(new Font("TimesRoman", Font.BOLD, 15));
        panel2.add(pass);
        password = new JPasswordField();
        panel2.add(password);
        JLabel check = new JLabel("");
        checkpass = new JCheckBox("Hiện Mật Khẩu");
        checkpass.setFont(new Font("TimesRoman", Font.PLAIN, 10));
        panel2.add(check);
        panel2.add(checkpass);
        panel2.setBounds(45, 90, 200, 70);
        checkpass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.AnHienPass(e);
                } catch (IOException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        buttonDangnhap = new JButton("Đăng Nhập");

        buttonDangnhap.setForeground(Color.decode("#00CC99"));
        buttonDangnhap.setFont(new Font("TimesRoman", Font.BOLD, 14));
        buttonDangnhap.setSize(110, 40);
        buttonDangnhap.setLocation(95, 200);
        buttonDangnhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    NHANVIENModel userModel = new NHANVIENModel(getuser(), getpass());
                    controller.ketnoi(e, userModel);
                } catch (IOException | ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        JLabel dangnhap = new JLabel("Đăng Nhập");

        dangnhap.setFont(new Font("TimesRoman", Font.BOLD, 18));
        dangnhap.setForeground(Color.decode("#009999"));
        dangnhap.setLocation(100, 30);
        dangnhap.setSize(110, 30);

//        ImageIcon img = new ImageIcon("C:\\Users\\User\\Desktop\\1.png");
//        JLabel hinh = new JLabel();
//        hinh.setLocation(0, 0);
//        hinh.setSize(300, 350);
//        hinh.setIcon(img);
//
//        jf.add(hinh);
        this.getRootPane().setDefaultButton(buttonDangnhap);
        this.add(buttonDangnhap);
        this.add(dangnhap);
//        panel1.add(panel2);

        this.add(panel2);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JButton getLoginButton() {
        return this.buttonDangnhap;
    }

    public JCheckBox getCheckBox() {
        return this.checkpass;
    }

    public String getuser() {

        return this.userid.getText();
    }

    public String getpass() {

        return this.password.getText();
    }

    public JTextField getUserid() {
        return userid;
    }

    public JPasswordField getPassword() {
        return password;
    }

}
