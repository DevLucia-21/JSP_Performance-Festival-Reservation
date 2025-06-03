package com.perfortival.admin.controller;

import java.io.IOException;

import com.perfortival.review.dao.CommentDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/admin/comment/delete")
public class AdminCommentDeleteController extends HttpServlet {

    private CommentDAO commentDAO = new CommentDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int commentId = Integer.parseInt(request.getParameter("id"));
        commentDAO.adminDeleteComment(commentId); // 관리자 삭제용 메서드 호출

        response.sendRedirect(request.getContextPath() + "/review/list");
    }
}
