package com.perfortival.review.controller;

import java.io.IOException;

import com.perfortival.review.dao.CommentDAO;
import com.perfortival.review.dao.ReviewDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/review/delete")
public class ReviewDeleteController extends HttpServlet {

    private ReviewDAO reviewDAO = new ReviewDAO();
    private CommentDAO commentDAO = new CommentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reviewIdStr = request.getParameter("reviewId");

        if (reviewIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/review/list");
            return;
        }

        int reviewId = Integer.parseInt(reviewIdStr);

        // 1. 해당 후기의 댓글 먼저 삭제
        commentDAO.deleteCommentsByReviewId(reviewId);

        // 2. 후기 삭제
        reviewDAO.deleteReview(reviewId);

        // 3. 목록 페이지로 리다이렉트
        response.sendRedirect(request.getContextPath() + "/review/list");
    }
}
