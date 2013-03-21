package connexionDB;

import java.sql.*;


public class connectToDb {
		
		// Connexion à la base de donnée
		
		//Initialisation de la connexion
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			System.out.println("Driver jdbc est OK !");
		}
		catch (Exception ex1){
			System.out.println("Driver jdbc erreur !");
		}
		Connection connexion=null;
		try{
			connexion=DriverManager.getConnection("jdbc:mysql://localhost/applicationws?"+"user=root&password=root");
			System.out.println("Connexion à la base initialisée");
		}
		catch (SQLException ex2){
			System.out.println("Erreur connexion à la base");
		}
		
		//Création de la table
		
		String request ="CREATE OR REPLACE TABLE personnel(" +
				"personnel_id INT NOT NULL AUTO_INCREMENT," +
				"nom VARCHAR(50)," +
				"prenom VARCHAR(50)," +
				"mail VARCHAR(50)," +
				"adrPostale VARCHAR(50)," +
				"PRIMARY KEY (personnel_id)" +
				") ";
		Statement st=null;
		ResultSet res=null;
		
		try{
			st=connexion.createStatement();
		}
		catch (SQLException ex3){
			ex3.printStackTrace();
			System.exit(-1);
		}
		
		try{
			st.executeUpdate(request);
			System.out.println("Création de la table Personnel réussi !");
		}
		catch(SQLException ex4){
		System.out.println("Echec création de la table Personnel");
		}
		
		//Méthode INSERT ET SELECT
		
		public ResultSet SelectRequest(String request){
			try{
				return st.executeQuery(request);
			}
			catch (SQLException ex){
				System.out.println("Erreur SELECT");
			}
		}
		
		public void InsertRequest(String request){
			try{
				st.execute(request);
				System.out.println("Nouveau personnel ajouter avec succés");
				
			}
			catch (SQLException ex5){
				System.out.println("Erreur Insert");
			}
		}
}
