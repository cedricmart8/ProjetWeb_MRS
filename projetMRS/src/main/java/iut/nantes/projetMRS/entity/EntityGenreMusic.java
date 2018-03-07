package iut.nantes.projetMRS.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class EntityGenreMusic {
	
	/**
	 * Attributs
	 */
	@Id
	private int    id;      // Id du genre musical depuis Deezer    | exemple => "id": "132",
	private String name;    // Nom du genre musical depuis Deezer   | exemple => "name": "Pop",
	private String picture; // Image du genre musical depuis Deezer | exemple => "picture":"https://api.deezer.com/genre/132/image",
	
	/**
	 * Constructeur
	 */
	public EntityGenreMusic(){}
	
	public EntityGenreMusic(
			int    id, 
			String name, 
			String picture
			) {
		super();
		this.id      = id;
		this.name    = name;
		this.picture = picture;
	}

	public int    getId()                    {return id;}

	public void   setId(int id)              {this.id = id;}

	public String getName()                  {return name;}

	public void   setName(String name)       {this.name = name;}

	public String getPicture()               {return picture;}

	public void   setPicture(String picture) {this.picture = picture;}
}