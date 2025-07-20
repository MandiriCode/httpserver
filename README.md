**Proyek Server HTTP Java untuk Manajemen Bansos**

Proyek ini adalah server web HTTP sederhana yang dibangun menggunakan Java JDK HTTP Server. Aplikasi ini memungkinkan pengguna untuk mengelola data penerima bantuan sosial (bansos) melalui antarmuka web yang sederhana.

**Fitur**

1. Manajemen Data Penerima: Tambah, lihat, edit, dan hapus data penerima bansos.
2. Pembaruan Status: Ubah status penerima menjadi "diterima" atau "ditolak".
3. Antarmuka Web Sederhana: Antarmuka pengguna yang mudah digunakan untuk berinteraksi dengan server.


**Teknologi yang Digunakan**

1. Java JDK HTTP Server: Untuk membuat server web.
2. MySQL: Untuk menyimpan data penerima.
3. HTML, CSS, dan JavaScript: Untuk membuat antarmuka pengguna.


**Cara Menjalankan Proyek**

1. Buat Database: Jalankan skrip database.sql di MySQL untuk membuat database dan tabel yang diperlukan.
2. Konfigurasi Database: Buka file src/ApiHandler.java dan ubah nilai DB_PASSWORD agar sesuai dengan kata sandi database Anda.
3. Unduh Pustaka: Pastikan Anda memiliki file gson-2.9.0.jar dan mysql-connector-java-8.0.28.jar di dalam direktori lib.
4. Kompilasi Proyek: Jalankan skrip compile.bat untuk mengkompilasi kode Java.
5. Jalankan Server: Jalankan skrip execute.bat untuk memulai server.
6. Akses Aplikasi: Buka browser web Anda dan buka http://localhost:8080.
