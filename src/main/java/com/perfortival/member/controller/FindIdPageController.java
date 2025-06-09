package com.perfortival.member.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/findIdPage")
public class FindIdPageController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // JSP 경로는 WEB-INF 안에 있으므로 반드시 포워딩
        request.getRequestDispatcher("/WEB-INF/views/member/findId.jsp").forward(request, response);
    }
}
