create database QLCuaHangTienLoi
use QLCuaHangTienLoi
drop database QLCuaHangTienLoi

create table CHUCVU
(
	MaChucVu char(5) primary key,
	TenChucVu nvarchar(50),
	HeSo float
)

create table NHANVIEN
(
	MaNV char(5) primary key,
	TenNV nvarchar(50),
	NgaySinh smalldatetime,
	GioiTinh nvarchar(3),
	DiaChi nvarchar(200),
	MaChucVu char(5) foreign key references HESOLUONG(MaChucVu),
	NgayVaoLam smalldatetime,
	SDT varchar(10),
	MatKhau varchar(30)
)

create table LOAISANPHAM
(
	MaLoai char(5) primary key,
	TenLoai nvarchar(50)
)

create table SANPHAM
(
	MaSP char(5) primary key,
	TenSP nvarchar(50),
	DonGiaNhap money,
	DonGiaBan money,
	DonVi nvarchar(20),
	MaLoai char(5) foreign key references LOAISANPHAM(MaLoai),
	TinhTrang bit,
	SoLuongTon int
)

create table DONNHAPHANG
(
	MaDonNhapHang char(5) primary key,
	NgayNhap smalldatetime,
	TongGiaTri money
)

create table CHITIETNHAPHANG
(
	MaSP char(5) foreign key references SANPHAM(MaSP),
	MaDonNhapHang char(5) foreign key references DONNHAPHANG(MaDonNhapHang),
	SoLuong int,
	DonGiaNhap money,
	primary key (MaSP, MaDonNhapHang)
)

create table HOADON
(
	MaHD char(5) primary key,
	NgayGio smalldatetime,
	MaNV char(5) foreign key references NHANVIEN(MaNV),
	TongTienHD money
)

create table CHITIETHOADON
(
	MaHD char(5) foreign key references HOADON(MaHD),
	MaSP char(5) foreign key references SANPHAM(MaSP),
	SoLuong int,
	DonGiaHienTai money,
	ThanhTien money,
	primary key (MaHD, MaSP)
)

create table BANGCHAMCONG
(
	MaChamCong char(5) primary key,
	Ngay smalldatetime,
	MaNV char(5) foreign key references NHANVIEN(MaNV),
	CaLam varchar(50)
)


SET DATEFORMAT DMY

-------------------------

------------------------------------------------------------------------
-- Tạo dữ liệu LOAISANPHAM
insert into LOAISANPHAM values ('ML001',N'Hoá mỹ phẩm')
insert into LOAISANPHAM values ('ML002',N'Thực phẩm')
insert into LOAISANPHAM values ('ML003',N'Đồ uống')
insert into LOAISANPHAM values ('ML004',N'Đồ dùng sinh hoạt')
insert into LOAISANPHAM values ('ML005',N'Văn phòng phẩm')

------------------------------------------------------------------------
-- Tạo dữ liệu SANPHAM
insert into SANPHAM values ('SP001',N'7 up','5000','8000',N'lon','ML003',1,20)
insert into SANPHAM values ('SP002',N'Cocacola','5000','8000',N'lon','ML003',1,20)
insert into SANPHAM values ('SP003',N'Sting','5000','8000',N'lon','ML003',1,20)
insert into SANPHAM values ('SP004',N'7 up','5000','8000',N'chai','ML003',1,20)
insert into SANPHAM values ('SP005',N'Cocacola','5000','8000',N'chai','ML003',1,20)
insert into SANPHAM values ('SP006',N'Sting','5000','8000',N'chai','ML003',1,20)
insert into SANPHAM values ('SP007',N'333','10000','13000',N'lon','ML003',1,20)
insert into SANPHAM values ('SP008',N'Strongbow','13000','18000',N'chai','ML003',1,20)
insert into SANPHAM values ('SP009',N'Heniken','15000','20000',N'lon','ML003',1,20)
insert into SANPHAM values ('SP010',N'Aquafina','3000','6000',N'chai','ML003',1,20)

