package iut.nantes.projetMRS;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import iut.nantes.projetMRS.entity.EntityPersonne;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws NoSuchAlgorithmException
    {
        EntityPersonne eP1 = new EntityPersonne("Dubernet", 
        		"Samuel", 
        		new Date(), 
        		"samuel.dubernet@etu.univ-nantes.fr", 
        		"12 rue de la flume", 
        		null, 
        		true, 
        		true, 
        		null, 
        		null, 
        		"C48t0R3n3Rv3r");
        System.out.println("password : " + eP1.getMotDePasse());
        System.out.println("password verified ?: " + eP1.testDuMotDePasseCrypte("C48t0R3n3Rv3r", "c1e263a82801b9ad90bf75d1411a6930"));
        System.out.println("password verified ?: " + eP1.testDuMotDePasseCrypte("C48t0R3n3Rv3r", "c1e263a82801b9ad90bf75d1411a6931"));
    }
}
