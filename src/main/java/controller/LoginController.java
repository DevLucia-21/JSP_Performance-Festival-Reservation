package controller;

import java.io.IOException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.MemberDAO;
import model.dto.MemberDTO;

@WebServlet("/member/login")
public class LoginController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String pw = request.getParameter("pw");

        MemberDAO dao = new MemberDAO();
        MemberDTO member = dao.login(id, pw);  // login 메서드는 DAO에 구현할 예정

        if (member != null) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", member);
            
            // 세션 유효 시간 30분으로 설정
            session.setMaxInactiveInterval(60 * 60);  // 초 단위 (1시간)
            
            response.sendRedirect(request.getContextPath() + "/main.jsp");  // 성공 시 이동할 메인 페이지
        } else {
            response.sendRedirect("login.jsp?error=1");  // 실패 시 다시 로그인
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}

