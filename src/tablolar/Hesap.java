
package tablolar;


public class Hesap {
    
    private int hno;
    private double bakiye;
    private String had , mtc ,pbirimiad;

    public Hesap(int hno, String pbirimiad, double bakiye, String had, String mtc) {
        this.hno = hno;
        this.pbirimiad = pbirimiad;
        this.bakiye = bakiye;
        this.had = had;
        this.mtc = mtc;
    }
    
    public Hesap(){
        
    }

    public int getHno() {
        return hno;
    }

    public void setHno(int hno) {
        this.hno = hno;
    }

    public String getPbirimiad() {
        return pbirimiad;
    }

    public void setPbirimiad(String pbirimiad) {
        this.pbirimiad = pbirimiad;
    }

    public double getBakiye() {
        return bakiye;
    }

    public void setBakiye(double bakiye) {
        this.bakiye = bakiye;
    }

    public String getHad() {
        return had;
    }

    public void setHad(String had) {
        this.had = had;
    }

    public String getMtc() {
        return mtc;
    }

    public void setMtc(String mtc) {
        this.mtc = mtc;
    }
    
    
    
}
