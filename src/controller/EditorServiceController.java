package controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import database.Account;
import database.Email;

public class EditorServiceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditorServiceController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String articleId = request.getParameter("id");
		String source = request.getParameter("source");

		if (source != null) {
			response.setContentType("application/pdf");
			// ServletContext ctx = getServletContext();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("user");
			if (user == null) {
				request.setAttribute("message",
						"You need to login to continue");
				request.getRequestDispatcher("/home.jsp")
						.forward(request, response);
			} else {
				try {

					String relativePath = getServletContext().getRealPath("")
							+ File.separator + "resources" + File.separator
							+ source;
					System.out.println(relativePath);
					File nfsPDF = new File(relativePath);
					FileInputStream fis;

					fis = new FileInputStream(nfsPDF);
					BufferedInputStream bis = new BufferedInputStream(fis);
					ServletOutputStream sos = response.getOutputStream();
					byte[] buffer = new byte[5120];
					while (true) {
						int bytesRead = bis.read(buffer, 0, buffer.length);
						if (bytesRead < 0) {
							break;
						}
						sos.write(buffer, 0, bytesRead);
						sos.flush();
					}
					sos.flush();
					bis.close();
				} catch (Exception e) {
					request.setAttribute("message", "pdf not found");
					try {
						request.getRequestDispatcher("/home.jsp").forward(
								request, response);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						request.setAttribute("message", "pdf not found");
						request.getRequestDispatcher("/home.jsp").forward(
								request, response);

					}
					e.printStackTrace();
				}
			}
		}else {
			request.getRequestDispatcher("/errorpage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hellowold");
		String firstname= request.getParameter("firstname");
		String lastname= request.getParameter("lastname");
		String email= request.getParameter("email");
		String message= request.getParameter("message");
		String hiddenMessage = request.getParameter("hiddenMessage");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
			conn = DriverManager.getConnection(DB);
			Account account = new Account(conn);
			if(hiddenMessage != null && hiddenMessage.equals("invitation")){
				System.out.println(email);
				
				if(account.exists(email)){
				
					request.setAttribute("emessage", "This Email Address already exists in the system!\nPlease choose a different email address");
				}else {
					Email mail = new Email();
					String password = mail.generatePassword();
					account.create(email, password, firstname, lastname);
					account.changeRole("editor", account.getUserId(email));
					mail.setSubject("Editor Invitation");
					mail.setBody("Dear "+firstname+",<br>You are invited to be an editor in our system.<br>Editor Message:<br>"+message+"<br> Your user name and password are:<br/></br>UserName:"+email+"<br>Password:"+password);
					mail.setRecipient(email);
					mail.sendEmail();
					request.setAttribute("message", "Invitation sent successfull!");
					
				}
				ArrayList<User> users = account
						.getUsersFromResultSet(account.getRecords());
				request.setAttribute("users", users);
				request.getRequestDispatcher("/views/registeredusers.jsp").forward(request, response);

				
				
				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
