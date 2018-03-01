package backend;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import backend.News;
import backend.JsonReader;
import backend.NewsObjectJson;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.json.JSONArray;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
	String url = "http://content.guardianapis.com/search?production-office=uk&from-date=2017-12-24&to-date=2017-12-30&page-size=100&api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";
	String json = "";
	JsonReader jsr = new JsonReader(url);

	/*Test para verificar tamaño de array != 0 */
	@Test
	public void TestArrayLenght(){
		System.out.println("Running TestArrayLenght...");
		try{
			json = jsr.readJsonFromUrl();
		}catch(Exception e){
			json = "{Exception TestArrayLenght: " + e + "}";
		}
		List<News> arrayTest = NewsObjectJson.extractFeatureFromJson(json);
		assertThat(arrayTest).isNotNull();
	}

	/*Test para verificar uno de los elementos*/
	@Test
	public void TestOneNews(){
		System.out.println("Running TestOneNews...");
		try{
			json = jsr.readJsonFromUrl();
		}catch(Exception e){
			json = "{Exception TestOneNews: " + e + "}";
		}
		List<News> newsTest = NewsObjectJson.extractFeatureFromJson(json);
		News n = new News("Football");
		assertEquals(n.getSectionName(), newsTest.get(0).getSectionName());
	}

	/*Test para verificar url*/
	@Test
	public void TestURL(){
		System.out.println("Running TestURL...");
		String office = "uk";
		String startDate = "2018-02-01";
		String endDate = "2018-02-10";
		String testURL = "http://content.guardianapis.com/search?production-office="+office+"&from-date="+startDate+"&to-date="+endDate+"&page-size=100&api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";

		JsonReader readerURL = new JsonReader(testURL);
		try{
			json = jsr.readJsonFromUrl();
		}catch(Exception e){
			json = "{Exception TestOneNews: " + e + "}";
		}
		List<News> newsURL = NewsObjectJson.extractFeatureFromJson(json);
		assertThat(newsURL).isNotNull();
	}

	/*Test para verificar recepcion de parametros desde servicio por el puerto 8080*/
	@Test
	public void TestHost(){
		System.out.println("Running TestHost...");
		String testHost = "http://localhost:8080/getArticles/all/2018-02-01/2018-02-10/";

		JsonReader readerURL = new JsonReader(testHost);
		try{
			json = jsr.readJsonFromUrl();
		}catch(Exception e){
			json = "{Exception TestOneNews: " + e + "}";
		}
		List<News> newsURL = NewsObjectJson.extractFeatureFromJson(json);
		assertThat(newsURL).isNotNull();
	}	
}
