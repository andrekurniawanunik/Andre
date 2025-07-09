/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Frontend;
import Backend.*;

/**
 *
 * @author Andre Kurniawan
 */
public class TestBackend {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        Anggota ang1 = new Anggota("Novel", "Koleksi buku novel", "Harga Buku Novel");   
        Anggota ang2 = new Anggota("Referensi", "Buku referensi ilmiah", "Pusat Referensi");
        Anggota ang3 = new Anggota("Komik", "Komik anak-anak", "Komik Dewasa");

        // test insert
        ang1.save();
        ang2.save();
        ang3.save();

        // test update  
        ang2.setAlamat("Koleksi buku referensi ilmiah");
        ang2.save();    

        // test delete
        ang3.delete();

        // test select all
        for (Anggota a : new Anggota ().getAll())
        {
            System.out.println("Nama : " + a.getNama()+",Ket : " + a.getAlamat());
        }
        
        //test search
        for(Anggota a : new Anggota ().search("ilmiah"))
        {
            System.out.println("Nama:" + a.getNama()+ ",Ang:" + a.getAlamat());
        }
    }
    
}
