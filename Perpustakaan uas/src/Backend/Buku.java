package Backend;

import java.util.ArrayList;
import java.sql.*;

public class Buku {
    private int idBuku;
    private int idKategori;
    private String judul;
    private String penerbit;
    private String penulis;
    private Kategori kategori;

    @Override
    public String toString() {
        return judul;
    }

    public Buku(int idBuku, int idKategori, String judul, String penerbit, String penulis) {
        this.idBuku = idBuku;
        this.idKategori = idKategori;
        this.judul = judul;
        this.penerbit = penerbit;
        this.penulis = penulis;
    }

    public Buku() {}

    public int getIdbuku() { return idBuku; }
    public void setIdbuku(int idBuku) { this.idBuku = idBuku; }

    public int getIdKategori() { return idKategori; }
    public void setIdKategori(int idKategori) {
        this.idKategori = idKategori;
        this.kategori = new Kategori().getById(idKategori);
    }

    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }

    public String getPenerbit() { return penerbit; }
    public void setPenerbit(String penerbit) { this.penerbit = penerbit; }

    public String getPenulis() { return penulis; }
    public void setPenulis(String penulis) { this.penulis = penulis; }

    public Kategori getKategori() { return kategori; }
    public void setKategori(Kategori kategori) { this.kategori = kategori; }

    public Buku getById(int id) {
        Buku buk = new Buku();
        ResultSet rs = DBHelper.selectQuery("SELECT * FROM buku WHERE idbuku = '" + id + "'");

        try {
            if (rs.next()) {
                buk.setIdbuku(rs.getInt("idbuku"));
                buk.setIdKategori(rs.getInt("idkategori"));
                buk.setJudul(rs.getString("judul"));
                buk.setPenerbit(rs.getString("penerbit"));
                buk.setPenulis(rs.getString("penulis"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buk;
    }

    public ArrayList<Buku> getAll() {
        ArrayList<Buku> listBuku = new ArrayList<>();
        String sql = "SELECT b.*, k.nama AS nama_kategori FROM buku b LEFT JOIN kategori k ON b.idkategori = k.idkategori";
        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Buku buk = new Buku();
                buk.setIdbuku(rs.getInt("idbuku"));
                buk.setIdKategori(rs.getInt("idkategori"));
                buk.setJudul(rs.getString("judul"));
                buk.setPenerbit(rs.getString("penerbit"));
                buk.setPenulis(rs.getString("penulis"));

                Kategori kat = new Kategori();
                kat.setIdkategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama_kategori"));
                buk.setKategori(kat);

                listBuku.add(buk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBuku;
    }

    public ArrayList<Buku> search(String keyword) {
        ArrayList<Buku> listBuku = new ArrayList<>();
        String sql = "SELECT b.*, k.nama AS nama_kategori FROM buku b " +
                     "LEFT JOIN kategori k ON b.idkategori = k.idkategori " +
                     "WHERE b.idkategori LIKE '%" + keyword + "%' OR " +
                     "b.judul LIKE '%" + keyword + "%' OR " +
                     "b.penerbit LIKE '%" + keyword + "%' OR " +
                     "b.penulis LIKE '%" + keyword + "%' OR " +
                     "k.nama LIKE '%" + keyword + "%'";

        ResultSet rs = DBHelper.selectQuery(sql);

        try {
            while (rs.next()) {
                Buku buk = new Buku();
                buk.setIdbuku(rs.getInt("idbuku"));
                buk.setIdKategori(rs.getInt("idkategori"));
                buk.setJudul(rs.getString("judul"));
                buk.setPenerbit(rs.getString("penerbit"));
                buk.setPenulis(rs.getString("penulis"));

                Kategori kat = new Kategori();
                kat.setIdkategori(rs.getInt("idkategori"));
                kat.setNama(rs.getString("nama_kategori"));
                buk.setKategori(kat);

                listBuku.add(buk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listBuku;
    }

    public void save() {
        if (getById(idBuku).getIdbuku() == 0) {
            String SQL = "INSERT INTO buku (idkategori, judul, penerbit, penulis) VALUES (" +
                         "'" + this.getKategori().getIdkategori() + "', " +
                         "'" + this.judul + "', " +
                         "'" + this.penerbit + "', " +
                         "'" + this.penulis + "')";
            this.idBuku = DBHelper.insertQueryGetId(SQL);
        } else {
            String SQL = "UPDATE buku SET " +
                         "idkategori = '" + this.getKategori().getIdkategori() + "', " +
                         "judul = '" + this.judul + "', " +
                         "penerbit = '" + this.penerbit + "', " +
                         "penulis = '" + this.penulis + "' " +
                         "WHERE idbuku = '" + this.idBuku + "'";
            DBHelper.executeQuery(SQL);
        }
    }

    public void delete() {
        String SQL = "DELETE FROM buku WHERE idbuku = '" + this.idBuku + "'";
        DBHelper.executeQuery(SQL);
    }
}
