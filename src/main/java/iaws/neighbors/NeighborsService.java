package iaws.neighbors;

import iaws.connexionDB.DbConnection;

import java.util.List;
import java.sql.ResultSet;

/**
 * @author Ismail
 * 
 */
public class NeighborsService {
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

		String select = "SELECT mail FROM personnel WHERE ABS(personnel.longitude - " + lon + ") < " + rayon
				+ " AND " + "ABS(personnel.latitude - " + lat + ") < " + rayon + ";";
		ResultSet res = dbConnection.selectRequest(select, DB_NAME);
		return dbConnection.getResult(res);
	}

	/**
	 * Returns the distance between two lat/lon defined points, in kilometers
	 * 
	 * @param lat1 First point latitude
	 * @param lng1 First point longitude
	 * @param lat2 Second point latitude
	 * @param lng2 Second point longitude
	 * 
	 * @return the distance between the two given points, in kilometers
	 */
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		double meterConversion = 1.609;

		return dist * meterConversion;
	}
}
