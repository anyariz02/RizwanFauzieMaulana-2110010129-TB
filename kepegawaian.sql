-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 30, 2023 at 03:50 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kepegawaian`
--

-- --------------------------------------------------------

--
-- Table structure for table `absensi`
--

CREATE TABLE `absensi` (
  `id_absen` int(3) NOT NULL,
  `id` int(3) NOT NULL,
  `tgl_absen` date NOT NULL,
  `keterangan` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `absensi`
--

INSERT INTO `absensi` (`id_absen`, `id`, `tgl_absen`, `keterangan`) VALUES
(2, 1, '2023-12-15', 'Hadir'),
(3, 11, '2023-12-15', 'Sakit'),
(4, 5, '2023-12-15', 'Sakit'),
(5, 14, '2023-12-15', 'Hadir'),
(6, 1, '2023-12-16', 'Hadir'),
(7, 1, '2023-12-17', 'Hadir'),
(8, 9, '2023-12-17', 'Sakit'),
(9, 1, '2023-12-24', 'Hadir'),
(10, 14, '2023-12-24', 'Hadir'),
(11, 15, '2023-12-28', 'Izin'),
(12, 13, '2023-12-28', 'Sakit'),
(13, 1, '2023-12-29', 'Hadir'),
(14, 15, '2023-12-29', 'Sakit');

-- --------------------------------------------------------

--
-- Table structure for table `gaji`
--

CREATE TABLE `gaji` (
  `id_gaji` int(3) NOT NULL,
  `id` int(3) NOT NULL,
  `tanggal` date NOT NULL,
  `gajipokok` varchar(32) NOT NULL,
  `tunjangan` varchar(32) NOT NULL,
  `potongan` varchar(32) NOT NULL,
  `gajibersih` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `gaji`
--

INSERT INTO `gaji` (`id_gaji`, `id`, `tanggal`, `gajipokok`, `tunjangan`, `potongan`, `gajibersih`) VALUES
(2, 1, '2023-12-17', '2500000', '50000', '0', '2550000'),
(3, 4, '2023-12-17', '1500000', '50000', '60000', '1490000'),
(4, 11, '2023-12-17', '5000000', '100000', '2000', '5098000'),
(5, 14, '2023-12-17', '1500000', '150000', '50000', '1600000'),
(6, 5, '2023-12-17', '5000000', '150000', '50000', '5100000'),
(7, 1, '2023-12-28', '2500000', '150000', '0', '2650000'),
(8, 14, '2023-12-28', '1500000', '150000', '0', '1650000');

-- --------------------------------------------------------

--
-- Table structure for table `pegawai`
--

CREATE TABLE `pegawai` (
  `id` int(3) NOT NULL,
  `nama` varchar(64) NOT NULL,
  `nik` varchar(10) NOT NULL,
  `tempat_lahir` varchar(32) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `alamat` text NOT NULL,
  `no_telepon` varchar(12) NOT NULL,
  `jabatan` varchar(15) NOT NULL,
  `status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `pegawai`
--

INSERT INTO `pegawai` (`id`, `nama`, `nik`, `tempat_lahir`, `tanggal_lahir`, `alamat`, `no_telepon`, `jabatan`, `status`) VALUES
(1, 'Rizwan', '1020304050', 'Rantau', '2023-12-01', 'Banjarbaru', '085701995362', 'Supervisor', 'Tetap'),
(4, 'Anya', '5544336677', 'Jepang', '2023-01-04', 'Sakura', '085701012233', 'Operator', 'Kontrak'),
(5, 'Hera', '0998877665', 'Amuntai', '2023-12-04', 'Jepang', '085233009933', 'Leader', 'Outsourcing'),
(9, 'Ueshama', '1122334455', 'Pelaihari', '2023-12-14', 'Barabai', '087766554433', 'Operator', 'Kontrak'),
(11, 'Dahlia', '1020304055', 'Martapura', '2023-12-14', 'Amuntai', '082233445566', 'Leader', 'Tetap'),
(13, 'Kudo', '5645342312', 'Barabai', '2023-12-15', 'Pelaihari', '082233449911', 'Operator', 'Outsourcing'),
(14, 'Dito', '4433234565', 'Sungai danau', '2001-01-09', 'Banjarmasin', '087766778899', 'Operator', 'Tetap'),
(15, 'Fauzie', '2211334455', 'Amuntai', '2023-12-28', 'Banjarbaru', '083322445566', 'Leader', 'Tetap');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nama` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `jabatan` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nama`, `username`, `password`, `jabatan`) VALUES
(1, 'Riaza', 'admin', 'admin', 'admin'),
(2, 'Anya', 'anya', 'anya', 'user'),
(3, 'Maulana', 'imau', 'imau', 'user'),
(5, 'kudo', 'kudo', 'haru', 'admin'),
(6, 'hera', 'hera', 'indah', 'user'),
(7, 'rifqi', 'rifqi', 'rifqi', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `absensi`
--
ALTER TABLE `absensi`
  ADD PRIMARY KEY (`id_absen`),
  ADD KEY `id` (`id`);

--
-- Indexes for table `gaji`
--
ALTER TABLE `gaji`
  ADD PRIMARY KEY (`id_gaji`),
  ADD KEY `gaji_ibfk_1` (`id`);

--
-- Indexes for table `pegawai`
--
ALTER TABLE `pegawai`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `absensi`
--
ALTER TABLE `absensi`
  MODIFY `id_absen` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `gaji`
--
ALTER TABLE `gaji`
  MODIFY `id_gaji` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `pegawai`
--
ALTER TABLE `pegawai`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `absensi`
--
ALTER TABLE `absensi`
  ADD CONSTRAINT `absensi_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `gaji`
--
ALTER TABLE `gaji`
  ADD CONSTRAINT `gaji_ibfk_1` FOREIGN KEY (`id`) REFERENCES `pegawai` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
