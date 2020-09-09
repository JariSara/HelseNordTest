package klientpakke;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.server.exceptions.ResourceNotFoundException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.hl7.fhir.r4.model.Patient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;

public class klient {
	private static String kjonn = "";
	private static String dato = "";
	private static JSONObject obj;
	private static String fornavn = "";
	private static String etternavn = "";
	private static List<Object> fornavnList = new ArrayList<Object>();
	private static JSONArray names;
	private static LocalDate yearBorn;
	private static LocalDate today;
	private static int age;

	//Oppgave 2, brukte apiet som lå på nettsiden og implementerte den 
	public static void main(String[] args) {

		contextAndClientInit();
		// 639125 1222826
		names = obj.getJSONArray("name");

		kjonn = getString("gender");
		dato = getString("birthDate");
		if (dato != "Ukjent") { //hvis dato finnes på pasienten så kjøres den her
			dato = flipDate(dato);
			yearBorn = stringToDateConverter(dato);
			age = ageCalculator(yearBorn);
		}
		fornavnList.add(names.getJSONObject(0).get("given"));
		fornavn = fornavnList.toString();
		fornavn = formatName(fornavn);
		try {
			etternavn = names.getJSONObject(0).getString("family").toString();

		} catch (JSONException e) {
			System.out.println("Fant ikke familienavn, feilmelding: " + e);
			etternavn = "ukjent";
		}

		System.out.print("Fornavn: " + fornavn + ", etternavn: " + etternavn + ", kjønn: " + kjonn + ", dato: " + dato
				+ ", " + age + " år gammel");

	}

	public static int ageCalculator(LocalDate yearOfBirth) { // kalkulerer alderen på personen
		today = LocalDate.now();
		Period diff = Period.between(yearOfBirth, today);
		return diff.getYears();
	}

	public static LocalDate stringToDateConverter(String date) { // konverter stringen fra resultatet til en dato

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate result = LocalDate.parse(date, formatter);

		return result;
	}

	public static String flipDate(String datoFlipped) { // Formaterer datoen til en norsk datoformat

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		// System.out.println(LocalDate.parse(datoFlipped,
		// formatter).format(formatter2));

		return LocalDate.parse(datoFlipped, formatter).format(formatter2);
	}

	public static void contextAndClientInit() { // starter hele søkeprosessen
		// Create a context
		FhirContext ctx = FhirContext.forR4();

		// Create a client
		IGenericClient client = ctx.newRestfulGenericClient("https://hapi.fhir.org/baseR4");

		Scanner myObj = new Scanner(System.in); // Create a Scanner object
		System.out.println("Skriv inn pasient id");
		String inputVerdi = myObj.nextLine();
		Patient patient = null;
		// Read a patient with the given ID
		String resultat = "";
		try {
			patient = client.read().resource(Patient.class).withId(inputVerdi).execute();
			resultat = ctx.newJsonParser().setPrettyPrint(true).encodeResourceToString(patient);
		} catch (ResourceNotFoundException e) {
			System.out.println("fant ikke en pasient med den id'en, feilmelding: " + e);
		}

		// Print the output

		// System.out.println(string);

		obj = new JSONObject(resultat);
	}

	public static String getString(String data) { // henter stringen fra nettsiden
		String result;
		try {
			result = obj.getString(data);
		} catch (JSONException e) {
			System.out.println("Fant ikke dataen, feilmelding: " + e);
			result = "Ukjent";
		}
		return result;
	}

	// Det her kunnet vært unngått men fant ikke ut hvordan få ut objektet uten
	// "[[]]" så måtte lage en formateringsmetode, er forståelig at ikke er ønskelig
	// i en fremtidig prosjekt
	public static String formatName(String name) {
		name = name.substring(3, name.length() - 3); // formaterer navnet da fornavn blir returnert som en objekt i en
														// liste, eksempel: "[["bob"]]
		return name;
	}

}
