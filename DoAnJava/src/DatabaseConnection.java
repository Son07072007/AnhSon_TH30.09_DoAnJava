import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException; 

public class DatabaseConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlserver://localhost\\SQLEXPRESS;"
                       + "databaseName=QLSinhVien;"
                       + "encrypt=true;"
                       + "trustServerCertificate=true;"
                       + "sendStringParametersAsUnicode=true;";
            String user = "sa";
            String password = "katze070707";
            
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối SQL: " + e.getMessage());
        }
        return conn;
    }
}