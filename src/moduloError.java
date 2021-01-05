import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class moduloError {
    HashMap<Integer, String> errorCodes;
    List<String> analysisErrors; // Almacen de Errores, para luego mostrarlos por pantalla

    public void raiseError(int errorCode) {
        String error = errorCodes.get(errorCode);
        System.err.println(error);

        analysisErrors.add(error);
    }

    public void raiseError(int errorCode, int line) {
        String error = errorCodes.get(errorCode);
        System.err.println(error + " en la linea:" + line);

        analysisErrors.add(error + " en la linea:" + line);
    }

    public void raiseError(int errorCode, int line, String msg) {
        String error = errorCodes.get(errorCode);
        System.err.println(error + " en la linea:" + line);
        System.err.println(msg);

        analysisErrors.add(error + " en la linea:" + line + "\n" + msg);

    }

    public void raiseError(int errorCode, String msg) {
        String error = errorCodes.get(errorCode);

        System.err.println(error + "\n" + msg);

        analysisErrors.add(error + "\n" + msg);

    }

    public moduloError() {
        errorCodes = new HashMap<>();
        analysisErrors = new ArrayList<>();

        errorCodes.put(0, "Error Lexico:\n Caracter No valido");
        errorCodes.put(1, "Error Lexico:\n Ya esta en la tabla de simbolos");
        errorCodes.put(-1, "Codigo de error -1: Algo no fue como esperabamos\n Nuestra Culpa =D");
        errorCodes.put(2, "Error Lexico-Semantico:\nEntero fuera de rango");
        errorCodes.put(3, "Error Semantico: ");

        errorCodes.put(4, "Error Lexico-Semantico:\nNombre de variable fuera de rango");

        errorCodes.put(5, "Error Sintactico");
    }
}
