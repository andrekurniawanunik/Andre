 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author Andre Kurniawan
 */

import java.sql.*;
import java.util.ArrayList;

public class Peminjaman {
    private int idpeminjaman;
    private int idanggota; 
    private int idbuku;
    private int status;
    private String tanggalpinjam;
    private String tanggalkembali;

    private Anggota anggota = new Anggota();
    private Buku buku = new Buku();

    public int getIdpeminjaman() {
        return idpeminjaman;
    }

    public void setIdpeminjaman(int idpeminjaman) {
        this.idpeminjaman = idpeminjaman;
    }

    public int getIdanggota() {
        return idanggota;
    }

    public void setIdanggota(int idanggota) {
        this.idanggota = idanggota;
        this.anggota = new Anggota().getById(idanggota);
    }

    public int getIdbuku() {
        return idbuku;
    }

    public void setIdbuku(int idbuku) {
        this.idbuku = idbuku;
        this.buku = new Buku().getById(idbuku);
    }

    public String getTanggalpinjam() {
        return tanggalpinjam;
    }

    public void setTanggalpinjam(String tanggalpinjam) {
        this.tanggalpinjam = tanggalpinjam;
    }

    public String getTanggalkembali() {
        return tanggalkembali;
    }

    public void setTanggalkembali(String tanggalkembali) {
        this.tanggalkembali = tanggalkembali;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public Buku getBuku() {
        return buku;
    }
    
    public int getStatus() {
    return status;
}

public void setStatus(int status) {
    this.status = status;
}

    public Peminjaman() {
    }

    public Peminjaman(Anggota anggota, Buku buku, String tanggalpinjam, String tanggalkembali) {
        this.anggota = anggota;
        this.buku = buku;
        this.tanggalpinjam = tanggalpinjam;
        this.tanggalkembali = tanggalkembali;
        this.idanggota = anggota.getIdAnggota();
        this.idbuku = buku.getIdbuku();
    }

    public Peminjaman getById(int id) {
        Peminjaman pinjam = new Peminjaman();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM peminjaman WHERE idpeminjaman = '" + id + "'");

        try {
            while (rs.next()) {
                pinjam = new Peminjaman();
                pinjam.setIdpeminjaman(rs.getInt("idpeminjaman"));
                pinjam.setIdanggota(rs.getInt("idanggota"));
                pinjam.setIdbuku(rs.getInt("idbuku"));
                pinjam.setTanggalpinjam(rs.getString("tanggalpinjam"));
                pinjam.setTanggalkembali(rs.getString("tanggalkembali"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pinjam;
    }

    public ArrayList<Peminjaman> getAll() {
        ArrayList<Peminjaman> ListPeminjaman = new ArrayList<>();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM peminjaman");

        try {
            while (rs.next()) {
                Peminjaman pinjam = new Peminjaman();
                pinjam.setIdpeminjaman(rs.getInt("idpeminjaman"));
                pinjam.setIdanggota(rs.getInt("idanggota"));
                pinjam.setIdbuku(rs.getInt("idbuku"));
                pinjam.setTanggalpinjam(rs.getString("tanggalpinjam"));
                pinjam.setTanggalkembali(rs.getString("tanggalkembali"));
                ListPeminjaman.add(pinjam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ListPeminjaman;
    }

public void save() {
    if (getById(idpeminjaman).getIdpeminjaman() == 0) {
        String SQL = "INSERT INTO peminjaman (idanggota, idbuku, tanggalpinjam, tanggalkembali, status) VALUES ("
                + "'" + this.getAnggota().getIdAnggota() + "', "
                + "'" + this.getBuku().getIdbuku() + "', "
                + "'" + this.tanggalpinjam + "', "
                + "'-', " 
                + "'0')"; 
        this.idpeminjaman = DBHelper.insertQueryGetId(SQL);
    } else {
        String SQL = "UPDATE peminjaman SET "
                + "tanggalkembali = '" + this.tanggalkembali + "', "
                + "status = '1' "
                + "WHERE idpeminjaman = '" + this.idpeminjaman + "'";
        DBHelper.executeQuery(SQL);
    }
}

    public void delete() {
        String SQL = "DELETE FROM peminjaman WHERE idpeminjaman = '" + this.idpeminjaman + "'";
        DBHelper.executeQuery(SQL);
    }
    
public ArrayList<Peminjaman> search(String keyword) {
    // Di dalam file Backend/Peminjaman.java
    ArrayList<Peminjaman> listPeminjaman = new ArrayList<>();
    
    // SQL query yang diperbaiki untuk mencari di beberapa kolom
    String sql = "SELECT "
               + "    p.idpeminjaman, p.tanggalpinjam, p.tanggalkembali, p.status, "
               + "    a.idanggota, a.nama AS namaanggota, "
               + "    b.idbuku, b.judul AS judulbuku "
               + "FROM peminjaman p "
               + "LEFT JOIN anggota a ON p.idanggota = a.idanggota "
               + "LEFT JOIN buku b ON p.idbuku = b.idbuku "
               + "WHERE p.idpeminjaman LIKE ? "
               + "   OR a.nama LIKE ? "
               + "   OR b.judul LIKE ? ";

    try (Connection conn = DBHelper.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        // Mengatur parameter untuk PreparedStatement (lebih aman)
        String searchKeyword = "%" + keyword + "%";
        ps.setString(1, searchKeyword);
        ps.setString(2, searchKeyword);
        ps.setString(3, searchKeyword);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Peminjaman pinjam = new Peminjaman();
            pinjam.setIdpeminjaman(rs.getInt("idpeminjaman"));
            pinjam.setTanggalpinjam(rs.getString("tanggalpinjam"));
            pinjam.setTanggalkembali(rs.getString("tanggalkembali"));
            pinjam.setStatus(rs.getInt("status"));
            
            // Mengambil dan mengatur objek Anggota dan Buku
            pinjam.setIdanggota(rs.getInt("idanggota"));
            pinjam.setIdbuku(rs.getInt("idbuku"));
            
            listPeminjaman.add(pinjam);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return listPeminjaman;

}
    
    
}
