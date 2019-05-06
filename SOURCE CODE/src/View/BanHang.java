
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Control.BanHangControl;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Minh Hieu
 */
public class BanHang extends JFrame {

    private DefaultTableModel defaultTableModelSanPham, defaultTableModelbanhang;
    private JPanel panel1, panel2;
    private JTable tbsanpham, tbban;
    private JTextField textmasp, textkh, textSl, textmahd, texttongtien, texttienthua, texttiendua;
    private Connection connection;
    private BanHangControl controller;
    private JButton quaylaiButton, timkiem, Thanhtoan;
    private JComboBox cbbnhanvien, cbbLoai;
    private String maloai = "TẤT CẢ";
    private JDateChooser ngayChooser;

    public BanHang(String MaNV, int capbac) throws SQLException, ClassNotFoundException {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, true);
        this.setTitle("Đăng Nhập");
        this.setIconImage(new ImageIcon("D:\\java\\Thu\\1.png").getImage());
        this.setSize(1000, 660);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        controller = new BanHangControl();
        quaylaiButton = new JButton("Quay lại");
        quaylaiButton.setBounds(10, 20, 80, 30);

        quaylaiButton.addActionListener((ActionEvent e) -> {
            try {
                controller.QuayLai(e, MaNV, capbac);
            } catch (IOException ex) {
                Logger.getLogger(Quanlytinthongcanhan.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException | SQLException ex) {
                Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.add(quaylaiButton);
        //panel
        panel1 = new JPanel();
        panel1.setLayout(null);
        JLabel name = new JLabel("Mã hàng");
        name.setBounds(5, 10, 50, 25);
        textmasp = new JTextField();
        textmasp.setBounds(60, 10, 100, 25);
        cbbLoai = new JComboBox(controller.Layloaisanpham());
        cbbLoai.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                try {
                    String maloai = controller.loadmaloaisp(e);
                    defaultTableModelSanPham = controller.themspvaobangsp(textmasp.getText(), maloai);
                    tbsanpham.setModel(defaultTableModelSanPham);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        cbbLoai.setBounds(190, 10, 125, 25);
        cbbLoai.setSelectedIndex(0);
        timkiem = new JButton("Tìm");
        timkiem.setBounds(330, 10, 55, 25);
        defaultTableModelSanPham = controller.themspvaobangsp(textmasp.getText(), maloai);
        timkiem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    maloai = controller.loadmaloai(e);
                    defaultTableModelSanPham = controller.themspvaobangsp(textmasp.getText(), maloai);
                    tbsanpham.setModel(defaultTableModelSanPham);
                    textmasp.setText("");
                } catch (SQLException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        );
        panel1.add(name);
        panel1.add(textmasp);
        panel1.add(cbbLoai);
        panel1.add(timkiem);
        panel1.setBounds(15, 70, 390, 38);
        panel1.setBackground(Color.decode("#99CCCC"));
        this.add(panel1);

        JLabel hoadon = new JLabel("Hóa Đơn");
        hoadon.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 35));
        hoadon.setBounds(370, 0, 150, 70);
        hoadon.setForeground(Color.decode("#009999"));
        this.add(hoadon);
        //panel2
        panel2 = new JPanel();
        panel2.setLayout(null);

        JLabel mahang = new JLabel("Khách hàng");
        mahang.setBounds(14, 14, 75, 25);
        textkh = new JTextField();
        textkh.setBounds(90, 14, 360, 25);

        JLabel sohd = new JLabel("Số HĐ");
        sohd.setBounds(320, 65, 33, 17);
        textmahd = new JTextField();
        textmahd.setBounds(376, 60, 69, 25);
        textmahd.setEditable(false);

        JLabel Nv = new JLabel("Nhân Viên");
        Nv.setBounds(14, 100, 76, 25);

        if (capbac == 1) {
            cbbnhanvien = new JComboBox(controller.layDsnhanvien());
            cbbnhanvien.setBounds(90, 100, 200, 25);
        } else {
            cbbnhanvien = new JComboBox(controller.laytennhanvien(MaNV));
            cbbnhanvien.setBounds(90, 100, 200, 25);
            cbbnhanvien.disable();

        }

        JLabel ngay = new JLabel("Ngày");
        ngay.setBounds(14, 63, 28, 17);
        ngayChooser = new JDateChooser();
        ngayChooser.setDateFormatString("yyyy-MM-dd");
        ngayChooser.setBounds(150, 60, 150, 25);

        //lay ngay
        JTextField ngayJTextField = new JTextField();
        ngayJTextField.setEditable(false);
        ngayJTextField.setBounds(150, 60, 150, 25);

        Date today = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngayString = dateFormat.format(today.getTime());
        ngayJTextField.setText(ngayString);

        //lay gio
        JTextField gio = new JTextField();
        gio.setEditable(false);
        gio.setBounds(90, 60, 55, 25);

        DateFormat dateFormatgio = new SimpleDateFormat("HH:mm:ss");
        String gioString = dateFormatgio.format(today.getTime());
        gio.setText(gioString);
        panel2.add(gio);

//        panel2.add(ngayChooser);
        panel2.add(ngayJTextField);
        panel2.add(sohd);
        panel2.add(textmahd);
        panel2.add(Nv);
        panel2.add(cbbnhanvien);
        //bang hang hoa
        tbban = new JTable();
        defaultTableModelbanhang = controller.themthuoctinhbangspduocchon();
        tbban.setModel(defaultTableModelbanhang);

        TableColumnModel columnModel = tbban.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(150);
        JScrollPane jScrollPaneban = new JScrollPane(tbban);
        jScrollPaneban.setBounds(0, 130, 500, 250);
        panel2.setBounds(480, 70, 500, 510);
        panel2.setBackground(Color.decode("#99CCCC"));

        panel2.add(jScrollPaneban);
        tbban.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    controller.chinhsuSoluong(e);
                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }
//

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        panel2.add(mahang);
        panel2.add(textkh);
        panel2.add(ngay);

        JLabel tongtien = new JLabel("Tổng tiền");
        tongtien.setBounds(14, 393, 75, 25);
        texttongtien = new JTextField();
        texttongtien.setBounds(100, 393, 184, 25);
        texttongtien.setEditable(false);
        panel2.add(tongtien);
        panel2.add(texttongtien);

        JLabel tiendua = new JLabel("Tiền Khách Đưa");
        tiendua.setBounds(14, 433, 87, 25);
        texttiendua = new JTextField();
        texttiendua.setBounds(100, 433, 184, 25);
        texttiendua.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                int tiendua = Integer.parseInt(texttiendua.getText());
                int kq = tiendua - Integer.parseInt(controller.tinhtongTienthanhtoan());
                texttienthua.setText(String.valueOf(kq));
            }

            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
            }
        });
        panel2.add(tiendua);
        panel2.add(texttiendua);

        JLabel tienthua = new JLabel("Tiền Thừa");
        tienthua.setBounds(14, 470, 94, 25);
        texttienthua = new JTextField();
        texttienthua.setBounds(101, 470, 184, 25);
        texttienthua.setEditable(false);

        panel2.add(tienthua);
        panel2.add(texttienthua);

