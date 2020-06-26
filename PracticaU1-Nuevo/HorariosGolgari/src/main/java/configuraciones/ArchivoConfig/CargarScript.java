package configuraciones.ArchivoConfig;

import java.io.*;
import java.sql.*;
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

            System.out.println(scriptCargado);

            //VERIFICANDO LA CONEXIÓN A LA BASE DE DATOS
            switch (gestor){
                case "Mysql":


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



                    break;
                case "Sqlite":


                    for (int i = 2; i < scriptCargado.size(); i++){
                        try(Connection conn = DriverManager.getConnection(url+NombreBaseDeDatos+".db");
                            Statement stmt = conn.createStatement()) {
                            //PREPARANDO LA SENTENCIA PARA SER EJECUTADA LA LINEA DE COMANDO Y CREAR, AGREGAR NUEVOS DATOS A LA BASE DE DATOS
                            stmt.execute(scriptCargado.get(i));

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }





                    break;
                default:
            }

        }
    }
}
