package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/perfortival?useSSL=false&serverTimezone=UTC",
                "jsp", "1234"  // 실제 DB 계정 정보로 수정!
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
