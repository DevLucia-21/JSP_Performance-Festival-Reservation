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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        String id = loginUser.getId();
        String pw = request.getParameter("pw");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String address = request.getParameter("address"); 
        boolean isAdmin = loginUser.isAdmin();

        MemberDTO updateUser = new MemberDTO(id, pw, name, email, isAdmin);
        updateUser.setAddress(address); 

        boolean success = new MemberDAO().update(updateUser);

        if (success) {
            session.setAttribute("loginUser", updateUser);
            response.sendRedirect(request.getContextPath() + "/member/mypage");
        } else {
            response.sendRedirect(request.getContextPath() + "/member/update?error=1");
        }
    }
}
