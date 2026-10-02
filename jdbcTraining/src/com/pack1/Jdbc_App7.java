package com.pack1;

import java.util.Scanner;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;


public class Jdbc_App7 {

	static Scanner sc = new Scanner(System.in);
	
	private static final String dbUrl ="jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String dbUnmae ="SYSTEM";
	private static final String dbUpwd ="1234";
	
	String sqlQuery1 = "select * from employee";
	String sqlQuery2 ="select eid, efname, esal from employee";
	
	
	void meth1()
	{
		System.out.println("1.JdbcRowSet");
		System.out.println("2.CachedRowSet");
		int choice=Integer.parseInt(sc.nextLine());
		
		try
		{
			RowSetFactory rsf=RowSetProvider.newFactory();
			switch(choice)
			{
				case 1:
					System.out.println("Implementing JdbcRowSet");
					
					JdbcRowSet jrs=rsf.createJdbcRowSet();
					jrs.setUrl(dbUrl);
					jrs.setUsername(dbUnmae);
					jrs.setPassword(dbUpwd);
					jrs.setCommand(sqlQuery1);
					jrs.execute();
					
					while(jrs.next())
					{
						System.out.println(jrs.getString(1)+" "+jrs.getString(2)+" "+jrs.getString(3)+" "+jrs.getInt(4)+" "+jrs.getString(5));
					}
					
					System.out.println("------------------------------");
					
					jrs.afterLast();
					while(jrs.previous())
					{
						System.out.println(jrs.getString(1)+" "+jrs.getString(2)+" "+jrs.getString(3)+" "+jrs.getInt(4)+" "+jrs.getString(5));
					}
					System.out.println("------------------------------");
					
					jrs.first();
					System.out.println(jrs.getString(1)+" "+jrs.getString(2)+" "+jrs.getString(3)+" "+jrs.getInt(4)+" "+jrs.getString(5));
					System.out.println("------------------------------");
					
					jrs.last();
					System.out.println(jrs.getString(1)+" "+jrs.getString(2)+" "+jrs.getString(3)+" "+jrs.getInt(4)+" "+jrs.getString(5));
					System.out.println("------------------------------");
					
					jrs.absolute(4);
					System.out.println(jrs.getString(1)+" "+jrs.getString(2)+" "+jrs.getString(3)+" "+jrs.getInt(4)+" "+jrs.getString(5));
					System.out.println("------------------------------");
					
					jrs.relative(1);
					System.out.println(jrs.getString(1)+" "+jrs.getString(2)+" "+jrs.getString(3)+" "+jrs.getInt(4)+" "+jrs.getString(5));
					System.out.println("------------------------------");
					
					System.out.println("\n============================================\n");
					
					break; 
				case 2:
					System.out.println("Implementing CachedRowSet");
					
					
					CachedRowSet crs=rsf.createCachedRowSet();
					crs.setUrl(dbUrl);
					crs.setUsername(dbUnmae);
					crs.setPassword(dbUpwd);
					crs.setCommand(sqlQuery2);
					crs.execute();
					
					while(crs.next())
					{
						
						if(crs.getString(1).equals("105"))
						{
							System.out.println("Employee record found!!!");
							crs.updateInt("esal", 55055 );
							crs.updateRow();
						}
		
					}
					crs.acceptChanges();
					
					//crs.absolute(9);
					//System.out.println(crs.getString(1)+" "+crs.getString(2)+" "+crs.getString(3)+" "+crs.getInt(4)+" "+crs.getString(5));
					
					break;	
				default:
					System.out.println("Invalid Choice");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Jdbc_App7 obj=new Jdbc_App7();
		obj.meth1();
	}
	
}
