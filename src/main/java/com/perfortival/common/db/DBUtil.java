package com.perfortival.common.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/perfortival?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "jsp";
    private static final String DB_PASSWORD = "1234";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            System.out.println("[DB 연결 오류] " + e.getMessage());
        }
        return conn;
    }
}
