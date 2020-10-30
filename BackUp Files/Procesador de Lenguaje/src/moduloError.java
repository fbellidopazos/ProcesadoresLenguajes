import java.util.HashMap;

public class moduloError {
    HashMap<Integer,String> errorCodes;


    public void raiseError(int errorCode){
        System.err.println(errorCodes.get(errorCode));
    }

    public moduloError(){
        errorCodes=new HashMap<>();
        
        errorCodes.put(0, "Caracter No valido");


    }
}
