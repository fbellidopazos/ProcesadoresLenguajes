import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import DataStructures.Token;
import DataStructures.types;

public class analizadorSemantico {
    analizadorLexico aLexico;
    moduloError errorModule;
    GestorTablaSimbolos gestorTablaSimbolos;
    List<String> logSemantico;

    Stack<HashMap<String, Object>> stackAtributos; // NOTA MENTAL: stack.get(stack.indexOf(stack.peek())-n) el primer
                                                   // elemento es la cima(NO hace pop)

    public HashMap<String, Object> function0() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", (types) getFromPos(1).get("tipo"));
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        return resHashMap;
    }

    public HashMap<String, Object> function1() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types bTipo = (types) getFromPos(2).get("tipo");
        types pTipo = (types) getFromPos(1).get("tipo");

        if (bTipo == types.tipo_Ok && pTipo == types.tipo_Ok) {
            resHashMap.put("tipo", types.tipo_Ok);

        } else {
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        return resHashMap;
    }

    public HashMap<String, Object> function2() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types fTipo = (types) getFromPos(2).get("tipo");
        types pTipo = (types) getFromPos(1).get("tipo");

        if (fTipo == types.tipo_Ok && pTipo == types.tipo_Ok) {
            resHashMap.put("tipo", types.tipo_Ok);
        } else {
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // P --> F P

        return resHashMap;
    }

    public HashMap<String, Object> function3() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", types.tipo_Ok);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // P --> Lambda

        return resHashMap;
    }

    public HashMap<String, Object> function4() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        HashMap<String, Object> iAtribs = getFromPos(3);
        HashMap<String, Object> jAtribs = getFromPos(2);
        HashMap<String, Object> gAtribs = getFromPos(1);

        if (gAtribs.get("hasReturn") != null && (boolean) gAtribs.get("hasReturn")) {

            if ((types) gAtribs.get("returnType") == (types) iAtribs.get("tipo")) {
                // gestorTablaSimbolos.insertarDatosFuncion((int)iAtribs.get("id"),
                // (int)jAtribs.get("numeros"), (List<types>)jAtribs.get("tipos"));
                resHashMap.put("tipo", gAtribs.get("tipo"));
            } else {
                errorModule.raiseError(3,
                        "Estas devolviendo dentor de la funcion algo del tipo: " + (types) gAtribs.get("returnType")
                                + " cuando tu funcion tiene que devolver: " + (types) iAtribs.get("tipo"));
            }
        } else {
            if ((types) iAtribs.get("tipo") == types.EMPTY) {

                // gestorTablaSimbolos.insertarDatosFuncion((int)iAtribs.get("id"),
                // (int)jAtribs.get("numeros"), (List<types>)jAtribs.get("tipos"));
                resHashMap.put("tipo", gAtribs.get("tipo"));
            } else {
                errorModule.raiseError(3,
                        "La cabeza de la funcion esta mal, prueba a poner : function id(...){}\n\tEs decir sin tipo definir el tipo de la funcion");
            }
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // F --> I J G

        gestorTablaSimbolos.exitFunction();
        return resHashMap;
    }

    public HashMap<String, Object> function5() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        String idValue = (String) getFromPos(1).get("valor");
        types hTipo = (types) getFromPos(2).get("tipo");
        if (gestorTablaSimbolos.comprobarEnTS(idValue) == 0) {

            int codigoTs = gestorTablaSimbolos.insertarFuncion(idValue, hTipo);
            // System.err.println(idValue);

            resHashMap.put("tipo", hTipo);
            resHashMap.put("valor", idValue);
            resHashMap.put("id", codigoTs);
        } else {
            errorModule.raiseError(3, "Nombre de funcion usado previamente");
            resHashMap.put("tipo", types.tipo_error);
            resHashMap.put("valor", idValue);
            resHashMap.put("id", -1);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);

        // I --> function H id

        return resHashMap;
    }

    public HashMap<String, Object> function6() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        HashMap<String, Object> aAtribs = getFromPos(2);

        gestorTablaSimbolos.insertarDatosFuncion(gestorTablaSimbolos.lastFunctionId, (int) aAtribs.get("numeros"),
                (List<types>) aAtribs.get("tipos"));

        resHashMap.put("tipo", null);
        resHashMap.put("numeros", getFromPos(2).get("numeros"));
        resHashMap.put("tipos", getFromPos(2).get("tipos"));
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // J --> ( A )

        return resHashMap;
    }

    public HashMap<String, Object> function7() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        HashMap<String, Object> cAtribs = getFromPos(2);

        resHashMap.put("tipo", cAtribs.get("tipo"));
        resHashMap.put("numeros", cAtribs.get("numeros"));
        resHashMap.put("tipos", cAtribs.get("tipos"));
        resHashMap.put("returnType", cAtribs.get("returnType"));
        resHashMap.put("hasReturn", cAtribs.get("hasReturn"));
        resHashMap.put("valor", cAtribs.get("valor"));
        resHashMap.put("id", cAtribs.get("id"));

        // G --> { C }

        return resHashMap;
    }

    public HashMap<String, Object> function8() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types tTipo = (types) getFromPos(1).get("tipo");

        resHashMap.put("tipo", tTipo);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // H --> T

        return resHashMap;
    }

    public HashMap<String, Object> function9() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", types.EMPTY);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // H --> Lambda

        return resHashMap;
    }

    public HashMap<String, Object> function10() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        HashMap<String, Object> kAtribs = getFromPos(1);
        String idValue = (String) getFromPos(2).get("valor");
        types tTipo = (types) getFromPos(3).get("tipo");

        gestorTablaSimbolos.insertarTipoLocal(gestorTablaSimbolos.insertarLocal(idValue), tTipo);

        resHashMap.put("tipo", null);
        resHashMap.put("numeros", 1 + (int) kAtribs.get("numeros"));
        resHashMap.put("tipos", unionTipos(tTipo, (List<types>) kAtribs.get("tipos")));
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // A --> T id K

        return resHashMap;
    }

    public HashMap<String, Object> function11() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", null);
        resHashMap.put("numeros", 0);
        resHashMap.put("tipos", new ArrayList<>());
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        return resHashMap;
    }

    public HashMap<String, Object> function12() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        HashMap<String, Object> kAtribs = getFromPos(1);
        String idValue = (String) getFromPos(2).get("valor");
        types tTipo = (types) getFromPos(3).get("tipo");

        gestorTablaSimbolos.insertarTipoLocal(gestorTablaSimbolos.insertarLocal(idValue), tTipo);

        resHashMap.put("tipo", null);
        resHashMap.put("numeros", 1 + (int) kAtribs.get("numeros"));
        resHashMap.put("tipos", unionTipos(tTipo, (List<types>) kAtribs.get("tipos")));
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // K --> , T id K

        return resHashMap;
    }

    public HashMap<String, Object> function13() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", null);
        resHashMap.put("numeros", 0);
        resHashMap.put("tipos", new ArrayList<types>());
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // K --> Lambda

        return resHashMap;
    }

    public HashMap<String, Object> function14() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        HashMap<String, Object> bAtribs = getFromPos(2);
        HashMap<String, Object> cAtribs = getFromPos(1);
        types bTipo = (types) bAtribs.get("tipo");
        types cTipo = (types) cAtribs.get("tipo");

        if (bTipo == types.tipo_Ok && cTipo == types.tipo_Ok) {
            resHashMap.put("tipo", types.tipo_Ok);
        } else {
            errorModule.raiseError(3, "Tienes algun problema en el codigo alrededor de la linea " + aLexico.line
                    + "\n\t@Usuario: Mira otros mensajes de error para hacerte una idea");
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", bAtribs.get("numeros"));
        resHashMap.put("tipos", bAtribs.get("tipos"));

        if (bAtribs.get("hasReturn") != null && (boolean) bAtribs.get("hasReturn")) {
            resHashMap.put("returnType", bAtribs.get("returnType"));
            resHashMap.put("hasReturn", true);
        } else if (cAtribs.get("hasReturn") != null && (boolean) cAtribs.get("hasReturn")) {
            resHashMap.put("returnType", cAtribs.get("returnType"));
            resHashMap.put("hasReturn", true);
        }

        resHashMap.put("valor", bAtribs.get("valor"));
        resHashMap.put("id", bAtribs.get("id"));

        // C --> B C

        return resHashMap;
    }

    public HashMap<String, Object> function15() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", types.tipo_Ok);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", false);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // C --> Lambda

        return resHashMap;
    }

    public HashMap<String, Object> function16() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        String idValue = (String) getFromPos(4).get("valor");

        types eTipo = (types) getFromPos(2).get("tipo");

        if (gestorTablaSimbolos.comprobarEnTS(idValue) == 0) {
            int id = gestorTablaSimbolos.insertarGlobal(idValue);
            gestorTablaSimbolos.insertarTipoGlobal(id, types.NUMBER);
        }

        if ((types) gestorTablaSimbolos.getType(idValue) == eTipo) {

            resHashMap.put("tipo", types.tipo_Ok);

        } else {
            errorModule.raiseError(3,
                    "Ambos lados tienen que ser del mismo tipo en un entorno de la linea " + aLexico.line
                            + "\n\t@Usuario: " + idValue + " con tipo <" + (types) gestorTablaSimbolos.getType(idValue)
                            + ">, esta siendo asignado a " + eTipo);
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // S --> id = E ;

        return resHashMap;
    }

    public HashMap<String, Object> function17() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        String nombreFuncion = (String) getFromPos(5).get("valor");

        if (gestorTablaSimbolos.comprobarEnTS(nombreFuncion) == 0) {
            resHashMap.put("tipo", types.tipo_error);
            errorModule.raiseError(3,
                    "La funcion " + nombreFuncion + " no ha sido definida previamente, por favor definela =D");
        } else {
            Object[] datosFuncion = gestorTablaSimbolos.getDatosFuncion(nombreFuncion);

            HashMap<String, Object> lAtribs = getFromPos(3);
            int numeros = (int) lAtribs.get("numeros");
            List<types> tipos = (List<types>) lAtribs.get("tipos");

            if (checkTiposNumeros(datosFuncion, numeros, tipos)) {

                resHashMap.put("tipo", types.tipo_Ok);

            } else {
                resHashMap.put("tipo", types.tipo_error);
            }
        }

        // resHashMap.put("tipo", types.tipo_Ok);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // S --> id ( L ) ;

        return resHashMap;
    }

    public HashMap<String, Object> function18() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types eTipo = (types) getFromPos(3).get("tipo");
        if (eTipo == types.STRING || eTipo == types.NUMBER) {
            resHashMap.put("tipo", types.tipo_Ok);
        } else {
            resHashMap.put("tipo", types.tipo_error);
            errorModule.raiseError(3, "Alert tiene que tener como argumento una cadena o numero"
                    + "\t@Usuario:\n\t\t(Contruccion Correcta) >> alert(<cadena|numero>);\n\t\t(Su construccion) >> alert(<"
                    + eTipo + ">);");
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // S --> alert ( E ) ;

        return resHashMap;
    }

    public HashMap<String, Object> function19() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        String idValue = (String) getFromPos(3).get("valor");
        if (gestorTablaSimbolos.comprobarEnTS(idValue) == 0) {
            int id = gestorTablaSimbolos.insertarGlobal(idValue);
            gestorTablaSimbolos.insertarTipoGlobal(id, types.NUMBER);
        }

        types tipoId = (types) gestorTablaSimbolos.getType(idValue);
        if (tipoId == types.NUMBER || tipoId == types.STRING) {

            resHashMap.put("tipo", types.tipo_Ok);

        } else {
            errorModule.raiseError(3,
                    "input tiene que tener como argumento una cadena o un entero\n\t@Usuario:\n \t\t (Construccion Correcta) >> input(<cadena|numero>);\t\t (Su construccion) >> input(<"
                            + tipoId + ">);");
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // S --> input( id );

        return resHashMap;
    }

    public HashMap<String, Object> function20() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types xTipo = (types) getFromPos(2).get("tipo");

        resHashMap.put("tipo", types.tipo_Ok);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", xTipo);
        resHashMap.put("hasReturn", true);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // S --> return X ;

        return resHashMap;
    }

    public HashMap<String, Object> function21() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        String idValue = (String) getFromPos(4).get("valor");
        types eTipo = (types) getFromPos(2).get("tipo");

        if (gestorTablaSimbolos.comprobarEnTS(idValue) == 0) {
            int id = gestorTablaSimbolos.insertarGlobal(idValue);
            gestorTablaSimbolos.insertarTipoGlobal(id, types.NUMBER);
        }

        if ((types) gestorTablaSimbolos.getType(idValue) == types.NUMBER) {
            if (eTipo == types.NUMBER) {
                resHashMap.put("tipo", types.tipo_Ok);
            } else {
                errorModule.raiseError(3,
                        "El lado izquierdo de la expresion tienen que ser un entero" + "\n\t@Usuario: " + idValue
                                + " con tipo " + (types) gestorTablaSimbolos.getType(idValue) + " -= " + eTipo);
                resHashMap.put("tipo", types.tipo_error);
            }
        } else {
            errorModule.raiseError(3, "El lado derecho de la expresion tienen que ser un entero" + "\n\t@Usuario: "
                    + idValue + " con tipo " + (types) gestorTablaSimbolos.getType(idValue) + " -= " + eTipo);
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // id -= E;

        return resHashMap;
    }

    public HashMap<String, Object> function22() throws Exception {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types eTipo = (types) getFromPos(3).get("tipo");
        HashMap<String, Object> cAtribs = getFromPos(1);

        if (eTipo == types.BOOLEAN) {
            if ((types) cAtribs.get("tipo") == types.tipo_Ok) {

                resHashMap.put("tipo", cAtribs.get("tipo"));

            } else {
                errorModule.raiseError(3, "Tienes algun error en el cuerpo del if en " + aLexico.line + "\n\t@Usuario: "
                        + "\n\t\t (Construccion Coreccta) >> if(<BOOLEAN>) <CodigoCorrecto>"
                        + "\n\t\t (Su construccion) >> if(<" + eTipo + ">) <" + (types) cAtribs.get("tipo") + ">");
                resHashMap.put("tipo", types.tipo_error);
            }
        } else {
            errorModule.raiseError(3,
                    "La expresion dentro del if debe ser logica en " + aLexico.line + "\n\t@Usuario: "
                            + "\n\t\t (Construccion Coreccta) >> if(<BOOLEAN>) <CodigoCorrecto>"
                            + "\n\t\t (Su construccion) >> if(<" + eTipo + ">) <" + (types) cAtribs.get("tipo") + ">");
            resHashMap.put("tipo", types.tipo_error);
        }
        resHashMap.put("numeros", cAtribs.get("numeros"));
        resHashMap.put("tipos", cAtribs.get("tipos"));
        resHashMap.put("returnType", cAtribs.get("returnType"));
        resHashMap.put("hasReturn", cAtribs.get("hasReturn"));
        resHashMap.put("valor", cAtribs.get("valor"));
        resHashMap.put("id", cAtribs.get("id"));

        // B --> if ( E ) S
        return resHashMap;
    }

    public HashMap<String, Object> function23() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types tTipo = (types) getFromPos(3).get("tipo");
        String idValue = (String) getFromPos(2).get("valor");

        int esta = gestorTablaSimbolos.comprobarEnTS(idValue);

        if (gestorTablaSimbolos.inFunction && (esta == 0 || esta == 1)) {
            int id = gestorTablaSimbolos.insertarLocal(idValue);
            gestorTablaSimbolos.insertarTipoLocal(id, tTipo);
            resHashMap.put("tipo", types.tipo_Ok);
        } else if (!gestorTablaSimbolos.inFunction && esta == 0) {
            int id = gestorTablaSimbolos.insertarGlobal(idValue);
            gestorTablaSimbolos.insertarTipoGlobal(id, tTipo);
            resHashMap.put("tipo", types.tipo_Ok);
        } else {
            errorModule.raiseError(3, "Nombre de identificador: \"" + idValue + "\" ,ya declarado previamente");
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // B --> let T id;

        return resHashMap;
    }

    public HashMap<String, Object> function24() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        HashMap<String, Object> cAtribs = getFromPos(1);

        resHashMap.put("tipo", cAtribs.get("tipo"));
        resHashMap.put("numeros", cAtribs.get("numeros"));
        resHashMap.put("tipos", cAtribs.get("tipos"));
        resHashMap.put("returnType", cAtribs.get("returnType"));
        resHashMap.put("hasReturn", cAtribs.get("hasReturn"));
        resHashMap.put("valor", cAtribs.get("valor"));
        resHashMap.put("id", cAtribs.get("id"));

        // B --> S

        return resHashMap;
    }

    public HashMap<String, Object> function25() throws Exception {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types eTipo = (types) getFromPos(3).get("tipo");
        HashMap<String, Object> cAtribs = getFromPos(7);

        if (eTipo == types.BOOLEAN) {
            if ((types) cAtribs.get("tipo") == types.tipo_Ok) {

                resHashMap.put("tipo", cAtribs.get("tipo"));
                resHashMap.put("numeros", cAtribs.get("numeros"));
                resHashMap.put("tipos", cAtribs.get("tipos"));
                resHashMap.put("returnType", cAtribs.get("returnType"));
                resHashMap.put("hasReturn", cAtribs.get("hasReturn"));
                resHashMap.put("valor", cAtribs.get("valor"));
                resHashMap.put("id", cAtribs.get("id"));
            } else {
                errorModule.raiseError(3,
                        "Tienes algun error en el cuerpo del do en " + aLexico.line + "\n\t@Usuario: "
                                + "\n\t\t (Construccion Coreccta) >> do {<CodigoCorrecto>} while(<BOOLEAN>) "
                                + "\n\t\t (Su construccion) >> do {<" + (types) cAtribs.get("tipo") + ">} while("
                                + eTipo + ")");
                resHashMap.put("tipo", types.tipo_error);
                resHashMap.put("numeros", cAtribs.get("numeros"));
                resHashMap.put("tipos", cAtribs.get("tipos"));
                resHashMap.put("returnType", cAtribs.get("returnType"));
                resHashMap.put("hasReturn", cAtribs.get("hasReturn"));
                resHashMap.put("valor", cAtribs.get("valor"));
                resHashMap.put("id", cAtribs.get("id"));
            }
        } else {
            errorModule.raiseError(3, "La expresion dentro del while debe ser logica en " + aLexico.line
                    + "\n\t@Usuario: " + "\n\t\t (Construccion Coreccta) >> do {<CodigoCorrecto>} while(<BOOLEAN>) "
                    + "\n\t\t (Su construccion) >> do {<" + (types) cAtribs.get("tipo") + ">} while(" + eTipo + ")");
            resHashMap.put("tipo", types.tipo_error);
            resHashMap.put("numeros", cAtribs.get("numeros"));
            resHashMap.put("tipos", cAtribs.get("tipos"));
            resHashMap.put("returnType", cAtribs.get("returnType"));
            resHashMap.put("hasReturn", cAtribs.get("hasReturn"));
            resHashMap.put("valor", cAtribs.get("valor"));
            resHashMap.put("id", cAtribs.get("id"));
        }

        // B --> do { C } while ( E ) ;

        return resHashMap;
    }

    public HashMap<String, Object> function26() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", types.NUMBER);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // T --> NUMBEr

        return resHashMap;
    }

    public HashMap<String, Object> function27() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", types.BOOLEAN);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // T --> Boolean

        return resHashMap;
    }

    public HashMap<String, Object> function28() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", types.STRING);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // T --> String

        return resHashMap;
    }

    public HashMap<String, Object> function29() throws Exception {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types r1Tipo = (types) getFromPos(3).get("tipo");
        types uTipo = (types) getFromPos(1).get("tipo");

        if (r1Tipo == types.BOOLEAN) {
            if (uTipo == types.BOOLEAN) {
                resHashMap.put("tipo", types.BOOLEAN);
            } else {
                errorModule.raiseError(3,
                        "El lado derecho tiene que ser un logico" + "\n\t@Usuario: "
                                + "\n\t\t (Construccion Coreccta) >> <BOOLEAN> || <BOOLEAN> "
                                + "\n\t\t (Su construccion) >> <" + r1Tipo + "> || <" + uTipo + ">");
                resHashMap.put("tipo", types.tipo_error);
            }
        } else {
            errorModule.raiseError(3,
                    "El lado izquierdo tiene que ser un logico" + "\n\t@Usuario: "
                            + "\n\t\t (Construccion Coreccta) >> <BOOLEAN> || <BOOLEAN> "
                            + "\n\t\t (Su construccion) >> <" + r1Tipo + "> || <" + uTipo + ">");
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // E --> E || R

        return resHashMap;
    }

    public HashMap<String, Object> function30() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", (types) getFromPos(1).get("tipo"));
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // E --> R

        return resHashMap;
    }

    public HashMap<String, Object> function31() throws Exception {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types r1Tipo = (types) getFromPos(3).get("tipo");
        types uTipo = (types) getFromPos(1).get("tipo");

        if (r1Tipo == types.BOOLEAN) {
            if (uTipo == types.BOOLEAN) {
                resHashMap.put("tipo", types.BOOLEAN);
            } else {
                errorModule.raiseError(3,
                        "El lado derecho tiene que ser un logico " + "\n\t@Usuario: "
                                + "\n\t\t (Construccion Coreccta) >> <BOOLEAN> && <BOOLEAN> "
                                + "\n\t\t (Su construccion) >> <" + r1Tipo + "> && <" + uTipo + ">");
                resHashMap.put("tipo", types.tipo_error);
            }
        } else {

            errorModule.raiseError(3,
                    "El lado izquierdo tiene que ser un logico " + "\n\t@Usuario: "
                            + "\n\t\t (Construccion Coreccta) >> <BOOLEAN> && <BOOLEAN> "
                            + "\n\t\t (Su construccion) >> <" + r1Tipo + "> && <" + uTipo + ">");
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // R --> R && U

        return resHashMap;
    }

    public HashMap<String, Object> function32() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", (types) getFromPos(1).get("tipo"));
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // R --> U

        return resHashMap;
    }

    public HashMap<String, Object> function33() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types u1Tipo = (types) getFromPos(3).get("tipo");
        types vTipo = (types) getFromPos(1).get("tipo");

        if (u1Tipo == vTipo && u1Tipo == types.NUMBER) {
            resHashMap.put("tipo", types.BOOLEAN);
        } else {
            errorModule.raiseError(3,
                    "Tienen que ser del mismo tipo entero" + "\n\t@Usuario: "
                            + "\n\t\t (Construccion Coreccta) >> <NUMBER> == <NUMBER> "
                            + "\n\t\t (Su construccion) >> <" + u1Tipo + "> == <" + vTipo + ">");
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // U --> U == V

        return resHashMap;
    }

    public HashMap<String, Object> function34() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types u1Tipo = (types) getFromPos(3).get("tipo");
        types vTipo = (types) getFromPos(1).get("tipo");

        if (u1Tipo == vTipo && u1Tipo == types.NUMBER) {
            resHashMap.put("tipo", types.BOOLEAN);
        } else {
            errorModule.raiseError(3,
                    "Tienen que ser del mismo tipo entero " + "\n\t@Usuario: "
                            + "\n\t\t (Construccion Coreccta) >> <NUMBER> != <NUMBER> "
                            + "\n\t\t (Su construccion) >> <" + u1Tipo + "> != <" + vTipo + ">");
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // U --> U != V

        return resHashMap;
    }

    public HashMap<String, Object> function35() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", (types) getFromPos(1).get("tipo"));
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // U --> V

        return resHashMap;
    }

    public HashMap<String, Object> function36() throws Exception {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types v1Tipo = (types) getFromPos(3).get("tipo");
        types wTipo = (types) getFromPos(1).get("tipo");

        if (v1Tipo == types.NUMBER) {
            if (wTipo == types.NUMBER) {
                resHashMap.put("tipo", types.NUMBER);
            } else {
                errorModule.raiseError(3, "El lado derecho tiene que ser un numero");
                resHashMap.put("tipo", types.tipo_error);
            }
        } else {
            errorModule.raiseError(3,
                    "El lado izquierdo tiene que ser un numero" + "\n\t@Usuario: "
                            + "\n\t\t (Construccion Coreccta) >> <NUMBER> + <NUMBER> " + "\n\t\t (Su construccion) >> <"
                            + v1Tipo + "> + <" + wTipo + ">");
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // V --> V + W

        return resHashMap;
    }

    public HashMap<String, Object> function37() throws Exception {
        HashMap<String, Object> resHashMap = new HashMap<>();

        types v1Tipo = (types) getFromPos(3).get("tipo");
        types wTipo = (types) getFromPos(1).get("tipo");

        if (v1Tipo == types.NUMBER) {
            if (wTipo == types.NUMBER) {
                resHashMap.put("tipo", types.NUMBER);
            } else {
                errorModule.raiseError(3,
                        "El lado derecho tiene que ser un numero " + "\n\t@Usuario: "
                                + "\n\t\t (Construccion Coreccta) >> <NUMBER> - <NUMBER> "
                                + "\n\t\t (Su construccion) >> <" + v1Tipo + "> - <" + wTipo + ">");
                resHashMap.put("tipo", types.tipo_error);
            }
        } else {
            errorModule.raiseError(3, "El lado izquierdo tiene que ser un numero ");
            resHashMap.put("tipo", types.tipo_error);
        }

        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // V --> V - W

        return resHashMap;
    }

    public HashMap<String, Object> function38() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", (types) getFromPos(1).get("tipo"));
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // V --> W

        return resHashMap;
    }

    public HashMap<String, Object> function39() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        String idValue = (String) getFromPos(1).get("valor");

        if (gestorTablaSimbolos.comprobarEnTS(idValue) == 0) {
            int id = gestorTablaSimbolos.insertarGlobal(idValue);
            gestorTablaSimbolos.insertarTipoGlobal(id, types.NUMBER);
        }

        resHashMap.put("tipo", gestorTablaSimbolos.getType(idValue));
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // W --> id

        return resHashMap;
    }

    public HashMap<String, Object> function40() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", (types) getFromPos(2).get("tipo"));
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // W --> ( E )

        return resHashMap;
    }

    public HashMap<String, Object> function41() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        String nombreFuncion = (String) getFromPos(4).get("valor");

        if (gestorTablaSimbolos.comprobarEnTS(nombreFuncion) == 0) {
            resHashMap.put("tipo", types.tipo_error);
            errorModule.raiseError(3,
                    "La funcion " + nombreFuncion + " no ha sido definida previamente, por favor definela =D");
        } else {
            Object[] datosFuncion = gestorTablaSimbolos.getDatosFuncion(nombreFuncion);

            HashMap<String, Object> lAtribs = getFromPos(2);
            int numeros = (int) lAtribs.get("numeros");
            List<types> tipos = (List<types>) lAtribs.get("tipos");

            if (checkTiposNumeros(datosFuncion, numeros, tipos)) {

                resHashMap.put("tipo", (types) datosFuncion[1]);

            } else {
                resHashMap.put("tipo", types.tipo_error);
            }
        }

        // resHashMap.put("tipo", types.tipo_error);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // W --> id ( L )

        return resHashMap;
    }

    public HashMap<String, Object> function42() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", types.NUMBER);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // W --> NUMBER

        return resHashMap;
    }

    public HashMap<String, Object> function43() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", types.STRING);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // W --> CADENA

        return resHashMap;
    }

    public HashMap<String, Object> function44() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        int qNumeros = (int) getFromPos(1).get("numeros");
        List<types> eTipo = new ArrayList<>();
        eTipo.add((types) getFromPos(2).get("tipo"));
        List<types> qTipos = (List<types>) getFromPos(1).get("tipos");

        resHashMap.put("tipo", null);
        resHashMap.put("numeros", qNumeros + 1);
        resHashMap.put("tipos", unionTipos(eTipo, qTipos));
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // L --> E Q

        return resHashMap;
    }

    public HashMap<String, Object> function45() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", null);
        resHashMap.put("numeros", 0);
        resHashMap.put("tipos", new ArrayList<types>());
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // L --> Lambda

        return resHashMap;
    }

    public HashMap<String, Object> function46() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        int qNumeros = (int) getFromPos(1).get("numeros");
        List<types> eTipo = new ArrayList<>();
        eTipo.add((types) getFromPos(2).get("tipo"));
        List<types> qTipos = (List<types>) getFromPos(1).get("tipos");

        resHashMap.put("tipo", null);
        resHashMap.put("numeros", qNumeros + 1);
        resHashMap.put("tipos", unionTipos(eTipo, qTipos));
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // Q --> , E Q

        return resHashMap;
    }

    public HashMap<String, Object> function47() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", null);
        resHashMap.put("numeros", 0);
        resHashMap.put("tipos", new ArrayList<types>());
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // Q --> Lambda

        return resHashMap;
    }

    public HashMap<String, Object> function48() {
        HashMap<String, Object> resHashMap = new HashMap<>();

        resHashMap.put("tipo", (types) getFromPos(1).get("tipo"));
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // X --> E

        return resHashMap;
    }

    public HashMap<String, Object> function49() {
        HashMap<String, Object> resHashMap = new HashMap<>();
        resHashMap.put("tipo", types.EMPTY);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", null);
        resHashMap.put("id", null);

        // X --> Lambda

        return resHashMap;
    }

    public HashMap<String, Object> accionEjecutar(int regla) throws Exception {
        HashMap<String, Object> resHashMap;

        switch (regla) {
            case 0:
                resHashMap = function0();
                break;
            case 1:
                resHashMap = function1();
                break;
            case 2:
                resHashMap = function2();
                break;
            case 3:
                resHashMap = function3();
                break;
            case 4:
                resHashMap = function4();
                break;
            case 5:
                resHashMap = function5();
                break;
            case 6:
                resHashMap = function6();
                break;
            case 7:
                resHashMap = function7();
                break;
            case 8:
                resHashMap = function8();
                break;
            case 9:
                resHashMap = function9();
                break;
            case 10:
                resHashMap = function10();
                break;
            case 11:
                resHashMap = function11();
                break;
            case 12:
                resHashMap = function12();
                break;
            case 13:
                resHashMap = function13();
                break;
            case 14:
                resHashMap = function14();
                break;
            case 15:
                resHashMap = function15();
                break;
            case 16:
                resHashMap = function16();
                break;
            case 17:
                resHashMap = function17();
                break;
            case 18:
                resHashMap = function18();
                break;
            case 19:
                resHashMap = function19();
                break;
            case 20:
                resHashMap = function20();
                break;
            case 21:
                resHashMap = function21();
                break;
            case 22:
                resHashMap = function22();
                break;
            case 23:
                resHashMap = function23();
                break;
            case 24:
                resHashMap = function24();
                break;
            case 25:
                resHashMap = function25();
                break;
            case 26:
                resHashMap = function26();
                break;
            case 27:
                resHashMap = function27();
                break;
            case 28:
                resHashMap = function28();
                break;
            case 29:
                resHashMap = function29();
                break;
            case 30:
                resHashMap = function30();
                break;
            case 31:
                resHashMap = function31();
                break;
            case 32:
                resHashMap = function32();
                break;
            case 33:
                resHashMap = function33();
                break;
            case 34:
                resHashMap = function34();
                break;
            case 35:
                resHashMap = function35();
                break;
            case 36:
                resHashMap = function36();
                break;
            case 37:
                resHashMap = function37();
                break;
            case 38:
                resHashMap = function38();
                break;
            case 39:
                resHashMap = function39();
                break;
            case 40:
                resHashMap = function40();
                break;
            case 41:
                resHashMap = function41();
                break;
            case 42:
                resHashMap = function42();
                break;
            case 43:
                resHashMap = function43();
                break;
            case 44:
                resHashMap = function44();
                break;
            case 45:
                resHashMap = function45();
                break;
            case 46:
                resHashMap = function46();
                break;
            case 47:
                resHashMap = function47();
                break;
            case 48:
                resHashMap = function48();
                break;
            case 49:
                resHashMap = function49();
                break;
            default:
                resHashMap = null;
                errorModule.raiseError(-1, aLexico.line, "Tenemos un error en el analizador Semantico");
                // Mandar Error
                break;
        }

        return resHashMap;
    }

    public void pushTokenId(Token<String, String> tokenIdentificador) {
        HashMap<String, Object> resHashMap = new HashMap<>();

        resHashMap.put("tipo", null);
        resHashMap.put("numeros", null);
        resHashMap.put("tipos", null);
        resHashMap.put("returnType", null);
        resHashMap.put("hasReturn", null);
        resHashMap.put("valor", tokenIdentificador.second);

        stackAtributos.push(resHashMap);
    }

    public boolean comprobarArgumentosFuncion(int numeros1, int numeros2, List<types> tipos1, List<types> tipos2) {
        boolean res = true;
        if (numeros1 != numeros2) {
            res = false;
        } else {
            for (int i = 0; i < tipos1.size() && res; i++) {
                res = tipos1.get(i) == tipos2.get(i);
            }
        }

        return res;
    }

    public List<types> unionTipos(List<types> tipos1, List<types> tipos2) {
        List<types> res = new ArrayList<>();
        res.addAll(tipos1);
        res.addAll(tipos2);

        return res;
    }

    public List<types> unionTipos(types tipo1, List<types> tipos2) {
        List<types> res = new ArrayList<>();
        res.add(tipo1);
        res.addAll(tipos2);

        return res;
    }

    public boolean checkTiposNumeros(Object[] datosFuncion, int numeros, List<types> tipos) {
        boolean res = false;
        if ((int) datosFuncion[0] == numeros) {
            res = true;
            List<types> tiposOriginales = (List<types>) datosFuncion[2];
            for (int i = 0; i < tipos.size() && res; i++) {
                res = res && (tiposOriginales.get(i) == tipos.get(i));
            }

        }
        return res;

    }

    public HashMap<String, Object> getFromPos(int i) {

        return stackAtributos.get(stackAtributos.size() - 1 - (2 * i - 1));
    }

    public analizadorSemantico(analizadorLexico aLexico, moduloError errorModule,
            GestorTablaSimbolos gestorTablaSimbolos) {
        this.aLexico = aLexico;
        this.logSemantico = new ArrayList<>();
        this.errorModule = errorModule;
        this.gestorTablaSimbolos = gestorTablaSimbolos;

        stackAtributos = new Stack<>();

    }
}
