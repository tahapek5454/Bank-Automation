
package tablolar;


public class Mtelefon {
    
     private int id;
    private String tel_no , tc_no;

    public Mtelefon(int id, String tel_no, String tc_no) {
        this.id = id;
        this.tel_no = tel_no;
        this.tc_no = tc_no;
    }
    
    public Mtelefon(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    }

    public String getTc_no() {
        return tc_no;
    }

    public void setTc_no(String tc_no) {
        this.tc_no = tc_no;
    }
    
    
    
}
