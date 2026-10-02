package com.pack1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Jdbc_App3 {

    Scanner sc = new Scanner(System.in);

    private String driver = "oracle.jdbc.OracleDriver";
    private String dbUrl = "jdbc:oracle:thin:@localhost:1521:orcl";
    private String dbUname = "SYSTEM";
    private String dbUpwd = "1234";

    private String sqlQuery = "SELECT * FROM EMPLOYEE";

    public Connection connect() {
        Connection con = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(dbUrl, dbUname, dbUpwd);
            System.out.println("Database Connected");
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    public void get_Data() {

        

        try {
            Connection con = connect();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(sqlQuery);

            boolean flag = false;
            System.out.println("Which employee record do you want to view?");
            String name = sc.nextLine();

            while (rs.next()) {
                if (rs.getString(2).equalsIgnoreCase(name)) {
                    System.out.println(rs.getString(1) + " " +
                                       rs.getString(2) + " " +
                                       rs.getInt(4));
                    flag = true;
                }
            }

            if (flag) {
                System.out.println("Your details are above.");
            } else {
                System.out.println("Employee " + name + " record not available.");
            }

            rs.close();
            stm.close();
            con.close();
            sc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Jdbc_App3().get_Data();
    }
}