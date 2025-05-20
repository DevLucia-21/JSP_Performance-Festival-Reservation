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
    private PerformanceDAO performanceDAO = new PerformanceDAO();

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
                        dto.setPosterUrl(getTagValue("poster", element));

                        performanceList.add(dto);
                    }
                }

                System.out.println("[INFO] 공연 목록 수: " + performanceList.size());

            } else {
                System.err.println("[ERROR] API 요청 실패: " + responseCode);
            }

        } catch (Exception e) {
            System.err.println("[ERROR] API 요청 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }

        return performanceList;
    }

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

    public PerformanceDTO getPerformanceById(String id) {
        PerformanceDTO performance = null;

        try {
            String apiKey = ConfigUtil.getApiKey();
            String apiUrl = "http://kopis.or.kr/openApi/restful/pblprfr/" + id + "?service=" + apiKey;

            System.out.println("[INFO] API 요청 URL: " + apiUrl);

            URL url = new URL(apiUrl);
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

                if (nodeList.getLength() > 0) {
                    Node node = nodeList.item(0);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;

                        performance = new PerformanceDTO();
                        performance.setId(id);
                        performance.setTitle(getTagValue("prfnm", element));
                        performance.setStartDate(getTagValue("prfpdfrom", element));
                        performance.setEndDate(getTagValue("prfpdto", element));
                        performance.setLocation(getTagValue("fcltynm", element));
                        performance.setGenre(getTagValue("genrenm", element));
                        performance.setPosterUrl(getTagValue("poster", element));

                        System.out.println("[INFO] 공연 데이터 수신 완료: " + performance.getTitle());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("[ERROR] 공연 조회 중 오류: " + e.getMessage());
            e.printStackTrace();
        }

        return performance;
    }
    
    public List<PerformanceDTO> getPerformancesByIds(String[] ids) {
        return performanceDAO.findByIds(ids);
    }

}
