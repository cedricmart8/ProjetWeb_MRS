package iut.nantes.projetMRS.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;

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

	/**
	 * ajoute une personne dans la bdd
	 * 
	 * @param personne
	 * @return String ("Personne added")
	 */
	public String addPersonne(EntityPersonne personne) {
		EntityPersonne p = datastore.find(EntityPersonne.class).field("email").equal(personne.getEmail()).get();
		if (p == null) {
			datastore.save(personne);
			ageByDateNaissance(personne.getDateNaissance(), personne.getId());
			return ("Personne added");
		} else {
			return ("User already create !");
		}
	}

	/**
	 * retourne toute les personnes de la BDD
	 * 
	 * @return List de toute les personnes
	 */
	public List<EntityPersonne> getAllPersonne() {
		List<EntityPersonne> listPersonne = datastore.find(EntityPersonne.class).asList();

		if (listPersonne != null) {
			return listPersonne;
		} else {
			return null;
		}
	}

	/**
	 * retourne une personne (ID de la personne en parametre)
	 * 
	 * @param id
	 * @return EntityPersonne
	 */
	public EntityPersonne getPersonne(ObjectId id) {
		EntityPersonne p = datastore.find(EntityPersonne.class).field("_id").equal(id).get();
		return p;
	}

	/**
	 * supprime une personne (ID de la personne en parametre)
	 * 
	 * @param deleteById
	 * @return String ("Personne deleted")
	 */
	public String delete(ObjectId deleteById) {

		EntityPersonne p1 = datastore.find(EntityPersonne.class).field("_id").equal(deleteById).get();

		Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id")
				.equal(deleteById);
		datastore.delete(query);

		return ("Personne deleted : Nom : " + p1.getNom() + " " + p1.getPrenom() + " Age : " + p1.getAge());
	}

	/**
	 * modifie une personne (avec en parametre toute les informations a
	 * modifier)
	 * 
	 * @param idModif
	 * @param nom
	 * @param prenom
	 * @param age
	 * @return String ("Personne Updated")
	 */
	public String modifUser(ObjectId idModif, String nom, String prenom, Date dateNaissance, String email,
			String adresse, String photo, Boolean profilPublic, Boolean localisationPartage) {

		EntityPersonne p1 = datastore.find(EntityPersonne.class).field("_id").equal(idModif).get();

		String newNom = nom;
		String newPrenom = prenom;
		Date newDateNaissance = dateNaissance;
		String newEmail = email;
		String newAdresse = adresse;
		String newPhoto = photo;
		Boolean newProfilPublic = profilPublic;
		Boolean newLocalisationPartage = localisationPartage;

		if (newNom.equals("null")) {
			newNom = p1.getNom();
		}
		if (newPrenom.equals("null")) {
			newPrenom = p1.getPrenom();
		}
		if (newDateNaissance == null) {
			newDateNaissance = p1.getDateNaissance();
		}
		if (newEmail.equals("null")) {
			newEmail = p1.getEmail();
		}
		if (newAdresse.equals("null")) {
			newAdresse = p1.getAdresse();
		}
		if (newPhoto.equals("null")) {
			newPhoto = p1.getPhoto();
		}
		if (newProfilPublic == null) {
			newProfilPublic = p1.getProfilPublic();
		}
		if (newLocalisationPartage == null) {
			newLocalisationPartage = p1.getLocalisationPartage();
		}

		Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id")
				.equal(idModif);
		UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class).set("nom", newNom)
				.set("prenom", newPrenom).set("dateNaissance", newDateNaissance).set("email", newEmail)
				.set("adresse", newAdresse).set("photo", newPhoto).set("profilPublic", newProfilPublic)
				.set("localisationPartage", newLocalisationPartage);
		datastore.update(query, ops);

		ageByDateNaissance(newDateNaissance, p1.getId());
		return ("Personne Updated");
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
	 * @return String ("Personne visiter !")
	 */
	public String addPersonneVisiter(ObjectId idPersonneConnecter, ObjectId idPersonneVisiter) {

		EntityPersonne pConnecter = datastore.find(EntityPersonne.class).field("_id").equal(idPersonneConnecter).get();
		EntityPersonne pVisiter = datastore.find(EntityPersonne.class).field("_id").equal(idPersonneVisiter).get();

		System.err.println("ajout de soit meme : " + (pConnecter.getEmail().equalsIgnoreCase(pVisiter.getEmail())));
		if (pConnecter.getEmail().equalsIgnoreCase(pVisiter.getEmail())) {
			return ("Se visite soit meme, pas d ajout dans la liste");
		} else {
			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id")
					.equal(idPersonneConnecter);
			// add est deprecated, si addToSet ne fonctionne pas alors ==>
			// //@SuppressWarnings("deprecation")
			UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class)
					.addToSet("listePersonneVisiter", idPersonneVisiter);
			datastore.update(query, ops);

			return ("Personne visiter !");
		}
	}

	public String addInteretMusical(ObjectId idUtilisateur, int idGenreMusical) {
		EntityPersonne pUtilisateur = datastore.find(EntityPersonne.class).field("_id").equal(idUtilisateur).get(); // recupere
																													// l'utilisateur
																													// courant
		EntityGenreMusic genreMusical = datastore.find(EntityGenreMusic.class).field("id").equal(idGenreMusical).get();

		boolean valExistante = false;
		for (EntityGenreMusic gM : pUtilisateur.getInteretsMusicaux()) {
			if (gM.equals(genreMusical)) {
				valExistante = true;
			} else {
				valExistante = false;
			}
		}
		// Si l'interet est déjà présent on ne l'ajoute pas à la liste sinon on
		// l'ajoute
		if (valExistante == true) {
			return ("Interet deja present dans la liste");
		} else {
			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id")
					.equal(pUtilisateur);
			UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class)
					.addToSet("interetsMusicaux", genreMusical);
			datastore.update(query, ops);

			return ("Interet musical ajouter");
		}
	}

	public void addAllGenreToDB() {
		// System.out.println("bdname =>" + client.getDatabaseNames());

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
