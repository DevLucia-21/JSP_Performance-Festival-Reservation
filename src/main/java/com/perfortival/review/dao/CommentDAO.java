package com.perfortival.review.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.perfortival.common.db.DBUtil;
import com.perfortival.review.dto.CommentDTO;

public class CommentDAO {

	public List<CommentDTO> getCommentsByReviewId(int reviewId) {
	    List<CommentDTO> list = new ArrayList<>();
	    String sql = "SELECT * FROM comments WHERE review_id = ? ORDER BY created_at ASC";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, reviewId);
	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	            	CommentDTO dto = new CommentDTO();
	            	dto.setId(rs.getInt("id"));
	            	dto.setReviewId(rs.getInt("review_id"));
	            	dto.setMemberId(rs.getString("member_id"));  // ✅ 수정
	            	dto.setContent(rs.getString("content"));
	            	dto.setCreatedAt(rs.getTimestamp("created_at").toString());  // toString 처리도 추가
	            	dto.setIsDeleted(rs.getInt("is_deleted"));
	            	list.add(dto);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

    public void insertComment(CommentDTO dto) {
        String sql = "INSERT INTO comments (review_id, member_id, content) VALUES (?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, dto.getReviewId());
            pstmt.setString(2, dto.getMemberId());
            pstmt.setString(3, dto.getContent());
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteCommentsByReviewId(int reviewId) {
        String sql = "DELETE FROM comments WHERE review_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reviewId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 댓글 단건 조회
    public CommentDTO getCommentById(int id) {
        String sql = "SELECT * FROM comments WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                CommentDTO dto = new CommentDTO();
                dto.setId(rs.getInt("id"));
                dto.setReviewId(rs.getInt("review_id"));
                dto.setMemberId(rs.getString("member_id"));
                dto.setContent(rs.getString("content"));
                dto.setCreatedAt(rs.getString("created_at"));
                return dto;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // CommentDAO.java
    public void updateComment(int commentId, String content) {
        String sql = "UPDATE comments SET content = ? WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, content);
            pstmt.setInt(2, commentId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void deleteComment(int commentId) {
        String sql = "DELETE FROM comments WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, commentId);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 관리자용 댓글 논리 삭제 + 내용 대체
    public boolean adminDeleteComment(int commentId) {
        String sql = "UPDATE comments SET is_deleted = 1 WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, commentId);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean delete(int id) {
        String sql = "UPDATE comments SET is_deleted = 1 WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
