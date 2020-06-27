package org.conection_db;

import configuraciones.ArchivoConfig.*;

import java.io.IOException;
import java.sql.*;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) throws IOException, SQLException {




        CRUD a = new CRUD();
        a.SelectCondicionado("carrera","idcarrera,nombre_carrera",
                "idcarrera",">=","2");



////////////////////////////////////////////////////////////////////////////////////////a.Insert(); a.DropTable("carrera");
        /*try(Connection conn = DriverManager.getConnection("jdbc:sqlite:horarios.db");
            Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT \n" +
                    "    name\n" +
                    "FROM \n" +
                    "    sqlite_master \n" +
                    "WHERE \n" +
                    "    type ='table' AND \n" +
                    "    name NOT LIKE 'sqlite_%';");


            while(rs.next()){
                System.out.println(rs.getString(1));
            }

        }catch (NullPointerException e){
            System.out.println("No se encontro ningun dato: "+e);
        }catch (SQLException e){
            System.out.println("Error de Sintaxis: " +e);
        }*/
////////////////////////////////////////////////////////////////////////////////////////
        //TODO LO QUE ESTA DENTRO DE ESTOS GUIONES ES PARA VER LA TABLA QUE VAS A MODIFICAR SOLO TIENES QUE MOVER
        //LA LINEA 24 Y 26, LA LINEA 24 SIRVE PARA SELECCIONAR LA TABLA QUE QUIERE VER LOS CAMBIOS Y LA 26 SIRVE
        //PARA MOVERTE DENTRO DE LAS COLUMNAS, EN SQLITE AL PARECER LA PRIMERA COLUMNA ES CON EL NUMERO 1
        //EN MYSQL LA PRIMERA ES 0
        //AHORA LO QUE TE PIDO ES QUE PRUBES TODOS LOS CRUD QUE HICE DE SQLITE Y YA, LOS PROPIOS METODOS TE VAN A DECIR
        //QUE OCUPAN


        /*
        a.UpadteSinCondicion("nomTabla","nomColumna","valor");
        a.UpadteCondicionado("nomTabla","nomColumna","valor","columnaCondicionada","condicion","valorDeCambio");
        a.DeleteDataSinCondicion("nomTabla");
        a.DeleteDataCondicionado("nomTabla","columnaCondicionada", "condicion","valor");
        a.DropTable("nomTabla");
        */

    }

}