//        JLabel giamgia = new JLabel("Giảm giá");
//        giamgia.setBounds(297, 393, 55, 25);
//        JTextField textgiamgia = new JTextField();
//        textgiamgia.setBounds(352, 393, 90, 25);
//        panel2.add(giamgia);
//        panel2.add(textgiamgia);
//
//        JComboBox cbbloaigiamgia = new JComboBox();
//        cbbloaigiamgia.addItem("$");
//        cbbloaigiamgia.addItem("%");
//        cbbloaigiamgia.setBounds(450, 393, 40, 25);
//        panel2.add(cbbloaigiamgia);
        this.add(panel2);

        Thanhtoan = new JButton();
        Thanhtoan.setBounds(342, 433, 138, 60);
        Thanhtoan.setText("Thanh Toán");
        Thanhtoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (texttongtien.getText().isEmpty() == false && textmahd.getText().isEmpty() == false) {
                        controller.thanhtoanHoaDon(e, textmahd.getText(), tbban);
                        while (defaultTableModelbanhang.getRowCount() > 0) {
                            defaultTableModelbanhang.removeRow(0);
                        }
                        textmahd.setText(null);
                        texttongtien.setText(null);
                        texttiendua.setText(null);
                        texttienthua.setText(null);
                        textkh.setText(null);
                    } else {

                        JOptionPane.showMessageDialog(null, "Hóa Đơn chưa được tạo hoặc hóa đơn chưa có sản phẩm", "Lỗi Hóa Đơn", 1);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        panel2.add(Thanhtoan);

        JButton taohoadon = new JButton();
        taohoadon.setBounds(407, 450, 70, 60);
        taohoadon.setMargin(new Insets(5, 5, 5, 5));
        taohoadon.setText("<html><center>" + "Tạo Mới" + "<br>" + "Hóa Đơn" + "</center></html>");
        taohoadon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String thoigian = df.format(ngayChooser.getDate()) + " " + gio.getText();//xử lý thời gian đưa vào YYYY-MM-DD HH::mm::ss
                    if (capbac == 2) {
                        controller.loadcbbnv(e, MaNV);
                    }
                    controller.themHoadonmoi(e, thoigian);
                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
                textSl.setText("1");
            }
        });
        this.add(taohoadon);

        JLabel Soluong = new JLabel("Số lượng");
        Soluong.setBounds(420, 220, 60, 20);
        textSl = new JTextField();

        textSl.setDocument(new DigitsDocument());
        textSl.setText("1");
        textSl.setBounds(407, 250, 70, 25);
        this.add(Soluong);
        this.add(textSl);

        JButton them = new JButton();
        them.setBounds(407, 180, 70, 32);
        them.setText("Thêm >>");
        them.setMargin(new Insets(0, -5, 0, 0));
        them.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.themspvaobangspchon(textSl.getText());
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
                textSl.setText("1");
                texttongtien.setText(controller.tinhtongTienthanhtoan());
            }
        });
        this.add(them);
        JButton suaButton = new JButton();
        suaButton.setBounds(407, 300, 70, 32);
        suaButton.setText("Sửa");
        suaButton.setMargin(new Insets(0, -5, 0, 0));
        suaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.suaSoluong(e);
                    tbban.setModel(defaultTableModelbanhang);
                    texttongtien.setText(controller.tinhtongTienthanhtoan());

                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }
        );

        this.add(suaButton);
        JButton xoa = new JButton();
        xoa.setBounds(407, 350, 70, 32);
        xoa.setText("Xóa");
        xoa.setMargin(new Insets(0, -5, 0, 0));
        xoa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.xoaSanphamdachon(e);
                    tbban.setModel(defaultTableModelbanhang);
                    texttongtien.setText(controller.tinhtongTienthanhtoan());

                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }
        );
        this.add(xoa);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");
        JMenu editMenu = new JMenu("Nhân viên");
        JMenu aboutMenu = new JMenu("Liên hệ");

        menuBar.add(fileMenu);

        menuBar.add(editMenu);

        menuBar.add(aboutMenu);
        tbsanpham = new JTable(defaultTableModelSanPham);
        TableColumnModel tcm = tbsanpham.getColumnModel();
        tcm.getColumn(1).setPreferredWidth(150);
        tbsanpham.getModel().addTableModelListener(tbsanpham);
        JScrollPane jScrollPane = new JScrollPane(tbsanpham);

        jScrollPane.setBounds(15, 110, 390, 470);
        tbsanpham.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e
            ) {
                try {
                    controller.LaySanPhamTuTable(e);
                } catch (IOException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(BanHang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mousePressed(MouseEvent e
            ) {
            }
//

            @Override
            public void mouseReleased(MouseEvent e
            ) {
            }

            @Override
            public void mouseEntered(MouseEvent e
            ) {
            }

            @Override
            public void mouseExited(MouseEvent e
            ) {
            }
        });

        this.add(jScrollPane);

        this.setJMenuBar(menuBar);

        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public JComboBox getNhanvien() {
        return cbbnhanvien;
    }

    public JComboBox getCbbLoai() {
        return cbbLoai;
    }

    public JTable getJt() {
        return tbsanpham;
    }

    public JTable getTbban() {
        return tbban;
    }

    public DefaultTableModel getDefaultTableModelSanPham() {
        return defaultTableModelSanPham;
    }

    public DefaultTableModel getDefaultTableModel2() {
        return defaultTableModelbanhang;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public JPanel getPanel2() {
        return panel2;
    }

    public JTextField gettextmasp() {
        return textmasp;
    }

    public JTextField getTextkh() {
        return textkh;
    }

    public Connection getConnection() {
        return connection;
    }

    public BanHangControl getController() {
        return controller;
    }

    public JButton getQuaylaiButton() {
        return quaylaiButton;
    }

    public JButton getTimkiem() {
        return timkiem;
    }

    public JTextField getTextSl() {
        return textSl;
    }

    public JTextField getMahd() {
        return textmahd;
    }

    public JTextField getTextmasp() {
        return textmasp;
    }

    public JTextField getTextmahd() {
        return textmahd;
    }

    public JTextField getTexttongtien() {
        return texttongtien;
    }

    public JTextField getTexttiendua() {
        return texttiendua;
    }

    public JTextField getTexttienthua() {
        return texttienthua;
    }
}
