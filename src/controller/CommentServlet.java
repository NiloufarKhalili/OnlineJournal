package controller;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;

import com.mysql.jdbc.Connection;

import database.ArticleTable;
import database.CommentDB;
import database.Form;

/**
 * Servlet implementation class CommentServlet
 */
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (session.getAttribute("user") == null) {
			request.setAttribute("message", "You need to login the system");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);
		} else if (user.getRole().equals("reader")) {
			request.setAttribute("message",
					"You logged is as a reader. You do not have permission to open the page");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);
		} else {

			String content = request.getParameter("content");
			int reason_id =-1;
			try {
				reason_id = Integer.valueOf(request.getParameter("reason_id"));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("wrong parameter reason_id");
			}
			Connection conn = null;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
				try {
					conn = (Connection) DriverManager.getConnection(DB);
					CommentDB commentDB = new CommentDB(conn);
					commentDB.insertIntoComments(reason_id, content);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

		}
	}

}
