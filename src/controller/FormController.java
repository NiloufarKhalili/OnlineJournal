package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Article;
import beans.Reason;
import beans.ReviewForm;
import beans.User;
import database.CommentDB;
import database.Form;


public class FormController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FormController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		ArrayList<Article> articles = (ArrayList<Article>) session
				.getAttribute("articles");
		System.out.println(articles);
		User user = (User) session.getAttribute("user");
		System.out.println(user);
		if (user == null) {
			request.setAttribute("message",
					"You need to login to view your articles");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);

		} else if (user.getRole().equals("reader")) {
			request.setAttribute("message",
					"You logged is as a reader. You do not have permission to open the page");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);

			// } else if(articles == null ){
			// request.setAttribute("message",
			// "Session is expired, try again");
			// request.getRequestDispatcher("/home.jsp")
			// .forward(request, response);
		} else {
			int articleId = Integer.valueOf(request.getParameter("articleId"));
			int formId = Integer.valueOf(request.getParameter("formId"));
			System.out.println(articleId);
			System.out.println(formId);
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
				Connection conn = DriverManager.getConnection(DB);
				CommentDB com = new CommentDB(conn);
				// Article article = articles.get(articleId);
				Article article = articles.get(articleId);// this is not article
															// id in the table
				ReviewForm form = article.getForms().get(formId);
				ArrayList<Reason> reasons = form.getReasons();
				System.out.println(request.getParameterNames());
				for (Reason reason : reasons) {
					// adding the new comment to the list of comments for each
					// reason
					String c = request.getParameter("reason" + reason.getId()); // this
																				// 'reasoni'
																				// parameter
																				// is
																				// defiend
																				// in
																				// the
																				// view
																				// (author-form.jsp)
					// reason.getComments().add(new
					// Comment(reason.getId(),reason.getTitle(),com));
					com.insertIntoComments(reason.getId(), c);
					Form f = new Form(conn);
					f.updateStatusOfTheForm(form.getId(), "update");

					System.out.println(com);
				}
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("/AuthorController");
				dispatcher.forward(request, response);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
