/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package frame;

import com.formdev.flatlaf.FlatLightLaf;
import database.Koneksi;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.Absensi;
import model.Gaji;
import model.Pegawai;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author anyariz02
 */
public class MainFrame extends javax.swing.JFrame {
    
    public Statement st;
    int positionX = 0;
    int positionY = 0;
     DateTimeFormatter date1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     LocalDateTime now = LocalDateTime.now();
    
    int status = 0;
    private final int tambah = 0;
    private final int update = 1;
      
    Pegawai pegawai;
    Gaji gaji;
    Absensi absensi;
    
     public MainFrame(String user) {
        initComponents();
        RoundedFrame();
        nama.setText(user);
        nama2.setText(user);
        ClosePanel();
        MainPanel();
        tfUserName.setText(user);
        
    }
     
     public void RoundedFrame(){
         setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
     }
   
       public ArrayList<Pegawai> getPegawaiList (String keyword) {
       ArrayList<Pegawai> pegawaiList = new ArrayList<Pegawai>();
       DefaultTableModel model = (DefaultTableModel) tbPegawai.getModel();
       Koneksi koneksi = new Koneksi();
       Connection connection = koneksi.getConnection();
       
           PreparedStatement ps;
           ResultSet rs;
           
           String sql = "SELECT * FROM pegawai";
           String order = " ORDER BY pegawai.id ASC";
           
            if(!keyword.equals("")){
                sql = sql + " WHERE nama = ? OR no_telepon like ?";
            }
            sql = sql+order;
            
            try{
                ps = connection.prepareStatement(sql);
                if(!keyword.equals("")){
                ps.setString(1,eCari.getText());
                ps.setString(2, "%"+eCari.getText()+"%");
            }
            rs = ps.executeQuery();
           while(rs.next()){
               pegawai = new Pegawai(
                                 rs.getInt("id"),
                                 rs.getString("nama"),
                                 rs.getString("nik"),
                                 rs.getString("tempat_lahir"),
                                 rs.getString("tanggal_lahir"),
                                 rs.getString("alamat"),
                                 rs.getString("no_telepon"),
                                 rs.getString("jabatan"),
                                 rs.getString("status"));
                pegawaiList.add(pegawai);
           }
       }catch(SQLException ex){
            System.err.println("ERROR getAnggotaList : "+ex);
            System.err.println(ex.getMessage());
        }
        return pegawaiList;
   }
    public void selectPegawai(String keyword){
        ArrayList<Pegawai> list;
        list = getPegawaiList(keyword);
        DefaultTableModel model = (DefaultTableModel)tbPegawai.getModel();
        Object[] row =  new Object[9];
        
        for (int i=0; i<list.size(); i++){
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getNama();
            row[2] = list.get(i).getNik();
            row[3] = list.get(i).getTempatLahir();
            row[4] = list.get(i).getTglLahir();
            row[5] = list.get(i).getAlamat();
            row[6] = list.get(i).getNomor();
            row[7] = list.get(i).getJabatan();
            row[8] = list.get(i).getStatus();
            
            model.addRow(row);
        }
    }
    
    public ArrayList<Absensi> getAbsensi () {
       ArrayList<Absensi> absensiList = new ArrayList<Absensi>();
       DefaultTableModel model = (DefaultTableModel) tbAbsen.getModel();
       Koneksi koneksi = new Koneksi();
       Connection connection = koneksi.getConnection();
       String tgl = date1.format(now);
       PreparedStatement ps;
       ResultSet rs;
       
            try{
                String sql = "SELECT absensi.*, pegawai.nik, pegawai.nama FROM absensi LEFT JOIN pegawai ON pegawai.id=absensi.id WHERE tgl_absen = ?";
                 ps = connection.prepareStatement(sql);
                 ps.setString(1,tgl);
                 rs = ps.executeQuery();
           while(rs.next()){
              absensi = new Absensi(
                                 rs.getInt("id_absen"),
                                 rs.getInt("id"),
                                 rs.getString("tgl_absen"),
                                 rs.getString("nama"),
                                 rs.getString("nik"),
                                  rs.getString("keterangan"));
                absensiList.add(absensi);
           }
       }catch(SQLException ex){
            System.err.println("ERROR  : "+ex);
            System.err.println(ex.getMessage());
        }
        return absensiList;
   }
    public void selectAbsensi(){
        ArrayList<Absensi> list;
        list = getAbsensi();
        DefaultTableModel model = (DefaultTableModel)tbAbsen.getModel();
        Object[] row =  new Object[6];
        
        for (int i=0; i<list.size(); i++){
            row[0] = list.get(i).getIdAbsens();
            row[1] = list.get(i).getId();
            row[2] = list.get(i).getTglAbsen();
            row[3] = list.get(i).getNikAbsen();
            row[4] = list.get(i).getNamaAbsen();
            row[5] = list.get(i).getKeterangan();
            model.addRow(row);
        }
    }
    
     public ArrayList<Gaji> getGaji (String keyword) {
       ArrayList<Gaji> gajiList = new ArrayList<Gaji>();
       DefaultTableModel model = (DefaultTableModel) tbGaji.getModel();
       Koneksi koneksi = new Koneksi();
       Connection connection = koneksi.getConnection();
       PreparedStatement ps;
       ResultSet rs;
       
        String sql = "SELECT gaji.*, pegawai.jabatan, pegawai.nik, pegawai.nama FROM gaji LEFT JOIN pegawai ON pegawai.id=gaji.id";
           String order = " ORDER BY gaji.id_gaji ASC";
           
            if(!keyword.equals("")){
                sql = sql + " WHERE nama = ? OR nik like ?";
            }
            sql = sql+order;
            
            try{
                ps = connection.prepareStatement(sql);
                if(!keyword.equals("")){
                ps.setString(1,eCariGaji.getText());
                ps.setString(2, "%"+eCariGaji.getText()+"%");
            }
            rs = ps.executeQuery();
           while(rs.next()){
              gaji = new Gaji(
                                 rs.getInt("id_gaji"),
                                 rs.getInt("id"),
                                 rs.getString("tanggal"),
                                 rs.getString("nama"),
                                 rs.getString("nik"),
                                rs.getString("jabatan"),
                      rs.getString("gajipokok"),
                      rs.getString("tunjangan"),
                      rs.getString("potongan"),
                      rs.getString("gajibersih"));
              gajiList.add(gaji);
           }
       }catch(SQLException ex){
            System.err.println("ERROR  : "+ex);
            System.err.println(ex.getMessage());
        }
        return gajiList;
   }
    public void selectGaji(String keyword){
        ArrayList<Gaji> list;
        list = getGaji(keyword);
        DefaultTableModel model = (DefaultTableModel)tbGaji.getModel();
        Object[] row =  new Object[10];
        
        for (int i=0; i<list.size(); i++){
            row[0] = list.get(i).getIdGaji();
            row[1] = list.get(i).getId();
            row[2] = list.get(i).getTglGaji();
            row[3] = list.get(i).getNamaGaji();
            row[4] = list.get(i).getNikGaji();
            row[5] = list.get(i).getJabatanGaji();
            row[6] = list.get(i).getGajiPokok();
            row[7] = list.get(i).getTunjangan();
            row[8] = list.get(i).getGajiPot();
            row[9] = list.get(i).getGajiBersih();
            model.addRow(row);
        }
    }
     
      public final void resetTable(String keyword){
        DefaultTableModel model = (DefaultTableModel)tbPegawai.getModel();
        DefaultTableModel model2 = (DefaultTableModel)tbAbsen.getModel();
         DefaultTableModel model3 = (DefaultTableModel)tbGaji.getModel();
        model.setRowCount(0);
        model2.setRowCount(0);
         model3.setRowCount(0);
        selectPegawai(keyword);
        selectAbsensi();
        selectGaji(keyword);
    }
    
     
     public void ClosePanel(){
         pnAbsen.setVisible(false);
         pnWidget.setVisible(false);
         pnPegawai.setVisible(false);
         pnGaji.setVisible(false);
         pnTambahPegawai.setVisible(false);
         pnEditUser.setVisible(false);
         pnTambahGaji.setVisible(false);
     }
     
     public void timeWidget(){
         DateTimeFormatter date = DateTimeFormatter.ofPattern("dd MMMM yyyy");
         DateTimeFormatter time = DateTimeFormatter.ofPattern("MM:mm:ss");
         DateTimeFormatter hari = DateTimeFormatter.ofPattern("eeee");
         LocalDateTime now = LocalDateTime.now();
         hariText.setText(hari.format(now));
         tglText.setText(date.format(now));
         waktuText.setText(time.format(now));
     }
     
          public void MainPanel(){
         ClosePanel();
         pnWidget.setVisible(true);
         pnMain.setVisible(true);
         timeWidget();
     }
     
     public void PegawaiPanel(String keyword){
         ClosePanel();
         pnPegawai.setVisible(true);
         resetTable(keyword);
    }
   
     public void GajiPanel(String keyword){
         ClosePanel();
         pnGaji.setVisible(true);
         resetTable(keyword);
     }
     
     public void AbsenPanel(){
         ClosePanel();
         pnAbsen.setVisible(true);
         resetTable("");
     }
     
     public void EditUserPanel(){
         ClosePanel();
         pnEditUser.setVisible(true);
     }
     
