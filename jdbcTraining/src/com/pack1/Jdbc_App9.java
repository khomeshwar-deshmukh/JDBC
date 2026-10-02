package com.pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.util.Scanner;


public class Jdbc_App9 {

	private static final String driver ="oracle.jdbc.OracleDriver";
	private static final String dbUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String dbUnmae ="SYSTEM";
	private static final String dbUpwd ="1234";
	
	static Scanner sc = new Scanner(System.in);
	
	String sqlQrey1 = "update trainseatavailability set available_seats = available_seats-1 where train_id=? and journey_date=? and class=? and available_seats>0";
	String sqlQrey2 = "INSERT INTO bookingdetails VALUES(?,?,?,?,?)";
	String sqlQrey3 = "select payment_status from customerpayment where customer_id=?"; 
	String sqlQrey4 = "update bookingdetails set status='Success' where customer_id=?";
	
	
	
	
	
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
		Connection con = connect();
		System.out.println("      Implementing Transaction Management");
		System.out.println("==================================================\n");
		try
		{
			con.setAutoCommit(false);
			System.out.println("Ststus of Auto Commit: "+ con.getAutoCommit());
		
			PreparedStatement pstmt = con.prepareStatement(sqlQrey1);
			
			System.out.println("Enter Tarin Id ");
			String tid= sc.nextLine();	
			System.out.println("Enter Journey Date ");
			String tdate= sc.nextLine();
			System.out.println("Enter Class ");
			String tclass= sc.nextLine();
			
			pstmt.setString(1, tid);
			pstmt.setString(2, tdate);
			pstmt.setString(3, tclass);
			
			int rowCount =pstmt.executeUpdate();
			if(rowCount>0)
			{
				System.out.println("Seat locked ... BUT NOT YET CONFIRMED");
			}
			else
			{
				throw new RuntimeException("There are NO available seats");
			}
		
			Savepoint sp =con.setSavepoint();
			
			PreparedStatement pstmt2 =con.prepareStatement(sqlQrey2);
			pstmt2.setString(1,"B101");
			pstmt2.setString(2,"12345");
			pstmt2.setString(3,"C123");
			pstmt2.setInt(4,10);
			pstmt2.setString(5,"Payment pending");
			
			int rowCount2 =pstmt2.executeUpdate();
			if(rowCount2>0)
			{
				System.out.println("Booking record Created...Waiting for Payment confirmation");
			}
			else
			{
				throw new RuntimeException("Booking Failed");
			}
			
			
			PreparedStatement pstmt3 =con.prepareStatement(sqlQrey3);
			pstmt3.setString(1,"C123");
			ResultSet rs1= pstmt3.executeQuery();
			String status="Failed";
			if(rs1.next())
			{
				if(rs1.getString(1).equals("Success"))
				{
					PreparedStatement pstmt4 =con.prepareStatement(sqlQrey4);
					pstmt4.setString(1,"C123");
					int rowCount3 = pstmt4.executeUpdate();
					if(rowCount3==0)
					{
						throw new RuntimeException("Transaction failed");
					}
					else
					{
						System.out.println("Booking Confirmed");
						System.out.println("Happy Journey");
						con.commit();//After commit() all the savepoint's will be automatically released
						//con.releaseSavepoint(sp); //not required only for example how manually release Save point (Savepoints are automatically released after a COMMIT)
					}
				}

			}
			else
			{
				con.rollback();
				System.out.println("Database NOT affected with any changes");
			}
			
			
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Jdbc_App9 obj=new Jdbc_App9();
		obj.meth1();
	}
}
