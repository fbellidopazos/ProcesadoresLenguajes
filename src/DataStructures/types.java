package DataStructures;

public enum types {
    NUMBER("NUMBER",2),
    STRING("STRING",3),
    BOOLEAN("BOOLEAN",2),
    FUNCTION("FUNCTION",0),
    EMPTY("EMTPY",0),
    tipo_Ok("tipoOk",0),
    tipo_error("tipoError",0);;
    

    private final String name;
    private final int ancho;

    types(String name,int ancho){
        this.name=name;
        this.ancho=ancho;
    }
    public String getName(){
        return this.name;
    }
    public int getAncho(){
        return this.ancho;
    }
}
