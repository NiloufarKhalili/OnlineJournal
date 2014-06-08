package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import beans.Article;
import beans.User;

public class ArticleTable {

	private Connection conn;
	private Article article;

	public ArticleTable(Connection conn, Article article) {
		this.conn = conn;
		this.article = article;
	}

	public ArticleTable(Connection conn) {
		this.conn = conn;
	}

	public ArticleTable() {

	}

	public int getArticleId(String title) throws SQLException {
		String sql = "select * from articles where title=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, title);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			int id = Integer.valueOf(rs.getString("id"));
			return id;
		}
		return 0;

	}

	public void insertIntoArticles() throws SQLException {

		String sql = "insert into articles(title,abstract,user_id,pdf_path) values(?,?,?,?) ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, article.getTitle());
		stmt.setString(2, article.getAbst());
		stmt.setString(3, article.getUserId());
		stmt.setString(4, article.getPdfPath());
		stmt.executeUpdate();
		stmt.close();
		String articleId = String.valueOf(getMaxIdFromTable("articles"));
		KeywordTable keyword = new KeywordTable(conn);
		article.setId(Integer.valueOf(articleId));
		keyword.insertIntoKeywords(article);
		insertIntoArticleAuthors(articleId, article.getUsers());

	}

	public ResultSet getUnpublishedArticles(int currentUserId,
			List<String> selectedIds) throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet;
		if (selectedIds.size() > 0)
			resultSet = stst
					.executeQuery("select * from articles where status = 'unpublished' and user_id != "
							+ currentUserId
							+ " and id not in "
							+ getIds(selectedIds));
		else
			// resultSet =
			// stst.executeQuery("select * from articles where status = 'unpublished' and user_id != "+
			// currentUserId);
			resultSet = stst
					.executeQuery("select * from articles where status = 'unpublished' and user_id != " + 444);
		System.out.println(selectedIds + "......");
		return resultSet;

	}

	public void insertIntoReviewerStatus(String reviewerId, String articleId)
			throws SQLException {

		String sql = "insert into reviewer_status(reviewer_id,article_id) values(?,?) ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, reviewerId);
		stmt.setString(2, articleId);
		stmt.executeUpdate();
		stmt.close();

	}

	public void insertIntoArticleAuthors(String articleId, ArrayList<User> users)
			throws SQLException {

		String sql = "insert into article_authors(article_id,firstname,lastname,email,affiliations) values(?,?,?,?,?) ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		for (User user : users) {
			stmt.setString(1, articleId);
			stmt.setString(2, user.getFirstname());
			stmt.setString(3, user.getLastname());
			stmt.setString(4, user.getEmail());
			stmt.setString(5, user.getAffiliation());
			stmt.executeUpdate();
		}
		stmt.close();

	}

	public void removeFromReviewerStatus(String reviewerId, String articleId)
			throws SQLException {

		String sql = "delete from reviewer_status where reviewer_id = "
				+ reviewerId + " and article_id = " + articleId;
		Statement st = conn.createStatement();
		st.execute(sql);
		st.close();

	}

	public ResultSet getPublishedArticles() throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet = stst
				.executeQuery("select * from articles where status = 'published'");
		return resultSet;

	}

	// return result of unpublished article list
	// filter result with the approved forms with the same reviewer_id
	public ResultSet getArticlesToSelect(int reviewer_id) throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet = stst
				.executeQuery("SELECT distinct a.id,a.title,a.abstract,b.first_name,b.last_name,a.created_at FROM articles as a, users as b where a.id "
						+ "not in (select article_id from forms where reviewer_id='"
						+ reviewer_id
						+ "' ) and status='unpublished' and a.user_id = b.id and a.user_id<>"+ reviewer_id + " order by created_at;");
		return resultSet;
	}

	public ResultSet getAwaitingArticles(int reviewer_id) throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet = stst
				.executeQuery("SELECT distinct a.id,a.title,a.abstract,b.first_name,b.last_name,a.created_at FROM articles as a, users as b where a.id "
						+ "in (select article_id from forms where reviewer_id='"
						+ reviewer_id
						+ "' and form_status='select' and article_approve=false ) and status='unpublished' and a.user_id = b.id order by created_at;");
		return resultSet;
	}

	// to get the article list which the application of review has already been
	// approved
	public ResultSet getApprovedArticles(int reviewer_id) throws SQLException {
		Statement stst = conn.createStatement();
		String sqlString = "SELECT distinct a.id,a.title,a.abstract,b.first_name,b.last_name,a.created_at,a.review_count,a.pdf_path, c.form_status FROM articles as a, users as b,forms as c "
				// "where a.user_id=b.id and a.id=c.article_id and a.status='unpublished' and a.id in (select article_id from forms where reviewer_id ='"
				// + reviewer_id +
				// "' and article_approve=true  )";//"' and article_approve=true and form_approve=true )";
				+ "where a.user_id=b.id and a.id=c.article_id and c.reviewer_id ='"
				+ reviewer_id + "' and c.article_approve=true";
		System.out.println(sqlString);
		ResultSet resultSet = stst.executeQuery(sqlString);
		return resultSet;
	}

	public ResultSet getArticleByID(int article_id) throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet;
		resultSet = stst.executeQuery("select * from articles where id='"
				+ article_id + "'");
		return resultSet;
	}

	public ResultSet getDownloadedArticles(List<String> downloadedIds)
			throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet;
		if (downloadedIds.size() > 0)
			resultSet = stst
					.executeQuery("select * from articles where status = 'unpublished' and id in "
							+ getIds(downloadedIds));
		else
			resultSet = stst
					.executeQuery("select * from articles where status = 'p'");
		return resultSet;
	}

	public ResultSet getSelectedArticles(List<String> selectedIds)
			throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet;
		if (selectedIds.size() > 0)
			resultSet = stst
					.executeQuery("select * from articles where status = 'unpublished' and id in "
							+ getIds(selectedIds));
		else
			resultSet = stst
					.executeQuery("select * from articles where status = 'p'");
		return resultSet;
	}

	/**
	 * This method implemented to ...
	 * 
	 * @param reviewerId
	 * @return
	 * @throws SQLException
	 * 
	 */
	public ResultSet getSelectedArticleIds(String reviewerId)
			throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet = stst
				.executeQuery("select * from reviewer_status where form_status = 'Selected' and reviewer_id = "
						+ reviewerId);
		return resultSet;

	}

	public ResultSet getDownloadedArticleIds(String reviewerId)
			throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet resultSet = stst
				.executeQuery("select * from reviewer_status where form_status = 'Downloaded' and reviewer_id = "
						+ reviewerId);
		return resultSet;
	}

	/**
	 * This method implemented to retrieves article ids from reviewer_status
	 * table
	 * 
	 * @param selected
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> getReviewerIds(ResultSet selected)
			throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		while (selected.next()) {
			list.add(selected.getString("article_id"));
		}
		return list;
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	private String getIds(List<String> ids) {
		int size = ids.size();
		String s = "(";
		if (ids.size() == 0) {
			return "(0)";
		}
		for (int i = 0; i < size - 1; i++) {
			s += (ids.get(i) + ",");
		}
		s += (ids.get(size - 1) + ")");
		return s;
	}

	/**
	 * 
	 * @param ids
	 * @return
	 */
	private String getIds2(List<Integer> ids) {
		int size = ids.size();
		String s = "(";
		if (ids.size() == 0) {
			return "(0)";
		}

		for (int i = 0; i < size - 1; i++) {
			s += (ids.get(i) + ",");
		}
		s += (ids.get(size - 1) + ")");
		return s;
	}

	/**
	 * This method is implemented to get the table name and return the id of the
	 * last record;
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public int getMaxIdFromTable(String tableName) throws SQLException {
		String sql = "select MAX(id) from " + tableName;
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		int id = 0;
		while (rs.next()) {
			id = rs.getInt(1);
		}
		rs.close();
		return id;
	}

	public ArrayList<Article> getAuthorArticles(int user_id)
			throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from articles where user_id = "
						+ user_id);
		return getArticlesFromResultSet(rs);

	}

	/**
	 * 
	 * @param title
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Article> getArticlesByTitle(String title)
			throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from articles where status = 'published' and title = '"
						+ title + "'");

		return getArticlesFromResultSet(rs);

	}

	// public ArrayList<User> getAuthors(int articleId) throws SQLException {
	// ArrayList<User> users = new ArrayList<User>();
	// Statement stst = conn.createStatement();
	// ResultSet rs = stst
	// .executeQuery("select * from article_authors where user_id = "+articleId);
	// while(rs.next()){
	// User user = new User();
	// user.sete
	// }
	// return users;
	//
	// }

	/**
	 * This method implemented to enable readers to search an article by author
	 * name. Since there are two tables for authors in the
	 * system(user,article_authors) both tables should be searched"
	 * 
	 * @param authorName
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Article> getArticlesByAuthorName(String authorName)
			throws SQLException {
		Statement stst = conn.createStatement();
		ArticleAuthorTable articleAuthorTable = new ArticleAuthorTable(conn);
		ArrayList<Integer> userIds = articleAuthorTable
				.getUserIdsByName(authorName);
		ArrayList<Integer> articleIds = articleAuthorTable
				.getArticleIdsByAuthorName(authorName);
		System.out.println("user ids are" + articleIds);
		String ids = getIds2(userIds);
		ResultSet rs = stst
				.executeQuery("select * from articles where status = 'published' and user_id in "
						+ ids);

		while (rs.next()) {
			articleIds.add(rs.getInt("id"));

		}
		HashSet<Integer> hs = new HashSet<Integer>();
		hs.addAll(articleIds);
		articleIds.clear();
		articleIds.addAll(hs);
		String ids2 = getIds2(articleIds);

		ResultSet rs2 = stst
				.executeQuery("select * from articles where status = 'published' and id in "
						+ ids2);

		return getArticlesFromResultSet(rs2);

	}

	public ArrayList<Article> getArticlesByKeywords(String keyword)
			throws SQLException {
		KeywordTable keywordTable = new KeywordTable(conn);
		ArrayList<Integer> articleIds = keywordTable
				.getArticleIdsByKeywrod(keyword);
		String ids = getIds2(articleIds);
		Statement stst = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from articles where status = 'published' and id in "
						+ ids);
		return getArticlesFromResultSet(rs);

	}

	/**
	 * This method is implemented to avoid code duplications
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Article> getArticlesFromResultSet(ResultSet rs)
			throws SQLException {
		ArrayList<Article> articles = new ArrayList<Article>();
		while (rs.next()) {
			Article article = new Article();
			article.setId(rs.getInt("id"));
			article.setUserId(rs.getString("user_id"));
			article.setMainUser(new Account(conn).getUserById(rs
					.getInt("user_id")));
			article.setTitle(rs.getString("title"));
			article.setAbst(rs.getString("abstract"));
			article.setKeywords(new KeywordTable(conn)
					.getKeywordsByArticleId(rs.getInt("id")));
			article.setCreatedAt(rs.getDate("created_at"));
			article.setStatus(rs.getString("status"));
			article.setReview_count(rs.getInt("review_count"));
			article.setEdition(rs.getString("edition"));
			article.setVolume(rs.getString("volume"));
			article.setPdfPath(rs.getString("pdf_path"));
			article.setUsers(new ArticleAuthorTable(conn)
					.getUsersByArticleId(rs.getInt("id")));// each article may
															// have a list of
			article.setForms(new Form(conn).getAuthorReviewForms(rs
					.getInt("id"))); // authors
			articles.add(article);

		}
		rs.close();
		return articles;
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Article> getAllArticles() throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet rs = stst.executeQuery("select * from articles");
		return getArticlesFromResultSet(rs);

	}

	public ArrayList<Article> getArticlesByDate(String startDate, String endDate)
			throws ParseException, SQLException {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		Date sDate = df.parse(startDate);
		Date eDate = df.parse(endDate);
		df.applyPattern("yyyy-MM-dd HH:mm:ss");
		String stDate = df.format(sDate);
		String edDate = df.format(eDate);
		System.out.println(stDate);
		System.out.println(edDate);
		Statement stst = conn.createStatement();
		ResultSet rsg = stst
				.executeQuery("select * from articles where created_at between '"
						+ stDate
						+ "' and '"
						+ edDate
						+ "' and status = 'published' ");
		return getArticlesFromResultSet(rsg);
	}
	/**
	 * updating the article status to published 
	 * @param articleId
	 * @throws SQLException
	 */

	public void publishArticle(int articleId) throws SQLException {

		Statement st = conn.createStatement();
		st.executeUpdate("update articles set status = 'published' where id = "
				+ articleId);
		if(st != null) st.close();

	}
	public boolean checkFileName(String fileName) throws SQLException {
			Statement stst = conn.createStatement();
			ResultSet rs = stst.executeQuery("select * from articles where pdf_path = '"+fileName+"'");
			while(rs.next()){
				return true;
			}
			if(stst != null) stst.close();
			if(rs != null) rs.close();
			return false;

		
	}


