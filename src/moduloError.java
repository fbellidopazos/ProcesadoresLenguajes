import java.util.HashMap;

public class moduloError {
    HashMap<Integer,String> errorCodes;


    public void raiseError(int errorCode){
        String error=errorCodes.get(errorCode);
        System.err.println(error);
        //System.out.println(error);
    }
    public void raiseError(int errorCode,int line){
        String error=errorCodes.get(errorCode);
        System.err.println(error+" at Line:"+line);
        //System.out.println(error+" at Line:"+line);
    }

    public moduloError(){
        errorCodes=new HashMap<>();
        
        errorCodes.put(0, "Error code 0: Caracter No valido");
        errorCodes.put(1,"Error code 1: Ya esta en la tabla de simbolos!!!");
        errorCodes.put(-1,"Error code -1:Something did not go according to plan\n Nuestra Culpa =D");

    }
}
