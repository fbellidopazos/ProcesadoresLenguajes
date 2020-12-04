import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import DataStructures.Pair;
import DataStructures.Token;

public class analizadorSintactico {
    analizadorLexico aLexico;
    moduloError errorModule;
    GestorTablaSimbolos gestorTablaSimbolos;

    public Pair<String, Integer>[][] tablaAccion; // Tabla accion
    public int[][] tablaGoTo; // Tabla GoTo
    public HashMap<String, Integer> aplicacionTerminal; // Aplicacion Terminal --> Entero de tabla Accion
    public HashMap<String, Integer> aplicacionNoTerminal; // Aplicacion NoTerminal --> Entero tabla GoTo
    public Stack<String> pila; // Pila A.Sinct. Ascendente
    public Pair<String, Integer>[] gramaticaDepurada; // Analisis de las cosas utiles de la gramatica
    public List<Token<String,String>> tokensUsados; // Tokens usados

    public String aSintactico() throws Exception {

        pila.removeAllElements();
        pila.push("0");

        String parse = "";
        Token<String, String> sig_token = aLexico.generarToken();
        tokensUsados.add(sig_token);
        boolean condicionSalida = true;

        if (sig_token.first == "EOF") {
            return "";
        }

        while (condicionSalida) {
            //System.out.println(sig_token);
            /*
             * if(sig_token.first=="EOF"){ return "EOF"; }
             */

            String a = sig_token.first + "" + sig_token.second;
            String s = pila.peek();
            // System.out.println(a);
            // System.out.println(s);

            // ------------------------------------------------------------------------------------------------------------------------------------->> EXPLICAR GRUPO
            if (sig_token != null && (sig_token.first.equals("identificador") || sig_token.first.equals("cadena")
                    || sig_token.first.equals("cteEntera"))) {
                a = sig_token.first;
            }
            if (sig_token.first == "EOF") {
                a = "$";
            }
            // ------------------------------------------------------------------------------------------------------------------------------------->> EXPLICAR GRUPO

            
            // System.out.println(pila.toString());
            Pair<String, Integer> accionRealizar = tablaAccion[Integer.valueOf(s)][aplicacionTerminal.get(a)];

            // System.out.println(accionRealizar);

            if (accionRealizar != null && accionRealizar.first.equals("S")) {
                pila.push(a);
                pila.push("" + accionRealizar.second);
                sig_token = aLexico.generarToken();
                tokensUsados.add(sig_token);
            } else if (accionRealizar != null && accionRealizar.first.equals("R")) {

                int k = gramaticaDepurada[accionRealizar.second].second;
                String antecedente = gramaticaDepurada[accionRealizar.second].first;

                // System.out.print(" --> "+k+" "+antecedente+"\n");
                for (int i = 0; i < 2 * k; i++) { // ------------------------------------------------------------------------------------------------------------------------------------->> EXPLICAR GRUPO (LAMBDA == 0)
                    pila.pop();
                }
                Integer sj = Integer.valueOf(pila.peek());
                pila.push(antecedente);
                Integer sk = Integer.valueOf(tablaGoTo[sj][aplicacionNoTerminal.get(antecedente)]);
                pila.push(Integer.toString(sk));


                // ------------------------------------------------------------------------------------------------------------------------------------->> EXPLICAR GRUPO
                parse = parse.length()==0?"Ascendente "+(accionRealizar.second + 1):parse + " " + (accionRealizar.second + 1);
                // ------------------------------------------------------------------------------------------------------------------------------------->> EXPLICAR GRUPO



                // System.out.print(parse);

            } else if (accionRealizar != null && accionRealizar.first.equals("ACC")) {
                condicionSalida = false;
                // ------------------------------------------------------------------------------------------------------------------------------------->> EXPLICAR GRUPO
                parse = parse + " " + 1;
                // ------------------------------------------------------------------------------------------------------------------------------------->> EXPLICAR GRUPO

            } else {
                errorModule.raiseError(5, aLexico.line);
                System.out.println("Error en el token: "+sig_token);
                return "";
            }

        }

        return parse;
    }

    public analizadorSintactico(analizadorLexico aLexico, moduloError errorModule,
            GestorTablaSimbolos gestorTablaSimbolos) throws Exception {
        this.aLexico = aLexico;
        this.errorModule = errorModule;
        this.gestorTablaSimbolos = gestorTablaSimbolos;

        this.tablaAccion = definitions.tablaAccion();
        this.tablaGoTo = definitions.tablaGoTo();
        this.aplicacionTerminal = definitions.accionHashMap();
        this.aplicacionNoTerminal = definitions.GoToHashMap();

        this.gramaticaDepurada = definitions.gramaticaArray();

        this.pila = new Stack<>();
        pila.push("0");

        this.tokensUsados=new ArrayList<>();

        // To Txt Tabla Accion --> Comprobaciones
        
          PrintStream fileOut = new PrintStream("./outputs/SLR.txt");
          System.setOut(fileOut);
          
          for (int i = 0; i < tablaAccion.length; i++) { for (int j = 0; j <
          tablaAccion[0].length; j++) { if (tablaAccion[i][j] == null)
          System.out.print(tablaAccion[i][j] + "        "); else { Pair<String,
          Integer> printer = tablaAccion[i][j]; if (printer.toString().length() == 6) {
          System.out.print(printer + "      "); } else if (printer.toString().length()
          == 7) { System.out.print(printer + "     "); } else if
          (printer.toString().length() == 8) { System.out.print(printer + "    "); }
          else { System.out.print(printer + " "); }
          
          }
          
          } System.out.println(); }
         fileOut.close();

    }
}
