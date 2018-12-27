package com.SQLDatabase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseTable {
    
    Connection db_con;
    String table_names[];
    int size = 0;
    
    public DatabaseTable(DatabaseConnection db){
        
        
        
        try{
            
            db_con = db.getConnection();
            DatabaseMetaData dbConnectionData = db_con.getMetaData();
            ResultSet dbResultSet = dbConnectionData.getTables("company", null, "%", null);
            
            while(dbResultSet.next()){
                //System.out.println(dbResultSet.getString(3));
                size++;
            }
            
            dbResultSet = dbConnectionData.getTables("company", null, "%", null);
            
            table_names = new String[size];
            int i = 0;
            
            while(dbResultSet.next()){
                table_names[i] = dbResultSet.getString(3);
                i++;
            }
            
            for(i = 0; i < size; i++){
                //System.out.println(table_names[i]);
            }
            
        }catch(SQLException ex){
            
        }
        
      
    }
    
    public String[] getTableNames(){
         
        return table_names;
    }
    
    public int getTableNum(){
        return size;
    }
    
}
