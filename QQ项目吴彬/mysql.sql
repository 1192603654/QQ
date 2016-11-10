/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.1.62-community : Database - qq
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`qq` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `qq`;

/*Table structure for table `qqdynamic` */

DROP TABLE IF EXISTS `qqdynamic`;

CREATE TABLE `qqdynamic` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `senderaccount` varchar(10) DEFAULT NULL,
  `senderheadico` varchar(100) DEFAULT NULL,
  `sendername` varchar(20) DEFAULT NULL,
  `dynamiccontent` varchar(1000) DEFAULT NULL,
  `sendertime` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`did`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `qqdynamic` */

insert  into `qqdynamic`(`did`,`senderaccount`,`senderheadico`,`sendername`,`dynamiccontent`,`sendertime`) values (4,'155334204',NULL,'玄冰','天气真冷！','2016-11-01/08:48:59');

/*Table structure for table `qqfrienduser` */

DROP TABLE IF EXISTS `qqfrienduser`;

CREATE TABLE `qqfrienduser` (
  `fid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `userheadico` varchar(100) DEFAULT NULL,
  `useraccount` varchar(10) DEFAULT NULL,
  `frienduseraccount` varchar(10) DEFAULT NULL,
  `frienduserstate` int(11) DEFAULT NULL COMMENT '1：表示已经是好友；0：表示等待确认',
  `friendusername` varchar(20) DEFAULT NULL,
  `frienduserheadico` varchar(100) DEFAULT NULL,
  `friendgroup` int(11) DEFAULT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

/*Data for the table `qqfrienduser` */

/*Table structure for table `qqmessage` */

DROP TABLE IF EXISTS `qqmessage`;

CREATE TABLE `qqmessage` (
  `mid` int(11) NOT NULL AUTO_INCREMENT,
  `senderaccount` varchar(20) DEFAULT NULL,
  `senderheadico` varchar(100) DEFAULT NULL,
  `sendername` varchar(20) DEFAULT NULL,
  `messagecontent` varchar(500) DEFAULT NULL,
  `messagetime` varchar(200) DEFAULT NULL,
  `receivername` varchar(20) DEFAULT NULL,
  `receiverheadico` varchar(20) DEFAULT NULL,
  `receiveraccount` varchar(10) DEFAULT NULL,
  `receiverstate` int(11) DEFAULT NULL COMMENT '：已经接收；0：未被接收',
  PRIMARY KEY (`mid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `qqmessage` */

/*Table structure for table `qquser` */

DROP TABLE IF EXISTS `qquser`;

CREATE TABLE `qquser` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `useraccount` varchar(10) DEFAULT NULL,
  `userpwd` varchar(20) DEFAULT NULL,
  `userheadico` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

/*Data for the table `qquser` */

insert  into `qquser`(`uid`,`username`,`useraccount`,`userpwd`,`userheadico`) values (41,'玄冰','155334204','1234567','');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
