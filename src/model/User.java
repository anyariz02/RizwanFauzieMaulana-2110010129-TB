
package model;


public class User {
    private int idUser;
    private String namaUser;
    private String username;
    private String password;
   private String jabatanUser;

    public User() {
    }

    public User(int idUser, String namaUser, String username, String password, String jabatanUser) {
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.username = username;
        this.password = password;
        this.jabatanUser = jabatanUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJabatanUser() {
        return jabatanUser;
    }

    public void setJabatanUser(String jabatanUser) {
        this.jabatanUser = jabatanUser;
    }
   
   
    
}
