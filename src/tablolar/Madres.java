
package tablolar;


public class Madres {
    
    private int id;
    private String adres , tc_no;

    public Madres(int id, String adres, String tc_no) {
        this.id = id;
        this.adres = adres;
        this.tc_no = tc_no;
    }
    
    public Madres(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTc_no() {
        return tc_no;
    }

    public void setTc_no(String tc_no) {
        this.tc_no = tc_no;
    }
    
}
