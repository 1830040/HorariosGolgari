package sqlConn.archivosSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.SQLSyntaxErrorException;

public class ConectarBD {

    //DECLARANDO LA VARIABLE DE CONEXIÓN Y LOS DRIVERS A UTILIZAR
    private static Connection conn = null;
    private static final String driver = "com.mysql.cj.jdbc.Driver";

    //CONEXIÓN A BASE DATOS, TOMANDO USUARIO, CONTRASEÑA, URL Y NOMBRE DE LA BASE DE DATOS
    public void conexionBD(String user2, String password2, String url2,String nombre_BaseDeDatos){
        try{
            //HABILITANDO LOS DRIVERS DE CONEXIÓN
            Class.forName(driver);

            if(password2.contains("null")){
                //CREANDO LA CONEXION DE LA BASE DE DATOS
                conn = DriverManager.getConnection(url2,user2,null);

                //CREANDO LA BASE DE DATOS EN CASO DE NO EXISTIR
                Statement baseDeDatos = conn.createStatement();
                baseDeDatos.executeUpdate("CREATE DATABASE IF NOT EXISTS "+nombre_BaseDeDatos);

                //HABILITANDO LA BASE DE DATOS QUE SE DESEA USAR
                Statement seleccioanrBase = conn.createStatement();
                seleccioanrBase.executeUpdate("USE "+nombre_BaseDeDatos);
            }else {
                //CREANDO LA CONEXION DE LA BASE DE DATOS
                conn = DriverManager.getConnection(url2,user2,password2);

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
            System.out.println("Error al Conectar ");
        }catch (ClassNotFoundException e){
            System.out.println("Clase no encontrada");
        }catch (NullPointerException n){
            System.out.println("Ruta no encontrada");
        }catch (Exception e){
            System.out.println("Error al cargar el script");
        }
    }

    //RETORNO DE LA CONEXIÓN
    public Connection getConection(){
        return conn;
    }

    //METODO PARA DESCONECTARSE DE LA BASE DE DATOS
    public void desconectar(){
        conn = null;
    }

}
