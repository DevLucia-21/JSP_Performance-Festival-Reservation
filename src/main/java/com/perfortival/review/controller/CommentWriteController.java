package com.perfortival.review.controller;

import com.perfortival.member.dto.MemberDTO;
import com.perfortival.review.dao.CommentDAO;
import com.perfortival.review.dto.CommentDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/comment/write")
public class CommentWriteController extends HttpServlet {
    private CommentDAO commentDAO = new CommentDAO();

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
        String content = request.getParameter("content");

        CommentDTO dto = new CommentDTO();
        dto.setReviewId(reviewId);
        dto.setMemberId(loginUser.getId());
        dto.setContent(content);

        commentDAO.insertComment(dto);
        response.sendRedirect(request.getContextPath() + "/review/list");
    }
}
