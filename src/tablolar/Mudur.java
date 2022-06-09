
package tablolar;

import java.sql.*;
import java.util.ArrayList;

public class Mudur extends Kullanici{
    
    Statement st = null;
    PreparedStatement ps =null;
    ResultSet rs = null;            //DataBase işlemleri
    Connection con =conn.connDB();

    public Mudur() {
    }

    public Mudur(String tc, String ad, String soyad, String sifre) {
        super(tc, ad, soyad, sifre);
    }
    
    public ArrayList <Musteri> getMusteriList(){ //musteri listesi dondurcegimizden array list donuslu olmalı
        
        ArrayList <Musteri> musteri_list = new ArrayList<Musteri>();
        Musteri musteri = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT *"
                                + "FROM musteri");
            
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
    
    public String getTemsilci(){
        
        ArrayList <String> t_tc_no_lar = new ArrayList<String>();
        String temptc = null;
        String sorgu2 ="SELECT  tc_no FROM temsilci";
        
        try {
            
            st = con.createStatement();
              rs = st.executeQuery(sorgu2);
              
              while(rs.next()){
                  
                  temptc = rs.getString("tc_no");
                  
                  t_tc_no_lar.add(temptc);
              }
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getTemsilci HATA");
            return null;
        }
        
        
        
         ArrayList <String> ekli_t_tc_no_lar = new ArrayList<String>();
        String t_tc_no = null;
        try {
            
              String sorgu = "SELECT t_tc_no ,COUNT(*) FROM musteri GROUP BY t_tc_no ORDER BY COUNT(*) ASC";
              st = con.createStatement();
              rs = st.executeQuery(sorgu);
                                                        // burdaki sorgu bize temsilcilik yapan temsilcilerin
                                                        // temsiliclik sayılarını küçükten buyuye sıralar
                                
              while(rs.next()){
                  t_tc_no=rs.getString("t_tc_no");
                  
                  ekli_t_tc_no_lar.add(t_tc_no);
                  
              }
          
               
        } catch (Exception e){
            
            e.printStackTrace();
            System.out.println("getTemsilci de hata var");
            
            return  null;
        }
        
        if(t_tc_no_lar.size() == ekli_t_tc_no_lar.size()){
             
            // bu durum iki array listinde esit yani her temsilcinin en az bir musterisi oldugunu gosterir
            // bu durumda sonda yaptigimiz islemden en az musteriye sahip temsilciyi buluruz
            
            return ekli_t_tc_no_lar.get(0);
            
        }
        else{
            
            // bu durum en az bir tane musterisi olmayan yani 0 musterili yeni bir temsilcinin oldugunu gosterir
            // burdan musterisi olmayan temsilciyi bulup onu dondurmeliyiz
            
            boolean flag = true;
            
            for(int i = 0; i<t_tc_no_lar.size() ; i++){
                
                for(int j=0; j<ekli_t_tc_no_lar.size(); j++){
                    
                    if(t_tc_no_lar.get(i).equals(ekli_t_tc_no_lar.get(j))){
                        
                        flag = false;
                        break;
                        
                    }
                }
                
                if(flag){
                    
                    return t_tc_no_lar.get(i);
                }
                
                flag = true;
                     
            }
            
        }
        
        return null;
    }
    
