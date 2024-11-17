/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;

/**
 *
 * @author Trung
 */
public class DBContext {
    private String host;
    private String instance;
    private String port;
    private String username;
    private String password;
    private String databaseName;
    
    public DBContext(ServletContext sc) {
        this.host = sc.getInitParameter("dbHost");
        this.instance = sc.getInitParameter("dbInstance");
        this.port = sc.getInitParameter("dbPort");
        this.username = sc.getInitParameter("dbUsername");
        this.password = sc.getInitParameter("dbPassword");
        this.databaseName = sc.getInitParameter("dbDatabaseName");
    }
    
    public Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(getUrl(), username, password);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
    
    private String getUrl() {
        return String.format("jdbc:sqlserver://%s\\%s:%s;databaseName=%s", host, instance, port, databaseName);
    }
}
