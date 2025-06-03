package com.perfortival.review.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.performance.dao.PerformanceDAO;
import com.perfortival.review.dao.ReviewDAO;
import com.perfortival.review.dto.ReviewDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/review/write")
public class ReviewWriteController extends HttpServlet {

    private ReviewDAO reviewDAO = new ReviewDAO();
    private PerformanceDAO performanceDAO = new PerformanceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List performances = performanceDAO.getAllPerformances();
        request.setAttribute("performanceList", performances);
        request.getRequestDispatcher("/WEB-INF/views/review/review_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/member/login?error=loginRequired");
            return;
        }

        String memberId = loginUser.getId();
        String performanceId = request.getParameter("performanceId");
        String reviewType = request.getParameter("reviewType");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        // 공연 후 후기일 경우, 조건 검사
        if ("후".equals(reviewType)) {
            boolean isEnded = reviewDAO.isPerformanceEnded(performanceId);
            boolean hasReservation = reviewDAO.hasUserReservedPerformance(memberId, performanceId);

            if (!isEnded || !hasReservation) {
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('작성 권한이 없습니다'); location.href='" + request.getContextPath() + "/review/write';</script>");
                out.close();
                return;
            }
        }

        ReviewDTO dto = new ReviewDTO(memberId, performanceId, reviewType, rating, title, content);
        reviewDAO.insertReview(dto);

        response.sendRedirect(request.getContextPath() + "/review/list");
    }
}
