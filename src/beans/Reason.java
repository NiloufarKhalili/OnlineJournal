package beans;

import java.util.ArrayList;

public class Reason {

	private int id;
	private int formId;
	private int reviewerId;
	private String title;
	private ArrayList<Comment> comments;
	public Reason() {
		
	}
	

	public Reason(int id, int formId, int reviewerId, String title) {
		super();
		this.id = id;
		this.formId = formId;
		this.reviewerId = reviewerId;
		this.title = title;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public int getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(int reviewerId) {
		this.reviewerId = reviewerId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public ArrayList<Comment> getComments() {
		return comments;
	}


	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}
	

}
