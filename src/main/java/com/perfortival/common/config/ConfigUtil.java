package com.perfortival.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigUtil {

    private static String API_KEY;

    static {
        try (InputStream input = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            if (input != null) {
                prop.load(input);
                API_KEY = prop.getProperty("API_KEY");

                if (API_KEY == null || API_KEY.trim().isEmpty()) {
                    System.err.println("[오류] API_KEY가 설정되지 않았습니다. config.properties 파일을 확인하세요.");
                } else {
                    System.out.println("[정보] API_KEY가 정상적으로 로드되었습니다.");
                }

            } else {
                System.err.println("[오류] config.properties 파일을 찾을 수 없습니다.");
            }
        } catch (IOException ex) {
            System.err.println("[오류] config.properties 파일을 로드하는 중 오류가 발생했습니다.");
            ex.printStackTrace();
        }
    }

    // API_KEY를 반환하는 메서드
    public static String getApiKey() {
        if (API_KEY == null || API_KEY.trim().isEmpty()) {
            throw new IllegalStateException("[오류] API_KEY가 설정되지 않았습니다. config.properties 파일을 확인하세요.");
        }
        return API_KEY;
    }
}
