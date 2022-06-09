
package tablolar;



public class ParaBirimi {
    
    private int id;
    private String bad;
    private int kur;
    

    public ParaBirimi(int id, String bad, int kur) {
        this.id = id;
        this.bad = bad;
        this.kur = kur;
    }
    
    public ParaBirimi(){
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBad() {
        return bad;
    }

    public void setBad(String bad) {
        this.bad = bad;
    }

    public int getKur() {
        return kur;
    }

    public void setKur(int kur) {
        this.kur = kur;
    }
    
       
    
}
