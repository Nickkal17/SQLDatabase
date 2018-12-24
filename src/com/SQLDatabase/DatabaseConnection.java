/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SQLDatabase;

import java.sql.*;

/**
 *
 * @author Nick
 */
public class DatabaseConnection {
    
    public static Connection getConnection(){
        
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/company";
            String username = "root";
            String password = "nikosm9599@17";
            
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected");
            return con;
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        return null;
    }
    
}


