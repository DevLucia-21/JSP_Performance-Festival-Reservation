package com.perfortival.performance.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.perfortival.common.config.ConfigUtil;
import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;

public class PerformanceService {

    private static final String API_URL = "http://kopis.or.kr/openApi/restful/pblprfr?";

    public List<PerformanceDTO> fetchPerformances(String keyword, String startDate, String endDate) {
        List<PerformanceDTO> performanceList = new ArrayList<>();

        try {
            String apiKey = ConfigUtil.getApiKey();
            StringBuilder apiUrl = new StringBuilder(API_URL);
            apiUrl.append("service=").append(apiKey);

            if (startDate != null && !startDate.isEmpty()) {
                apiUrl.append("&stdate=").append(startDate.replace("-", ""));
            }

            if (endDate != null && !endDate.isEmpty()) {
                apiUrl.append("&eddate=").append(endDate.replace("-", ""));
            }

            if (keyword != null && !keyword.isEmpty()) {
                apiUrl.append("&shprfnm=").append(keyword);
            }

            apiUrl.append("&cpage=1&rows=10");

            System.out.println("[INFO] API 요청 URL: " + apiUrl.toString());

            URL url = new URL(apiUrl.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            System.out.println("[INFO] API 응답 코드: " + responseCode);

            if (responseCode == 200) {
                InputStream inputStream = conn.getInputStream();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputStream);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("db");

                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        PerformanceDTO dto = new PerformanceDTO();
                        dto.setId(getTagValue("mt20id", element));
                        dto.setTitle(getTagValue("prfnm", element));
                        dto.setStartDate(getTagValue("prfpdfrom", element));
                        dto.setEndDate(getTagValue("prfpdto", element));
                        dto.setLocation(getTagValue("fcltynm", element));
                        dto.setGenre(getTagValue("genrenm", element));
                        String posterUrl = getTagValue("poster", element);
                        dto.setPosterUrl(posterUrl);

                        performanceList.add(dto);
                    }
                }

                System.out.println("[INFO] 공연 목록 수: " + performanceList.size());

            } else {
                System.out.println("[ERROR] API 요청 실패: " + responseCode);
            }

        } catch (Exception e) {
            System.err.println("[ERROR] API 요청 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return performanceList;
    }

    // XML 태그 값을 추출하는 메서드
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0).getFirstChild();
            if (node != null) {
                return node.getNodeValue();
            }
        }
        return "";
    }

    private PerformanceDAO performanceDAO = new PerformanceDAO();

    // 공연 ID로 공연 정보 조회
    public PerformanceDTO getPerformanceById(String id) {
        // DAO에서 공연 정보를 가져온다.
        return performanceDAO.getPerformanceById(id);
    }
   
}
