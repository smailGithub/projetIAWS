/**
 * 
 */
package connexionDB;

import java.sql.ResultSet;
import java.util.List;
import java.sql.*;

/**
 * @author Ismail
 *
 */
public interface ConnectToDbInt {
	public void checkDriver();
	public Connection connexionDB(String db);
	public void createTable(String db,String table);
	public ResultSet SelectRequest(String request,String db);
	public List<String> CoutResult(ResultSet res);
	public void InsertRequest (String request,String db);
	public int NbreCol(ResultSet res);
}
