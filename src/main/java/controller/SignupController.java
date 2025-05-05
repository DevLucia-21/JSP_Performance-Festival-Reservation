package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import model.dao.MemberDAO;
import model.dto.MemberDTO;

@WebServlet("/member/signup")
public class SignupController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String pw = request.getParameter("pw");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        MemberDTO dto = new MemberDTO(id, pw, name, email);
        MemberDAO dao = new MemberDAO();

        boolean success = dao.signup(dto);

        if (success) {
            response.sendRedirect("login.jsp");
        } else {
            response.sendRedirect("signup.jsp?error=1");
        }
    }
}
