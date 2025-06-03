package com.perfortival.review.controller;

import java.io.IOException;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.review.dao.ReviewDAO;
import com.perfortival.review.dto.ReviewDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/review/edit")
public class ReviewEditController extends HttpServlet {

    private ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reviewId = request.getParameter("id");
        if (reviewId == null) {
            response.sendRedirect(request.getContextPath() + "/review/list");
            return;
        }

        ReviewDTO review = reviewDAO.getReviewById(Integer.parseInt(reviewId));
        if (review == null) {
            response.sendRedirect(request.getContextPath() + "/review/list");
            return;
        }

        HttpSession session = request.getSession();
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser == null || !loginUser.getId().equals(review.getMemberId())) {
            response.sendRedirect(request.getContextPath() + "/review/list");
            return;
        }

        request.setAttribute("review", review);
        request.getRequestDispatcher("/WEB-INF/views/review/review_edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int rating = Integer.parseInt(request.getParameter("rating"));

        ReviewDTO review = new ReviewDTO();
        review.setId(id);
        review.setTitle(title);
        review.setContent(content);
        review.setRating(rating);

        reviewDAO.updateReview(review);
        response.sendRedirect(request.getContextPath() + "/review/list");
    }
}
