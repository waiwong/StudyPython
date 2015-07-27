package dbi.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

public class SqliteDB {
	protected Connection conn = null;
	protected Statement stmt = null;
	protected PreparedStatement prepstmt = null;
	
	public SqliteDB(String DBName) throws Exception {
		// Get a connection from the pool
		Class.forName("org.sqlite.JDBC");
		// create a database connection
		conn = DriverManager.getConnection(String.format("jdbc:sqlite:%s", DBName));
	}
	
	public SqliteDB(DataSource pool) throws Exception {
		// Get a connection from the pool
		conn = pool.getConnection();
	}
	
	public void close() throws Exception {
		try {
			if (stmt != null) {
				stmt.close();
				stmt = null;
			}
			
			if (prepstmt != null) {
				prepstmt.close();
				prepstmt = null;
			}
			
			// Close the Connection to return it to the pool
			if (conn != null)
				conn.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * @return current connection
	 */
	public Connection getConnection() {
		return conn;
	}
	
	public void prepareStatement(String sql) throws SQLException {
		prepstmt = conn.prepareStatement(sql);
	}
	
	public void prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		prepstmt = conn.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}
	
	public void setString(int index, String value) throws SQLException {
		prepstmt.setString(index, value);
	}
	
	public void setInt(int index, int value) throws SQLException {
		prepstmt.setInt(index, value);
	}
	
	public void setBoolean(int index, boolean value) throws SQLException {
		prepstmt.setBoolean(index, value);
	}
	
	public void setDate(int index, Date value) throws SQLException {
		prepstmt.setDate(index, value);
	}
	
	public void setLong(int index, long value) throws SQLException {
		prepstmt.setLong(index, value);
	}
	
	public void setFloat(int index, float value) throws SQLException {
		prepstmt.setFloat(index, value);
	}
	
	public void setBytes(int index, byte[] value) throws SQLException {
		prepstmt.setBytes(index, value);
	}
	
	public void clearParameters() throws SQLException {
		prepstmt.clearParameters();
		prepstmt = null;
	}
	
	public PreparedStatement getPreparedStatement() {
		return prepstmt;
	}
	
	public ResultSet executeQuery() throws SQLException {
		if (prepstmt != null) {
			return prepstmt.executeQuery();
		} else
			return null;
	}
	
	public int executeUpdate() throws SQLException {
		if (prepstmt != null)
			return prepstmt.executeUpdate();
		else
			return -1;
	}
	
	public Object executeScalar() throws SQLException {
		Object obj = null;
		if (prepstmt != null) {
			try {
				ResultSet rs = executeQuery();
				if (rs.next()) {
					obj = rs.getObject(1);
				}
			} catch (SQLException e) {
				// log.error("", e);
				System.out.println(e.getMessage());
			}
		}
		
		return obj;
	}
	
	public void SetStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		stmt = conn.createStatement(resultSetType, resultSetConcurrency);
	}
	
	public ResultSet executeQuery(String sql) throws SQLException {
		if (stmt == null) {
			stmt = conn.createStatement();
		}
		
		return stmt.executeQuery(sql);
	}
	
	public int executeUpdate(String sql) throws SQLException {
		if (stmt == null) {
			stmt = conn.createStatement();
		}
		
		return stmt.executeUpdate(sql);
	}
	
	public Object executeScalar(String sql) throws SQLException {
		Object obj = null;
		if (stmt == null) {
			stmt = conn.createStatement();
		}
		
		try {
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				obj = rs.getObject(1);
			}
		} catch (SQLException e) {
			// log.error("", e);
			System.out.println(e.getMessage());
		}
		
		return obj;
	}
	
	public int executeUpdateWithTrans(String sql) throws SQLException {
		if (stmt == null) {
			stmt = conn.createStatement();
		}
		
		int result = 0;
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setAutoCommit(false);
		result = stmt.executeUpdate(sql);
		stmt.close();
		conn.commit();
		
		return result;
	}
}