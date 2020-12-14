import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.io.FileNotFoundException;


public class GestorTablaSimbolos {
    Stack<TablaSimbolos> pilaTablas;
    List<TablaSimbolos> todasTablas;
    TablaSimbolos currentTable;
    moduloError errorModule;

    public GestorTablaSimbolos(String mainName,moduloError errorModule){
        this.pilaTablas=new Stack<>();
        this.todasTablas=new ArrayList<>();
        this.errorModule=errorModule;
        TablaSimbolos ts=new TablaSimbolos(mainName+" #1");

        this.pilaTablas.push(ts);
        this.todasTablas.add(ts);
        this.currentTable=ts;
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
    public void showAllTables() throws FileNotFoundException{
        //PrintStream fileOut = new PrintStream("./outputs/TablaSimbolos.txt");
        //System.setOut(fileOut);


        for (TablaSimbolos tablaSimbolos : todasTablas) {
            //tablaSimbolos.showTable();
            tablaSimbolos.printTable();
        }




    }
}
