package configuraciones.ArchivoConfig;

import datosXLSX.archivosXLSX.*;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class LeyendoConfiguraciones {

    ArrayList<String> cadenasDeDatos = new ArrayList<String>();
    ArchivoXLSX arcXlsx = new ArchivoXLSX();

    File archivo2 = new File("src/main/resources/configuracion.txt");

    String auxiliar = "", gestor = "", usuario = "", contrasena = "", nombreBD = "", url = "", tipoArc = "", nomArc = "";

    public void archivoDeConfiguracion() {

        try {
            String path = archivo2.getCanonicalPath();
            File archivo = new File(path);
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);

            auxiliar = br.readLine();

            while (auxiliar != null) {
                cadenasDeDatos.add(auxiliar);
                auxiliar = br.readLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("No se encontro nada mi chavo");
        } catch (IOException e) {
            System.out.println("Error al encontrar la ruta");
        }
        for (int i = 1; i < cadenasDeDatos.size(); i++) {
            if (cadenasDeDatos.get(i).contains("gestor")) {
                gestor = cadenasDeDatos.get(i).substring(cadenasDeDatos.get(i).indexOf("{") + 1, cadenasDeDatos.get(i).indexOf("}"));
            } else if (cadenasDeDatos.get(i).contains("url")) {
                url = cadenasDeDatos.get(i).substring(cadenasDeDatos.get(i).indexOf("{") + 1, cadenasDeDatos.get(i).indexOf("}"));
            } else if (cadenasDeDatos.get(i).contains("password")) {
                contrasena = cadenasDeDatos.get(i).substring(cadenasDeDatos.get(i).indexOf("{") + 1, cadenasDeDatos.get(i).indexOf("}"));
            } else if (cadenasDeDatos.get(i).contains("nombre_bd")) {
                nombreBD = cadenasDeDatos.get(i).substring(cadenasDeDatos.get(i).indexOf("{") + 1, cadenasDeDatos.get(i).indexOf("}"));
            } else if (cadenasDeDatos.get(i).contains("tipo de archivo")) {
                tipoArc = cadenasDeDatos.get(i).substring(cadenasDeDatos.get(i).indexOf("{") + 1, cadenasDeDatos.get(i).indexOf("}"));
            } else if (cadenasDeDatos.get(i).contains("user")) {
                usuario = cadenasDeDatos.get(i).substring(cadenasDeDatos.get(i).indexOf("{") + 1, cadenasDeDatos.get(i).indexOf("}"));
            } else if (cadenasDeDatos.get(i).contains("nombre del archivo")) {
                nomArc = cadenasDeDatos.get(i).substring(cadenasDeDatos.get(i).indexOf("{") + 1, cadenasDeDatos.get(i).indexOf("}"));
            }
        }

        try{
            arcXlsx.lecturaDatos( nomArc + ".xlsx", usuario, contrasena, url, nombreBD);
        }catch (SQLException e){
            System.out.println("Error al cargar datos");
        }catch (IOException i){
            System.out.println("Error al ingresar valores");
        }

    }
}
