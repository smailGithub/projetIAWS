/**
 * 
 *
 */

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
import org.jdom.xpath.XPath;



/**
 * @author Vincent Carassus
 * 
 */
public class InscriptionService {

	private DbConnection dbConnection = new DbConnection();
	private static final String DB_NAME = "applicationws";
	
	private XPath latExpression;
	private XPath lonExpression;
	
	{
		try {
			latExpression = XPath.newInstance("//place@lat");
			lonExpression = XPath.newInstance("//place@lon");
		} catch(JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int inscription(String lastname, String firstname, String mail, String adrPostale) {
		if(isMailValid(mail)) {
			if(isMailInDB(mail)) {
				return 100; 
			}
			double[] latLon = getLatLon(adrPostale);
			addToDB(lastname, firstname, mail, adrPostale, latLon[0], latLon[1]);
			return 0;
		}
		return 110;
	}

	private double[] getLatLon(String adrPostale) {
		double[] res = new double[2];
		try {
			String query = "http://nominatim.openstreetmap.org/search/" + adrPostale + "format=xml&polygon=1&addressdetails=1";
			
			
			URL url = new URL(query);
			HttpURLConnection connection =
			    (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");

			Reader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(reader);
			res[0] = Double.valueOf(latExpression.valueOf(document));
			res[1] = Double.valueOf(lonExpression.valueOf(document));
			
		} catch(JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	private void addToDB(String lastname, String firstname, String mail, String adrPostale, double latitude, double longitude) {
		String insert = "INSERT INTO personnel(`nom`,`prenom`,`mail`,`adrPostale`,`latitude`,`longitude`)VALUES('" + lastname + "','" + firstname
				+ "','" + mail + "','" + adrPostale + "'," + latitude + ", " + longitude + ");";
		dbConnection.insertRequest(insert, DB_NAME);
	}

	private boolean isMailInDB(String mail) {
		ResultSet res = null;
		String requete = "SELECT mail FROM personnel";
		res = dbConnection.selectRequest(requete, DB_NAME);
		List<String> resultList = dbConnection.getResult(res);
		for (int i = 0; i < resultList.size();i++) {
			if (resultList.get(i).equals(mail))
				return true;
		}
		return false;
	}

	private boolean isMailValid(String mail) {
		return (mail.contains("@"));
	}
}
