package junitTest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

import klientpakke.klient;

public class KlientTest {

	@Test
	public void testAgeDifference() { //tester om aldersforskjellen fungerer
		LocalDate tenYearsOld = LocalDate.of(2010, 1, 1);
		assertEquals(10, klient.ageCalculator(tenYearsOld));
	}
	
	@Test
	public void testflipDate() { //tester om datoflippen fungerer
		String dato = "1996-09-04";
		assertEquals("04-09-1996", klient.flipDate(dato));
	}
	
	@Test
	public void testFormatName() { //tester om formateringen av navnet fungerer
		String navn = "[['Bob']]";
		assertEquals("Bob", klient.formatName(navn));
	}
}
