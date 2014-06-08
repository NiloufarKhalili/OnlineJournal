package controller;

/**
 * 
 */
import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import database.Account;
import database.Email;

/**
 * Servlet implementation class Controller Author Iman Rastkhadiv
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;

	/**
	 * Default constructor.
	 */
	public Login() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		if (request.getParameter("action").equals("logout")) {
			request.getSession().removeAttribute("user");
			request.getSession().removeAttribute("sometih");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);
		}
//		if (request.getParameter("action").equals("users")) {
//			doPost(request, response);
//			// request.getRequestDispatcher("/home.jsp");
//		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		if (action == null) {
			out.println("Unrecognised action!");
			return;
		}

		// Class.forName("org.gjt.mm.mysql.Driver").newInstance();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
			conn = DriverManager.getConnection(DB);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Account account = new Account(conn);

		if (action.equals("login")) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			//User user = new User(email, password);

//			request.setAttribute(email, "email");
//			request.setAttribute(password, "");
			try {
				if (account.login(email, password)) {
					HttpSession session = request.getSession();
					// njy session
					session.setAttribute("user", account.getUserByEmail(email));
					request.getRequestDispatcher("/home.jsp").forward(request,
							response);
				} else {
					request.setAttribute("message",
							"Invalid email or password!");
					request.getRequestDispatcher("/home.jsp").forward(request,
							response);

				}
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		} else if (action.equals("createaccount")) {
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String confirmpassword = request.getParameter("confirmpassword");

			request.setAttribute("email", email);
			request.setAttribute(password, "");
			request.setAttribute(confirmpassword, "");

			try {
				if (account.exists(email)) {
					HttpSession session = request.getSession();
					if(session.getAttribute("user") != null)
					session.removeAttribute("user");
					request.setAttribute("message",
							"An account already exists with this email!");
					request.getRequestDispatcher("/views/register.jsp")
							.forward(request, response);
				} else {

					account.create(email, confirmpassword, firstname, lastname);
					HttpSession session = request.getSession();
					if (account.login(email, password))
						request.setAttribute("info",
								"Thank you for registering<br/>An email has been sent to you");
					// njy change session
					session.setAttribute("user", account.getUserByEmail(email));
					
//					session.setAttribute("user", new User(email, password));
					request.getRequestDispatcher("/home.jsp").forward(request,
							response);
					User user = account.getUserByEmail(email);
					Email mail = new Email(email, "Registeration",
							"Dear "+user.getFirstname()+"<br/>Thank you for registering");
					mail.sendEmail();
				}

			} catch (SQLException e1) {
				System.out.println(e1.getMessage());
			} catch (AddressException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}

		}

	}

}