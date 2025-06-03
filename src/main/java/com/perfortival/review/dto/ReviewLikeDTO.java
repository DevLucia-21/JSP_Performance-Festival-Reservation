package com.perfortival.review.dto;

public class ReviewLikeDTO {
    private int id;
    private int reviewId;
    private String memberId;
    private boolean isLike;

    // Getter / Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getReviewId() {
        return reviewId;
    }
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getMemberId() {
        return memberId;
    }
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public boolean isLike() {
        return isLike;
    }
    public void setLike(boolean isLike) {
        this.isLike = isLike;
    }
}
