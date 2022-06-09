
package tablolar;

import ekPaket.DataBaseBaglanti;


public class Kullanici {
    
   private String tc;
   private String ad;
   private String soyad;
   private String sifre;
   
   DataBaseBaglanti conn = new DataBaseBaglanti(); // tüm kullanıcılarda db islemleri yapacağımızdan 
                                                                //baglantıları buraya ekledik
   
    public Kullanici(){
       
    }

    public Kullanici(String tc, String ad, String soyad ,String sifre) {
        this.tc = tc;
        this.ad = ad;
        this.soyad = soyad;
        this.sifre = sifre;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }
    
    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
      
}
