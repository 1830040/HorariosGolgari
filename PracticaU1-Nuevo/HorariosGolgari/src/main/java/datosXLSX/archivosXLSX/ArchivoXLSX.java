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

    static ArrayList<String> comandosSinBD = new ArrayList<String>();
    static ArrayList<String> scriptTerminado = new ArrayList<String>();

    public void lecturaDatos(String nombre_archivo,String user,String password,String url,String Nombre_BaseDeDatos) throws IOException, SQLException {

        File archivo2 = new File("src/main/resources/"+nombre_archivo);
        String path = archivo2.getCanonicalPath();

        File global = new File(path);

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

        try {

            file = new FileInputStream(global);
            // Guardando y Mostrando Valores de la Tabla Libros
            libro = new XSSFWorkbook(file);

        } catch (IOException e) {
            System.out.println("Error dentro del archivo, Vuelve a Intentar");
        }catch (NullPointerException e){
            System.out.println("Error al seleccionar archivo, Vuelve a Intentar");
        }
        comandosSinBD.add("CREATE DATABASE IF NOT EXISTS HORARIOS;");
        for(int i = 0; i < libro.getNumberOfSheets(); i++) {
            // Obtiene la hoja que leerá
            hoja1 = libro.getSheetAt(i);
            //Obteniendo nombre de la hoja
            nombreHoja = libro.getSheetName(i);
            comandosSinBD.add("CREATE TABLE IF NOT EXISTS  " + nombreHoja + "(");
            // Obtener todas las filas de la hoja excel
            recorrerFila1 = hoja1.iterator();
            // Se recorre cada fila hasta el final
            while (recorrerFila1.hasNext()) {
                fila1 = recorrerFila1.next();
                // Se obtiene las celdas por fila
                cellIterator = fila1.cellIterator();
                // Se recorre cada celda
                while (cellIterator.hasNext()) {
                    // Se obtiene la celda en específico y se la imprime
                    celda = cellIterator.next();
                    if (contador == 0) { //CREANDO LA TABLA CON SUS RESPECTIVAS COLUMNAS Y TIPOS DE DATOS
                        if (!cellIterator.hasNext()) {
                            comandosSinBD.add(varGlobalCreateTable, comandosSinBD.get(varGlobalCreateTable) + "," + celda + ");");
                            tablaTDatos = comandosSinBD.get(varGlobalCreateTable);
                            contador++;
                            conContador = 0;
                            vGlobalScript++;
                        } else if (conContador == 0) {
                            comandosSinBD.add(varGlobalCreateTable, comandosSinBD.get(varGlobalCreateTable) + celda);
                            conContador = 1;
                        } else {
                            comandosSinBD.add(varGlobalCreateTable, comandosSinBD.get(varGlobalCreateTable) + "," + celda);
                        }
                    } else if (contador == 1) { //AGREGANDO VALORES CON INSERT
                        if (!cellIterator.hasNext()) {
                            comandosSinBD.add(a, comandosSinBD.get(a) + ", " + celda + ");");
                            vGlobalScript++;
                            conContador = 0;
                            a++;
                        } else if (conContador == 0) {
                            texto = tablaTDatos.substring(tablaTDatos.indexOf("(") + 1, tablaTDatos.indexOf(";"));
                            separacion = texto.split(" ");
                            for (int r = 0; r < separacion.length; r++) {
                                textoCiclo = separacion[r] + " ";

                                if (r == 0) {
                                    columnas = separacion[r];
                                } else if (textoCiclo.contains(",")) {
                                    if(!textoCiclo.contains(", ")){
                                        columnas = columnas + textoCiclo.substring(textoCiclo.indexOf(","), textoCiclo.indexOf(" "));
                                    }
                                }
                            }
                            comandosSinBD.add(a, "INSERT INTO " + nombreHoja + "(" + columnas + ") values (" + celda);
                            conContador = 1;
                        } else {
                            comandosSinBD.add(a, comandosSinBD.get(a) + ", " + celda);
                        }
                    }
                }
            }
            for(int z = 0; z < vGlobalScript; z++) {
                scriptTerminado.add(comandosSinBD.get(z));
            }
            a = 1;
            contador = conContador = vGlobalScript = varGlobalCreateTable = 0;
            comandosSinBD = new ArrayList<String>();
        }
        ConectarBD conn;
        System.out.println(user + " ----- " +password + " ------ " + url + " ------ "+ Nombre_BaseDeDatos);
        conn = new ConectarBD(user,password,url,Nombre_BaseDeDatos);
        Connection reg = conn.getConection();
        try{
            for(int contador2 = 0 ; contador2 < scriptTerminado.size() ; contador2 ++){

                try {
                    PreparedStatement b = reg.prepareStatement(scriptTerminado.get(contador2));
                    b.executeUpdate();
                    System.out.println("DATOS CARGADOS");
                }catch (SQLSyntaxErrorException e ){
                    System.out.println("Error al ingresar datos");
                    System.out.println("Usar la platilla Generada");
                    PlantillaExcel pex = new PlantillaExcel();
                    pex.EscribirEXCEL();
                }catch (SQLIntegrityConstraintViolationException d){
                    System.out.println("Error al ingresar datos");
                    System.out.println("Datos duplicados");
                }catch (NullPointerException e){
                    //System.out.println("Ruta no encontrada");
                }

            }
        }catch (SQLException e){
            System.out.println("Error de sintaxis: "+ e);
        }
    }
}
