import java.util.HashMap;
import DataStructures.Pair;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class analizadorLexico {
    /**
     * Atributos - Estructura de la gramatica regular - Archivo de lectura ->
     * BufferedReader 1 - HashMaps para traducir estado -> posicion array - Caracter
     * por donde vamos - Estructura de datos para Palabras reservadas
     */
    public Pair<String, String>[][] gramaticaRegular; // Gramatica en forma tabular
    public BufferedReader file; // Lector
    public char c;
    public moduloError errorModule;
    public HashMap<String, Integer> aplicacionEstados; // Biyeccion estado - posicion array vertical
    public HashMap<String, Integer> aplicacionCaracter; // Biyeccion char/charInt - posicion array horizontal
    public int line=0;
    /**
     * Modulo buscador accionEstado
     * Letras [charInit:charFinal] --> 65-90 [Mayus]// 97-122 [Minus]
     * Numeros [charInit:charFinal] --> 48-57
     */
    public Pair<String, String> getAccionEstado(String estado){
        /*
         * Biyeccion estado - posicion array vertical 
         * Biyeccion char/charInt - posicion array horizontal
         * Devolver resultado de gramaticaRegular[estado][char]
         */
        
        int caracter = (int) c;
        String caracter2find;
 
        if(estado.equals("A") &&  !((caracter>=65 && caracter<=90) || (caracter>=97 && caracter<=122) || (caracter>=48 && caracter<=57))){
            caracter2find="o.c"; // Estamos en A y no es letra ni digito
 
        }else if(estado.equals("E") && !(caracter>=48 && caracter<=57)){
            caracter2find="o.c"; // Estamos en E y no es un digito
 
        }else if (estado.equals("G") && c!= '='){
            caracter2find="o.c"; // Estamos en G y no es un =
 
        }else if (estado.equals("N") && c!= '='){
            caracter2find="o.c"; //Estamos en N y no es un igual
                
        }else if (estado.equals("C") && c!= '"'){
            caracter2find="c1"; //Estamos en C y no es un "
            
        }else if (estado.equals("Y") && caracter!= 42 ){
            caracter2find="c2"; //Estamos en Y y no es un *
 
        }else if (estado.equals("Z") && (caracter!= 42 && caracter!=47)){ // ES UN AND!!!!??? ------->
            caracter2find="c3"; //Estamos en Z y no es un * o /
            
 
        }else{
            if((caracter>=65 && caracter<=90) || (caracter>=97 && caracter<=122))
                caracter2find="l"; // Es una letra
                
            else if( (caracter>=48 && caracter<=57) )
                caracter2find="d"; //Es un digito

            else if(getDels(caracter))  
                caracter2find="del"; //Es un delimitador

            else if(getCarEspeciales(caracter))  
                caracter2find="ce"; //Es un delimitador
            else
                caracter2find=Character.toString(c);
        }
        
        if(((int) c) == -1 || ((int) c) ==65535){
            return new Pair<>("EOF","A32");
        }

        int fila=aplicacionEstados.get(estado);
        int columna=aplicacionCaracter.get(caracter2find);
        return gramaticaRegular[fila][columna];
    }
    
    private boolean getDels(int car){
        int[] delimitadores={32,9,10,13};

        boolean found=false;
        

        for (int i = 0; i < delimitadores.length && !found; i++) {
            found= car == delimitadores[i];
        }
        

        return found;
    }
    private boolean getCarEspeciales(int car){
        int[] carEspeciales={59,44,123,125,40,41};

        boolean found=false;
        
        for (int i = 0; i < carEspeciales.length && !found; i++) {
            found= car == carEspeciales[i];
        }
        
        return found;
    }


    /**
     * Modulo lectura: Se encarga de leer un caracter del fichero fuente Lo asgina a
     * c
     * 
     * @return
     */
    public void leer() throws IOException {
        int charInt = 0;
        charInt = file.read();

        this.c = (char) charInt;
    }

    private boolean isReservada(String lexema){
        String[] reservadas={};

        boolean found=false;
        for (int i = 0; i < reservadas.length && !found; i++) {
            found= lexema.equals(reservadas[i]);
        }
        return found;
    }

    private boolean isFinal(String estado){
        String[] finals={"V","M","B","D","F","H","NN","T","L","O","K","R","D"};

        boolean found=false;
        
        for (int i = 0; i < finals.length && !found; i++) {
            found= estado.equals(finals[i]);
        }

        return found;
    }
    /**
     * Modulo de generacion de Tokens
     */
    public void generarToken() throws Exception{
        String estado = "S";
        String accion;

        
        

        while (estado != null && !isFinal(estado)) {
            
            

            Pair<String, String> accionEstado = getAccionEstado(estado);
            System.out.println(c+" "+(int)c+" "+accionEstado);
            accion = accionEstado == null ? null : accionEstado.second; // Obtenemos la accion a ejecutar
            estado = accionEstado == null ? null : accionEstado.first; // Obtenemos el estado al que hemos llegado


            
            if (estado == null) {
                errorModule.raiseError(0);

            } else {
                switch (accion) {
                    case "A0":
                        
                        System.out.println("Accion 0");
                        break;
                    case "A1":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A2":
                        
                        System.out.println("Accion 2");
                        break;
                    case "A3":
                        
                        System.out.println("Accion 4");
                        break;
                    case "A4":
                        
                        System.out.println("Accion 5");
                        break;
                    case "A5":
                        
                        System.out.println("Accion 6");
                        break;
                    case "A6":
                        
                        System.out.println("Accion 7");
                        break;
                    case "A7":
                        
                        System.out.println("Accion 8");
                        break;
                    case "A8":
                        
                        System.out.println("Accion 9");
                        break;
                    case "A9":
                        
                        System.out.println("Accion 10");
                        break;
                    case "A10":
                        
                        System.out.println("Accion 11");
                        break;
                    case "A11":
                        
                        System.out.println("Accion 12");
                        break;
                    case "A12":
                        
                        System.out.println("Accion 13");
                        break;
                    case "A13":
                        
                        System.out.println("Accion 2");
                        break;
                    case "A14":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A15":
                        
                        System.out.println("Accion 2");
                        break;
                    case "A16":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A17":
                        
                        System.out.println("Accion 2");
                        break;
                    case "A18":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A19":
                        
                        System.out.println("Accion 2");
                        break;
                    case "A20":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A21":
                        
                        System.out.println("Accion 2");
                        break;
                    case "A22":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A23":
                        
                        System.out.println("Accion 2");
                        break;
                    case "A24":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A25":
                        
                        System.out.println("Accion 2");
                        break;
                    case "A26":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A27":
                        
                        System.out.println("Accion 27");
                        
                        break;
                    case "A28":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A29":
                        
                        System.out.println("Accion 2");
                        break;
                    case "A30":
                        
                        System.out.println("Accion 1");
                        break;
                    case "A31":
                        
                        System.out.println("Accion 2");
                        break;

                    default:
                        System.out.println("EOF");
                        System.exit(0); // No hacemos nada
                        break;

                }
                leer();
            }
        }

    }

    /**
     * Constructor para el analidador Lexico Requiere del archivo a analizar
     * 
     * @param archivo
     */
    public analizadorLexico(String archivo,moduloError errorModule) throws IOException {
        // Inicializamos archivo a leer
        File f = new File(archivo); // Creation of File Descriptor for input file
        FileReader fr = new FileReader(f); // Creation of File Reader object
        file = new BufferedReader(fr); // Creation of BufferedReader object
        this.errorModule=errorModule;

        gramaticaRegular = new Pair[12][17];
        /*
         * Gramatica Regular en formato tabular FINALES = V,M,B,D,F,H,NN,T,L,O,K,R,D
         */
        // Creacion de los Pares Estado al que llegan - Accion a ejecutar
        Pair<String, String> par = new Pair<String, String>("S", "A0"); // S->delS
        Pair<String, String> par1 = new Pair<String, String>("A", "A1"); // S->l(letra)A
        Pair<String, String> par2 = new Pair<String, String>("C", "A2"); // S->"C
        Pair<String, String> par3 = new Pair<String, String>("E", "A3"); // S->dE
        Pair<String, String> par4 = new Pair<String, String>("G", "A4"); // S->-G
        Pair<String, String> par5 = new Pair<String, String>("I", "A5"); // S->|i
        Pair<String, String> par6 = new Pair<String, String>("J", "A6"); // S->&J
        Pair<String, String> par7 = new Pair<String, String>("N", "A7"); // S->=N
        Pair<String, String> par8 = new Pair<String, String>("Q", "A8"); // S->!Q
        Pair<String, String> par9 = new Pair<String, String>("U", "A9"); // S->/U
        Pair<String, String> par10 = new Pair<String, String>("V", "A10"); // S->CARACTERES ESPECIALES : , ; ( ) { }
        Pair<String, String> par11 = new Pair<String, String>("M", "A11"); // S->+

        Pair<String, String> par12 = new Pair<String, String>("A", "A12"); // A->dA
        Pair<String, String> par13 = new Pair<String, String>("A", "A13"); // A->lA
        Pair<String, String> par14 = new Pair<String, String>("B", "A14"); // A->otroCaracter-B

        Pair<String, String> par15 = new Pair<String, String>("C", "A15"); // B->c_1C
        Pair<String, String> par16 = new Pair<String, String>("D", "A16"); // B->"D

        Pair<String, String> par17 = new Pair<String, String>("E", "A17"); // dE
        Pair<String, String> par18 = new Pair<String, String>("F", "A18"); // otroCaracter-F

        Pair<String, String> par19 = new Pair<String, String>("H", "A19"); // =
        Pair<String, String> par20 = new Pair<String, String>("NN", "A20"); // otroCaracter-NN

        Pair<String, String> par21 = new Pair<String, String>("T", "A21"); // |T

        Pair<String, String> par22 = new Pair<String, String>("L", "A22"); // &L

        Pair<String, String> par23 = new Pair<String, String>("O", "A23"); // =0
        Pair<String, String> par24 = new Pair<String, String>("K", "A24"); // otroCarcter--K

        Pair<String, String> par25 = new Pair<String, String>("R", "A25"); // =R

        Pair<String, String> par26 = new Pair<String, String>("Y", "A26"); // *Y

        Pair<String, String> par27 = new Pair<String, String>("Y", "A27"); // c_2 Y
        Pair<String, String> par28 = new Pair<String, String>("Z", "A28"); // *Z

        Pair<String, String> par29 = new Pair<String, String>("Y", "A29"); // c_3 Y
        Pair<String, String> par30 = new Pair<String, String>("Z", "A30"); // *Z
        Pair<String, String> par31 = new Pair<String, String>("S", "A31"); // /S

        // Insercion de la gramatica en la Matriz
        gramaticaRegular[0][0] = par;
        gramaticaRegular[0][1] = par1;
        gramaticaRegular[0][2] = par3;
        gramaticaRegular[0][3] = par4;
        gramaticaRegular[0][4] = par2;
        gramaticaRegular[0][5] = par5;
        gramaticaRegular[0][6] = par6;
        gramaticaRegular[0][7] = par7;
        gramaticaRegular[0][8] = par8;
        gramaticaRegular[0][9] = par9;
        gramaticaRegular[0][10] = par10;
        gramaticaRegular[0][14] = par11;

        gramaticaRegular[1][1] = par13;
        gramaticaRegular[1][2] = par12;
        gramaticaRegular[1][16] = par14;

        gramaticaRegular[2][4] = par16;
        gramaticaRegular[2][11] = par15;

        gramaticaRegular[3][2] = par17;
        gramaticaRegular[3][16] = par18;

        gramaticaRegular[4][7] = par19;
        gramaticaRegular[4][16] = par20;

        gramaticaRegular[5][5] = par21;

        gramaticaRegular[6][6] = par22;

        gramaticaRegular[7][7] = par23;
        gramaticaRegular[7][16] = par24;

        gramaticaRegular[8][7] = par25;

        gramaticaRegular[9][15] = par26;

        gramaticaRegular[10][12] = par27;
        gramaticaRegular[10][15] = par28;

        gramaticaRegular[11][9] = par31;
        gramaticaRegular[11][13] = par29;
        gramaticaRegular[11][15] = par30;

        aplicacionEstados = new HashMap<>();
        // Correlacion de estados posicion
        aplicacionEstados.put("S", 0);
        aplicacionEstados.put("A", 1);
        aplicacionEstados.put("C", 2);
        aplicacionEstados.put("E", 3);
        aplicacionEstados.put("G", 4);
        aplicacionEstados.put("I", 5);
        aplicacionEstados.put("J", 6);
        aplicacionEstados.put("N", 7);
        aplicacionEstados.put("Q", 8);
        aplicacionEstados.put("U", 9);
        aplicacionEstados.put("Y", 10);
        aplicacionEstados.put("Z", 11);

        aplicacionCaracter = new HashMap<>();
        // Correlacion de caracteres posicion
        aplicacionCaracter.put("del", 0);
        aplicacionCaracter.put("l", 1);
        aplicacionCaracter.put("d", 2);
        aplicacionCaracter.put("-", 3);
        aplicacionCaracter.put("\"", 4);
        aplicacionCaracter.put("|", 5);
        aplicacionCaracter.put("&", 6);
        aplicacionCaracter.put("=", 7);
        aplicacionCaracter.put("!", 8);
        aplicacionCaracter.put("/", 9);
        aplicacionCaracter.put("ce", 10); // caracterEspecial
        aplicacionCaracter.put("c1", 11);
        aplicacionCaracter.put("c2", 12);
        aplicacionCaracter.put("c3", 13);
        aplicacionCaracter.put("+", 14);
        aplicacionCaracter.put("*", 15);
        aplicacionCaracter.put("o.c", 16);

        // Testing Purposes
        
        for (int i = 0; i < gramaticaRegular.length; i++) {
            for (int j = 0; j < gramaticaRegular[0].length; j++) {
                if (gramaticaRegular[i][j] == null)
                    System.out.print(gramaticaRegular[i][j] + "  ");
                else
                    System.out.print(gramaticaRegular[i][j] + " ");

            }
            System.out.println();
        }
        

        leer(); // Leemos el Primer caracter del archivo
    }
}
