package iut.nantes.projetMRS.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.MongoClient;

import iut.nantes.projetMRS.entity.EntityGenreMusic;
import iut.nantes.projetMRS.entity.EntityPersonne;

public class ServicePersonne {
	/**
	 * Connection a mongoDB Host : localhost | Port : 8081
	 * Selection de la collection service sur mongoDB
	 */
	MongoClient client = new MongoClient("localhost", 8081); //connect to mongodb
	Datastore datastore = new Morphia().createDatastore(client, "service"); //select service collection

	/**
	 * ajoute une personne dans la bdd
	 * @param personne
	 * @return String ("Personne added")
	 */
	public String addPersonne(EntityPersonne personne){
		datastore.save(personne);
		ageByDateNaissance(personne.getDateNaissance(), personne.getId());
		return ("Personne added");
	}
	
	/**
	 * retourne toute les personnes de la BDD
	 * @return List de toute les personnes
	 */
	public List<EntityPersonne> getAllPersonne(){
		List<EntityPersonne> listPersonne = datastore.find(EntityPersonne.class).asList();
		
		if (listPersonne != null){ 
			return listPersonne;
		} else { 
			return null; 
		}
	}

	/**
	 * retourne une personne (ID de la personne en parametre)
	 * @param id
	 * @return EntityPersonne
	 */
	public EntityPersonne getPersonne(ObjectId id) {
		EntityPersonne p = datastore.find(EntityPersonne.class).field("_id").equal(id).get();
		return p;
	}

	/**
	 * supprime une personne (ID de la personne en parametre)
	 * @param deleteById
	 * @return String ("Personne deleted")
	 */
	public String delete(ObjectId deleteById) {
		
		EntityPersonne p1 = datastore.find(EntityPersonne.class).field("_id").equal(deleteById).get();
		
		Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id").equal(deleteById);
		datastore.delete(query);	
		
		return ("Personne deleted : Nom : " + p1.getNom() + " " + p1.getPrenom() + " Age : " + p1.getAge());
	}

	/**
	 * modifie une personne (avec en parametre toute les informations a modifier)
	 * @param idModif
	 * @param nom
	 * @param prenom
	 * @param age
	 * @return String ("Personne Updated")
	 */
	public String modifUser(ObjectId idModif, String nom, String prenom, Date dateNaissance, 
			String email, String adresse, String photo, Boolean profilPublic, Boolean localisationPartage) {
		
		EntityPersonne p1 = datastore.find(EntityPersonne.class).field("_id").equal(idModif).get();
		
		String  newNom 				   = nom;
		String  newPrenom 			   = prenom;
		Date    newDateNaissance 	   = dateNaissance;
		String  newEmail 			   = email;
		String  newAdresse 			   = adresse;
		String  newPhoto 			   = photo;
		Boolean newProfilPublic        = profilPublic;
		Boolean newLocalisationPartage = localisationPartage;
		
		if (newNom.equals("null"))           { newNom = p1.getNom(); }
		if (newPrenom.equals("null"))        { newPrenom = p1.getPrenom(); }
		if (newDateNaissance == null)        { newDateNaissance = p1.getDateNaissance(); }		
		if (newEmail.equals("null"))         { newEmail = p1.getEmail(); }
		if (newAdresse.equals("null"))       { newAdresse = p1.getAdresse(); }
		if (newPhoto.equals("null"))         { newPhoto = p1.getPhoto(); }
		if (newProfilPublic == null)         { newProfilPublic = p1.getProfilPublic(); }
		if (newLocalisationPartage == null)  { newLocalisationPartage = p1.getLocalisationPartage(); }
		
		Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id").equal(idModif);
		UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class)
				.set("nom",                 newNom)
				.set("prenom",              newPrenom)
				.set("dateNaissance",       newDateNaissance)
				.set("email",               newEmail)
				.set("adresse",             newAdresse)
				.set("photo",               newPhoto)
				.set("profilPublic",        newProfilPublic)
				.set("localisationPartage", newLocalisationPartage);
		datastore.update(query, ops);
		
		ageByDateNaissance(newDateNaissance, p1.getId());
		return ("Personne Updated");
	}
	
	/**
	 * update age depuis la date de naissance
	 * @param dateNaissance
	 * @return
	 */
	public int ageByDateNaissance(Date dateNaissance, ObjectId id){
		EntityPersonne p = datastore.find(EntityPersonne.class).field("_id").equal(id).get();
		
		int age = p.getAge();
		Calendar curr = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		
		birth.setTime(dateNaissance);
		int yeardiff = curr.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		curr.add(Calendar.YEAR,-yeardiff);
		if(birth.after(curr))
		{
		  age = yeardiff - 1;
		}
		
		Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id").equal(id);
		UpdateOperations<EntityPersonne> ops =  datastore.createUpdateOperations(EntityPersonne.class).set("age", age);
		datastore.update(query, ops);
		
		p.setAge(age);
		return age;
	}
	
	/**
	 * ajoute la personne visiter dans la liste
	 * @param personneConnecter
	 * @param personneVisiter
	 * @return String ("Personne visiter !")
	 */
	public String addPersonneVisiter(ObjectId idPersonneConnecter, ObjectId idPersonneVisiter) {
		
		EntityPersonne pConnecter = datastore.find(EntityPersonne.class).field("_id").equal(idPersonneConnecter).get();
		EntityPersonne pVisiter = datastore.find(EntityPersonne.class).field("_id").equal(idPersonneVisiter).get();
		
		System.err.println("ajout de soit meme : "+(pConnecter.getEmail().equalsIgnoreCase(pVisiter.getEmail())));
		if (pConnecter.getEmail().equalsIgnoreCase(pVisiter.getEmail())) 
		{
			return ("Se visite soit meme, pas d ajout dans la liste");
		} else 
		{
			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id").equal(idPersonneConnecter);
			//add est deprecated, si addToSet ne fonctionne pas alors ==> //@SuppressWarnings("deprecation")
			UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class).addToSet("listePersonneVisiter", idPersonneVisiter);
			datastore.update(query, ops);
		
			return ("Personne visiter !");
		}
	}
	
	public String addInteretMusical(ObjectId idUtilisateur, int idGenreMusical){
		EntityPersonne pUtilisateur = datastore.find(EntityPersonne.class).field("_id").equal(idUtilisateur).get(); //recupere l'utilisateur courant
		EntityGenreMusic genreMusical = datastore.find(EntityGenreMusic.class).field("id").equal(idGenreMusical).get();
		
		boolean valExistante = false;
		for(EntityGenreMusic gM : pUtilisateur.getInteretsMusicaux()){
			if(gM.equals(genreMusical)){
				valExistante = true;
			}
			else {
				valExistante = false;
			}
		}
		//Si l'interet est déjà présent on ne l'ajoute pas à la liste sinon on l'ajoute
		if(valExistante == true){
			return ("Interet deja present dans la liste");
		} else {
			Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id").equal(pUtilisateur);
			UpdateOperations<EntityPersonne> ops = datastore.createUpdateOperations(EntityPersonne.class).addToSet("interetsMusicaux", genreMusical);
			datastore.update(query, ops);
			
			return ("Interet musical ajouter");
		}
	}
	
}
