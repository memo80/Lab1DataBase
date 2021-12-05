/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package booksdb.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcMySQL {

    public JdbcMySQL() throws SQLException{

        String database = "MyDB";
        String server = "jdbc:mysql ://myplace.se:3306/" + database + "?UseClientEnc = UTF8";
        String password = "test623";
        String sql = "SELECT * FROM book";
        Statement stmt = null;

        try {

            Connection con = DriverManager.getConnection(database, server, password);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                int eno = rs.getInt ("eNo");
                String name = rs.getString ("name");
            }
            

        }
        catch (SQLException e){
            System.out.println("error");
        }
        finally {
            stmt.close();
        }
    }
    
}
