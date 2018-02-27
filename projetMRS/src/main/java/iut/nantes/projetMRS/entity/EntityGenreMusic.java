package iut.nantes.projetMRS.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class EntityGenreMusic {
	
	/**
	 * Attributs
	 */
	@Id
	private int    idGenre; //"id": "132",
	private String nomGenre; // "name": "Pop",
	private String lienImageGenre; //"picture":"https://api.deezer.com/genre/132/image",
	
	/**
	 * Constructeur
	 */
	public EntityGenreMusic(){}
	
	public EntityGenreMusic(int idGenre, String nomGenre, String lienImageGenre) {
		super();
		this.idGenre        = idGenre;
		this.nomGenre       = nomGenre;
		this.lienImageGenre = lienImageGenre;
	}

	public int    getIdGenre()                             {return idGenre;}

	public void   setIdGenre(int idGenre)                  {this.idGenre = idGenre;}

	public String getNomGenre()                            {return nomGenre;}

	public void   setNomGenre(String nomGenre)             {this.nomGenre = nomGenre;}

	public String getLienImageGenre()                      {return lienImageGenre;}

	public void   setLienImageGenre(String lienImageGenre) {this.lienImageGenre = lienImageGenre;}
}