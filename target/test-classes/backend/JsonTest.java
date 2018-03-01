package com.carlos.demo.rest.api;

import org.junit.Test;
import static org.junit.Assert.*;
import com.carlos.demo.rest.api.model.Earthquake;
import com.carlos.demo.rest.api.service.JsonReader;
import com.carlos.demo.rest.api.service.JsonToObject;

import static org.junit.Assert.assertEquals;

import java.util.List;

public class JunitTests {
	
	@Test
	public void JsonTest(){
		String jsonExpected = "{\"mensaje\":\"hola mundo\"}";
		JsonReader jsonclass = new JsonReader("https://api.myjson.com/bins/qwawv");
		String jsonResult = "";
		try{
			jsonResult = jsonclass.readJsonFromUrl();
		}catch(Exception e){
			System.out.println(e);
		}
		
		assertEquals(jsonExpected, jsonResult);
		
	}
	
	@Test
	public void EarthquakeList(){
		String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2017-06-20&endtime=2017-06-25&orderby=time-asc&minmagnitude=6";
		String jsontext = "";
		JsonReader json = new JsonReader(url);
		
		
		try{
			jsontext = json.readJsonFromUrl();
		}catch(Exception e){
			jsontext = "{\"exception\":\"" + e + "\"}";
		}
		
		List<Earthquake> earthquake = JsonToObject.extractFeatureFromJson(jsontext);

		Earthquake a = new Earthquake(6.8, 
				"23km SW of Puerto San Jose, Guatemala",
				1498134664490L,
				"https://earthquake.usgs.gov/earthquakes/eventpage/us20009p1a",
				13.7527,
				-90.9488);
		assertEquals(a.getLocation(), earthquake.get(0).getLocation());
		assertEquals(a.getTimeInMilliseconds(), earthquake.get(0).getTimeInMilliseconds());
		
		List<Earthquake> earthquake2 = JsonToObject.extractFeatureFromJson("");
		
		assertEquals(null, earthquake2);
	}	
	
}
