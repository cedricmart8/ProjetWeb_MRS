package iut.nantes.projetMRS.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;

import iut.nantes.projetMRS.InputStreamOperations;

public class ServiceDeezer {
	
	MongoClient client = new MongoClient("localhost", 8081); // connect to mongodb
	Datastore datastore = new Morphia().createDatastore(client, "service"); // select service collection
	
	public void getDataDeezerGenre(){
		try {
			System.out.println("test2");
	        String myurl= "https://api.deezer.com/genre";

	        URL url = new URL(myurl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.connect();
	        InputStream inputStream = connection.getInputStream();
	        String result = InputStreamOperations.InputStreamToString(inputStream);
	        
	        System.err.println(result);
	        
	        String result2 = result.replace("data", "EntityGenreMusic");
	        JsonObject jsonObject = new JsonParser().parse(result2).getAsJsonObject();
	        System.out.println("jsonObject:=> "+jsonObject);
	        
	        jsonObject.remove("picture_small");        
	        jsonObject.remove("picture_medium");
	        jsonObject.remove("picture_big");
	        jsonObject.remove("picture_xl");
	        jsonObject.remove("type");
	        
	        JsonArray jsonArray = jsonObject.getAsJsonArray("EntityGenreMusic");
	        
	        //Drop de la table
	        client.getDatabase("service").getCollection("EntityGenreMusic").drop();
	        
	        for(int i = 0 ; i < jsonArray.size(); i++){
	        	System.out.println("i = " + i);
	        	JsonElement jsonElement = jsonArray.get(i);
	        	jsonElement.getAsJsonObject().remove("picture_small");
	        	jsonElement.getAsJsonObject().remove("picture_medium");
	        	jsonElement.getAsJsonObject().remove("picture_big");
	        	jsonElement.getAsJsonObject().remove("picture_xl");
	        	jsonElement.getAsJsonObject().remove("type");
	        	Document docJson = Document.parse(jsonArray.get(i).toString());
	        	client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson);
	        }          
	        System.out.println("jsonObject:=> "+jsonObject);
	        
	        FindIterable<Document> iterable = client.getDatabase("service").getCollection("EntityGenreMusic").find();
			for (Document document : iterable) {
				System.out.println("document => " + document);
			}
	    } catch (Exception e) {
	    	System.out.println("test3");
	        e.printStackTrace();
	    }
	}
}
