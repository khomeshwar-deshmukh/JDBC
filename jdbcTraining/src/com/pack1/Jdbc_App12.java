package com.pack1;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class Jdbc_App12 {

	private String driver = "oracle.jdbc.OracleDriver";
	private String dbUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
	private String dbUname = "SYSTEM";
	private String dbUpwd = "1234";
	
	public Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName(driver);
			con=DriverManager.getConnection(dbUrl,dbUname,dbUpwd);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Connection successful\n");
		return con;
		
	}
	
	void meth1()
	{
		System.out.println("Implementing Metadata\n");
	  try
	  {
		 Connection con = connect();
		 
		 
		 DatabaseMetaData dmdt=con.getMetaData();
		 System.out.println(" ------ DatabaseMetaData -----");
		 System.out.println("getDatabaseProductName() : "+dmdt.getDatabaseProductName());
		 System.out.println("getDatabaseProductVersion() : "+dmdt.getDatabaseProductVersion());
		 System.out.println("getDriverName() : "+dmdt.getDriverName());
		 System.out.println("supportsStoredProcedures() : "+dmdt.supportsStoredProcedures());

		 PreparedStatement pstmt=con.prepareStatement("select * from employee where eid =? ");
		 pstmt.setString(1, "101");
		 ResultSet rs=pstmt.executeQuery();

		 System.out.println("\n ------ ParameterMetaData ------- ");
		 ParameterMetaData pmtdt=pstmt.getParameterMetaData();
		 System.out.println("getParameterCount() : "+pmtdt.getParameterCount());
		 System.out.println("getParameterType() : "+pmtdt.getParameterType(1));
		 System.out.println("getParameterMode() : "+pmtdt.getParameterMode(1));
		 System.out.println("isNullable() : "+pmtdt.getParameterCount());

		 ResultSetMetaData rsmtdt=rs.getMetaData();
		 System.out.println("\n ------ ResultSetMetaData ---\n");
		 System.out.println("getColumnCount() : "+rsmtdt.getColumnCount());
		 System.out.println("getColumnName() : "+rsmtdt.getColumnName(2));
		 System.out.println("getColumnDisplaySize() : "+rsmtdt.getColumnDisplaySize(2));
		 System.out.println("isAutoIncrement() : "+rsmtdt. isAutoIncrement(2));
		 
		 RowSetFactory rsf=RowSetProvider.newFactory();
		 CachedRowSet crs=rsf.createCachedRowSet();
		 crs.setUrl(dbUrl);
		 crs.setUsername(dbUname);
		 crs.setPassword(dbUpwd);
		 crs.setCommand("select eid,efname,esal from employee");
		 crs.execute();

		 RowSetMetaData row_stmt=(RowSetMetaData)crs.getMetaData();
		 System.out.println("\n ------ RowSetMetaData ---\n");
		 System.out.println("getColumnCount() : "+row_stmt.getColumnCount());
		 System.out.println("getColumnName() : "+row_stmt.getColumnName(2));
		 System.out.println("getColumnDisplaySize() : "+row_stmt.getColumnDisplaySize(2));
		 System.out.println("isAutoIncrement() : "+row_stmt.isAutoIncrement(2));
	  }
	  catch(Exception e)
	  {
		  e.printStackTrace();
	  }
	}
	public static void main(String[] args) {
		new Jdbc_App12().meth1();
	}
}
