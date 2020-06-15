package org.conection_db;

import configuraciones.ArchivoConfig.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args ) throws IOException, SQLException {

        CRUD a = new CRUD();
        a.InsertarDatos();
        /*
        a.UpadteSinCondicion("nomTabla","nomColumna","valor");
        a.UpadteCondicionado("nomTabla","nomColumna","valor","columnaCondicionada","condicion","valorDeCambio");
        a.DeleteDataSinCondicion("nomTabla");
        a.DeleteDataCondicionado("nomTabla","columnaCondicionada", "condicion","valor");
        a.DropTable("nomTabla");
        */

    }

}
