package com.perfortival.performance.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.perfortival.common.config.ConfigUtil;
import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dao.PerformanceTimeDAO;
import com.perfortival.performance.dao.SeatDAO;
import com.perfortival.performance.dto.PerformanceDTO;
import com.perfortival.performance.dto.PerformanceTimeDTO;
import com.perfortival.performance.dto.SeatDTO;

public class PerformanceService {

    private static final String API_URL = "http://kopis.or.kr/openApi/restful/pblprfr?";
    private PerformanceDAO performanceDAO = new PerformanceDAO();
    private PerformanceTimeDAO timeDAO = new PerformanceTimeDAO();
    private SeatDAO seatDAO = new SeatDAO(); 

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

    public List<PerformanceTimeDTO> getTimesByPerformanceId(String performanceId) {
        return timeDAO.getTimesByPerformanceId(performanceId);
    }

    public List<SeatDTO> getSeatsByPerformanceId(String performanceId) {
        return new PerformanceDAO().getSeatsByPerformanceId(performanceId);
    }
    
    public PerformanceDTO getPerformanceByIdFromDB(String id) {
        PerformanceDTO performance = performanceDAO.getPerformanceById(id);

        if (performance != null && "자유석".equals(performance.getReservationType())) {
            Integer price = performanceDAO.getFreeSeatPrice(id); // 이건 DAO에 이미 만든 메서드
            if (price != null) {
                performance.setBasePrice(price);
            }
        }

        return performance;
    }
    
    public Integer getFreeSeatPrice(String performanceId) {
        return performanceDAO.getFreeSeatPrice(performanceId);
    }
    
