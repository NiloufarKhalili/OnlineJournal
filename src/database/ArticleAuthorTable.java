package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeSet;

import beans.Article;
import beans.User;

public class ArticleAuthorTable {
	
	private Connection conn;

	public ArticleAuthorTable(Connection con) {
		super();
		this.conn = con;
	}

	public ArticleAuthorTable(){
		
	}
	
	public ArrayList<User> getUsersByArticleId(int articleId) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		Statement stst = conn.createStatement();
		ResultSet rs = stst.executeQuery("select * from article_authors where article_id = "+ articleId);
		while(rs.next()) {
			User user = new User();
			user.setEmail(rs.getString("email"));
			user.setFirstname(rs.getString("firstname"));
			user.setLastname(rs.getString("lastname"));
			user.setAffiliation(rs.getString("affiliations"));
			users.add(user);
		
	}
		return users;
	}
	/**
	 * For searching an article by author name it is needed to search for users in the article_authors table. this method returns article ids;
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getArticleIdsByAuthorName(String name) throws SQLException {
		TreeSet<Integer> articleIds = new TreeSet<Integer>();
		Statement stst = conn.createStatement();
		ResultSet rs = stst.executeQuery("select * from article_authors where firstname = '"+ name+"' Or lastname = '"+name+"'");
		while(rs.next()) {
			articleIds.add(rs.getInt("article_id"));
		}
		ArrayList<Integer> ids = new ArrayList<Integer>(articleIds);
		return ids;
	}
	
	public ArrayList<Integer> getUserIdsByName(String name) throws SQLException {
		ArrayList<Integer> userIds = new ArrayList<Integer>();
		Statement stst = conn.createStatement();
		ResultSet rs = stst.executeQuery("select * from users where first_name = '"+ name+"' Or last_name = '"+name+"'");
		while(rs.next()) {
			userIds.add(rs.getInt("id"));
		}
		
		return userIds;
	}

}
