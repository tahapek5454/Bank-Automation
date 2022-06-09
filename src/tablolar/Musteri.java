
package tablolar;

import java.sql.*;
import java.util.ArrayList;


public class Musteri extends Kullanici{
    
    private String t_tc_no;
    
    Statement st = null;
    PreparedStatement ps =null;
    ResultSet rs = null;            //DataBase işlemleri
    Connection con =conn.connDB();

    public Musteri() {
    }

    public Musteri(String tc, String ad, String soyad, String sifre, String t_tc_no) {
        super(tc, ad, soyad, sifre);
        this.t_tc_no = t_tc_no;
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
    
    
    public ArrayList<Hesap> getHesapList(){
        
        ArrayList <Hesap> hesap_list = new ArrayList<Hesap>();
        Hesap hesap = null;
        
        try {
            
            String sorgu = "SELECT h.hno , h.had ,h.bakiye ,p.bad AS pbirimiad , h.mtc "
                        + "FROM hesap h , pbirimi p WHERE h.pbirimi = p.id AND h.mtc = '"+getTc()+"'";
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                hesap = new Hesap();
                
                hesap.setHno(rs.getInt("hno"));
                hesap.setHad(rs.getString("had"));
                hesap.setBakiye(rs.getDouble("bakiye"));
                hesap.setPbirimiad(rs.getString("pbirimiad"));
                hesap.setMtc(rs.getString("mtc"));
                
                hesap_list.add(hesap);
            }
            
            return hesap_list;
           
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getHesapList hata");
            return null;
        }
             
    }
    
