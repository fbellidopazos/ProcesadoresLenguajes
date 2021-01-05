import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataStructures.Table;
import DataStructures.types;

public class TablaSimbolos {
    List<String> cabecera;
    HashMap<Integer, List<Object>> tabla;
    int posTS;
    int longitud;
    String name;
    int desplazamientoInterno = 0;

    public TablaSimbolos(String name) {
        this.name = name;
        cabecera = new ArrayList<String>();
        longitud = 6;
        String[] cabeza = { "LEXEMA", "Tipo", "Desplazamiento", "NºParametros", "ReturnType", "Etiqueta" };
        for (int i = 0; i < cabeza.length; i++) {
            cabecera.add(cabeza[i]);
        }
        tabla = new HashMap<>();
    }

    public int newInsert(String lexema) {

        List<Object> entrada = new ArrayList<>(6);

        // entrada.add(0, posTS); // Posicion en Tabla
        entrada.add(lexema);
        for (int i = 0; i < 5; i++) {
            entrada.add(null);
        }

        tabla.put(posTS, entrada);

        int toReturn = posTS;
        posTS++;
        return toReturn;
    }

    public boolean existsInTable(String valor) {
        for (List<Object> lista : tabla.values()) {
            if (valor.equals(lista.get(0))) {
                return true;
            }
        }
        return false;
    }

    public int getId(String valor) {
        int i = 0;
        boolean found = false;
        for (List<Object> lista : tabla.values()) {
            if (valor.equals(lista.get(0))) {
                found=true;
                break;
            }
            i++;
        }
        return found ? i : -1;
    }

    public types getTipo(String valor) {
        List<Object> list = null;
        for (List<Object> lista : tabla.values()) {
            if (valor.equals(lista.get(0))) {
                list = lista;
            }
        }
        return list == null ? types.tipo_error : (types) list.get(1);
    }

    public void insertarTipo(int id, types tipo) {
        List<Object> entrada = tabla.get(id);
        entrada.add(1, tipo);
        entrada.add(2, desplazamientoInterno);
        desplazamientoInterno += tipo.getAncho();

    }

    public List<Object> getFromPos(int pos) {
        return tabla.get(pos);
    }

    public void insertarDatosFuncion(int idFuncion, int numero, List<types> tipos) {
        List<Object> data = getFromPos(idFuncion);

        //System.err.println(idFuncion);
        //System.err.println(numero);


        data.set(3, numero);

        data.set(5, "eti" + idFuncion);
        int i = 0;
        for (types tipoArg : tipos) {
            data.add(tipoArg);
            if ((longitud - i) - 6 == 0) {
                cabecera.add("tipoArgumento" + i);
                longitud++;
            }
            i++;
        }
        return;
    }

    public void insertarFuncion(int idFuncion, types returnType) {
        List<Object> data = getFromPos(idFuncion);
        data.set(1, types.FUNCTION);
        data.set(2, desplazamientoInterno);
        data.set(3, null);
        data.set(4, returnType);
        // System.err.println(data);
        return;
    }

    public void showTable() {

        String[] cabeza = new String[longitud];
        for (int i = 0; i < cabeza.length; i++) {
            cabeza[i] = cabecera.get(i);
        }

        if (posTS == 0) {
            Object[][] data = { { null, null, null, null, null, null } };
            Table.createAndShowGUI(name, cabeza, data);
        } else {
            Object[][] data = new Object[posTS][longitud];
            int i = 0;
            for (List<Object> lista : tabla.values()) {
                for (int j = 0; j < lista.size(); j++) {
                    try {
                        data[i][j] = lista.get(j);
                    } catch (Exception e) {
                        continue;
                    }
                }
                i++;
            }
            Table.createAndShowGUI(name, cabeza, data);
        }

    }

    private void printRow(List<Object> row) {
        int i = 0;
        for (Object object : row) {
            if (object != null) {
                String atributo = cabecera.get(i);
                if (i == 0) {
                    System.out.println("\t* " + atributo + " : \'" + object.toString() + "\'");

                } else {
                    if (i == 1) {
                        System.out.println("\tATRIBUTOS:");
                    }
                    System.out.println("\t+ " + atributo + " : " + object.toString());
                    if (i >= longitud) {
                        System.out.println("\t\t+ " + atributo + " : " + object.toString());
                    }

                }

            }
            i++;
        }
        System.out.println("--------------------------------------------------------------");
    }

    public void printTable() {

        System.out.println("CONTENIDO DE LA TABLA " + name + " :\n");
        for (List<Object> row : tabla.values()) {
            printRow(row);
        }

    }
}
