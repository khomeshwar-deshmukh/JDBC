package com.pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Savepoint;
import java.util.Scanner;

public class Jdbc_App10 {

	private static final String driver ="oracle.jdbc.OracleDriver";
	private static final String dbUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String dbUnmae ="SYSTEM";
	private static final String dbUpwd ="1234";
	
	static Scanner sc = new Scanner(System.in);
	
	String sqlQrey1 = "select balance from bank_account where acc_no = ?";
	String sqlQrey2 = "update bank_account set balance = balance - ? where acc_no = ?";  
	String sqlQrey3 = "update bank_account set balance = balance + ? where acc_no = ?"; 
	
	
	
	
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
		Savepoint sp = null;
		System.out.println("      Implementing Transaction Management");
		System.out.println("==================================================\n");
		try
		{
			con.setAutoCommit(false);
			System.out.println("Ststus of Auto Commit: "+ con.getAutoCommit());
		
			PreparedStatement pstmt1 = con.prepareStatement(sqlQrey1);
			System.out.println("Enter your Account Number");
			int acc_no = Integer.parseInt(sc.nextLine());
			pstmt1.setInt(1, acc_no);
			ResultSet rs1= pstmt1.executeQuery();
			
			if (rs1.next()) 
			{
			    int balance = rs1.getInt("balance");
                 
			    System.out.println("Enter Amount");
		        float amount = Float.parseFloat(sc.nextLine());
			    
			    if (balance >= amount) 
			    {
			        System.out.println("Sufficient balance");
			        PreparedStatement pstmt2 = con.prepareStatement(sqlQrey2);
								        
			        pstmt2.setFloat(1,amount);
			        pstmt2.setInt(2, acc_no);
			        
			        int rowCount2 =pstmt2.executeUpdate();
			        
			        sp = con.setSavepoint();
			        // Simulating credit failure
			        // int x = 10 / 0;
			        
			        if(rowCount2>0) //balance<(balance+amount)
					{
			        	System.out.println("Amount Debited Successfully -----> Account Number: "+ acc_no);
			            System.out.println("Remaining Balance : " + (balance - amount));
			        	PreparedStatement pstmt3 = con.prepareStatement(sqlQrey3);
						pstmt3.setFloat(1,amount);
				        pstmt3.setInt(2, 102);
				        int rowCount3 =pstmt3.executeUpdate();
						if(rowCount3>0)
						{ 
							System.out.println("\n"+amount+" Amount Successfully Credited ----> Account Number: 102");
							con.commit();
							
							rs1.close();
							pstmt1.close();
							con.close();
						}	
					}
					else
					{
						System.out.println("crediting fails");
			        	con.rollback(sp);
													
					}
			    } 
			    else 
			    {
			        System.out.println("Insufficient balance");
			        con.rollback();
					System.out.println("crediting fails");
			    }
			} 
			else
			{
				System.out.println("Transaction failed");
			}
			
		}		
		catch(Exception e)
		{
			 System.out.println("Transaction Failed");  
			    e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		Jdbc_App10 obj=new Jdbc_App10();
		obj.meth1();
	}
}
