package backend;

import java.util.List;

public class NewsAdapter{
	private List<News> theNews;

	public void setNews(List<News> theNews){
		this.theNews = theNews;
	}

	public List<News> getNews(){
		return theNews;
	}

	public static NewsAdapter createNew(List<News> theNews){
		NewsAdapter na = new NewsAdapter();
		na.setNews(theNews);
		return na;
	}
}