    public boolean musteriEkle(String tc_no, String ad, String soyad, String t_tc_no){ //musteri ya eklenir ya eklenmez boolean donucek
      // sifre belirlenmemsinin sebebi burası mudurın ekleme yeri default olarak
      //sifre zaten ekleniyor kullanıcı dilerse sifresini kendi belirleyebilicek
      
      String sorgu ="INSERT INTO musteri (tc_no, ad, soyad, t_tc_no)"
                     + " VALUES (?, ?, ?, ?)";
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, tc_no);
             ps.setString(2, ad);              //preparedStatmentlarımızı oluşturduk
              ps.setString(3, soyad);
                ps.setString(4, t_tc_no);
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("musteriEkle hatali");
            return false;
        }
        
     
    }
    
    public ArrayList <ParaBirimi> getParaBirimiList(){ //para birimi listesi dondurcegimizden array list donuslu olmalı
        
        ArrayList <ParaBirimi> parabirimi_list = new ArrayList<ParaBirimi>();
        ParaBirimi parabirimi = null;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT *"
                                + "FROM pbirimi");
            
            while(rs.next()){
                
                // para birimi nesnesi olusturduk
                parabirimi = new ParaBirimi(rs.getInt("id"),rs.getString("bad"),rs.getInt("kur"));
                           
                parabirimi_list.add(parabirimi); //listeye ekledik
                
            }
            
            return parabirimi_list;
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("getParaBirimiList metodunda hata aldik");
            
            return null;
        }
             
    }
    
    public boolean parabirimiEkle(String bad, int kur){ //para birimi  ya eklenir ya eklenmez boolean donucek
      // id belirlenmemsinin sebebi auto ıncremant olması
      
      
      String sorgu ="INSERT INTO pbirimi (bad, kur)"
                     + " VALUES (?, ?)";
      
        try {
            
          
            ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
            ps.setString(1, bad);
             ps.setInt(2, kur);              //preparedStatmentlarımızı oluşturduk
             
               
            ps.executeUpdate(); //calistir
            
            return true;
               
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("parabirimi ekle hatali");
            return false;
        }
        
     
    }
    
    public boolean parabirimiGuncelle(int id ,int kur){ //para birimi  ya guncellenir ya guncellenmez boolean donucek
        
        String sorgu ="UPDATE pbirimi SET kur = ? WHERE id = ?";
        
        try {
            
             ps = con.prepareStatement(sorgu); //modüllerden obje olusturduk
             
             ps.setInt(1, kur);
              ps.setInt(2, id);
              
             ps.executeUpdate();
             
             return true;
             
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("parabirimiGuncelle hata");
            return false;
        }  
        
    }
    
    public String getMaas(){ //maasi direkt ekrana bastiracagmizdan string tipinde alıyoruz
        
        String sorgu ="SELECT * FROM maas";
        
        int maas2;
        String maas=null;
        
        try {
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                maas2=rs.getInt("maas");
                maas = String.valueOf(maas2); //gelen degeristringe cevirdik
                break;
                
            }
            
            return maas;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getMaas da hata");
            return null;
            
        }
        
        
    }
    
    public boolean maasGuncelle(int maas){ //maas ya guncellenir ya gıncellenmez boolean donus
        
        String sorgu ="UPDATE maas SET maas = ?";
        
        try {
            
             ps = con.prepareStatement(sorgu); //modüllerden obje olusturduk
             
             ps.setInt(1, maas);
             
             ps.executeUpdate();
             
             return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("maasGuncelle hata");
            return false;
            
        }
          
    }
    
    public ArrayList<String> getFaiz(){
        
        String sorgu ="SELECT * FROM faiz";
        ArrayList <String> faizlist = null;
        String value=null;
        int value2;
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                faizlist=new ArrayList<String>();
                value2=rs.getInt("faiz");
                value = String.valueOf(value2);
                faizlist.add(value);
                                                      // bu kısımda degeleri castiing edip Stringe cevirdik
                value2=rs.getInt("gfaiz");
                value = String.valueOf(value2);
                faizlist.add(value);
                
                break;
                
                
            }
            
            return faizlist;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getFaiz hata");
            
            return null;
        }
        
     
    }
    
    public boolean faizGuncelle(int faiz){  //ya  gunceller ya guncellemez boolean
        
        String sorgu ="UPDATE faiz SET faiz = ?";
        
        try {
            
            ps = con.prepareStatement(sorgu); //modüllerden obje olusturduk
             
             ps.setInt(1, faiz);
             
             ps.executeUpdate();
             
             return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("faizGuncelle hata");
            return false;
            
        }
          
    }
    
    public boolean gFaizGuncelle(int gfaiz){  //ya  gunceller ya guncellemez boolean
        
        String sorgu ="UPDATE faiz SET gfaiz = ?";
        
        try {
            
            ps = con.prepareStatement(sorgu); //modüllerden obje olusturduk
             
             ps.setInt(1, gfaiz);
             
             ps.executeUpdate();
             
             return true;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("gfaizGuncelle hata");
            return false;
            
        }
          
    }
    
    public Durum getDurum(){
        
        
        Durum durum =null;
        String sorgu = "SELECT * FROM durum";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                durum = new Durum();
                
                durum.setId(rs.getInt("id"));
                durum.setGelir(rs.getDouble("gelir"));
                durum.setGider(rs.getDouble("gider"));
                durum.setKaar(rs.getDouble("kaar"));
                durum.setToplamBakiye(rs.getDouble("bakiye"));
                
                break;
            }
            
               return durum;
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("getDurum hata");
            return null;
        }
        
    }
    
    public ArrayList<Islem> getIslemList(){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        
        
        String sorgu ="SELECT i.ino , h1.had AS kaynak , h2.had AS hedef ,it.iad AS islem ,i.tutar, h1.bakiye AS kbakiye ,h2.bakiye AS hbakiye ,i.tarih "
                + "FROM islem i , hesap h1 ,hesap h2 ,itipi it "
                + "WHERE i.kno=h1.hno AND "
                + "i.hno=h2.hno AND "
                + "i.iid=it.iid"; //parametre bos gelirse
        
         //System.out.println(sorgu);
        
            
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
                + "FROM islem i , hesap h1 ,hesap h2 ,itipi it "
                + "WHERE i.kno=h1.hno AND "
                + "i.hno=h2.hno AND "
                + "i.iid=it.iid "
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
    
    public String tarihAyarla(String tarih){
        
        String myTarih = tarih;
         String str []=myTarih.split("-");
         int data[] = new int[3];
         int i=0;
         String nexTarih=null;
         for(String s : str){
            
            // System.out.println(Integer.parseInt(s));
             data[i]=Integer.parseInt(s);
             i++;
             
         }
         
          //  System.out.println(data[1]);
         
         data[1]++;
         
         if(data[1]>12){
             data[1]=1;
             data[0]++;
         }
         
         if(data[1]<10 && data[2]<10){
             
             nexTarih=data[0]+"-0"+data[1]+"-0"+data[2];
             
         }
         else if(data[1]<10){
             
              nexTarih=data[0]+"-0"+data[1]+"-"+data[2];
             
         }
         else if(data[2]<10){
             
             nexTarih=data[0]+"-"+data[1]+"-0"+data[2];
             
         }
         else{
              nexTarih=data[0]+"-"+data[1]+"-"+data[2];
         }
    
        
        return nexTarih;
    }
    
    public boolean tarihIlerlet(){ //tarih ya ilerletilir ya da ilerletilmez
        
        String tarih = getNowDate();
        
        tarih = tarihAyarla(tarih);
        
        String sorgu ="UPDATE tarih SET tarih = ? WHERE id = 1";
        
        try {
            
             ps = con.prepareStatement(sorgu); //modüllerden obje olusturduk
             
             ps.setString(1, tarih);
             
             ps.executeUpdate();
             
             return true;
            
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("tarihIlerlet hata");
            return false;
        }
         
         
    }
    
    
    public boolean krediDevamEttir(){  // kredi ya devam eder ya da etmez hata alırız
        
        ArrayList <Integer> hesap_numaralari = new ArrayList<Integer>();
        
        // ilk basta sahip olunan kredi hesap numaralarını bulmalıyız
        
        String sorgu1 = "SELECT DISTINCT hno From kredi";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu1);
            
            while(rs.next()){
                
                hesap_numaralari.add(rs.getInt("hno"));
                    
            }
            
            if(hesap_numaralari.size()==0){  // kredi tablosu bostur bir seyler yapma
                
                return false;
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("krediDevameTTir hata");
            return false;
        }
        
        
        ArrayList <Kredi> krediler = new ArrayList<Kredi>();
        Kredi kredi = null;
        
        
        // son olan kredi bilgilerini alalim
        
        for(int i=0; i<hesap_numaralari.size(); i++){
            
            String sorgu2 = "SELECT * "
                         + "FROM kredi "
                         + "WHERE hno = "+hesap_numaralari.get(i)+" AND "
                    + "kalan_gun  = (SELECT MIN(kalan_gun) FROM kredi WHERE hno = "+hesap_numaralari.get(i)+")";
            
            try {
                
                st = con.createStatement();
               rs = st.executeQuery(sorgu2);
            
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
                
                
                krediler.add(kredi);
                
                    
            }
                
            } catch (Exception e) {
                
                e.printStackTrace();
                System.out.println("krediDevameTTir hata");
                return false;
            }
            
            
        }
        
        double kalan_nborc = 0;
        int miktar = 0;
        int kalan_gun = 0;
        double aylik = 0;
        String tarih = null;
        
        
         String sorgu3 ="INSERT INTO kredi (miktar , hno , aylik , afaiz  , kalan_nborc ,kalan_gun ,tarih)"
                     + " VALUES (?, ? , ? , ? , ? ,? ,?)";
         
         String sorgu4 ="DELETE FROM kredi WHERE hno = ?";
        
        for(int i=0; i<hesap_numaralari.size() ; i++){
           
            
            miktar = krediler.get(i).getMiktar();
            kalan_gun = krediler.get(i).getKalan_gun() - 1;
            aylik = krediler.get(i).getAylik();
            kalan_nborc = (double)miktar - ((10-kalan_gun)*aylik);
            tarih = getNowDate();
            
            if(kalan_gun == 0){
                
                try {
                    
                     
                     
                    
                     ps = con.prepareStatement(sorgu4);  //modüllerden objelerimizi oluşturduk
                     ps.setInt(1, krediler.get(i).getHno());
                     
                      ps.executeUpdate(); //calistir
                      
                      
                     
                } catch (Exception e) {
                    
                    e.printStackTrace();
                    System.out.println("kredi silme hata");
                    return false;
                }
                
                continue;
                
            }
            
     
            
            try {
                
                ps = con.prepareStatement(sorgu3);  //modüllerden objelerimizi oluşturduk
            
                ps.setInt(1, krediler.get(i).getMiktar());
                ps.setInt(2, krediler.get(i).getHno());             //preparedStatmentlarımızı oluşturduk
                ps.setDouble(3, krediler.get(i).getAylik());    
                ps.setDouble(4, krediler.get(i).getAfaiz());
                ps.setDouble(5, kalan_nborc);
                ps.setInt(6, kalan_gun);
                ps.setString(7, tarih);
            
           
             
                ps.executeUpdate(); //calistir
                
            } catch (Exception e) {
                e.printStackTrace();
                 System.out.println("krediDevameTTir hata");
                return false;
            }
        }
                
        
        
        
        return true;
    }
    
    
    public ArrayList<Kredi> getKrediList(){
        
        ArrayList <Kredi> kredi_list = new ArrayList<Kredi>();
        Kredi kredi = null;
        
        try {
            
            String sorgu ="SELECT k.kid ,k.miktar ,k.dfaiz , k.hno , k.aylik , k.afaiz , k.agfaiz ,k.kalan_nborc , k.odurum , k.kalan_gun , k.tarih "
                    + "FROM kredi k";
                  
            
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
    
    
    
    
    public boolean krediBilgileriGuncelle(){
        
        ArrayList <Kredi> krediler = new ArrayList<Kredi>();
        
        krediler = getKrediList();
        
        ArrayList <Integer> kredi_id = new ArrayList<Integer>();
        
        for(Kredi kredi : krediler){
            
            if(kredi.getOdurum() == 2){
                
                kredi_id.add(kredi.getKid());
            }
        }
        
        int eleman_sayisi = kredi_id.size();
        
        String sorgu = "UPDATE kredi SET odurum = ? WHERE kid = ?" ;
        
        try {
            
              for(int i =0; i<eleman_sayisi; i++){
                  
                   ps = con.prepareStatement(sorgu);  //modüllerden objelerimizi oluşturduk
            
           
                   ps.setInt(1, 0);             //preparedStatmentlarımızı oluşturduk
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
    
   
    
    public ArrayList<Islem> getIslemList2(){
        
        ArrayList <Islem> islem_list = new ArrayList<Islem>();
        Islem islem = null;
        
        
        String sorgu ="SELECT i.ino AS ino , h.had AS kaynak , it.iad AS islem , i.tutar AS tutar , h.bakiye AS bakiye , i.tarih AS tarih "
                + "FROM islem i , hesap h , itipi it "
                + "WHERE i.kno = h.hno AND i.iid = it.iid AND (i.iid = 2 OR i.iid = 7 )";
                
        
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
                + "FROM islem i , hesap h , itipi it "
                + "WHERE i.kno = h.hno AND i.iid = it.iid AND (i.iid = 2 OR i.iid = 7 ) ORDER BY i.ino DESC LIMIT "+miktar;
        
        
        
     
        
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
    
    
    public ArrayList<Kredi> getOwnKrediList(){
        
        ArrayList <Kredi> kredi_list = new ArrayList<Kredi>();
        Kredi kredi = null;
        
        try {
            
            String sorgu ="SELECT k.kid ,k.miktar ,k.dfaiz , k.hno , k.aylik , k.afaiz , k.agfaiz ,k.kalan_nborc , k.odurum , k.kalan_gun , k.tarih ,h.mtc AS mtc "
                    + "FROM kredi k ,hesap h "
                    + "WHERE k.hno = h.hno";
            
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
    
    
    public boolean temsilciMaasOde(){
        
        int toplamMaas=0;
        
        String sorgu ="SELECT SUM(m.maas) AS toplam_maas "
                + "FROM temsilci t , maas m "
                + "WHERE t.maasid = m.mid";
        
        try {
            
            st = con.createStatement();
            rs = st.executeQuery(sorgu);
            
            while(rs.next()){
                
                toplamMaas = rs.getInt("toplam_maas");
                break;
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("temsilciMaasOde");
            return false;
        }
        
        
        System.out.println("toplam maas miktari = "+toplamMaas);
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
                   
                  // System.out.println("Gelir = "+bankaGelir);
                   
                   
                   bankaGider = rs.getDouble("gider");
                   
                 //  System.out.println("gider = "+bankaGider);
                   
                   bankaKaar = rs.getDouble("kaar");
                   
                 //  System.out.println("kaar = "+bankaKaar);
                   
                   bankaBakiye = rs.getDouble("bakiye");
                   
                 //  System.out.println("bakiye = "+bankaBakiye);
                   
                   break;
                   
               }
           
            
        } catch (Exception e) {
            
            e.printStackTrace();
            System.out.println("krediTalepOnay hatali");
            return false;
            
        }
        
        bankaGider+=toplamMaas;
        
      //  System.out.println("Guncellenmis gider = "+bankaGider);
        
        bankaBakiye-=toplamMaas;
        
      //  System.out.println("Guncellenmiş bakiye = "+bankaBakiye);
        
        bankaKaar = bankaGelir - bankaGider;
        
      //  System.out.println("Guncellenmis kaar = "+bankaKaar);
        
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
        
        
        
        return true;
                
        
    }
    
    
    public void deadLockAnaliz(){
        
        ArrayList <Islem> islemler = new ArrayList<Islem>();
        ArrayList <DeadLock> deadlocklar = new ArrayList<DeadLock>();
        DeadLock deadlock = null;
        
        islemler = getIslemList();
        
        if(islemler.size()==0){
            
            System.out.println("islem yok");
        }else{
            
            
            boolean flag = false;
            int i=0;
            int j=i+1;
            
            for( i=0; i<islemler.size() ; i++ ){
                
                for( j=0 ; j<islemler.size() ; j++){
                    
                    if(islemler.get(i).getKaynak().equals(islemler.get(j).getHedef())){
                        
                        // transfer isleminde cakisma gozukuyor deadlock
                        flag = true;
                        break;
                    }
                }
                
                if(flag){
                    
                    deadlock = new DeadLock();
                    deadlock.setIno1(islemler.get(i).getIno());
                    deadlock.setIno2(islemler.get(j).getIno());
                    
                    deadlocklar.add(deadlock);
                    flag = false;
                    
                    
                }
            }
            
            System.out.println("DeadLock sayisi = "+deadlocklar.size());
            System.out.print("islemler :  ");
            
            for(int k = 0; k<deadlocklar.size(); k++){
                
                System.out.print(deadlocklar.get(k).getMessage()+" , ");
                
            }
            
            System.out.println("");
            
        }
        
        
    }
    
   
    
}
