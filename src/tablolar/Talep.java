
package tablolar;


public class Talep {
    
   private int id , onay;
   private String tc_no ,iad;

    public Talep(int id, String tc_no, String iad ,int onay) {
        this.id = id;
        this.tc_no = tc_no;
        this.iad = iad;
        this.onay = onay;
    }
   
    public Talep(){
       
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTc_no() {
        return tc_no;
    }

    public void setTc_no(String tc_no) {
        this.tc_no = tc_no;
    }

    public int getOnay() {
        return onay;
    }

    public void setOnay(int onay) {
        this.onay = onay;
    }

    public String getIad() {
        return iad;
    }

    public void setIad(String iad) {
        this.iad = iad;
    }
   
   
   
    
}
