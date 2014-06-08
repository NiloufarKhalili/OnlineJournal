package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import beans.Error;
import beans.ReviewForm;


public class MistakeDB {
	private Connection conn;

	public MistakeDB(Connection conn) {
		this.conn = conn;
	}

	public MistakeDB() {

	}
	
	public ResultSet getMistakesByForm(int reviewer_id,int article_id) throws SQLException
	{
		Statement stmt = conn.createStatement();
		String sql = "select a.id,a.article_id,a.reviewer_id,a.mistake_id,b.mistake from mistake_list as a,mistake as b where a.mistake_id=b.id and reviewer_id='"+reviewer_id+"' and article_id='"+article_id+"'";
		System.out.println("getMistakesByForm sql"+sql);
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}
	
	public void approveMistake(int reviewer_id,int article_id,int mistake_id) throws SQLException
	{
		Statement st = conn.createStatement();
		String sqlString = "delete from mistake_list where reviewer_id = '"
				+ reviewer_id + "' and article_id='" +article_id+ "' and mistake_id='"
				+ mistake_id + "'";
		System.out.println("approveMistake sql"+sqlString);
		st.execute(sqlString);
		st.close();
	}
	
	public void insertIntoError(int article_id, int reviewer_id,String mistake) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "select count(*) as count from mistake where mistake='"+ mistake +"'";
		String sqlTemp = "";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		if(rs.getInt("count")>0)
		{
			sql = "select * from mistake where mistake='"+mistake+"'";
			ResultSet resultSet = stmt.executeQuery(sql);
			resultSet.next();
			//TODO exist mistake
			int mistake_id = resultSet.getInt("id");
			sqlTemp = "select count(*) as count from mistake_list where mistake_id='"
					+ mistake_id
					+ "' and reviewer_id='"
					+ reviewer_id
					+ "' and article_id='" + article_id + "'";
			ResultSet reSet = stmt.executeQuery(sqlTemp);
			reSet.next();
			if (reSet.getInt("count") == 0) {
				sqlTemp = "insert into mistake_list (article_id,reviewer_id,mistake_id) values('"
						+ article_id
						+ "','"
						+ reviewer_id
						+ "','"
						+ mistake_id
						+ "')";
				stmt.execute(sqlTemp);
			}
		}
		else {
			//TODO insert new mistake
			sqlTemp = "insert into mistake (mistake) values ('"+mistake +"')";
			stmt.execute(sqlTemp);
			sqlTemp = "select * from mistake where mistake='"+mistake+"'";
			ResultSet rSet = stmt.executeQuery(sqlTemp);
			rSet.next();
			int mistake_id = rSet.getInt("id");
			sqlTemp = "insert into mistake_list (article_id,reviewer_id,mistake_id) values('"
					+ article_id
					+ "','"
					+ reviewer_id
					+ "','"
					+ mistake_id
					+ "')";
			stmt.execute(sqlTemp);
		}
		stmt.close();
	}
	public ArrayList<Error> getListOfErrors(int articleId,int reviewerId) throws SQLException {
		ArrayList<Error> errors = new ArrayList<Error>();
		Statement stst = conn.createStatement();
		Statement stst2 = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from mistake_list where article_id = "
						+ articleId + " and reviewer_id = " + reviewerId);
	while(rs.next()){
		 
		ResultSet rs2 = stst2.executeQuery("select * from mistake where id = "+rs.getInt("mistake_id"));
		while(rs2.next()){
			Error error = new Error(rs2.getInt("id"),rs2.getString("mistake"));
			errors.add(error);
		}
		if(rs2!=null)rs2.close();
	}
	if(rs != null)rs.close();
	if(stst !=null)stst.close();
	if(stst2!=null)stst2.close();
	
	return errors;
		
	}
	 public static void main(String[] args) throws java.text.ParseException {
	 try {
	 Class.forName("com.mysql.jdbc.Driver");
	 String DB =
	 "jdbc:mysql://stusql.dcs.shef.ac.uk/team107?user=team107&password=8b8ba518";
	 Connection conn = DriverManager.getConnection(DB);
	 MistakeDB m = new MistakeDB(conn);
	 
	 for(ReviewForm item:new Form(conn).getAuthorReviewForms(21)){
		 if(item.getErrors().size() != 0){
		 for(Error i :item.getErrors())
		 System.out.println(i.getError());
		 }
	 }
	 
	 
	 } catch (ClassNotFoundException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 } catch (SQLException e) {
	 // TODO Auto-generated catch block
	 e.printStackTrace();
	 }
	 }
}
