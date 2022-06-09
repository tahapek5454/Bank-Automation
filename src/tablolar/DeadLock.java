
package tablolar;


public class DeadLock {
    
    private int ino1;
    private int ino2;
    
    public DeadLock(){
        
    }
    
    
    
    public String getMessage(){
        
        return "("+getIno1()+","+getIno2()+")";
    }

    public int getIno1() {
        return ino1;
    }

    public void setIno1(int ino1) {
        this.ino1 = ino1;
    }

    public int getIno2() {
        return ino2;
    }

    public void setIno2(int ino2) {
        this.ino2 = ino2;
    }
    
    

 
    
    
    
}
