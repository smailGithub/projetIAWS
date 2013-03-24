/**
 * 
 *
 */

package inscription;

import java.util.List;
import java.sql.ResultSet;

import connexionDB.ConnectToDbImpl;

/**
 * @author Ismail
 * 
 */
public class InscriptionService {

	private ConnectToDbImpl dbConnection = new ConnectToDbImpl();
	private static final String DB_NAME = "applicationws";

	public int inscription(String lastname, String firstname, String mail, String adrPostale) {
		if(isMailValid(mail)) {
			if(isMailInDB(mail)) {
				return 100; 
			}
			addToDB(lastname, firstname, mail, adrPostale);
			return 0;
		}
		return 110;
	}

	private void addToDB(String lastname, String firstname, String mail, String adrPostale) {
		String insert = "INSERT INTO personnel(`nom`,`prenom`,`mail`,`adrPostale`)VALUES('" + lastname + "','" + firstname
				+ "','" + mail + "'," + adrPostale + ");";
		dbConnection.insertRequest(insert, DB_NAME);
	}

	private boolean isMailInDB(String mail) {
		ResultSet res = null;
		String requete = "SELECT mail FROM personnel";
		res = dbConnection.selectRequest(requete, DB_NAME);
		// System.out.println(request.CoutResult(res));
		List<String> resultList = dbConnection.countResult(res);
		for (int i = 0; i < resultList.size();i++) {
			if (resultList.get(i).equals(mail))
				return true;
		}
		return false;
	}

	private boolean isMailValid(String mail) {
		return (mail.contains("@") && mail.substring(mail.lastIndexOf('@') + 1).equals("univ-tlse3.fr"));
	}
}
