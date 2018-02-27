package iut.nantes.projetMRS.entity;

import org.mongodb.morphia.annotations.Entity;

@Entity
public class EntityLocalisation {
	
	/**
	 * Attributs
	 */
	private float latitude;  //Latitude de l'utilisateur (point)
	private float longitude; //Longitude de l'utilisateur (point)
	private float precision; //Precision du point géographique /!\ Représente le rayon d'un cercle autour du point géographique
	
	/**
	 * Constructeur
	 */
	public EntityLocalisation(){}
	
	public EntityLocalisation(
			float latitude, 
			float longitude, 
			float precision
			) {
		super();
		this.latitude  = latitude;
		this.longitude = longitude;
		this.precision = precision;
	}

	public float getLattitude()               {return latitude;}

	public void setLattitude(float latitude)  {this.latitude = latitude;}

	public float getLongitude()               {return longitude;}

	public void setLongitude(float longitude) {this.longitude = longitude;}

	public float getPrecision()               {return precision;}

	public void setPrecision(float precision) {this.precision = precision;}
}
