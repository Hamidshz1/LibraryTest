package test;
//Questo è un servlet Java che gestisce le richieste GET e POST a uno specifico
//pattern URL ("/admin").
//questo servlet consente agli amministratori di aggiungere nuovi utenti a un database
//tramite un'interfaccia web.
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(urlPatterns = "/admin")

public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/test";
	private final String USER = "root";
	private final String PASS = "Admin123";
//Il metodo doGet() viene chiamato quando una richiesta GET viene ricevuta dal servlet.
//	Controlla se l'utente ha effettuato l'accesso come amministratore e, in tal caso,
//	visualizza un modulo per aggiungere un nuovo utente
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("link.html").include(request, response);

		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("admin") != null) {
			out.println("<h2>Aggiungi nuovo utente</h2>");
			
		} else {
			out.println("<p>Bisogna effettuare il login come admin per accedere a questa pagina.</p>");
		}
		out.close();
	}
/*Il metodo doPost() viene chiamato quando una richiesta POST viene ricevuta dal servlet.
Controlla se l'utente ha effettuato l'accesso come amministratore e, in tal caso,
recupera i dati dell'utente dai parametri della richiesta e aggiunge un nuovo utente al database.*/	

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		request.getRequestDispatcher("link.html").include(request, response);

		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("admin") != null) {
			String nome = request.getParameter("nome");
			String cognome = request.getParameter("cognome");
			String email = request.getParameter("email");

			try {
				Class.forName(JDBC_DRIVER);

				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

				String sql = "INSERT INTO utente (nome, cognome, email) VALUES (?, ?, ?)";
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, nome);
				stmt.setString(2, cognome);
				stmt.setString(3, email);
				stmt.executeUpdate();
				stmt.close();
				conn.close();

				out.println("<p>Utente aggiunto correttamente al database.</p>");

			} catch (ClassNotFoundException | SQLException e) {
				out.println("<p>Errore durante l'aggiunta dell'utente al database.</p>");
				e.printStackTrace();
			}

		} else {
			out.println("<p>Bisogna effettuare il login come admin per accedere a questa pagina.</p>");
		}
		out.close();
	}

}