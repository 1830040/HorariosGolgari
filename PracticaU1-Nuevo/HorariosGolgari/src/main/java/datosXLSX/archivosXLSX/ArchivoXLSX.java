package datosXLSX.archivosXLSX;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sqlConn.archivosSQL.*;

public class ArchivoXLSX {

    /*DECLARACIÓN DE VARIABLES PARA REALIZAR LA CONEXIÓN A LA BASE DE DATOS ASÍ COMO TAMBIÉN
    * LOS VECTORES QUE ALMACENERAN LOS DATOS A AGREGAR*/
    ConectarBD conect = new ConectarBD();
    static ArrayList<String> comandosSinBD = new ArrayList<String>();
    static ArrayList<String> scriptTerminado = new ArrayList<String>();

    //METODO ENCARGADO DE CARGAR LOS DATOS DEL XLSX A LA BASE DE DATOS
    public void InsertarDatos(String nombreArchivo, String user, String password, String url, String NombreBaseDeDatos, String gestor) {
        try{

            //DECLARANDO RUTA DEL ARCHIVO
            File archivo2 = new File("src/main/resources/"+nombreArchivo);

            //EXTRAYENDO RUTA DEL ARCHIVO
            String path = archivo2.getCanonicalPath();
            File global = new File(path);

            //DECLARACIÓN DE VARIABLES PARA EXTRAER VALORES DEL XLSX
            FileInputStream file = null;
            XSSFWorkbook libro = null;
            XSSFSheet hoja1 = null;
            Iterator<Row> recorrerFila1;
            Iterator<Cell> cellIterator = null;
            Row fila1 = null;
            Cell celda = null;
            String nombreHoja = "", tablaTDatos = "", texto = "-", textoCiclo = " ", columnas = "";
            String separacion[];
            int contador = 0, conContador = 0, a = 2, varGlobalCreateTable = 1, vGlobalScript = 1;

            file = new FileInputStream(global);

            //EXTRAYENDO VALORES DEL XLSS
            libro = new XSSFWorkbook(file);

            //REALIZANDO LAS SENTENCIAS PARA CARGAR A LA BASE DE DATOS
            comandosSinBD.add("CREATE DATABASE IF NOT EXISTS HORARIOS;");
            for (int i = 0; i < libro.getNumberOfSheets(); i++) {
                //OBTENIENDO LA HOJA A LEER
                hoja1 = libro.getSheetAt(i);
                //OBTENIENDO EL NOMBRE DE LA HOJA Y CREANDO LA SENTENCIA PARA AGREGAR NUEVA TABLA
                nombreHoja = libro.getSheetName(i);
                comandosSinBD.add("CREATE TABLE IF NOT EXISTS  " + nombreHoja + "(");
                //OBTENIENDO LAS FILAS A LEER
                recorrerFila1 = hoja1.iterator();
                //RECORRIENDO LOS VALORES DE LAS FILAS HASTA QUE SEA NULO
                while (recorrerFila1.hasNext()) {
                    //OBTENIENDO CELDAS POR FILA
                    fila1 = recorrerFila1.next();
                    cellIterator = fila1.cellIterator();
                    //RECORRIENDO VALORES POR CELDA
                    while (cellIterator.hasNext()) {
                        //OBTENIENDO CELDA EN ESPECIFICO
                        celda = cellIterator.next();
                        if (contador == 0) { //CREANDO LA TABLA CON SUS RESPECTIVAS COLUMNAS Y TIPOS DE DATOS
                            if (!cellIterator.hasNext()) { //ULTIMA VUELTA TERMINANDO DE CREAR UNA SENTENCIA DE CREACIÓN DE TABLAS
                                comandosSinBD.add(varGlobalCreateTable, comandosSinBD.get(varGlobalCreateTable) + "," + celda + ");");
                                tablaTDatos = comandosSinBD.get(varGlobalCreateTable);
                                contador++;
                                conContador = 0;
                                vGlobalScript++;
                            } else if (conContador == 0) {//PRIMERA VUELTA PARA CREAR UNA SENTENCIA DE CREACIÓN DE TABLAS
                                comandosSinBD.add(varGlobalCreateTable, comandosSinBD.get(varGlobalCreateTable) + celda);
                                conContador = 1;
                            } else {//SEGUNDA VUELTA HASTA (N - 1)
                                comandosSinBD.add(varGlobalCreateTable, comandosSinBD.get(varGlobalCreateTable) + "," + celda);
                            }
                        } else if (contador == 1) { //AGREGANDO VALORES CON INSERT
                            if (!cellIterator.hasNext()) { //ULTIMA VUELTA TERMINANDO DE CREAR UNA SENTENCIA PARA AGREGAR DATOS EXTRAIDOS DEL XLSX
                                comandosSinBD.add(a, comandosSinBD.get(a) + ", " + celda + ");");
                                vGlobalScript++;
                                conContador = 0;
                                a++;
                            } else if (conContador == 0) {//PRIMERA VUELTA CREANDO LA SENTENCIA PARA AGREGAR DATOS
                                //EXTRAYENDO LOS VALORES DE LAS COLUMNAS
                                texto = tablaTDatos.substring(tablaTDatos.indexOf("(") + 1, tablaTDatos.indexOf(";"));
                                separacion = texto.split(" ");
                                for (int r = 0; r < separacion.length; r++) {
                                    textoCiclo = separacion[r] + " ";
                                    if (r == 0) {
                                        columnas = separacion[r];
                                    } else if (textoCiclo.contains(",")) {
                                        if (!textoCiclo.contains(", ")) {
                                            columnas = columnas + textoCiclo.substring(textoCiclo.indexOf(","), textoCiclo.indexOf(" "));
                                        }
                                    }
                                }
                                //CREANDO SENTENCIA PARA INSERTAR NUEVOS DATOS EXTRAIDOS DEL XLSX
                                comandosSinBD.add(a, "INSERT INTO " + nombreHoja + "(" + columnas + ") values (" + celda);
                                conContador = 1;
                            } else {//SEGUNDA VUELTA HASTA (N - 1) PARA AGREGAR LOS VALORES DE LAS COLUMNAS EXTRAIDAS
                                comandosSinBD.add(a, comandosSinBD.get(a) + ", " + celda);
                            }
                        }
                    }
                }
                //ALMACENANDO LOS VALORES EN UN VECTOR VACIO
                for (int z = 0; z < vGlobalScript; z++) {
                    scriptTerminado.add(comandosSinBD.get(z));
                }
                a = 1;
                contador = conContador = vGlobalScript = varGlobalCreateTable = 0;
                comandosSinBD = new ArrayList<String>();
            }

            //VERIFICANDO LA CONEXIÓN A LA BASE DE DATOS
            if(gestor.equals("Sqlite")){

                System.out.println("SQLITE ENTRA");




                for (int contador2 = 1; contador2 < scriptTerminado.size(); contador2++) {
                    try(Connection conn = DriverManager.getConnection(url+NombreBaseDeDatos+".db");
                    Statement stmt = conn.createStatement()) {
                        //PREPARANDO LA SENTENCIA PARA SER EJECUTADA LA LINEA DE COMANDO Y CREAR, AGREGAR NUEVOS DATOS A LA BASE DE DATOS
                        stmt.execute(scriptTerminado.get(contador2));
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
                    }
                }



            }else if(gestor.equals("Mysql")){


                System.out.println("MYSQL ENTRA");

                conect.conexionBD(user, password, url, NombreBaseDeDatos);
                Connection reg = conect.getConection();


                for (int contador2 = 0; contador2 < scriptTerminado.size(); contador2++) {
                    try {
                        //PREPARANDO LA SENTENCIA PARA SER EJECUTADA LA LINEA DE COMANDO Y CREAR, AGREGAR NUEVOS DATOS A LA BASE DE DATOS
                        PreparedStatement b = reg.prepareStatement(scriptTerminado.get(contador2));
                        b.executeUpdate();
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
                    }
                }




            }


        }catch (SQLException e){
            System.out.println(e);
            System.out.println("Error de sintaxis ");
        }catch (IOException e) {
            System.out.println("Error dentro del archivo, Vuelve a Intentar");
        }catch (NullPointerException e){
            System.out.println("Error al seleccionar archivo, Vuelve a Intentar");
        }
    }
}
