package test;
//Questo è un servlet Java che gestisce l'accesso dell'utente. 
//le richieste POST sul pattern URL "/login" e recupera il nome utente 
//la password dai parametri della richiesta.
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("nomeutente");
		System.out.println(name);
		String password = request.getParameter("pw");


		if (name.equals("admin") && password.equals("password")) {
			HttpSession session = request.getSession(true);
			session.setAttribute("username", name);
			System.out.println("Access Done");

			response.sendRedirect(request.getContextPath() + "/admin");
		} else {
			System.out.println("Access Failed");
			request.setAttribute("errorMessage", "Invalid username or password");
			request.getRequestDispatcher("login.html").forward(request, response);
		}
	}
}