// public static void main(String[] args) throws java.text.ParseException {
// try {
// Class.forName("com.mysql.jdbc.Driver");
// String DB =
// "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
// Connection conn = DriverManager.getConnection(DB);
// ArticleTable articleTable = new ArticleTable(conn);
// ArrayList<Article> articles = articleTable.getArticlesByTitle("sample");
// // System.out.println(articles.get(0).getTitle());
// for (Article article : articles) {
// System.out.println(article.getId());
// System.out.println(article.getTitle());
// System.out.println(article.getAbst());
// }
// long DAY_IN_MS = 1000 * 60 * 60 * 24;
// Date date = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
// System.out.println(date);
// for (Article article : articles) {
// //System.out.println(article.get);
// String startDateString = article.getCreatedAt();
// DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
// Date startDate;
// try {
// startDate = df.parse(startDateString);
// String newDateString = df.format(startDate);
// if (date.before(startDate)) {
// System.out.println("hello");
// }
// System.out.println(newDateString);
// } catch (ParseException e) {
// e.printStackTrace();
// }
// }
// // System.out.println(articles.size());
// } catch (ClassNotFoundException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// } catch (SQLException e) {
// // TODO Auto-generated catch block
// e.printStackTrace();
// }
// }





public static void main(String[] args) throws java.text.ParseException {
try {
Class.forName("com.mysql.jdbc.Driver");
String DB =
"jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
Connection conn = DriverManager.getConnection(DB);
ArticleTable articleTable = new ArticleTable(conn);
System.out.println(articleTable.checkFileName("temp.pdf"));

} catch (ClassNotFoundException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
}

}
