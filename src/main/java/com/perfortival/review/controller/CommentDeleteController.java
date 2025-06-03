package com.perfortival.review.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.perfortival.common.db.DBUtil;
import com.perfortival.review.dao.CommentDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comment/delete")
public class CommentDeleteController extends HttpServlet {

    private CommentDAO commentDAO = new CommentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int commentId = Integer.parseInt(request.getParameter("id"));

        commentDAO.deleteComment(commentId);

        // 댓글 삭제 후 후기 목록으로 이동
        response.sendRedirect(request.getContextPath() + "/review/list");
    }
    
    public boolean deleteComment(int id) {
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
