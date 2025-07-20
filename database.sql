CREATE DATABASE bansos_db;

USE bansos_db;

CREATE TABLE penerima (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(255) NOT NULL,
    alamat TEXT NOT NULL,
    penghasilan INT NOT NULL,
    status ENUM('diterima', 'ditolak', 'menunggu') NOT NULL DEFAULT 'menunggu'
);