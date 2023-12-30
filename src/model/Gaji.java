package model;


public class Gaji {
    private int idGaji;
    private int id;
    private String tglGaji;
    private String namaGaji;
    private String nikGaji;
    private String jabatanGaji;
    private String gajiPokok;
    private String tunjangan;
    private String gajiPot;
    private String gajiBersih;

    public Gaji(int idGaji, int id, String tglGaji, String namaGaji, String nikGaji, String jabatanGaji, String gajiPokok, String tunjangan, String gajiPot, String gajiBersih) {
        this.idGaji = idGaji;
        this.id = id;
        this.tglGaji = tglGaji;
        this.namaGaji = namaGaji;
        this.nikGaji = nikGaji;
        this.jabatanGaji = jabatanGaji;
        this.gajiPokok = gajiPokok;
        this.tunjangan = tunjangan;
        this.gajiPot = gajiPot;
        this.gajiBersih = gajiBersih;
    }

    public Gaji() {
    }

    public int getIdGaji() {
        return idGaji;
    }

    public void setIdGaji(int idGaji) {
        this.idGaji = idGaji;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTglGaji() {
        return tglGaji;
    }

    public void setTglGaji(String tglGaji) {
        this.tglGaji = tglGaji;
    }

    public String getNamaGaji() {
        return namaGaji;
    }

    public void setNamaGaji(String namaGaji) {
        this.namaGaji = namaGaji;
    }

    public String getNikGaji() {
        return nikGaji;
    }

    public void setNikGaji(String nikGaji) {
        this.nikGaji = nikGaji;
    }

    public String getJabatanGaji() {
        return jabatanGaji;
    }

    public void setJabatanGaji(String jabatanGaji) {
        this.jabatanGaji = jabatanGaji;
    }

    public String getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(String gajiPokok) {
        this.gajiPokok = gajiPokok;
    }

    public String getTunjangan() {
        return tunjangan;
    }

    public void setTunjangan(String tunjangan) {
        this.tunjangan = tunjangan;
    }

    public String getGajiPot() {
        return gajiPot;
    }

    public void setGajiPot(String gajiPot) {
        this.gajiPot = gajiPot;
    }

    public String getGajiBersih() {
        return gajiBersih;
    }

    public void setGajiBersih(String gajiBersih) {
        this.gajiBersih = gajiBersih;
    }

    
    }
