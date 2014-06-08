package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Article;
import beans.Keyword;

public class KeywordTable {
	
	private Connection conn;

	public KeywordTable(Connection conn) {
		super();
		this.conn = conn;
	}
	
	public KeywordTable() {
		
	}
	/**
	 * This method checks if the keyword exists in the keywords table
	 * 
	 * @param word
	 * @return
	 * @throws SQLException
	 */
	public boolean checkKeyword(String word) throws SQLException {
		String sql = "select count(*) as count from keywords where word=? ";

		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, word);
		ResultSet rs = stmt.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt("count");
		}
		rs.close();
		if (count == 0) {
			return false;
		} else {

			return true;
		}
	}
	public void insertIntoKeywords(Article article) throws SQLException {
		//String[] keyWords = article.getKeywords().split(",");
//		for (String word : keyWords) {
			for(Keyword key: article.getKeywords()){
			if (!checkKeyword(key.getWord())) {
				String sql = "insert into keywords(word) values(?) ";

				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, key.getWord());

				stmt.executeUpdate();
				stmt.close();
			}
			String sql = "insert into article_keywords(keyword_id,article_id) values(?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, String.valueOf(getKeywordId(key.getWord())));
			//stmt.setString(2, String.valueOf(getArticleId(article.getTitle())));
			stmt.setString(2, String.valueOf(article.getId()));
			stmt.executeUpdate();
			stmt.close();
		}
	}
	/**
	 * This method get a keyword and return the id of that word in the keywords table
	 * @param word
	 * @return
	 * @throws SQLException
	 */
	public int getKeywordId(String word) throws SQLException {
		String sql = "select * from keywords where word=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, word);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			int id = Integer.valueOf(rs.getString("id"));
			return id;
		}
		rs.close();
		return 0;

	}
	public ArrayList<Integer> getArticleIdsByKeywrod(String word) throws SQLException {
		ArrayList<Integer> articleIds = new ArrayList<Integer>();
		Statement stst = conn.createStatement();
		String[] words = word.split(",");
		for(String w:words){
		int id = getKeywordId(w);
		ResultSet rs = stst
				.executeQuery("select * from article_keywords where keyword_id = "+ id);
		while(rs.next()){
			int articleId = rs.getInt("article_id");
			if(!articleIds.contains(articleId))
			articleIds.add(articleId);
		}
		rs.close();
		}
		return articleIds;
						
	}
	public ArrayList<Keyword> getKeywordsByArticleId(int articleId) throws SQLException {
		ArrayList<Integer> keywordIds = new ArrayList<Integer>();
		Statement stst = conn.createStatement();
		ResultSet st = stst.executeQuery("select * from article_keywords where article_id = "+ articleId);
		while(st.next()){
			keywordIds.add(st.getInt("keyword_id"));
		}
		st.close();
		
		ArrayList<Keyword> keywords = new ArrayList<Keyword>();
		String ids = getIds(keywordIds);
		Statement statement = conn.createStatement();
		ResultSet rss = statement.executeQuery("select * from keywords where id in "+ids);
		while(rss.next()){
			Keyword keyword = new Keyword();
			keyword.setId(rss.getInt("id"));
			keyword.setWord(rss.getString("word"));
			keywords.add(keyword);
		}
		rss.close();
		return keywords;
		
	}

	private String getIds(List<Integer> ids) {
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
	public ArrayList<Keyword> getKeywords(String text) {
		String[] keywords = text.split(",");
		ArrayList<Keyword> keys = new ArrayList<Keyword>();
		for(String key:keywords){
			Keyword w = new Keyword();
			w.setWord(key);
			keys.add(w);
			
		}
		return keys;

	}

}
