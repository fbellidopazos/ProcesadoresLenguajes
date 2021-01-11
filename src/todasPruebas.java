import DataStructures.Token;

import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class todasPruebas {
    public static void main(String[] args) throws Exception {
        PrintStream fileOut = System.out;
        
        File directoryPath = new File("./allTests/");
        FilenameFilter textFilefilter = new FilenameFilter(){
           public boolean accept(File dir, String name) {
              String lowercaseName = name.toLowerCase();
              if (lowercaseName.endsWith(".txt")) {
                 return true;
              } else {
                 return false;
              }
           }
        };
        //List of all the text files
        String filesList[] = directoryPath.list(textFilefilter);



        for (int i = 1; i < filesList.length; i++) {
            fileOut = new PrintStream(System.out);
            String archivo = "./allTests/"+filesList[i];
            File pruebaAnalizar = new File(archivo);


            String path = "./allTests/";
            File directory = new File(path + "Prueba " + i);
            if (!directory.exists()) {

                directory.mkdir();
            }

            Files.copy(pruebaAnalizar.toPath(),(new File(path + "Prueba " + i+"/Prueba"+i+".txt")).toPath(),StandardCopyOption.REPLACE_EXISTING);




            
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
            fileOut = new PrintStream("./allTests/Prueba " + i + "/Parse" + i + ".txt");
            System.setOut(fileOut);
            String parse = aSintactico.aSintactico();

            System.out.println(parse);

            // Recolector de Tokens de ALex
            fileOut = new PrintStream("./allTests/Prueba " + i + "/tokens" + i + ".txt");
            System.setOut(fileOut);
            for (Token<String, String> token : aSintactico.tokensUsados) {
                System.out.println(token);
            }

            // Log Semantico
            fileOut = new PrintStream("./allTests/Prueba " + i + "/logSemantico" + i + ".txt");
            System.setOut(fileOut);
            for (String str : aSemantico.logSemantico) {
                System.out.println(str);
            }

            // Recolector de tabla de Simbolos
            fileOut = new PrintStream("./allTests/Prueba " + i + "/tablaSimbolos" + i + ".txt");
            System.setOut(fileOut);

            gestorTablaSimbolos.showAllTables(false);

            fileOut = new PrintStream("./allTests/Prueba " + i + "/erroresDeAnalisis" + i + ".txt");
            System.setOut(fileOut);
            for (String error : errorModule.analysisErrors) {
                System.out.println(error + "\n");
            }

        }
        fileOut.close();
    }
}
