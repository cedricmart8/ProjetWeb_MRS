package iut.nantes.projetMRS.entity;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class EntityPersonne {
	
	@Id
	private ObjectId id;
	private String nom;
	private String prenom;
	private String age;
	
	public EntityPersonne(){}
	
	public EntityPersonne(String nom, String prenom, String age) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	
}
