package assignment.business;

public class Article {
	
	private String id;
	private String title;
	private String contents;
	private String dateCreated;
	
	
	public Article(String id, String title, String contents, String dateCreated) {
		setId(id);
		setTitle(title);
		setContents(contents);
		setDateCreated(dateCreated);
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
}