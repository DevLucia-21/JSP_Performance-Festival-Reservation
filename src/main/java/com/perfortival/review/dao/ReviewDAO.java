package com.perfortival.review.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.common.db.DBUtil;
import com.perfortival.review.dto.CommentDTO;
import com.perfortival.review.dto.ReviewDTO;

public class ReviewDAO {

    public void insertReview(ReviewDTO dto) {
        String sql = "INSERT INTO reviews (member_id, performance_id, review_type, rating, title, content) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.getMemberId());
            pstmt.setString(2, dto.getPerformanceId());
            pstmt.setString(3, dto.getReviewType());
            pstmt.setInt(4, dto.getRating());
            pstmt.setString(5, dto.getTitle());
            pstmt.setString(6, dto.getContent());

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ReviewDTO> getAllReviews() {
        List<ReviewDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE is_deleted = 0 ORDER BY created_at DESC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ReviewDTO dto = new ReviewDTO();
                dto.setId(rs.getInt("id"));
                dto.setTitle(rs.getString("title"));
                dto.setContent(rs.getString("content"));
                dto.setMemberId(rs.getString("member_id"));
                dto.setCreatedAt(rs.getTimestamp("created_at").toString());
                dto.setRating(rs.getInt("rating"));
                dto.setIsDeleted(rs.getInt("is_deleted"));
                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean isPerformanceEnded(String performanceId) {
        String sql = "SELECT end_date FROM performances WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, performanceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Date endDate = rs.getDate("end_date");
                    return endDate.before(new java.sql.Date(System.currentTimeMillis()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasUserReservedPerformance(String memberId, String performanceId) {
        String sql = "SELECT COUNT(*) FROM reservations WHERE member_id = ? AND performance_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, memberId);
            pstmt.setString(2, performanceId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<ReviewDTO> getFilteredReviews(String performanceId, String reviewType, String sort) {
        List<ReviewDTO> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT r.*, p.title AS performanceTitle FROM reviews r JOIN performances p ON r.performance_id = p.id");

        if (performanceId != null && !performanceId.isEmpty()) {
            sql.append(" AND r.performance_id = ?");
        }
        if (reviewType != null && !reviewType.isEmpty()) {
            sql.append(" AND r.review_type = ?");
        }

        if ("likes".equals(sort)) {
            sql.append(" ORDER BY r.likes DESC, r.created_at DESC");
        } else {
            sql.append(" ORDER BY r.created_at DESC");
        }

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (performanceId != null && !performanceId.isEmpty()) {
                pstmt.setString(index++, performanceId);
            }
            if (reviewType != null && !reviewType.isEmpty()) {
                pstmt.setString(index++, reviewType);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ReviewDTO dto = new ReviewDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setMemberId(rs.getString("member_id"));
                    dto.setPerformanceId(rs.getString("performance_id"));
                    dto.setReviewType(rs.getString("review_type"));
                    dto.setRating(rs.getInt("rating"));
                    dto.setTitle(rs.getString("title"));
                    dto.setContent(rs.getString("content"));
                    dto.setLikes(rs.getInt("likes"));
                    dto.setDislikes(rs.getInt("dislikes"));
                    dto.setCreatedAt(rs.getString("created_at"));
                    dto.setPerformanceTitle(rs.getString("performanceTitle"));
                    dto.setIsDeleted(rs.getInt("is_deleted"));

                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateReview(ReviewDTO dto) {
        String sql = "UPDATE reviews SET title = ?, content = ?, rating = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, dto.getTitle());
            pstmt.setString(2, dto.getContent());
            pstmt.setInt(3, dto.getRating());
            pstmt.setInt(4, dto.getId());

            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reviewId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 관리자 삭제 - 내용 대체 및 논리 삭제 처리
    public boolean adminDeleteReview(int reviewId) {
        String sql = "UPDATE reviews SET is_deleted = 1 WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ReviewDTO getReviewById(int reviewId) {
        String sql = "SELECT * FROM reviews WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reviewId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ReviewDTO dto = new ReviewDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setMemberId(rs.getString("member_id"));
                    dto.setPerformanceId(rs.getString("performance_id"));
                    dto.setReviewType(rs.getString("review_type"));
                    dto.setRating(rs.getInt("rating"));
                    dto.setTitle(rs.getString("title"));
                    dto.setContent(rs.getString("content"));
                    dto.setLikes(rs.getInt("likes"));
                    dto.setDislikes(rs.getInt("dislikes"));
                    dto.setCreatedAt(rs.getTimestamp("created_at").toString());
                    dto.setIsDeleted(rs.getInt("is_deleted"));
                    return dto;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 댓글 조회
    public List<CommentDTO> getCommentsByReviewId(int reviewId) {
        List<CommentDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE review_id = ? AND is_deleted = 0 ORDER BY created_at ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reviewId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CommentDTO dto = new CommentDTO();
                    dto.setId(rs.getInt("id"));
                    dto.setReviewId(rs.getInt("review_id"));
                    dto.setMemberId(rs.getString("member_id"));
                    dto.setContent(rs.getString("content"));
                    dto.setCreatedAt(rs.getTimestamp("created_at").toString());
                    dto.setIsDeleted(rs.getInt("is_deleted"));
                    list.add(dto);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
