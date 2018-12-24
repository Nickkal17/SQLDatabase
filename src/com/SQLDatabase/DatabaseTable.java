package com.SQLDatabase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTable {
    
    Connection db_con;
    
    public DatabaseTable(DatabaseConnection db){
        
        try{
            
            db_con = db.getConnection();
            DatabaseMetaData dbConnectionData = db_con.getMetaData();
            ResultSet dbResultSet = dbConnectionData.getTables(null, null, "%", null);
            
            while(dbResultSet.next()){
                System.out.println(dbResultSet.getString(3));
            }
            
        }catch(SQLException ex){
            
        }
    }
    
}
