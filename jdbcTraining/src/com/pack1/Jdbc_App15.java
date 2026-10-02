package com.pack1;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;

public class Jdbc_App15 {

	private static final String driver ="oracle.jdbc.OracleDriver";
	private static final String dbUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String dbUnmae ="SYSTEM";
	private static final String dbUpwd ="1234";
	
	static Scanner sc = new Scanner(System.in);
	
	public static Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName(driver);
			con=DriverManager.getConnection(dbUrl,dbUnmae,dbUpwd);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return con;
	}
	
	void meth1()
	{
		System.out.println("Implementing Batch Processing");
		try
		{
			Connection con = connect();
			
			Statement stmt = con.createStatement();
			System.out.println("How many queries you want to add to the batch");
			int no_queries = Integer.parseInt(sc.nextLine());
			for(int i=1; i<=no_queries; i++)
			{
				System.out.println("Enter "+ i +" query");
				stmt.addBatch(sc.nextLine());
			}
			System.out.println("All "+no_queries+ " queries added to the batch");
			int arr[] =stmt.executeBatch();
			System.out.println("arr : "+Arrays.toString(arr));
				
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Jdbc_App15 obj = new Jdbc_App15();
		obj.meth1();
	}
}
