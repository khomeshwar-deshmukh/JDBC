package com.pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Scanner;


public class Jdbc_App4 {
 
	private static final String driver="oracle.jdbc.OracleDriver";
	private static final String dbUrl="jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String dbUname="SYSTEM";
	private static final String dbUpwd="1234";
	
	Scanner sc = new Scanner(System.in);
	 
	public static Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName(driver);
			con = DriverManager.getConnection(dbUrl,dbUname,dbUpwd);
			System.out.println("Database Connected.\n");	
			
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
		
		return con;
	}
	void insert_empRecord()
	{
		System.out.println("Incerting the records in to the Employee Table");
		
		

		 System.out.println("Enter Id");
		 String id = sc.nextLine();

		 System.out.println("Enter your first name");
		 String fName = sc.nextLine();

		 System.out.println("Enter your last name");
		 String lName = sc.nextLine();

		 System.out.println("Enter your salary");
		 double sal = Double.parseDouble(sc.nextLine());

		 System.out.println("Enter location");
		 String loc = sc.nextLine();
		 
		 
		 String sqlQuery =
				    "insert into employee values(" +
				    id + ",'" +
				    fName + "','" +
				    lName + "'," +
				    sal + ",'" +
				    loc + "')";
		
		try 
		{
			Connection con = Jdbc_App4.connect();
			Statement stmt = con.createStatement();
			int rowCount = stmt.executeUpdate(sqlQuery);
			if(rowCount>0)
			{
				System.out.println("Data Inserted");
				System.out.println("Do you want to view the data (Y/N)");
				char choice = sc.nextLine().charAt(0);
				switch(choice)
				{
					case 'y','Y':
						new Jdbc_App2().get_empData();
				    	break;
					case 'n','N':
						System.out.println("Thankyou for inserting your data");
					    break;
				}
			}
			else
			{
				System.out.println("Data not Insreted");
			}
		}
		catch(SQLIntegrityConstraintViolationException sicve)
		{
		    System.out.println("Data is repeated. Please enter valid data.");
		    insert_empRecord();
		    
		}
		catch(Exception e)
		{
		    e.printStackTrace();
		}
	}
	void update_emprecord()
	{
		System.out.println("Updating the Employee Record");
		System.out.println("Enter Employee Id");
		String eid=sc.nextLine();
		System.out.println("Enter Update employe salary");
		int esal = Integer.parseInt(sc.nextLine());
		Connection con= Jdbc_App4.connect();
		try
		{
			Statement stmt = con.createStatement();
			int rowCount = stmt.executeUpdate(
				    "update employee set esal=" + esal + " where eid='" + eid + "'"
				);
			if(rowCount>0)
			{
				System.out.println("Employee record with employee id :"+eid+" Updated");
				display_emprecord(eid);
			}
			else
			{
				System.out.println("there is BO record with Employee id : "+eid+" in the datebase");
			}
		}
		catch(Exception e)
			{
				e.printStackTrace();
			}
       }
	void display_emprecord(String eid)
	{
		
		System.out.println("Retriving the data from the employe table\n");
		try
		{
		Connection con = connect();
		Statement stmt = con.createStatement() ;
		ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEE WHERE eid = '"+eid+"'");
		
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
	void delete_emprecord()
	{
		System.out.println("Deleting the Employee Record");
		System.out.println("Enter Employee Id");
		String eid=sc.nextLine();
		Connection con= Jdbc_App4.connect();
		try
		{
			Statement stmt = con.createStatement();
			int rowCount = stmt.executeUpdate(
				    "delete from employee where eid='" + eid + "'"
				);
			if(rowCount>0)
			{
				System.out.println("Employee record with employee id :"+eid+" Deleted");
				new Jdbc_App2().get_empData();
			}
			else
			{
				System.out.println("there is NO record with Employee id : "+eid+" in the datebase");
			}
		}
		catch(Exception e)
			{
				e.printStackTrace();
			}
       }
		
	public static void main(String[] args) 
	{
		Jdbc_App4 obj=new Jdbc_App4();
		//obj.insert_empRecord();
		//obj.update_emprecord();
		obj.delete_emprecord();
	}
}
