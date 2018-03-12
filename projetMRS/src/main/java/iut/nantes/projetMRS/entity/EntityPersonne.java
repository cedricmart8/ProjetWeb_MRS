package iut.nantes.projetMRS.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	private ObjectId 	 	   id;
	private String   	 	   nom;
	private String   	 	   prenom;
	private int      	 	   age;
	private Date         	   dateNaissance;
	private String   	 	   email;
	private String  	 	   adresse;
	private String  	 	   photo;
	private boolean	 	 	   profilPublic;
	private boolean 	 	   localisationPartage;
	private List<String> 	   listePersonneVisiter;
	private List<EntityGenreMusic> 	   interetsMusicaux;
	
	private EntityLocalisation localisation;
	private String             motDePasse;
	
	/**
	 * Constructeur
	 */
	public EntityPersonne(){}
	
	public EntityPersonne(
			String  	 nom,                  //Nom de la personne
			String 	 	 prenom,               //Prenom de la personne
			//int 		 age,                  //Age de la personne
			Date 		 dateNaissance,        //Date de naissance de la personne
			String 		 email,                //Mail de la personne
			String 		 adresse,              //Adresse physique de la personne
			String 		 photo,                //Photo de profil de la personne (url)
			boolean      profilPublic,         //Rend le profil de l'utilisateur publique ou non
			boolean      localisationPartage,  //Active la localisation de l'utilisateur ou non
			List<String> listePersonneVisiter, //Liste des personnes avec les mêmes intérêts musicaux visité
			List<EntityGenreMusic> interetsMusicaux,     //Liste des intérêts musicaux qui plaise a l'utilisateur
			
			//EntityLocalisation localisation, //La localisation de l'utilisateur (null si l'utilisateur n'active pas la localisation), null a la creation
			String 		       motDePasse      //Le mot de passe de connexion de l'utilisateur (cryptage a prevoir en bonus)
			) {
		super();
		this.nom    			  = nom;
		this.prenom 			  = prenom;
		//this.age 				  = age; //Automatiquement calcule dans le service
		this.dateNaissance		  = dateNaissance;
		this.email  			  = email;
		this.adresse 			  = adresse;
		this.photo 				  = photo;
		this.profilPublic 		  = profilPublic;
		this.localisationPartage  = localisationPartage;
		this.listePersonneVisiter = listePersonneVisiter;
		this.interetsMusicaux 	  = interetsMusicaux;
		
		//this.localisation       = localisation; //Non prise en compte lors de la creation
		this.motDePasse           = EncodeLeMotDePasse(motDePasse);
	}

	//Encodage du mot de passe, renvoie le mot de passe encodé
	public static String EncodeLeMotDePasse(String key) { //encryptage par md5
		byte[] uniqueKey = key.getBytes();
		byte[] hash = null;
		try {
			hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
		} catch (NoSuchAlgorithmException e) {
			throw new Error("no MD5 support in this VM");
		}
		StringBuffer hashString = new StringBuffer();
		for ( int i = 0; i < hash.length; ++i ) {
			String hex = Integer.toHexString(hash[i]);
			if ( hex.length() == 1 ) {
				hashString.append('0');
				hashString.append(hex.charAt(hex.length()-1));
			} else {
				hashString.append(hex.substring(hex.length()-2));
			}
		}
		return hashString.toString();
	}
	
	//Verification du mot de passe
	public boolean testDuMotDePasseCrypte(String clearTextTestPassword, String encodedActualPassword) throws NoSuchAlgorithmException {
		String encodedTestPassword = EncodeLeMotDePasse(clearTextTestPassword);
		return (encodedTestPassword.equals(encodedActualPassword));
	}
	
	/**
	 * Accesseur
	 */
	public ObjectId     	  getId()                                                    {return id;}

	public List<String> 	  getListePersonneVisiter()                                  {return listePersonneVisiter;}

	public void          	  setListePersonneVisiter(List<String> listePersonneVisiter) {this.listePersonneVisiter = listePersonneVisiter;}

	public void         	  setId(ObjectId id)                                         {this.id = id;}

	public String       	  getNom()                                                   {return nom;}

	public void         	  setNom(String nom)                                         {this.nom = nom;}

	public String       	  getPrenom()                                                {return prenom;}

	public void         	  setPrenom(String prenom)                                   {this.prenom = prenom;}

	public int          	  getAge()                                                   {return age;}

	public void          	  setAge(int age)                                            {this.age = age;}
	
	public Date         	  getDateNaissance()                                         {return dateNaissance;}
	
	public void        	 	  setDateNaissance(Date dateNaissance)                       {this.dateNaissance = dateNaissance;}

	public String      	  	  getEmail()                                                 {return email;}

	public void         	  setEmail(String email)                                     {this.email = email;}

	public String       	  getAdresse()                                               {return adresse;}

	public void         	  setAdresse(String adresse)                                 {this.adresse = adresse;}

	public String       	  getPhoto()                                                 {return photo;}

	public void         	  setPhoto(String photo)                                     {this.photo = photo;}

	public boolean      	  getProfilPublic()                                          {return profilPublic;}

	public void         	  setProfilPublic(boolean profilPublic)                      {this.profilPublic = profilPublic;}

	public boolean      	  getLocalisationPartage()                                   {return localisationPartage;}

	public void         	  setLocalisationPartage(boolean localisationPartage)        {this.localisationPartage = localisationPartage;}

	public List<EntityGenreMusic>   getInteretsMusicaux()        						 {return interetsMusicaux;}

	public void             setInteretsMusicaux(List<EntityGenreMusic> interetsMusicaux) {this.interetsMusicaux = interetsMusicaux;}
	
	public EntityLocalisation getLocalisation()									   		 {return localisation;}
	
	public void               setLocalisation(EntityLocalisation localisation)           {this.localisation = localisation;}
	
	public String       	  getMotDePasse()                                            {return motDePasse;} 

	public void         	  setMotDePasse()                           {this.motDePasse = EncodeLeMotDePasse(this.motDePasse);}	
}
