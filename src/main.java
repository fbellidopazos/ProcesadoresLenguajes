import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import DataStructures.Token;
import java.io.PrintStream;

public class main {

    public static void main(String[] args) throws IOException {

        // Seleccionador de archivos
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
        File workingDirectory = new File(System.getProperty("user.dir")); // El directorio principal
        jfc.setCurrentDirectory(workingDirectory);
        int returnValue = jfc.showOpenDialog(null);

        moduloError errorModule = new moduloError();

        if (returnValue == JFileChooser.APPROVE_OPTION) {

            File directory = new File("./outputs");
            if (!directory.exists()) {

                directory.mkdir();
            }

            File selectedFile = jfc.getSelectedFile();
            String archivo = selectedFile.getCanonicalPath();

            System.out.println("Analizando el archivo: ");
            System.out.println(archivo);

            String value = "GLOBAL";

            GestorTablaSimbolos gestorTablaSimbolos = new GestorTablaSimbolos("TS" + value, errorModule);
            analizadorLexico aLexico = new analizadorLexico(archivo, errorModule, gestorTablaSimbolos);
            analizadorSemantico aSemantico = new analizadorSemantico(aLexico, errorModule, gestorTablaSimbolos);
            analizadorSintactico aSintactico = new analizadorSintactico(aLexico, aSemantico, errorModule,
                    gestorTablaSimbolos);

            try {
                getLogs(gestorTablaSimbolos, aLexico, aSintactico, aSemantico, errorModule);
            } catch (Exception e) {
                PrintStream fileOut = new PrintStream("./outputs/erroresDeAnalisis.txt");
                System.setOut(fileOut);
                for (String error : errorModule.analysisErrors) {
                    System.out.println(error);
                }
                System.out.println(e.getMessage());
                e.printStackTrace(fileOut);


                fileOut.close();
            }

        }

        else {
            File directory = new File("./outputs");
            if (!directory.exists()) {

                directory.mkdir();
            }
            PrintStream fileOut = new PrintStream("./outputs/erroresDeAnalisis.txt");
            System.setOut(fileOut);
            System.out.println("No hay archivo para analizar");

            // No hay archivo a analizador
        }

    }

    public static void getLogs(GestorTablaSimbolos gestorTablaSimbolos, analizadorLexico aLexico,
            analizadorSintactico aSintactico, analizadorSemantico aSemantico, moduloError errorModule)
            throws IOException {
        // Recolector de Parse
        PrintStream fileOut = new PrintStream("./outputs/Parse.txt");
        System.setOut(fileOut);
        String parse = aSintactico.aSintactico();

        System.out.println(parse);

        // Recolector de Tokens de ALex
        fileOut = new PrintStream("./outputs/tokens.txt");
        System.setOut(fileOut);
        for (Token<String, String> token : aSintactico.tokensUsados) {
            System.out.println(token);
        }

        // Log Semantico
        fileOut = new PrintStream("./outputs/logSemantico.txt");
        System.setOut(fileOut);
        for (String str : aSemantico.logSemantico) {
            System.out.println(str);
        }

        // Recolector de tabla de Simbolos
        fileOut = new PrintStream("./outputs/tablaSimbolos.txt");
        System.setOut(fileOut);

        gestorTablaSimbolos.showAllTables(true);
        fileOut = new PrintStream("./outputs/erroresDeAnalisis.txt");
        System.setOut(fileOut);
        for (String error : errorModule.analysisErrors) {
            System.out.println(error);
        }
        fileOut.close();
    }

}
