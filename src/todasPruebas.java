import DataStructures.Token;

import java.io.File;
import java.io.PrintStream;

public class todasPruebas {
    public static void main(String[] args) throws Exception {
        PrintStream fileOut = System.out;

        for (int i = 1; i < 13; i++) {
            fileOut = new PrintStream(System.out);
            String archivo = "D:/OneDrive - Universidad Politécnica de Madrid/Universidad/3º/Procesadores de Lenguajes/Practica/ProcesadoresLenguajes/Pruebas/Todas Pruebas/Prueba "
                    + i + ".txt";

            String path = "D:/OneDrive - Universidad Politécnica de Madrid/Universidad/3º/Procesadores de Lenguajes/Practica/ProcesadoresLenguajes/outputs/All/";
            File directory = new File(path + "Prueba " + i);
            if (!directory.exists()) {

                directory.mkdir();
            }

            System.err.println("Analizando el archivo: ");
            System.err.println("\t" + archivo);

            String value = "MAIN";
            moduloError errorModule = new moduloError();
            GestorTablaSimbolos gestorTablaSimbolos = new GestorTablaSimbolos("TS" + value, errorModule);
            analizadorLexico aLexico = new analizadorLexico(archivo, errorModule, gestorTablaSimbolos);
            analizadorSemantico aSemantico = new analizadorSemantico(aLexico, errorModule, gestorTablaSimbolos);
            analizadorSintactico aSintactico = new analizadorSintactico(aLexico, aSemantico, errorModule,
                    gestorTablaSimbolos);

            // Recolector de Parse
            fileOut = new PrintStream("./outputs/All/Prueba " + i + "/Parse" + i + ".txt");
            System.setOut(fileOut);
            String parse = aSintactico.aSintactico();

            System.out.println(parse);

            // Recolector de Tokens de ALex
            fileOut = new PrintStream("./outputs/All/Prueba " + i + "/tokens" + i + ".txt");
            System.setOut(fileOut);
            for (Token<String, String> token : aSintactico.tokensUsados) {
                System.out.println(token);
            }

            // Log Semantico
            fileOut = new PrintStream("./outputs/All/Prueba " + i + "/logSemantico" + i + ".txt");
            System.setOut(fileOut);
            for (String str : aSemantico.logSemantico) {
                System.out.println(str);
            }

            // Recolector de tabla de Simbolos
            fileOut = new PrintStream("./outputs/All/Prueba " + i + "/tablaSimbolos" + i + ".txt");
            System.setOut(fileOut);

            gestorTablaSimbolos.showAllTables(false);

            fileOut = new PrintStream("./outputs/All/Prueba " + i + "/erroresDeAnalisis" + i + ".txt");
            System.setOut(fileOut);
            for (String error : errorModule.analysisErrors) {
                System.out.println(error + "\n");
            }

        }
        fileOut.close();
    }
}
