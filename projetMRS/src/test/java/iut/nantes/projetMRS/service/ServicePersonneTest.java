package iut.nantes.projetMRS.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

import iut.nantes.projetMRS.entity.EntityPersonne;

@SuppressWarnings("unused")
public class ServicePersonneTest {

	ServicePersonne sp = new ServicePersonne();
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAddPersonneNew() {
		System.out.println("Test addPersonneNew");
		
		EntityPersonne p = sp.getDatastore().find(EntityPersonne.class).field("email").equal("MailTest@mailtest.test").get();
		System.out.println("|======== p= "+p);
		
		EntityPersonne eP = new EntityPersonne(
				"NomTest", "PrenomTest", 
				new Date(1990,10,10), "MailTest@mailtest.test",
				"5 rue des tests", "testpicture.hostTest.test",
				true, true,
				"m0td3p4ssTest"
				);
		
		//Reinitialisation des données à cause des ordres de passage des test
		if(p != null)
			sp.delete("MailTest@mailtest.test");
		
		assertEquals("test addPersonne(EntityPersonne)", "Personne added", sp.addPersonne(eP));
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAddPersonneNewAlreadyAdded() {
		System.out.println("Test addPersonneAlreadyUsed");
		testAddPersonneNew();
		EntityPersonne eP = new EntityPersonne(
				"NomTest", "PrenomTest", 
				new Date(1990,10,10), "MailTest@mailtest.test",
				"5 rue des tests", "testpicture.hostTest.test",
				true, true,
				"m0td3p4ssTest"
				);
		assertEquals("test addPersonne(EntityPersonne)", "User already create !", sp.addPersonne(eP));
		
		sp.delete("MailTest@mailtest.test");
	}

//	@Test
//	public void testGetAllPersonne() {
//		System.out.println("Test GetAllPersonne");
//		List<EntityPersonne> listPersonne = sp.getDatastore().find(EntityPersonne.class).asList();
//		
//		assertEquals("test getAllPersonne()", listPersonne.equals(sp.getAllPersonne()), true);
//	}

	@Test
	public void testGetPersonne() {
		System.out.println("Test GetPersonne");
		testAddPersonneNew();
		
		EntityPersonne p = sp.getDatastore().find(EntityPersonne.class).field("email").equal("MailTest@mailtest.test").get();
		
		assertEquals("test getPersonne(EntityPersonneMail)", p.getEmail(), sp.getPersonne("MailTest@mailtest.test").getEmail());
		sp.delete("MailTest@mailtest.test");
	}
	
	@Test(expected = Exception.class)
	public void testGetPersonneNull() {
		System.out.println("Test GetPersonneNull");
		testAddPersonneNew();
		
		EntityPersonne p = sp.getDatastore().find(EntityPersonne.class).field("email").equal("MailTest@mailpasbon.test").get();
		
		//assertEquals("test getPersonne(EntityPersonneMail)", p.getEmail(), sp.getPersonne("MailTest@mailtest.test").getEmail());
		sp.delete("MailTest@mailtest.test");
	}

	@Test
	public void testDeleteWork() {
		System.out.println("Test DeletePersonneWork");
		testAddPersonneNew();
		
		EntityPersonne p = sp.getDatastore().find(EntityPersonne.class).field("email").equal("MailTest@mailtest.test").get();
		String mail = p.getEmail();
		assertEquals("test delete(EntityPersonneMail)","Personne deleted : Nom : " + p.getNom() + " " + p.getPrenom() + " Age : " + p.getAge(), sp.delete(mail));
	}
	
	@Test(expected = Exception.class)
	public void testDeleteNoUserFound() {
		System.out.println("Test DeletePersonneNotWorking");
		testAddPersonneNew();
		sp.delete("MailTest@mailtest.test");
		
		EntityPersonne p = sp.getDatastore().find(EntityPersonne.class).field("email").equal("MailTest@mailtest.test").get();
		String mail = p.getEmail();
		//assertEquals("test delete(EntityPersonneMail)", "Error while deleting a person", sp.delete(mail));
	}

	@Test
	public void testModifUser() {
		System.out.println("Test ModifUser");
		testAddPersonneNew();
		
		assertEquals("test modifUser", "Personne Updated", sp.modifUser("testNomModifier", null, null, "MailTest@mailtest.test", null, null, null, null));
		
		sp.delete("MailTest@mailtest.test");
	}
	
	@Test(expected = Exception.class)
	public void testModifUserChangingMail() {
		System.out.println("Test ModifUser");
		testAddPersonneNew();
		sp.modifUser("testNomModifier", null, null, "MailTest@mailmodifiertest.test", null, null, null, null);
		sp.delete("MailTest@mailtest.test");
	}

	@Test
	public void testAddPersonneVisiter() {
		System.out.println("Test AddPersonneVisiter");
	}

	@Test
	public void testAddInteretMusical() {
		System.out.println("Test AddINteretMusical");
	}

}
