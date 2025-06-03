package com.perfortival.review.controller;

import java.io.IOException;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.review.dao.ReviewLikeDAO;
import com.perfortival.review.dto.ReviewLikeDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/review/like")
public class ReviewLikeController extends HttpServlet {

    private ReviewLikeDAO likeDAO = new ReviewLikeDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/member/login");
            return;
        }

        int reviewId = Integer.parseInt(request.getParameter("reviewId"));
        boolean isLike = Boolean.parseBoolean(request.getParameter("isLike"));
        String memberId = loginUser.getId();  // 🔥 추가

        ReviewLikeDTO dto = new ReviewLikeDTO();
        dto.setReviewId(reviewId);
        dto.setMemberId(memberId);
        dto.setLike(isLike);

        if (likeDAO.hasUserLiked(reviewId, memberId)) {
            if (likeDAO.isSameLike(reviewId, memberId, isLike)) {
                // 같은 버튼 → 해제
                likeDAO.removeLike(reviewId, memberId);
            } else {
                // 다른 버튼 → 업데이트
                likeDAO.updateLike(reviewId, memberId, isLike);
            }
        } else {
            // 처음 누름
            likeDAO.insertLike(reviewId, memberId, isLike);
        }

        response.sendRedirect(request.getContextPath() + "/review/list");
    }
}