    public void generateSeats(String performanceId, String reservationType, Map<String, Integer> priceMap) {
        SeatDAO seatDAO = new SeatDAO();
        seatDAO.deleteByPerformanceId(performanceId);

        if ("좌석A".equals(reservationType)) {
            // 1층 VIP석 (zone A~D, col: V1~V4)
            for (char zone = 'A'; zone <= 'D'; zone++) {
                for (int i = 1; i <= 4; i++) {
                    SeatDTO seat = new SeatDTO();
                    seat.setPerformanceId(performanceId);
                    seat.setSeatType("VIP");
                    seat.setFloor(1);
                    seat.setSection("1층");
                    seat.setZone(String.valueOf(zone));
                    seat.setRow("V");         
                    seat.setCol(String.valueOf(i));                  
                    System.out.println("[DEBUG] zone=" + zone + ", row=" + seat.getRow() + ", col=" + seat.getCol());
                    seat.setEntryNumber(i);
                    seat.setPrice(priceMap.get("VIP"));
                    seat.setColor("#0047AB");
                    seat.setReserved(false);
                    seatDAO.insertOrUpdate(seat);
                }
            }

            // 2층 일반석 (zone I~M, col: R1~R4)
            for (char zone = 'I'; zone <= 'M'; zone++) {
                for (int i = 1; i <= 4; i++) {
                    SeatDTO seat = new SeatDTO();
                    seat.setPerformanceId(performanceId);
                    seat.setSeatType("일반석");
                    seat.setFloor(2);
                    seat.setSection("2층");
                    seat.setZone(String.valueOf(zone));
                    seat.setRow("R");           
                    seat.setCol(String.valueOf(i));                     
                    seat.setEntryNumber(i);
                    seat.setPrice(priceMap.get("일반석"));
                    seat.setColor("#4682B4");
                    seat.setReserved(false);
                    seatDAO.insertOrUpdate(seat);
                }
            }

        } if ("좌석B".equals(reservationType)) {
            // 1층 - VIP석 (A1~6, B1~6)
            for (String row : List.of("A", "B")) {
                for (int col = 1; col <= 6; col++) {
                    SeatDTO seat = new SeatDTO();
                    seat.setPerformanceId(performanceId);
                    seat.setFloor(1);
                    seat.setZone(row);
                    seat.setRow("V");
                    seat.setCol(String.valueOf(col));
                    seat.setSeatType("VIP");
                    seat.setColor("#A7C7E7");
                    seat.setPrice(priceMap.get("VIP"));
                    seat.setReserved(false);
                    seatDAO.insertOrUpdate(seat);
                }
            }

            // 1층 - R석 (I1~6, J1~6)
            for (String row : List.of("I", "J")) {
                for (int col = 1; col <= 6; col++) {
                    SeatDTO seat = new SeatDTO();
                    seat.setPerformanceId(performanceId);
                    seat.setFloor(1);
                    seat.setZone(row);
                    seat.setRow("R");
                    seat.setCol(String.valueOf(col));
                    seat.setSeatType("R석");
                    seat.setColor("#D8B4F8");
                    seat.setPrice(priceMap.get("R석"));
                    seat.setReserved(false);
                    seatDAO.insertOrUpdate(seat);
                }
            }

            // 2층 - R석 (K1~6)
            for (String row : List.of("K")) {
                for (int col = 1; col <= 6; col++) {
                    SeatDTO seat = new SeatDTO();
                    seat.setPerformanceId(performanceId);
                    seat.setFloor(2);
                    seat.setZone(row);
                    seat.setRow("R");
                    seat.setCol(String.valueOf(col));
                    seat.setSeatType("R석");
                    seat.setColor("#D8B4F8");
                    seat.setPrice(priceMap.get("R석"));
                    seat.setReserved(false);
                    seatDAO.insertOrUpdate(seat);
                }
            }

            // 2층 - S석 (O1~6, P1~6, Q1~6)
            for (String row : List.of("O", "P", "Q")) {
                for (int col = 1; col <= 6; col++) {
                    SeatDTO seat = new SeatDTO();
                    seat.setPerformanceId(performanceId);
                    seat.setFloor(2);
                    seat.setZone(row);
                    seat.setRow("S");
                    seat.setCol(String.valueOf(col));
                    seat.setSeatType("S석");
                    seat.setColor("#F9F3A9");
                    seat.setPrice(priceMap.get("S석"));
                    seat.setReserved(false);
                    seatDAO.insertOrUpdate(seat);
                }
            }

        } else if ("혼합".equals(reservationType)) {
            // 1층 - 스탠딩 (Zone A~D, T1~T4)
            for (char zone = 'A'; zone <= 'D'; zone++) {
                for (int i = 1; i <= 4; i++) {
                    SeatDTO seat = new SeatDTO();
                    seat.setPerformanceId(performanceId);
                    seat.setSeatType("스탠딩");
                    seat.setFloor(1);
                    seat.setSection("1층");
                    seat.setZone(String.valueOf(zone));
                    seat.setRow("T"); // row = T
                    seat.setCol(String.valueOf(i)); 
                    seat.setEntryNumber(i);
                    seat.setPrice(priceMap.get("스탠딩"));
                    seat.setReserved(false);
                    seat.setColor("#FFE5D0");
                    seatDAO.insertOrUpdate(seat);
                }
            }

            // 2층 - 지정석 (Zone I~J, R1~R6)
            for (char zone = 'I'; zone <= 'J'; zone++) {
                for (int i = 1; i <= 6; i++) {
                    SeatDTO seat = new SeatDTO();
                    seat.setPerformanceId(performanceId);
                    seat.setSeatType("좌석");
                    seat.setFloor(2);
                    seat.setSection("2층");
                    seat.setZone(String.valueOf(zone));
                    seat.setRow("R");
                    seat.setCol(String.valueOf(i)); 
                    seat.setEntryNumber(i);
                    seat.setPrice(priceMap.get("좌석"));
                    seat.setReserved(false);
                    seat.setColor("#D3F4C3");
                    seatDAO.insertOrUpdate(seat);
                }
            }
        }

    }
} 