insert into SANPHAM values ('SP011',N'Colgate','19000','25000',N'tuýp','ML001',1,20)
insert into SANPHAM values ('SP012',N'PS','5000','8000',N'tuýp','ML001',1,20)
insert into SANPHAM values ('SP013',N'Nước rửa chén','18000','23000',N'chai','ML001',1,20)
insert into SANPHAM values ('SP014',N'Sữa tắm','35000','45000',N'chai','ML001',1,20)
insert into SANPHAM values ('SP015',N'Dầu gội','35000','45000',N'chai','ML001',1,20)
insert into SANPHAM values ('SP016',N'Khăn ướt','7000','10000',N'bịch','ML001',1,20)
insert into SANPHAM values ('SP017',N'Khăn giấy','3000','5000',N'bịch','ML001',1,20)
insert into SANPHAM values ('SP018',N'Dầu xả','40000','50000',N'chai','ML001',1,20)
insert into SANPHAM values ('SP019',N'Nước xả','30000','40000',N'chai','ML001',1,20)
insert into SANPHAM values ('SP020',N'Bột giặt','40000','50000',N'bịch','ML001',1,20)

insert into SANPHAM values ('SP021',N'Bánh mì tươi KINHDO','3000','6000',N'cái','ML002',1,20)
insert into SANPHAM values ('SP022',N'Snack POCA','4000','7000',N'bịch','ML002',1,20)
insert into SANPHAM values ('SP023',N'Mì Hảo Hảo','6000','9000',N'ly','ML002',1,20)
insert into SANPHAM values ('SP024',N'Mì chay Reevan','4000','6000',N'gói','ML002',1,20)
insert into SANPHAM values ('SP025',N'Snack Bí đỏ','3000','5000',N'bịch','ML002',1,20)
insert into SANPHAM values ('SP026',N'Kem Calano','14000','18000',N'cây','ML002',1,20)
insert into SANPHAM values ('SP027',N'Sữa chua Vinamilk','3000','5000',N'hũ','ML002',1,20)
insert into SANPHAM values ('SP028',N'Sữa chua TH','5000','8000',N'hũ','ML002',1,20)
insert into SANPHAM values ('SP029',N'Oreo','10000','13000',N'cây','ML002',1,20)
insert into SANPHAM values ('SP030',N'Mì xào táo quân','5000','8000',N'gói','ML002',1,20)

insert into SANPHAM values ('SP031',N'Bàn chải đánh răng','9000','11000',N'cái','ML004',1,20)
insert into SANPHAM values ('SP032',N'Bàn chải','5000','8000',N'cái','ML004',1,20)
insert into SANPHAM values ('SP033',N'Kiềm cắt móng','20000','25000',N'cái','ML004',1,20)
insert into SANPHAM values ('SP034',N'Ly','8000','13000',N'cái','ML004',1,20)
insert into SANPHAM values ('SP035',N'Chén','8000','13000',N'cái','ML004',1,20)
insert into SANPHAM values ('SP036',N'Đũa','5000','8000',N'cái','ML004',1,20)
insert into SANPHAM values ('SP037',N'Muỗng','5000','8000',N'cái','ML004',1,20)
insert into SANPHAM values ('SP038',N'Gương','10000','15000',N'cái','ML004',1,20)
insert into SANPHAM values ('SP039',N'Dũa móng tay','3000','5000',N'cái','ML004',1,20)
insert into SANPHAM values ('SP040',N'Khăn','5000','8000',N'cái','ML004',1,20)

insert into SANPHAM values ('SP041',N'Bút chì','5000','8000',N'cây','ML005',1,20)
insert into SANPHAM values ('SP042',N'Bút mực','5000','8000',N'cây','ML005',1,20)
insert into SANPHAM values ('SP043',N'Bút xoá','10000','15000',N'cây','ML005',1,20)
insert into SANPHAM values ('SP044',N'Ruột bút chì','9000','14000',N'hộp','ML005',1,20)
insert into SANPHAM values ('SP045',N'Tẩy','5000','8000',N'cái','ML005',1,20)
insert into SANPHAM values ('SP046',N'Bút dạ quang','10000','15000',N'cái','ML005',1,20)
insert into SANPHAM values ('SP047',N'Thước kẻ','5000','8000',N'cây','ML005',1,20)
insert into SANPHAM values ('SP048',N'Thước ê-ke','5000','8000',N'cây','ML005',1,20)
insert into SANPHAM values ('SP049',N'Vở','8000','12000',N'quyển','ML005',1,20)
insert into SANPHAM values ('SP050',N'Sổ tay','10000','15000',N'quyển','ML005',1,20)

------------------------------------------------------------------------
-- Tạo dữ liệu CHUCVU
INSERT INTO CHUCVU(MaChucVu,TenChucVu,HeSo) VALUES ('CV001',N'Quản lý cấp 1','5')
INSERT INTO CHUCVU(MaChucVu,TenChucVu,HeSo) VALUES ('CV002',N'Quản lý cấp 2','5')
INSERT INTO CHUCVU(MaChucVu,TenChucVu,HeSo) VALUES ('CV003',N'Nhân viên cấp 1','4')
INSERT INTO CHUCVU(MaChucVu,TenChucVu,HeSo) VALUES ('CV004',N'Nhân viên cấp 2','3')
INSERT INTO CHUCVU(MaChucVu,TenChucVu,HeSo) VALUES ('CV005',N'Nhân viên cấp 3','2')

