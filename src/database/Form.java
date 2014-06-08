package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import beans.ReviewForm;
import beans.User;

public class Form {

	private Connection conn;

	public Form(Connection conn) {
		this.conn = conn;
	}

	public Form() {

	}

	/**
	 * This method implemented to find how many articles the current user has
	 * selected for peer-review
	 * 
	 * @param currentUserId
	 * @return
	 * @throws SQLException
	 */
	public ResultSet getApprovedArticles(int currentUserId) throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet = stst
				.executeQuery("select * from forms where reviewer_id ='"
						+ currentUserId + "' and article_approve=true");
		return resultSet;

	}

	public void insertIntoForm(int article_id, int auther_id, int reviewer_id,
			String level, String summary, String secret, String overall)
			throws SQLException {

		String sql = "insert into forms(article_id,author_id,reviewer_id,level,summary,secret_message,overall) values(?,?,?,?,?,?,?) ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, article_id);
		stmt.setInt(2, auther_id);
		stmt.setInt(3, reviewer_id);
		stmt.setString(4, level);
		stmt.setString(5, summary);
		stmt.setString(6, secret);
		stmt.setString(7, overall);
		stmt.executeUpdate();
		stmt.close();
		// insertIntoKeywords();

	}

	public void updateForm(int article_id, int author_id, int reviewer_id,
			String level, String summary, String secret, String overall)
			throws SQLException {

		Statement st = conn.createStatement();
		String sql = "update forms set level='" + level + "', summary='"
				+ summary + "', secrete='" + secret + "', overall='" + overall
				+ "' where article_id = '" + article_id + "' and author_id='"
				+ author_id + "' and reviewer_id='" + reviewer_id + "'";
		st.execute(sql);
		st.close();
	}

	public void createForm(int article_id, int author_id, int reviewer_id)
			throws SQLException {

		Statement st = conn.createStatement();
		String sql_validString = "select count(*) as count from forms where article_id = '"
				+ article_id
				+ "' and author_id='"
				+ author_id
				+ "' and reviewer_id='" + reviewer_id + "'";
		ResultSet resultSet = st.executeQuery(sql_validString);
		resultSet.next();
		if (resultSet.getInt("count") == 0) {
			String sql = "insert into forms(article_id,author_id,reviewer_id) values(?,?,?) ";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, article_id);
			stmt.setInt(2, author_id);
			stmt.setInt(3, reviewer_id);
			stmt.executeUpdate();
			stmt.close();
		} else {
			// nothing
		}
		// st.close();
	}

	public void downloadArticle(int article_id, int reviewer_id)
			throws SQLException {
		Date nowDate = new Date();
		String sqlString = "update forms set form_status='download', download_at='"+ new Timestamp(nowDate.getTime())+"' where article_id='"
				+ article_id + "' and reviewer_id='" + reviewer_id + "'";
		Statement st = conn.createStatement();
		st.execute(sqlString);
	}

	public void updateForm(int article_id, int reviewer_id, String level,
			String summary, String secret, String overall) throws SQLException {
		String sql = "update forms set level='" + level + "', summary='"
				+ summary + "', secret_message='" + secret + "', overall='"
				+ overall + "' where article_id ='" + article_id
				+ "' and reviewer_id='" + reviewer_id + "'";
		Statement st = conn.createStatement();
		st.execute(sql);
		st.close();
	}

	// for editor to approve the form
	public boolean setApproveToForms(int article_id, int reviewer_id) {
		Statement stst;
		try {
			stst = conn.createStatement();
			try {
				stst.executeUpdate("update forms set form_approve = '1' where article_id = "
						+ article_id + " and reviewer_id = " + article_id + "");
				return true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				return false;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
	}

	public ResultSet getReviewFormsByArticle_Reviewer(int article_id,
			int reviewer_id) throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from forms where article_id = '"
						+ article_id + "' and reviewer_id='" + reviewer_id
						+ "'");
		return rs;
	}

	public ArrayList<ReviewForm> getAuthorReviewForms(int articleId)
			throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from forms where article_id = "
						+ articleId);
		ArrayList<ReviewForm> forms = new ArrayList<ReviewForm>();
		while (rs.next()) {
			ReviewForm form = new ReviewForm();
			form.setId(rs.getInt("id"));
			form.setArticleId(rs.getInt("article_id"));
			form.setAutherId(rs.getInt("author_id"));
			form.setReviewerId(rs.getInt("reviewer_id"));
			form.setLevel(rs.getString("level"));
			form.setOverall(rs.getString("overall"));
			form.setSummary(rs.getString("summary"));
			form.setSecrete(rs.getString("secret_message"));
			System.out.println("....................................................."+form.getSecrete());
			form.setFormApproved(rs.getString("form_approve"));
			form.setArticleApproved(rs.getString("article_approve"));
			form.setReviewer(new Account(conn).getUserById(rs.getInt("reviewer_id")));
			form.setReasons(new ReasonTable(conn).getFormReasons(rs.getInt("id")));
			form.setErrors(new MistakeDB(conn).getListOfErrors(articleId,rs.getInt("reviewer_id")));
			form.setStatus(rs.getString("form_status"));

			forms.add(form);

			// form.setCommentList(commentList);
			// form.setErrors(errors);

		}
		return forms;
	}

	/**
	 * 
	 * @param articleId
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<User> getReviewersOfTheArticle(int articleId)
			throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from forms where article_id = "
						+ articleId);
		ArrayList<User> authors = new ArrayList<User>();
		while (rs.next()) {
			User author = new User();
			author = new Account(conn).getUserById(rs.getInt("reviewer_id"));
			authors.add(author);
		}
		return authors;
	}

	public void cancelSelect(int article_id, int reviewer_id)
			throws SQLException {
		String sql = "delete from forms where article_id='" + article_id
				+ "' and reviewer_id='" + reviewer_id + "'";
		Statement st = conn.createStatement();
		st.execute(sql);
		st.close();
	}
	
	public List<User> getUsersForReminderSchduler() throws SQLException
	{
		List<User> userList = new ArrayList<User>();
		Statement st = conn.createStatement();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date weekbefore = cal.getTime();
		Timestamp weekAgo = new Timestamp(weekbefore.getTime());
		String sql =  "select * from users where id in ( select reviewer_id from forms where form_status='select' and (download_at<='"+weekAgo+"' or download_at is null))";
		ResultSet resultSet = st.executeQuery(sql);
		while(resultSet.next()){
			User user = new User();
			user.setAffiliation(resultSet.getString("affiliation"));
			user.setEmail(resultSet.getString("email"));
			user.setFirstname(resultSet.getString("first_name"));
			user.setId(resultSet.getInt("id"));
			user.setLastname(resultSet.getString("last_name"));
			userList.add(user);
		}
		resultSet.close();
		st.close();
		return userList;
	}
	
	// set status to 'submit' if it past 7 days
	public void updateStatusForScheduler() throws SQLException
	{
		Statement st = conn.createStatement();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		Date weekbefore = cal.getTime();
		Timestamp weekAgo = new Timestamp(weekbefore.getTime());
		String sql =  "update forms set form_status='submit' where form_status='update' and download_at<='"+weekAgo+"'";
		st.execute(sql);
		st.close();
	}

	public void updateStatus(String status, int article_id, int reviewer_id)
			throws SQLException {
		Statement st = conn.createStatement();
		String sqlString = "select count(*) as count from forms where article_id='"
				+ article_id + "' and reviewer_id='" + reviewer_id + "'";
		ResultSet rs = st.executeQuery(sqlString);
		rs.next();
		int reject_count = rs.getInt("count");

		if (status.equals("reject") && reject_count < 2) {
			// TODO add reject count if the status is reject and count<2
			st.execute("update forms set reject_count=reject_count+1 where article_id='"
					+ article_id + "' and reviewer_id='" + reviewer_id + "'");
		} else if (status.equals("reject") && reject_count == 2) {
			// TODO set status to final reject when the reject count is 1
			status = "final reject";
		} else if (status.equals("accept")) {
			st.execute("update articles set review_count=review_count+1 where id='"
					+ article_id + "'");
		} else if (status.equals("submit") && reject_count < 2) {
			// TODO add reject count if the status is submit and count<2
			st.execute("update forms set reject_count=reject_count+1 where article_id='"
					+ article_id + "' and reviewer_id='" + reviewer_id + "'");
		} else if (status.equals("submit") && reject_count == 2) {
			// TODO add reject count if the status is submit and count<2
			status = "final reject";
		} 
		st.executeUpdate("update forms set form_status = '" + status
				+ "' where article_id = '" + article_id + "' and reviewer_id='"
				+ reviewer_id + "'");

		st.close();
	}

	public void approveTheForm(int formId) throws SQLException {

		Statement st = conn.createStatement();
		st.executeUpdate("update forms set form_approve = true where id = "
				+ formId);

		st.close();

	}

	/**
	 * Rejecting the aricle-review in form
	 * 
	 * @param formId
	 * @throws SQLException
	 */
	public void approveTheArticleForm(int formId) throws SQLException {

		Statement st = conn.createStatement();
		st.executeUpdate("update forms set article_approve = true where id = "
				+ formId);

		st.close();

	}

	/**
	 * when the editor do not allow the reviewer for peer-reviewing the article it deltes the form
	 * @param formId
	 * @throws SQLException
	 */
	public void rejectTheArticleForm(int formId) throws SQLException {

		String sql = "delete from forms where id = " + formId;
		Statement st = conn.createStatement();
		st.execute(sql);
		st.close();

	}

	public User getReviewer(int userId) throws SQLException {
		Account account = new Account(conn);
		User reviewer = account.getUserById(userId);
		return reviewer;

	}
	/**
	 * 
	 * @param formId
	 * @param status
	 * @throws SQLException
	 */
	public void updateStatusOfTheForm(int formId,String status) throws SQLException {
		
		Statement st = conn.createStatement();
		st.executeUpdate("update forms set form_status = '"+status+"' where id = "
				+ formId );
				
		if(st != null)st.close();
	}
		
	public void rejectTheForm(int formId) throws SQLException {
		
		Statement st = conn.createStatement();
		st.executeUpdate("update forms set form_approve = 0 where id = "+formId);
		
		if(st != null)st.close();
	}

	public int getReviewCount(int reviewer_id, int caseNo) throws SQLException {
		int count = 0;
		Statement st = conn.createStatement();
		String sql = "";
		switch (caseNo) {
		case 0:
			// 0 is count for selected article in the awaiting list
			sql="select count(*) as count from forms where form_status='select' and article_approve=false and reviewer_id='"+reviewer_id+"'";
			break;
		case 1:
			// 1 is count for approved selected articles which has not been download
			sql="select count(*) as count from forms where form_status='select' and article_approve=true and reviewer_id='"+reviewer_id+"'";
			break;
		case 2: 
			// 2 is count for approved forms (finish review)
			sql="select count(*) as count from forms where form_status='accept' and reviewer_id='"+reviewer_id+"'";
			break;
		case 3:
			// 3 is count for finally reject forms (finish review)
			sql="select count(*) as count from forms where form_status='final reject'  and reviewer_id='"+reviewer_id+"'";
			break;
		case 4:
			// 4 is count for delete forms(rejected by editor)
			sql="select count(*) as count from forms where form_status='delete' and reviewer_id='"+reviewer_id+"'";
			break;
		default:
			count = -1;
			break;
		}
		if(!sql.equals(""))
		{
			ResultSet rs = st.executeQuery(sql);
			rs.next();
			count = rs.getInt("count");
			rs.close();
		}
		st.close();
		return count;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		String DB = "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
		Connection conn = DriverManager.getConnection(DB);
		Form form = new Form(conn);
		//
		List<User> users = new ArrayList<User>();
		users = form.getUsersForReminderSchduler();
		//
//		form.approveTheForm(3);
		
	}
	


//	 public static void main(String[] args) throws ClassNotFoundException, SQLException {
//	
//	
//	 Class.forName("com.mysql.jdbc.Driver");
//	 String DB =
//	 "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
//	 Connection conn = DriverManager.getConnection(DB);
//	 Form form = new Form(conn);
//	 //form.approveTheForm(3);
//	 form.updateStatusOfTheForm(7, "updated");
//	 form.rejectTheForm(7);
//	
//	 }

}
