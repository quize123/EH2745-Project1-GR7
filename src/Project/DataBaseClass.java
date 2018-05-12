package Project;

//This class was written to accomplish the following tasks: 

//1- Connect to and create database 

//2- Create and insert tables into database


//*******************************************************************************************

//There is several Methods developed in this class which are:

//DataBaseClass(DataBaseName) Establish database connect and create database with the name DataBaseName

//CreateTable(TableName,ColumnName) Create database table which have TableName and columns identified ColumnName

//insertInTable(TableName, ArrayListColumns) Insert ArrayList into TableName  




//*******************************************************************************************


import java.sql.*;
import java.util.*;

public class DataBaseClass {

// Database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";

// Database Login info
	static final String USER = "root";
	static final String PASS = "y9mkrg6wyc8r";
	
	private String database;
	private Connection DatabaseConnect = null;
	private Statement stmt = null;

	
	
	public DataBaseClass(String DataBaseName){	
		
		database = DataBaseName;
		
		try{
//Connect to database
			Class.forName(JDBC_DRIVER);
			DatabaseConnect = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = DatabaseConnect.createStatement();
//If database does not exist then create datanase 
			String sql = "CREATE DATABASE IF NOT EXISTS " + database; 
			stmt.executeUpdate(sql);
			
//Connects to the created database 
			DatabaseConnect = DriverManager.getConnection(DB_URL + database, USER, PASS);
			stmt = DatabaseConnect.createStatement();
			
			//System.out.println("Database Connection Established");

				
		}catch(SQLException se){
	
			se.printStackTrace();
		
		}catch(Exception e){

			e.printStackTrace();
		}
	}
	

	public void CreateTable (String TableName, String ColumnName){
		
		try{
		    String sql = "CREATE TABLE IF NOT EXISTS " +TableName+ "" +ColumnName;
			stmt.executeUpdate(sql);
				
		}catch(SQLException se){

			se.printStackTrace();
			
		}catch (Exception de){
			de.printStackTrace();
		}
	}
	

	public void InsertArrayToTable(String TableName, ArrayList<String> SQLColumns){
		
		try{
			for(int i = 0;i<SQLColumns.size();i++){
				
				String sqlInsert = "INSERT INTO " + TableName + " VALUES ("+SQLColumns.get(i)+")"; 
	        	stmt.executeUpdate(sqlInsert);
			}
			
		}
		catch(SQLIntegrityConstraintViolationException integ){
			
		}
		catch(SQLException sqle){

			sqle.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
		
	public void CloseDatabase(){
		
		try{
			DatabaseConnect.close();
		}
		catch(SQLException sqle){

			sqle.printStackTrace();
		}
	}
	

}
