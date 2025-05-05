package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import model.dao.MemberDAO;
import model.dto.MemberDTO;

@WebServlet("/member/update")
public class UpdateMemberController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String id = loginUser.getId(); // 로그인된 ID로 고정
        String pw = request.getParameter("pw");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        MemberDTO updateUser = new MemberDTO(id, pw, name, email);
        boolean success = new MemberDAO().update(updateUser);

        if (success) {
            session.setAttribute("loginUser", updateUser); // 세션 업데이트
            response.sendRedirect("mypage.jsp");
        } else {
            response.sendRedirect("updateMember.jsp?error=1");
        }
    }
}
