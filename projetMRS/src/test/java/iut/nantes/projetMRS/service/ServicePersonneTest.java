package iut.nantes.projetMRS.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;

import iut.nantes.projetMRS.entity.EntityGenreMusic;
import iut.nantes.projetMRS.entity.EntityPersonne;

@SuppressWarnings("unused")
public class ServicePersonneTest {

	ServicePersonne sp = new ServicePersonne();
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAddPersonneNew() {
		System.out.println("Test addPersonneNew\n\n\n");
		
		EntityPersonne p = sp.getDatastore().find(EntityPersonne.class).field("email").equal("MailTest@mailtest.test").get();
		System.out.println("|======== p= "+p);
		
		ArrayList<String> list = new ArrayList<>(); list.add("None");
		ArrayList<EntityGenreMusic> list2 = new ArrayList<>(); list2.add(new EntityGenreMusic(1,"None","noPicture"));
		EntityPersonne eP = new EntityPersonne(
				"NomTest", "PrenomTest", 
				new Date(1990,10,10), "MailTest@mailtest.test",
				"5 rue des tests", "testpicture.hostTest.test",
				true, true,
				list, list2,
				"m0td3p4ssTest"
				);
		
		//Reinitialisation des données à cause des ordres de passage des test
		if(p != null)
			sp.delete("MailTest@mailtest.test");
		
		assertEquals("test addPersonne(EntityPersonne)", "Personne added", sp.addPersonne(eP));
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAddPersonneNewAlreadyAdded() {
		System.out.println("Test addPersonneAlreadyUsed\n\n\n");
		testAddPersonneNew();
		ArrayList<String> list = new ArrayList<>(); list.add("None");
		ArrayList<EntityGenreMusic> list2 = new ArrayList<>(); list2.add(new EntityGenreMusic(1,"None","noPicture"));
		EntityPersonne eP = new EntityPersonne(
				"NomTest", "PrenomTest", 
				new Date(1990,10,10), "MailTest@mailtest.test",
				"5 rue des tests", "testpicture.hostTest.test",
				true, true,
				list, list2,
				"m0td3p4ssTest"
				);
		assertEquals("test addPersonne(EntityPersonne)", "User already create !", sp.addPersonne(eP));
		
		sp.delete("MailTest@mailtest.test");
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}

//	@Test
//	public void testGetAllPersonne() {
//		System.out.println("Test GetAllPersonne");
//		
//		assertEquals("test getAllPersonne()", false, !sp.getAllPersonne().isEmpty());
//		
//		sp.delete("MailTest@mailtest.test");
//	}

	@Test
	public void testGetPersonne() {
		System.out.println("Test GetPersonne\n\n\n");
		testAddPersonneNew();
		
		EntityPersonne p = sp.getDatastore().find(EntityPersonne.class).field("email").equal("MailTest@mailtest.test").get();
		
		assertEquals("test getPersonne(EntityPersonneMail)", p.getEmail(), sp.getPersonne("MailTest@mailtest.test").getEmail());
		sp.delete("MailTest@mailtest.test");
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}
	
	@Test(expected=Exception.class)
	public void testGetPersonneNull() {
		System.out.println("Test GetPersonneNull\n\n\n");
		String resEp = sp.getPersonne("MailTest@mailtest.test").getEmail();
		
		assertEquals("test getPersonne(EntityPersonneMail)", null, resEp);
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}

	@Test
	public void testDeleteWork() {
		System.out.println("Test DeletePersonneWork\n\n\n");
		testAddPersonneNew();
		
		EntityPersonne p = sp.getDatastore().find(EntityPersonne.class).field("email").equal("MailTest@mailtest.test").get();
		String mail = p.getEmail();
		assertEquals("test delete(EntityPersonneMail)","Personne deleted : Nom : " + p.getNom() + " " + p.getPrenom() + " Age : " + p.getAge(), sp.delete(mail));
	
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}
	
	@Test(expected = Exception.class)
	public void testDeleteNoUserFound() {
		System.out.println("Test DeletePersonneNotWorking\n\n\n");
		testAddPersonneNew();
		sp.delete("MailTest@mailtest.test");
		
		EntityPersonne p = sp.getDatastore().find(EntityPersonne.class).field("email").equal("MailTest@mailtest.test").get();
		String mail = p.getEmail();
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}

	@Test
	public void testModifUser() {
		System.out.println("Test ModifUser\n\n\n");
		testAddPersonneNew();
		
		assertEquals("test modifUser", "Personne Updated", sp.modifUser("testNomModifier", "null", null, "MailTest@mailtest.test", "null", "null", null, null));
		
		sp.delete("MailTest@mailtest.test");
	
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}
	
	@Test
	public void testModifUserChangingMail() {
		System.out.println("Test ModifUser changingmail\n\n\n");
		testAddPersonneNew();
		
		assertEquals("test modifUser", "Error while updating personne", sp.modifUser("testNomModifier", "null", null, "MailTest@mailupdatedtest.test", "null", "null", null, null));
		sp.delete("MailTest@mailtest.test");
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}

	@Test(expected=Exception.class)
	public void testAddPersonneVisiterSame() {
		System.out.println("Test AddPersonneVisiterSame\n\n\n");
		testAddPersonneNew();
		
		String res = sp.addPersonneVisiter("MailTest@mailupdatedtest.test", "MailTest@mailupdatedtest.test");
	
		sp.delete("MailTest@mailtest.test");
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testAddPersonneVisiterNotSame() {
		System.out.println("Test AddPersonneVisiterNotSame\n\n\n");
		testAddPersonneNew();
		
		ArrayList<String> list = new ArrayList<>(); list.add("None");
		ArrayList<EntityGenreMusic> list2 = new ArrayList<>(); list2.add(new EntityGenreMusic(1,"None","noPicture"));
		EntityPersonne eP = new EntityPersonne(
				"Nom2Test", "Prenom2Test", 
				new Date(1990,10,10), "Mail2Test@mail2test.test",
				"5 rue des tests2", "test2picture.hostTest.test",
				true, true,
				list, list2,
				"m0td3p4ssTest2"
				);
		sp.addPersonne(eP);
		
		String res = sp.addPersonneVisiter("MailTest@mailtest.test", "Mail2Test@mail2test.test");
		
		assertEquals("test AddPersonneVisiterNotSame", "Personne visiter !", res);
	
		sp.delete("MailTest@mailtest.test");
		sp.delete("Mail2Test@mail2test.test");
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}

	@Test
	public void testAddInteretMusicalWorking() {
		System.out.println("Test addInteretMusicalWorking\n\n\n");
		ServiceDeezer sd = new ServiceDeezer();
		sd.getAllGenreDB();

		testAddPersonneNew();

		String res = sp.addInteretMusical("MailTest@mailtest.test", 84);
		
		assertEquals("test addInteretMusical", "Interet musical ajouter", res);
		
		sp.delete("MailTest@mailtest.test");
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}
	
	@Test
	public void testAddInteretMusicalAddMoreData() {
		System.out.println("Test addInteretMusicalAddMoreData\n\n\n");
		ServiceDeezer sd = new ServiceDeezer();
		sd.getAllGenreDB();

		testAddPersonneNew();

		sp.addInteretMusical("MailTest@mailtest.test", 132);
		String res = sp.addInteretMusical("MailTest@mailtest.test", 84);
		
		assertEquals("test addInteretMusical", "Interet musical ajouter", res);
		
		sp.delete("MailTest@mailtest.test");
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}
	
	@Test
	public void testAddInteretMusicalError() {
		System.out.println("Test addInteretMusicalAddMoreData\n\n\n");
		ServiceDeezer sd = new ServiceDeezer();
		sd.getAllGenreDB();

		testAddPersonneNew();

		String res = sp.addInteretMusical("MailTest@mailtest.test", -1);
		
		assertEquals("test addInteretMusical", "Error while adding Genre", res);
		
		sp.delete("MailTest@mailtest.test");
		
		System.out.println("|=================|  COLLECTION ENTITYPERSONNE  |=================|");
		FindIterable<Document> iterable = sp.getClient().getDatabase("service").getCollection("EntityPersonne").find();
		for (Document document : iterable) {
			System.out.println("document => " + document);
		}
	}
}
