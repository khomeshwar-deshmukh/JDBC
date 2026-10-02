package com.pack1;

import java.sql.Connection;
import java.sql.DriverManager;

public class Jdbc_App1 {

	private String driver="oracle.jdbc.OracleDriver";
	private String dbUrl = "jdbc:oracle:thin:@localhost:1521/orcl";
	private String dbUname="system";
	private String dbUpwd="1234";
	
	public void connect()
	{
		System.out.println("Connecting to the Database");
		try
		{
			 Class.forName(driver);
			 Connection con=DriverManager.getConnection(dbUrl, dbUname, dbUpwd);
			 System.out.println("Connection Created");
			 con.close();
		}
		catch(Exception e) 
		{
			  e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Jdbc_App1 obj=new Jdbc_App1();
		obj.connect();
	}
} 
