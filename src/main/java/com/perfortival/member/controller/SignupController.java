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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 회원가입 페이지로 포워딩 (JSP 경로)
        request.getRequestDispatcher("/WEB-INF/views/member/signup.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String pw = request.getParameter("pw");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // 회원가입 시 관리자 여부는 기본적으로 false로 설정
        boolean isAdmin = false;

        // 새로운 생성자에 맞춰서 객체 생성
        MemberDTO dto = new MemberDTO(id, pw, name, email, isAdmin);
        MemberDAO dao = new MemberDAO();

        boolean success = dao.signup(dto);

        if (success) {
            // 로그인 페이지로 리다이렉트 (서블릿 경로 사용)
            response.sendRedirect(request.getContextPath() + "/member/login");
        } else {
            // 회원가입 페이지로 리다이렉트 (에러 파라미터 포함)
            response.sendRedirect(request.getContextPath() + "/member/signup?error=1");
        }
    }
}
