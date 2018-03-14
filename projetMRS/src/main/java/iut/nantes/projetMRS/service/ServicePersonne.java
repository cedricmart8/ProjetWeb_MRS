package iut.nantes.projetMRS.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;

import iut.nantes.projetMRS.entity.EntityGenreMusic;
import iut.nantes.projetMRS.entity.EntityPersonne;

public class ServicePersonne {
	/**
	 * Connection a mongoDB Host : localhost | Port : 8081 Selection de la
	 * collection service sur mongoDB
	 */
	MongoClient client = new MongoClient("localhost", 8081); // connect to mongodb
	Datastore datastore = new Morphia().createDatastore(client, "service"); // select service collection

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
	 * ajoute une personne dans la bdd
	 * 
	 * @param personne
	 * @return 
	 * @return String ("Personne added")
	 */
	public /*String*/ JsonElement addPersonne(EntityPersonne personne) {
		try{
			EntityPersonne p = datastore.find(EntityPersonne.class).field("email").equal(personne.getEmail()).get();
			if (p == null) {
				
				datastore.save(personne);
				ageByDateNaissance(personne.getDateNaissance(), personne.getId());
				return new JsonParser().parse("Personne added");
			} else {
				return new JsonParser().parse("User already create !");
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			return new JsonParser().parse("Error while adding a person");
		}
		
	}

	/**
	 * retourne toute les personnes de la BDD
	 * @return 
	 * 
	 * @return List de toute les personnes
	 */
	public /*List<EntityPersonne>*/ JsonElement getAllPersonne() {
		System.out.println("test0");
		List<EntityPersonne> listPersonne = datastore.find(EntityPersonne.class).asList();

		System.out.println("test1");
		if (listPersonne != null) {
			System.out.println("test2");
			return /*listPersonne*/new JsonParser().parse(listPersonne.toString());
		} else {
			System.out.println("test3");
			return null;
		}
	}

	/**
	 * retourne une personne (ID de la personne en parametre)
	 * 
	 * @param id
	 * @return 
	 * @return EntityPersonne
	 */
	public EntityPersonne /*JsonElement*/ getPersonne(String email) {
		try{
			EntityPersonne p = datastore.find(EntityPersonne.class).field("email").equal(email).get();
			//probleme de test
			return p /*new JsonParser().parse(p.toString())*/;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null /*new JsonParser().parse("null")*/;
		}
		
	}
	
	/**
	 * supprime une personne (ID de la personne en parametre)
	 * 
	 * @param deleteById
	 * @return 
	 * @return String ("Personne deleted")
	 */
	public /*String*/ JsonElement delete(String email) {
		try{
			EntityPersonne p1 = datastore.find(EntityPersonne.class).field("email").equal(email).get();

			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("email")
					.equal(email);
			datastore.delete(query);

			return new JsonParser().parse("Personne deleted : Nom : " + p1.getNom() + " " + p1.getPrenom() + " Age : " + p1.getAge());
		}catch(Exception e){
			System.out.println(e.getMessage());
			return new JsonParser().parse("Error while deleting a person");
		}
		
	}

	/**
	 * modifie une personne (avec en parametre toute les informations a
	 * modifier)
	 * 
	 * @param idModif
	 * @param nom
	 * @param prenom
	 * @param age
	 * @return 
	 * @return String ("Personne Updated")
	 */
	public /*String*/ JsonElement modifUser(String nom, String prenom, Date dateNaissance, String email,
			String adresse, String photo, Boolean profilPublic, Boolean localisationPartage) {
		try{
			EntityPersonne p1 = datastore.find(EntityPersonne.class).field("email").equal(email).get();
	
			String newNom = nom;
			String newPrenom = prenom;
			Date newDateNaissance = dateNaissance;
			String newAdresse = adresse;
			String newPhoto = photo;
			Boolean newProfilPublic = profilPublic;
			Boolean newLocalisationPartage = localisationPartage;
	
			if (newNom.equals("null"))
				newNom = p1.getNom();
			if (newPrenom.equals("null"))
				newPrenom = p1.getPrenom();
			if (newDateNaissance == null)
				newDateNaissance = p1.getDateNaissance();
			if (newAdresse.equals("null"))
				newAdresse = p1.getAdresse();
			if (newPhoto.equals("null"))
				newPhoto = p1.getPhoto();
			if (newProfilPublic == null)
				newProfilPublic = p1.getProfilPublic();
			if (newLocalisationPartage == null)
				newLocalisationPartage = p1.getLocalisationPartage();
	
			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("email")
					.equal(email);
			UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class).set("nom", newNom)
					.set("prenom", newPrenom).set("dateNaissance", newDateNaissance).set("email", email)
					.set("adresse", newAdresse).set("photo", newPhoto).set("profilPublic", newProfilPublic)
					.set("localisationPartage", newLocalisationPartage);
			datastore.update(query, ops);
	
			ageByDateNaissance(newDateNaissance, p1.getId());
			return new JsonParser().parse("Personne Updated");
		}catch(Exception e){
			System.out.println(e.getMessage());
			return new JsonParser().parse("Error while updating personne");
		}
	}

