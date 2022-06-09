
package tablolar;

import java.sql.*;
import java.util.ArrayList;


public class Temsilci extends Kullanici {
    
    private int maas;
    
    Statement st = null;
    PreparedStatement ps =null;
    ResultSet rs = null;            //DataBase işlemleri
    Connection con =conn.connDB();

    public Temsilci() {
        this.maas = 1;
    }

    public Temsilci(String tc, String ad, String soyad, String sifre) {
        super(tc, ad, soyad, sifre); 
        this.maas = 1;
    }
    
    
    public String getNowDate(){
        
        String sorgu = "SELECT * FROM tarih";
        String tarih = null ;
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                tarih = rs.getString("tarih");
                break;
                
            }
            
            return tarih;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getNowDate hata");
            return null;
        }
    }
    
    
    public ArrayList <Musteri> getOwnMusteriList(){
        
        ArrayList <Musteri> musteri_list = new ArrayList<Musteri>();
        Musteri musteri = null;
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery("SELECT *"
                                + "FROM musteri");
            
            while(rs.next()){
                
                if(rs.getString("t_tc_no").equals(getTc())){
                    
                    
                    musteri = new Musteri();
                    
                    musteri.setTc(rs.getString("tc_no"));
                    musteri.setAd(rs.getString("ad"));
                    musteri.setSoyad(rs.getString("soyad"));    //musteri objesine query den gelen bilgileri aldık
                    musteri.setSifre(rs.getString("sifre"));
                    musteri.setT_tc_no(rs.getString("t_tc_no"));
                
                    musteri_list.add(musteri); //listeye ekledik
                }
            }
            
            return musteri_list;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getOwnMusteriList de hata var");
            return null;
        }
           
    }
    
    public ArrayList <Madres> getOwnAdresiList(){
        
        ArrayList <Madres> musteri_adres_list = new ArrayList<Madres>();
        Madres adres = null;
        String sorgu ="SELECT ma.id , ma.adres , ma.tc_no "
                + "FROM madres ma , musteri m , temsilci t "
                + "WHERE ma.tc_no = m.tc_no AND m.t_tc_no=t.tc_no AND t.tc_no = '"+getTc()+"'";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
                                
            
            while(rs.next()){
             
                    adres = new Madres();
                    
                     //musteri objesine query den gelen bilgileri aldık
                     
                     adres.setId(rs.getInt("id"));
                     adres.setAdres(rs.getString("adres"));
                     adres.setTc_no(rs.getString("tc_no"));
                    
                
                    musteri_adres_list.add(adres); //listeye ekledik
                
            }
            
            return musteri_adres_list;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getAdresLsit de hata var");
            return null;
        }
           
    }
    
    public ArrayList <Meposta> getOwnEpostaList(){
        
        ArrayList <Meposta> musteri_eposta_list = new ArrayList<Meposta>();
        Meposta eposta = null;
        String sorgu ="SELECT me.id , me.eposta , me.tc_no "
                + "FROM meposta me , musteri m , temsilci t "
                + "WHERE me.tc_no = m.tc_no AND m.t_tc_no=t.tc_no AND t.tc_no = '"+getTc()+"'";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
                                
            
            while(rs.next()){
             
                    eposta = new Meposta();
                    
                     //musteri objesine query den gelen bilgileri aldık
                     
                     eposta.setId(rs.getInt("id"));
                     eposta.setEposta(rs.getString("eposta"));
                     eposta.setTc_no(rs.getString("tc_no"));
                    
                
                    musteri_eposta_list.add(eposta); //listeye ekledik
                
            }
            
            return musteri_eposta_list;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getEpostaList de hata var");
            return null;
        }
           
    }
    
    public ArrayList <Mtelefon> getOwnTelefonList(){
        
        ArrayList <Mtelefon> musteri_telefon_list = new ArrayList<Mtelefon>();
        Mtelefon telefon = null;
        String sorgu ="SELECT mt.id , mt.tel_no , mt.tc_no "
                + "FROM mtelefon mt , musteri m , temsilci t "
                + "WHERE mt.tc_no = m.tc_no AND m.t_tc_no=t.tc_no AND t.tc_no = '"+getTc()+"'";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
                                
            
            while(rs.next()){
             
                    telefon = new Mtelefon();
                    
                     //musteri objesine query den gelen bilgileri aldık
                     
                     telefon.setId(rs.getInt("id"));
                     telefon.setTel_no(rs.getString("tel_no"));
                     telefon.setTc_no(rs.getString("tc_no"));
                    
                
                    musteri_telefon_list.add(telefon); //listeye ekledik
                
            }
            
            return musteri_telefon_list;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getTelefonList de hata var");
            return null;
        }
           
    }
    

     
    public boolean musteriEkle(String tc_no, String ad, String soyad){ //musteri ya eklenir ya eklenmez boolean donucek
      // sifre belirlenmemsinin sebebi burası temsilcinin ekleme yeri default olarak
      //sifre zaten ekleniyor kullanıcı dilerse sifresini kendi belirleyebilicek
      
      String sorgu ="INSERT INTO musteri (tc_no, ad, soyad, t_tc_no)"
                     + " VALUES (?, ?, ?, ?)";
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, tc_no);
             ps.setString(2, ad);              //preparedStatmentlarımızı oluşturduk
              ps.setString(3, soyad);
                ps.setString(4, getTc());
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("musteriEkle hatali");
            return false;
        }
        
     
    }
    
    public boolean musteriSil(String tc_no){ //musteri ya silinir ya silinmez boolean donucek
     
      
      String sorgu ="DELETE FROM musteri WHERE tc_no = ?"; // tc nosu belirtilen musteriyi silmek
                     
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, tc_no);
                         //preparedStatmentlarımızı oluşturduk
             
                
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("musteriSil hatali");
            return false;
        }
        
     
    }
    
    public boolean musteriGuncelle(String tc_no, String ad , String soyad){ //musteri ya guncellenir ya guncellenmez boolean donucek
     
      
      String sorgu ="UPDATE musteri SET ad = ? , soyad = ? WHERE tc_no = ?"; // tc nosu belirtilen musteriyi silmek
                     
          // System.out.println(tc_no);
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setString(1, ad);             //preparedStatmentlarımızı oluşturduk
            ps.setString(2, soyad);
            ps.setString(3, tc_no);
                
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("musteriGuncelle hatali");
            return false;
        }
        
     
    }
    
    public boolean musteriGuncelleAdres(String tc_no, String adres , int id){ //musteri ya guncellenir ya guncellenmez boolean donucek
     
      
      String sorgu ="UPDATE madres SET adres = ? WHERE id = ?"; // tc nosu belirtilen musteriyi silmek
                     
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, adres);
            ps.setInt(2, id);             //preparedStatmentlarımızı oluşturduk
             
                
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("musteriGuncelleAdres hatali");
            return false;
        }
        
     
    }
    
    public boolean musteriGuncelleTelefon(String tc_no, String tel_no , int id ){ //musteri ya guncellenir ya guncellenmez boolean donucek
     
      
      String sorgu ="UPDATE mtelefon SET tel_no = ? WHERE id = ?"; 
                     
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, tel_no);
            ps.setInt(2, id);             //preparedStatmentlarımızı oluşturduk
            
                
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("musteriGuncelleTelefon hatali");
            return false;
        }
        
     
    }
    
    public boolean musteriGuncelleEposta(String tc_no, String eposta , int id){ //musteri ya guncellenir ya guncellenmez boolean donucek
     
      
      String sorgu ="UPDATE meposta SET eposta = ? WHERE id = ?";
                     
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, eposta);
            ps.setInt(2, id);             //preparedStatmentlarımızı oluşturduk
             
                
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("musteriGuncelleEposta hatali");
            return false;
        }
        
     
    }
    
    public ArrayList<Islem> getIslemList(){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        
        
        String sorgu ="SELECT i.ino , h1.had AS kaynak , h2.had AS hedef ,it.iad AS islem ,i.tutar, h1.bakiye AS kbakiye ,h2.bakiye AS hbakiye ,i.tarih "
                + "FROM islem i , hesap h1 ,hesap h2 ,itipi it ,musteri m "
                + "WHERE i.kno=h1.hno AND "
                + "i.hno=h2.hno AND "
                + "i.iid=it.iid AND "
                + "h1.mtc = m.tc_no AND "
                + "m.t_tc_no = '"+getTc()+"'";
                 //parametre bos gelirse
        
         // System.out.println(sorgu);
        
            
            try {
                
                st = con.createStatement();
                rs = st.executeQuery(sorgu);
                
                while(rs.next()){
                    
                    islem = new Islem();
                    
                    islem.setIno(rs.getInt("ino"));
                    islem.setKaynak(rs.getString("kaynak"));
                    islem.setHedef(rs.getString("hedef"));
                    islem.setIslem(rs.getString("islem"));
                    islem.setTutar(rs.getInt("tutar"));
                    islem.setKbakiye(rs.getDouble("kbakiye"));
                    islem.setHbakiye(rs.getDouble("hbakiye"));
                    islem.setTarih(rs.getString("tarih"));
                    
                    
                    islem_list.add(islem);
                }
                
                return islem_list;
                
            } catch (Exception e) {
                
                e.printStackTrace();
                System.out.println("getIslemList hatali");
                return null;
            }
                
    }
    
    
    public ArrayList<Islem> getIslemMiktarList(String miktar){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        
        
        String sorgu2 ="SELECT i.ino , h1.had AS kaynak , h2.had AS hedef ,it.iad AS islem ,i.tutar, h1.bakiye AS kbakiye ,h2.bakiye AS hbakiye ,i.tarih "
                + "FROM islem i , hesap h1 ,hesap h2 ,itipi it , musteri m "
                + "WHERE i.kno=h1.hno AND "
                + "i.hno=h2.hno AND "
                + "i.iid=it.iid AND "
                + "h1.mtc = m.tc_no AND "
                + "m.t_tc_no = '"+getTc()+"' "
                + "ORDER BY i.ino DESC "
                + "LIMIT "+miktar;
        
        //String sorgu2 ="SELECT * FROM islem ORDER BY ino DESC LIMIT "+miktar;
        
        try {
                
                st = con.createStatement();
                         
                rs = st.executeQuery(sorgu2);
                
                while(rs.next()){
                    
                     islem = new Islem();
                    
                    islem.setIno(rs.getInt("ino"));
                    islem.setKaynak(rs.getString("kaynak"));
                    islem.setHedef(rs.getString("hedef"));
                    islem.setIslem(rs.getString("islem"));
                    islem.setTutar(rs.getInt("tutar"));
                    islem.setKbakiye(rs.getDouble("kbakiye"));
                    islem.setHbakiye(rs.getDouble("hbakiye"));
                    islem.setTarih(rs.getString("tarih"));
                    
                    islem_list.add(islem);
                    
                }
                
                return islem_list;
                
            } catch (Exception e) {
                
                e.printStackTrace();
                System.out.println("getIslemMiktarList hata verdi");
                return null;
            }  
        
    }
    
    
    public ArrayList<Islem> getIslemList2(){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        
        
        String sorgu ="SELECT i.ino AS ino , h.had AS kaynak , it.iad AS islem , i.tutar AS tutar , h.bakiye AS bakiye , i.tarih AS tarih "
                + "FROM islem i , hesap h , itipi it , musteri m "
                + "WHERE i.kno = h.hno AND i.iid = it.iid AND h.mtc = m.tc_no AND m.t_tc_no = '"+getTc()+"' AND (i.iid = 2 OR i.iid = 7)";
                
        
         //System.out.println(sorgu);
        
            
            try {
                
                st = con.createStatement();
                rs = st.executeQuery(sorgu);
                
                while(rs.next()){
                    
                    islem = new Islem();
                    
                    islem.setIno(rs.getInt("ino"));
                    islem.setKaynak(rs.getString("kaynak"));
                    //islem.setHedef(rs.getString("hedef"));
                    islem.setIslem(rs.getString("islem"));
                    islem.setTutar(rs.getInt("tutar"));
                    islem.setKbakiye(rs.getDouble("bakiye"));
                    //islem.setHbakiye(rs.getDouble("hbakiye"));
                    islem.setTarih(rs.getString("tarih"));
                    
                    
                    islem_list.add(islem);
                }
                
                return islem_list;
                
            } catch (Exception e) {
                
                e.printStackTrace();
                System.out.println("getIslemList2 hatali");
                return null;
            }
                
    }
    
    
    public ArrayList<Islem> getIslemMiktarList2(String miktar){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        
        
       String sorgu2 ="SELECT i.ino AS ino , h.had AS kaynak , it.iad AS islem , i.tutar AS tutar , h.bakiye AS bakiye , i.tarih AS tarih "
                + "FROM islem i , hesap h , itipi it , musteri m "
                + "WHERE i.kno = h.hno AND i.iid = it.iid AND h.mtc = m.tc_no AND m.t_tc_no = '"+getTc()+"' AND (i.iid = 2 OR i.iid = 7) "
               + "ORDER BY i.ino DESC LIMIT "+miktar;
        
        
        
     
        
        //String sorgu2 ="SELECT * FROM islem ORDER BY ino DESC LIMIT "+miktar;
        
        try {
                
                st = con.createStatement();
                         
                rs = st.executeQuery(sorgu2);
                
                while(rs.next()){
                    
                     islem = new Islem();
                    
                    islem.setIno(rs.getInt("ino"));
                    islem.setKaynak(rs.getString("kaynak"));
                  //  islem.setHedef(rs.getString("hedef"));
                    islem.setIslem(rs.getString("islem"));
                    islem.setTutar(rs.getInt("tutar"));
                    islem.setKbakiye(rs.getDouble("bakiye"));
                   // islem.setHbakiye(rs.getDouble("hbakiye"));
                    islem.setTarih(rs.getString("tarih"));
                    
                    islem_list.add(islem);
                    
                }
                
                return islem_list;
                
            } catch (Exception e) {
                
                e.printStackTrace();
                System.out.println("getIslemMiktarList2 hata verdi");
                return null;
            }  
        
    }
    
    
    
    public ArrayList <Talep> getTalepList(){
        
        ArrayList <Talep> talep_list = new ArrayList<Talep>();
        Talep talep = null;
        
        String sorgu = "SELECT ta.id , m.tc_no , i.iad ,ta.onay "
                + "FROM talep ta , itipi i , musteri m "
                + "WHERE ta.m_tc_no = m.tc_no AND ta.iid = i.iid AND m.t_tc_no = '"+getTc()+"'";
        
        
        try {
            
            st = con.createStatement();
                         
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                talep = new Talep();
                
                talep.setId(rs.getInt("id"));
                talep.setTc_no(rs.getString("tc_no"));
                talep.setIad(rs.getString("iad"));
                talep.setOnay(rs.getInt("onay"));
                
                talep_list.add(talep);
            }
            
            return talep_list;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getTalepList HATALI");
            return null;
        }
        
               
    }
    
    public boolean setTalep(int id,int onay){
        
        String sorgu ="UPDATE talep SET onay = ? WHERE id = ?";
        
        try {
            
             ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setInt(1, onay);
            ps.setInt(2, id);             //preparedStatmentlarımızı oluşturduk
             
                
               
            ps.executeUpdate(); //calistir
            
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("setTalep hata");
            return false;
        }
        
    }
    
    public boolean hAcmaTalepOnay(int id){
        
        // bilgileri almak icin kullandıgımız sorgu
        String sorgu = "SELECT ha.had AS had , ha.pbirimi AS pbirimi , ha.mtc AS mtc ,t.onay "
                       +"FROM hatalep ha , talep t "
                        +"WHERE ha.mtc = t.m_tc_no AND ha.iid = t.iid AND t.onay = 1 AND t.id = "+id;
        
        String had =null;
        int pbirimi = 0;
        String mtc =null;
        
        try {
            
            st = con.createStatement();
                         
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                had = rs.getString("had");
                pbirimi = rs.getInt("pbirimi");
                mtc = rs.getString("mtc");
                
                
                break;
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("hAcmaTalep hata");
            return false;
        }
        
        System.out.println(had);
        System.out.println(pbirimi);
        System.out.println(mtc);
        
        // hesap acmak icin kullandııgımız sorgu
        String sorgu2 ="INSERT INTO hesap (had, pbirimi, mtc)"
                     + " VALUES (?, ?, ?)";
        try {
            
            ps = con.prepareStatement(sorgu2);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, had);
            ps.setInt(2, pbirimi);             //preparedStatmentlarımızı oluşturduk
            ps.setString(3, mtc);
             
                
               
            ps.executeUpdate(); //calistir
            
            return true;
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("hAcmaTalep hata");
            return false;
        }
                       
    }
    
    public boolean hesapAcmaTalepSil(int id){
        
        //tablolardaki silinecek id leri alıyoruz
        String sorgu = "SELECT ha.id AS haid , t.id AS tid  "
                       +"FROM hatalep ha , talep t "
                        +"WHERE ha.mtc = t.m_tc_no AND ha.iid = t.iid AND t.id = "+id;
        
        int haid=-1;
        int tid = -1;
        
        try {
            
            st = con.createStatement();
                         
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                haid = rs.getInt("haid");
                tid = rs.getInt("tid");
                
                
                
                break;
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("hesapAcmaTalepSil hata");
            return false;
        }
        
        
        String sorgu2 ="DELETE FROM talep WHERE id = ?"; // id belirtilen talep silmek
        
        String sorgu3 ="DELETE FROM hatalep WHERE id = ?"; // id belirtilen talep silmek
        
        try {
            
            
            ps = con.prepareStatement(sorgu2);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setInt(1, tid);             //preparedStatmentlarımızı oluşturduk
                  
               
            ps.executeUpdate(); //calistir
            
            
            ps = con.prepareStatement(sorgu3);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setInt(1, haid);             //preparedStatmentlarımızı oluşturduk
                  
               
            ps.executeUpdate(); //calistir
            
            return true;
            
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("hesapAcmaTalepSil hata");
            return false;
        }
           
        
    }
    
    
    
    public boolean krediTalepOnay(int id , String tarih){
        
        // bilgileri almak icin kullandıgımız sorgu
        String sorgu = "SELECT k.hno , k.miktar "
                       +"FROM ktalep k , talep t "
                        +"WHERE k.mtc = t.m_tc_no AND k.iid = t.iid AND t.id = "+id;
        
        int hno =0;
        int miktar = 0;
        int faiz = 0;
        int gfaiz = 0;
        int kalan_gun = 10;
        double aylik =0;
        double afaiz =0;
      //  double agfaiz = 0;
        double kalan_nborc = 0;
        
       
        
        try {
            
            st = con.createStatement();
                         
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                hno = rs.getInt("hno");
                miktar = rs.getInt("miktar");
              
                
                break;
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("krediTalepOnay hata");
            return false;
        }
        
        //hazır miktari almışken musteriye ekelyelim
        
        
        String sorgu_musteriye_ekle = "SELECT * FROM hesap h WHERE h.hno = "+hno;
        double musteri_gucel_hesabi=0;
        
        try {
            
            st = con.createStatement();
               rs = st.executeQuery(sorgu_musteriye_ekle);
               
               while(rs.next()){
                   
                   musteri_gucel_hesabi = rs.getDouble("bakiye");
                   break;
                   
               }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("borcOde hatali");
            return false;
        }
        
        musteri_gucel_hesabi+=miktar;
        
        String sorgu_musteri_hesap_guncelle = "UPDATE hesap SET bakiye = ? WHERE hno = ?";
        
        try {
            
            ps = con.prepareStatement(sorgu_musteri_hesap_guncelle);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setDouble(1,musteri_gucel_hesabi);             //preparedStatmentlarımızı oluşturduk
            ps.setInt(2, hno);
                   
            ps.executeUpdate(); //calistir
     
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("krediTalepOnay hatali");
            return false;
        }
        
        // hazır miktari almışsken bankadan parayı keselim
        
        String sorgu_banka_durumu = "SELECT * FROM durum WHERE id = 1";
        double bankaBakiye =0;
        double bankaGider = 0;
        double bankaGelir = 0;
        double bankaKaar = 0;
        
        try {
            
             st = con.createStatement();
               rs = st.executeQuery(sorgu_banka_durumu);
               
               while(rs.next()){
                   
                   bankaGelir = rs.getDouble("gelir");
                   
                   bankaGider = rs.getDouble("gider");
                   
                   bankaKaar = rs.getDouble("kaar");
                   
                   bankaBakiye = rs.getDouble("bakiye");
                   
                   break;
                   
               }
           
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("krediTalepOnay hatali");
            return false;
            
        }
        
        bankaGider+=miktar;
        bankaBakiye-=miktar;
        bankaKaar = bankaGelir - bankaGider;
        
        String sorgu_banka_hesap_durumu_guncelle = "UPDATE durum SET gider = ? , kaar = ? , bakiye = ?  WHERE id = 1";
        
        try {
            
             ps = con.prepareStatement(sorgu_banka_hesap_durumu_guncelle);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setDouble(1,bankaGider);             //preparedStatmentlarımızı oluşturduk
            ps.setDouble(2, bankaKaar);
            ps.setDouble(3, bankaBakiye);
                   
            ps.executeUpdate(); //calistir
            
            
        } catch (Exception e) {
            
             e.printStackTrace();
            System.out.println("krediTalepOnay hatali");
            return false;
            
        }
        
        
        String sorguFaiz = "SELECT * FROM faiz";
        
        try {
            
            st = con.createStatement();
                         
            rs = st.executeQuery(sorguFaiz);
            
            while(rs.next()){
                
                faiz = rs.getInt("faiz");
                gfaiz = rs.getInt("gfaiz");
                
                System.out.println("faiz = "+faiz);
                System.out.println("gfaiz = "+gfaiz);
              
                
                break;
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("krediTalepOnay");
        }
        
        aylik = (double) miktar / 10;
        
        try {
            
            
            
             afaiz = (double)aylik / faiz;
        
           
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("4 islem hatasi");
        }
        
        kalan_nborc = (double)miktar - ((10-kalan_gun)*aylik);
       
        
        
        
        // kredi acmak icin kullandııgımız sorgu
        String sorgu2 ="INSERT INTO kredi (miktar , hno , aylik , afaiz  , kalan_nborc ,tarih)"
                     + " VALUES (?, ? , ? , ? , ? ,?)";
        try {
            
            ps = con.prepareStatement(sorgu2);  //modüllerden objelerimizi oluşturduk
            
            ps.setInt(1, miktar);
            ps.setInt(2, hno);             //preparedStatmentlarımızı oluşturduk
            ps.setDouble(3, aylik);    
            ps.setDouble(4, afaiz);
            ps.setDouble(5, kalan_nborc);
            ps.setString(6, tarih);
            
           
            
             
                
               
            ps.executeUpdate(); //calistir
            
            return true;
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("krediTalepOnay hata");
            return false;
        }
                       
    }
    
    public boolean krediTalepSil(int id){
        
        //tablolardaki silinecek id leri alıyoruz
        String sorgu = "SELECT k.id AS kid , t.id AS tid  "
                       +"FROM ktalep k , talep t "
                        +"WHERE k.mtc = t.m_tc_no AND k.iid = t.iid AND t.id = "+id;
        
        int kid=-1;
        int tid = -1;
        
        try {
            
            st = con.createStatement();
                         
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                kid = rs.getInt("kid");
                tid = rs.getInt("tid");
                
                
                
                break;
            }
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("krediTalepSil hata");
            return false;
        }
        
        
        String sorgu2 ="DELETE FROM talep WHERE id = ?"; // id belirtilen talep silmek
        
        String sorgu3 ="DELETE FROM ktalep WHERE id = ?"; // id belirtilen talep silmek
        
        try {
            
            
            ps = con.prepareStatement(sorgu2);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setInt(1, tid);             //preparedStatmentlarımızı oluşturduk
                  
               
            ps.executeUpdate(); //calistir
            
            
            ps = con.prepareStatement(sorgu3);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setInt(1, kid);             //preparedStatmentlarımızı oluşturduk
                  
               
            ps.executeUpdate(); //calistir
            
            return true;
            
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("krediTalepSil hata");
            return false;
        }
           
        
    }
    
    
    
    public boolean hesapSilmeTalepOnay(int id){
        
        // bilgileri almak icin kullandıgımız sorgu
        String sorgu = "SELECT hs.hno "
                       +"FROM hstalep hs , talep t "
                        +"WHERE hs.mtc = t.m_tc_no AND hs.iid = t.iid AND t.id = "+id;
        
        int hno =0;
        
       
        
        try {
            
            st = con.createStatement();
                         
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                hno = rs.getInt("hno");
                
              
                
                break;
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("krediTalepOnay hata");
            return false;
        }
        
        
        
        // kredi acmak icin kullandııgımız sorgu
        String sorgu2 ="DELETE FROM hesap WHERE hno = ?";
                     
        try {
            
            ps = con.prepareStatement(sorgu2);  //modüllerden objelerimizi oluşturduk
            
         
            ps.setInt(1, hno);             //preparedStatmentlarımızı oluşturduk
            
             
                
               
            ps.executeUpdate(); //calistir
            
            return true;
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("hesapSilmeTalepOnay hata");
            return false;
        }
                       
    }
    
    
    
    public boolean hesapSilmeTalepSilme2(int id){
        
        //tablolardaki silinecek id leri alıyoruz
        
        int tid = id;
        int hsid = 0;
       
        String sorgu = "SELECT hs.id AS hsid "
                       +"FROM hstalep hs , talep t "
                        +"WHERE hs.mtc = t.m_tc_no AND hs.iid = t.iid AND t.id = "+id;
          
        try {
            
            
           st = con.createStatement();
                         
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                hsid = rs.getInt("hsid");
                
                break;
            }
            
    
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("hesapSilmeTalepSİL hata");
            return false;
        }
        
        
        String sorgu2 ="DELETE FROM talep WHERE id = ?"; // id belirtilen talep silmek
        
        String sorgu3 ="DELETE FROM hstalep WHERE id = ?"; // id belirtilen talep silmek
        
        try {
            
            
            ps = con.prepareStatement(sorgu2);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setInt(1, tid);             //preparedStatmentlarımızı oluşturduk
                  
               
            ps.executeUpdate(); //calistir
            
            
            ps = con.prepareStatement(sorgu3);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setInt(1, hsid);             //preparedStatmentlarımızı oluşturduk
                  
               
            ps.executeUpdate(); //calistir
            
            return true;
            
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("2 hata");
            return false;
        }
           
        
    }
    
    
    
    public boolean hesapSilmeTalepSilme(int id){
        
        //tablolardaki silinecek id leri alıyoruz
        
        int tid = id;
       
        String sorgu2 ="DELETE FROM talep WHERE id = ?"; // id belirtilen talep silmek
          
        try {
            
            
            ps = con.prepareStatement(sorgu2);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setInt(1, tid);             //preparedStatmentlarımızı oluşturduk
                  
               
            ps.executeUpdate(); //calistir
            
            
            
            
            return true;
            
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("hesapSilmeTalepSİL hata");
            return false;
        }
           
        
    }
    
    
    public ArrayList<Kredi> getOwnKrediList(){
        
        ArrayList <Kredi> kredi_list = new ArrayList<Kredi>();
        Kredi kredi = null;
        
        try {
            
            String sorgu ="SELECT k.kid ,k.miktar ,k.dfaiz , k.hno , k.aylik , k.afaiz , k.agfaiz ,k.kalan_nborc , k.odurum , k.kalan_gun , k.tarih ,m.tc_no AS mtc "
                    + "FROM kredi k ,hesap h , musteri m "
                    + "WHERE k.hno = h.hno AND h.mtc = m.tc_no AND m.t_tc_no = '"+getTc()+"'";
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                kredi = new Kredi();
                
                kredi.setKid(rs.getInt("kid"));
                kredi.setMiktar(rs.getInt("miktar"));
                kredi.setDfaiz(rs.getInt("dfaiz"));
                kredi.setHno(rs.getInt("hno"));
                kredi.setAylik(rs.getDouble("aylik"));
                kredi.setAfaiz(rs.getDouble("afaiz"));
                kredi.setAgfaiz(rs.getDouble("agfaiz"));
                kredi.setKalan_nborc(rs.getDouble("kalan_nborc"));
                kredi.setOdurum(rs.getInt("odurum"));
                kredi.setKalan_gun(rs.getInt("kalan_gun"));
                kredi.setTarih(rs.getString("tarih"));
                kredi.setMtc(rs.getString("mtc"));
                
                kredi_list.add(kredi);
            }
            
            return kredi_list;
           
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getOwnKrediList hata");
            return null;
        }
             
    }
    
    
    
    
    

    public int getMaas() {
        return maas;
    }

    public void setMaas(int maas) {
        this.maas = maas;
    }
    
    
    
}
