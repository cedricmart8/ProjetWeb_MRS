package iut.nantes.projetMRS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import iut.nantes.projetMRS.entity.EntityPersonne;
import iut.nantes.projetMRS.service.ServicePersonne;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.put;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bson.types.ObjectId;

public class Api {
	public static ServicePersonne servicePersonne = new ServicePersonne();
	
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args){
		
//		Gson gson = new Gson();
		Gson gson=  new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		
		/**
		 * POST ajouter un utilisateur avec dans le body le JSON du nouveau user
		 * Dans le BODY le json complet de la personne
		 */
		post("/add", (req, res) -> {
			res.type("application/json");
			EntityPersonne personne = gson.fromJson(req.body(), EntityPersonne.class);
			return servicePersonne.addPersonne(personne);
		}, gson ::toJson);
		
		/**
		 * GET recuperer la list de tout les users
		 */
		get("/", (req, res) -> {
			res.type("application/json");
			return servicePersonne.getAllPersonne();
		}, gson ::toJson);
		
		
		/**
		 * GET recuperer le JSON d un user avec dans le HEADER l id de ce user
		 */
		get("/user", (req, res) -> {
			res.type("application/json");
			//Replace pour supprimer les " renvoyer par la requête pour permettre la creation de notre objet
			String id = gson.toJson(req.headers("id")).replace("\"", "");
			System.err.println("Id : " + id);
			ObjectId ObjectId = new ObjectId(id);
			return servicePersonne.getPersonne(ObjectId);
		}, gson ::toJson);
		
		
		/**
		 * DELETE suprime le user dont l id est dans le HEADER
		 */
		delete("/deleteUser", (req, res) -> {
			res.type("application/json");
			String idDelete = gson.toJson(req.headers("id")).replace("\"", "");
			ObjectId DeleteById = new ObjectId(idDelete);			
			return servicePersonne.delete(DeleteById);
		}, gson ::toJson);
		
		/**
		 * PUT modif le user 
		 * Id + les attributs a modifier dans le HEADER
		 */
		put("/modifUser", (req, res) -> {
			res.type("application/json");
			
			String idModif = gson.toJson(req.headers("id")).replace("\"", "");
			ObjectId ModifById = new ObjectId(idModif);
			String nom = gson.toJson(req.headers("nom")).replace("\"", "");
			String prenom = gson.toJson(req.headers("prenom")).replace("\"", "");
			String date = gson.toJson(req.headers("dateNaissance")).replace("\"", "");
			String email = gson.toJson(req.headers("email")).replace("\"", "");
			String adresse = gson.toJson(req.headers("adresse")).replace("\"", "");
			String photo = gson.toJson(req.headers("photo")).replace("\"", "");
			String profilPublic = gson.toJson(req.headers("profilPublic")).replace("\"", "");
			String localisation = gson.toJson(req.headers("localisation")).replace("\"", "");
			
			Date dateNaissance = null;
			Boolean profilEnvoi = null;
			Boolean localisationEnvoi = null;
			
			//verifi si la date est au bon format jj/mm/aaaa
			if (date.equals("null") == false){
				try {
					dateFormat.parse(date);
					dateNaissance = dateFormat.parse(date);
				}catch (Exception e){
					System.err.println("pas le bon format date");
				}
			}
			
			profilEnvoi = Boolean.parseBoolean(profilPublic);
			localisationEnvoi = Boolean.parseBoolean(localisation);

			return servicePersonne.modifUser(ModifById, nom, prenom, dateNaissance, email, adresse, photo, profilEnvoi, localisationEnvoi);
		}, gson ::toJson);
		
		get("/addPersonneVisiter", (req, res) -> {
			res.type("application/json");
			
			String idPersonneConnecter = gson.toJson(req.headers("idPersonneConnecter")).replace("\"", "");
			ObjectId personneConnecter = new ObjectId(idPersonneConnecter);
			
			String idPersonneVisiter = gson.toJson(req.headers("idPersonneVisiter")).replace("\"", "");
			ObjectId personneVisiter = new ObjectId(idPersonneVisiter);
			
			return servicePersonne.addPersonneVisiter(personneConnecter, personneVisiter);
		}, gson ::toJson);
	}
}