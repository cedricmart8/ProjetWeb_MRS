package iut.nantes.projetMRS.entity;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class EntityPersonne {
	
	/**
	 * Attributs
	 */
	@Id
	private ObjectId id;
	private String nom;
	private String prenom;
	private int age;
	private Date dateNaissance;
	private String email;
	private String adresse;
	private String photo;
	private boolean profilPublic;
	private boolean localisation;
	private ArrayList<EntityPersonne> profilVisite;
	private ArrayList<EntityPersonne> interetsMusicaux;
	
	/**
	 * Constructeur
	 */
	public EntityPersonne(){}

	public EntityPersonne(ObjectId id, String nom, String prenom, int age, Date dateNaissance, String email,
			String adresse, String photo, boolean profilPublic, boolean localisation,
			ArrayList<EntityPersonne> profilVisite, ArrayList<EntityPersonne> interetsMusicaux) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.dateNaissance = dateNaissance;
		this.email = email;
		this.adresse = adresse;
		this.photo = photo;
		this.profilPublic = profilPublic;
		this.localisation = localisation;
		this.profilVisite = profilVisite;
		this.interetsMusicaux = interetsMusicaux;
	}

	/**
	 * Accesseur
	 */
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public Date getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean getProfilPublic() {
		return profilPublic;
	}

	public void setProfilPublic(boolean profilPublic) {
		this.profilPublic = profilPublic;
	}

	public boolean getLocalisation() {
		return localisation;
	}

	public void setLocalisation(boolean localisation) {
		this.localisation = localisation;
	}

	public ArrayList<EntityPersonne> getProfilVisite() {
		return profilVisite;
	}

	public void setProfilVisite(ArrayList<EntityPersonne> profilVisite) {
		this.profilVisite = profilVisite;
	}

	public ArrayList<EntityPersonne> getInteretsMusicaux() {
		return interetsMusicaux;
	}

	public void setInteretsMusicaux(ArrayList<EntityPersonne> interetsMusicaux) {
		this.interetsMusicaux = interetsMusicaux;
	}
	
	
	
	
}
