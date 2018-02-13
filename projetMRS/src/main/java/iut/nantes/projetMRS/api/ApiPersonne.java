package iut.nantes.projetMRS.api;

import com.google.gson.Gson;

import iut.nantes.projetMRS.entity.EntityPersonne;
import iut.nantes.projetMRS.service.ServicePersonne;

import static spark.Spark.get;
import static spark.Spark.post;

public class ApiPersonne {
	public static ServicePersonne servicePersonne = new ServicePersonne();
	
	public static void main(String[] args){
		
		EntityPersonne personne2 = new EntityPersonne("DUBERNET", "Samuel", "34");
		servicePersonne.addPersonne(personne2);
		
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
	}
}
