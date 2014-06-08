package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Article;
import beans.ReviewForm;
import beans.User;
import database.Account;
import database.ArticleTable;
import database.Email;
import database.Form;


public class EditorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditorController() {
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
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String formId = request.getParameter("formId");
		String articleId = request.getParameter("articleId");
		String userId = request.getParameter("userId");
		String approve = request.getParameter("approve");
		if (user == null) {
			request.setAttribute("message",
					"You need to login to view the users");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);

		} else if (!user.getRole().equals("editor")) {
			request.setAttribute("message",
					"You are not an editor. You do not have permission to open this page");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);

		} else {

			try {
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
				conn = DriverManager.getConnection(DB);
				if ((action != null) && (action.equals("users"))) {
					Account account = new Account(conn);
					ArrayList<User> users = account
							.getUsersFromResultSet(account.getRecords());
					request.setAttribute("users", users);
					request.getRequestDispatcher("/views/registeredusers.jsp")
							.forward(request, response);

				} else if (formId != null && articleId != null) {
					@SuppressWarnings("unchecked")
					ArrayList<Article> articles = (ArrayList<Article>) session
							.getAttribute("editorArticles");

					if (articles == null) {
						request.setAttribute("message",
								"your session is expired.\nPlease login and try again");
						request.getRequestDispatcher("/home.jsp").forward(
								request, response);
					} else {
						Article article = articles.get(Integer
								.valueOf(articleId));
						ReviewForm form = article.getForms().get(
								Integer.valueOf(formId));
						System.out.println(article.getTitle());
						System.out.println(form.getId());
						Form f = new Form(conn);
						if (action != null && action.equals("confirm")) {
							f.approveTheArticleForm(form.getId());

						} else if (action != null && action.equals("reject")) {
							f.rejectTheArticleForm(form.getId());

						} else {
							if(approve.equals("true")){
							f.approveTheForm(form.getId());
							f.updateStatusOfTheForm(form.getId(), "submit");
							}else{
								f.updateStatusOfTheForm(form.getId(), "delete");
								f.rejectTheForm(form.getId());
								
							}
						}
						articles = (ArrayList<Article>) (new ArticleTable(conn)
								.getAllArticles());
						session.setAttribute("editorArticles", articles);
						request.setAttribute("info", "Form updated successfull");
						request.getRequestDispatcher(
								"/views/editor-articles.jsp?id=" + articleId)
								.forward(request, response);

					}

				} else if (action != null && action.equals("articles")) {
					@SuppressWarnings("unchecked")
					ArrayList<Article> articles = (ArrayList<Article>) (new ArticleTable(
							conn).getAllArticles());
					session.setAttribute("editorArticles", articles);
					request.getRequestDispatcher("/views/editor-articles.jsp")
							.forward(request, response);

				} else if ((action != null && action.equals("invite"))
						&& (userId != null)) {
					Account account = new Account(conn);
					User u = account.getUserById(Integer.valueOf(userId));

					account.changeRole("editor", u.getId());
					Email email = new Email();
					email.setBody("Dear "
							+ user.getFirstname()
							+ ",<br>You are invited to be an editor in our system<br/>You can login with your previous password and access the editor page<br><br>Thank you");
					email.setRecipient(u.getEmail());
					email.setSubject("Invitation");
					email.sendEmail();
					ArrayList<User> users = account
							.getUsersFromResultSet(account.getRecords());
					request.setAttribute("users", users);
					request.setAttribute("message",
							"Your invitation is sent successfully");
					request.getRequestDispatcher("/views/registeredusers.jsp")
							.forward(request, response);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		@SuppressWarnings("unchecked")
		ArrayList<Article> articles = (ArrayList<Article>) session
				.getAttribute("editorArticles");
		String formId = request.getParameter("formId");
		String articleId = request.getParameter("articleId");
		String userId = request.getParameter("userId");
		System.out.println(articleId);
		if (user == null) {
			request.setAttribute("message",
					"You need to login to view the users");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);

		} else if (!user.getRole().equals("editor")) {
			request.setAttribute("message",
					"You are not an editor. You do not have permission to open this page");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);

		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
				conn = DriverManager.getConnection(DB);
				if (articleId != null) {

					Article article = articles.get(Integer.valueOf(articleId));
					ArticleTable at = new ArticleTable(conn);
					at.publishArticle(article.getId());
					articles = (ArrayList<Article>) (new ArticleTable(
							conn).getAllArticles());
					session.setAttribute("editorArticles", articles);
					Email mail = new Email();
					mail.setBody("Dear "+article.getMainUser().getFirstname()+",<br>Your article has been published successfully.");
					mail.setSubject("Published Successfully");
					mail.setRecipient(article.getMainUser().getEmail());
					mail.sendEmail();
					request.setAttribute("info", "Article published successfully");
					
					request.getRequestDispatcher("/views/editor-articles.jsp")
							.forward(request, response);
					
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				request.setAttribute("message", "Record not found");
				request.getRequestDispatcher("/home.jsp").forward(request,
						response);
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
