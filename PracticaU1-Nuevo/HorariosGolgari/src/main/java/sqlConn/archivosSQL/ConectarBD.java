package sqlConn.archivosSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.SQLSyntaxErrorException;

public class ConectarBD {

    private static Connection conn = null;
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    //Con esto se conecta a la base de datos
    public void conexionBD(String user2, String password2, String url2,String nombre_BaseDeDatos){
        try{
            Class.forName(driver);

            if(password2.contains("null")){
                //CREANDO LA CONEXION DE LA BASE DE DATOS
                conn = DriverManager.getConnection(url2,user2,null);
                System.out.println(conn);
                System.out.println("Hola" + password2);

                //CREANDO LA BASE DE DATOS EN CASO DE NO EXISTIR
                Statement baseDeDatos = conn.createStatement();
                baseDeDatos.executeUpdate("CREATE DATABASE IF NOT EXISTS "+nombre_BaseDeDatos);

                //HABILITANDO LA BASE DE DATOS QUE SE DESEA USAR
                Statement seleccioanrBase = conn.createStatement();
                seleccioanrBase.executeUpdate("USE "+nombre_BaseDeDatos);
            }else {
                //CREANDO LA CONEXION DE LA BASE DE DATOS
                System.out.println(url2);
                System.out.println(user2);
                System.out.println(password2);
                conn = DriverManager.getConnection(url2,user2,password2);
                System.out.println(conn);
                System.out.println("Hola" + password2);

                //CREANDO LA BASE DE DATOS EN CASO DE NO EXISTIR
                Statement baseDeDatos = conn.createStatement();
                baseDeDatos.executeUpdate("CREATE DATABASE IF NOT EXISTS "+nombre_BaseDeDatos);

                //HABILITANDO LA BASE DE DATOS QUE SE DESEA USAR
                Statement seleccioanrBase = conn.createStatement();
                seleccioanrBase.executeUpdate("USE "+nombre_BaseDeDatos);
            }

        }catch (SQLSyntaxErrorException e){
            System.out.println("Error de Sintaxis");
        }catch (SQLException e){//---
            System.out.println("Error al Conectar " + e);
        }catch (ClassNotFoundException e){
            System.out.println("Clase no encontrada");
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
