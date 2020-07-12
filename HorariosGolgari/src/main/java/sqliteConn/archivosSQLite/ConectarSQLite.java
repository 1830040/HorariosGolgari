package sqliteConn.archivosSQLite;

import java.sql.*;

public class ConectarSQLite {

    Connection con = null;


    public void CrearSQLite(String url){

        Connection con = null;
        //if (url.contains(null))url="jdbc:sqlite:test.db";
        try {
            con = DriverManager.getConnection(url);
            System.out.println("Opened database successfully");
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


    }

    public Connection getConectionSQLite(){
        return con;
    }
}
