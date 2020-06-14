package datosTXT.archivosTXT;

import java.io.*;
import java.util.ArrayList;

public class PlantillaTxt {

    public  void EscribirTxt() {
        String nombreArchivo = "PlantillaTXT.txt";

        ArrayList Contenido = new ArrayList();
        Contenido.add("A continuación se muestra la plantilla esperada para cargar datos de un TXT:");
        Contenido.add("");
        Contenido.add("TABLA {(Nombre de la Tabla)} #");
        Contenido.add("COLUMNAS {(NOMBRE_COLUMNA1) (TIPO DE DATO INT/TINYINT) (RESTRICCIONES), (NOMBRE_COLUMNA2) (TIPO DE DATO CHAR/VARCHAR) (RESTRICCIONES)} #");
        Contenido.add("NOMBRE_COLUMNA1 {(NUMERO - FILA 1 COLUMNA1) / (NUMERO 2 - FILA 2 COLUMNA 1) } #");
        Contenido.add("NOMBRE_COLUMNA2 {'(VALOR - FILA 1 COLUMNA2)' / '(VALOR 2 - FILA 2 COLUMNA 2)'} #");
        Contenido.add("!");
        Contenido.add("");
        Contenido.add("Notas:");
        Contenido.add(">No usar caracteres especiales dentro de los datos, ni usar acentos");
        Contenido.add(">Los valores deben ser asignados entre llaves  { }");
        Contenido.add(">Al terminar la linea se debe poner un #");
        Contenido.add(">Para separar los datos de una misma columna que se acomodaran en otras filas se separan con /");
        Contenido.add(">Cuando termine de ingresar los datos por tabla, se debe poner el signo ! para determinar el fin de una tabla y el posible comienzo de otra");
        Contenido.add(">Los valores de tipo varchar/char deben ser guardados con 'comillas simples'");
        Contenido.add(">Los valores numericos solamente son escritos, ejemplo:  Columna {1/2/5/5} #");
        Contenido.add(">Los valores BOOLEANOS se almacenan con TRUE / FALSE");
        Contenido.add(">El acomodo es <<Columna {Fila 1 / Fila 2 / Fila 3} #>> todos los valores de las filas pertenencen a la columna donde estan asignados\n");
        Contenido.add("");
        Contenido.add("VISUALIZACION DE LAS TABLAS:");
        Contenido.add("TABLA: Carreras");
        Contenido.add("|  clv_carrera  |   Nombre                                                 |");
        Contenido.add("|     ITI       |   Ingenieria en Tecnologias de la Informacion            |");
        Contenido.add("|     ITM       |   Ingenieria en Tenologias en Manufactura                |");
        Contenido.add("|     LAE       |   Licenciatura en Adiministracion y Gestion empresarial  |");
        Contenido.add("|     ISA       |   Ingenieria en Sistemas Automotrices                    |");
        Contenido.add("");
        Contenido.add("TABLA: Alumnos");
        Contenido.add("|  matricula    |   Nombre    |   ApellidoPaterno    |   ApellidoMaterno   |   Turno   |   CuatrimestreActual   |  clv_carrera   |");
        Contenido.add("|   1830040     |    Jose     |        Perez         |      Perez          |   TRUE    |           4            |     ITI        |");
        Contenido.add("|   1830387     |   Daniela   |       Vigueras       |      Gatica         |   FALSE   |           5            |     ITM        |");
        Contenido.add("|   1830039     |  Alejandro  |       Martinez       |      Lopez          |   TRUE    |           6            |     LAE        |");
        Contenido.add("");
        Contenido.add("EJEMPLO DE VISUALIZACION EN FORMATO TXT:");
        Contenido.add("");
        Contenido.add("TABLA {Carreras} #");
        Contenido.add("COLUMNAS {clv_carrera char (3) Not Null Primary Key, Nombre varchar(60) Not Null} #");
        Contenido.add("CLV_CARRERA {ITI/ITM/LAE/ISA} #");
        Contenido.add("NOMBRE {Ingenieria en Tecnologias de la Informacion/Ingenieria en Tecnologias en Mecatronica/Licenciatura en Administración y Gestión Empresarial/Ingenieria en Sistemas Automotrices} #");
        Contenido.add("!");
        Contenido.add("TABLA {Alumnos} #");
        Contenido.add("COLUMNAS {matricula char(7) Not Null, Nombre Varchar(30), ApellidoPaterno varchar (30) Not Null, ApellidoMaterno varchar (30),Turno BOOLEAN, CuatrimestreActual TINYINT Not Null, clv_carrera char (3)} #");
        Contenido.add("MATRICULA {'1830040'/'1830387'/'1830039'} #");
        Contenido.add("NOMBRE {Perez/Fuente/Garcia} #");
        Contenido.add("APELLIDOPATERNO {'Jose'/'Daniela'/'Alejandro'} #");
        Contenido.add("APELLIDO MATERNO {'Perez'/'Gatica'/'Lopez'} #");
        Contenido.add("TURNO {TRUE/FALSE/TRUE} #");
        Contenido.add("CUATRIMESTRE {4/5/6} #");
        Contenido.add("CLV_CARRERA {'ITI'/'ITM'/'LAE'} #");
        Contenido.add("!");
        try {
            FileWriter fichero = new FileWriter(nombreArchivo);
            String auxiliar;
            for (int x=0;x<Contenido.size();x++){
                auxiliar = (String) Contenido.get(x);
                fichero.write(auxiliar+"\n");
            }
            fichero.close();
        }catch (IOException e){
            System.out.println("Error al crear el archivo");
        }
    }
}
