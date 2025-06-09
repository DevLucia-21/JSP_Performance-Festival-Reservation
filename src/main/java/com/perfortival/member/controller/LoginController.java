package com.perfortival.member.controller;

import java.io.IOException;

import com.perfortival.member.dao.MemberDAO;
import com.perfortival.member.dto.MemberDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/member/login")
public class LoginController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 로그인 페이지로 포워딩
        request.getRequestDispatcher("/WEB-INF/views/member/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String pw = request.getParameter("pw");

        MemberDAO dao = new MemberDAO();
        MemberDTO member = dao.login(id, pw);

        if (member != null) {
            // 로그인 성공 시 세션에 사용자 정보 저장
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", member); 
            session.setAttribute("isAdmin", member.isAdmin());

            // 세션 유효 시간 1시간
            session.setMaxInactiveInterval(60 * 60);

            response.sendRedirect(request.getContextPath() + "/main");
        } else {
            // 로그인 실패 시 로그인 페이지로 이동 + 에러 파라미터 포함
            response.sendRedirect(request.getContextPath() + "/member/login?error=1");
        }
    }
}
