package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.User;
import database.Account;
import database.ArticleTable;

/**
 * Servlet implementation class ReviewArticle
 */
public class ReviewArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewArticle() {
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
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
			conn = DriverManager.getConnection(DB);
			HttpSession session = request.getSession(false);
			// User user = (User)
			// request.getSession(false).getAttribute("user");
			if (session != null) {
				User user = (User) session.getAttribute("user");
				String email = user.getEmail();
				Account account = new Account(conn);
				ArticleTable article = new ArticleTable(conn);
				int userId = account.getUserId(email);

				// String update = request.getParameter("update");
				String[] checkBoxes = request.getParameterValues("articles");
				String[] selectedcheckBoxes = request
						.getParameterValues("selected");
				System.out.println(checkBoxes);
				System.out.println(selectedcheckBoxes);

				if (checkBoxes != null) {
					PrintWriter out = response.getWriter();
					for (String item : checkBoxes) {
						int articleId = article.getArticleId(item);
						article.insertIntoReviewerStatus(
								String.valueOf(userId),
								String.valueOf(articleId));
					}

				}

				if (selectedcheckBoxes != null) {
					PrintWriter out = response.getWriter();
					for (String item : selectedcheckBoxes) {
						int articleId = article.getArticleId(item);
						article.removeFromReviewerStatus(
								String.valueOf(userId),
								String.valueOf(articleId));
					}

				}

				ResultSet selectedSet = article.getSelectedArticleIds(String
						.valueOf(userId));
				ResultSet downloadedSet = article
						.getDownloadedArticleIds(String.valueOf(userId));

				ResultSet selectedIds = article.getSelectedArticleIds(String
						.valueOf(userId));
				ResultSet selected = article.getSelectedArticles(article
						.getReviewerIds(selectedSet));
				request.setAttribute("selected", selected);

				ResultSet downloadedIds = article
						.getDownloadedArticleIds(String.valueOf(userId));
				ResultSet downloaded = article.getDownloadedArticles(article
						.getReviewerIds(downloadedSet));
				request.setAttribute("downloaded", downloaded);

				ArrayList<String> s = article.getReviewerIds(selectedIds);
				ArrayList<String> ss = article.getReviewerIds(downloadedIds);
				s.addAll(ss);
				System.out.println(s);
				System.out.println(ss + "?????");

				// System.out.println(article.getSelectedIds(selectedIds));

				ResultSet result = article.getUnpublishedArticles(
						account.getUserId(email), s);
				request.setAttribute("result", result);

				request.getRequestDispatcher("/views/unpublished.jsp").forward(
						request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
