package com.perfortival.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.perfortival.common.db.DBUtil;
import com.perfortival.review.dto.ReviewLikeDTO;

public class ReviewLikeDAO {

    // 좋아요/싫어요 등록 (중복 확인 필요)
    public boolean insertLike(int reviewId, String memberId, boolean isLike) {
        String insertSql = "INSERT INTO review_likes (review_id, member_id, is_like) VALUES (?, ?, ?)";
        String updateCount = isLike ?
                "UPDATE reviews SET likes = likes + 1 WHERE id = ?" :
                "UPDATE reviews SET dislikes = dislikes + 1 WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateCount)) {

            insertStmt.setInt(1, reviewId);
            insertStmt.setString(2, memberId);
            insertStmt.setBoolean(3, isLike);
            insertStmt.executeUpdate();

            updateStmt.setInt(1, reviewId);
            updateStmt.executeUpdate();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 사용자 좋아요 기록 있는지 확인
    public boolean hasUserLiked(int reviewId, String memberId) {
        String sql = "SELECT 1 FROM review_likes WHERE review_id = ? AND member_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reviewId);
            pstmt.setString(2, memberId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 현재 기록된 상태와 같은지 (true: 같음 → toggle)
    public boolean isSameLike(int reviewId, String memberId, boolean isLike) {
        String sql = "SELECT is_like FROM review_likes WHERE review_id = ? AND member_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reviewId);
            pstmt.setString(2, memberId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("is_like") == isLike;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 좋아요/싫어요 삭제 (토글 해제)
    public void removeLike(int reviewId, String memberId) {
        String checkSql = "SELECT is_like FROM review_likes WHERE review_id = ? AND member_id = ?";
        String deleteSql = "DELETE FROM review_likes WHERE review_id = ? AND member_id = ?";
        String updateCount = "UPDATE reviews SET likes = likes - 1 WHERE id = ?";
        String updateDislike = "UPDATE reviews SET dislikes = dislikes - 1 WHERE id = ?";

        try (Connection conn = DBUtil.getConnection()) {
            boolean isLike = true; // 기본값

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, reviewId);
                checkStmt.setString(2, memberId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    isLike = rs.getBoolean("is_like");
                }
            }

            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                deleteStmt.setInt(1, reviewId);
                deleteStmt.setString(2, memberId);
                deleteStmt.executeUpdate();
            }

            try (PreparedStatement updateStmt = conn.prepareStatement(isLike ? updateCount : updateDislike)) {
                updateStmt.setInt(1, reviewId);
                updateStmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 반대 상태로 업데이트 (좋아요 → 싫어요 or 반대)
    public void updateLike(int reviewId, String memberId, boolean isLike) {
        String updateSql = "UPDATE review_likes SET is_like = ? WHERE review_id = ? AND member_id = ?";
        String inc = isLike ? "likes" : "dislikes";
        String dec = isLike ? "dislikes" : "likes";

        String updateInc = "UPDATE reviews SET " + inc + " = " + inc + " + 1 WHERE id = ?";
        String updateDec = "UPDATE reviews SET " + dec + " = " + dec + " - 1 WHERE id = ?";

        try (Connection conn = DBUtil.getConnection()) {
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setBoolean(1, isLike);
                pstmt.setInt(2, reviewId);
                pstmt.setString(3, memberId);
                pstmt.executeUpdate();
            }

            try (PreparedStatement incStmt = conn.prepareStatement(updateInc);
                 PreparedStatement decStmt = conn.prepareStatement(updateDec)) {

                incStmt.setInt(1, reviewId);
                decStmt.setInt(1, reviewId);
                incStmt.executeUpdate();
                decStmt.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
