package com.perfortival.review.controller;

import java.io.IOException;

import com.perfortival.review.dao.CommentDAO;
import com.perfortival.review.dto.CommentDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/comment/edit")
public class CommentEditController extends HttpServlet {

    private CommentDAO commentDAO = new CommentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int commentId = Integer.parseInt(request.getParameter("id"));
        CommentDTO comment = commentDAO.getCommentById(commentId);

        request.setAttribute("comment", comment);
        request.getRequestDispatcher("/WEB-INF/views/review/comment_edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int commentId = Integer.parseInt(request.getParameter("id"));
        String content = request.getParameter("content");

        commentDAO.updateComment(commentId, content);

        response.sendRedirect(request.getContextPath() + "/review/list");
    }
}

