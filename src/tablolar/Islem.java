
package tablolar;


public class Islem {
    
    private int ino, tutar;
    private String tarih,kaynak,hedef,islem;
    private double kbakiye,hbakiye;
  

    public Islem(int ino, String kaynak, String hedef, int tutar, double kbakiye, double hbakiye, String tarih) {
        this.ino = ino;
        this.kaynak = kaynak;
        this.hedef = hedef;
        this.tutar = tutar;
        this.kbakiye = kbakiye;
        this.hbakiye = hbakiye;
        this.tarih = tarih;
    }
    
    
    public Islem(){
        
    }

    public int getIno() {
        return ino;
    }

    public void setIno(int ino) {
        this.ino = ino;
    }

    public int getTutar() {
        return tutar;
    }

    public void setTutar(int tutar) {
        this.tutar = tutar;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getKaynak() {
        return kaynak;
    }

    public void setKaynak(String kaynak) {
        this.kaynak = kaynak;
    }

    public String getHedef() {
        return hedef;
    }

    public void setHedef(String hedef) {
        this.hedef = hedef;
    }

    public String getIslem() {
        return islem;
    }

    public void setIslem(String islem) {
        this.islem = islem;
    }

    public double getKbakiye() {
        return kbakiye;
    }

    public void setKbakiye(double kbakiye) {
        this.kbakiye = kbakiye;
    }

    public double getHbakiye() {
        return hbakiye;
    }

    public void setHbakiye(double hbakiye) {
        this.hbakiye = hbakiye;
    }

   
    
    
}
