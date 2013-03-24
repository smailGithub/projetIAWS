/**
 * 
 *
 */

package inscription;

import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;

import connexionDB.*;

/**
 * @author Ismail
 * 
 */
public class InscriptionImpl implements InscriptionInt {

	ConnectToDbInt request = new ConnectToDbImpl();
	List<String> l = new ArrayList<String>();
	ResultSet res;
	String requete;
	String insert;
	String db = "applicationws";
	int col;

	public int verificationPersonnel(String nom, String prenom, String mail, int adrPostale) {

		// Vérification du mail
		if (mail.contains("@") && mail.substring(mail.lastIndexOf('@') + 1).equals("univ-tlse3.fr")) {
			// request.connexionDB(db);
			ResultSet res = null;
			requete = "SELECT mail FROM personnel";
			res = request.selectRequest(requete, db);
			// System.out.println(request.CoutResult(res));
			l = request.countResult(res);
			col = request.nbCol(res);
			System.out.println(col);
			for (int i = 0; i < l.size();i++) {
				if (l.get(i).equals(mail))
					return 100;
			}
			insert = "INSERT INTO personnel(`nom`,`prenom`,`mail`,`adrPostale`)VALUES('" + nom + "','" + prenom
					+ "','" + mail + "'," + adrPostale + ");";
			request.insertRequest(insert, db);
			return 0;
			
		}
		return 110;
	}
}
