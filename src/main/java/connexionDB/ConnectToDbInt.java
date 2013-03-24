/**
 * 
 */
package connexionDB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;


/**
 * @author Ismail
 *
 */
public interface ConnectToDbInt {
	
	
	public void checkDriver();
	
	public Connection connexionDB(String db);
	
	public void createTable(String db,String table);
	
	public ResultSet selectRequest(String request,String db);
	
	public List<String> countResult(ResultSet res);
	
	public void insertRequest(String request,String db);
	
	public int nbCol(ResultSet res);
}