    public ArrayList<Hesap> getTumHesapList(){
        
        ArrayList <Hesap> hesap_list = new ArrayList<Hesap>();
        Hesap hesap = null;
        
        try {
            
            String sorgu = "SELECT h.hno , h.had ,h.bakiye ,p.bad AS pbirimiad , h.mtc "
                        + "FROM hesap h , pbirimi p WHERE h.pbirimi = p.id";
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                hesap = new Hesap();
                
                hesap.setHno(rs.getInt("hno"));
                hesap.setHad(rs.getString("had"));
                hesap.setBakiye(rs.getDouble("bakiye"));
                hesap.setPbirimiad(rs.getString("pbirimiad"));
                hesap.setMtc(rs.getString("mtc"));
                
                hesap_list.add(hesap);
            }
            
            return hesap_list;
           
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getTumHesapList hata");
            return null;
        }
             
    }
    
    public boolean islem2Ekle(int tutar , String tarih , int iid , int kno){
        
        // transfer islemi yapilirken islem tablosuna eklenmel
        
        String sorgu = "INSERT INTO islem (tutar , tarih , iid , kno) "
                + "VALUES (?, ?, ?, ?)";
        
        try {
            
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setInt(1, tutar);
             ps.setString(2, tarih);              //preparedStatmentlarımızı oluşturduk
              ps.setInt(3, iid);
               ps.setInt(4, kno);
               
              
               
            ps.executeUpdate(); //calistir
            
            return true;
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("islem2Ekle hatali");
            return false;
        }
        
    }
    
    public boolean paraYatirma(int hno ,int miktar , String tarih){
        
        ArrayList <Hesap> hesaplar = new ArrayList<Hesap>();
        hesaplar = getHesapList();
        Hesap hesap = null;
           
        for(Hesap hesapTemp: hesaplar){
            
            if(hesapTemp.getHno() == hno){
                
                hesap=new Hesap();
                hesap=hesapTemp;
            }
                     
        }
        
        double bakiye = hesap.getBakiye();
        
        bakiye+=miktar;
        
        String sorgu ="UPDATE hesap SET bakiye = ? WHERE hno = ?";
        
        try {
            
             ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setDouble(1, bakiye);             //preparedStatmentlarımızı oluşturduk
            ps.setInt(2, hno);
            
                
               
            ps.executeUpdate(); //calistir
            
            //return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("paraYatirma hatalı");
            return  false;
        }
        
        
        
        // para yatirdigimizda bankanin kasasina para girecektir ekleyelim gelir olarak
        
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
            System.out.println("para yatirma banka  hatali");
            return false;
            
        }
        
        bankaGelir+=miktar;
        
        bankaBakiye+=miktar;
        
        bankaKaar = bankaGelir - bankaGider;
        
        String sorgu_banka_hesap_durumu_guncelle = "UPDATE durum SET gelir = ? , kaar = ? , bakiye = ?  WHERE id = 1";
        
        try {
            
             ps = con.prepareStatement(sorgu_banka_hesap_durumu_guncelle);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setDouble(1,bankaGelir);             //preparedStatmentlarımızı oluşturduk
            ps.setDouble(2, bankaKaar);
            ps.setDouble(3, bankaBakiye);
                   
            ps.executeUpdate(); //calistir
            
            
        } catch (Exception e) {
            
             e.printStackTrace();
            System.out.println("paraYatirma banka odemesi hata");
            return false;
            
        }
        
        
        
        boolean bitti_mi = islem2Ekle(miktar, tarih, 7, hno);
        
        if(bitti_mi){
            
            return true;
        }
        else{
            
            return false;
        }
        
    }
    
    public boolean paraCekme(int hno ,int miktar ,String tarih){
        
        ArrayList <Hesap> hesaplar = new ArrayList<Hesap>();
        hesaplar = getHesapList();
        Hesap hesap = null;
           
        for(Hesap hesapTemp: hesaplar){
            
            if(hesapTemp.getHno() == hno){
                
                hesap=new Hesap();
                hesap=hesapTemp;
            }
                     
        }
        
        double bakiye = hesap.getBakiye();
        
        bakiye-=miktar;
        
        String sorgu ="UPDATE hesap SET bakiye = ? WHERE hno = ?";
        
        try {
            
             ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setDouble(1, bakiye);             //preparedStatmentlarımızı oluşturduk
            ps.setInt(2, hno);
            
                
               
            ps.executeUpdate(); //calistir
            
            //return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("paraYatirma hatalı");
            return  false;
        }
        
        
        // para cektigimizde hesap bizim de olsa bankada bulunan bir rparayı cektigimizden onu gider olarak eklemeliyiz
        
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
            System.out.println("para cekme banka islemleri hatali");
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
            System.out.println("para cekme banka  hatali");
            return false;
            
        }
        
        
        boolean bitti_mi = islem2Ekle(miktar, tarih, 2, hno);
        
        if(bitti_mi){
            
            return true;
        }
        else{
            
            return false;
        }
        
    }
    
    
    public ArrayList <Musteri> getMusteriList(){ //musteri listesi dondurcegimizden array list donuslu olmalı
        
        ArrayList <Musteri> musteri_list = new ArrayList<Musteri>();
        Musteri musteri = null;
        try {
            
            String sorgu ="SELECT * FROM musteri WHERE musteri.tc_no = '"+getTc()+"'";
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
                               
            
            while(rs.next()){
                
                musteri = new Musteri();
                
                musteri.setTc(rs.getString("tc_no"));
                musteri.setAd(rs.getString("ad"));
                musteri.setSoyad(rs.getString("soyad"));    //musteri objesine query den gelen bilgileri aldık
                musteri.setSifre(rs.getString("sifre"));
                musteri.setT_tc_no(rs.getString("t_tc_no"));
                
                musteri_list.add(musteri); //listeye ekledik
                
            }
            
            return musteri_list;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getMusteriList metodunda hata aldik");
            
            return null;
        }
             
    }
    
    
    public boolean bilgiGuncelle(String tc_no, String ad , String soyad , String sifre){ //musteri ya guncellenir ya guncellenmez boolean donucek
     
      
      String sorgu ="UPDATE musteri SET ad = ? , soyad = ? ,sifre =? WHERE tc_no = ?"; // tc nosu belirtilen musteriyi silmek
                     
          // System.out.println(tc_no);
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setString(1, ad);             //preparedStatmentlarımızı oluşturduk
            ps.setString(2, soyad);
            ps.setString(3, sifre);
            ps.setString(4, tc_no);
                
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("bilgiGuncelle hatali");
            return false;
        }
        
     
    }
    
    public boolean adresGuncelle(String adres , int id){ //musteri ya guncellenir ya guncellenmez boolean donucek
     
      
      String sorgu ="UPDATE madres SET adres = ? WHERE id = ?"; // tc nosu belirtilen musteriyi silmek
                     
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, adres);
            ps.setInt(2, id);             //preparedStatmentlarımızı oluşturduk
             
                
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("adresGuncelle hatali");
            return false;
        }
        
     
    }
    
    public boolean telefonGuncelle(String tel_no , int id ){ //musteri ya guncellenir ya guncellenmez boolean donucek
     
      
      String sorgu ="UPDATE mtelefon SET tel_no = ? WHERE id = ?"; 
                     
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            
            
            ps.setString(1, tel_no);
            ps.setInt(2, id);             //preparedStatmentlarımızı oluşturduk
            
                
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("telefonGuncelle hatali");
            return false;
        }
        
     
    }
    
    public boolean epostaGuncelle(String eposta , int id){ //musteri ya guncellenir ya guncellenmez boolean donucek
     
      
      String sorgu ="UPDATE meposta SET eposta = ? WHERE id = ?";
                     
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, eposta);
            ps.setInt(2, id);             //preparedStatmentlarımızı oluşturduk
             
                
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("epostaGuncelle hatali");
            return false;
        }
        
     
    }
    
    public ArrayList <Madres> getAdresiList(){
        
        ArrayList <Madres> musteri_adres_list = new ArrayList<Madres>();
        Madres adres = null;
        String sorgu ="SELECT * FROM madres WHERE madres.tc_no = '"+getTc()+"'";
        
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
    
    public ArrayList <Meposta> getEpostaList(){
        
        ArrayList <Meposta> musteri_eposta_list = new ArrayList<Meposta>();
        Meposta eposta = null;
        String sorgu ="SELECT * FROM meposta WHERE meposta.tc_no = '"+getTc()+"'";
        
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
    
    public ArrayList <Mtelefon> getTelefonList(){
        
        ArrayList <Mtelefon> musteri_telefon_list = new ArrayList<Mtelefon>();
        Mtelefon telefon = null;
        String sorgu ="SELECT * FROM mtelefon WHERE mtelefon.tc_no = '"+getTc()+"'";
        
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
    
    public boolean adresEkle(String adres){
        
        String sorgu = "INSERT INTO madres (adres , tc_no) "
                + "VALUES (?, ?)";
        
        try {
            
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, adres);
             ps.setString(2, getTc());              //preparedStatmentlarımızı oluşturduk
              
               
            ps.executeUpdate(); //calistir
            
            return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("adresEkle Hatalı");
            
            return false;
        }
     
    }
    
    public boolean telefonEkle(String tel_no){
        
        String sorgu = "INSERT INTO mtelefon (tel_no , tc_no) "
                + "VALUES (?, ?)";
        
        try {
            
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, tel_no);
             ps.setString(2, getTc());              //preparedStatmentlarımızı oluşturduk
              
               
            ps.executeUpdate(); //calistir
            
            return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("telefonEkle Hatalı");
            
            return false;
        }
     
    }
    
    public boolean epostaEkle(String eposta){
        
        String sorgu = "INSERT INTO meposta (eposta , tc_no) "
                + "VALUES (?, ?)";
        
        try {
            
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, eposta);
             ps.setString(2, getTc());              //preparedStatmentlarımızı oluşturduk
              
               
            ps.executeUpdate(); //calistir
            
            return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("epostaEkle Hatalı");
            
            return false;
        }
     
    }
    
    public ArrayList<Islem> getIslemList(int ay , int yil){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        String sorgu = null;
        
        if(ay>9){
         sorgu ="SELECT i.ino , h1.had AS kaynak , h2.had AS hedef ,it.iad AS islem ,i.tutar, h1.bakiye AS kbakiye ,h2.bakiye AS hbakiye ,i.tarih "
                + "FROM islem i , hesap h1 ,hesap h2 ,itipi it ,musteri m "
                + "WHERE i.kno=h1.hno AND "
                + "i.hno=h2.hno AND "
                + "i.iid=it.iid AND "
                + "h1.mtc = m.tc_no AND "
                + "m.tc_no = '"+getTc()+"' "
                + "i.tarih BETWEEN '"+yil+"-"+ay+"-01' AND '"+yil+"-"+ay+"-27'";
                 //parametre bos gelirse
        
         // System.out.println(sorgu);
        }
        if(ay<10){
            
             sorgu ="SELECT i.ino , h1.had AS kaynak , h2.had AS hedef ,it.iad AS islem ,i.tutar, h1.bakiye AS kbakiye ,h2.bakiye AS hbakiye ,i.tarih "
                + "FROM islem i , hesap h1 ,hesap h2 ,itipi it ,musteri m "
                + "WHERE i.kno=h1.hno AND "
                + "i.hno=h2.hno AND "
                + "i.iid=it.iid AND "
                + "h1.mtc = m.tc_no AND "
                + "m.tc_no = '"+getTc()+"' AND "
                + "i.tarih BETWEEN '"+yil+"-0"+ay+"-01' AND '"+yil+"-0"+ay+"-27'";
            
        }
        
            
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
    
    
    public ArrayList<Islem> getIslemMiktarList(String miktar , int ay , int yil){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        String sorgu2=null;
        
        if(ay > 9){
         sorgu2 ="SELECT i.ino , h1.had AS kaynak , h2.had AS hedef ,it.iad AS islem ,i.tutar, h1.bakiye AS kbakiye ,h2.bakiye AS hbakiye ,i.tarih "
                + "FROM islem i , hesap h1 ,hesap h2 ,itipi it , musteri m "
                + "WHERE i.kno=h1.hno AND "
                + "i.hno=h2.hno AND "
                + "i.iid=it.iid AND "
                + "h1.mtc = m.tc_no AND "
                + "m.tc_no = '"+getTc()+"' AND "
                + "i.tarih BETWEEN '"+yil+"-"+ay+"-01' AND '"+yil+"-"+ay+"-27' "
                + "ORDER BY i.ino DESC "
                + "LIMIT "+miktar;
        }
        
        if(ay<10){
             sorgu2 ="SELECT i.ino , h1.had AS kaynak , h2.had AS hedef ,it.iad AS islem ,i.tutar, h1.bakiye AS kbakiye ,h2.bakiye AS hbakiye ,i.tarih "
                + "FROM islem i , hesap h1 ,hesap h2 ,itipi it , musteri m "
                + "WHERE i.kno=h1.hno AND "
                + "i.hno=h2.hno AND "
                + "i.iid=it.iid AND "
                + "h1.mtc = m.tc_no AND "
                + "m.tc_no = '"+getTc()+"' AND "
                + "i.tarih BETWEEN '"+yil+"-0"+ay+"-01' AND '"+yil+"-0"+ay+"-27' "
                + "ORDER BY i.ino DESC "
                + "LIMIT "+miktar;
            
        }
        
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
    
    public ArrayList<Islem> getIslemAList(){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        
        
        String sorgu ="SELECT i.ino , h1.had AS kaynak , h2.had AS hedef ,it.iad AS islem ,i.tutar, h1.bakiye AS kbakiye ,h2.bakiye AS hbakiye ,i.tarih "
                + "FROM islem i , hesap h1 ,hesap h2 ,itipi it ,musteri m "
                + "WHERE i.kno=h1.hno AND "
                + "i.hno=h2.hno AND "
                + "i.iid=it.iid AND "
                + "h1.mtc = m.tc_no AND "
                + "m.tc_no = '"+getTc()+"' "
                + "ORDER BY i.ino ASC";
        
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
    
    
    public ArrayList<Islem> getIslemList2(int ay , int yil){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        String sorgu = null;
        
        if(ay>9){
           sorgu ="SELECT i.ino AS ino , h.had AS kaynak , it.iad AS islem , i.tutar AS tutar , h.bakiye AS bakiye , i.tarih AS tarih "
                + "FROM islem i , hesap h , itipi it "
                + "WHERE i.kno = h.hno AND i.iid = it.iid AND h.mtc = '"+getTc()+"' AND (i.iid = 2 OR i.iid = 7) AND "
                   + "i.tarih BETWEEN '"+yil+"-"+ay+"-01' AND '"+yil+"-"+ay+"-27'";
                 //parametre bos gelirse
        
         // System.out.println(sorgu);
        }
        if(ay<10){
            
             sorgu ="SELECT i.ino AS ino , h.had AS kaynak , it.iad AS islem , i.tutar AS tutar , h.bakiye AS bakiye , i.tarih AS tarih "
                + "FROM islem i , hesap h , itipi it "
                + "WHERE i.kno = h.hno AND i.iid = it.iid AND h.mtc = '"+getTc()+"' AND (i.iid = 2 OR i.iid = 7) AND "
                + "i.tarih BETWEEN '"+yil+"-0"+ay+"-01' AND '"+yil+"-0"+ay+"-27'";
            
        }
        
            
            try {
                
                st = con.createStatement();
                rs = st.executeQuery(sorgu);
                
                while(rs.next()){
                    
                    islem = new Islem();
                    
                    islem.setIno(rs.getInt("ino"));
                    islem.setKaynak(rs.getString("kaynak"));
                   // islem.setHedef(rs.getString("hedef"));
                    islem.setIslem(rs.getString("islem"));
                    islem.setTutar(rs.getInt("tutar"));
                    islem.setKbakiye(rs.getDouble("bakiye"));
                  //  islem.setHbakiye(rs.getDouble("hbakiye"));
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
    
    
    public ArrayList<Islem> getIslemMiktarList2(String miktar , int ay , int yil){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        String sorgu2=null;
        
        if(ay > 9){
         sorgu2 ="SELECT i.ino AS ino , h.had AS kaynak , it.iad AS islem , i.tutar AS tutar , h.bakiye AS bakiye , i.tarih AS tarih "
                + "FROM islem i , hesap h , itipi it "
                + "WHERE i.kno = h.hno AND i.iid = it.iid AND h.mtc = '"+getTc()+"' AND (i.iid = 2 OR i.iid = 7) AND "
                + "i.tarih BETWEEN '"+yil+"-"+ay+"-01' AND '"+yil+"-"+ay+"-27' "
                + "ORDER BY i.ino DESC "
                + "LIMIT "+miktar;
        }
        
        if(ay<10){
             sorgu2 ="SELECT i.ino AS ino , h.had AS kaynak , it.iad AS islem , i.tutar AS tutar , h.bakiye AS bakiye , i.tarih AS tarih "
                + "FROM islem i , hesap h , itipi it "
                + "WHERE i.kno = h.hno AND i.iid = it.iid AND h.mtc = '"+getTc()+"' AND (i.iid = 2 OR i.iid = 7) AND "
                + "i.tarih BETWEEN '"+yil+"-0"+ay+"-01' AND '"+yil+"-0"+ay+"-27' "
                + "ORDER BY i.ino DESC "
                + "LIMIT "+miktar;
            
        }
        
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
                  //  islem.setHbakiye(rs.getDouble("hbakiye"));
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
    
    public ArrayList<Islem> getIslemAList2(){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        
        
        String sorgu ="SELECT i.ino AS ino , h.had AS kaynak , it.iad AS islem , i.tutar AS tutar , h.bakiye AS bakiye , i.tarih AS tarih "
                + "FROM islem i , hesap h , itipi it "
                + "WHERE i.kno = h.hno AND i.iid = it.iid AND h.mtc = '"+getTc()+"' AND (i.iid = 2 OR i.iid = 7) "
                + "ORDER BY i.ino ASC";
                
                 //parametre bos gelirse
        
         // System.out.println(sorgu);
        
            
            try {
                
                st = con.createStatement();
                rs = st.executeQuery(sorgu);
                
                while(rs.next()){
                    
                    islem = new Islem();
                    
                    islem.setIno(rs.getInt("ino"));
                    islem.setKaynak(rs.getString("kaynak"));
                  //  islem.setHedef(rs.getString("hedef"));
                    islem.setIslem(rs.getString("islem"));
                    islem.setTutar(rs.getInt("tutar"));
                    islem.setKbakiye(rs.getDouble("bakiye"));
                  //  islem.setHbakiye(rs.getDouble("hbakiye"));
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
    
    public double tlCevir(double paraa, int id){
        
        String sorgu = "SELECT * FROM pbirimi";
        int kur = -1;
        double para = paraa;
        
        try {
            
            st = con.createStatement();
             rs = st.executeQuery(sorgu);
             
             while(rs.next()){
                 
                 if(id == rs.getInt("id")){
                     
                     kur = rs.getInt("kur");
                     
                     
                 }
             }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("tlCevir hatali");
            return 0;
        }
        
        para*=(double)kur;
        
        return para;
          
    }
    
    public double tlGeriCevir(double paraa , int id){
                 
        String sorgu = "SELECT * FROM pbirimi";
        int kur = -1;
        double para = paraa;
        
        try {
            
            st = con.createStatement();
             rs = st.executeQuery(sorgu);
             
             while(rs.next()){
                 
                 if(id == rs.getInt("id")){
                     
                     kur = rs.getInt("kur");
                     
                     
                 }
             }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("tlCevir hatali");
            return 0;
        }
        
        para/=(double)kur;
        
        return para;         
    }
    
    
    public boolean islemEkle(int tutar , String tarih , int iid , int kno , int hno){
        
        // transfer islemi yapilirken islem tablosuna eklenmel
        
        String sorgu = "INSERT INTO islem (tutar , tarih , iid , kno , hno) "
                + "VALUES (?, ?, ?, ?, ?)";
        
        try {
            
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setInt(1, tutar);
             ps.setString(2, tarih);              //preparedStatmentlarımızı oluşturduk
              ps.setInt(3, iid);
               ps.setInt(4, kno);
                ps.setInt(5, hno);
              
               
            ps.executeUpdate(); //calistir
            
            return true;
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("islemEkle hatali");
            return false;
        }
        
    }
        
    
    
    public boolean paraTransfer(int khno ,int hhno ,int miktarr ,String tarih ){  // para transferi ya olur ya da olmaz
            
        // para birimlerini ogrenme asamasi cevirmeler icin
        String sorgu = "SELECT p.id FROM pbirimi p , hesap h WHERE h.hno = "+khno+" AND p.id = h.pbirimi";
         String sorguu = "SELECT p.id FROM pbirimi p , hesap h WHERE h.hno = "+hhno+" AND p.id = h.pbirimi";
        int khpbirimi = -1;
        int hhpbirimi = -1;
        
        try {
             st = con.createStatement();
             rs = st.executeQuery(sorgu);
             
             while(rs.next()){
                 
                     
                     khpbirimi = rs.getInt("id");
                     
                 break;
             }
             
             
             rs = st.executeQuery(sorguu);
             
             while(rs.next()){
                
                     
                     hhpbirimi = rs.getInt("id");
                     break;
                     
                 
             }
             
             
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("paraTransfer hata");
            return false;
        }
        
        
        
        
        ArrayList <Hesap> hesaplar = new ArrayList<Hesap>();
        hesaplar = getHesapList();
        Hesap hesap = null;
           
        for(Hesap hesapTemp: hesaplar){
            
            if(hesapTemp.getHno() == khno){
                
                hesap=new Hesap();
                hesap=hesapTemp;
            }
                     
        }
           
        
        double kbakiye = hesap.getBakiye();
        
        
        
        kbakiye = tlCevir(kbakiye, khpbirimi);
        
        
        
        hesaplar = getTumHesapList();
        hesap = null;
           
        for(Hesap hesapTemp: hesaplar){
            
            if(hesapTemp.getHno() == hhno){
                
                hesap=new Hesap();
                hesap=hesapTemp;
            }
                     
        }
        
        double hbakiye = hesap.getBakiye();
        
        
        
        hbakiye = tlCevir(hbakiye, hhpbirimi);
        
      
        
        double miktar = miktarr;
        
        
        
        miktar = tlCevir(miktar, khpbirimi);
        
        
        
        kbakiye-=miktar;
        hbakiye+=miktar;
        
        
        
        kbakiye = tlGeriCevir(kbakiye, khpbirimi);
        hbakiye = tlGeriCevir(hbakiye, hhpbirimi);
        
        
        
        
        String sorgu1 ="UPDATE hesap SET bakiye = ? WHERE hno = ?";
        String sorgu2 ="UPDATE hesap SET bakiye = ? WHERE hno = ?";
        
        try {
            
            ps = con.prepareStatement(sorgu1);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setDouble(1, kbakiye);             //preparedStatmentlarımızı oluşturduk
            ps.setInt(2, khno);
                   
            ps.executeUpdate(); //calistir
            
            ps = con.prepareStatement(sorgu2);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setDouble(1, hbakiye);             //preparedStatmentlarımızı oluşturduk
            ps.setInt(2, hhno);
                   
            ps.executeUpdate(); //calistir
            
           // return true;
            
                       
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("paraTransfer Hatalı");
            return false;
        }
        
        boolean bitti_mi = islemEkle(miktarr, tarih, 1, khno, hhno);
        
        if(bitti_mi){
            
            return true;
        }
        else{
            
            return false;
        }
        
       
    }
    
    public boolean talepOlustur(int iid){
        
        String sorgu = "INSERT INTO talep (m_tc_no , iid ) "
                + "VALUES (?, ?)";
        
        try {
            
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, getTc());
             ps.setInt(2, iid);              //preparedStatmentlarımızı oluşturduk
              
              
               
            ps.executeUpdate(); //calistir
            
            return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("talepOlustur Hatalı");
            
            return false;
        }
        
        
    }
    
    
    public boolean hesapAcmaTalep(String had , int pbirimi){ // talep ya gondedrilir ya gonderilmez
        
        String sorgu = "INSERT INTO hatalep (had , pbirimi ,mtc) "
                + "VALUES (?, ? , ?)";
        
        try {
            
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, had);
             ps.setInt(2, pbirimi);              //preparedStatmentlarımızı oluşturduk
              ps.setString(3, getTc());
              
               
            ps.executeUpdate(); //calistir
            
            return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("hesapAcmaTalep Hatalı");
            
            return false;
        }
        
        
    }
    
    public boolean krediTalep(int hno , int miktar){ // talep ya gondedrilir ya gonderilmez
        
        
        // baste paraBiriminin tl olup olmadigini kontrol edelim
        int pbirimi = 0;
        
        String sorgu_pbirimi_kontrol = "SELECT * FROM hesap WHERE hno = "+hno;
        
        try {
            
            st = con.createStatement();
             rs = st.executeQuery(sorgu_pbirimi_kontrol);
             
             while(rs.next()){
                 
                 pbirimi = rs.getInt("pbirimi");
                 break;
             }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("krediTalep hata");
            return false;
        }
        
        if(pbirimi!=1){
            
            return false;
        }
        
        
        String sorgu = "INSERT INTO ktalep (hno , miktar ,mtc) "
                + "VALUES (?, ? , ?)";
        
        try {
            
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setInt(1, hno);
             ps.setInt(2, miktar);              //preparedStatmentlarımızı oluşturduk
              ps.setString(3, getTc());
              
               
            ps.executeUpdate(); //calistir
            
            return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("krediTalep Hatalı");
            
            return false;
        }
        
        
    }
    
    public boolean hesapSilmeTalep(int hno){ // talep ya gondedrilir ya gonderilmez
        
        
        
        String sorgu2 = "SELECT bakiye FROM hesap WHERE hno = "+hno;
        
        try {
            
            st = con.createStatement();
             rs = st.executeQuery(sorgu2);
             
             while(rs.next()){
                 
                 if(rs.getDouble("bakiye")!=0){
                     return false;
                 }
             }
            
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            
            return false;
        }
        
        
          
        
        String sorgu = "INSERT INTO hstalep (hno , mtc) "
                + "VALUES (?, ?)";
       
        
        try {
            
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setInt(1, hno);
                          //preparedStatmentlarımızı oluşturduk
              ps.setString(2, getTc());
              
               
            ps.executeUpdate(); //calistir
            
            return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("hesapSilmeTalep Hatalı");
            
            return false;
        }
        
        
    }
    
    
    public ArrayList<Kredi> getOwnKrediList(){
        
        ArrayList <Kredi> kredi_list = new ArrayList<Kredi>();
        Kredi kredi = null;
        
        try {
            
            String sorgu ="SELECT k.kid ,k.miktar ,k.dfaiz , k.hno , k.aylik , k.afaiz , k.agfaiz ,k.kalan_nborc , k.odurum , k.kalan_gun , k.tarih "
                    + "FROM kredi k ,hesap h "
                    + "WHERE k.hno = h.hno AND h.mtc = '"+getTc()+"'";
            
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
                
                kredi_list.add(kredi);
            }
            
            return kredi_list;
           
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getOwnKrediList hata");
            return null;
        }
             
    }
    
    public double [] krediBorcu(){
        
        String sorgu = "SELECT * FROM faiz";
        
        
        
        int faiz = 0;
        int gecikme_faiz = 0;
        
        try {
            
               st = con.createStatement();
               rs = st.executeQuery(sorgu);
               
               while(rs.next()){
                   
                   faiz = rs.getInt("faiz");
                   gecikme_faiz = rs.getInt("gfaiz");
                   break;
                   
               }
              
        } catch (Exception e) {
            
            e.printStackTrace();
            
            System.out.println("krediBorcu ode hatali");
        }
        
        
        ArrayList <Kredi> krediler =new ArrayList <Kredi>();
        
        krediler = getOwnKrediList();
        
        double odenecekBorc = 0;
        double kalan_borc = 0;
        
        if(krediler==null){
            
            return  null;
        }
        
       
        
        for(Kredi kredi : krediler){
            
            if(kredi.getOdurum() == 1){   // odenmis anlamina gelir isleme katilmaz
                
                continue;
            }
            if(kredi.getOdurum() == 0){ // gecmis aylarda odenmeyen var bunu bu ayki borca yansitmaliyiz
                
                double para = kredi.getAylik() + kredi.getAfaiz();
                double gecikmeFaizMiktari = para / (double)gecikme_faiz;
                para+=gecikmeFaizMiktari;
                
                odenecekBorc += para;
                
            }
            if(kredi.getOdurum() == 2){
                
                odenecekBorc += kredi.getAfaiz() + kredi.getAylik();
                kalan_borc = kredi.getKalan_nborc();
            }
        }
        
        double dizi [] = {odenecekBorc,kalan_borc};
        
        
        return dizi;
    }
    
    public boolean borcOde(double miktar ,String tarih){  // borc ya odenir ya da odenmez
        
        
        // basta bankaya miktar cinsinden parasını verelim
        
        // hazır miktari almışsken bankadan parayı keselim
        
        String sorgu_banka_durumu = "SELECT * FROM durum WHERE id = 1";
        double bankaBakiye =0;
        double bankaGelir = 0;
        double bankaGider = 0;
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
        
        bankaGelir+=miktar;
        bankaBakiye+=miktar;
        bankaKaar = bankaGelir - bankaGider;
        
        String sorgu_banka_hesap_durumu_guncelle = "UPDATE durum SET gelir = ? , kaar = ? , bakiye = ?  WHERE id = 1";
        
        try {
            
             ps = con.prepareStatement(sorgu_banka_hesap_durumu_guncelle);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setDouble(1,bankaGelir);             //preparedStatmentlarımızı oluşturduk
            ps.setDouble(2, bankaKaar);
            ps.setDouble(3, bankaBakiye);
                   
            ps.executeUpdate(); //calistir
            
            
        } catch (Exception e) {
            
             e.printStackTrace();
            System.out.println("krediTalepOnay hatali");
            return false;
            
        }
        
        
        
        
        //********************************************
        
        String sorgu ="SELECT k.hno FROM kredi k , hesap h WHERE k.hno = h.hno AND h.mtc = '"+getTc()+"'";
        int hno=0;
        
        try {
            
            st = con.createStatement();
               rs = st.executeQuery(sorgu);
               
               while(rs.next()){
                   
                   hno = rs.getInt("hno");
                   break;
                   
               }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("borcOde hatali");
            return false;
        }
        
        String sorgu2 = "SELECT * FROM hesap h WHERE h.hno = "+hno;
        double bakiye = 0;
        
        try {
            
            st = con.createStatement();
               rs = st.executeQuery(sorgu2);
               
               while(rs.next()){
                   
                   bakiye = rs.getDouble("bakiye");
                   break;
                   
               }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("borcOde hatali");
            return false;
        }
        
        bakiye-=miktar;
        
        String sorgu3 = "UPDATE hesap SET bakiye = ? WHERE hno = ?";
        
        try {
            
            ps = con.prepareStatement(sorgu3);  //modüllerden objelerimizi oluşturduk
            
           
            ps.setDouble(1, bakiye);             //preparedStatmentlarımızı oluşturduk
            ps.setInt(2, hno);
                   
            ps.executeUpdate(); //calistir
            
           
            
           // return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("borcOde hatali");
            return false;
        }
        
        
        // islem tablosuna borc odemeyi ekleyelim
        
        boolean bitti_mi = islemEkle((int)miktar, tarih, 4, hno, 19);
        
        if(bitti_mi){
            
            return true;
        }
        else{
            
            return false;
        }
        
         
    }
    
    public boolean krediBilgileriGuncelle(){
        
        ArrayList <Kredi> krediler = new ArrayList<Kredi>();
        
        krediler = getOwnKrediList();
        
        ArrayList <Integer> kredi_id = new ArrayList<Integer>();
        
        for(Kredi kredi : krediler){
            
            if(kredi.getOdurum() == 2 ||kredi.getOdurum() == 0){
                
                kredi_id.add(kredi.getKid());
            }
        }
        
        int eleman_sayisi = kredi_id.size();
        
        String sorgu = "UPDATE kredi SET odurum = ? WHERE kid = ?" ;
        
        try {
            
              for(int i =0; i<eleman_sayisi; i++){
                  
                   ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
           
                   ps.setInt(1, 1);             //preparedStatmentlarımızı oluşturduk
                   ps.setInt(2,kredi_id.get(i));
            
                   
                   ps.executeUpdate(); //calistir
                  
                  
              }
     
                   
            return true;  
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("krediBilgileriGuncelle hata");
            return false;
        }
        
    }
    
    public boolean krediBitir(){ //kredi tam odeninca ya biter ya bitmez
        
        String sorgu ="SELECT k.hno FROM kredi k , hesap h WHERE k.hno = h.hno AND h.mtc = '"+getTc()+"'";
        int hno=0;
        
        try {
            
            st = con.createStatement();
               rs = st.executeQuery(sorgu);
               
               while(rs.next()){
                   
                   hno = rs.getInt("hno");
                   break;
                   
               }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("krediBitir  hatali");
            return false;
        }
        
        String sorgu4 ="DELETE FROM kredi WHERE hno = ?";
        
        
        try {
            
            ps = con.prepareStatement(sorgu4);  //modüllerden objelerimizi oluşturduk
            
           
                   ps.setInt(1, hno);             //preparedStatmentlarımızı oluştu
                   
                   ps.executeUpdate(); //calistir
                   
                   return true;
                   
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("krediBitir hata");
            return false;
        }
        
            
    }
    
    

    public String getT_tc_no() {
        return t_tc_no;
    }

    public void setT_tc_no(String t_tc_no) {
        this.t_tc_no = t_tc_no;
    }
    
    
    
}
