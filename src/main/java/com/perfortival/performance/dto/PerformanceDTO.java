package com.perfortival.performance.dto;

public class PerformanceDTO {

    private String id;  
    private String title;
    private String startDate;
    private String endDate;
    private String location;
    private String genre;
    private boolean adminSelected;  // 관리자 선택 여부
    private String posterUrl;       

    // 기본 생성자
    public PerformanceDTO() {}

    // 생성자
    public PerformanceDTO(String id, String title, String startDate, String endDate, String location, String genre, boolean adminSelected, String posterUrl) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.genre = genre;
        this.adminSelected = adminSelected;
        this.posterUrl = posterUrl;
    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isAdminSelected() {
        return adminSelected;
    }

    public void setAdminSelected(boolean adminSelected) {
        this.adminSelected = adminSelected;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
}
