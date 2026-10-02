package com.pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

public class ConnectionPool 
{

	private String driver;
	private String dbUrl;
	private String dbUnmae;
	private String dbUpwd;
	
	Vector<Connection> v= new Vector<Connection>();
	
	
	public ConnectionPool(String driver, String dbUrl, String dbUnmae, String dbUpwd)
	{
		  this.driver = driver;
		  this.dbUrl = dbUrl;
		  this.dbUnmae = dbUnmae;
		  this.dbUpwd = dbUpwd;
	}
	
	void con_Initialization()
	{
		System.out.println("Creating 5 Connection Object");
		System.out.println("Before: "+ v.size());
		Connection con = null;
		while(v.size()<5)
		{			
			try
			{
				Class.forName(driver);
				con=DriverManager.getConnection(dbUrl,dbUnmae,dbUpwd);
				v.addElement(con);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println(v.size()+" Connection Object created");
		for(Object data:v)
		{
			System.out.println(data);
		}
	}
	
	Connection con_Acquisition() 
	{
		System.out.println("Assiging a Connection Object");
		Connection con_obj= v.get(0);
		v.remove(0);
		return con_obj;
	}
	
	
	void con_Return(Connection con_obj) 
	{
		System.out.println("\nReturning the Connection Object back to the Pool");
		v.addElement(con_obj);
		System.out.println("After Returning: "+ v.size()+"\n");
		for(Object data:v)
		{
			System.out.println(data);
		}
	}
	
}
