package iut.nantes.projetMRS.entity;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class testcryptage {

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		EntityPersonne eP = new EntityPersonne("Samix","Choumi",new Date(), "samixchoumi@mail.com", "adressequelconque",
				null,true,true,"azerty");
		String mdpCrypte = eP.getMotDePasse();
		System.out.println("mdpcrypte = "+mdpCrypte);
		System.out.println("mdp creatio = "+" azerty");
		System.out.println(eP.testDuMotDePasseCrypte("azerty", mdpCrypte));
		String mdpCo = "azerty";
		System.out.println("mdpconnex = "+mdpCo);
		System.out.println(eP.testDuMotDePasseCrypte(mdpCo, mdpCrypte));
	}

}
