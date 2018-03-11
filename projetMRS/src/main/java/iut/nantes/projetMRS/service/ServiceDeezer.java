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
	
	public boolean getDataDeezerGenre(){
		System.out.println("Utilisation getDataDeezerGenre()");
		try {
			System.out.println("test2");
	        String myurl= "https://api.deezer.com/genre";

	        URL url = new URL(myurl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.connect();
	        InputStream inputStream = connection.getInputStream();
	        String result = InputStreamOperations.InputStreamToString(inputStream);
	        
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
			return true;
	    } catch (Exception e) {
	    	System.out.println("test3");
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public void addAllGenreToDB() {
		// System.out.println("bdname =>" + client.getDatabaseNames());

		System.out.println("Utilisation addAllGenreToDB()");
		// drop de la table service
		client.getDatabase("service").getCollection("EntityGenreMusic").drop();

		// Les différents genre (source Deezer)
		String jsonDataGenre1 = "{\"id\": 0,\"name\": \"Tous\",\"picture\": \"https://api.deezer.com/genre/0/image\"}";
		String jsonDataGenre2 = "{\"id\": 132,\"name\": \"Pop\",\"picture\": \"https://api.deezer.com/genre/132/image\"}";
		String jsonDataGenre3 = "{\"id\": 457,\"name\": \"Livres audio\",\"picture\": \"https://api.deezer.com/genre/457/image\"}";
		String jsonDataGenre4 = "{\"id\": 116,\"name\": \"Rap/Hip Hop\",\"picture\": \"https://api.deezer.com/genre/116/image\"}";
		String jsonDataGenre5 = "{\"id\": 152,\"name\": \"Rock\", \"picture\": \"https://api.deezer.com/genre/152/image\"}";
		String jsonDataGenre6 = "{\"id\": 113,\"name\": \"Dance\",\"picture\": \"https://api.deezer.com/genre/113/image\"}";
		String jsonDataGenre7 = "{\"id\": 165,\"name\": \"R&B\",\"picture\": \"https://api.deezer.com/genre/165/image\"}";
		String jsonDataGenre8 = "{\"id\": 85,\"name\": \"Alternative\",\"picture\": \"https://api.deezer.com/genre/85/image\"}";
		String jsonDataGenre9 = "{\"id\": 106,\"name\": \"Electro\",\"picture\": \"https://api.deezer.com/genre/106/image\"}";
		String jsonDataGenre10 = "{\"id\": 466,\"name\": \"Folk\",\"picture\": \"https://api.deezer.com/genre/466/image\"}";
		String jsonDataGenre11 = "{\"id\": 144,\"name\": \"Reggae\",\"picture\": \"https://api.deezer.com/genre/144/image\"}";
		String jsonDataGenre12 = "{\"id\": 129,\"name\": \"Jazz\",\"picture\": \"https://api.deezer.com/genre/129/image\"}";
		String jsonDataGenre13 = "{\"id\": 52,\"name\": \"Chanson française\",\"picture\": \"https://api.deezer.com/genre/52/image\"}";
		String jsonDataGenre14 = "{\"id\": 84,\"name\": \"Country\",\"picture\": \"https://api.deezer.com/genre/84/image\"}";
		String jsonDataGenre15 = "{\"id\": 98,\"name\": \"Classique\",\"picture\": \"https://api.deezer.com/genre/98/image\"}";
		String jsonDataGenre16 = "{\"id\": 173,\"name\": \"Films/Jeux vidéo\",\"picture\": \"https://api.deezer.com/genre/173/image\"}";
		String jsonDataGenre17 = "{\"id\": 464,\"name\": \"Metal\",\"picture\": \"https://api.deezer.com/genre/464/image\"}";
		String jsonDataGenre18 = "{\"id\": 169,\"name\": \"Soul & Funk\",\"picture\": \"https://api.deezer.com/genre/169/image\"}";
		String jsonDataGenre19 = "{\"id\": 153,\"name\": \"Blues\",\"picture\": \"https://api.deezer.com/genre/153/image\"}";
		String jsonDataGenre20 = "{\"id\": 95,\"name\": \"Jeunesse\",\"picture\": \"https://api.deezer.com/genre/95/image\"}";
		String jsonDataGenre21 = "{\"id\": 197,\"name\": \"Latino\",\"picture\": \"https://api.deezer.com/genre/197/image\"}";
		String jsonDataGenre22 = "{\"id\": 2,\"name\": \"Musique africaine\",\"picture\": \"https://api.deezer.com/genre/2/image\"}";
		String jsonDataGenre23 = "{\"id\": 12,\"name\": \"Musique arabe\",\"picture\": \"https://api.deezer.com/genre/12/image\"}";
		String jsonDataGenre24 = "{\"id\": 16,\"name\": \"Musique asiatique\",\"picture\": \"https://api.deezer.com/genre/16/image\"}";
		String jsonDataGenre25 = "{\"id\": 75,\"name\": \"Musique brésilienne\",\"picture\": \"https://api.deezer.com/genre/75/image\"}";
		String jsonDataGenre26 = "{\"id\": 81,\"name\": \"Musique indienne\",\"picture\": \"https://api.deezer.com/genre/81/image\"})";

		// Creation du document correspondant au genre
		Document docJson1 = Document.parse(jsonDataGenre1);
		Document docJson2 = Document.parse(jsonDataGenre2);
		Document docJson3 = Document.parse(jsonDataGenre3);
		Document docJson4 = Document.parse(jsonDataGenre4);
		Document docJson5 = Document.parse(jsonDataGenre5);
		Document docJson6 = Document.parse(jsonDataGenre6);
		Document docJson7 = Document.parse(jsonDataGenre7);
		Document docJson8 = Document.parse(jsonDataGenre8);
		Document docJson9 = Document.parse(jsonDataGenre9);
		Document docJson10 = Document.parse(jsonDataGenre10);
		Document docJson11 = Document.parse(jsonDataGenre11);
		Document docJson12 = Document.parse(jsonDataGenre12);
		Document docJson13 = Document.parse(jsonDataGenre13);
		Document docJson14 = Document.parse(jsonDataGenre14);
		Document docJson15 = Document.parse(jsonDataGenre15);
		Document docJson16 = Document.parse(jsonDataGenre16);
		Document docJson17 = Document.parse(jsonDataGenre17);
		Document docJson18 = Document.parse(jsonDataGenre18);
		Document docJson19 = Document.parse(jsonDataGenre19);
		Document docJson20 = Document.parse(jsonDataGenre20);
		Document docJson21 = Document.parse(jsonDataGenre21);
		Document docJson22 = Document.parse(jsonDataGenre22);
		Document docJson23 = Document.parse(jsonDataGenre23);
		Document docJson24 = Document.parse(jsonDataGenre24);
		Document docJson25 = Document.parse(jsonDataGenre25);
		Document docJson26 = Document.parse(jsonDataGenre26);

		// Ajout du document à la base
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson1);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson2);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson3);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson4);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson5);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson6);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson7);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson8);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson9);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson10);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson11);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson12);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson13);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson14);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson15);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson16);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson17);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson18);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson19);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson20);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson21);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson22);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson23);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson24);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson25);
		client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson26);

		System.out.println("Collection EntityGenreMusic à jour");

		// verification des données de la collection "EntityGenreMusic"
		FindIterable<Document> iterable = client.getDatabase("service").getCollection("EntityGenreMusic").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}
}
