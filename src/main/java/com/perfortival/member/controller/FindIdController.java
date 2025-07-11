package com.perfortival.member.controller;

import java.io.IOException;

import com.perfortival.member.dao.MemberDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/member/findId")
public class FindIdController extends HttpServlet {

    private MemberDAO memberDAO = new MemberDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // DB에서 해당 이름 + 이메일로 아이디 찾기
        String foundId = memberDAO.findIdByNameAndEmail(name, email);

        if (foundId != null) {
            request.setAttribute("foundId", foundId);
        } else {
            request.setAttribute("error", "일치하는 회원 정보가 없습니다.");
        }

        request.getRequestDispatcher("/WEB-INF/views/member/findIdResult.jsp").forward(request, response);
    }
}
