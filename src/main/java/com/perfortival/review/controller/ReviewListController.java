package com.perfortival.review.controller;

import com.perfortival.review.dao.ReviewDAO;
import com.perfortival.review.dao.CommentDAO;
import com.perfortival.review.dto.ReviewDTO;
import com.perfortival.review.dto.CommentDTO;
import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.performance.dto.PerformanceDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet("/review/list")
public class ReviewListController extends HttpServlet {

    private ReviewDAO reviewDAO = new ReviewDAO();
    private CommentDAO commentDAO = new CommentDAO();
    private PerformanceDAO performanceDAO = new PerformanceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String performanceId = request.getParameter("performanceId");
        String reviewType = request.getParameter("reviewType");
        String sort = request.getParameter("sort");

        List<ReviewDTO> reviewList = reviewDAO.getFilteredReviews(performanceId, reviewType, sort);
        List<PerformanceDTO> performanceList = performanceDAO.getAllPerformances();

        // 각 후기의 댓글 리스트 매핑
        Map<Integer, List<CommentDTO>> commentMap = new HashMap<>();
        for (ReviewDTO review : reviewList) {
            List<CommentDTO> comments = commentDAO.getCommentsByReviewId(review.getId());
            commentMap.put(review.getId(), comments);
        }

        request.setAttribute("reviewList", reviewList);
        request.setAttribute("performanceList", performanceList);
        request.setAttribute("commentMap", commentMap);
        request.getRequestDispatcher("/WEB-INF/views/review/review_list.jsp").forward(request, response);
    }
}
