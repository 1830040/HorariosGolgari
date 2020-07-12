package datosXLSX.archivosXLSX;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PlantillaExcel {

    public  void EscribirEXCEL() {
        //CREANDO VARIABLES PARA EL NOMBRE DEL ARCHIVO Y DE LA HOJA
        String nombreArchivo = "PlantillaExcel.xlsx";
        String NombreHoja = "(NombreDeLaTabla)";

        //DECLARANDO VARIABLES PARA LA CREACIÓN DEL XLSX
        XSSFWorkbook libro = new XSSFWorkbook();
        XSSFSheet hoja1 = libro.createSheet(NombreHoja);

        //CREANDO CABECERA DE LA HOJA
        String[] Columnas = new String[]{"(COLUMNA1) (TIPO DE DATO INT) (RESTRICCIONES)\n", "(COLUMNA2) (TIPO DE DATO VARCHAR/CHAR (RANGO)) (RESTRICCIONES)\n"};

        //ALMACENANDO EL CONTENIDO QUE SE AGREGARA A LA HOJA
        String[][] Contenido = new String[][]{
                {"'(NUMERO)\n ", "'(VALOR)'\n"},
                {"'(NUMERO)\n", "'(VALOR)'\n"},
                {"'(NUMERO)\n ", "'(VALOR)'\n"},
                {"'(NUMERO)\n ", "'(VALOR)'\n"},
                {"", ""},
                {"", ""},
                {"NOTAS:", ""},
                {"Las tablas que hacen referencia a otra tabla, primero se crea la tabla que será referenciada\n", "Las tablas deben comenzar desde la hoja 1\n"},
                {"Tipo de dato:\n", "Instrucciones:\n"},
                {"Int/Tinyint \n", "Se almacena poniendo un ' antes del numero\n"},
                {"Varchar/char\n", "La palabra se almacena entre 'dos' comillas simples\n"},
                {"Boolean\n", "TRUE / FALSE\n"},
                {"Ejemplo:\n", "En la siguiente Hoja se muestra un ejemplo de como debe ser creado el Excel\n"},
        };
        EscribirCiclo(hoja1, Columnas, Contenido);

        //CREANDO UNA NUEVA HOJA
        NombreHoja = "Carreras";
        XSSFSheet hoja2 = libro.createSheet(NombreHoja);

        //ASIGNANDO CABECERAS A LA HOJA
        String[] Columnas2 = new String[]{" clv_carrera char (3) Not Null Primary Key\n", "Nombre varchar(60) Not Null\n"};

        //ALMACENANDO EL CONTENIDO QUE SE AGREGARA A LA HOJA
        String[][] Contenido2 = new String[][]{
                {"ITI\n ", "Ingenieria en Tecnologias de la Información\n"},
                {"ITM\n", "Ingenieria en Tecnologias en Mecatronica\n"},
                {"LAE\n", "Licenciatura en Administración y Gestión Empresarial\n"},
                {"ISA\n ", "Ingenieria en Sistemas Automotrices\n"},
        };
        EscribirCiclo(hoja2, Columnas2, Contenido2);

        //CREANDO UNA NUEVA HOJA
        NombreHoja = "Alumnos";
        XSSFSheet hoja3 = libro.createSheet(NombreHoja);

        //ASIGNANDO CABECERAS A LA HOJA
        String[] Columnas3 = new String[]{"matricula char(7) Not Null\n", "Nombre Varchar(30)\n", "ApellidoPaterno varchar (30) Not Null\n", "ApellidoMaterno varchar (30)\n", "Turno BOOLEAN\n", "CuatrimestreActual TINYINT Not Null\n", "clv_carrera char (3)\n"};

        //ALMACENANDO EL CONTENIDO QUE SE AGREGARA A LA HOJA
        String[][] Contenido3 = new String[][]{
                {"'1830040'\n ", "'Jose'\n", "'Perez'\n", "'Perez'\n", "TRUE\n", "4\n", "'ITI'\n"},
                {"'1830387'\n", "'Daniela'\n", "'Vigueras'\n", "'Gatica'\n", "FALSE\n", "5\n", "'ITM'\n"},
                {"'1830039'\n", "'Alejandro'\n", "'Martinez'\n", "'Lopez'\n", "TRUE\n", "6\n", "'LAE'\n"}
        };
        EscribirCiclo(hoja3, Columnas3, Contenido3);

        //CREACIÓN DEL ARCHIVO
        try (OutputStream fileOut = new FileOutputStream(nombreArchivo)) {
            System.out.println("PLANTILLA CREADA");
            libro.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void EscribirCiclo(XSSFSheet hoja1,String[] Columnas, String[][] Contenido ) {
        // GENERANDO LOS DATOS PARA EL DOCUMENTO
        for (int i = 0; i <= Contenido.length; i++) {
            //CREACIÓN DE UNA FILA
            XSSFRow row = hoja1.createRow(i);

            for (int j = 0; j < Columnas.length; j++) {
                //ASIGNANDO NOMBRE DE LAS COLUMNAS
                if (i == 0) {
                    XSSFCell cell = row.createCell(j); //CREACIÓN DE CELDAS PARA LAS COLUMNAS
                    cell.setCellValue(Columnas[j]); //ASIGNANDO CONTENIDO
                } else {
                    XSSFCell cell = row.createCell(j); //CREACIÓN DE CELDAS PARA LAS COLUMNAS
                    cell.setCellValue(Contenido[i - 1][j]); //ASIGNANDO CONTENIDO
                }
            }
        }
    }
}
