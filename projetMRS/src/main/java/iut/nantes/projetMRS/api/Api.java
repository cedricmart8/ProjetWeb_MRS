package iut.nantes.projetMRS.api;

import com.google.gson.Gson;

import iut.nantes.projetMRS.entity.EntityPersonne;
import iut.nantes.projetMRS.service.ServicePersonne;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;

import org.bson.types.ObjectId;

public class Api {
	public static ServicePersonne servicePersonne = new ServicePersonne();
	
	public static void main(String[] args){
		
		Gson gson = new Gson();
		
		//POST ajouter un utilisateur avec dans le body le JSON du nouveau user
		post("/add", (req, res) -> {
			res.type("application/json");
			EntityPersonne personne = gson.fromJson(req.body(), EntityPersonne.class);
			return servicePersonne.addPersonne(personne);
		}, gson ::toJson);
		
		//GET recuperer la list de tout les user
		get("/", (req, res) -> {
			res.type("application/json");
			return servicePersonne.getAllPersonne();
		}, gson ::toJson);
		
		
		//GET recuperer le JSON d un user avec dans le HEADER l id de ce user
		get("/user", (req, res) -> {
			res.type("application/json");
			//Replace pour supprimer les " renvoyer par la requête pour permettre la creation de notre objet
			String id = gson.toJson(req.headers("id")).replace("\"", "");
			System.err.println("Id : " + id);
			ObjectId ObjectId = new ObjectId(id);
			return servicePersonne.getPersonne(ObjectId);
		}, gson ::toJson);
		
		
		//DELETE suprime le user dont l id est dans le HEADER
		delete("/deleteUser", (req, res) -> {
			res.type("application/json");
			String idDelete = gson.toJson(req.headers("id")).replace("\"", "");
			ObjectId DeleteById = new ObjectId(idDelete);			
			return servicePersonne.delete(DeleteById);
		}, gson ::toJson);
	}
}
