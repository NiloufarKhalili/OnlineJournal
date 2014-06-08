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

import beans.Article;
import beans.User;
import database.Account;
import database.ArticleTable;
import database.Email;


public class ReaderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReaderController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws IOException
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String articleId = request.getParameter("id");
		String searchBy = request.getParameter("action");
		String text = request.getParameter("value");
		String fromDate = request.getParameter("from");
		String toDate = request.getParameter("to");
		String view = request.getParameter("view");
		
		if(articleId != null || view !=null){
			String relativePath="";
			try {
		if (articleId != null) {
			response.setContentType("application/pdf");
			// ServletContext ctx = getServletContext();
			HttpSession session = request.getSession(false);
			
				@SuppressWarnings("unchecked")
				ArrayList<Article> articles = (ArrayList<Article>) session
						.getAttribute("readerArticles");
				String fileName = articles.get(Integer.valueOf(articleId))
						.getPdfPath();

				 relativePath = getServletContext().getRealPath("")
						+ File.separator + "resources" + File.separator
						+ fileName;
			}else if(view != null && view.equals("userguide")){
					  relativePath = getServletContext().getRealPath("")+File.separator+"userguide"+File.separator+"userguide.pdf";
				}
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
					request.getRequestDispatcher("/home.jsp").forward(request,
							response);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					request.setAttribute("message", "pdf not found");
					request.getRequestDispatcher("/home.jsp").forward(request,
							response);

				}
				e.printStackTrace();
		}}

	else {

			try {
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
				conn = DriverManager.getConnection(DB);
				ArticleTable articleTable = new ArticleTable(conn);
				HttpSession session = request.getSession();
				ArrayList<Article> articles = new ArrayList<Article>();
				if (searchBy.equals("all")) {
					 articles = articleTable.getArticlesFromResultSet(articleTable.getPublishedArticles());

				} else if (searchBy.equals("author")) {
					articles = articleTable
							.getArticlesByAuthorName(text);
				} else if (searchBy.equals("keyword")) {
					 articles = articleTable
							.getArticlesByKeywords(text);
				} else if (fromDate != null) {
					 articles = articleTable
							.getArticlesByDate(fromDate, toDate);

				} else if (searchBy.equals("title")) {
					 articles = articleTable
							.getArticlesByTitle(text);

				}  else {
					articles = articleTable.getArticlesFromResultSet(articleTable.getPublishedArticles());
					
				}
				if(articles !=null && articles.size() != 0) {
					session.setAttribute("readerArticles", articles);
					request.getRequestDispatcher("/views/reader.jsp").forward(
							request, response);
				}else {
					request.setAttribute("message", "Record not found");
					request.getRequestDispatcher("/home.jsp").forward(request, response);
				}
					
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				request.setAttribute("message", "pdf not found");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String articleId = request.getParameter("articleId");
		String title = request.getParameter("title");
		String comment = request.getParameter("comment");
		String email = request.getParameter("email");
		@SuppressWarnings("unchecked")
		ArrayList<Article> articles = (ArrayList<Article>) session
				.getAttribute("readerArticles");
		if (articles == null) {
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);
		} else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
				Connection conn = DriverManager.getConnection(DB);
				Article article = articles.get(Integer.valueOf(articleId));
				Account account = new Account(conn);
				ArrayList<User> editors = account.getEditors();
				String subject = "Reader Comment";
				for (User editor : editors) {

					String body = "Dear " + editor.getFirstname()
							+ ",<br/>The following reader left a comment for "
							+ article.getTitle() + "<br>Email:" + email
							+ "<br>Title:" + title + "<br>Comment:" + comment;
					Email mail = new Email(editor.getEmail(), subject, body);
					mail.sendEmail();
				}
				String body = "Dear Reader,<br>Your comment for the following article is sent to the editor.<br>Article Title:"+article.getTitle()+"<br/>Your comment:"+comment;
				Email mail = new Email(email,subject,body);
				mail.sendEmail();
				request.setAttribute("message", "your comment posted successfully");
				request.getRequestDispatcher("/views/reader.jsp?id="+articleId).forward(request, response);

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

}
