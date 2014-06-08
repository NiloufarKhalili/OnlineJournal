package beans;

import java.util.ArrayList;

public class Mistake {

	private int id;
	private int articleId;
	private int reviewerId;
	private int mistakeId;
	private ArrayList<Error> errors;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(int reviewerId) {
		this.reviewerId = reviewerId;
	}

	public int getMistakeId() {
		return mistakeId;
	}

	public void setMistakeId(int mistakeId) {
		this.mistakeId = mistakeId;
	}

	public ArrayList<Error> getErrors() {
		return errors;
	}

	public void setErrors(ArrayList<Error> errors) {
		this.errors = errors;
	}

}
