package iut.nantes.projetMRS.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
import iut.nantes.projetMRS.entity.EntityGenreMusic;

public class ServiceDeezer {
	/**
	 * client lié à mongoDB
	 * datastore lié au stockage des services
	 */
	MongoClient client = new MongoClient("localhost", 8081); // connect to mongodb
	Datastore datastore = new Morphia().createDatastore(client, "service"); // select service collection
	
	/**
	 * Getter et Setter
	 */
	public MongoClient getClient() {
		return client;
	}

	public void setClient(MongoClient client) {
		this.client = client;
	}

	public Datastore getDatastore() {
		return datastore;
	}

	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}

	/**
	 * Fonction qui appel l'API deezer et recupère tous les genres pour les stockés dans la table EntityGenreMusic de mongoDb
	 * @return un boolean, true si les données ont bien été récupéré, false sinon
	 */
	public boolean getDataDeezerGenre(){
		System.out.println("Utilisation getDataDeezerGenre()");
		try {
			// System.out.println("test2");
	        String myurl= "https://api.deezer.com/genre";

	        URL url = new URL(myurl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.connect();
	        InputStream inputStream = connection.getInputStream();
	        String result = InputStreamOperations.InputStreamToString(inputStream);
	        
	        String result2 = result.replace("data", "EntityGenreMusic");
	        JsonObject jsonObject = new JsonParser().parse(result2).getAsJsonObject();
	        // System.out.println("jsonObject:=> "+jsonObject);
	        
	        jsonObject.remove("picture_small");        
	        jsonObject.remove("picture_medium");
	        jsonObject.remove("picture_big");
	        jsonObject.remove("picture_xl");
	        jsonObject.remove("type");
	        
	        JsonArray jsonArray = jsonObject.getAsJsonArray("EntityGenreMusic");
	        
	        //Drop de la table
	        client.getDatabase("service").getCollection("EntityGenreMusic").drop();
	        
	        // verification des données de la collection "EntityGenreMusic"
 			System.out.println("|=================| COLLECTION ENTITYGENREMUSIC |=================|");
 			FindIterable<Document> iterable = client.getDatabase("service").getCollection("EntityGenreMusic").find();
 			for (Document document : iterable) {
 				System.out.println("document => " + document);
 			}
	        System.out.println("Data removed");
	        for(int i = 0 ; i < jsonArray.size(); i++){
	        	JsonElement jsonElement = jsonArray.get(i);
	        	jsonElement.getAsJsonObject().remove("picture_small");
	        	jsonElement.getAsJsonObject().remove("picture_medium");
	        	jsonElement.getAsJsonObject().remove("picture_big");
	        	jsonElement.getAsJsonObject().remove("picture_xl");
	        	jsonElement.getAsJsonObject().remove("type");
	        	//Document docJson = Document.parse(jsonArray.get(i).toString());
	        	//client.getDatabase("service").getCollection("EntityGenreMusic").insertOne(docJson);
	        	
	        	EntityGenreMusic egm = new EntityGenreMusic(
	        			Integer.parseInt(jsonElement.getAsJsonObject().get("id").toString()), 
	        			jsonElement.getAsJsonObject().get("name").toString(),
	        			jsonElement.getAsJsonObject().get("picture").toString()
	        			);
	        	if(egm.getId() == 0)
	        		System.out.println(egm.getName());
	        	else
		        	datastore.save(egm);

	        } 
	        
	        // verification des données de la collection "EntityGenreMusic"
			System.out.println("|=================| COLLECTION ENTITYGENREMUSIC |=================|");
			iterable = client.getDatabase("service").getCollection("EntityGenreMusic").find();
			for (Document document : iterable) {
				System.out.println("document => " + document);
			}
			
			// verification des données de la collection "EntityPersonne"
			System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
			iterable = client.getDatabase("service").getCollection("EntityPersonne").find();
			for (Document document : iterable) {
				System.out.println("document => " + document);
			}
			return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}

	/**
	 * Fonction qui renvoie tous les genres stocké depuis la base de donnée
	 * @return tous les genres sous forme de liste
	 */
	public Object getAllGenreDB() {
		FindIterable<Document> iterable = client.getDatabase("service").getCollection("EntityGenreMusic").find();
		ArrayList<Document> listMusique = new ArrayList<Document>();
		for (Document document : iterable) {
			listMusique.add(document);
		}
		return listMusique;
	}
}
