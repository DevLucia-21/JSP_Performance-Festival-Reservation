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

@WebServlet("/member/withdraw")
public class WithdrawController extends HttpServlet {
	
	@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 탈퇴 페이지로 포워딩
		request.getRequestDispatcher("/WEB-INF/views/member/withdraw.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	
	    request.setCharacterEncoding("UTF-8");
	    HttpSession session = request.getSession();
	
	    MemberDTO member = (MemberDTO) session.getAttribute("loginUser");
	    String inputPw = request.getParameter("pw");
	
	    if (member != null && inputPw.equals(member.getPw())) {
	        MemberDAO dao = new MemberDAO();
	        boolean success = dao.deleteMember(member.getId());
	
	        if (success) {
	            // 탈퇴 성공 - 세션 무효화 후 로그인 페이지로 리다이렉트
	            session.invalidate();
	            response.sendRedirect(request.getContextPath() + "/member/login");
	        } else {
	            // 탈퇴 실패 - 에러 파라미터 추가 후 다시 탈퇴 페이지로
	            response.sendRedirect(request.getContextPath() + "/member/withdraw?error=1");
	        }
	    } else {
	        // 비밀번호 불일치 - 에러 파라미터 추가 후 탈퇴 페이지로
	        response.sendRedirect(request.getContextPath() + "/member/withdraw?error=1");
	    }
	}
}
