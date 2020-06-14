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
        String nombreArchivo = "PlantillaExcel.xlsx";

        String NombreHoja = "(NombreDeLaTabla)";

        XSSFWorkbook libro = new XSSFWorkbook();
        XSSFSheet hoja1 = libro.createSheet(NombreHoja);

        // Cabecera de la hoja de excel
        String[] Columnas = new String[]{"(COLUMNA1) (TIPO DE DATO INT) (RESTRICCIONES)\n", "(COLUMNA2) (TIPO DE DATO VARCHAR/CHAR (RANGO)) (RESTRICCIONES)\n"};

        // Contenido de la hoja de excel
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


        NombreHoja = "Carreras";

        XSSFSheet hoja2 = libro.createSheet(NombreHoja);


        String[] Columnas2 = new String[]{" clv_carrera char (3) Not Null Primary Key\n", "Nombre varchar(60) Not Null\n"};

        String[][] Contenido2 = new String[][]{
                {"ITI\n ", "Ingenieria en Tecnologias de la Información\n"},
                {"ITM\n", "Ingenieria en Tecnologias en Mecatronica\n"},
                {"LAE\n", "Licenciatura en Administración y Gestión Empresarial\n"},
                {"ISA\n ", "Ingenieria en Sistemas Automotrices\n"},
        };
        EscribirCiclo(hoja2, Columnas2, Contenido2);


        NombreHoja = "Alumnos";
        XSSFSheet hoja3 = libro.createSheet(NombreHoja);
        // Cabecera de la hoja de excel
        String[] Columnas3 = new String[]{"matricula char(7) Not Null\n", "Nombre Varchar(30)\n", "ApellidoPaterno varchar (30) Not Null\n", "ApellidoMaterno varchar (30)\n", "Turno BOOLEAN\n", "CuatrimestreActual TINYINT Not Null\n", "clv_carrera char (3)\n"};

        // Contenido de la hoja de excel
        String[][] Contenido3 = new String[][]{
                {"'1830040'\n ", "'Jose'\n", "'Perez'\n", "'Perez'\n", "TRUE\n", "4\n", "'ITI'\n"},
                {"'1830387'\n", "'Daniela'\n", "'Vigueras'\n", "'Gatica'\n", "FALSE\n", "5\n", "'ITM'\n"},
                {"'1830039'\n", "'Alejandro'\n", "'Martinez'\n", "'Lopez'\n", "TRUE\n", "6\n", "'LAE'\n"}
        };
        EscribirCiclo(hoja3, Columnas3, Contenido3);


        // Crear el archivo
        try (OutputStream fileOut = new FileOutputStream(nombreArchivo)) {

            System.out.println("SE CREO EL EXCEL");
            libro.write(fileOut);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
    private static void EscribirCiclo(XSSFSheet hoja1,String[] Columnas, String[][] Contenido ) {
        // Generar los datos para el documento

        for (int i = 0; i <= Contenido.length; i++) {

            XSSFRow row = hoja1.createRow(i); // Se crea la fila

            for (int j = 0; j < Columnas.length; j++) {

                if (i == 0) { // Para el nombre de las columnas

                    XSSFCell cell = row.createCell(j); // Se crean las celdas pra las columas
                    cell.setCellValue(Columnas[j]); // Se añade el contenido

                } else {

                    XSSFCell cell = row.createCell(j); // Se crean las celdas para el contenido
                    cell.setCellValue(Contenido[i - 1][j]); // Se añade el contenido

                }

            }

        }
    }
}