------------------------------------------------------------------------
-- Tạo dữ liệu NHANVIEN
INSERT INTO NHANVIEN(MaNV,TenNV,SDT,DiaChi,NgaySinh,MaChucVu,GioiTinh,NgayVaoLam,MatKhau) VALUES ('NV001',N'Nguyễn Minh Hiếu','023456780',N'Quận Thủ Đức, TP.HCM','12/05/1998','CV001',N'Nam','14/10/2018','1')
INSERT INTO NHANVIEN(MaNV,TenNV,SDT,DiaChi,NgaySinh,MaChucVu,GioiTinh,NgayVaoLam,MatKhau) VALUES ('NV002',N'Nguyễn Đặng Mỹ Ngọc','01234679',N'Quận 2, TP.HCM','22/02/1998','CV001',N'Nữ','14/10/2018','1')
INSERT INTO NHANVIEN(MaNV,TenNV,SDT,DiaChi,NgaySinh,MaChucVu,GioiTinh,NgayVaoLam,MatKhau) VALUES ('NV003',N'Võ Hồng Mỹ Duyên','07843688',N'Quận 1, TP.HCM','16/09/1998','CV002',N'Nữ','20/10/2018','1')
INSERT INTO NHANVIEN(MaNV,TenNV,SDT,DiaChi,NgaySinh,MaChucVu,GioiTinh,NgayVaoLam,MatKhau) VALUES ('NV004',N'Nguyễn Văn Thinh','03472422',N'Quận 5, TP.HCM','25/02/1998','CV003',N'Nam','22/10/2018','1')
INSERT INTO NHANVIEN(MaNV,TenNV,SDT,DiaChi,NgaySinh,MaChucVu,GioiTinh,NgayVaoLam,MatKhau) VALUES ('NV005',N'Trần Văn B','07914203',N'Quận 12, TP.HCM','15/04/2000','CV004',N'Nam','5/11/2018','1')

--Tạo Dữ Liệu Hóa Đơn

SET DATEFORMAT DMY

INSERT INTO HOADON VALUES('HD001','13/1/2018','NV001',null)
INSERT INTO HOADON VALUES('HD002','13/1/2018','NV002',null)
INSERT INTO HOADON VALUES('HD003','22/3/2018','NV002',null)
INSERT INTO HOADON VALUES('HD004','15/2/2018','NV002',null)
INSERT INTO HOADON VALUES('HD005','16/5/2018','NV002',null)
INSERT INTO HOADON VALUES('HD006','17/7/2018','NV002',null)
INSERT INTO HOADON VALUES('HD007','17/7/2018','NV002',null)
INSERT INTO HOADON VALUES('HD008','17/8/2018','NV002',null)
INSERT INTO HOADON VALUES('HD009','17/9/2018','NV002',null)
INSERT INTO HOADON VALUES('HD010','14/9/2018','NV002',null)
INSERT INTO HOADON VALUES('HD011','27/9/2018','NV002',null)
INSERT INTO HOADON VALUES('HD012','31/12/2018','Nv003',null)
INSERT INTO HOADON VALUES('HD013','22/10/2018','Nv003',null)
INSERT INTO HOADON VALUES('HD014','22/11/2018','Nv004',null)
INSERT INTO HOADON VALUES('HD015','22/1/2018','Nv004',null)
INSERT INTO HOADON VALUES('HD016','22/6/2018','Nv004',null)
INSERT INTO HOADON VALUES('HD017','19/7/2018','Nv004',null)
INSERT INTO HOADON VALUES('HD018','16/8/2018','Nv004',null)
INSERT INTO HOADON VALUES('HD019','23/3/2018','NV001',null)
INSERT INTO HOADON VALUES('HD020','23/4/2018','NV001',null)

create trigger TRIG_CapnhattienCTHD on CHITIETHOADON after insert
 as
