package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author anyariz02
 */
public class Koneksi {
    private final String URL = "jdbc:mysql://localhost:3306/kepegawaian";
    private final String USER = "root";
    private final String PASS = "";

    public Connection getConnection(){
        Connection con;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL,USER,PASS);
            System.out.println("Berhasil !");

            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Koneksi Gagal..");
            System.err.println(ex.getMessage());
            return null;
        }
        }

    public static void main(String[] args) {
        Koneksi kon = new Koneksi();
        kon.getConnection();
    }

    }
