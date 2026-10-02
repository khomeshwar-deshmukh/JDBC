package com.pack1;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Jdbc_App6 {

	private static final String driver ="oracle.jdbc.OracleDriver";
	private static final String dbUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String dbUnmae ="SYSTEM";
	private static final String dbUpwd ="1234";
	
	Scanner sc = new Scanner(System.in);
	
	void meth1()
	{
		System.out.println("ResultSet.TYPE_FORWARD_ONLY: "+ ResultSet.TYPE_FORWARD_ONLY ); // 1003
		System.out.println("ResultSet.TYPE_SCROLL_INSENSITIVE: "+ ResultSet.TYPE_SCROLL_INSENSITIVE); // 1004
		System.out.println("ResultSet.TYPE_SCROLL_SENSITIVE: "+ ResultSet.TYPE_SCROLL_SENSITIVE); // 1005
		
		System.out.println("\n------------------\n");
		
		System.out.println("ResultSet.CONCUR_READ_ONLY: "+ ResultSet.CONCUR_READ_ONLY); // 1007
		System.out.println("ResultSet.CONCUR_UPDATABLE: "+ ResultSet.CONCUR_UPDATABLE); // 1008
		
		System.out.println("\n========================================\n");
	}
	
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
	
	void meth2()
	{
		System.out.println("Implementing ScroallableResultSet ==> ReadOnly");
		System.out.println("========================================\n");
		try
		{
			Connection con=connect();
			Statement stmt =con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//Statement stmt=con.createStatement(1004,1007);
			
			ResultSet rs= stmt.executeQuery("Select * from employee");
			
			while(rs.next()) 
			{
				System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getString(5));
			}
			
			System.out.println("\n------------------------------------\n");
			
			rs.afterLast();
			while(rs.previous()) 
			{
				System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getString(5));
			}
			
			System.out.println("\n------------------------------------\n");
			
			rs.last();
			System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getString(5));
			
			System.out.println("\n------------------------------------\n");
			
			rs.first();
			System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getString(5));
			
			System.out.println("\n------------------------------------\n");
			
			rs.absolute(4);
			System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getString(5));
			System.out.println();
			rs.absolute(-2);
			System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getString(5));
			
			System.out.println("\n------------------------------------\n");
			
			rs.relative(-4);
			System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getString(5));
			System.out.println();
			rs.relative(1);
			System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getString(3)+" "+rs.getInt(4)+" "+rs.getString(5));
			System.out.println("\n------------------------------------\n");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	void meth3()
	{
		System.out.println("Implementing ScroallableResultSet ==> Updatable");
		System.out.println("========================================\n");
		try
		{
			Connection con=connect();
			Statement stmt2=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rs=stmt2.executeQuery("select eid, efname, esal from employee");
			while(rs.next())
			{
				String empId = rs.getString(1);
				if(empId.equals("102"))
				{
					System.out.println("Updating the Salary of employee : "+empId);
					rs.updateInt("esal", 80000);
					rs.updateRow();
				}
			}
			System.out.println("Record Updateed\n");
		
			
			Statement stmt =con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs1 =stmt.executeQuery("select * from employee");
			
			System.out.println("Updated record of empId: 102");
			rs1.absolute(3);
			System.out.println(rs1.getString(1)+" "+ rs1.getString(2)+" "+rs1.getString(3)+" "+rs1.getInt(4)+" "+rs1.getString(5));
			
			System.out.println("\nAll records----->\n");
			rs1.beforeFirst();
			while(rs1.next()) 
			{
				System.out.println(rs1.getString(1)+" "+ rs1.getString(2)+" "+rs1.getString(3)+" "+rs1.getInt(4)+" "+rs1.getString(5));
			}
			
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Jdbc_App6 obj=new Jdbc_App6();
		obj.meth1();
		obj.meth2();
		obj.meth3();
		
	}
	
}
