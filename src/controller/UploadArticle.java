package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletContext;
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
import database.Account;
import database.ArticleTable;
import database.Email;
import database.KeywordTable;

//import beans.Keyword;

public class UploadArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "resources";

	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	private Connection conn;
	private String email;
	private String firstname;
	private String lastname;
	private String title;
	private String abst;
	private String keywords;
	private String file;
	private String numberOfAuthors;
	private String affiliation;
	private ArrayList<String> list;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadArticle() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String relativePath = "";
		if (fileName.equals("test.docx")) {
			relativePath = getServletContext().getRealPath("") + File.separator
					+ "templates" + File.separator + "test.docx";
			System.out.println(relativePath);
		} else if (fileName.equals("LaTeX.tex")) {
			relativePath = getServletContext().getRealPath("") + File.separator
					+ "templates" + File.separator + "LaTeX.tex";
			System.out.println(relativePath);
		}
		File downloadFile = new File(relativePath);
		FileInputStream inStream = new FileInputStream(downloadFile);

		System.out.println(getServletContext().getContextPath() + "");
		// obtains ServletContext
		ServletContext context = getServletContext();

		// gets MIME type of the file
		String mimeType = context.getMimeType(relativePath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		System.out.println("MIME type: " + mimeType);

		// modifies response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// forces download
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				downloadFile.getName());
		response.setHeader(headerKey, headerValue);
		// obtains response's output stream
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[4096];
		int bytesRead = -1;

		while ((bytesRead = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inStream.close();
		outStream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterNames() == null) {
			request.setAttribute("message", "Error");
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}

		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
			conn = DriverManager.getConnection(DB);
			Account account = new Account(conn);

			HttpSession session = request.getSession();
			User se = (User) session.getAttribute("user");
			if(se != null){
				session.removeAttribute("user");
			}
			uploadFile(request, response);
			if(new ArticleTable(conn).checkFileName(this.file)){
				request.setAttribute("message", "Your file name exists. Please upload an article with a different name");
				request.getRequestDispatcher("/views/upload-article.jsp").forward(request, response);
				return;
			}

			
			Email mail = new Email();
			if (account.exists(email)) {
				
				User user = account.getUserById(account.getUserId(email));
				mail.setBody("Dear "+user.getFirstname()+",<br/>Thank you for uploading another article.<br/>You can use your previous password to login to the system.");
				mail.setSubject("Successfully Uploaded");
				mail.setRecipient(email);
				mail.sendEmail();

			} else {
				String password = mail.generatePassword();
				account.create(email, password, firstname, lastname);
				mail.sendEmailForUploadArticle(email, firstname, password);

			}
//
			int userId = account.getUserId(email);
			account.changeRole("Author", userId);
			Article article = new Article(title, abst, String.valueOf(userId),
					file);
			article.setKeywords(new KeywordTable().getKeywords(keywords));
//		    
			if(list != null)
			article.setUsers(this.getUsers(list));
			
			ArticleTable articleTable = new ArticleTable(conn, article);
			articleTable.insertIntoArticles();
			System.out.println("this is list" + list);
			System.out.println(getUsers(list));
			for (User item : getUsers(list)) {
				System.out.println(item.getFirstname() + "/"
						+ item.getLastname() + "/" + item.getEmail());
			}

			request.setAttribute("info",
					"Your artilce was uploaded sucessfully. You will receive an email shortly");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);
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
		} catch (Exception e) {
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

	public void uploadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		list = new ArrayList<String>();
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
		// FILES SHOULD BE SAVED ON WEBCONTENT ROOT INSIDE RESOURCES
		System.out.println(uploadPath);

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
						System.out.println("full path:" + filePath);
						file = fileName;
						File storeFile = new File(filePath);
						// saves the file on disk
						item.write(storeFile);
					} else {
						//
						if (item.getFieldName().equals("firstname"))
							this.firstname = item.getString();
						else if (item.getFieldName().equals("lastname"))
							// this.lastname = setInputValue(item);
							this.lastname = item.getString();
						else if (item.getFieldName().equals("email"))
							this.email = item.getString();
						else if (item.getFieldName().equals("title"))
							this.title = item.getString();
						else if (item.getFieldName().equals("abstract"))
							this.abst = item.getString();
						else if (item.getFieldName().equals("keywords"))
							this.keywords = item.getString();
						else if(item.getFieldName().equals("affiliation"))
							this.affiliation = item.getString();
						else if (item.getFieldName().equals("numberOfAuthors"))
							this.numberOfAuthors = item.getString();
						else {
							System.out.println(item.getFieldName());
							System.out.println(item.getString());
							list.add(item.getString());
						}
					}

				}
			}
		} catch (Exception ex) {
			request.setAttribute("message",
					"There was an error: " + ex.getMessage());
		}

	}

	private ArrayList<User> getUsers(ArrayList<String> list) {
		ArrayList<User> users = new ArrayList<User>();
		int n = 0;
		for (int i = 0; i < Integer.valueOf(numberOfAuthors); i++) {
			User user = new User();
			user.setFirstname(list.get(n));
			n += 1;
			user.setLastname(list.get(n));
			n += 1;
			user.setEmail(list.get(n));
			n += 1;
			user.setAffiliation(list.get(n));
			users.add(user);
			n += 1;
		}
		return users;
	}
	
		
	

}
