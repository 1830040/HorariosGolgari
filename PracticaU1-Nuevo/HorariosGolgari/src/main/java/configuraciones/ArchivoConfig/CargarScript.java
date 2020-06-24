package configuraciones.ArchivoConfig;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import sqlConn.archivosSQL.*;

public class CargarScript {

    ConectarBD conect = new ConectarBD();
    static ArrayList<String> scriptCargado = new ArrayList<String>();

    public static File global = new File("src/main/resources/BaseDeDatosGolgari.sql");

    public void cargaBDScript(String nombreArchivo,String user,String password,String url,String NombreBaseDeDatos, String gestor) {

        File archivo = global;
        FileReader buscar = null;
        BufferedReader lectura;
        String auxiliar = " ";

        if (archivo != null) {
            try {
                buscar = new FileReader(archivo);
            } catch (FileNotFoundException e) {
                System.out.println("ERROR - ARCHIVO NO ENCONTRADO");
            }
            lectura = new BufferedReader(buscar);
            try {
                auxiliar = lectura.readLine();
            } catch (IOException e) {
                System.out.println("ERROR - NO ES POSIBLE LEER EL ARCHIVO");
            }
            while (auxiliar != null) {
                try {
                    scriptCargado.add(auxiliar);
                    auxiliar = lectura.readLine();
                } catch (IOException e) {
                    System.out.println("ERROR - NO ES POSIBLE LEER EL ARCHIVO");
                }
            }

            //VERIFICANDO LA CONEXIÓN A LA BASE DE DATOS
            conect.conexionBD(user, password, url, NombreBaseDeDatos);
            Connection reg = conect.getConection();

            for (int i = 0; i < scriptCargado.size(); i++){
                try {
                    PreparedStatement b = reg.prepareStatement(scriptCargado.get(i));
                    b.executeUpdate();
                }catch (SQLException e){
                    System.out.println("Error al ingresar datos");
                    System.out.println("Usar la platilla Generada");
                }catch (NullPointerException e){
                    System.out.println("Ruta no encontrada para el Script");
                }
            }
        }
    }
}
