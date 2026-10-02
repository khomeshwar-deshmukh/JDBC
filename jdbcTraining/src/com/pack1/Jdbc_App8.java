package com.pack1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;
import java.util.Scanner;

public class Jdbc_App8 {

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
		System.out.println("  Implementing Callable Statement ==> Inserting");
		System.out.println("==================================================\n");
		System.out.println("  Implementing Procedure in CallableStatement ");
		System.out.println("==================================================\n");
		Connection con =connect();
		try
		{
		  CallableStatement  cstmt =con.prepareCall("{call insertEmpData(?,?,?,?,?)}");
		  
		  System.out.println("Enter Employee Id: ");
		  String emp_id =sc.nextLine();
		  System.out.println("Enter Employee Name: ");
		  String emp_name =sc.nextLine();
		  System.out.println("Enter Employee Desg: ");
		  String emp_desg =sc.nextLine();
		  System.out.println("Enter Employee Basic Sal: ");
		  int emp_bsal = Integer.parseInt(sc.nextLine());
		  float emp_tsal = (float)(emp_bsal+(0.35*emp_bsal)+(0.15*emp_bsal));
		  
		  cstmt.setString(1, emp_id);
		  cstmt.setString(2, emp_name);
		  cstmt.setString(3, emp_desg);
		  cstmt.setInt(4, emp_bsal);
		  cstmt.setFloat(5, emp_tsal);
		  
		  cstmt.execute();
		  System.out.println("Data Inserted!!!!");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	void meth2()
	{
		System.out.println("  Implementing Callable Statement ==> Retrieving");
		System.out.println("==================================================\n");
		System.out.println("  Implementing Procedure in CallableStatement ");
		System.out.println("==================================================\n");
		Connection con =connect();
		try
		{
		  CallableStatement  cstmt =con.prepareCall("{call  ReteriveEmpData(?,?,?,?,?)}");
		  
		  System.out.println("Enter Employee Id: ");
		  String emp_id =sc.nextLine();
		  
		  cstmt.setString(1,emp_id);
		  
		  cstmt.registerOutParameter(2, Types.VARCHAR);
		  cstmt.registerOutParameter(3, Types.VARCHAR);
		  cstmt.registerOutParameter(4, Types.INTEGER);
		  cstmt.registerOutParameter(5, Types.FLOAT);
		  
		  cstmt.execute();
		  System.out.println("Data Retrieved of Employe: "+emp_id+"\n");
		  System.out.println("----------Employee Data-----------");
		  System.out.println("Employee Id: " +emp_id);
		  System.out.println("Employee Name: " +cstmt.getString(2));
		  System.out.println("Employee Desg: " +cstmt.getString(3));
		  System.out.println("Employee Basic Sal: " +cstmt.getInt(4));
		  System.out.println("Employee Total Sal: " +cstmt.getFloat(5));
		  
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			System.out.println("Employee Record NOT FOUND");
		}
	}
	
	void meth3()
	{
		System.out.println("  Implementing Function in CallableStatement ");
		System.out.println("==================================================\n");
		Connection con =connect();
		try
		{
		  CallableStatement  cstmt =con.prepareCall("{call ?:=ReteriveTsal(?)}");
		  
		  System.out.println("Enter Employee Id: ");
		  String emp_id =sc.nextLine();
		  
		  cstmt.setString(2,emp_id);
		  
		  cstmt.registerOutParameter(1, Types.FLOAT);
		  
		  cstmt.execute();
		  System.out.println("----------Employee Data-----------");
		  System.out.println("Employee Id: " +emp_id);
		  System.out.println("Employee Total Sal: " +cstmt.getFloat(1));
		  
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}	
		
	}
	
	
	public static void main(String[] args) {
		Jdbc_App8 obj =new Jdbc_App8();
		//obj.meth1();
		//obj.meth2();
		obj.meth3();
	}
}

