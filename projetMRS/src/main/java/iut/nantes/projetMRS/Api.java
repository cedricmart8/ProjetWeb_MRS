package iut.nantes.projetMRS;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import iut.nantes.projetMRS.entity.EntityGenreMusic;
import iut.nantes.projetMRS.entity.EntityPersonne;
import iut.nantes.projetMRS.service.ServiceDeezer;
import iut.nantes.projetMRS.service.ServicePersonne;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.put;
import static spark.Spark.port;
import static spark.Spark.options;
import static spark.Spark.before;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Api {
	public static ServicePersonne servicePersonne = new ServicePersonne();
	public static ServiceDeezer serviceDeezer     = new ServiceDeezer();
	
	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args) throws InterruptedException{
		
		Gson gson=  new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
		port(8082);
		
		options("/*",
	        (request, response) -> {

	            String accessControlRequestHeaders = request
	                    .headers("Access-Control-Request-Headers");
	            if (accessControlRequestHeaders != null) {
	                response.header("Access-Control-Allow-Headers",
	                        accessControlRequestHeaders);
	            }

	            String accessControlRequestMethod = request
	                    .headers("Access-Control-Request-Method");
	            if (accessControlRequestMethod != null) {
	                response.header("Access-Control-Allow-Methods",
	                        accessControlRequestMethod);
	            }

	            return "OK";
	        });

		before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
		
		//Pause de 3 secondes pour laisser le temps à la connexion de bien se faire
		TimeUnit.SECONDS.sleep(3);
		
		//recuperation des genre depuis deezer
		serviceDeezer.getDataDeezerGenre();
		
		//ajoutUserAdmin
		ArrayList<String> list = new ArrayList<>(); list.add("None");
		ArrayList<EntityGenreMusic> list2 = new ArrayList<>(); list2.add(new EntityGenreMusic(1,"None","noPicture"));
		EntityPersonne admin = new EntityPersonne(
				"admin", "admin", 
				new Date(1990,10,10), "admin",
				"admin", "admin",
				true, true,
				list, list2,
				"admin"
				);
		servicePersonne.addPersonne(admin);
		
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
		get("/allPersonne", (req, res) -> {
			res.type("application/json");
			return servicePersonne.getAllPersonne();
		}, gson ::toJson);
		
		
		/**
		 * GET recuperer le JSON d un user avec dans le HEADER l id de ce user
		 */
		get("/user", (req, res) -> {
			res.type("application/json");
			String email = gson.toJson(req.headers("email")).replace("\"", "");
			return servicePersonne.getPersonne(email);
		}, gson ::toJson);
		
		
		/**
		 * DELETE suprime le user dont l id est dans le HEADER
		 */
		delete("/deleteUser", (req, res) -> {
			res.type("application/json");
			String email = gson.toJson(req.headers("email")).replace("\"", "");		
			return servicePersonne.delete(email);
		}, gson ::toJson);
		
		/**
		 * PUT modif le user 
		 * Id + les attributs a modifier dans le HEADER
		 */
		put("/modifUser", (req, res) -> {
			res.type("application/json");
			
			String nom = gson.toJson(req.headers("nom")).replace("\"", "");
			String prenom = gson.toJson(req.headers("prenom")).replace("\"", "");
			String date = gson.toJson(req.headers("dateNaissance")).replace("\"", "");
			String email = gson.toJson(req.headers("email")).replace("\"", "");
			String adresse = gson.toJson(req.headers("adresse")).replace("\"", "");
			String photo = gson.toJson(req.headers("photo")).replace("\"", "");
			String profilPublic = gson.toJson(req.headers("profilPublic")).replace("\"", "");
			String localisation = gson.toJson(req.headers("localisationPartage")).replace("\"", "");
			
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

			return servicePersonne.modifUser(nom, prenom, dateNaissance, email, adresse, photo, profilEnvoi, localisationEnvoi);
		}, gson ::toJson);
		
		/**
		 * GET recupere le profil d'une personne puis l'ajoute a sa liste de visite
		 */
		put("/addPersonneVisiter", (req, res) -> {
			res.type("application/json");
			
			String PersonneConnecter = gson.toJson(req.headers("emailPersonneConnecter")).replace("\"", "");
			
			String PersonneVisiter = gson.toJson(req.headers("emailPersonneVisiter")).replace("\"", "");
			
			return servicePersonne.addPersonneVisiter(PersonneConnecter, PersonneVisiter);
		}, gson ::toJson);
		
		/**
		 * GET recupère le genre musical puis l'ajoute a l'utilisateur
		 */
		put("/addGenreMusical", (req, res) -> {
			res.type("application/json");
			
			String mailUtilisateur = gson.toJson(req.headers("email")).replace("\"", "");
			
			String idGenreMusical = gson.toJson(req.headers("idGenreMusical")).replace("\"", "");
			int idConvertiGenreMusical = Integer.parseInt(idGenreMusical); //On converti l'id (normalement string) en int
			
			return servicePersonne.addInteretMusical(mailUtilisateur, idConvertiGenreMusical);
		}, gson ::toJson);
		
		get("/allGenreMusical", (req, res) -> {
			res.type("application/json");
			
			return serviceDeezer.getAllGenreDB();
		}, gson ::toJson);
		
		get("/connexion", (req, res) -> {
			res.type("application/json");
			String email = gson.toJson(req.headers("email")).replace("\"", "");
			String mdp = gson.toJson(req.headers("mdp")).replace("\"", "");
			
			return servicePersonne.connexion(email, mdp);
		}, gson ::toJson);
	}
}
