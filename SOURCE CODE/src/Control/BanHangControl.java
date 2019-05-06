/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import Model.HOADONModel;
import Model.LOAISANPHAMModel;
import Model.SANPHAMModel;
import Model.NHANVIENModel;
import View.BanHang;
import View.ManHinhChinh;
import com.itextpdf.text.PageSize;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Minh Hieu
 */
public class BanHangControl {

    private static Connection connection;
    private DefaultTableModel defaultTableModelSanPham, defaultTableModelbanhang;
    private String ma = "", ten = "", donvi = "", gia = "", soluong = "", tong = "";
    private ArrayList<SANPHAMModel> DSSP;
    private ArrayList<LOAISANPHAMModel> Dsloai;
    private ArrayList<NHANVIENModel> DSnhanvien;
    private ArrayList<HOADONModel> DShoadon;
    private int vitrihangtableban, vitrihangtablesp;

    public BanHangControl() {
    }

    public void QuayLai(ActionEvent e, String NhanVien, int capbac) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);
        try {
            fr.dispose();
            ManHinhChinh manHinhChinh = new ManHinhChinh(NhanVien, capbac);
        } catch (IOException ex) {
            Logger.getLogger(LoginControl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadcbbnv(ActionEvent e, String manv) throws SQLException, ClassNotFoundException {
        Component component = (Component) e.getSource();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);
        NHANVIENModel nv = new NHANVIENModel();
        DSnhanvien = new ArrayList<>();
        DSnhanvien = nv.layThongtinnhanvien();
        int j = fr.getNhanvien().getItemCount();
        String tennv = "";
        for (NHANVIENModel Temp : DSnhanvien) {
            if (Temp.getMaNV().equals(manv) == true) {
                tennv = Temp.getTenNV();
            }
        }
        for (int k = 0; k < j - 1; k++) {
            if (fr.getNhanvien().getItemAt(k).toString().equals(tennv) == true) {
                {
                    fr.getNhanvien().setSelectedIndex(k);
                }
            }
        }
    }

    public void thanhtoanHoaDon(ActionEvent e, String MAHD, JTable banhangJTable) throws IOException, ClassNotFoundException, SQLException, DocumentException {
        Component component = (Component) e.getSource();
        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);
        int numcols = banhangJTable.getColumnCount();
        int row = banhangJTable.getRowCount();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < numcols; j++) {
                String tam = (String) banhangJTable.getValueAt(i, j);
                if (j == 0) {
                    ma = tam;
                }
                if (j == 1) {
                    ten = tam;
                }
                if (j == 2) {
                    gia = tam;
                }
                if (j == 4) {
                    soluong = tam;
                }

            }
            String sql = "insert into CHITIETHOADON values('" + MAHD + "','" + ma + "','" + soluong + "',null,null)";
            statement.executeUpdate(sql);
        }
        Date today = new Date(System.currentTimeMillis());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngayString = dateFormat.format(today.getTime());
        DateFormat dateFormatgio = new SimpleDateFormat("HH:mm:ss");
        String gioString = dateFormatgio.format(today.getTime());

        inHoadon(MAHD, fr.getNhanvien().getSelectedItem().toString(),
                tinhtongTienthanhtoan(), banhangJTable, ngayString, gioString, fr.getTexttiendua().getText(),
                fr.getTexttienthua().getText(), fr.getTextkh().getText());

        connection.close();
    }

    public String loadmaloai(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);
        String maloai = "TẤT CẢ";
        for (LOAISANPHAMModel loai : Dsloai) {
            if (fr.getCbbLoai().getSelectedItem().toString().equals(loai.getTENLOAI())) {
                maloai = loai.getMALOAI();
            }
        }
        themspvaobangsp("", maloai);
        return maloai;
    }

    public String loadmaloaisp(ItemEvent e) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);
        String maloai = "TẤT CẢ";
        for (LOAISANPHAMModel loai : Dsloai) {
            if (fr.getCbbLoai().getSelectedItem().toString().equals(loai.getTENLOAI())) {
                maloai = loai.getMALOAI();
            }
        }
        themspvaobangsp("", maloai);
        return maloai;
    }

    public void themHoadonmoi(ActionEvent e, String thoigian) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);

        HOADONModel hd = new HOADONModel();
        ArrayList<HOADONModel> danhsachtam = hd.layDanhsachhoadon();

        int size = danhsachtam.size();
        String MAHD = danhsachtam.get(size - 1).getMAHD();
        String chuoisohdtam = MAHD.substring(2, 5);//cắt chuỗi từ vị trí 2->(5-1)
        int temp = Integer.parseInt(chuoisohdtam);//lấy sohd cuối cùng

        if (temp < 9) {
            fr.getMahd().setText("HD00" + (temp + 1));
        }
        if (temp >= 9 && temp < 99) {
            fr.getMahd().setText("HD0" + (temp + 1));
        }
        if (temp >= 99) {
            fr.getMahd().setText("HD" + (temp + 1));
        }
        NHANVIENModel nv = new NHANVIENModel();
        DSnhanvien = nv.layThongtinnhanvien();
        String manv = null;
        for (NHANVIENModel tam : DSnhanvien) {
            if (tam.getTenNV().equals(fr.getNhanvien().getSelectedItem().toString())) {
                manv = tam.getMaNV();
            }
        }

        connection = MSSQLControl.getConnect();
        Statement statement = connection.createStatement();
        String sql = "insert into HOADON values('" + fr.getMahd().getText() + "','" + thoigian + "','" + manv + "',null)";
        statement.executeUpdate(sql);
        connection.close();
    }

    public String[] Layloaisanpham() throws SQLException, ClassNotFoundException {
        LOAISANPHAMModel loai = new LOAISANPHAMModel();
        loai.layDanhsachloaisanpham();
        String[] ds = new String[loai.demSoloaisanpham() + 1];
        Dsloai = loai.getDsloai();
        int i = 1;
        ds[0] = "TẤT CẢ";
        for (LOAISANPHAMModel tam : Dsloai) {

            ds[i] = tam.getTENLOAI();
            i++;
        }

        return ds;

    }

    public String[] layDsnhanvien() throws SQLException, ClassNotFoundException {
        DSnhanvien = new ArrayList<>();
        NHANVIENModel nv = new NHANVIENModel();
        DSnhanvien = nv.layThongtinnhanvien();
        String[] ds = new String[nv.demSonhanvien()];

        int i = 0;
        for (NHANVIENModel tam : DSnhanvien) {

            ds[i] = tam.getTenNV();
            i++;
        }

        return ds;

    }

    public String[] laytennhanvien(String manv) throws SQLException, ClassNotFoundException {
        DSnhanvien = new ArrayList<>();
        NHANVIENModel nv = new NHANVIENModel();
        DSnhanvien = nv.layThongtinnhanvien();
        String[] tennv = new String[1];
        for (NHANVIENModel tam : DSnhanvien) {

            if (tam.getMaNV().equals(manv)) {
                tennv[0] = tam.getTenNV();
            }
        }

        return tennv;

    }

    public void LaySanPhamTuTable(MouseEvent e) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);
        int row = fr.getJt().rowAtPoint(e.getPoint());
        vitrihangtablesp = row;
        int col = fr.getJt().columnAtPoint(e.getPoint());
        int numcols = defaultTableModelSanPham.getColumnCount();
        for (int i = 0; i < numcols; i++) {
            String str = (String) defaultTableModelSanPham.getValueAt(row, i);
            if (i == 0) {
                ma = str;
            }
            if (i == 1) {
                ten = str;
            }
            if (i == 2) {
                gia = str;
            }
            if (i == 3) {
                donvi = str;
            }
            fr.gettextmasp().setText(ma);

        }
    }

    public void chinhsuSoluong(MouseEvent e) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);
        int row = fr.getTbban().rowAtPoint(e.getPoint());
        vitrihangtableban = row;
        int col = fr.getTbban().columnAtPoint(e.getPoint());
        int numcols = defaultTableModelbanhang.getColumnCount();
        String str = (String) defaultTableModelbanhang.getValueAt(row, 4);
        fr.getTextSl().setText(str);
        for (int i = 0; i < numcols; i++) {
            String tam = (String) defaultTableModelbanhang.getValueAt(row, i);
            if (i == 0) {
                ma = tam;
            }
            if (i == 1) {
                ten = tam;
            }
            if (i == 2) {
                gia = tam;
            }
            if (i == 3) {
                donvi = tam;
            }
            if (i == 4) {
                soluong = tam;
            }
            if (i == 5) {
                tong = tam;
            }

        }

    }

    public void suaSoluong(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);
        int array[];
        array = fr.getTbban().getSelectedRows();
        if (kiemtraSoluongtonkho(ma) >= Integer.parseInt(fr.getTextSl().getText())) {
            for (int i = 0; i < array.length; i++) {
                defaultTableModelbanhang.removeRow(array[i]);
            }
            Vector z = new Vector();
            z.add(ma);
            z.add(ten);
            z.add(gia);
            z.add(donvi);
            z.add(fr.getTextSl().getText());
            int tongdongia = Integer.parseInt(gia) * Integer.parseInt(fr.getTextSl().getText());
            z.add(String.valueOf(tongdongia));
            defaultTableModelbanhang.insertRow(vitrihangtableban, z);
        } else {
            JOptionPane.showMessageDialog(null, "Sản phẩm không đủ số lượng tồn kho", "Lỗi số lượng", 2);
        }
    }

    public void xoaSanphamdachon(ActionEvent e) throws IOException, ClassNotFoundException, SQLException {
        Component component = (Component) e.getSource();
        BanHang fr = (BanHang) SwingUtilities.getRoot(component);
        int array[];
        array = fr.getTbban().getSelectedRows();
        for (int i = 0; i < array.length; i++) {
            defaultTableModelbanhang.removeRow(array[i]);
        }
    }

    public void themthuoctinhbangsp() {
        defaultTableModelSanPham = new DefaultTableModel();
        defaultTableModelSanPham.addColumn("Mã SP");
        defaultTableModelSanPham.addColumn("Tên Sản Phẩm");
        defaultTableModelSanPham.addColumn("Giá");
        defaultTableModelSanPham.addColumn("Đơn vị");
        defaultTableModelSanPham.addColumn("Loại");
//        defaultTableModelSanPham.addColumn("Số Lượng Tồn");
    }

    public DefaultTableModel themspvaobangsp(String masp, String tenloai) throws SQLException, ClassNotFoundException {
        themthuoctinhbangsp();
        DSSP = new ArrayList<>();
        SANPHAMModel Sanpham = new SANPHAMModel();
        DSSP = Sanpham.layDanhSachSpDeban();

        defaultTableModelSanPham.setRowCount(0);
        for (SANPHAMModel sp : DSSP) {
            Vector v = new Vector();
            if (masp.isEmpty()) {
                if (tenloai.equals("TẤT CẢ") == true && sp.getTinhTrang() == true) {
                    v.add(sp.getMASP());
                    v.add(sp.getTENSP());
                    v.add(String.valueOf(sp.getDONGIABAN()));
                    v.add(sp.getDONVI());
                    v.add(sp.getMALOAI());

//                    v.add(String.valueOf(sp.getSOLUONGTON()));
                    defaultTableModelSanPham.addRow(v);
                } else {
                    if (sp.getMALOAI().equals(tenloai)) {
                        v.add(sp.getMASP());
                        v.add(sp.getTENSP());
                        v.add(String.valueOf(sp.getDONGIABAN()));
                        v.add(sp.getDONVI());
                        v.add(sp.getMALOAI());
//                        v.add(String.valueOf(sp.getSOLUONGTON()));
                        defaultTableModelSanPham.addRow(v);
                    }
                }
            } else {
                if (sp.getMASP().equals(masp)) {
                    v.add(sp.getMASP());
                    v.add(sp.getTENSP());
                    v.add(String.valueOf(sp.getDONGIABAN()));
                    v.add(sp.getDONVI());
                    v.add(sp.getMALOAI());
//                    v.add(String.valueOf(sp.getSOLUONGTON()));
                    defaultTableModelSanPham.addRow(v);
                }
            }
        }
        return defaultTableModelSanPham;
    }

    public DefaultTableModel themthuoctinhbangspduocchon() {
        defaultTableModelbanhang = new DefaultTableModel();
        defaultTableModelbanhang.addColumn("Mã SP");
        defaultTableModelbanhang.addColumn("Tên Sản Phẩm");
        defaultTableModelbanhang.addColumn("Giá");
        defaultTableModelbanhang.addColumn("Đơn vị");
        defaultTableModelbanhang.addColumn("Số lượng");
        defaultTableModelbanhang.addColumn("Tổng");
        return defaultTableModelbanhang;
    }

    public void themspvaobangspchon(String sl) throws SQLException, ClassNotFoundException {
        if (sl.isEmpty() || sl == null) {
            JOptionPane.showMessageDialog(null, "Chưa nhập số lượng", "Lỗi nhập", 1);
        } else {
            if (kiemtraTrungsanphamkhithemchon() == true) {
                if (kiemtraSoluongtonkho(ma) >= Integer.parseInt(sl)) {
                    Vector v = new Vector();
                    v.add(ma);
                    v.add(ten);
                    v.add(gia);
                    v.add(donvi);
                    v.add(sl);
                    int tam = Integer.parseInt(gia) * Integer.parseInt(sl);
                    v.add(String.valueOf(tam));
                    defaultTableModelbanhang.addRow(v);
                } else {
                    JOptionPane.showMessageDialog(null, "Sản phẩm không đủ số lượng tồn kho", "Lỗi số lượng", 2);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Sản phẩm đã được chọn", "Lỗi nhập", 1);
            }
        }
    }

    public int kiemtraSoluongtonkho(String MasanPham) throws SQLException, ClassNotFoundException {
        DSSP = new ArrayList<>();
        SANPHAMModel Sanpham = new SANPHAMModel();
        DSSP = Sanpham.layDanhSachSpDeban();
        int soluongton = 0;
        for (SANPHAMModel sp : DSSP) {
            if (sp.getMASP().equals(MasanPham)) {
                soluongton = sp.getSOLUONGTON();
            }
        }

        return soluongton;
    }

    public boolean kiemtraTrungsanphamkhithemchon() {
        int numcols = defaultTableModelbanhang.getColumnCount();
        for (int j = 0; j < defaultTableModelbanhang.getRowCount(); j++) {
            for (int i = 0; i < numcols; i++) {
                String str = (String) defaultTableModelbanhang.getValueAt(j, i);
                if (ma.equals(str)) {
                    return false;
                }
            }
        }
        return true;
    }

    public String tinhtongTienthanhtoan() {
        int tongtien = 0;
        for (int j = 0; j < defaultTableModelbanhang.getRowCount(); j++) {
            String str = (String) defaultTableModelbanhang.getValueAt(j, 5);
            tongtien = tongtien + Integer.parseInt(str);
        }
        if (String.valueOf(tongtien).equals("0")) {
            return null;
        }
        return String.valueOf(tongtien);
    }

    public void inHoadon(String MAHD, String TenNV, String TongTien, JTable table, String Date, String time, String tiendua, String tienthoi, String khach) throws IOException, DocumentException {

        try {
            File file = new File("src/HOADON/" + MAHD + ".pdf");
            if (file.createNewFile()) {
                System.out.println("Success!");
            } else {
                System.out.println("Error, file already exists.");
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        // Tạo đối tượng tài liệu
        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        // Tạo đối tượng PdfWriter
        PdfWriter.getInstance(document, new FileOutputStream("src/HOADON/" + MAHD + ".pdf"));

        // Mở file để thực hiện ghi
        document.open();
        BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED); // khỏi tạo font chữ 
        Font font1 = new Font(bf, 18, Font.BOLD);
        Font font10 = new Font(bf, 10, Font.NORMAL);
        //Font font11 = new Font(bf, 11, Font.STRIKETHRU);

        // Thêm nội dung 
        Paragraph tencuahang = new Paragraph("Cửa hàng tiện lợi Ministop ", font1);
        document.add(tencuahang);
        Paragraph diachi = new Paragraph("Địa Chỉ: Kí túc xá khu A, Đại học quốc gia TP.HCM ", font10);
        document.add(diachi);
        Paragraph sdt = new Paragraph("Số Điện Thoại: 0123456789", font10);
        document.add(sdt);
        Paragraph thoigian = new Paragraph("Thời Gian: " + Date + " " + time, font10);
        document.add(thoigian);

        Paragraph HoaDon = new Paragraph("Hóa Đơn Bán Hàng", font1);
        HoaDon.setIndentationLeft(200.0f);
        document.add(HoaDon);
        Paragraph mahd = new Paragraph("Mã Hóa Đơn: " + MAHD, font10);
        mahd.setSpacingBefore(20);
        document.add(mahd);

        Paragraph nhanvien = new Paragraph("Tên Nhân Viên: " + TenNV, font10);
        document.add(nhanvien);
        Paragraph khachhang = new Paragraph("Khách hàng: " + khach, font10);
        document.add(khachhang);

        float[] withsKM = {15.0f, 15.0f, 10.0f, 10.0f, 8.0f, 15.0f}; // khởi tạo bảng có số cột là bằng 5 cột

        PdfPTable t = new PdfPTable(6);// set độ rộng của table, mặc định là 80% 
        t.setWidthPercentage(100); // set độ rộng cho từng cột 
        t.setWidths(withsKM);
        t.setSpacingBefore(20);
        t.setSpacingAfter(20);

        PdfPCell c1 = new PdfPCell(new Phrase("         Mã sản phẩm", font10));
        t.addCell(c1);
        PdfPCell c2 = new PdfPCell(new Phrase("         Tên sản phẩm", font10));
        t.addCell(c2);
        PdfPCell c3 = new PdfPCell(new Phrase("         Giá", font10));
        t.addCell(c3);
        PdfPCell c4 = new PdfPCell(new Phrase("         Đơn vị", font10));
        t.addCell(c4);
        PdfPCell c5 = new PdfPCell(new Phrase("  Số Lượng", font10));
        t.addCell(c5);
        PdfPCell c6 = new PdfPCell(new Phrase("        Tổng tiền", font10));
        t.addCell(c6);

        int numcols = table.getColumnCount();
        int row = table.getRowCount();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < numcols; j++) {
                String tam = (String) table.getValueAt(i, j);
                Phrase masp = new Phrase(tam, font10);
                t.addCell(masp);
            }
        }

        document.add(t);

        Paragraph Tongtien = new Paragraph("Tổng Tiền: " + TongTien + " VNĐ", font10);
        Tongtien.setIndentationLeft(350.0f);
        document.add(Tongtien);
        Paragraph tienkhachdua = new Paragraph("Tiền khách đưa: " + tiendua + " VNĐ", font10);
        tienkhachdua.setIndentationLeft(350.0f);
        document.add(tienkhachdua);
        Paragraph tienthoikhach = new Paragraph("Tiền Thói: " + tienthoi + " VNĐ", font10);
        tienthoikhach.setIndentationLeft(350.0f);
        document.add(tienthoikhach);

        Paragraph ghichu = new Paragraph("*Tổng tiền đã bao gồm thuế VAT", font10);
        document.add(ghichu);

        Paragraph khoangcach = new Paragraph("----------------------", font10);
        khoangcach.setIndentationLeft(200.0f);
        document.add(khoangcach);

        Paragraph camon = new Paragraph("Cảm ơn bạn đã mua sản phẩm tại MiniStop", font10);
        camon.setIndentationLeft(150.0f);
        document.add(camon);

        // Đóng File
        document.close();
        File file = new File("src/HOADON/" + MAHD + ".pdf");//đường dẫn file 
        Desktop dt = Desktop.getDesktop();
        dt.open(file);
    }
}
