package datosTXT.archivosTXT;

import com.mysql.cj.x.protobuf.MysqlxCursor;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

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

    public void lecturaDatosTxt(String nombreArchivo, String user, String password, String url, String NombreBaseDeDatos){

        File archivo = global;
        FileReader buscar = null;
        BufferedReader bufferedReader;

        String auxiliar = " ", txt = "", linea = "", nomTabla = "",columnas = "", divisiones = "", datos = "";
        String separacion[];
        String tablasTexto[];
        String datosTabla[];
        String filas[];
        String valores[];
        int contador = 1, vColTablas = 0, a = 0, contadorDelContador = 0;

        comandosSinBDTxt.add("CREATE DATABASE IF NOT EXISTS HORARIOS;");

        if (archivo != null) {

            try {
                buscar = new FileReader(archivo);

                bufferedReader = new BufferedReader(buscar);
                auxiliar = bufferedReader.readLine();

                while (auxiliar != null) {
                    txt = txt + " " + auxiliar;
                    auxiliar = bufferedReader.readLine();
                }
            } catch (IOException e) {
                System.out.println("ERROR - NO ES POSIBLE LEER EL ARCHIVO");
            }

            //SEPARANDO LAS TABLAS CONFORME EL ! Y CONCATENANDOLAS EN UNA SOLA LINEA
            tablasTexto = txt.split("!");
            for (int i = 0; i < tablasTexto.length; i++) {
                //EXTRAYENDO LAS LINEAS EN UNA VARIABLE DE TIPO STRING Y PASANDO EL TEXTO A MINUSCULAS
                divisiones = tablasTexto[i];
                divisiones = divisiones.toLowerCase();

                //REALIZANDO QUE LA TABLA CONTENGA LO ESCENCIAL PARA CREARLA
                if(divisiones.contains("columnas") && divisiones.contains("tabla")){
                    //DIVIDIENDO LAS TABLAS EN FRACCIONES SEPARADAS POR EL #
                    datosTabla = divisiones.split("#");

                    for(int t = 0; t < datosTabla.length; t++){ // LEYENDO LOS VALORES DE LA LINEA PREVIAMENTE CARGADOS

                        //EXTRAYENDO LOS VALORES DE LA TABLA
                        linea = datosTabla[t];
                        //OBTENIENDO NOMBRE DE LA TABLA
                        if (linea.contains("tabla") && linea.contains("{") && linea.contains("}")){
                            /*if(contadorDelContador == 0){
                                a++;
                                contadorDelContador++;
                            }*/
                            a++;

                            nomTabla = linea.substring(linea.indexOf("{") + 1, linea.indexOf("}"));
                            comandosSinBDTxt.add(a,"CREATE TABLE IF NOT EXISTS " + nomTabla + " (");

                        //OBTENIENDO COLUMNAS DE LA TABLA
                        }else if (linea.contains("columnas") && linea.contains("{") && linea.contains("}")){
                            columnas = linea.substring(linea.indexOf("{") + 1, linea.indexOf("}"));
                            comandosSinBDTxt.add(a,comandosSinBDTxt.get(a) + columnas + ");");

                            //OBTENIENDO LAS COLUMNAS DE CADA TABLA
                            separacion = columnas.split(" ");
                            for (int r = 0; r < separacion.length; r++) {
                                datos = separacion[r] + " ";
                                if (r == 0) {
                                    columnas = separacion[r];
                                } else if (datos.contains(",")) {
                                    if (!datos.contains(", ")) {
                                        columnas = columnas + datos.substring(datos.indexOf(","), datos.indexOf(" "));
                                    }
                                }
                            }

                        //OBTENIENDO LOS VALORES DE CADA COLUMNA
                        }else if(linea.contains("{") && linea.contains("}") ){
                            //ESTO CONTROLA EL NUMERO DE COLUMNAS QUE CONTIENE LA TABLA ACTUAL
                            filas = columnas.split(",");
                            for(int yy = 0; yy < filas.length; yy++) {
                                linea = datosTabla[t];
                                //ESTO CONTROLA LOS VALORES POR FILA DE LAS COLUMNAS DE LAS TABLAS
                                valores = linea.substring(linea.indexOf("{")+1,linea.indexOf("}")).split("/");
                                for (int tt = 0; tt < valores.length; tt++) {
                                    //System.out.println(valores[tt]);
                                    a++;

                                    if(contador == 1 && (yy + 1 < filas.length)) {
                                        System.out.println(a);
                                        comandosSinBDTxt.add(a, "INSERT INTO " + nomTabla + " ("  + columnas + ") values ("+ valores[tt]);
                                        vColTablas++;
                                    }else if((contador == 2 && (yy + 1 < filas.length)) ){
                                        comandosSinBDTxt.add(a, comandosSinBDTxt.get(a) + "," + valores[tt]);
                                        //comandosSinBDTxt.remove(a+1);
                                       // System.out.println(comandosSinBDTxt.get(a+1));
                                        vColTablas++;
                                    }else if(contador >= 2 && (yy + 1 == filas.length)) {
                                        System.out.println(a);
                                        comandosSinBDTxt.add(a, comandosSinBDTxt.get(a) + "," + valores[tt] + ");");
                                         vColTablas++;
                                         if(comandosSinBDTxt.get(a).contains(comandosSinBDTxt.get(a+1))){
                                             comandosSinBDTxt.remove(a+1);
                                         }

                                       /* System.out.println(a);
                                        System.out.println(comandosSinBDTxt.get(a) + "posicion: "+ a);
                                        System.out.println(comandosSinBDTxt.get(a+1)+ "posicion: "+ (a+1));
                                        System.out.println(comandosSinBDTxt.get(a+2)+ "posicion: "+ (a+2));
                                        System.out.println(comandosSinBDTxt.get(a+3)+ "posicion: "+ (a+3));
                                        System.out.println(comandosSinBDTxt.get(a+4)+ "posicion: "+ (a+4));
                                        System.out.println("---------Gatitos---------------");*/
                                    }

                                    if(vColTablas == valores.length && (yy + 1 < filas.length)) {
                                        contador++;
                                    }else if(vColTablas == valores.length &&(yy + 1 == filas.length)){
                                        contador = 1;
                                        vColTablas = 0;
                                    }
                                }
                                System.out.println("Cortes, a vale: "+ a + " Y las columnas valen: " + vColTablas);
                                a = a - vColTablas;
                                System.out.println("Cortes, a vale: "+ a + " Y las columnas valen: " + vColTablas);
                                vColTablas=0;
                                t++;
                            }
                        }
                    }
                } else if (divisiones.contains("tabla")){  // POSIBLE ERROR 1
                    datosTabla = divisiones.split("#");
                    for(int t = 0; t < datosTabla.length; t++){
                        if(datosTabla[t].contains("tabla")){
                            System.out.println("Error en las columnas de la tabla <" + datosTabla[t].substring(datosTabla[t].indexOf("{") + 1, datosTabla[t].indexOf("}")) + ">");
                            System.out.println("Favor de asignar los valores en base a la plantilla proporcionada");
                            pt.EscribirTxt();
                        }
                    }
                } else if (divisiones.contains("columnas")){ // POSIBLE ERROR 2
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

        //GUARDANDO LOS VALORES ALMACENADOS EN UN SCRIPT
        for (int oo = 0; oo < a; oo++){
           scriptTerminadoTxt.add(comandosSinBDTxt.get(oo));
        }

        //MOSTRANDO LOS VALORES ALMACENADOS
        for (int oo = 0; oo < a; oo++){
            System.out.println(scriptTerminadoTxt.get(oo));
        }

    }
}

