import DataStructures.Pair;
import DataStructures.Token;
import java.io.IOException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;
import java.io.PrintStream;

public class main {
    
    

    public static void main(String[] args) throws Exception {
        

        

        // Seleccionador de archivos 
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
        File workingDirectory = new File(System.getProperty("user.dir")); // El directorio principal
        jfc.setCurrentDirectory(workingDirectory); 
        int returnValue = jfc.showOpenDialog(null);
		       
        moduloError errorModule=new moduloError();
        

		if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String archivo=selectedFile.getCanonicalPath();


            System.out.println(archivo);
            String[] parts=archivo.split("\\\\");
            String[] name=parts[parts.length-1].split(".");
            
            String value="MAIN";
            
            GestorTablaSimbolos gestorTablaSimbolos=new GestorTablaSimbolos("TS"+value,errorModule);
            analizadorLexico aLexico=new analizadorLexico(archivo,errorModule,gestorTablaSimbolos);
            analizadorSintactico aSintactico = new analizadorSintactico(aLexico,errorModule,gestorTablaSimbolos);


            PrintStream fileOut = new PrintStream("./outputs/Parse.txt");
            System.setOut(fileOut);
            String parse="-";
            while(!parse.equals("")){
                parse=aSintactico.aSintactico();
                System.out.println(parse);
            }
            //System.out.println(parse);
            

            /*
            PrintStream fileOut = new PrintStream("./outputs/gramaticaTabular.txt");
            System.setOut(fileOut);
            */
            
            /*
            fileOut = new PrintStream("./outputs/tokens.txt");
            System.setOut(fileOut);

            
            //Aqui el cuerpo de funcionamiento del procesador
            
            Token<String,String> token=aLexico.generarToken(); 
            for (int i = 0; token.first!="EOF"; i++) {
                System.out.println(token);

                token=aLexico.generarToken();
            }   
            System.out.println(token);
            */
            
            
            gestorTablaSimbolos.showAllTables();

            
        }
        else{
            throw new Exception("No hay archivo para analizar"); // No hay archivo a analizador
        }
        
    }
}
