import java.sql.Connection;
import java.sql.DriverManager;
 
class test2 {
 
    private static final String url = "jdbc:mysql://localhost";
 
    private static final String user = "aghiles";
 
    private static final String password = "aghilesDJ35B";
 
    public static void main(String args[]) {
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Success");
 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
