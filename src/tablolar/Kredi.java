
package tablolar;


public class Kredi {
    
    private int kid ,miktar ,dfaiz ,hno ,kalan_gun ,odurum;

    

    private double aylik , afaiz ,agfaiz , kalan_nborc;
    
    private String tarih ,mtc;

    

    
    public Kredi(int miktar, int hno) {
        this.miktar = miktar;
        this.hno = hno;
    }
    
    public Kredi(){
        
    }

    public int getKid() {
        return kid;
    }

    public void setKid(int kid) {
        this.kid = kid;
    }

    public int getMiktar() {
        return miktar;
    }

    public void setMiktar(int miktar) {
        this.miktar = miktar;
    }

    public int getDfaiz() {
        return dfaiz;
    }

    public void setDfaiz(int dfaiz) {
        this.dfaiz = dfaiz;
    }

    public int getHno() {
        return hno;
    }

    public void setHno(int hno) {
        this.hno = hno;
    }

    public double getAylik() {
        return aylik;
    }

    public void setAylik(double aylik) {
        this.aylik = aylik;
    }

    public double getAfaiz() {
        return afaiz;
    }

    public void setAfaiz(double afaiz) {
        this.afaiz = afaiz;
    }

    public double getAgfaiz() {
        return agfaiz;
    }

    public void setAgfaiz(double agfaiz) {
        this.agfaiz = agfaiz;
    }

    public double getKalan_nborc() {
        return kalan_nborc;
    }

    public void setKalan_nborc(double kalan_nborc) {
        this.kalan_nborc = kalan_nborc;
    }
    
    public int getKalan_gun() {
        return kalan_gun;
    }

    public void setKalan_gun(int kalan_gun) {
        this.kalan_gun = kalan_gun;
    }
    
    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
    
    public int getOdurum() {
        return odurum;
    }

    public void setOdurum(int odurum) {
        this.odurum = odurum;
    }
    
    public String getMtc() {
        return mtc;
    }

    public void setMtc(String mtc) {
        this.mtc = mtc;
    }
    
    
    
}
