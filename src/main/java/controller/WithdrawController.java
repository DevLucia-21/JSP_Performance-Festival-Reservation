package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.MemberDAO;
import model.dto.MemberDTO;

@WebServlet("/member/withdraw")
public class WithdrawController extends HttpServlet {
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
                session.invalidate(); // 세션 무효화
                response.sendRedirect(request.getContextPath() + "/member/login.jsp");
            } else {
                response.sendRedirect("withdraw.jsp?error=1"); // 삭제 실패
            }
        } else {
            response.sendRedirect("withdraw.jsp?error=1"); // 비밀번호 불일치
        }
    }
}
