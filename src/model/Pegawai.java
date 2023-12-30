
package model;


public class Pegawai {
    private int id;
    private String nama;
    private String nik;
    private String tempatLahir;
    private String tglLahir;
    private String Alamat;
    private String nomor;
    private String jabatan;
    private String status;

    public Pegawai(int id, String nama, String nik, String tempatLahir, String tglLahir, String Alamat, String nomor, String jabatan, String status) {
        this.id = id;
        this.nama = nama;
        this.nik = nik;
        this.tempatLahir = tempatLahir;
        this.tglLahir = tglLahir;
        this.Alamat = Alamat;
        this.nomor = nomor;
        this.jabatan = jabatan;
        this.status = status;
    }

    public Pegawai() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getAlamat() {
        return Alamat;
    }

    public void setAlamat(String Alamat) {
        this.Alamat = Alamat;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
    