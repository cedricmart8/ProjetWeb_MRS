package iut.nantes.projetMRS.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class ServiceDeezerTest {

	ServiceDeezer sd = new ServiceDeezer();
	
	@Test
	public void testGetDataDeezerGenre() {
		System.out.println("Test GetDataDeezerGenre\n\n\n");
		
		assertEquals("test getDataGenreDeezer", true, sd.getDataDeezerGenre());
	}
	
	@Test
	public void testGetDataDeezerGenreError() {
		System.out.println("Test GetDataDeezerGenreError\n\n\n");
		
		sd.setClient(null);
		
		assertEquals("test getDataGenreDeezer", false, sd.getDataDeezerGenre());
	}

}
