import java.io.PrintStream;
import java.util.HashMap;

import DataStructures.Pair;

public class analizadorSintactico {
    analizadorLexico aLexico;
    moduloError errorModule;
    GestorTablaSimbolos gestorTablaSimbolos;
    public Pair<String, Integer>[][] tablaAccion;
    public int[][] tablaGoTo;
    public HashMap<String, Integer> aplicacionTerminal;
    public HashMap<String, Integer> aplicacionNoTerminal;

    public analizadorSintactico(analizadorLexico aLexico, moduloError errorModule,
            GestorTablaSimbolos gestorTablaSimbolos) throws Exception {
        this.aLexico = aLexico;
        this.errorModule = errorModule;
        this.gestorTablaSimbolos = gestorTablaSimbolos;

        this.tablaAccion = definitions.tablaAccion();
        this.tablaGoTo = definitions.tablaGoTo();
        this.aplicacionTerminal = definitions.accionHashMap();
        this.aplicacionNoTerminal = definitions.GoToHashMap();

        // To Txt Tabla Accion --> Comprobaciones
        PrintStream fileOut = new PrintStream("./outputs/SLR.txt");
        System.setOut(fileOut);

        for (int i = 0; i < tablaAccion.length; i++) {
            for (int j = 0; j < tablaAccion[0].length; j++) {
                if (tablaAccion[i][j] == null)
                    System.out.print(tablaAccion[i][j] + "        ");
                else {
                    Pair<String, Integer> printer = tablaAccion[i][j];
                    if (printer.toString().length() == 6) {
                        System.out.print(printer + "      ");
                    } else if (printer.toString().length() == 7) {
                        System.out.print(printer + "     ");
                    } else if (printer.toString().length() == 8) {
                        System.out.print(printer + "    ");
                    } else {
                        System.out.print(printer + " ");
                    }

                }

            }
            System.out.println();
        }


    }
}
