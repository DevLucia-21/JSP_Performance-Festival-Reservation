package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false); // 세션이 있으면 가져오고, 없으면 null
        if (session != null) {
            session.invalidate(); // 세션 무효화 (로그아웃)
        }
        response.sendRedirect("member/login.jsp"); // 로그인 페이지로 리다이렉트
    }
}
