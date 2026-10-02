package com.pack1;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Jdbc_App14 {

	private static final String driver ="oracle.jdbc.OracleDriver";
	private static final String dbUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String dbUnmae ="SYSTEM";
	private static final String dbUpwd ="1234";
	
	String sqlQuery1 ="insert into mydata2 values(?,?)";
	String sqlQuery2 ="select FILE_DATA from mydata2 where id=?";
	
	
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
		System.out.println("Passing an File into the database\n");
		try
		{
			Connection con =connect();
			PreparedStatement pstmt = con.prepareStatement(sqlQuery1);
			pstmt.setString(1, "101");
			
			FileReader fr =new FileReader("D:\\PhoneWallpaper\\file1.txt");
			pstmt.setClob(2, fr);
			int rowCount = pstmt.executeUpdate();
			
			if(rowCount == 0)
			{
				throw new RuntimeException("File NOT Inserted");
			}
			System.out.println("File Inserted !!!! ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	void meth2()
	{
		System.out.println("Retriving an File from the database\n");
		
		try
		{
			Connection con =connect();
			PreparedStatement pstmt = con.prepareStatement(sqlQuery2);
			pstmt.setString(1, "101");
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				Clob file_data =rs.getClob(1);
				Reader data = file_data.getCharacterStream();
				BufferedReader br = new BufferedReader(data);
				
				FileWriter fw = new FileWriter("D:\\PhoneWallpaper\\file2.txt");
				
				String line;
				while((line=br.readLine())!= null)
				{
					fw.write(line);
				}
				br.close();
				fw.close();
				System.out.println("File retrived");
			}
			else
			{
				throw new SQLException("Invalide Id");
			}
	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Jdbc_App14 obj =  new Jdbc_App14();
		//obj.meth1();
		obj.meth2();
	}
	
	
	
}
