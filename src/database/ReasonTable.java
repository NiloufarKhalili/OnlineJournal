package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import beans.Reason;


public class ReasonTable {
	private Connection conn;
	
	public ReasonTable() {
		
	}
	public ReasonTable(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * This method is implemented in order to get all the reviewer reasons for a from.
	 * @param article_id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Reason> getFormReasons(int formId) throws SQLException {
		Statement stst = conn.createStatement();
		ResultSet rs = stst
				.executeQuery("select * from reviewer_reason_list where form_id = "
						+ formId);
		ArrayList<Reason> reasons = new ArrayList<Reason>();
		while(rs.next()){
			Reason reason = new Reason();
			reason.setId(rs.getInt("id"));
			reason.setTitle(rs.getString("title"));
			reason.setReviewerId(rs.getInt("reviewer_id"));
			reason.setFormId(rs.getInt("form_id"));
			reason.setComments(new CommentDB(conn).getFormReasonComments(rs.getInt("id")));
			reasons.add(reason);
		
		}
		if(rs!=null)rs.close();
		if(stst!=null)stst.close();
		return reasons;
		
	}

}