	/**
	 * update age depuis la date de naissance
	 * 
	 * @param dateNaissance
	 * @return l'age
	 */
	public int ageByDateNaissance(Date dateNaissance, ObjectId id) {
		EntityPersonne p = datastore.find(EntityPersonne.class).field("_id").equal(id).get();

		int age = p.getAge();
		Calendar curr = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();

		birth.setTime(dateNaissance);
		int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		curr.add(Calendar.YEAR, -yeardiff);
		if (birth.after(curr)) {
			age = yeardiff - 1;
		}

		Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id")
				.equal(id);
		UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class).set("age", age);
		datastore.update(query, ops);

		p.setAge(age);
		return age;
	}

	/**
	 * ajoute la personne visiter dans la liste
	 * 
	 * @param personneConnecter
	 * @param personneVisiter
	 * @return 
	 * @return String ("Personne visiter !")
	 */
	public /*String*/ JsonElement addPersonneVisiter(String emailPersonneConnecter, String emailPersonneVisiter) {
		EntityPersonne pConnecter = datastore.find(EntityPersonne.class).field("email").equal(emailPersonneConnecter).get();
		EntityPersonne pVisiter = datastore.find(EntityPersonne.class).field("email").equal(emailPersonneVisiter).get();
		
		if (pConnecter.getEmail().equalsIgnoreCase(pVisiter.getEmail())) {
			return new JsonParser().parse("Se visite soit meme, pas d ajout dans la liste");
		} else {
			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("email")
					.equal(emailPersonneConnecter);
			UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class)
					.addToSet("listePersonneVisiter", emailPersonneVisiter);
			datastore.update(query, ops);

			return new JsonParser().parse("Personne visiter !");
		}
	}

	/**
	 * Methode qui permet d'ajoute un genre a un utilisateur
	 * @param email de l'utilisateur
	 * @param idGenreMusical du genre souhaité
	 * @return 
	 * @return un string correspondant au succès ou non de la requête
	 */
	public /*String*/ JsonElement addInteretMusical(String email, int idGenreMusical) {
		try{
			System.out.println("idGenreMusical =====> " + idGenreMusical);
			
			EntityPersonne pUtilisateur = datastore.find(EntityPersonne.class).field("email").equal(email).get(); // recupere l'utilisateur courant.
			EntityGenreMusic genreMusical = datastore.find(EntityGenreMusic.class).field("_id").equal(idGenreMusical).get();
			
			System.out.println("PUtilisateur.email ====> " + pUtilisateur.getEmail());
			
			System.out.println("GenreMusical.id =====> " + genreMusical.getName());
			System.out.println("GenreMusical.name =====> " + genreMusical.getId());
			System.out.println("GenreMusical.picture =====> " + genreMusical.getPicture());
			
			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("email")
					.equal(email);
			UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class)
					.addToSet("interetsMusicaux", genreMusical);
			datastore.update(query, ops);

			return new JsonParser().parse("Interet musical ajouter");
				
		}catch(Exception e){
			System.out.println(e.getMessage());
			return new JsonParser().parse("Error while adding Genre");
		}
	}

	/**
	 * Methode qui permet de connecter un utilisateur si existant
	 * @param email de connexion
	 * @param mdp de connexion
	 * @return 
	 * @return la personne connectée si mdp correspond à celui de la bd, sinon retourne null
	 */
	public /*EntityPersonne*/ JsonElement connexion(String email, String mdp) {
		try {
			EntityPersonne utilisateur = datastore.find(EntityPersonne.class).field("email").equal(email).get();
			if (utilisateur.getMotDePasse().equals(mdp)){
				return new JsonParser().parse(utilisateur.toString());
			} else {
				return /*null*/new JsonParser().parse("null");
			}
		}catch(Exception e){
			System.out.println("Error while connecting !");
			return /*null*/new JsonParser().parse("null");
		}
	}
}
