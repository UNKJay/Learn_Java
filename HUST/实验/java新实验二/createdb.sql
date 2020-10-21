use java_lab2;

DROP TABLE  IF EXISTS `T_KSXX`;
CREATE TABLE `T_KSXX` (
  `KSBH` char(6),
  `KSMC` char(10) NOT NULL,
  `PYZS` char(8) NOT NULL,
  PRIMARY KEY  (`KSBH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into T_KSXX value('000001','内科','NK');
insert into T_KSXX value('000002','外科','WK');
insert into T_KSXX value('000003','妇科','FK');
insert into T_KSXX value('000004','儿科','EK');
insert into T_KSXX value('000005','肿瘤科','ZLK');
insert into T_KSXX value('000006','皮肤科','PFK');

DROP TABLE  IF EXISTS `T_BRXX`;
CREATE TABLE `T_BRXX` (
  `BRBH` char(6),
  `BRMC` char(10) NOT NULL,
  `DLKL` char(8) NOT NULL,
  `YCJE` decimal(10,2) NOT NULL,
  `DLRQ` timestamp  ,
  PRIMARY KEY  (`BRBH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into T_BRXX(BRBH,BRMC,DLKL,YCJE) value('000001','张三','12345678',100.0);
insert into T_BRXX(BRBH,BRMC,DLKL,YCJE) value('000002','李四','12345678',80.0);
insert into T_BRXX(BRBH,BRMC,DLKL,YCJE) value('000003','王五','12345678',60.0);
insert into T_BRXX(BRBH,BRMC,DLKL,YCJE) value('000004','黄六','12345678',40.0);
insert into T_BRXX(BRBH,BRMC,DLKL,YCJE) value('000005','蒋七','12345678',20.0);
insert into T_BRXX(BRBH,BRMC,DLKL,YCJE) value('000006','何八','12345678',0);



DROP TABLE  IF EXISTS `T_KSYS`;
CREATE TABLE `T_KSYS` (
  `YSBH` char(6),
  `KSBH` char(6) NOT NULL,
  `YSMC` char(10) NOT NULL,
  `PYZS` char(4),
  `DLKL` char(8) NOT NULL,
  `SFZJ` boolean NOT NULL,
  `DLRQ` timestamp ,
  PRIMARY KEY  (`YSBH`),
  FOREIGN KEY(`KSBH`) references `T_KSXX`(`KSBH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000001','000001','张内科','ZNK','12345678',1);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000002','000001','李内科','LNK','12345678',0);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000003','000002','赵外科','ZWK','12345678',1);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000004','000002','钱外科','QWK','12345678',0);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000005','000003','王妇科','WFK','12345678',1);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000006','000003','周妇科','ZFK','12345678',0);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000007','000004','孙儿科','SEK','12345678',1);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000008','000004','吴儿科','WEK','12345678',0);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000009','000005','郑肿瘤','ZZL','12345678',1);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000010','000005','冯肿瘤','FZL','12345678',0);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000011','000006','魏皮肤','WPF','12345678',1);
insert into T_KSYS(YSBH,KSBH,YSMC,PYZS,DLKL,SFZJ) value('000012','000006','唐皮肤','TPF','12345678',0);


DROP TABLE  IF EXISTS `T_HZXX`;
CREATE TABLE `T_HZXX` (
  `HZBH` char(6),
  `HZMC` char(12) NOT NULL,
  `PYZS` char(4) NOT NULL,
  `KSBH` char(6) NOT NULL,
  `SFZJ` boolean NOT NULL,
  `GHRS` int NOT NULL,
  `GHFY`decimal(10,2) NOT NULL,
  PRIMARY KEY  (`HZBH`),
  FOREIGN KEY(`KSBH`) references `T_KSXX`(`KSBH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into T_HZXX value('000001','内科普通','NKPT','000001',0,10,7.5);
insert into T_HZXX value('000002','内科专家','NKZJ','000001',1,5,15.5);
insert into T_HZXX value('000003','外科普通','WKPT','000002',0,10,7.5);
insert into T_HZXX value('000004','外科专家','WKZJ','000002',1,5,15.5);
insert into T_HZXX value('000005','妇科普通','FKPT','000003',0,10,7.5);
insert into T_HZXX value('000006','妇科专家','FKZJ','000003',1,5,15.5);
insert into T_HZXX value('000007','儿科普通','EKPT','000004',0,10,7.5);
insert into T_HZXX value('000008','儿科专家','EKZJ','000004',1,5,15.5);
insert into T_HZXX value('000009','肿瘤科普通','ZLPT','000005',0,10,10);
insert into T_HZXX value('000010','肿瘤科专家','ZLZJ','000005',1,5,20);
insert into T_HZXX value('000011','皮肤科普通','PFPT','000006',0,10,7.5);
insert into T_HZXX value('000012','皮肤科专家','PFZJ','000006',1,5,15.5);

DROP TABLE  IF EXISTS `T_GHXX`;
CREATE TABLE `T_GHXX` (
  `GHBH` char(6),
  `HZBH` char(6) NOT NULL,
  `YSBH` char(6) NOT NULL,
  `BRBH` char(6) NOT NULL,
  `GHRC` int NOT NULL,
  `THBZ` boolean NOT NULL,
  `GHFY` decimal(8,2) NOT NULL,
  `RQSJ` timestamp NOT NULL,
  PRIMARY KEY  (`GHBH`),
  FOREIGN KEY(`HZBH`) references `T_HZXX`(`HZBH`),
  FOREIGN KEY(`YSBH`) references `T_KSYS`(`YSBH`),
  FOREIGN KEY(`BRBH`) references `T_BRXX`(`BRBH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
