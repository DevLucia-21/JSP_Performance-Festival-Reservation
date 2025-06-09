package com.perfortival.member.controller;

import com.perfortival.member.dao.MemberDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/member/checkId")
public class CheckIdController extends HttpServlet {

    private MemberDAO memberDAO = new MemberDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("id");
        boolean isDuplicate = memberDAO.isDuplicateId(id);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String result = "{\"duplicate\": " + isDuplicate + "}";
        response.getWriter().write(result);
    }
}