     public void ClearPegawai(){
           tfNama.setText("");
            tfNik.setText("");
            tfTempatLahir.setText("");
            tfTglLahir.setText("");
            tfAlamat.setText("");
            tfNomor.setText("");
            cbJabatan.setSelectedIndex(0);
            cbStatus.setSelectedIndex(0);
     }
     
      public void ClearAbsen(){
            tfNamaAbsen.setText("");
            tfIdAbsen.setText("");
            tfId2Absen.setText("");
            cbNikAbsen.setSelectedIndex(0);
            cbKeterangan.setSelectedIndex(0);
     }
            public void ClearGaji(){
            cbNamaGaji.setSelectedIndex(0);
            tfNikGaji.setText("");
            tfJabatanGaji.setText("");
            tfGajiPokok.setText("");
            tfGajiBersih.setText("");
            tfPotongan.setText("");
            cbKeluarga.setSelectedIndex(0);
            tfKeluarga.setText("");
     }

     
   

  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pmTable = new javax.swing.JPopupMenu();
        Ubah = new javax.swing.JMenuItem();
        Hapus = new javax.swing.JMenuItem();
        pmTableAbsen = new javax.swing.JPopupMenu();
        Ubah2 = new javax.swing.JMenuItem();
        Hapus2 = new javax.swing.JMenuItem();
        pmTableGaji = new javax.swing.JPopupMenu();
        Ubah3 = new javax.swing.JMenuItem();
        Hapus3 = new javax.swing.JMenuItem();
        date = new com.raven.datechooser.DateChooser();
        tfIdPegawai = new javax.swing.JTextField();
        tfIdAbsen = new javax.swing.JTextField();
        tfId2Absen = new javax.swing.JTextField();
        tfUserName = new javax.swing.JTextField();
        tfIdGaji = new javax.swing.JTextField();
        tfIdNamaGaji = new javax.swing.JTextField();
        tfKeluarga = new javax.swing.JTextField();
        bg = new javax.swing.JPanel();
        pnSide = new javax.swing.JPanel();
        logo = new javax.swing.JLabel();
        pnProfile = new javax.swing.JPanel();
        profil = new javax.swing.JLabel();
        nama = new javax.swing.JLabel();
        nama1 = new javax.swing.JLabel();
        btnHome = new custom.panel.PanelRound();
        jLabel35 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        btnPegawai = new custom.panel.PanelRound();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        btnGaji = new custom.panel.PanelRound();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        btnAbsen = new custom.panel.PanelRound();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        btnUser = new custom.panel.PanelRound();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        btnLogout1 = new custom.panel.PanelRound();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        pnBar = new javax.swing.JPanel();
        close = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        pnWidget = new javax.swing.JPanel();
        hariText = new javax.swing.JLabel();
        tglText = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        waktuText = new javax.swing.JLabel();
        pnPegawai = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPegawai = new javax.swing.JTable();
        eCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnTambahPegawai = new javax.swing.JButton();
        btnCetakPegawai = new javax.swing.JButton();
        pnTambahPegawai = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        lbTitle = new javax.swing.JLabel();
        tfNama = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        tfTglLahir = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        tfAlamat = new javax.swing.JTextField();
        tfNomor = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cbJabatan = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        btnSimpanPegawai = new javax.swing.JButton();
        btnBatalPegawai = new javax.swing.JButton();
        tfNik = new javax.swing.JTextField();
        btnDate = new javax.swing.JButton();
        tfTempatLahir = new javax.swing.JTextField();
        NIK = new javax.swing.JLabel();
        pnGaji = new javax.swing.JPanel();
        lbGaji = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbGaji = new javax.swing.JTable();
        eCariGaji = new javax.swing.JTextField();
        btnCariGaji = new javax.swing.JButton();
        btnTambahGaji = new javax.swing.JButton();
        btnCetakGaji = new javax.swing.JButton();
        pnTambahGaji = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        lbTitle1 = new javax.swing.JLabel();
        tfTanggalGaji = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        tfJabatanGaji = new javax.swing.JTextField();
        Jabatan = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        tfGajiPokok = new javax.swing.JTextField();
        tfPotongan = new javax.swing.JTextField();
        Potongan = new javax.swing.JLabel();
        btnSimpanGaji = new javax.swing.JButton();
        btnBatalGaji = new javax.swing.JButton();
        tfNikGaji = new javax.swing.JTextField();
        NIK1 = new javax.swing.JLabel();
        Potongan1 = new javax.swing.JLabel();
        tfGajiBersih = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        cbNamaGaji = new javax.swing.JComboBox<>();
        cbKeluarga = new javax.swing.JComboBox<>();
        pnAbsen = new javax.swing.JPanel();
        cbNikAbsen = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        tfNamaAbsen = new javax.swing.JTextField();
        tfTglAbsen = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        btnSimpanAbsen = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbAbsen = new javax.swing.JTable();
        jLabel33 = new javax.swing.JLabel();
        cbKeterangan = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        pnEditUser = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tfIdUser = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tfNamaUser = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tfUsernameUser = new javax.swing.JTextField();
        tfPasswordUser = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        pnMain = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        nama2 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();

