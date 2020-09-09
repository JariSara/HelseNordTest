package MainTekstHandler;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.dataaccess.www.webservicesserver.TextCasingLocator;
import com.dataaccess.www.webservicesserver.TextCasingSoapBindingStub;
import java.util.Scanner; 

/*
 * Brukte API'et som eksisterte og lagde en main metode for å hente ut informasjonen.  
 */
public class TextClient {
	private static String inputVerdi, outputVerdi;
	private static long startMillis;
	public static void main(String[] args) {
		TextCasingLocator locator= new TextCasingLocator();
		try {
			TextCasingSoapBindingStub stub= (TextCasingSoapBindingStub)locator.getTextCasingSoap();
			Scanner myObj = new Scanner(System.in);  
		    System.out.println("Skriv inn et ord");
		    inputVerdi = myObj.nextLine();
		    startCounter();
			outputVerdi = stub.invertStringCase(inputVerdi);
			endCounter();
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void startCounter() { //teller
		startMillis = System.currentTimeMillis();
	}
	public static void endCounter() { //avslutter telleren og returnerer tiden
		long timeInMillis = System.currentTimeMillis() - startMillis;
		System.out.println("ny string: " + outputVerdi + " Tid: " + timeInMillis);
	}
}
