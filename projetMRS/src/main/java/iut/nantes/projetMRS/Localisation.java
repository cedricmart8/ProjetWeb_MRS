package iut.nantes.projetMRS;

public class Localisation {
	
	/**
	 * Attributs
	 */
	private double latitude;  //Latitude de l'utilisateur (point)
	private double longitude; //Longitude de l'utilisateur (point)
	
	/**
	 * Constructeur
	 */	
	public Localisation() {
		
	}
	
	public Localisation(
			double latitude, 
			double longitude
			) {
		super();
		this.latitude  = latitude;
		this.longitude = longitude;
	}

	public double getLattitude()               {return latitude;}

	public void setLattitude(double latitude)  {this.latitude = latitude;}

	public double getLongitude()               {return longitude;}

	public void setLongitude(double longitude) {this.longitude = longitude;}
}
