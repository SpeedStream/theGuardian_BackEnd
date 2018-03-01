package backend;

public class News{

	private String mSectionName;

	public News(String sectionName){
		mSectionName = sectionName;
	}

	public String getSectionName(){
		return mSectionName;
	}

	public News(){
		this.mSectionName = "";
	}
}