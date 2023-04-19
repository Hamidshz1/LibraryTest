package test;
//Si tratta di classi servlet Java per un sistema di accesso di base
//con gestione delle sessioni in un'applicazione web.
import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);	

		if (session != null) {
			session.invalidate();
		}
		PrintWriter out = response.getWriter();
		out.println("You are now Logged out ");


//		response.sendRedirect(request.getContextPath() + "/login");
	}
}