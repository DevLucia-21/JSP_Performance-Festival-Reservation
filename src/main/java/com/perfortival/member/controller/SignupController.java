package com.perfortival.member.controller;

import java.io.IOException;

import com.perfortival.member.dao.MemberDAO;
import com.perfortival.member.dto.MemberDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/signup")
public class SignupController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String pw = request.getParameter("pw");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address");

        // 회원가입 시 관리자 여부는 기본적으로 false로 설정
        boolean isAdmin = false;

        MemberDTO dto = new MemberDTO(id, pw, name, email, isAdmin);
        dto.setAddress(address); 

        MemberDAO dao = new MemberDAO();
        boolean success = dao.signup(dto);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/member/login");
        } else {
            response.sendRedirect(request.getContextPath() + "/member/signup?error=1");
        }
    }
}
