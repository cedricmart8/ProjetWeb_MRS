package iut.nantes.projetMRS.service;

import static org.junit.Assert.*;

import java.util.Date;

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
	public void testAddPersonne() {
		EntityPersonne eP = new EntityPersonne(
				"NomTest", "PrenomTest", 
				new Date(1990,10,10), "MailTest@mailtest.test",
				"5 rue des tests", "testpicture.hostTest.test",
				true, true,
				"m0td3p4ssTest"
				);
		assertEquals("test addPersonne(EntityPersonne)", "Personne added", sp.addPersonne(eP));
	}

	@Test
	public void testGetAllPersonne() {

	}

	@Test
	public void testGetPersonne() {

	}

	@Test
	public void testDelete() {

	}

	@Test
	public void testModifUser() {

	}

	@Test
	public void testAgeByDateNaissance() {

	}

	@Test
	public void testAddPersonneVisiter() {

	}

	@Test
	public void testAddInteretMusical() {

	}

}
