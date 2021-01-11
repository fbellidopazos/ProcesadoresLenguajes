package DataStructures;

public enum types {
    NUMBER("NUMBER",1),
    STRING("STRING",64),
    BOOLEAN("BOOLEAN",1),
    FUNCTION("FUNCTION",0),
    EMPTY("EMPTY",0),
    tipo_Ok("tipoOk",0),
    tipo_error("tipoError",0);;
    

    private final String name;
    private final int ancho;

    types(String name,int ancho){
        this.name=name;
        this.ancho=ancho;
    }
    public String getName(){
        return "\'"+this.name+"\'";
    }
    public int getAncho(){
        return this.ancho;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getName();
    }
}
