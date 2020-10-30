import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GestorTablaSimbolos {
    Stack<TablaSimbolos> pilaTablas;
    List<TablaSimbolos> todasTablas;
    TablaSimbolos currentTable;

    public GestorTablaSimbolos(String mainName){
        this.pilaTablas=new Stack<>();
        this.todasTablas=new ArrayList<>();

        TablaSimbolos ts=new TablaSimbolos(mainName);

        this.pilaTablas.push(ts);
        this.todasTablas.add(ts);
        this.currentTable=ts;
    }
    public int insertarLexema(String lexema){

        
        return currentTable.newInsert(lexema);
    }
    public void showAllTables(){
        for (TablaSimbolos tablaSimbolos : todasTablas) {
            tablaSimbolos.showTable();
        }
    }
}
