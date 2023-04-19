package test;

/*Questo è un servlet Java che gestisce la creazione di nuovi utenti.
 le richieste POST nel pattern URL "/add" ed estrae il nome, il cognome,
l'e-mail e la password dell'utente dai parametri della richiesta. Quindi inserisce un
nuovo record nella tabella "utente" in un database MySQL utilizzando JDBC.*/

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet(urlPatterns = "/add")
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;

//	Il servlet ha un metodo "init()" che inizializza una connessione al database.
	
	public void init(ServletConfig config) {
		try {
			ServletContext context = config.getServletContext();
			
			System.out.println("init()");

			Enumeration<String> parameterNames = context.getInitParameterNames();

			while (parameterNames.hasMoreElements()) {
				String eachName = parameterNames.nextElement();
				System.out.println("Nome parametro: " + eachName);
				System.out.println("Valore parametro: " + context.getInitParameter(eachName));
			}

			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(context.getInitParameter("dbUrl"),
					context.getInitParameter("dbUser"), context.getInitParameter("dbPassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		System.out.println("doPost()");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		try {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate("INSERT INTO user(firstname,lastname,email,password) VALUES('" + firstName + "','" + lastName + "','"
					+ email + "','" + password + "')");
			PrintWriter out = response.getWriter();
			if (result > 0) {
				out.print("<H1>HAI CREATO UN NUOVO UTENTE</H1>"); 
			} else {
				out.print("<H1>ERRORE NELLA CREAZIONE</H1>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
//	Il servlet ha anche un metodo "destroy()" che chiude la connessione al database
//	quando il servlet viene scaricato dal servlet container.

	public void destroy() {
		try {
			System.out.println("destroy()");
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}