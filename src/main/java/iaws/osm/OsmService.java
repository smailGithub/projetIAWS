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

	public List<String> getVoisin(String mail, String rayon) {
		String findUser = "SELECT latitude, longitude FROM personnel WHERE mail = '" + mail + "';";
		ResultSet user = dbConnection.selectRequest(findUser, DB_NAME);

		List<String> latLonStrings = dbConnection.getResult(user);
		if (latLonStrings.size() != 2)
			throw new RuntimeException("ERROR : db request returned unexpected number of objects");

		double lat = Double.valueOf(latLonStrings.get(0));
		double lon = Double.valueOf(latLonStrings.get(1));

		String select = "SELECT mail FROM personnel WHERE " + "ABS(personnel.longitude - " + lon + ") < " + rayon
				+ " AND " + "ABS(personnel.latitude - " + lat + ") < " + rayon + ";";
		ResultSet res = dbConnection.selectRequest(select, DB_NAME);
		return dbConnection.getResult(res);
	}
}
