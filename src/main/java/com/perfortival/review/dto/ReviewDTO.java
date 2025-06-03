package com.perfortival.review.dto;

public class ReviewDTO {
    private int id;
    private String memberId;
    private String performanceId;
    private String reviewType;       // 공연 전/후 ('전' or '후')
    private int rating;              // 별점 (1~5)
    private String title;
    private String content;
    private int likes;               // 좋아요 수
    private int dislikes;            // 싫어요 수
    private String createdAt;
    private int isDeleted;

    // (리스트 출력 시 공연 제목용 - 조인 결과를 담기 위한 용도)
    private String performanceTitle;

    public ReviewDTO() {}

    public ReviewDTO(String memberId, String performanceId, String reviewType, int rating, String title, String content) {
        this.memberId = memberId;
        this.performanceId = performanceId;
        this.reviewType = reviewType;
        this.rating = rating;
        this.title = title;
        this.content = content;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPerformanceId() {
        return performanceId;
    }
    public void setPerformanceId(String performanceId) {
        this.performanceId = performanceId;
    }

    public String getReviewType() {
        return reviewType;
    }
    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }

    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public int getLikes() {
        return likes;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }
    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPerformanceTitle() {
        return performanceTitle;
    }
    public void setPerformanceTitle(String performanceTitle) {
        this.performanceTitle = performanceTitle;
    }
    public int getIsDeleted() {
        return isDeleted;
    }
    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }
}
