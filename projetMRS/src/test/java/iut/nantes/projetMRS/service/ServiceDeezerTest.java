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
	
	@Test(expected=Exception.class)
	public void testGetDataDeezerGenreError() {
		System.out.println("Test GetDataDeezerGenreError\n\n\n");
		
		sd.setClient(null);
		
		sd.getDataDeezerGenre();
	}

}
