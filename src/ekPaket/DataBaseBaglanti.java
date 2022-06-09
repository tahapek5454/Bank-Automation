
package ekPaket;

import java.sql.*;

public class DataBaseBaglanti {
    
    private String kullanici_adi = "root";
    private String parola = "";

    private String db_ismi = "banka";

    private String host =  "localhost";

    private int port = 3306;

    private Connection con = null;
    
    public DataBaseBaglanti(){
        
    }
    
    public Connection connDB(){
        
        String url = "jdbc:mysql://" + host + ":" + port + "/" + db_ismi;
        
         try {
            con = DriverManager.getConnection(url, kullanici_adi, parola);        
           // System.out.println("Bağlantı Başarılı...");
           return con;
           


        } catch (SQLException ex) {
            System.out.println("Bağlantı Başarısız...");
            //ex.printStackTrace();
            return con;
        }
    }
    
     
}
