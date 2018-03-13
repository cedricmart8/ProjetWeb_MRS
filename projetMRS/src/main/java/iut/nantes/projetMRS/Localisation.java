package iut.nantes.projetMRS;

public class Localisation {
	
	/**
	 * Attributs
	 */
	private float latitude;  //Latitude de l'utilisateur (point)
	private float longitude; //Longitude de l'utilisateur (point)
	
	/**
	 * Constructeur
	 */	
	public Localisation(
			float latitude, 
			float longitude
			) {
		super();
		this.latitude  = latitude;
		this.longitude = longitude;
	}

	public float getLattitude()               {return latitude;}

	public void setLattitude(float latitude)  {this.latitude = latitude;}

	public float getLongitude()               {return longitude;}

	public void setLongitude(float longitude) {this.longitude = longitude;}
}
