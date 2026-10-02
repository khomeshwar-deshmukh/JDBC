package com.pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Jdbc_App5 {
  
	private static final String driver ="oracle.jdbc.OracleDriver";
	private static final String dbUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String dbUnmae ="SYSTEM";
	private static final String dbUpwd ="1234";
	
	Scanner sc = new Scanner(System.in);
	
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
	void pat_Operations() 
	{
		Connection con = Jdbc_App5.connect();
		try
		{
			PreparedStatement pstmt1 = con.prepareStatement("insert into patient values(?,?,?,?)");
			PreparedStatement pstmt2 = con.prepareStatement("select * from patient");
			PreparedStatement pstmt3 = con.prepareStatement("select * from patient where PID=?");
			PreparedStatement pstmt4 = con.prepareStatement("update patient set PCONTACT = ? where PID=?");
			PreparedStatement pstmt5 = con.prepareStatement("delete from patient where PID=?");
			while(true) 
			{
				System.out.println("\nWelcome to Patient database");
				System.out.println("1.Add Patient Data \n2.View Patient Data \n3.Reterive Patient Data \n4.Update patient Data \n5.Delete Patient Data \n6.Exit");
				int choice = Integer.parseInt(sc.nextLine());
				
				switch(choice)
				{
					case 1:
						System.out.println("Add patient data to database");
						System.out.println("Enter Patient ID");
						String id=sc.nextLine();
						System.out.println("Enter Patient Name");
						String name=sc.nextLine();
						System.out.println("Enter Patient Age");
						int age=Integer.parseInt(sc.nextLine());
						System.out.println("Enter Patient Contact");
						long cont=Long.parseLong(sc.nextLine());
						
						pstmt1.setString(1,id);
						pstmt1.setString(2,name);
						pstmt1.setInt(3,age);
						pstmt1.setLong(4,cont);
						
						int count_row = pstmt1.executeUpdate();
						if(count_row>0)
							System.out.println("Patient Record Incirted");
						else
							throw new RuntimeException("Patient Record NOT Inserted");
							
						break;
					case 2:
						System.out.println("Patient database");
						ResultSet rs = pstmt2.executeQuery();
						while(rs.next())
						{
							System.out.println(rs.getString(1)+" "+ rs.getString(2)+" "+rs.getInt(3)+" "+rs.getLong(4));
							
						}
						break;
					case 3:
						System.out.println("Reteriving patient data");
						System.out.println("Enter Patient ID");
						String pid =sc.nextLine();
						pstmt3.setString(1,pid);
						ResultSet rs1 = pstmt3.executeQuery();
						if(rs1.next())
						{
							System.out.println(rs1.getString(1)+" "+ rs1.getString(2)+" "+rs1.getInt(3)+" "+rs1.getLong(4));
							
						}

						else
						{
							System.out.println("Patient record NOT Found");
						}
						break;
					case 4:
						System.out.println("Updating patient data");
						System.out.println("Enter Patient ID");
						String pid1 =sc.nextLine();
						
						System.out.println("Enter Patient Contact to Update");
						long cont1 =Long.parseLong(sc.nextLine());
						
						
						pstmt4.setLong(1,cont1);
						pstmt4.setString(2,pid1);
						int rouCount = pstmt4.executeUpdate();
						
						if(rouCount>0)
						{
							System.out.println("Patient record updated");
						}
						else
						{
							System.out.println("there is NO record with Patient ID: "+pid1+" in the datebase");
						}
						
						break;
					case 5:
						System.out.println("Deleting patient data");
						System.out.println("Enter Patient ID");
						String pid2 =sc.nextLine();
						pstmt5.setString(1,pid2);
						int rouCount1 = pstmt5.executeUpdate();
						
						if(rouCount1>0)
						{
							System.out.println("Patient record deleted");
						}
						else
						{
							System.out.println("there is NO record with Patient ID: "+pid2+" in the datebase");
						}
						break;
					case 6:
						System.out.println("Thank you for visiting");
						System.exit(0);
						break;
					default:
						System.out.println("Invalid input");
				}	 
			}
		}
		catch(Exception e)
		{
			 e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Jdbc_App5().pat_Operations();
	}

}

