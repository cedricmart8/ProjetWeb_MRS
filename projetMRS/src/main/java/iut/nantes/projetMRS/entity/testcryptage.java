package iut.nantes.projetMRS.entity;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class testcryptage {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		EntityPersonne eP = new EntityPersonne("Samix","Choumi",new Date(), "samixchoumi@mail.com", "adressequelconque",
				null,true,true,"mdp415du4428FUTUR");
		String mdpCrypte = eP.getMotDePasse();
		System.out.println(eP.testDuMotDePasseCrypte("mdp415du4428FUTUR", mdpCrypte));
		
		String mdpConnectionCrypte = eP.EncodeLeMotDePasse("mdp415du4428FUTUR");
		System.out.println(eP.testDuMotDePasseCrypte("mdp415du4428FUTUR", mdpConnectionCrypte));
	}

}
