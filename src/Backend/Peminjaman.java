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
    private String tanggalpinjam;
    private String tanggalkembali;

    private Anggota anggota = new Anggota();
    private Buku buku = new Buku();

    // Getter dan Setter
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

    // Constructor
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

    // Get by ID
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

    // Get all
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

    // Save
    public void save() {
        if (getById(idpeminjaman).getIdpeminjaman() == 0) {
            String SQL = "INSERT INTO peminjaman (idanggota, idbuku, tanggalpinjam, tanggalkembali) VALUES ("
                    + "'" + this.idanggota + "', "
                    + "'" + this.idbuku + "', "
                    + "'" + this.tanggalpinjam + "', "
                    + "'" + this.tanggalkembali + "')";
            this.idpeminjaman = DBHelper.insertQueryGetId(SQL);
        } else {
            String SQL = "UPDATE peminjaman SET "
                    + "idanggota = '" + this.idanggota + "', "
                    + "idbuku = '" + this.idbuku + "', "
                    + "tanggalpinjam = '" + this.tanggalpinjam + "', "
                    + "tanggalkembali = '" + this.tanggalkembali + "' "
                    + "WHERE idpeminjaman = " + this.idpeminjaman;
            DBHelper.executeQuery(SQL);
        }
    }

    // Delete
    public void delete() {
        String SQL = "DELETE FROM peminjaman WHERE idpeminjaman = '" + this.idpeminjaman + "'";
        DBHelper.executeQuery(SQL);
    }
}