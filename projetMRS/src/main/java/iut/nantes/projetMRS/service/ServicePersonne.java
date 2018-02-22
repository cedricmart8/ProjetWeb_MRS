package iut.nantes.projetMRS.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.MongoClient;
import com.mongodb.operation.UpdateOperation;

import iut.nantes.projetMRS.entity.EntityPersonne;

public class ServicePersonne {
	MongoClient client = new MongoClient("localhost", 27017); //connect to mongodb
	Datastore datastore = new Morphia().createDatastore(client, "service"); //select service collection

	public String addPersonne(EntityPersonne personne){
		datastore.save(personne);
		return ("personne added"); 
	}
	
	public List<EntityPersonne> getAllPersonne(){
		 List<EntityPersonne> listPersonne = datastore.find(EntityPersonne.class).asList();
		if(listPersonne != null){
			return listPersonne;
		}
		
		return null;
	}

	public EntityPersonne getPersonne(ObjectId id) {
		
		EntityPersonne p = datastore.find(EntityPersonne.class).field("_id").equal(id).get();
		return p;
	}

	public String delete(ObjectId deleteById) {
		
		EntityPersonne p1 = datastore.find(EntityPersonne.class).field("_id").equal(deleteById).get();
		
		Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id").equal(deleteById);
		datastore.delete(query);	
		
		return ("Personne deleted : Nom : " + p1.getNom() + " " + p1.getPrenom() + " Age : " + p1.getAge());
	}

	public String modifUser(ObjectId idModif, String nom, String prenom, String age) {
		String newName = nom;
		String newPrenom = prenom;
		String newAge = age;
		
		EntityPersonne p1 = datastore.find(EntityPersonne.class).field("_id").equal(idModif).get();
		
		if (newName.equals("null")){ newName = p1.getNom(); }
		
		if (newPrenom.equals("null")){ newPrenom = p1.getPrenom(); }
			
		if (newAge.equals("null")){ newAge = p1.getAge(); }
		
		try {
			Integer.parseInt(newAge);
		}catch (NumberFormatException e){
			newAge = p1.getAge();
			System.err.println("Age not a number");
		}
		
		Query<EntityPersonne> query = datastore.createQuery(EntityPersonne.class).disableValidation().field("id").equal(idModif);
		
		UpdateOperations<EntityPersonne> ops =  datastore.createUpdateOperations(EntityPersonne.class).set("nom", newName).set("prenom", newPrenom).set("age", newAge);
		
		datastore.update(query, ops);
		
		return ("Personne Updated");
	}
}
