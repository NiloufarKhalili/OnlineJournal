package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import beans.Comment;
import beans.Error;
import beans.Mistake;
import beans.ReviewForm;
import beans.User;
import database.Account;
import database.CommentDB;
import database.Email;
import database.Form;
import database.MistakeDB;

/**
 * Servlet implementation class ReviewFormServlet
 */
public class ReviewFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection conn;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReviewFormServlet() {
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

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jsonString = request.getParameter("json");
		System.out.println("json is " + jsonString);
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		if (session.getAttribute("user") == null) {
			request.setAttribute("message",
					"You need to login the system");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);

		} else if (user.getRole().equals("reader")) {
			request.setAttribute("message",
					"You logged is as a reader. You do not have permission to open the page");
			request.getRequestDispatcher("/home.jsp")
					.forward(request, response);

		}
		// this is the user id

		else {
			try {
				// start connection with database
				Class.forName("com.mysql.jdbc.Driver");
				String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
				conn = DriverManager.getConnection(DB);
				Form form = new Form(conn);
				CommentDB commentDB = new CommentDB(conn);
				MistakeDB mistakeDB = new MistakeDB(conn);
				if (jsonString != null) {
					try {
						JSONObject jsonObject = new JSONObject(jsonString);
						int article_id = jsonObject.getInt("article_id");
						// get the error json array
						String json_errors = jsonObject.get("error").toString();
						System.out.println(json_errors);
						JSONArray errorJsonArray = new JSONArray(json_errors);
						// list of error
						List<Error> errorList = new ArrayList();
						if (errorJsonArray.length() > 0) {
							for (int i = 0; i < errorJsonArray.length(); i++) {
								// TODO get the error list and insert into the
								// database
								String errorString = errorJsonArray
										.getJSONObject(i).getString("error");
								Error error = new Error(errorString);
								mistakeDB.insertIntoError(article_id, user.getId(), errorString);
								errorList.add(error);
							}
							System.out.println(errorJsonArray.getJSONObject(0));
						}
						// list of criticism
						List<Comment> criticismList = new ArrayList();
						// get the comment json array
						String json_criticism = jsonObject.get("criticism")
								.toString();
						System.out.println(json_errors);
						JSONArray criticismJsonArray = new JSONArray(
								json_criticism);
						if (criticismJsonArray.length() > 0) {
							for (int i = 0; i < criticismJsonArray.length(); i++) {
								// TODO get the error list and insert into the
								// database
								String titleString = criticismJsonArray
										.getJSONObject(i).getString("title");
								String contentString = criticismJsonArray
										.getJSONObject(i).getString("content");
								Comment comment = new Comment(titleString,
										contentString);
								criticismList.add(comment);
								System.out.println("title : " + titleString);
								System.out
										.println("content : " + contentString);
								commentDB.insertReason(user.getId(), article_id, titleString, contentString);
								
								
							}
							System.out.println(criticismJsonArray
									.getJSONObject(0));
						}
						// update form
						String typeString = jsonObject.getString("type").toString();
						
						String level = jsonObject.getString("level").toString();
						String summary = jsonObject.getString("summary")
								.toString();
						String secret = jsonObject.getString("secret")
								.toString();
						String overall = jsonObject.getString("overall")
								.toString();
						form.updateForm(article_id, user.getId(), level,
								summary, secret, overall);
						boolean secret_message = jsonObject.getBoolean("send_message");
//						System.out.println("secrete message is "+secret_message);
						Email mail = new Email();
						// send email to editors if the secret message is selected
						if(secret_message&&(!typeString.equals("update"))){
							Account account = new Account(conn);
							List<User> userList = new ArrayList<User>();
								userList = account.getEditorList();
								System.out.println(userList.size());
								for(int i = 0; i< userList.size();i++){
									mail.sendSecretToEditor(userList.get(i).getEmail(),
											user.getFirstname()+" "+ user.getLastname(), secret);
								}
//								System.out.println(secret_message);
						}
						
						form.updateStatus(typeString, article_id, user.getId());						
						mail.sendEmailForUpdateForm(user.getEmail(), user.getFirstname());
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				//TODO insert mistake (check if exists) and middle table
				//TODO insert into reason list and comment
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
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
