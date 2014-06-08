package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.Article;
import beans.User;
import database.ArticleTable;
import database.Email;
import database.Form;

/**
 * Servlet implementation class AuthorController author Iman Rastkhadiv
 */
public class AuthorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "resources";
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	private Connection conn;
	private String file;
	private int articleId;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AuthorController() {
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

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
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

		}

		else {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
				conn = DriverManager.getConnection(DB);
				ArticleTable article = new ArticleTable(conn);
				ArrayList<Article> articles = article.getAuthorArticles(user
						.getId());
				System.out.println(articles.get(0).getForms());
				HttpSession articlesSession = request.getSession();
				articlesSession.setAttribute("articles", articles);
				request.getRequestDispatcher("/views/author-articles.jsp")
						.forward(request, response);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
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
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		@SuppressWarnings("unchecked")
		ArrayList<Article> articles = (ArrayList<Article>) session
				.getAttribute("articles");

		if (user == null || articles == null) {
			request.setAttribute("message",
					"your session is expired.\nPleas login and try again");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);
		}
		try {
			
			uploadFile(request, response);
			Article article = articles.get(articleId);
			System.out.println(articleId+"This is the article Id from arrayList");
			System.out.println(article.getId()+"this is original id"+article.getTitle());
			if (!article.getPdfPath().equals(file)) {
				request.setAttribute("message",
						"Your file name should be the same as the file you uploaded before.\n("
								+ article.getPdfPath() + ")");
				request.getRequestDispatcher(
						"/views/author-articles.jsp?id=" + articleId).forward(
						request, response);
			} else {
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
				conn = DriverManager.getConnection(DB);
				Form form = new Form(conn);
				Email email = new Email();
				// sending emial to all the reviewer
				for (User reviewer : form.getReviewersOfTheArticle(article
						.getId())) {
					email.setRecipient(reviewer.getEmail());
					email.setSubject("Revised Article");
					email.setBody("Dear "
							+ reviewer.getFirstname()
							+ "<br/>The following article has been revised<br/>Article Title: "
							+ article.getTitle() + "<br/>Author Email:"
							+ article.getMainUser().getEmail() + "<br>");
					if (email.getRecipient() != null)
						email.sendEmail();

				}
				request.setAttribute("success", "Uploaded Successfully");
				// request.getRequestDispatcher("/views/author-articles.jsp?id="+articleId).forward(request,
				// response);
				request.getRequestDispatcher("/views/author-articles.jsp")
						.forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// sets memory threshold - beyond which files are stored in disk
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// sets temporary location to store files
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		// sets maximum size of upload file
		upload.setFileSizeMax(MAX_FILE_SIZE);

		// sets maximum size of request (include file + form data)
		upload.setSizeMax(MAX_REQUEST_SIZE);

		// constructs the directory path toup store upload file
		// this path is relative to application's directory
		String uploadPath = getServletContext().getRealPath("")
				+ File.separator + UPLOAD_DIRECTORY;
		// /String uploadPath = getServletContext().getRealPath("/WEB-INF/pdf");

		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			// parses the request's content to extract file data
			// @SuppressWarnings("unchecked")
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 0) {
				// iterates over form's fields
				for (FileItem item : formItems) {
					// processes only fields that are not form fields
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						String filePath = uploadPath + File.separator
								+ fileName;
						file = fileName;
						File storeFile = new File(filePath);
						// saves the file on disk
						item.write(storeFile);
					} else {
						if (item.getFieldName().equals("articleId")){
							this.articleId = Integer.valueOf(item.getString());
						}
					}
				}
			}
		} catch (Exception ex) {
			request.setAttribute("message",
					"There was an error: " + ex.getMessage());
		}

	}
}
