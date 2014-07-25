import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Properties;


public class DataBase {

	public static void init(String dburl, String dbuser, String dbpassword) {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		} catch (SQLException e) {
		}

		DataBase.dburl = dburl;
		DataBase.dbuser = dbuser;
		DataBase.dbpassword = dbpassword;
	}
	
	public static int genNextId(String table, String column) {
		int nextId = 1;

		Statement s = getStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		if (s == null) {
			closeConnection();
			return -1;
		}
		
		try {
			ResultSet res = s.executeQuery("SELECT MAX(" + column + ") maxid FROM " + table + "; ");
				
			if (res != null && res.next())
				nextId = res.getInt("maxid") + 1;
			
			s.close();
		} catch (SQLException e) {
			return -1;
		}
		
		return nextId;
	}

	@Deprecated
	public static ArrayList<String[]> getData() {
		ArrayList<String[]> table = new ArrayList<String[]>(); 
		
		Statement s = getStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		if (s == null) {
			closeConnection();
		}
		
		try {
			ResultSet res = s.executeQuery("SELECT * FROM data_values LEFT JOIN data_types ON data_types.id = data_values.data_type ORDER BY data_values.id; ");
			
			while (res != null && res.next()) {
				table.add(new String[] {res.getString("id"), res.getString("article_id"), res.getString("name"),
						res.getString("value"), res.getString("parent_id"), res.getString("lang")});
			}
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return table;
	}

	@Deprecated
	public static Hashtable<String, String> getTypes() {
		Hashtable<String, String> table = new Hashtable<String, String>(); 
		
		Statement s = getStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		if (s == null) {
			closeConnection();
		}
		
		try {
			ResultSet res = s.executeQuery("SELECT * FROM data_types");
			
			while (res != null && res.next())
				table.put(res.getString("name"), res.getString("id"));
			
			s.close();
		} catch (SQLException e) {
		}
		return table;
	}
	

	public static int getNumberID(String volume, String number) {
		int numberID = 0; 
		
		Statement s = getStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		if (s == null) {
			closeConnection();
		}
		
		try {
			ResultSet res = s.executeQuery("SELECT id FROM journal_numbers WHERE volume = "
											+ volume + " AND number = '" + number + "'");
			
			if (res != null && res.next())
				numberID = res.getInt("id");
			
			s.close();
		} catch (SQLException e) {
		}
		
		return numberID;
	}
	
	
	public static int executeUpdate(String query) {
		int rows = 0;

		Statement s = getStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
		if (s == null) {
			closeConnection();
			return -1;
		}
		
		try {
			rows = s.executeUpdate(query);
			
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return rows;
	}
	
	private static Statement getStatement(int type, int concur) {
		try {
			if (c == null) {
		        Properties properties = new Properties();
		        properties.setProperty("user", dbuser);
		        properties.setProperty("password", dbpassword);
		        properties.setProperty("useUnicode", "true");
		        properties.setProperty("characterEncoding", "UTF-8");
				
				c = DriverManager.getConnection(dburl, properties);
			}
			
			Statement s = c.createStatement(type, concur);
			
			return s;
		} catch (SQLException e) {
			closeConnection();

			return null;
		}
	}
	
	public static void closeConnection() {
		try {
			if (c != null)
				c.close();
		} catch (SQLException e1) {
		} finally {
			c = null;
		}
	}
	
	private static String dburl = null;
	private static String dbuser = null;
	private static String dbpassword = null;
	private static Connection c = null;
}
