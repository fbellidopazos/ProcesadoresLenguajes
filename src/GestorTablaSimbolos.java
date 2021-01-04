import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import DataStructures.types;

import java.io.FileNotFoundException;


public class GestorTablaSimbolos {
    // Stack<TablaSimbolos> pilaTablas;
    List<TablaSimbolos> todasTablas;
    TablaSimbolos currentTable;
    TablaSimbolos globalTable;
    moduloError errorModule;
    int contador=1;
    boolean inFunction=false;

    public GestorTablaSimbolos(String mainName,moduloError errorModule){
        // this.pilaTablas=new Stack<>();
        this.todasTablas=new ArrayList<>();
        this.errorModule=errorModule;
        TablaSimbolos ts=new TablaSimbolos(mainName+" #"+contador);

        // this.pilaTablas.push(ts);
        this.todasTablas.add(ts);
        this.currentTable=ts;
        this.globalTable=ts;

        contador++;
    }

    public int comprobarEnTS(String valor){
        int i= 0;
        i=globalTable.existsInTable(valor)?1:0;
        if(inFunction){
            i=currentTable.existsInTable(valor)?2:i;
        }
        return i;
    }
    public int insertarGlobal(String lexema){
        return globalTable.newInsert(lexema);
    }
    public int insertarLocal(String lexema){
        return currentTable.newInsert(lexema);
    }
    public int insertarFuncion(String nombre,types tipo){
        int i = currentTable.newInsert(nombre);
        currentTable.insertarFuncion(i, tipo);  

        TablaSimbolos ts=new TablaSimbolos("funcion #"+contador);
        contador++;
        currentTable=ts;
        todasTablas.add(ts);

        inFunction=true;
        
        return i;
    }
    public void exitFunction(){
        currentTable=globalTable;
        inFunction=false;
    }
    public void insertarTipo(int id,types tipo){
        if(inFunction){
            currentTable.insertarTipo(id, tipo);
            

        }else{
            globalTable.insertarTipo(id, tipo);
        }
    }
    
    public types getType(String valor){
        int i =comprobarEnTS(valor);
        if(i == 1){
            return globalTable.getTipo(valor);
        }else if(i == 2){
            return currentTable.getTipo(valor);
        }else{
            return types.tipo_error;
        }
    }
    

    public int insertarLexema(String lexema,int line) throws Exception{

        // Buscamos si esta declarado en la activa
        boolean found=false;
        int i=0;
        for (List<Object> fila : currentTable.tabla.values()) {
            
            if(fila.get(0).equals(lexema)){ // .get 0 OJO
                errorModule.raiseError(1,line);
                found=true;
                break;
                //throw new Exception("Error de Analisis"); // EVIL IS EVIL 
            }
            i++;
        }

        if(!found){
            return currentTable.newInsert(lexema);
        }

        return i;
    }

    


    public List<Object> getFuncion(int id){
        return globalTable.getFromPos(id);
    }

    public void insertarFuncion(int idFuncion,int numero,List<types> tipos,types returnType){
        currentTable.insertarFuncion(idFuncion, numero, tipos, returnType);
        TablaSimbolos ts=new TablaSimbolos("funcion #"+contador);
        contador++;
        currentTable=ts;
        todasTablas.add(ts);
        
        return;
    }
    public void salirFuncion(){
        currentTable=globalTable;
        inFunction=false;
    }
    public void showAllTables() throws FileNotFoundException{
        //PrintStream fileOut = new PrintStream("./outputs/TablaSimbolos.txt");
        //System.setOut(fileOut);


        for (TablaSimbolos tablaSimbolos : todasTablas) {
            //tablaSimbolos.showTable();
            tablaSimbolos.printTable();
        }




    }
}