        Ubah.setText("Ubah");
        Ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UbahActionPerformed(evt);
            }
        });
        pmTable.add(Ubah);

        Hapus.setText("Hapus");
        Hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HapusActionPerformed(evt);
            }
        });
        pmTable.add(Hapus);

        Ubah2.setText("Ubah");
        Ubah2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ubah2ActionPerformed(evt);
            }
        });
        pmTableAbsen.add(Ubah2);

        Hapus2.setText("Hapus");
        Hapus2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hapus2ActionPerformed(evt);
            }
        });
        pmTableAbsen.add(Hapus2);

        Ubah3.setText("Ubah");
        Ubah3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Ubah3ActionPerformed(evt);
            }
        });
        pmTableGaji.add(Ubah3);

        Hapus3.setText("Hapus");
        Hapus3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Hapus3ActionPerformed(evt);
            }
        });
        pmTableGaji.add(Hapus3);

        date.setForeground(new java.awt.Color(45, 45, 45));
        date.setDateFormat("yyyy-MM-dd");
        date.setTextRefernce(tfTglLahir);

        tfUserName.setEditable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        bg.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                bgMouseDragged(evt);
            }
        });
        bg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bgMousePressed(evt);
            }
        });
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnSide.setBackground(new java.awt.Color(45, 45, 45));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N

        pnProfile.setBackground(new java.awt.Color(45, 45, 45));

        profil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Male User_1.png"))); // NOI18N

        nama.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nama.setForeground(new java.awt.Color(255, 255, 255));
        nama.setText("Nama");

        nama1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nama1.setForeground(new java.awt.Color(255, 255, 255));
        nama1.setText("User");

        javax.swing.GroupLayout pnProfileLayout = new javax.swing.GroupLayout(pnProfile);
        pnProfile.setLayout(pnProfileLayout);
        pnProfileLayout.setHorizontalGroup(
            pnProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProfileLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(profil)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nama)
                    .addComponent(nama1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnProfileLayout.setVerticalGroup(
            pnProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnProfileLayout.createSequentialGroup()
                .addGroup(pnProfileLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnProfileLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(profil))
                    .addGroup(pnProfileLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(nama)
                        .addGap(3, 3, 3)
                        .addComponent(nama1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnHome.setBackground(new java.awt.Color(217, 217, 217));
        btnHome.setRoundBottomLeft(25);
        btnHome.setRoundBottomRight(25);
        btnHome.setRoundTopLeft(25);
        btnHome.setRoundTopRight(25);
        btnHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnHomeMousePressed(evt);
            }
        });

        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/home.png"))); // NOI18N

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel37.setText("Menu Utama");

        javax.swing.GroupLayout btnHomeLayout = new javax.swing.GroupLayout(btnHome);
        btnHome.setLayout(btnHomeLayout);
        btnHomeLayout.setHorizontalGroup(
            btnHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHomeLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnHomeLayout.setVerticalGroup(
            btnHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnHomeLayout.createSequentialGroup()
                .addGroup(btnHomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnHomeLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel35))
                    .addGroup(btnHomeLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel37)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnPegawai.setBackground(new java.awt.Color(153, 153, 153));
        btnPegawai.setRoundBottomLeft(25);
        btnPegawai.setRoundBottomRight(25);
        btnPegawai.setRoundTopLeft(25);
        btnPegawai.setRoundTopRight(25);
        btnPegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnPegawaiMousePressed(evt);
            }
        });

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/people.png"))); // NOI18N

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel39.setText("Data Pegawai");

        javax.swing.GroupLayout btnPegawaiLayout = new javax.swing.GroupLayout(btnPegawai);
        btnPegawai.setLayout(btnPegawaiLayout);
        btnPegawaiLayout.setHorizontalGroup(
            btnPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPegawaiLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel39)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnPegawaiLayout.setVerticalGroup(
            btnPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnPegawaiLayout.createSequentialGroup()
                .addGroup(btnPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnPegawaiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel38))
                    .addGroup(btnPegawaiLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel39)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnGaji.setBackground(new java.awt.Color(153, 153, 153));
        btnGaji.setRoundBottomLeft(25);
        btnGaji.setRoundBottomRight(25);
        btnGaji.setRoundTopLeft(25);
        btnGaji.setRoundTopRight(25);
        btnGaji.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnGajiMousePressed(evt);
            }
        });

        jLabel40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lpPegawai.png"))); // NOI18N

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel41.setText("Data Gaji");

        javax.swing.GroupLayout btnGajiLayout = new javax.swing.GroupLayout(btnGaji);
        btnGaji.setLayout(btnGajiLayout);
        btnGajiLayout.setHorizontalGroup(
            btnGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGajiLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel40)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel41)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnGajiLayout.setVerticalGroup(
            btnGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnGajiLayout.createSequentialGroup()
                .addGroup(btnGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnGajiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel40))
                    .addGroup(btnGajiLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel41)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnAbsen.setBackground(new java.awt.Color(153, 153, 153));
        btnAbsen.setRoundBottomLeft(25);
        btnAbsen.setRoundBottomRight(25);
        btnAbsen.setRoundTopLeft(25);
        btnAbsen.setRoundTopRight(25);
        btnAbsen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAbsenMousePressed(evt);
            }
        });

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/absen.png"))); // NOI18N

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel43.setText("Absensi");

        javax.swing.GroupLayout btnAbsenLayout = new javax.swing.GroupLayout(btnAbsen);
        btnAbsen.setLayout(btnAbsenLayout);
        btnAbsenLayout.setHorizontalGroup(
            btnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAbsenLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel42)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel43)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnAbsenLayout.setVerticalGroup(
            btnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAbsenLayout.createSequentialGroup()
                .addGroup(btnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnAbsenLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel42))
                    .addGroup(btnAbsenLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel43)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnUser.setBackground(new java.awt.Color(153, 153, 153));
        btnUser.setRoundBottomLeft(25);
        btnUser.setRoundBottomRight(25);
        btnUser.setRoundTopLeft(25);
        btnUser.setRoundTopRight(25);
        btnUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnUserMousePressed(evt);
            }
        });

        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/admin.png"))); // NOI18N

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel45.setText("Edit Akun User");

        javax.swing.GroupLayout btnUserLayout = new javax.swing.GroupLayout(btnUser);
        btnUser.setLayout(btnUserLayout);
        btnUserLayout.setHorizontalGroup(
            btnUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUserLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel45)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnUserLayout.setVerticalGroup(
            btnUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnUserLayout.createSequentialGroup()
                .addGroup(btnUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnUserLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel44))
                    .addGroup(btnUserLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel45)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnLogout1.setBackground(new java.awt.Color(153, 153, 153));
        btnLogout1.setRoundBottomLeft(25);
        btnLogout1.setRoundBottomRight(25);
        btnLogout1.setRoundTopLeft(25);
        btnLogout1.setRoundTopRight(25);
        btnLogout1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLogout1MousePressed(evt);
            }
        });

        jLabel46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/exit.png"))); // NOI18N

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel47.setText("Logout");

        javax.swing.GroupLayout btnLogout1Layout = new javax.swing.GroupLayout(btnLogout1);
        btnLogout1.setLayout(btnLogout1Layout);
        btnLogout1Layout.setHorizontalGroup(
            btnLogout1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogout1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel47)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnLogout1Layout.setVerticalGroup(
            btnLogout1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnLogout1Layout.createSequentialGroup()
                .addGroup(btnLogout1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnLogout1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel46))
                    .addGroup(btnLogout1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel47)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnSideLayout = new javax.swing.GroupLayout(pnSide);
        pnSide.setLayout(pnSideLayout);
        pnSideLayout.setHorizontalGroup(
            pnSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnSideLayout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(logo)
                .addGap(29, 29, 29))
            .addComponent(pnProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnSideLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPegawai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGaji, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAbsen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLogout1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnSideLayout.setVerticalGroup(
            pnSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSideLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        bg.add(pnSide, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 590));

        pnBar.setBackground(new java.awt.Color(45, 45, 45));
        pnBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        pnBar.add(close, new org.netbeans.lib.awtextra.AbsoluteConstraints(772, 15, -1, -1));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/min.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        pnBar.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(729, 15, -1, -1));

        bg.add(pnBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 820, 60));

        pnWidget.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        hariText.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        hariText.setText("Hari");

        tglText.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        tglText.setText("Tanggal");

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/clock.png"))); // NOI18N

        waktuText.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        waktuText.setText("Waktu");

        javax.swing.GroupLayout pnWidgetLayout = new javax.swing.GroupLayout(pnWidget);
        pnWidget.setLayout(pnWidgetLayout);
        pnWidgetLayout.setHorizontalGroup(
            pnWidgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnWidgetLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(pnWidgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hariText)
                    .addComponent(tglText)
                    .addComponent(waktuText))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnWidgetLayout.setVerticalGroup(
            pnWidgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnWidgetLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnWidgetLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnWidgetLayout.createSequentialGroup()
                        .addComponent(hariText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tglText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(waktuText, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        bg.add(pnWidget, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 480, 260, 90));

        pnPegawai.setBackground(new java.awt.Color(255, 255, 255));
        pnPegawai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel17.setText("Data Pegawai");

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        tbPegawai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tbPegawai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Nama", "NIK", "Tempat Lahir", "Tanggal Lahir", "Alamat", "No. Telp", "Jabatan", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPegawai.setEditingColumn(0);
        tbPegawai.setEditingRow(0);
        tbPegawai.setGridColor(new java.awt.Color(255, 255, 255));
        tbPegawai.setRowHeight(30);
        tbPegawai.setSelectionBackground(new java.awt.Color(45, 45, 45));
        tbPegawai.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbPegawai.getTableHeader().setReorderingAllowed(false);
        tbPegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbPegawaiMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbPegawai);
        if (tbPegawai.getColumnModel().getColumnCount() > 0) {
            tbPegawai.getColumnModel().getColumn(0).setMinWidth(0);
            tbPegawai.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbPegawai.getColumnModel().getColumn(0).setMaxWidth(0);
            tbPegawai.getColumnModel().getColumn(1).setResizable(false);
            tbPegawai.getColumnModel().getColumn(2).setResizable(false);
            tbPegawai.getColumnModel().getColumn(3).setResizable(false);
            tbPegawai.getColumnModel().getColumn(4).setResizable(false);
            tbPegawai.getColumnModel().getColumn(5).setResizable(false);
            tbPegawai.getColumnModel().getColumn(6).setResizable(false);
            tbPegawai.getColumnModel().getColumn(7).setResizable(false);
            tbPegawai.getColumnModel().getColumn(8).setResizable(false);
        }

        eCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eCariActionPerformed(evt);
            }
        });
        eCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                eCariKeyTyped(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnTambahPegawai.setBackground(new java.awt.Color(45, 45, 45));
        btnTambahPegawai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTambahPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahPegawai.setText("Tambah Data");
        btnTambahPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPegawaiActionPerformed(evt);
            }
        });

        btnCetakPegawai.setBackground(new java.awt.Color(45, 45, 45));
        btnCetakPegawai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCetakPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakPegawai.setText("Cetak Data");
        btnCetakPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakPegawaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnPegawaiLayout = new javax.swing.GroupLayout(pnPegawai);
        pnPegawai.setLayout(pnPegawaiLayout);
        pnPegawaiLayout.setHorizontalGroup(
            pnPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPegawaiLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnPegawaiLayout.createSequentialGroup()
                        .addComponent(btnTambahPegawai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCetakPegawai)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(eCari, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnPegawaiLayout.setVerticalGroup(
            pnPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPegawaiLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel17)
                .addGap(33, 33, 33)
                .addGroup(pnPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari)
                    .addComponent(btnTambahPegawai)
                    .addComponent(btnCetakPegawai))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        bg.add(pnPegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 800, 510));

        pnTambahPegawai.setBackground(new java.awt.Color(255, 255, 255));
        pnTambahPegawai.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel21.setText("Nama");

        lbTitle.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lbTitle.setText("Tambah Data Pegawai");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setText("Tempat Lahir");

        tfTglLahir.setFocusable(false);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel24.setText("Tanggal Lahir");

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel25.setText("Alamat");

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel26.setText("No. Telepon");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel27.setText("Jabatan");

        cbJabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----", "Operator", "Leader", "Supervisor", "Manager" }));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel29.setText("Status");

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----", "Outsourcing", "Kontrak", "Tetap" }));

        btnSimpanPegawai.setBackground(new java.awt.Color(45, 45, 45));
        btnSimpanPegawai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSimpanPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanPegawai.setText("Simpan");
        btnSimpanPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanPegawaiActionPerformed(evt);
            }
        });

        btnBatalPegawai.setBackground(new java.awt.Color(255, 0, 0));
        btnBatalPegawai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBatalPegawai.setForeground(new java.awt.Color(255, 255, 255));
        btnBatalPegawai.setText("Batal");
        btnBatalPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalPegawaiActionPerformed(evt);
            }
        });

        btnDate.setText("...");
        btnDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDateActionPerformed(evt);
            }
        });

        NIK.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        NIK.setText("NIK");

        javax.swing.GroupLayout pnTambahPegawaiLayout = new javax.swing.GroupLayout(pnTambahPegawai);
        pnTambahPegawai.setLayout(pnTambahPegawaiLayout);
        pnTambahPegawaiLayout.setHorizontalGroup(
            pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTambahPegawaiLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTitle)
                    .addGroup(pnTambahPegawaiLayout.createSequentialGroup()
                        .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(jLabel29)
                            .addComponent(NIK)
                            .addComponent(jLabel23))
                        .addGap(40, 40, 40)
                        .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfNik)
                            .addComponent(tfNama)
                            .addComponent(tfAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                            .addComponent(tfNomor, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                            .addComponent(cbJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tfTempatLahir, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                            .addGroup(pnTambahPegawaiLayout.createSequentialGroup()
                                .addComponent(tfTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(63, 195, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTambahPegawaiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpanPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBatalPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        pnTambahPegawaiLayout.setVerticalGroup(
            pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTambahPegawaiLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbTitle)
                .addGap(31, 31, 31)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(tfNama, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNik, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NIK))
                .addGap(12, 12, 12)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(tfTempatLahir, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTglLahir, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addGap(12, 12, 12)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(tfAlamat, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(tfNomor, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(cbJabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(83, 83, 83)
                .addGroup(pnTambahPegawaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatalPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpanPegawai, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        cbJabatan.getAccessibleContext().setAccessibleName("");

        bg.add(pnTambahPegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 800, 510));

        pnGaji.setBackground(new java.awt.Color(255, 255, 255));
        pnGaji.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        lbGaji.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lbGaji.setText("Data Gaji");

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

        tbGaji.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        tbGaji.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "id", "Tanggal", "Nama", "NIK", "jabatan", "Gaji Pokok", "Tunjangan", "Potongan", "Gaji Bersih"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbGaji.setEditingColumn(0);
        tbGaji.setEditingRow(0);
        tbGaji.setGridColor(new java.awt.Color(255, 255, 255));
        tbGaji.setRowHeight(30);
        tbGaji.setSelectionBackground(new java.awt.Color(45, 45, 45));
        tbGaji.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbGaji.getTableHeader().setReorderingAllowed(false);
        tbGaji.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbGajiMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbGaji);
        if (tbGaji.getColumnModel().getColumnCount() > 0) {
            tbGaji.getColumnModel().getColumn(0).setMinWidth(0);
            tbGaji.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbGaji.getColumnModel().getColumn(0).setMaxWidth(0);
            tbGaji.getColumnModel().getColumn(1).setMinWidth(0);
            tbGaji.getColumnModel().getColumn(1).setPreferredWidth(0);
            tbGaji.getColumnModel().getColumn(1).setMaxWidth(0);
            tbGaji.getColumnModel().getColumn(2).setResizable(false);
            tbGaji.getColumnModel().getColumn(3).setResizable(false);
            tbGaji.getColumnModel().getColumn(4).setResizable(false);
            tbGaji.getColumnModel().getColumn(5).setMinWidth(0);
            tbGaji.getColumnModel().getColumn(5).setPreferredWidth(0);
            tbGaji.getColumnModel().getColumn(5).setMaxWidth(0);
            tbGaji.getColumnModel().getColumn(6).setResizable(false);
            tbGaji.getColumnModel().getColumn(8).setResizable(false);
            tbGaji.getColumnModel().getColumn(9).setResizable(false);
        }

        eCariGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eCariGajiActionPerformed(evt);
            }
        });
        eCariGaji.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                eCariGajiKeyTyped(evt);
            }
        });

        btnCariGaji.setText("Cari");
        btnCariGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariGajiActionPerformed(evt);
            }
        });

        btnTambahGaji.setBackground(new java.awt.Color(45, 45, 45));
        btnTambahGaji.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTambahGaji.setForeground(new java.awt.Color(255, 255, 255));
        btnTambahGaji.setText("Tambah Data");
        btnTambahGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahGajiActionPerformed(evt);
            }
        });

        btnCetakGaji.setBackground(new java.awt.Color(45, 45, 45));
        btnCetakGaji.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCetakGaji.setForeground(new java.awt.Color(255, 255, 255));
        btnCetakGaji.setText("Cetak Data");
        btnCetakGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakGajiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnGajiLayout = new javax.swing.GroupLayout(pnGaji);
        pnGaji.setLayout(pnGajiLayout);
        pnGajiLayout.setHorizontalGroup(
            pnGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGajiLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbGaji)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 756, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnGajiLayout.createSequentialGroup()
                        .addComponent(btnTambahGaji)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCetakGaji)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(eCariGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCariGaji)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        pnGajiLayout.setVerticalGroup(
            pnGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGajiLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lbGaji)
                .addGap(33, 33, 33)
                .addGroup(pnGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eCariGaji, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariGaji)
                    .addComponent(btnTambahGaji)
                    .addComponent(btnCetakGaji))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        bg.add(pnGaji, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 800, 510));

        pnTambahGaji.setBackground(new java.awt.Color(255, 255, 255));
        pnTambahGaji.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setText("Tanggal");

        lbTitle1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        lbTitle1.setText("Tambah Data Gaji");

        tfTanggalGaji.setEditable(false);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel34.setText("NIK");

        tfJabatanGaji.setFocusable(false);

        Jabatan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Jabatan.setText("Jabatan");

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel36.setText("Gaji Pokok");

        tfGajiPokok.setEditable(false);

        tfPotongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfPotonganActionPerformed(evt);
            }
        });
        tfPotongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfPotonganKeyTyped(evt);
            }
        });

        Potongan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Potongan.setText("Potongan");

        btnSimpanGaji.setBackground(new java.awt.Color(45, 45, 45));
        btnSimpanGaji.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSimpanGaji.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanGaji.setText("Simpan");
        btnSimpanGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanGajiActionPerformed(evt);
            }
        });

        btnBatalGaji.setBackground(new java.awt.Color(255, 0, 0));
        btnBatalGaji.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBatalGaji.setForeground(new java.awt.Color(255, 255, 255));
        btnBatalGaji.setText("Batal");
        btnBatalGaji.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalGajiActionPerformed(evt);
            }
        });

        tfNikGaji.setFocusable(false);

        NIK1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        NIK1.setText("Nama");

        Potongan1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Potongan1.setText("Gaji Bersih");

        tfGajiBersih.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tfGajiBersihKeyTyped(evt);
            }
        });

        jLabel20.setForeground(new java.awt.Color(102, 102, 102));
        jLabel20.setText("*Lakukan enter pada textfield Potongan untuk melihat gaji bersih");

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel50.setText("Status Keluarga");

        cbNamaGaji.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----" }));
        cbNamaGaji.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbNamaGajiItemStateChanged(evt);
            }
        });

        cbKeluarga.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----", "Belum Menikah", "Sudah Menikah, Belum Punya Anak", "Sudah Menikah Punya Anak" }));
        cbKeluarga.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbKeluargaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnTambahGajiLayout = new javax.swing.GroupLayout(pnTambahGaji);
        pnTambahGaji.setLayout(pnTambahGajiLayout);
        pnTambahGajiLayout.setHorizontalGroup(
            pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTambahGajiLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnTambahGajiLayout.createSequentialGroup()
                            .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(Potongan)
                                .addComponent(Potongan1))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                            .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfPotongan, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                                .addComponent(jLabel20)
                                .addComponent(tfGajiBersih)))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnTambahGajiLayout.createSequentialGroup()
                            .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel28)
                                .addComponent(Jabatan)
                                .addComponent(jLabel36)
                                .addComponent(NIK1)
                                .addComponent(jLabel34)
                                .addComponent(jLabel50))
                            .addGap(40, 40, 40)
                            .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tfTanggalGaji)
                                .addComponent(tfGajiPokok, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                                .addComponent(tfNikGaji, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                                .addComponent(tfJabatanGaji)
                                .addComponent(cbNamaGaji, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cbKeluarga, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addComponent(lbTitle1))
                .addGap(111, 180, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTambahGajiLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSimpanGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBatalGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        pnTambahGajiLayout.setVerticalGroup(
            pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTambahGajiLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lbTitle1)
                .addGap(31, 31, 31)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(tfTanggalGaji, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NIK1)
                    .addComponent(cbNamaGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(tfNikGaji, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfJabatanGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Jabatan))
                .addGap(15, 15, 15)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(tfGajiPokok, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(15, 15, 15)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(cbKeluarga, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Potongan)
                    .addComponent(tfPotongan, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addComponent(jLabel20)
                .addGap(15, 15, 15)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Potongan1)
                    .addComponent(tfGajiBersih, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addGroup(pnTambahGajiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBatalGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpanGaji, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(138, 138, 138))
        );

        bg.add(pnTambahGaji, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 800, 510));

        pnAbsen.setBackground(new java.awt.Color(255, 255, 255));
        pnAbsen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        cbNikAbsen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----" }));
        cbNikAbsen.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbNikAbsenItemStateChanged(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel22.setText("Absensi Hari ini");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel30.setText("NIK");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel31.setText("Nama");

        tfNamaAbsen.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        tfNamaAbsen.setFocusable(false);

        tfTglAbsen.setEditable(false);
        tfTglAbsen.setFocusable(false);

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel32.setText("Tanggal");

        btnSimpanAbsen.setBackground(new java.awt.Color(45, 45, 45));
        btnSimpanAbsen.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSimpanAbsen.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanAbsen.setText("Simpan");
        btnSimpanAbsen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanAbsenActionPerformed(evt);
            }
        });

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        tbAbsen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "id", "Tanggal", "NIK", "Nama", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbAbsen.setSelectionBackground(new java.awt.Color(45, 45, 45));
        tbAbsen.setSelectionForeground(new java.awt.Color(255, 255, 255));
        tbAbsen.getTableHeader().setReorderingAllowed(false);
        tbAbsen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbAbsenMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tbAbsen);
        if (tbAbsen.getColumnModel().getColumnCount() > 0) {
            tbAbsen.getColumnModel().getColumn(0).setMinWidth(0);
            tbAbsen.getColumnModel().getColumn(0).setPreferredWidth(0);
            tbAbsen.getColumnModel().getColumn(0).setMaxWidth(0);
            tbAbsen.getColumnModel().getColumn(1).setMinWidth(0);
            tbAbsen.getColumnModel().getColumn(1).setPreferredWidth(0);
            tbAbsen.getColumnModel().getColumn(1).setMaxWidth(0);
            tbAbsen.getColumnModel().getColumn(2).setResizable(false);
            tbAbsen.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel33.setText("Keterangan");

        cbKeterangan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "----", "Hadir", "Sakit", "Izin", "Alpa" }));

        jButton1.setBackground(new java.awt.Color(45, 45, 45));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cetak Absen");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnAbsenLayout = new javax.swing.GroupLayout(pnAbsen);
        pnAbsen.setLayout(pnAbsenLayout);
        pnAbsenLayout.setHorizontalGroup(
            pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAbsenLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addGroup(pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(pnAbsenLayout.createSequentialGroup()
                            .addGroup(pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel30)
                                .addComponent(jLabel31)
                                .addComponent(jLabel32)
                                .addComponent(jLabel33))
                            .addGap(74, 74, 74)
                            .addGroup(pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cbNikAbsen, 0, 260, Short.MAX_VALUE)
                                .addComponent(tfNamaAbsen)
                                .addComponent(tfTglAbsen)
                                .addComponent(cbKeterangan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSimpanAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1)
                            .addGap(6, 6, 6))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        pnAbsenLayout.setVerticalGroup(
            pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnAbsenLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addGroup(pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfTglAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbNikAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30))
                .addGap(6, 6, 6)
                .addGroup(pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfNamaAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addGap(6, 6, 6)
                .addGroup(pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(btnSimpanAbsen))
                    .addGroup(pnAbsenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel33)
                        .addComponent(cbKeterangan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        bg.add(pnAbsen, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 800, 510));

        pnEditUser.setBackground(new java.awt.Color(255, 255, 255));
        pnEditUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel19.setText("Edit Data User");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("ID User");

        tfIdUser.setEditable(false);
        tfIdUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setText("Nama");

        tfNamaUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tfNamaUser.setSelectionColor(new java.awt.Color(0, 0, 0));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setText("Username");

        tfUsernameUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        tfPasswordUser.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel18.setText("Password");

        jButton2.setBackground(new java.awt.Color(45, 45, 45));
        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Simpan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnEditUserLayout = new javax.swing.GroupLayout(pnEditUser);
        pnEditUser.setLayout(pnEditUserLayout);
        pnEditUserLayout.setHorizontalGroup(
            pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEditUserLayout.createSequentialGroup()
                .addGroup(pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnEditUserLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jLabel19))
                    .addGroup(pnEditUserLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnEditUserLayout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(tfPasswordUser, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnEditUserLayout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(54, 54, 54)
                                .addComponent(tfUsernameUser, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnEditUserLayout.createSequentialGroup()
                                .addGroup(pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfNamaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnEditUserLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(316, Short.MAX_VALUE))
        );
        pnEditUserLayout.setVerticalGroup(
            pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnEditUserLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel19)
                .addGap(35, 35, 35)
                .addGroup(pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(tfIdUser, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tfNamaUser, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(tfUsernameUser, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnEditUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(tfPasswordUser, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(243, Short.MAX_VALUE))
        );

        bg.add(pnEditUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 800, 510));

        pnMain.setBackground(new java.awt.Color(255, 255, 255));
        pnMain.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel48.setText("Hai, Selamat datang");

        nama2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        nama2.setText("Nama");

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel49.setText("Anda telah mengakses sebagai User pada aplikasi ini, silahkan gunakan menu navigasi dikiri untuk mengelola data.");

        javax.swing.GroupLayout pnMainLayout = new javax.swing.GroupLayout(pnMain);
        pnMain.setLayout(pnMainLayout);
        pnMainLayout.setHorizontalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addGroup(pnMainLayout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nama2)))
                .addContainerGap(89, Short.MAX_VALUE))
        );
        pnMainLayout.setVerticalGroup(
            pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnMainLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(nama2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel49)
                .addContainerGap(440, Short.MAX_VALUE))
        );

        bg.add(pnMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 800, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
      resetTable("");
    }//GEN-LAST:event_formWindowActivated

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeMouseClicked

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        this.setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void bgMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgMousePressed
        positionX = evt.getX();
        positionY = evt.getY();
        
    }//GEN-LAST:event_bgMousePressed

    private void bgMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bgMouseDragged
       setLocation(evt.getXOnScreen()-positionX, evt.getYOnScreen()-positionY);
    }//GEN-LAST:event_bgMouseDragged

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
       resetTable(eCari.getText());
    }//GEN-LAST:event_btnCariActionPerformed

    private void eCariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eCariKeyTyped
       resetTable("");
    }//GEN-LAST:event_eCariKeyTyped

    private void eCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eCariActionPerformed
        resetTable(eCari.getText());
    }//GEN-LAST:event_eCariActionPerformed

    private void tbPegawaiMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPegawaiMouseReleased
        if(evt.getButton() == MouseEvent.BUTTON3){
            if(evt.isPopupTrigger() && tbPegawai.getSelectedRowCount()!=0){
                pmTable.show(evt.getComponent(),evt.getX(),evt.getY());
            }
        }
    }//GEN-LAST:event_tbPegawaiMouseReleased

    private void tbGajiMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGajiMouseReleased
         if(evt.getButton() == MouseEvent.BUTTON3){
            if(evt.isPopupTrigger() && tbGaji.getSelectedRowCount()!=0){
                pmTableGaji.show(evt.getComponent(),evt.getX(),evt.getY());
            }
        }
    }//GEN-LAST:event_tbGajiMouseReleased

    private void eCariGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eCariGajiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_eCariGajiActionPerformed

    private void eCariGajiKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_eCariGajiKeyTyped
        resetTable("");
    }//GEN-LAST:event_eCariGajiKeyTyped

    private void btnCariGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariGajiActionPerformed
        resetTable(eCariGaji.getText());
    }//GEN-LAST:event_btnCariGajiActionPerformed

    private void btnSimpanPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanPegawaiActionPerformed
            String idTable = tfIdPegawai.getText();
            String nama = tfNama.getText();
            String nik = tfNik.getText();
            String TpLahir = tfTempatLahir.getText();
            String tglahir = tfTglLahir.getText();
            String alamat = tfAlamat.getText();
            String no = tfNomor.getText();
            String jabatan = cbJabatan.getSelectedItem().toString();
            String stat = cbStatus.getSelectedItem().toString();
            
            Koneksi koneksi = new Koneksi();
            Connection con = koneksi.getConnection();
            PreparedStatement ps;
            
            try {
                if(status==tambah) {
                    if(nama.isEmpty() ||
                       nik.isEmpty() ||
                       TpLahir.isEmpty()||
                       tglahir.isEmpty() ||   
                       alamat.isEmpty() ||
                       no.isEmpty() ||
                       cbJabatan.getSelectedIndex() == 0 ||
                       cbStatus.getSelectedIndex() == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Isi Data Terlebih Dahulu");
                    }else{
                        
                    st = con.createStatement();
                    st.execute("INSERT INTO pegawai (nama, nik, tempat_lahir, tanggal_lahir, alamat, no_telepon, jabatan, status) VALUES('"+nama+"','"+nik+"','"+TpLahir+"','"+tglahir+"','"+alamat+"','"+no+"','"+jabatan+"','"+stat+"')");
                    JOptionPane.showMessageDialog(null, "Data Pegawai Berhasil Ditambahkan");
                    ClearPegawai();
                    ClosePanel();
                    PegawaiPanel("");
                    }
         }else{
                   //String qry = "UPDATE pegawai set (nama, nik, tempat_lahir, tanggal_lahir, alamat, no_telepon, jabatan, status) VALUES('"+nama+"','"+nik+"','"+TpLahir+"','"+tglahir+"','"+alamat+"','"+no+"','"+jabatan+"','"+stat+"') WHERE = ?";
                   String qry = "UPDATE pegawai SET nama = ?, nik = ?,"
                            + "tempat_lahir = ?, tanggal_lahir = ?,"
                            + "alamat = ?, no_telepon = ?,"
                            + "jabatan = ?, status = ? WHERE id = ?";
                   ps = con.prepareStatement(qry);
                   ps.setString(1, nama);
                   ps.setString(2, nik);
                   ps.setString(3, TpLahir);
                   ps.setString(4, tglahir);
                   ps.setString(5,alamat);
                   ps.setString(6, no);
                   ps.setString(7, jabatan);
                   ps.setString(8, stat);
                   ps.setString(9, idTable);
                   ps.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Data Pegawai Berhasil Diupdate");
                    ClearPegawai();
                    ClosePanel();
                    status = tambah;
                    PegawaiPanel("");
                }
            }catch (SQLException ex) {
                System.err.println("Error : "+ex);
            }
         
    }//GEN-LAST:event_btnSimpanPegawaiActionPerformed

    private void btnBatalPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalPegawaiActionPerformed
        ClearPegawai();
        ClosePanel();
        status = tambah;
        PegawaiPanel("");
    }//GEN-LAST:event_btnBatalPegawaiActionPerformed

    private void btnDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDateActionPerformed
        date.showPopup();
    }//GEN-LAST:event_btnDateActionPerformed

    private void UbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UbahActionPerformed
       ClosePanel();
       pnTambahPegawai.setVisible(true);
       ClearPegawai();
       status = update;
       
       lbTitle.setText("Ubah Data Pegawai");
       
        int i  = tbPegawai.getSelectedRow();
        TableModel tbp = tbPegawai.getModel();
        
        tfIdPegawai.setText(tbp.getValueAt(i, 0).toString());
        tfNama.setText(tbp.getValueAt(i, 1).toString());
        tfNik.setText(tbp.getValueAt(i, 2).toString());
        tfTempatLahir.setText(tbp.getValueAt(i, 3).toString());
        tfTglLahir.setText(tbp.getValueAt(i, 4).toString());
        tfAlamat.setText(tbp.getValueAt(i, 5).toString());
        tfNomor.setText(tbp.getValueAt(i, 6).toString());
        cbJabatan.setSelectedItem(tbp.getValueAt(i, 7).toString());
        cbStatus.setSelectedItem(tbp.getValueAt(i, 8).toString());
    }//GEN-LAST:event_UbahActionPerformed

    private void HapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HapusActionPerformed
        int i = tbPegawai.getSelectedRow();
        int pilihan = JOptionPane.showConfirmDialog(
                null,
                "Apakah anda yakin ingin menghapus data?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );
        if(pilihan==0){
            if(i>=0){
                try{
                    TableModel model = tbPegawai.getModel();
                    Koneksi koneksi = new Koneksi();
                    Connection con = koneksi.getConnection();
                    String executeQuery = "DELETE FROM pegawai WHERE pegawai.id =?";
                    PreparedStatement ps = con.prepareStatement(executeQuery);
                    ps.setString(1, model.getValueAt(i, 0).toString());
                    ps.executeUpdate();
                } catch (SQLException ex){
                    System.err.println(ex);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus");
            }
        }
        resetTable("");
    }//GEN-LAST:event_HapusActionPerformed

    private void cbNikAbsenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbNikAbsenItemStateChanged
        String pilihan = (String) cbNikAbsen.getSelectedItem();
        String sql = "SELECT * FROM pegawai WHERE nik = ?";
        Koneksi koneksi = new Koneksi();
        
        try{
            Connection con = koneksi.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            ps = con.prepareStatement(sql);
            ps.setString(1, pilihan);
            rs = ps.executeQuery();
            
            if(rs.next()){
                String isinama = rs.getString("nama");
                String id2 = rs.getString("id");
                tfNamaAbsen.setText(isinama);
                tfId2Absen.setText(id2);
            }
        }catch(SQLException ex){
            System.err.println("Error : "+ ex );
        }
    }//GEN-LAST:event_cbNikAbsenItemStateChanged

    private void btnSimpanAbsenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanAbsenActionPerformed
           String tglnow = date1.format(now);
           String idabsensi = tfId2Absen.getText();
           String idUbah = tfIdAbsen.getText();
           String ket = cbKeterangan.getSelectedItem().toString();
         
           Koneksi koneksi = new Koneksi();
            Connection con = koneksi.getConnection();
            PreparedStatement ps;
            
            try {
                if(status==tambah) {
                    if(cbNikAbsen.getSelectedIndex() == 0||
                       cbKeterangan.getSelectedIndex() == 0)
                    {
                        JOptionPane.showMessageDialog(null, "Pilih NIK dan Keterangan Absen");
                    }else{
                    st = con.createStatement();
                    st.execute("INSERT INTO absensi (id, tgl_absen, keterangan) VALUES('"+idabsensi+"','"+tglnow+"','"+ket+"')");
                    JOptionPane.showMessageDialog(null, "Data Absensi Berhasil Ditambahkan");
                    ClearAbsen();
                    AbsenPanel();
                    }
         }else{
                   String qry = "UPDATE absensi SET id = ?, keterangan = ? WHERE id_absen = ?";
                   
                   ps = con.prepareStatement(qry);
                   ps.setString(1, idabsensi);
                   ps.setString(2, ket);
                   ps.setString(3,idUbah );
                   ps.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Data Absensi Berhasil Diupdate");
                    ClearAbsen();
                    status = tambah;
                    AbsenPanel();
                }
            }catch (SQLException ex) {
                System.err.println("Error : "+ex);
            }
    }//GEN-LAST:event_btnSimpanAbsenActionPerformed

    private void Hapus2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hapus2ActionPerformed
        int i = tbAbsen.getSelectedRow();
        int pilihan = JOptionPane.showConfirmDialog(
                null,
                "Apakah anda yakin ingin menghapus data?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );
        if(pilihan==0){
            if(i>=0){
                try{
                    TableModel model = tbAbsen.getModel();
                    Koneksi koneksi = new Koneksi();
                    Connection con = koneksi.getConnection();
                    String sqlhapus = "DELETE FROM absensi WHERE id_absen = ?";
                    PreparedStatement ps = con.prepareStatement(sqlhapus);
                    ps.setString(1, model.getValueAt(i, 0).toString());
                    ps.executeUpdate();
                } catch (SQLException ex){
                    System.err.println(ex);
                }
            }else{
      //          JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus");
            }
        }
        resetTable("");        
    }//GEN-LAST:event_Hapus2ActionPerformed

    private void tbAbsenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAbsenMouseReleased
         if(evt.getButton() == MouseEvent.BUTTON3){
            if(evt.isPopupTrigger() && tbAbsen.getSelectedRowCount()!=0){
                pmTableAbsen.show(evt.getComponent(),evt.getX(),evt.getY());
            }
        }
    }//GEN-LAST:event_tbAbsenMouseReleased

    private void Ubah2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ubah2ActionPerformed
        ClearAbsen();
        status = update;
        int i  = tbAbsen.getSelectedRow();
        TableModel tbp = tbAbsen.getModel();
        
        tfIdAbsen.setText(tbp.getValueAt(i, 0).toString());
        tfId2Absen.setText(tbp.getValueAt(i, 1).toString());
        tfTglAbsen.setText(tbp.getValueAt(i, 2).toString());
        cbNikAbsen.setSelectedItem(tbp.getValueAt(i, 3).toString());
        tfNamaAbsen.setText(tbp.getValueAt(i, 4).toString());
       cbKeterangan.setSelectedItem(tbp.getValueAt(i, 5).toString()); 
    }//GEN-LAST:event_Ubah2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        status = update;
        String idUser = tfIdUser.getText();
        String namaUser = tfNamaUser.getText();
        String userUser = tfUsernameUser.getText();
        String passUser = tfPasswordUser.getText();
        Koneksi koneksi = new Koneksi();
        Connection con = koneksi.getConnection();
        PreparedStatement ps;
        String qry = "UPDATE user SET nama = ?, username = ?, password = ? WHERE id = ?";
                   
                  try{
                   ps = con.prepareStatement(qry);
                   ps.setString(1, namaUser);
                   ps.setString(2, userUser);
                   ps.setString(3,passUser );
                   ps.setString(4,idUser);
                   ps.executeUpdate();
                  }catch(SQLException ex){
                      System.err.println("Error : "+ex);
                  }
                    
                   JOptionPane.showMessageDialog(null, "Data User Berhasil Diupdate");
                   nama.setText(userUser);
                   nama2.setText(userUser);
                   tfUserName.setText(userUser);
                   
         status = tambah;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnSimpanGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanGajiActionPerformed
           String tglnow = date1.format(now);
           String idgaji = tfIdGaji.getText();
           String idgaji2 = tfIdNamaGaji.getText();
           String nama = cbNamaGaji.getSelectedItem().toString();
           String nik = tfNikGaji.getText();
           String jabatan = tfJabatanGaji.getText();
           String gajip = tfGajiPokok.getText();
           String pot = tfPotongan.getText();
           String tunj = tfKeluarga.getText();
           String gajib = tfGajiBersih.getText();
         
           Koneksi koneksi = new Koneksi();
            Connection con = koneksi.getConnection();
            PreparedStatement ps;
            
            try {
                if(status==tambah) {
                    if( cbNamaGaji.getSelectedIndex() == 0 ||
                       nik.isEmpty() ||
                            jabatan.isEmpty()) {
                      JOptionPane.showMessageDialog(null, "Masukkan Data Gaji Dulu");
                    }else{
                    st = con.createStatement();
                    st.execute("INSERT INTO gaji (id, tanggal, gajipokok, tunjangan, potongan, gajibersih) VALUES('"+idgaji2+"','"+tglnow+"','"+gajip+"','"+tunj+"','"+pot+"','"+gajib+"')");
                    JOptionPane.showMessageDialog(null, "Data Gaji Berhasil Ditambahkan");
                    ClearGaji();
                    GajiPanel("");
                    }
         }else{
                   String qry = "UPDATE gaji SET id = ?, tanggal = ?, gajipokok = ?,  tunjangan = ?, potongan = ?, gajibersih = ? WHERE id_gaji = ?";
                   
                   ps = con.prepareStatement(qry);
                   ps.setString(1, idgaji2);
                   ps.setString(2, tglnow);
                   ps.setString(3,gajip);
                    ps.setString(4,tunj);
                    ps.setString(5,pot);
                     ps.setString(6,gajib);
                      ps.setString(7,idgaji);
                   ps.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Data Gaji Berhasil Diupdate");
                    ClearGaji();
                    status = tambah;
                    GajiPanel("");
                }
            }catch (SQLException ex) {
                System.err.println("Error : "+ex);
            }
    }//GEN-LAST:event_btnSimpanGajiActionPerformed

    private void btnBatalGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalGajiActionPerformed
       ClearGaji();
       ClosePanel();
        GajiPanel("");
    }//GEN-LAST:event_btnBatalGajiActionPerformed

    private void btnTambahGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahGajiActionPerformed
       ClosePanel();
       pnTambahGaji.setVisible(true);
       
       cbNamaGaji.removeAllItems();
       cbNamaGaji.addItem("----");
       try {  
       Koneksi koneksi = new Koneksi();
        Connection con = koneksi.getConnection();
        st = con.createStatement();
        ResultSet rs= st.executeQuery("select * from pegawai");;
            while (rs.next()) {  
             cbNamaGaji.addItem(rs.getString("nama"));  
             }
            con.close();
        } catch (Exception ex) {  
        System.err.println("Error : "+ex);
        }
       
      
       tfTanggalGaji.setText(date1.format(now));
    }//GEN-LAST:event_btnTambahGajiActionPerformed

    private void tfPotonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfPotonganActionPerformed
         String pot1 = tfPotongan.getText();
        if(pot1.isEmpty()){
            JOptionPane.showMessageDialog(null, "Masukkan Nilai Potongan Gaji");
        }else{
              int pokok = Integer.parseInt(tfGajiPokok.getText());
              int pot = Integer.parseInt(tfPotongan.getText());
              int tun = Integer.parseInt(tfKeluarga.getText());
             int hasil = pokok + tun - pot;
             String bersih = Integer.toString(hasil);
             tfGajiBersih.setText(bersih);
        }
    }//GEN-LAST:event_tfPotonganActionPerformed

    private void Ubah3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Ubah3ActionPerformed
       ClosePanel();
       pnTambahGaji.setVisible(true);
       ClearGaji();
       status = update;
       
       lbTitle1.setText("Ubah Data Gaji");
       
        int i  = tbGaji.getSelectedRow();
        TableModel tbp = tbGaji.getModel();
        
        tfIdGaji.setText(tbp.getValueAt(i, 0).toString());
        tfIdNamaGaji.setText(tbp.getValueAt(i, 1).toString());
        tfTanggalGaji.setText(tbp.getValueAt(i, 2).toString());
        cbNamaGaji.setSelectedItem(tbp.getValueAt(i, 3).toString());
        tfNikGaji.setText(tbp.getValueAt(i, 4).toString());
        tfJabatanGaji.setText(tbp.getValueAt(i, 5).toString());
        tfGajiPokok.setText(tbp.getValueAt(i, 6).toString());
        tfKeluarga.setText(tbp.getValueAt(i, 7).toString());
         tfPotongan.setText(tbp.getValueAt(i, 8).toString());
         tfGajiBersih.setText(tbp.getValueAt(i, 9).toString());
    }//GEN-LAST:event_Ubah3ActionPerformed

    private void Hapus3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Hapus3ActionPerformed
         int i = tbGaji.getSelectedRow();
        int pilihan = JOptionPane.showConfirmDialog(
                null,
                "Apakah anda yakin ingin menghapus data?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );
        if(pilihan==0){
            if(i>=0){
                try{
                    TableModel model = tbGaji.getModel();
                    Koneksi koneksi = new Koneksi();
                    Connection con = koneksi.getConnection();
                    String sqlhapus = "DELETE FROM gaji WHERE id_gaji = ?";
                    PreparedStatement ps = con.prepareStatement(sqlhapus);
                    ps.setString(1, model.getValueAt(i, 0).toString());
                    ps.executeUpdate();
                } catch (SQLException ex){
                    System.err.println(ex);
                }
            }else{
      //          JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus");
            }
        }
        resetTable("");    
    }//GEN-LAST:event_Hapus3ActionPerformed

    private void btnHomeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHomeMousePressed
        setColor(btnHome);
       resetColor(btnPegawai);
       resetColor(btnGaji);
       resetColor(btnAbsen);
       resetColor(btnUser);
       resetColor(btnLogout1);
       
       MainPanel();
       ClearGaji();
       ClearPegawai();
        eCari.setText("");
       eCariGaji.setText("");
       tfNamaAbsen.setText("");
        cbNikAbsen.removeAllItems();
       cbNikAbsen.addItem("----");
        cbKeterangan.setSelectedIndex(0);
    }//GEN-LAST:event_btnHomeMousePressed

    private void btnPegawaiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPegawaiMousePressed
        setColor(btnPegawai);
       resetColor(btnHome);
       resetColor(btnGaji);
       resetColor(btnAbsen);
       resetColor(btnUser);
       resetColor(btnLogout1);
       
       PegawaiPanel("");
       ClearGaji();
       ClearPegawai();
       eCariGaji.setText("");
        cbNikAbsen.removeAllItems();
        tfNamaAbsen.setText("");
       cbNikAbsen.addItem("----");
        cbKeterangan.setSelectedIndex(0);
    }//GEN-LAST:event_btnPegawaiMousePressed

    private void btnGajiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGajiMousePressed
       setColor(btnGaji);
       resetColor(btnPegawai);
       resetColor(btnHome);
       resetColor(btnAbsen);
       resetColor(btnUser);
       resetColor(btnLogout1);
       
       GajiPanel("");
       ClearGaji();
       ClearPegawai();
       eCari.setText("");
        tfNamaAbsen.setText("");
        cbNikAbsen.removeAllItems();
       cbNikAbsen.addItem("----");
        cbKeterangan.setSelectedIndex(0);
    }//GEN-LAST:event_btnGajiMousePressed

    private void btnAbsenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAbsenMousePressed
        setColor(btnAbsen);
        resetColor(btnGaji);
       resetColor(btnHome);
       resetColor(btnPegawai);
       resetColor(btnUser);
       resetColor(btnLogout1);
       
       AbsenPanel();
       ClearGaji();
       ClearPegawai();
        eCari.setText("");
       eCariGaji.setText("");
       cbNikAbsen.removeAllItems();
       cbNikAbsen.addItem("----");
       try {  
       Koneksi koneksi = new Koneksi();
        Connection con = koneksi.getConnection();
        st = con.createStatement();
        ResultSet rs= st.executeQuery("select * from pegawai");;
            while (rs.next()) {  
             cbNikAbsen.addItem(rs.getString("nik"));  
             }
            con.close();
        } catch (Exception ex) {  
        System.err.println("Error : "+ex);
        }
       
      
       tfTglAbsen.setText(date1.format(now));
    }//GEN-LAST:event_btnAbsenMousePressed

    private void btnUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserMousePressed
        setColor(btnUser);
       resetColor(btnAbsen);
       resetColor(btnGaji);
       resetColor(btnHome);
       resetColor(btnPegawai);
       resetColor(btnLogout1);
       
        eCari.setText("");
        ClearGaji();
        ClearPegawai();
       eCariGaji.setText("");
        tfNamaAbsen.setText("");
       cbNikAbsen.removeAllItems();
       cbNikAbsen.addItem("----");
       cbKeterangan.setSelectedIndex(0);
       
       EditUserPanel();
        String user1 = tfUserName.getText();
        String sql = "SELECT * FROM user WHERE username = ?";
        Koneksi koneksi = new Koneksi();
        
        try{
            Connection con = koneksi.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            ps = con.prepareStatement(sql);
            ps.setString(1, user1);
            rs = ps.executeQuery();
            
            if(rs.next()){
                String iduser = rs.getString("id");
                String namauser = rs.getString("nama");
                String usernameuser = rs.getString("username");
                 String passuser = rs.getString("password");
                tfIdUser.setText(iduser);
                tfNamaUser.setText(namauser);
                tfUsernameUser.setText(usernameuser);
                tfPasswordUser.setText(passuser);
            }
        }catch(SQLException ex){
            System.err.println("Error : "+ ex );
        }
    }//GEN-LAST:event_btnUserMousePressed

    private void btnLogout1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLogout1MousePressed
        int pilihan = JOptionPane.showConfirmDialog(
                null,
                "Apakah anda yakin ingin logout?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION
        );
        if(pilihan==0){
            dispose();
         new LoginFrame().setVisible(true);
        }
    }//GEN-LAST:event_btnLogout1MousePressed

    private void btnTambahPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPegawaiActionPerformed
        ClosePanel();
       pnTambahPegawai.setVisible(true);
    }//GEN-LAST:event_btnTambahPegawaiActionPerformed

    private void btnCetakPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakPegawaiActionPerformed
        Koneksi koneksi = new Koneksi();
         Connection conn= koneksi.getConnection(); 
        
        try {
            HashMap<String, Object> parameter = new HashMap();            
            JasperPrint jp = JasperFillManager.fillReport("src\\report\\DataPegawai.jasper", parameter, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        } catch(Exception e) {
            System.err.println("error report :"+e);
        }
    }//GEN-LAST:event_btnCetakPegawaiActionPerformed

    private void btnCetakGajiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakGajiActionPerformed
       Koneksi koneksi = new Koneksi();
         Connection conn= koneksi.getConnection(); 
        
        try {
            HashMap<String, Object> parameter = new HashMap();            
            JasperPrint jp = JasperFillManager.fillReport("src\\report\\DataGaji.jasper", parameter, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        } catch(Exception e) {
            System.err.println("error report :"+e);
        }
    }//GEN-LAST:event_btnCetakGajiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Koneksi koneksi = new Koneksi();
         Connection conn= koneksi.getConnection(); 
        
        try {
            HashMap<String, Object> parameter = new HashMap();            
            JasperPrint jp = JasperFillManager.fillReport("src\\report\\Absen.jasper", parameter, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
        viewer.setVisible(true);
        } catch(Exception e) {
            System.err.println("error report :"+e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tfPotonganKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPotonganKeyTyped
         char c = evt.getKeyChar();
        if( ! ((Character.isDigit(c) ||
            (c == KeyEvent.VK_BACK_SPACE) ||
            (c == KeyEvent.VK_DELETE)) ||
            (c == KeyEvent.VK_ENTER)
        )) {
            getToolkit().beep();
            JOptionPane.showMessageDialog(null,"Masukkan hanya angka 0-9");
            evt.consume();

        }
    }//GEN-LAST:event_tfPotonganKeyTyped

    private void tfGajiBersihKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfGajiBersihKeyTyped
        char c = evt.getKeyChar();
        if( ! ((Character.isDigit(c) ||
            (c == KeyEvent.VK_BACK_SPACE) ||
            (c == KeyEvent.VK_DELETE))
        )) {
            getToolkit().beep();
            JOptionPane.showMessageDialog(null,"Masukkan hanya angka 0-9");
            evt.consume();

        }
    }//GEN-LAST:event_tfGajiBersihKeyTyped

    private void cbNamaGajiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbNamaGajiItemStateChanged
        String pilihan = (String) cbNamaGaji.getSelectedItem();
        String pilihan2 = tfJabatanGaji.getText();
        String sql = "SELECT * FROM pegawai WHERE nama = ?";
        Koneksi koneksi = new Koneksi();
        
        try{
            Connection con = koneksi.getConnection();
            PreparedStatement ps;
            ResultSet rs;
            ps = con.prepareStatement(sql);
            ps.setString(1, pilihan);
            rs = ps.executeQuery();
            
            if(rs.next()){
                String id = rs.getString("id");
                String nik = rs.getString("nik");
                String jabatan = rs.getString("jabatan");
                tfIdNamaGaji.setText(id);
                tfNikGaji.setText(nik);
                tfJabatanGaji.setText(jabatan);
            }
        }catch(SQLException ex){
            System.err.println("Error : "+ ex );
        }
        
        switch(pilihan2){
            case "Leader":
                tfGajiPokok.setText("5000000");
                tfPotongan.setText("0");
            break;
            case "Manager":
                tfGajiPokok.setText("4000000");
                 tfPotongan.setText("0");
            break;
            case "Supervisor":
                tfGajiPokok.setText("2500000");
                 tfPotongan.setText("0");
            break;
            case "Operator":
                tfGajiPokok.setText("1500000");
                 tfPotongan.setText("0");
            break;
        }
    }//GEN-LAST:event_cbNamaGajiItemStateChanged

    private void cbKeluargaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbKeluargaItemStateChanged
      String keluarga = (String) cbKeluarga.getSelectedItem();

        switch(keluarga){
            case "Belum Menikah":
            tfKeluarga.setText("50000");
            break;
            case "Sudah Menikah, Belum Punya Anak":
            tfKeluarga.setText("100000");
            break;
            case "Sudah Menikah Punya Anak":
            tfKeluarga.setText("150000");
            break;
        }
    }//GEN-LAST:event_cbKeluargaItemStateChanged

    void setColor (JPanel panel){
        panel.setBackground(new Color(217,217,217));
    }
      void resetColor (JPanel panel){
        panel.setBackground(new Color(153,153,153));
    }
      

      
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       try {
           UIManager.setLookAndFeel(new FlatLightLaf());
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame("").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Hapus;
    private javax.swing.JMenuItem Hapus2;
    private javax.swing.JMenuItem Hapus3;
    private javax.swing.JLabel Jabatan;
    private javax.swing.JLabel NIK;
    private javax.swing.JLabel NIK1;
    private javax.swing.JLabel Potongan;
    private javax.swing.JLabel Potongan1;
    private javax.swing.JMenuItem Ubah;
    private javax.swing.JMenuItem Ubah2;
    private javax.swing.JMenuItem Ubah3;
    private javax.swing.JPanel bg;
    private custom.panel.PanelRound btnAbsen;
    private javax.swing.JButton btnBatalGaji;
    private javax.swing.JButton btnBatalPegawai;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCariGaji;
    private javax.swing.JButton btnCetakGaji;
    private javax.swing.JButton btnCetakPegawai;
    private javax.swing.JButton btnDate;
    private custom.panel.PanelRound btnGaji;
    private custom.panel.PanelRound btnHome;
    private custom.panel.PanelRound btnLogout1;
    private custom.panel.PanelRound btnPegawai;
    private javax.swing.JButton btnSimpanAbsen;
    private javax.swing.JButton btnSimpanGaji;
    private javax.swing.JButton btnSimpanPegawai;
    private javax.swing.JButton btnTambahGaji;
    private javax.swing.JButton btnTambahPegawai;
    private custom.panel.PanelRound btnUser;
    private javax.swing.JComboBox<String> cbJabatan;
    private javax.swing.JComboBox<String> cbKeluarga;
    private javax.swing.JComboBox<String> cbKeterangan;
    private javax.swing.JComboBox<String> cbNamaGaji;
    private javax.swing.JComboBox<String> cbNikAbsen;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JLabel close;
    private com.raven.datechooser.DateChooser date;
    private javax.swing.JTextField eCari;
    private javax.swing.JTextField eCariGaji;
    private javax.swing.JLabel hariText;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbGaji;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbTitle1;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel minimize;
    private javax.swing.JLabel nama;
    private javax.swing.JLabel nama1;
    private javax.swing.JLabel nama2;
    private javax.swing.JPopupMenu pmTable;
    private javax.swing.JPopupMenu pmTableAbsen;
    private javax.swing.JPopupMenu pmTableGaji;
    private javax.swing.JPanel pnAbsen;
    private javax.swing.JPanel pnBar;
    private javax.swing.JPanel pnEditUser;
    private javax.swing.JPanel pnGaji;
    private javax.swing.JPanel pnMain;
    private javax.swing.JPanel pnPegawai;
    private javax.swing.JPanel pnProfile;
    private javax.swing.JPanel pnSide;
    private javax.swing.JPanel pnTambahGaji;
    private javax.swing.JPanel pnTambahPegawai;
    private javax.swing.JPanel pnWidget;
    private javax.swing.JLabel profil;
    private javax.swing.JTable tbAbsen;
    private javax.swing.JTable tbGaji;
    private javax.swing.JTable tbPegawai;
    private javax.swing.JTextField tfAlamat;
    private javax.swing.JTextField tfGajiBersih;
    private javax.swing.JTextField tfGajiPokok;
    private javax.swing.JTextField tfId2Absen;
    private javax.swing.JTextField tfIdAbsen;
    private javax.swing.JTextField tfIdGaji;
    private javax.swing.JTextField tfIdNamaGaji;
    private javax.swing.JTextField tfIdPegawai;
    private javax.swing.JTextField tfIdUser;
    private javax.swing.JTextField tfJabatanGaji;
    private javax.swing.JTextField tfKeluarga;
    private javax.swing.JTextField tfNama;
    private javax.swing.JTextField tfNamaAbsen;
    private javax.swing.JTextField tfNamaUser;
    private javax.swing.JTextField tfNik;
    private javax.swing.JTextField tfNikGaji;
    private javax.swing.JTextField tfNomor;
    private javax.swing.JTextField tfPasswordUser;
    private javax.swing.JTextField tfPotongan;
    private javax.swing.JTextField tfTanggalGaji;
    private javax.swing.JTextField tfTempatLahir;
    private javax.swing.JTextField tfTglAbsen;
    private javax.swing.JTextField tfTglLahir;
    private javax.swing.JTextField tfUserName;
    private javax.swing.JTextField tfUsernameUser;
    private javax.swing.JLabel tglText;
    private javax.swing.JLabel waktuText;
    // End of variables declaration//GEN-END:variables
}
