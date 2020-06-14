package sqlConn.archivosSQL;

import java.sql.*;

public class ConectarBD {

    private static Connection conn;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String password = null;
    private static final String bd_nombre = "horarios";
    private static final String url = "jdbc:mysql://localhost:3306/"+bd_nombre;

    //Con esto se conecta a la base de datos
    public ConectarBD(String user2, String password2, String url2,String nombre_BaseDeDatos){
        conn = null;

        try{
            Class.forName(driver);

            if(password2.contains("null")){
                conn = DriverManager.getConnection(url2,user2,null);
                Statement baseDeDatos = conn.createStatement();
                baseDeDatos.executeUpdate("CREATE DATABASE IF NOT EXISTS "+nombre_BaseDeDatos);
                Statement seleccioanrBase = conn.createStatement();
                seleccioanrBase.executeUpdate("USE "+nombre_BaseDeDatos);
            }else {
                conn = DriverManager.getConnection(url2,user2,password2);
                Statement baseDeDatos = conn.createStatement();
                baseDeDatos.executeUpdate("CREATE DATABASE IF NOT EXISTS "+nombre_BaseDeDatos);
                Statement seleccioanrBase = conn.createStatement();
                seleccioanrBase.executeUpdate("USE "+nombre_BaseDeDatos);
            }

        }catch (SQLSyntaxErrorException e){
        }catch (SQLException e){
            System.out.println("2 "+e);
        }catch (ClassNotFoundException e){
            System.out.println("1 "+e);
        }catch (NullPointerException n){
            System.out.println("Ruta no encontrada");
        }catch (Exception e){
            System.out.println("Error al cargar el script");
        }
    }

    //Retorna la conexión
    public Connection getConection(){
        return conn;
    }

    //Con este metodo te desconectas de la base de datos
    public void desconectar(){
        conn = null;
    }

}
