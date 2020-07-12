package datosTXT.archivosTXT;

import datosXLSX.archivosXLSX.PlantillaExcel;
import sqlConn.archivosSQL.ConectarBD;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class ArchivoTXT {

    PlantillaTxt pt = new PlantillaTxt();
    ConectarBD conect = new ConectarBD();
    static ArrayList<String> comandosSinBDTxt = new ArrayList<String>();
    static ArrayList<String> scriptTerminadoTxt = new ArrayList<String>();

    static File global = new File("src/main/resources/BaseDeDatosGolgari.txt");

    public void lecturaDatosTxt(String nombreArchivo, String user, String password, String url, String NombreBaseDeDatos, String gestor){

        File archivo = global;
        FileReader buscar = null;
        BufferedReader bufferedReader;

        String auxiliar = " ", txt = "", linea = "", nomTabla = "",columnas = "", divisiones = "", datos = "";
        String separacion[];
        String tablasTexto[];
        String datosTabla[];
        String filas[];
        String valores[];
        int contador = 1, vColTablas = 1, a = 0, contadorDelContador = 0;

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
                            comandosSinBDTxt.add(a,comandosSinBDTxt.get(a) + columnas);

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
                            for(int yy = 0; yy < (filas.length - (filas.length - 1)); yy++) {
                                linea = datosTabla[t];
                                //ESTO CONTROLA LOS VALORES POR FILA DE LAS COLUMNAS DE LAS TABLAS
                                valores = linea.substring(linea.indexOf("{")+1,linea.indexOf("}")).split("/");
                                for (int tt = 0; tt < valores.length; tt++) {

                                    a++;
                                    contadorDelContador++;

                                    if(contador == 1){
                                        comandosSinBDTxt.add(a, "INSERT INTO " + nomTabla + " ("  + columnas + ") values ("+ valores[tt]);
                                    }else if(contador >= 1){

                                        if(comandosSinBDTxt.get(a-1).contains(comandosSinBDTxt.get(a))){
                                            comandosSinBDTxt.remove(a);
                                        }
                                        comandosSinBDTxt.add(a, comandosSinBDTxt.get(a)+", "+ valores[tt]);
                                    }


                                    if (tt + 1 == valores.length && filas.length == vColTablas) {
                                        contador = 1;
                                        contadorDelContador = 0;
                                        vColTablas = 0;
                                    }else if (tt + 1 == valores.length && filas.length > yy + 1) {
                                        a = a - contadorDelContador;
                                        contadorDelContador = 0;
                                        contador++;
                                    }
                                }
                                vColTablas++;
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
            if(!(oo == 0)){
                scriptTerminadoTxt.add(comandosSinBDTxt.get(oo) + ");");
            }else{
                scriptTerminadoTxt.add(comandosSinBDTxt.get(oo));
            }
        }
        //VERIFICANDO LA CONEXIÓN A LA BASE DE DATOS

        switch (gestor){
            case "Mysql":


                conect.conexionBD(user, password, url, NombreBaseDeDatos);
                Connection reg = conect.getConection();

                for (int contador2 = 0; contador2 < scriptTerminadoTxt.size(); contador2++) {
                    try {
                        //PREPARANDO LA SENTENCIA PARA SER EJECUTADA LA LINEA DE COMANDO Y CREAR, AGREGAR NUEVOS DATOS A LA BASE DE DATOS
                        PreparedStatement b = reg.prepareStatement(scriptTerminadoTxt.get(contador2));
                        b.executeUpdate();
                    } catch (SQLSyntaxErrorException e) {
                        System.out.println("Error al ingresar datos con un txt");
                        System.out.println("Usar la platilla Generada");
                        PlantillaTxt ptxt = new PlantillaTxt();
                        ptxt.EscribirTxt();
                    } catch (SQLIntegrityConstraintViolationException d) {
                        System.out.println("Error al ingresar datos con un txt");
                        System.out.println("Datos duplicados");
                    } catch (NullPointerException e) {
                        System.out.println("Ruta no encontrada para cargar los datos del txt");
                    } catch (SQLException e) {
                        System.out.println("Error de sintaxis");
                    }
                }


                break;
            case "Sqlite":


                for (int contador2 = 1; contador2 < scriptTerminadoTxt.size(); contador2++) {
                    try(Connection conn = DriverManager.getConnection(url+NombreBaseDeDatos+".db");
                        Statement stmt = conn.createStatement()) {
                        //PREPARANDO LA SENTENCIA PARA SER EJECUTADA LA LINEA DE COMANDO Y CREAR, AGREGAR NUEVOS DATOS A LA BASE DE DATOS
                        stmt.execute(scriptTerminadoTxt.get(contador2));
                    } catch (SQLSyntaxErrorException e) {
                        System.out.println("Error al ingresar datos");
                        System.out.println("Usar la platilla Generada");
                        PlantillaExcel pex = new PlantillaExcel();
                        pex.EscribirEXCEL();
                    } catch (SQLIntegrityConstraintViolationException d) {
                        System.out.println("Error al ingresar datos");
                        System.out.println("Datos duplicados");
                    } catch (NullPointerException e) {
                        System.out.println("Ruta no encontrada para cargar los datos del xlsx");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }


                break;
            default:
        }



    }
}
