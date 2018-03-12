package iut.nantes.projetMRS.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

@SuppressWarnings("unused")
public class ServicePersonneTest {

	MongoClient client = new MongoClient("localhost", 8081); // connect to mongodb
	Datastore datastore = new Morphia().createDatastore(client, "service"); // select service collection
	
	@Test
	public void testAddPersonne() {
		
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
