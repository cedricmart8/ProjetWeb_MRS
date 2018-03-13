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
	 * @return String ("Personne added")
	 */
	public String addPersonne(EntityPersonne personne) {
		EntityPersonne p = datastore.find(EntityPersonne.class).field("email").equal(personne.getEmail()).get();
		if (p == null) {
			personne.setMotDePasse();
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
	public EntityPersonne getPersonne(String email) {
		try{
			EntityPersonne p = datastore.find(EntityPersonne.class).field("email").equal(email).get();
			return p;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * supprime une personne (ID de la personne en parametre)
	 * 
	 * @param deleteById
	 * @return String ("Personne deleted")
	 */
	public String delete(String email) {
		try{
			EntityPersonne p1 = datastore.find(EntityPersonne.class).field("email").equal(email).get();

			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("email")
					.equal(email);
			datastore.delete(query);

			return ("Personne deleted : Nom : " + p1.getNom() + " " + p1.getPrenom() + " Age : " + p1.getAge());
		}catch(Exception e){
			System.out.println(e.getMessage());
			return ("Error while deleting a person");
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
	 * @return String ("Personne Updated")
	 */
	public String modifUser(String nom, String prenom, Date dateNaissance, String email,
			String adresse, String photo, Boolean profilPublic, Boolean localisationPartage) {
		try{
			EntityPersonne p1 = datastore.find(EntityPersonne.class).field("email").equal(email).get();
	
			String newNom = nom;
			String newPrenom = prenom;
			Date newDateNaissance = dateNaissance;
	//		String newEmail = email;
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
	//		if (newEmail.equals("null"))
	//			newEmail = p1.getEmail();
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
			return ("Personne Updated");
		}catch(Exception e){
			System.out.println(e.getMessage());
			return "Error while updating personne";
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
	 * @return String ("Personne visiter !")
	 */
	public String addPersonneVisiter(String emailPersonneConnecter, String emailPersonneVisiter) {
		EntityPersonne pConnecter = datastore.find(EntityPersonne.class).field("email").equal(emailPersonneConnecter).get();
		EntityPersonne pVisiter = datastore.find(EntityPersonne.class).field("email").equal(emailPersonneVisiter).get();
		
		if (pConnecter.getEmail().equalsIgnoreCase(pVisiter.getEmail())) {
			return ("Se visite soit meme, pas d ajout dans la liste");
		} else {
			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("email")
					.equal(emailPersonneConnecter);
			UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class)
					.addToSet("listePersonneVisiter", emailPersonneVisiter);
			datastore.update(query, ops);

			return ("Personne visiter !");
		}
	}

	public String addInteretMusical(String email, int idGenreMusical) {
		try{
			EntityPersonne pUtilisateur = datastore.find(EntityPersonne.class).field("email").equal(email).get(); // recupere l'utilisateur courant
			EntityGenreMusic genreMusical = datastore.find(EntityGenreMusic.class).field("_id").equal(idGenreMusical).get();
			
			System.out.println("Nom : " + genreMusical.getName() + " _ " + genreMusical.getId() + "----------");
			
//			boolean valExistante = false;
//			for (EntityGenreMusic gM : pUtilisateur.getInteretsMusicaux()) {
//				System.out.println("Nom : " + gM.getName() + " _ " + gM.getId());
//				if (gM.getId() == genreMusical.getId()) {
//					valExistante = true;
//				} else {
//					valExistante = false;
//				}
//			}
//			// Si l'interet est déjà présent on ne l'ajoute pas à la liste sinon on l'ajoute
//			if (valExistante == true) {
//				return ("Interet deja present dans la liste");
//			} else {
				Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("_id")
						.equal(pUtilisateur);
				UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class)
						.addToSet("interetsMusicaux", genreMusical);
				datastore.update(query, ops);

				return ("Interet musical ajouter");
//			}
		}catch(Exception e){
			System.out.println(e.getMessage());
			return "Error while adding Genre";
		}
	}
}
