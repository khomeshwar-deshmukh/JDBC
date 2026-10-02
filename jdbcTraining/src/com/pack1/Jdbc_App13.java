package com.pack1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Jdbc_App13 {

	private static final String driver ="oracle.jdbc.OracleDriver";
	private static final String dbUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String dbUnmae ="SYSTEM";
	private static final String dbUpwd ="1234";
	
	String sqlQuery1 ="insert into mydata values(?,?)";
	String sqlQuery2 ="select PIC_DATA from mydata where id=?";
	
	
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
		System.out.println("Passing an Image into the database\n");
		try
		{
			Connection con =connect();
			PreparedStatement pstmt = con.prepareStatement(sqlQuery1);
			pstmt.setString(1, "101");
			
			FileInputStream fis =new FileInputStream("D:\\PhoneWallpaper\\main.png");
			pstmt.setBlob(2, fis,fis.available());
			int rowCount = pstmt.executeUpdate();
			
			if(rowCount == 0)
			{
				throw new RuntimeException("Image Insertion failed");
			}
			System.out.println("Image Inserted !!!! ");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	void meth2()
	{
		System.out.println("Retriving an Image from the database\n");
		
		try
		{
			Connection con =connect();
			PreparedStatement pstmt = con.prepareStatement(sqlQuery2);
			pstmt.setString(1, "101");
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				Blob img_data =rs.getBlob(1);
				byte arr[] =img_data.getBytes(1,(int)img_data.length());
				//byte arr[]=img_data.getBytes(1, 1024*200);
				
				FileOutputStream fos = new FileOutputStream("D:\\PhoneWallpaper\\main77Copy.png");
				fos.write(arr);
				fos.close();
				
			}
			System.out.println("Image retrived");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Jdbc_App13 obj =  new Jdbc_App13();
		//obj.meth1();
		obj.meth2();
	}
	
}
