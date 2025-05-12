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

@WebServlet("/member/update")
public class UpdateMemberController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 업데이트 페이지로 포워딩
        request.getRequestDispatcher("/WEB-INF/views/member/updateMember.jsp").forward(request, response);
    }
    
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

        String id = loginUser.getId();  // 로그인된 사용자 ID는 고정
        String pw = request.getParameter("pw");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        boolean isAdmin = loginUser.isAdmin();  // 기존 세션에서 isAdmin 값을 가져옴

        // MemberDTO 객체 생성 시 isAdmin 필드도 포함시킴
        MemberDTO updateUser = new MemberDTO(id, pw, name, email, isAdmin);
        boolean success = new MemberDAO().update(updateUser);

        if (success) {
            // 세션 업데이트
            session.setAttribute("loginUser", updateUser);

            // 마이페이지로 리다이렉트 (서블릿 경로)
            response.sendRedirect(request.getContextPath() + "/member/mypage");
        } else {
            // 업데이트 페이지로 리다이렉트 (에러 파라미터 포함)
            response.sendRedirect(request.getContextPath() + "/member/update?error=1");
        }
    }
}
