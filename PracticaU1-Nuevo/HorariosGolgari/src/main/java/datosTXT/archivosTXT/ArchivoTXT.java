package datosTXT.archivosTXT;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

public class ArchivoTXT {

    PlantillaTxt pt = new PlantillaTxt();
    static ArrayList<String> comandosSinBDTxt = new ArrayList<String>();
    static ArrayList<String> scriptTerminadoTxt = new ArrayList<String>();

    static File global = new File("src/main/resources/BaseDeDatosGolgari.txt");

    public void lecturaDatosTxt() {

        File archivo = global;
        FileReader buscar = null;
        BufferedReader bufferedReader;

        String auxiliar = " ", txt = "", linea = "",columnas = "", divisiones = "", datos = "";
        String tablasTexto[];
        String datosTabla[];
        String filas[];
        int a = 0;

        if (archivo != null) {
            try {
                buscar = new FileReader(archivo);
            } catch (FileNotFoundException e) {
                System.out.println("ERROR - ARCHIVO NO ENCONTRADO");
            }
            try {
                bufferedReader = new BufferedReader(buscar);
                auxiliar = bufferedReader.readLine();

                while (auxiliar != null) {
                    txt = txt + " " + auxiliar;
                    auxiliar = bufferedReader.readLine();
                }
            } catch (IOException e) {
                System.out.println("ERROR - NO ES POSIBLE LEER EL ARCHIVO");
            }
            comandosSinBDTxt.add("CREATE DATABASE HORARIOS;");

            tablasTexto = txt.split("!");
            for (int i = 0; i < tablasTexto.length; i++) {

                divisiones = tablasTexto[i];
                divisiones = divisiones.toLowerCase();
                System.out.println(divisiones);

                if(divisiones.contains("columnas") && divisiones.contains("tabla")){
                    datosTabla = divisiones.split("#");
                    for(int t = 0; t < datosTabla.length; t++){
                        //System.out.println(datosTabla[t]);
                    }
                } else if (divisiones.contains("tabla")){  //ERROR 1
                    datosTabla = divisiones.split("#");
                    for(int t = 0; t < datosTabla.length; t++){
                        if(datosTabla[t].contains("tabla")){
                            System.out.println("Error en las columnas de la tabla <" + datosTabla[t].substring(datosTabla[t].indexOf("{") + 1, datosTabla[t].indexOf("}")) + ">");
                            System.out.println("Favor de asignar los valores en base a la plantilla proporcionada");
                            pt.EscribirTxt();
                        }
                    }
                } else if (divisiones.contains("columnas")){ //ERROR 2
                    datosTabla = divisiones.split("#");
                    for(int t = 0; t < datosTabla.length; t++){
                        if(datosTabla[t].contains("tabla")){
                            System.out.println("Error no se encuentra un nombre de la tabla");
                            System.out.println("Favor de asignar los valores en base a la plantilla proporcionada");
                            pt.EscribirTxt();
                        }
                    }
                }
            }
        }


        //columnas = divisiones.substring(auxiliar.indexOf("columnas {") + 1, auxiliar.).toLowerCase();
        //System.out.println(columnas);



        /*for(int m = 0; m < scriptTerminado.size(); m++) {
            if(scriptTerminado.get(m).contains("CREATE")){
                System.out.println(" ");
            }
            System.out.println(scriptTerminado.get(m));
        }*/

    }
}

