/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SQLDatabase;

import java.sql.*;
import javax.swing.JOptionPane;


public class DatabaseConnection {
    
    public static String userName;
    public static char [] dpassword;
    
    
    public static Connection getConnection(){
        
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/company?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
            String username = userName;
            String passwordText = new String(dpassword);
            
            //System.out.println(passwordText);
            
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, passwordText);
            System.out.println("Connected");
            return con;
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Access Denied", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println(ex.getMessage());
        }
        
        return null;
    }
    
}


