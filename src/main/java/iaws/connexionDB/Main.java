
package iaws.connexionDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DB testing class
 * 
 * 
 * @author Ismail
 * 
 */
public class Main {

	public static void main(String[] args) {
		DbConnection connexion = new DbConnection();
		String db = "applicationws";
		connexion.checkDriver();
		connexion.connexionDB(db);
		// connexion.createTable(db,"personnel");
		String request = "INSERT INTO personnel('nom','prenom','mail','adrPostale')VALUES('senhaji','ismail','senhaij.ismail@univ-tlse3.fr',31000);";
		connexion.insertRequest(request, db);
		ResultSet res = connexion.selectRequest("SELECT mail FROM personnel;",
				db);
		List<String> Liste = new ArrayList<String>();
		Liste = (connexion.getResult(res));
		for (int i = 0; i < Liste.size(); i++)
			System.out.println(Liste.get(i));
	}
}