begin 
DECLARE @MASP CHAR(5)
Declare  @giaht money
Declare @MAHD CHAR(5)
SELECT @MASP=MASP FROM inserted
SELECT @MAHD=MAHD FROM inserted
SELECT @giaht=DonGiaBan FROM SANPHAM where masp=@MASP
	update CHITIETHOADON
	set DonGiaHienTai=@giaht
	where MAHD=@MAHD and MASP=@MASP

	update CHITIETHOADON
	set ThanhTien=SoLuong*DonGiaHienTai
	where MAHD=@MAHD and MASP=@MASP
end

--2.Trigger tự động cập nhật tông tiền cho hóa đơn khi thêm, chỉnh sửa sp trong chi tiết hóa đơn
create trigger TRIG_Capnhattienhoadon on CHITIETHOADON after insert,update
 as
begin 
	Declare @MAHD CHAR(5)
	Declare @TongTien money
	select @MAHD=MAHD from inserted 
	select @TongTien=sum(ThanhTien)from CHITIETHOADON 
						where MAHD=@MAHD
	update HOADON
	set TongTienHD=@TongTien
	where MAHD=@MAHD
end

--3.Trigger cap nhat số lượng khi bán sản phẩm

create trigger TRIG_Capnhatsltonkhokhiban on CHITIETHOADON after insert
 as
begin 
	DECLARE @MASP CHAR(5)
	Declare @Soluongnhap int
	SELECT @MASP=MASP FROM inserted
	SELECT @Soluongnhap=SoLuong FROM inserted
	update SANPHAM
	set SoLuongTon=SoLuongTon-@Soluongnhap
	where MaSP=@MASP
end

--CTHD

INSERT INTO CHITIETHOADON VALUES('HD001','SP001','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD001','SP002','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD001','SP003','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD001','SP004','3',null,null)
INSERT INTO CHITIETHOADON VALUES('HD001','SP010','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD001','SP015','3',null,null)
INSERT INTO CHITIETHOADON VALUES('HD002','SP003','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD002','SP004','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD002','SP010','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD002','SP015','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD002','SP003','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD002','SP004','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD003','SP002','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD003','SP018','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD003','SP019','3',null,null)
INSERT INTO CHITIETHOADON VALUES('HD003','SP020','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD003','SP004','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD003','SP021','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD003','SP003','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD003','SP016','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD003','SP018','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD004','SP018','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD004','SP019','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD004','SP020','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD004','SP004','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD004','SP021','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD004','SP003','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD004','SP016','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD004','SP018','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD004','SP018','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP020','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP004','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP021','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP003','4',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP016','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP015','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP014','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP023','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP026','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP009','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD005','SP008','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD006','SP023','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD006','SP026','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD006','SP009','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD006','SP008','3',null,null)
INSERT INTO CHITIETHOADON VALUES('HD006','SP023','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD006','SP026','3',null,null)
INSERT INTO CHITIETHOADON VALUES('HD006','SP011','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD006','SP008','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD006','SP023','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD007','SP024','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD007','SP025','3',null,null)
INSERT INTO CHITIETHOADON VALUES('HD008','SP026','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD009','SP027','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD010','SP028','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD011','SP029','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD011','SP030','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD012','SP031','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD012','SP032','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD012','SP033','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD012','SP034','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD012','SP035','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD012','SP036','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD012','SP037','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD012','SP038','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD012','SP039','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD013','SP040','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD013','SP041','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD013','SP042','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD013','SP043','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD013','SP044','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD013','SP045','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD013','SP046','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD013','SP047','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD014','SP047','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD014','SP048','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD014','SP049','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD015','SP050','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD015','SP001','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD015','SP002','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD016','SP006','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD016','SP010','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD016','SP014','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD016','SP018','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD016','SP022','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD016','SP026','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD017','SP030','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD017','SP034','3',null,null)
INSERT INTO CHITIETHOADON VALUES('HD017','SP038','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD017','SP042','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD017','SP046','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD017','SP050','2',null,null)
INSERT INTO CHITIETHOADON VALUES('HD018','SP007','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD018','SP009','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD018','SP011','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD018','SP013','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD018','SP015','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD018','SP017','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD018','SP019','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD018','SP021','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD018','SP023','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD019','SP025','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD019','SP027','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD019','SP029','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD019','SP031','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD019','SP033','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD019','SP035','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD019','SP037','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD020','SP039','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD020','SP041','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD020','SP043','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD020','SP045','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD020','SP047','1',null,null)
INSERT INTO CHITIETHOADON VALUES('HD020','SP049','1',null,null)


INSERT INTO BANGCHAMCONG VALUES('CC001','NV001','2019-1-15',N'Ca sáng')

INSERT INTO DONNHAPHANG VALUES('DH001','2019-1-15','100000')
