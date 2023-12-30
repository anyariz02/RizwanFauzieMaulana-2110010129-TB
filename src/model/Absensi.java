package model;

public class Absensi {
     private int idAbsens;
    private int id;
    private String tglAbsen;
    private String namaAbsen;
    private String nikAbsen;
    private String keterangan;

    public Absensi(int idAbsens, int id, String tglAbsen, String namaAbsen, String nikAbsen, String keterangan) {
        this.idAbsens = idAbsens;
        this.id = id;
        this.tglAbsen = tglAbsen;
        this.namaAbsen = namaAbsen;
        this.nikAbsen = nikAbsen;
        this.keterangan = keterangan;
    }

    public Absensi() {
    }

    public int getIdAbsens() {
        return idAbsens;
    }

    public void setIdAbsens(int idAbsens) {
        this.idAbsens = idAbsens;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTglAbsen() {
        return tglAbsen;
    }

    public void setTglAbsen(String tglAbsen) {
        this.tglAbsen = tglAbsen;
    }

    public String getNamaAbsen() {
        return namaAbsen;
    }

    public void setNamaAbsen(String namaAbsen) {
        this.namaAbsen = namaAbsen;
    }

    public String getNikAbsen() {
        return nikAbsen;
    }

    public void setNikAbsen(String nikAbsen) {
        this.nikAbsen = nikAbsen;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    
    
}
