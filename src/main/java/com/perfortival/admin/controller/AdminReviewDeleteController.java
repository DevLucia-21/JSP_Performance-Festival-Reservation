package com.perfortival.admin.controller;

import java.io.IOException;

import com.perfortival.review.dao.ReviewDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/review/delete")
public class AdminReviewDeleteController extends HttpServlet {

    private ReviewDAO reviewDAO = new ReviewDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int reviewId = Integer.parseInt(request.getParameter("id"));
        reviewDAO.adminDeleteReview(reviewId);

        response.sendRedirect(request.getContextPath() + "/review/list");
    }
}
