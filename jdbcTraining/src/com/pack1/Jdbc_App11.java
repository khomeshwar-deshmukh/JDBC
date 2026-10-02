package com.pack1;

import java.sql.Connection;

public class Jdbc_App11 {

	private String driver ="oracle.jdbc.OracleDriver";
	private String dbUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private String dbUnmae ="SYSTEM";
	private String dbUpwd ="1234";
	
	
	ConnectionPool cp = new ConnectionPool(driver, dbUrl, dbUnmae, dbUpwd);
	
	void meth1()
	{
		System.out.println("Implementing Connection Pooling");
		cp.con_Initialization();
		
		System.out.println("\n---------------User1---------------");
		Connection con1 =cp.con_Acquisition();
		System.out.println("After User1: "+ cp.v.size());
		
		System.out.println("\n---------------User2---------------");
		Connection con2 =cp.con_Acquisition();
		System.out.println("After User2: "+ cp.v.size());
		
		System.out.println("\n---------------User3---------------");
		Connection con3 =cp.con_Acquisition();
		System.out.println("After User3: "+ cp.v.size());
		
		
		cp.con_Return(con1);
		cp.con_Return(con2);
		cp.con_Return(con3);

	
	}
	public static void main(String[] args) {
		Jdbc_App11 obj = new Jdbc_App11();
		obj.meth1();
	}
}
