
package tablolar;


public class Durum {
    
    private int id;
    private double gelir, gider, kaar, toplamBakiye;

    public Durum(int id, double gelir, double gider, double kaar, double toplamBakiye) {
        this.id = id;
        this.gelir = gelir;
        this.gider = gider;
        this.kaar = kaar;
        this.toplamBakiye = toplamBakiye;
    }
    
    public Durum(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getGelir() {
        return gelir;
    }

    public void setGelir(double gelir) {
        this.gelir = gelir;
    }

    public double getGider() {
        return gider;
    }

    public void setGider(double gider) {
        this.gider = gider;
    }

    public double getKaar() {
        return kaar;
    }

    public void setKaar(double kaar) {
        this.kaar = kaar;
    }

    public double getToplamBakiye() {
        return toplamBakiye;
    }

    public void setToplamBakiye(double toplamBakiye) {
        this.toplamBakiye = toplamBakiye;
    }
    
    
    
}
