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

            GestorTablaSimbolos gestorTablaSimbolos=new GestorTablaSimbolos("TS_"+parts[parts.length-1],errorModule);

            PrintStream fileOut = new PrintStream("./outputs/gramaticaTabular.txt");
            System.setOut(fileOut);
            analizadorLexico aLexico=new analizadorLexico(archivo,errorModule,gestorTablaSimbolos);
            
            fileOut = new PrintStream("./outputs/tokens.txt");
            System.setOut(fileOut);

            /*
            Aqui el cuerpo de funcionamiento del procesador
            */
            Token<String,String> token=aLexico.generarToken(); 
            for (int i = 0; token.first!="EOF"; i++) {
                System.out.println(token);

                token=aLexico.generarToken();
            }   
            System.out.println(token);


            
            gestorTablaSimbolos.showAllTables();

            
        }
        else{
            throw new Exception("No hay archivo para analizar"); // No hay archivo a analizador
        }
        


        System.out.println("");
    }
}
