import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DataStructures.Table;

public class TablaSimbolos {
    List<String> cabecera;
    HashMap<Integer,List<Object>> tabla;
    int posTS;
    int longitud;
    String name;

    public TablaSimbolos(String name){
        this.name=name;
        cabecera=new ArrayList<String>();
        longitud=6;
        String[] cabeza={"PosicionTS","Lexema","Desplazamiento","NºParametros","ReturnType","Etiqueta"};
        for (int i = 0; i < cabeza.length; i++) {
            cabecera.add(cabeza[i]);
        }
        tabla=new HashMap<>();
    }
    public int newInsert(String lexema){

        List<Object> entrada=new ArrayList<>(6);

        entrada.add(0, posTS);
        entrada.add(1, lexema);

        tabla.put(posTS, entrada);

        int toReturn=posTS;
        posTS++;
        return toReturn;
    }

    public void showTable(){

       

        String[] cabeza=new String[longitud];
        for (int i = 0; i < cabeza.length; i++) {
            cabeza[i]=cabecera.get(i);
        }


        if(posTS==0){
            Object[][] data={{"null","null","null","null","null","null"}};
            Table.createAndShowGUI(name,cabeza, data);
        }else{
            Object[][] data=new Object[posTS][longitud];
            int i=0;
            for (List<Object> lista : tabla.values()) {
                for (int j = 0; j < lista.size(); j++) {
                    
                    data[i][j]=lista.get(j);
                    
                }
                i++;
            }
            Table.createAndShowGUI(name,cabeza, data);
        }
        
    }
}
