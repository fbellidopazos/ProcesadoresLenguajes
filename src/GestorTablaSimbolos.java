import java.util.ArrayList;
import java.util.List;


import DataStructures.types;




public class GestorTablaSimbolos {
    
    List<TablaSimbolos> todasTablas;
    TablaSimbolos currentTable;
    TablaSimbolos globalTable;
    moduloError errorModule;
    int contador=1;
    boolean inFunction=false;
    int lastFunctionId = 0;

    public GestorTablaSimbolos(String mainName,moduloError errorModule){
        
        this.todasTablas=new ArrayList<>();
        this.errorModule=errorModule;
        TablaSimbolos ts=new TablaSimbolos(mainName+" #"+contador);

        
        this.todasTablas.add(ts);
        this.currentTable=ts;
        this.globalTable=ts;

        contador+=1;
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
        
        int i = globalTable.newInsert(nombre);
        globalTable.insertarFuncion(i, tipo);  
        lastFunctionId = i;

        TablaSimbolos ts=new TablaSimbolos("FUNCION \""+nombre+"\" #"+contador);
        
        currentTable=ts;
        todasTablas.add(ts);

        inFunction=true;
        
        return i;
    }
    public void exitFunction(){
        currentTable=globalTable;
        inFunction=false;
        contador+=1;
    }
    
    public void insertarTipoLocal(int id,types tipo){

        
        
            currentTable.insertarTipo(id, tipo);
            

    }
    public void insertarTipoGlobal(int id,types tipo){

        
       
            globalTable.insertarTipo(id, tipo);
        
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
    
    public Object[] getDatosFuncion(String nombreFuncion)  {
        
        List<Object> datosFuncion = getFuncion(globalTable.getId(nombreFuncion));
        
        
        Object[] datos =new Object[3];

        datos[0] = datosFuncion.get(3);
        datos[1] = datosFuncion.get(4);
        List<types> tipos = new ArrayList<>();
        int i = 0;
        for(Object valores : datosFuncion){
            if(i>=6){
                tipos.add((types)valores);
            }
            i++;
        }
        datos[2] = tipos;

        return datos;
    }

    public int insertarLexema(String lexema,int line){

        // Buscamos si esta declarado en la activa
        boolean found=false;
        int i=0;
        for (List<Object> fila : currentTable.tabla.values()) {
            
            if(fila.get(0).equals(lexema)){ // .get 0 OJO
                errorModule.raiseError(1,line);
                found=true;
                break;
                 
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

    public void insertarDatosFuncion(int idFuncion,int numero,List<types> tipos){
        
        globalTable.insertarDatosFuncion(idFuncion, numero, tipos);
        

    }
    public void salirFuncion(){
        currentTable=globalTable;
        inFunction=false;
    }
    public void showAllTables(boolean show) {
        
        for (TablaSimbolos tablaSimbolos : todasTablas) {
            if(show)
                tablaSimbolos.showTable();
            tablaSimbolos.printTable();
        }




    }
}
