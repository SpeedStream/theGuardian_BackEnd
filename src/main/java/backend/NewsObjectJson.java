package backend;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NewsObjectJson {

    public static List<News> extractFeatureFromJson(String newsJSON) {
    	
        if (newsJSON == "") {
            return null;
        }
        List<News> news = new ArrayList<News>();
        try {

            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject newsObject = baseJsonResponse.getJSONObject("response");

            JSONArray newsArray = newsObject.getJSONArray("results");
            System.out.println("Arrayln: " + newsArray.length());
            for (int i = 0; i < newsArray.length(); i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);

                //String sectionId = currentNews.getString("sectionId");    //Depreciated 22/02/2018
                String sectionName = currentNews.getString("sectionName");  
                //String pillarName = currentNews.getString("pillarName");  //Depreciated 22/02/2018

                //News theNews = new News(sectionId, sectionName, pillarName);  //Depreciated 22/02/2018
                News theNews = new News(sectionName);

                news.add(theNews);
            }

        } catch (JSONException e) {
        }
        System.out.println("Returning");
        // Return the list of news
        return news;
    }

	
}