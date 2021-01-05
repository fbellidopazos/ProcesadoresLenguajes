import java.io.PrintStream;

import DataStructures.Token;

public class test {
    public static void main(String[] args) throws Exception {
       
        String value = "GLOBAL";
        

        moduloError errorModule=new moduloError();
        GestorTablaSimbolos gestorTablaSimbolos = new GestorTablaSimbolos("TS" + value, errorModule);
        analizadorLexico aLexico = new analizadorLexico("test.txt", errorModule, gestorTablaSimbolos);
        analizadorSemantico aSemantico = new analizadorSemantico(aLexico, errorModule, gestorTablaSimbolos);
        analizadorSintactico aSintactico = new analizadorSintactico(aLexico, aSemantico, errorModule,
                gestorTablaSimbolos);

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
