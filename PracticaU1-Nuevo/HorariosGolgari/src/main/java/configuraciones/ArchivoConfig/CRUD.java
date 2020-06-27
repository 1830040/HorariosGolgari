package configuraciones.ArchivoConfig;

import datosTXT.archivosTXT.ArchivoTXT;
import datosXLSX.archivosXLSX.*;
import org.sqlite.SQLiteException;
import sqlConn.archivosSQL.ConectarBD;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class CRUD {

    ConectarBD conect = new ConectarBD();

    ArrayList<String> cadenasDeDatos = new ArrayList<String>();
    ArchivoXLSX arcXlsx = new ArchivoXLSX();
    ArchivoTXT arcTXT = new ArchivoTXT();
    CargarScript cargaSC = new CargarScript();

    File archivo2 = new File("src/main/resources/configuracion.txt");

    String auxiliar = "", gestor = "", usuario = "", contrasena = "", nombreBD = "", url = "", tipoArc = "", nomArc = "";

    public void Insert() {
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
        for (int i = 0; i < cadenasDeDatos.size(); i++) {
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


        if (tipoArc.equals("Excel") || tipoArc.equals("excel") || tipoArc.equals("xlsx")) {
            arcXlsx.InsertarDatos(nomArc + ".xlsx", usuario, contrasena, url, nombreBD,gestor);
        } else if (tipoArc.equals("Txt") || tipoArc.equals("txt")) {
            arcTXT.lecturaDatosTxt(nomArc + ".txt", usuario, contrasena, url, nombreBD,gestor);
        } else if (tipoArc.equals("SQL") || tipoArc.equals("sql")) {
            cargaSC.cargaBDScript(nomArc + ".sql", usuario, contrasena, url, nombreBD,gestor);
        }

    }

    public void DropTable(String NombreTabla) throws SQLException {


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
        for (int i = 0; i < cadenasDeDatos.size(); i++) {
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
        switch (gestor) {
            case "Sqlite":


                try (Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                     Statement stmt = conn.createStatement()) {
                    stmt.execute("DROP TABLE " + NombreTabla + ";");
                } catch (SQLiteException e){
                    System.out.println("Error de sintaxis");
                }
                break;


            case "Mysql":


                conect.conexionBD(usuario,contrasena,url,nombreBD);
                Connection reg = conect.getConection();
                try{
                    PreparedStatement a = reg.prepareStatement("DROP TABLE "+NombreTabla+";");
                    a.executeUpdate();
                }catch (SQLSyntaxErrorException e){
                    System.out.println("Error. No se encuentra ninguna tabla coincidente");
                }catch (SQLException e){

                    if (tipoArc.equals("Excel") || tipoArc.equals("excel") || tipoArc.equals("xlsx")) {
                        arcXlsx.InsertarDatos(nomArc + ".xlsx", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("Txt") || tipoArc.equals("txt")) {
                        arcTXT.lecturaDatosTxt(nomArc + ".txt", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("SQL") || tipoArc.equals("sql")) {
                        cargaSC.cargaBDScript(nomArc + ".sql", usuario, contrasena, url, nombreBD,"Sqlite");
                    }


                    try (Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                         Statement stmt = conn.createStatement()) {
                        stmt.execute("DROP TABLE " + NombreTabla + ";");
                    } catch (SQLSyntaxErrorException a){
                        System.out.println("Error. No se encuentra ningnua tabla coincidente");
                    }


                }
                break;

            default:

        }

    }

    public void DeleteDataCondicionado(String NombreTabla,String columnaCondicionada, String condicion, String valor) throws SQLException {
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
        for (int i = 0; i < cadenasDeDatos.size(); i++) {
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

        switch (gestor){
            case "Mysql":

                conect.conexionBD(usuario,contrasena,url,nombreBD);
                Connection reg = conect.getConection();

                try{

                    PreparedStatement a = reg.prepareStatement("DELETE FROM "+NombreTabla+" WHERE "+ columnaCondicionada +" "+ condicion+" " +valor+ ";");
                    a.executeUpdate();

                }catch (SQLSyntaxErrorException e){
                    System.out.println("Error. No se encuentra ninguna tabla coincidente");
                }catch (SQLException e){


                    if (tipoArc.equals("Excel") || tipoArc.equals("excel") || tipoArc.equals("xlsx")) {
                        arcXlsx.InsertarDatos(nomArc + ".xlsx", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("Txt") || tipoArc.equals("txt")) {
                        arcTXT.lecturaDatosTxt(nomArc + ".txt", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("SQL") || tipoArc.equals("sql")) {
                        cargaSC.cargaBDScript(nomArc + ".sql", usuario, contrasena, url, nombreBD,"Sqlite");
                    }


                    try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                        Statement stmt = conn.createStatement()){

                        stmt.executeUpdate("DELETE FROM "+NombreTabla+" WHERE "+ columnaCondicionada +" "+ condicion+" " +valor+ ";");

                    }catch (SQLiteException a){
                        System.out.println("Error de sintaxis");
                    }


                }

                break;

            case "Sqlite":

                /*try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                    Statement stmt = conn.createStatement()){

                    stmt.executeUpdate("DELETE FROM "+NombreTabla+" WHERE "+ columnaCondicionada +" "+ condicion+" " +valor+ ";");

                }catch (SQLiteException e){
                    System.out.println("Error de sintaxis");
                }*/
                break;
            default:

        }

    }

    public void DeleteDataSinCondicion(String NombreTabla){
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
            System.out.println("Error al encontrar la ruta en el archivo de configuracion");
        }
        for (int i = 0; i < cadenasDeDatos.size(); i++) {
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

        switch (gestor){
            case "Mysql":

                conect.conexionBD(usuario,contrasena,url,nombreBD);
                Connection reg = conect.getConection();
                try{
                    PreparedStatement a = reg.prepareStatement("DELETE FROM "+NombreTabla+";");
                    a.executeUpdate();
                }catch (SQLSyntaxErrorException e){
                    System.out.println("Error. No se encuentra ninguna tabla coincidente");
                } catch (SQLException e) {


                    if (tipoArc.equals("Excel") || tipoArc.equals("excel") || tipoArc.equals("xlsx")) {
                        arcXlsx.InsertarDatos(nomArc + ".xlsx", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("Txt") || tipoArc.equals("txt")) {
                        arcTXT.lecturaDatosTxt(nomArc + ".txt", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("SQL") || tipoArc.equals("sql")) {
                        cargaSC.cargaBDScript(nomArc + ".sql", usuario, contrasena, url, nombreBD,"Sqlite");
                    }

                    try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                        Statement stmt = conn.createStatement()){
                        stmt.executeUpdate("DELETE FROM "+NombreTabla+";");
                    }catch (SQLiteException a){
                        System.out.println("Error de sintaxis, tabla "+NombreTabla+" no encontrada");
                    }catch (SQLException a){
                    }

                }

                break;

            case "Sqlite":

                try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                    Statement stmt = conn.createStatement()){
                    stmt.executeUpdate("DELETE FROM "+NombreTabla+";");
                }catch (SQLiteException e){
                    System.out.println("Error de sintaxis, tabla "+NombreTabla+" no encontrada");
                }catch (SQLException e){
                }

                break;

            default:
        }



    }

    public void UpadteSinCondicion(String NombreTabla, String NombreColumna, String valor){
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
        for (int i = 0; i < cadenasDeDatos.size(); i++) {
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


        switch (gestor){
            case "Mysql":

                conect.conexionBD(usuario,contrasena,url,nombreBD);
                Connection reg = conect.getConection();
                try{

                    PreparedStatement a = reg.prepareStatement("UPDATE "+NombreTabla+" SET "+NombreColumna+" = "+valor+";");
                    a.executeUpdate();

                }catch (SQLSyntaxErrorException e){
                    System.out.println("Error. No se encuentra ninguna tabla coincidente");
                } catch (SQLException e) {


                    if (tipoArc.equals("Excel") || tipoArc.equals("excel") || tipoArc.equals("xlsx")) {
                        arcXlsx.InsertarDatos(nomArc + ".xlsx", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("Txt") || tipoArc.equals("txt")) {
                        arcTXT.lecturaDatosTxt(nomArc + ".txt", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("SQL") || tipoArc.equals("sql")) {
                        cargaSC.cargaBDScript(nomArc + ".sql", usuario, contrasena, url, nombreBD,"Sqlite");
                    }

                    try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                        Statement stmt = conn.createStatement()){

                        stmt.executeUpdate("UPDATE "+NombreTabla+" SET "+NombreColumna+" = "+valor+";");

                    }catch (SQLiteException a){

                    }catch (SQLException a){

                    }

                }

                break;

            case "Sqlite":

                try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                    Statement stmt = conn.createStatement()){

                    stmt.executeUpdate("UPDATE "+NombreTabla+" SET "+NombreColumna+" = "+valor+";");

                }catch (SQLiteException e){

                }catch (SQLException e){

                }

                break;

            default:
        }
    }

    public void UpadteCondicionado(String NombreTabla, String NombreColumna, String valor, String ColumnaCondicionada,String condicion, String valorCambiado){
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
            System.out.println("Error al encontrar la ruta en el archivo de configuracion");
        }
        for (int i = 0; i < cadenasDeDatos.size(); i++) {
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


        switch (gestor){
            case "Mysql":

                conect.conexionBD(usuario,contrasena,url,nombreBD);
                Connection reg = conect.getConection();
                try{

                    PreparedStatement a = reg.prepareStatement("UPDATE "+NombreTabla+" SET "+NombreColumna+" = "+valor+" WHERE "+ColumnaCondicionada+" "+condicion+" "+valorCambiado+";");
                    a.executeUpdate();

                }catch (SQLSyntaxErrorException e){
                    System.out.println("Error. No se encuentra ninguna tabla coincidente");
                } catch (SQLException e) {

                    if (tipoArc.equals("Excel") || tipoArc.equals("excel") || tipoArc.equals("xlsx")) {
                        arcXlsx.InsertarDatos(nomArc + ".xlsx", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("Txt") || tipoArc.equals("txt")) {
                        arcTXT.lecturaDatosTxt(nomArc + ".txt", usuario, contrasena, url, nombreBD,"Sqlite");
                    } else if (tipoArc.equals("SQL") || tipoArc.equals("sql")) {
                        cargaSC.cargaBDScript(nomArc + ".sql", usuario, contrasena, url, nombreBD,"Sqlite");
                    }


                    try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                        Statement stmt = conn.createStatement()){

                        stmt.executeUpdate("UPDATE "+NombreTabla+" SET "+NombreColumna+" = "+valor+" WHERE "+ColumnaCondicionada+" "+condicion+" "+valorCambiado+";");

                    }catch (SQLiteException a){

                    }catch (SQLException a){

                    }


                }

                break;

            case "Sqlite":


                try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                    Statement stmt = conn.createStatement()){

                    stmt.executeUpdate("UPDATE "+NombreTabla+" SET "+NombreColumna+" = "+valor+" WHERE "+ColumnaCondicionada+" "+condicion+" "+valorCambiado+";");

                }catch (SQLiteException e){

                }catch (SQLException e){

                }

                break;

            default:
        }

    }

    public void SelectSinCondicion(String NombreTabla_o_ShowTables,String columnas){

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
        for (int i = 0; i < cadenasDeDatos.size(); i++) {
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

        switch (NombreTabla_o_ShowTables){
            case "SHOW TABLES":

                switch (gestor){
                    case "Mysql":

                        conect.conexionBD(usuario,contrasena,url,nombreBD);
                        Connection reg = conect.getConection();
                        try{

                            Statement query = reg.createStatement();
                            ResultSet rs = query.executeQuery("SHOW TABLES;");
                            System.out.println("------------Tablas-------------------");
                            while (rs.next()){
                                System.out.println(rs.getString(1));
                            }

                        }catch (SQLSyntaxErrorException e){
                            System.out.println(e+": Error. No se encuentra ninguna tabla coincidente");
                        }catch (SQLException e){

                            System.out.println(e);
                        }

                        break;
                    case "Sqlite":

                        try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                            Statement stmt = conn.createStatement()){
                            ResultSet rs = stmt.executeQuery("SELECT \n" +
                                    "    name\n" +
                                    "FROM \n" +
                                    "    sqlite_master \n" +
                                    "WHERE \n" +
                                    "    type ='table' AND \n" +
                                    "    name NOT LIKE 'sqlite_%';");

                            System.out.println("------------Tablas-------------------");
                            while(rs.next()){
                                System.out.println(rs.getString(1));
                            }

                        }catch (NullPointerException e){
                            System.out.println("No se encontro ningun dato: "+e);
                        }catch (SQLException e){
                            System.out.println("Error de Sintaxis: " +e);
                        }

                        break;
                    default:
                }

                break;
            default:



                switch (gestor){
                    case "Mysql":


                        conect.conexionBD(usuario,contrasena,url,nombreBD);
                        Connection reg = conect.getConection();
                        try{

                            String[] columnasCantidad = columnas.split(",");

                            for(int contador = 0; contador < columnasCantidad.length ; contador++){
                                Statement query = reg.createStatement();
                                ResultSet rs = query.executeQuery("SELECT "+columnasCantidad[contador]+" FROM "+NombreTabla_o_ShowTables+";");
                                System.out.println(columnasCantidad[contador]+"--------------------------");
                                while (rs.next()){
                                    System.out.println(rs.getString(1));
                                }
                            }

                        }catch (SQLSyntaxErrorException e){
                            System.out.println(e+": Error. No se encuentra ninguna tabla coincidente");
                        }catch (SQLException e){

                            System.out.println(e);
                        }



                        break;
                    case "Sqlite":


                        String[] columnasCantidad = columnas.split(",");

                        try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                            Statement stmt = conn.createStatement()){

                            for(int contador = 0; contador < columnasCantidad.length ; contador++){
                                System.out.println(columnasCantidad[contador]+"--------------------------");
                                ResultSet rs = stmt.executeQuery("SELECT "+columnasCantidad[contador]+" FROM "+NombreTabla_o_ShowTables+";");
                                while(rs.next()){
                                    System.out.println(rs.getString(1));
                                }
                            }



                        }catch (NullPointerException e){
                            System.out.println("No se encontro ningun dato: "+e);
                        }catch (SQLException e){
                            System.out.println("Error de Sintaxis: " +e);
                        }



                        break;
                    default:
                }


                break;
        }






    }


    public void SelectCondicionado(String NombreTabla,String columnas,String columnaCondicionada, String condicion, String valorCondicionador){

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
        for (int i = 0; i < cadenasDeDatos.size(); i++) {
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

        switch (NombreTabla){
            case "SHOW TABLES":

                switch (gestor){
                    case "Mysql":

                        conect.conexionBD(usuario,contrasena,url,nombreBD);
                        Connection reg = conect.getConection();
                        try{

                            Statement query = reg.createStatement();
                            ResultSet rs = query.executeQuery("SHOW TABLES;");
                            System.out.println("------------Tablas-------------------");
                            while (rs.next()){
                                System.out.println(rs.getString(1));
                            }

                        }catch (SQLSyntaxErrorException e){
                            System.out.println(e+": Error. No se encuentra ninguna tabla coincidente");
                        }catch (SQLException e){

                            System.out.println(e);
                        }

                        break;
                    case "Sqlite":

                        try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                            Statement stmt = conn.createStatement()){
                            ResultSet rs = stmt.executeQuery("SELECT \n" +
                                    "    name\n" +
                                    "FROM \n" +
                                    "    sqlite_master \n" +
                                    "WHERE \n" +
                                    "    type ='table' AND \n" +
                                    "    name NOT LIKE 'sqlite_%';");

                            System.out.println("------------Tablas-------------------");
                            while(rs.next()){
                                System.out.println(rs.getString(1));
                            }

                        }catch (NullPointerException e){
                            System.out.println("No se encontro ningun dato: "+e);
                        }catch (SQLException e){
                            System.out.println("Error de Sintaxis: " +e);
                        }

                        break;
                    default:
                }

                break;
            default:



                switch (gestor){
                    case "Mysql":


                        conect.conexionBD(usuario,contrasena,url,nombreBD);
                        Connection reg = conect.getConection();
                        try{

                            String[] columnasCantidad = columnas.split(",");

                            for(int contador = 0; contador < columnasCantidad.length ; contador++){
                                Statement query = reg.createStatement();
                                ResultSet rs = query.executeQuery("SELECT "+columnasCantidad[contador]+" FROM "+NombreTabla+" WHERE "
                                        +columnaCondicionada+" "+condicion+" "+valorCondicionador+";");
                                System.out.println(columnasCantidad[contador]+"--------------------------");
                                while (rs.next()){
                                    System.out.println(rs.getString(1));
                                }
                            }

                        }catch (SQLSyntaxErrorException e){
                            System.out.println(e+": Error. No se encuentra ninguna tabla coincidente");
                        }catch (SQLException e){

                            System.out.println(e);
                        }



                        break;
                    case "Sqlite":


                        String[] columnasCantidad = columnas.split(",");

                        try(Connection conn = DriverManager.getConnection(url + nombreBD + ".db");
                            Statement stmt = conn.createStatement()){

                            for(int contador = 0; contador < columnasCantidad.length ; contador++){
                                System.out.println(columnasCantidad[contador]+"--------------------------");
                                ResultSet rs = stmt.executeQuery("SELECT "+columnasCantidad[contador]+
                                        " FROM "+NombreTabla+" WHERE "+columnaCondicionada+" "+condicion+" "+valorCondicionador+";");
                                while(rs.next()){
                                    System.out.println(rs.getString(1));
                                }
                            }



                        }catch (NullPointerException e){
                            System.out.println("No se encontro ningun dato: "+e);
                        }catch (SQLException e){
                            System.out.println("Error de Sintaxis: " +e);
                        }



                        break;
                    default:
                }


                break;
        }


    }


}
