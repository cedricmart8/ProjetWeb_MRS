package iut.nantes.projetMRS.entity;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class EntityPersonne {
	
	/**
	 * Attributs
	 */
	@Id
	private ObjectId 	 id;
	private String   	 nom;
	private String   	 prenom;
	private int      	 age;
	private Date         dateNaissance;
	private String   	 email;
	private String  	 adresse;
	private String  	 photo;
	private boolean	 	 profilPublic;
	private boolean 	 localisation;
	private List<String> listePersonneVisiter;
	private List<Object> interetsMusicaux;
	
	/**
	 * Constructeur
	 */
	public EntityPersonne(){}
	
	public EntityPersonne(
			String  	 nom,                  //Nom de la personne
			String 	 	 prenom,               //Prenom de la personne
			int 		 age,                  //Age de la personne
			Date 		 dateNaissance,        //Date de naissance de la personne
			String 		 email,                //Mail de la personne
			String 		 adresse,              //Adresse physique de la personne
			String 		 photo,                //Photo de profil de la personne (url)
			boolean      profilPublic,         //Rend le profil de l'utilisateur publique ou non
			boolean      localisation,         //Active la localisation de l'utilisateur ou non
			List<String> listePersonneVisiter, //Liste des personnes avec les mêmes intérêts musicaux visité
			List<Object> interetsMusicaux      //Liste des intérêts musicaux qui plaise a l'utilisateur
			) {
		super();
		this.nom    			  = nom;
		this.prenom 			  = prenom;
		this.age 				  = age;
		this.dateNaissance		  = dateNaissance;
		this.email  			  = email;
		this.adresse 			  = adresse;
		this.photo 				  = photo;
		this.profilPublic 		  = profilPublic;
		this.localisation 		  = localisation;
		this.listePersonneVisiter = listePersonneVisiter;
		this.interetsMusicaux 	  = interetsMusicaux;
	}


	/**
	 * Accesseur
	 */
	public ObjectId     getId()                                                    {return id;}

	public List<String> getListePersonneVisiter()                                  {return listePersonneVisiter;}

	public void         setListePersonneVisiter(List<String> listePersonneVisiter) {this.listePersonneVisiter = listePersonneVisiter;}

	public void         setId(ObjectId id)                                         {this.id = id;}

	public String       getNom()                                                   {return nom;}

	public void         setNom(String nom)                                         {this.nom = nom;}

	public String       getPrenom()                                                {return prenom;}

	public void         setPrenom(String prenom)                                   {this.prenom = prenom;}

	public int          getAge()                                                   {return age;}

	public void         setAge(int age)                                            {this.age = age;}
	
	public Date         getDateNaissance()                                         {return dateNaissance;}

	public void         setDateNaissance(Date dateNaissance)                       {this.dateNaissance = dateNaissance;}

	public String       getEmail()                                                 {return email;}

	public void         setEmail(String email)                                     {this.email = email;}

	public String       getAdresse()                                               {return adresse;}

	public void         setAdresse(String adresse)                                 {this.adresse = adresse;}

	public String       getPhoto()                                                 {return photo;}

	public void         setPhoto(String photo)                                     {this.photo = photo;}

	public boolean      getProfilPublic()                                          {return profilPublic;}

	public void         setProfilPublic(boolean profilPublic)                      {this.profilPublic = profilPublic;}

	public boolean      getLocalisation()                                          {return localisation;}

	public void         setLocalisation(boolean localisation)                      {this.localisation = localisation;}

	public List<Object> getInteretsMusicaux()                                      {return interetsMusicaux;}

	public void         setInteretsMusicaux(List<Object> interetsMusicaux)         {this.interetsMusicaux = interetsMusicaux;}
	
	
	
	
}
