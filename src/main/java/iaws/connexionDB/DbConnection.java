/**
 *
 *
 */

package iaws.connexionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ismail
 * 
 */
// Connexion à la base de donnée
public class DbConnection {

	Connection connexion = null;
	Statement st = null;
	ResultSet res = null;

	// Initialisation de la connexion

	public void checkDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Driver jdbc est OK !");
		} catch(Exception ex1) {
			System.out.println("Driver jdbc erreur !");
		}
	}

	// Connexion à la BD

	public Connection connexionDB(String db) {
		try {
			System.out.println("Connexion à la base initialisée ! ");
			return connexion = DriverManager
					.getConnection("jdbc:mysql://localhost/" + db + "?"
							+ "user=root&password=root");
		} catch(SQLException ex2) {
			System.out.println("Erreur connexion à la base");
			return null;
		}
	}

	// Création de la table

	public void createTable(String db, String table) {
		connexion = connexionDB(db);
		String request = "CREATE TABLE " + table + "("
				+ "personnel_id INT(100) NOT NULL AUTO_INCREMENT,"
				+ "nom VARCHAR(50)," + "prenom VARCHAR(50),"
				+ "mail VARCHAR(50)," + "adrPostale VARCHAR(5),"
				+ "PRIMARY KEY (personnel_id)" + ") ";
		try {
			st = connexion.createStatement();
		} catch(SQLException ex3) {
			ex3.printStackTrace();
			System.exit(-1);
		}

		try {
			st.executeUpdate(request);
			System.out.println("Création de la table Personnel réussi !");
		} catch(SQLException ex4) {
			System.out.println("Echec création de la table Personnel");
		}
	}

	// SELECT Request

	public ResultSet selectRequest(String request, String db) {
		connexion = connexionDB(db);
		try {
			st = connexion.createStatement();
			if (st.execute(request)) {
				System.out.println("Select ok !");
				return res = st.getResultSet();
			}
			return null;
		} catch(SQLException ex5) {
			System.out.println("Erreur SELECT");
			return null;
		}
	}

	// Resultat de la requête

	public List<String> countResult(ResultSet res) {

		try {
			ResultSetMetaData data = res.getMetaData();
			int col = data.getColumnCount();
			List<String> l = new ArrayList<String>();
			while (res.next()) {
				for (int i = 1; i < col + 1; i++)
					l.add(res.getString(i));
			}
			return l;
		} catch(SQLException ex6) {
			ex6.printStackTrace();
			return null;
		}

	}

	// INSERT Request

	public void insertRequest(String request, String db) {
		connexion = connexionDB(db);
		try {
			st = connexion.createStatement();
			st.executeUpdate(request);
			System.out.println("Nouveau personnel ajouter avec succés");
		} catch(SQLException ex7) {
			System.out.println("Erreur Insert");
		}
	}

	// Nombre de column d'une requête
	public int nbCol(ResultSet res) {
		try {
			ResultSetMetaData data = res.getMetaData();
			return data.getColumnCount();
		} catch(SQLException ex8) {
			ex8.printStackTrace();
			System.out.println("Erreur count column");
			return 0;
		}
	}
}
