package iut.nantes.projetMRS.api;

import com.google.gson.Gson;

import iut.nantes.projetMRS.entity.EntityPersonne;
import iut.nantes.projetMRS.service.ServicePersonne;

import static spark.Spark.get;
import static spark.Spark.post;

import org.bson.types.ObjectId;

public class Api {
	public static ServicePersonne servicePersonne = new ServicePersonne();
	
	public static void main(String[] args){
		
		Gson gson = new Gson();
		post("/add", (req, res) -> {
			res.type("application/json");
			EntityPersonne personne = gson.fromJson(req.body(), EntityPersonne.class);
			return servicePersonne.addPersonne(personne);
		}, gson ::toJson);
		
		get("/", (req, res) -> {
			res.type("application/json");
			return servicePersonne.getAllPersonne();
		}, gson ::toJson);
		
		get("/user", (req, res) -> {
			res.type("application/json");
			String id = gson.toJson(req.headers("id")).replace("\"", "");
//			String id = "5a8ac6acdff0f51c2430c6bd";
			
			System.err.println("Id : " + id);
			
			ObjectId ObjectId = new ObjectId(id);
			
			return servicePersonne.getPersonne(ObjectId);
		}, gson ::toJson);
	}
}
