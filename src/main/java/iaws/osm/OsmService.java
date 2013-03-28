/**
 * 
 *
 */

package iaws.osm;

import iaws.connexionDB.DbConnection;

import java.util.List;
import java.sql.ResultSet;


/**
 * @author Ismail
 * 
 */
public class OsmService {
	private DbConnection dbConnection = new DbConnection();
	private static final String DB_NAME = "applicationws";

	public List<String> getVoisin(String longitude,String latitude) {
			String select ="SELECT mail FROM personnel WHERE " +
				"personnel."+longitude+"='longitude' AND" +
						"personnel."+latitude+"='latitude' ;";
		ResultSet res=dbConnection.selectRequest(select,DB_NAME);
		return dbConnection.getResult(res);
	}
}
