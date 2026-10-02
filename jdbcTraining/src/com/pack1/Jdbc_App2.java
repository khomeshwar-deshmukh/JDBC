package com.pack1;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class Jdbc_App2 {

	private String driver = "oracle.jdbc.OracleDriver";
	private String dbUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String dbUname = "SYSTEM";
	private String dbUpwd = "1234";
	
	private String sqlQuery="SELECT * FROM EMPLOYEE";
	
	public Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName(driver);
			con=DriverManager.getConnection(dbUrl,dbUname,dbUpwd);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Connection successful");
		return con;
		
	}
	void get_empData()
	{
		System.out.println("Retriving the data from the employe table\n");
		try
		{
		Connection con = connect();
		Statement stmt = con.createStatement() ;
		ResultSet rs = stmt.executeQuery(sqlQuery);
		
		while(rs.next())
		{
			System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+ " "+ rs.getString(5));
			
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("\nData Reterived");
	}
	public static void main(String[] args) {
		new Jdbc_App2().get_empData()
;	}
}
