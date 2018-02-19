package iut.nantes.projetMRS.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

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
}
