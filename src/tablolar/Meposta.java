
package tablolar;


public class Meposta {
    
     private int id;
    private String eposta , tc_no;

    public Meposta(int id, String eposta, String tc_no) {
        this.id = id;
        this.eposta = eposta;
        this.tc_no = tc_no;
    }
    
    public Meposta(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }

    public String getTc_no() {
        return tc_no;
    }

    public void setTc_no(String tc_no) {
        this.tc_no = tc_no;
    }
    
    
    
}
