
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import DataStructures.Pair;
import DataStructures.Token;

public class analizadorSintactico {
    analizadorLexico aLexico;
    analizadorSemantico aSemantico;
    moduloError errorModule;
    GestorTablaSimbolos gestorTablaSimbolos;

    public Pair<String, Object>[][] tablaAccion; // Tabla accion
    public int[][] tablaGoTo; // Tabla GoTo
    public HashMap<String, Integer> aplicacionTerminal; // Aplicacion Terminal --> Entero de tabla Accion
    public HashMap<String, Integer> aplicacionNoTerminal; // Aplicacion NoTerminal --> Entero tabla GoTo
    public Stack<String> pila; // Pila A.Sinct. Ascendente
    public Pair<String, Integer>[] gramaticaDepurada; // Analisis de las cosas utiles de la gramatica
    public List<Token<String, String>> tokensUsados; // Tokens usados
    // public Pair<String, String>[][] tablaAccionErrores;

    public String aSintactico() throws Exception {

        pila.removeAllElements();
        pila.push("0");
        aSemantico.stackAtributos.removeAllElements();
        aSemantico.stackAtributos.push(null); // Metemos un hueco >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        String parse = "";
        Token<String, String> sig_token = aLexico.generarToken();
        tokensUsados.add(sig_token);
        boolean condicionSalida = true;

        if (sig_token.first == "EOF") {
            return "";
        }

        while (condicionSalida) {
            // Tomamos el siguiente Token
            String a = sig_token.first + "" + sig_token.second;
            String s = pila.peek();

            // Limpiamos el token para poder procesarlo bien, solo en los casos que se
            // necesiten
            if (sig_token != null && (sig_token.first.equals("identificador") || sig_token.first.equals("cadena")
                    || sig_token.first.equals("cteEntera"))) {
                a = sig_token.first;
            }
            if (sig_token.first == "EOF") {
                a = "$";
            }

            // Obtenemos la accion a realizar
            Pair<String, Object> accionRealizar = tablaAccion[Integer.valueOf(s)][aplicacionTerminal.get(a)];

            if (accionRealizar != null && accionRealizar.first.equals("S")) {
                // Desplazamos
                pila.push(a);

                // Meter If y cuando es identificador, entonces meter el id en los elementos
                // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                if (a.equals("identificador")) {
                    aSemantico.pushTokenId(sig_token);
                } else {
                    aSemantico.stackAtributos.push(null); // Metemos hueco
                                                          // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                }

                pila.push("" + accionRealizar.second);
                aSemantico.stackAtributos.push(null); // Metemos hueco >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                sig_token = aLexico.generarToken();
                tokensUsados.add(sig_token);
            } else if (accionRealizar != null && accionRealizar.first.equals("R")) {
                // System.err.println(this.pila);
                // System.err.println(this.aSemantico.stackAtributos);
                // Reducimos por regla X

                int k = gramaticaDepurada[(int) accionRealizar.second].second;
                String antecedente = gramaticaDepurada[(int) accionRealizar.second].first;

                aSemantico.logSemantico.add("Aplicando regla: " + (int) accionRealizar.second
                        + " con stack de Atributos --> " + aSemantico.stackAtributos);
                HashMap<String, Object> atributo = aSemantico.accionEjecutar((int) accionRealizar.second); // Evaluamos
                                                                                                           // la accion
                                                                                                           // Semantica
                                                                                                           // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                for (int i = 0; i < 2 * k; i++) {
                    pila.pop();
                    aSemantico.stackAtributos.pop(); // Quitamos cosas de la Pila Semantica
                                                     // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                }

                Integer sj = Integer.valueOf(pila.peek());
                pila.push(antecedente);
                aSemantico.stackAtributos.push(atributo); // Metemos el valor Calculado
                                                          // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                

                Integer sk = Integer.valueOf(tablaGoTo[sj][aplicacionNoTerminal.get(antecedente)]);
                pila.push(Integer.toString(sk));
                aSemantico.stackAtributos.push(null); // Metemos un hueco en pila
                                                      // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                aSemantico.logSemantico.add("\tSe obtiene stack de Atributos --> " + aSemantico.stackAtributos + "\n");
                // Almacenamos el PARSE
                parse = parse.length() == 0 ? "Ascendente " + ((int) accionRealizar.second + 1)
                        : parse + " " + ((int) accionRealizar.second + 1);

            } else if (accionRealizar != null && accionRealizar.first.equals("ACC")) {
                // Aceptamos
                condicionSalida = false; // Salimos bucle


                aSemantico.logSemantico.add("Aplicando regla: " + 0
                        + " con stack de Atributos --> " + aSemantico.stackAtributos);
                aSemantico.accionEjecutar(0);
                aSemantico.logSemantico.add("\tSe obtiene stack de Atributos --> " + aSemantico.stackAtributos + "\n");


                parse = parse + " " + 1; // Necesario si tomamos la gramatica aumentada como gramatica

            } else {
                // Error sintactico o interno
                errorModule.raiseError(5, aLexico.line,
                        "\t@Usuario: " + (String) tablaAccion[Integer.valueOf(s)][aplicacionTerminal.get(a)].second
                                + "\n\t@Internal: Error en el token: " + sig_token);

                return parse;
            }

        }

        return parse;
    }

    public analizadorSintactico(analizadorLexico aLexico, analizadorSemantico aSemantico, moduloError errorModule,
            GestorTablaSimbolos gestorTablaSimbolos) throws Exception {
        this.aLexico = aLexico;
        this.aSemantico = aSemantico;
        this.errorModule = errorModule;
        this.gestorTablaSimbolos = gestorTablaSimbolos;

        this.tablaAccion = definitions.tablaAccion();
        this.tablaGoTo = definitions.tablaGoTo();
        this.aplicacionTerminal = definitions.accionHashMap();
        this.aplicacionNoTerminal = definitions.GoToHashMap();
        this.gramaticaDepurada = definitions.gramaticaArray();

        this.pila = new Stack<>();
        pila.push("0");

        this.tokensUsados = new ArrayList<>();

    }

}
