package iaws.inscription;

import iaws.connexionDB.DbConnection;

import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * @author Vincent Carassus
 * 
 */
public class InscriptionService {

	private DbConnection dbConnection = new DbConnection();
	private static final String DB_NAME = "applicationws";

	private static XPath latExpression;
	private static XPath lonExpression;

	static {
		try {
			latExpression = XPath.newInstance("//place/@lat");
			lonExpression = XPath.newInstance("//place/@lon");
		} catch(JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int inscription(String lastname, String firstname, String mail, String adrPostale) {
		if (isMailValid(mail)) {
			if (isMailInDB(mail)) {
				return 100;
			}
			double[] latLon;
			try {
				latLon = getLatLon(adrPostale);
			} catch(JDOMException e) {
				// no result : adress unknown
				return 200;
			}
			addToDB(lastname, firstname, mail, adrPostale, latLon[0], latLon[1]);
			return 0;
		}
		return 110;
	}

	private static double[] getLatLon(String adrPostale) throws JDOMException {
		double[] res = new double[2];
		try {
			String query = "http://nominatim.openstreetmap.org/search/" + adrPostale
					+ "?format=xml&polygon=1&addressdetails=1";
			
			System.out.println(query);

			URL url = new URL(query);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");

			Reader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(reader);

			XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
			String xmlString = outputter.outputString(document);
			System.out.println(xmlString);

			res[0] = Double.valueOf(latExpression.valueOf(document));
			res[1] = Double.valueOf(lonExpression.valueOf(document));
			
		} catch(MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	private void addToDB(String lastname, String firstname, String mail, String adrPostale, double latitude,
			double longitude) {
		String insert = "INSERT INTO personnel(`nom`,`prenom`,`mail`,`adrPostale`,`latitude`,`longitude`)VALUES('"
				+ lastname + "','" + firstname + "','" + mail + "','" + adrPostale + "'," + latitude + ", " + longitude
				+ ");";
		dbConnection.insertRequest(insert, DB_NAME);
	}

	private boolean isMailInDB(String mail) {
		ResultSet res = null;
		String requete = "SELECT mail FROM personnel";
		res = dbConnection.selectRequest(requete, DB_NAME);
		List<String> resultList = dbConnection.getResult(res);
		for (int i = 0; i < resultList.size(); i++) {
			if (resultList.get(i).equals(mail))
				return true;
		}
		return false;
	}

	private boolean isMailValid(String mail) {
		return (mail.contains("@"));
	}

	/**
	 * Main used for testing purposes
	 * 
	 * @param args
	 *            unused
	 */
	public static void main(String... args) {
		double[] res;
		try {
			res = getLatLon("118%20route%20de%20narbonne,Toulouse,France");
			System.out.println(res[0] + ", " + res[1]);
		} catch(JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
