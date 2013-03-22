/**
 * 
 */
package connexionDB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Ismail
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConnectToDbInt connexion=new ConnectToDbImpl();
		String db="applicationws";
		connexion.checkDriver();
		connexion.connexionDB(db);
		//connexion.createTable(db,"personnel");
		String request="INSERT INTO personnel(`nom`,`prenom`,`mail`,`adrPostale`)VALUES('senhaji','ismail','senhaij.ismail@univ-tlse3.fr',31000);";
		connexion.InsertRequest(request,db);
		ResultSet res=connexion.SelectRequest("SELECT mail FROM personnel;",db);
		List<String> Liste=new ArrayList<String>();
		Liste=(connexion.CoutResult(res));
		for(int i=0;i<Liste.size();i++)
			System.out.println(Liste.get(i));
	}
}
	
