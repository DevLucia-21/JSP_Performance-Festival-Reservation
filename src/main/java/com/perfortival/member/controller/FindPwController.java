package com.perfortival.member.controller;

import java.io.IOException;

import com.perfortival.member.dao.MemberDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/findPw")
public class FindPwController extends HttpServlet {

    private MemberDAO memberDAO = new MemberDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String id = request.getParameter("id");
        String email = request.getParameter("email");

        String foundPw = memberDAO.findPwByNameIdEmail(name, id, email);

        if (foundPw != null) {
            request.setAttribute("foundPw", foundPw);
        } else {
            request.setAttribute("error", "일치하는 회원 정보가 없습니다.");
        }

        request.getRequestDispatcher("/WEB-INF/views/member/findPwResult.jsp").forward(request, response);
    }
}
