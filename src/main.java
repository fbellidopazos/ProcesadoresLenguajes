import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import DataStructures.Token;

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
            
            System.out.println("Analizando el archivo: ");
            System.out.println(archivo);
            //String[] parts=archivo.split("\\\\");
            //String name=parts[parts.length-1];
            //System.out.println(name);
            String value="GLOBAL";
            
            GestorTablaSimbolos gestorTablaSimbolos=new GestorTablaSimbolos("TS"+value,errorModule);
            analizadorLexico aLexico=new analizadorLexico(archivo,errorModule,gestorTablaSimbolos);
            analizadorSemantico aSemantico = new analizadorSemantico(aLexico,errorModule,gestorTablaSimbolos);
            analizadorSintactico aSintactico = new analizadorSintactico(aLexico,aSemantico,errorModule,gestorTablaSimbolos);
            

            /*
            // Recolector de Tokens de ALex
            fileOut = new PrintStream("./outputs/tokens.txt");
            System.setOut(fileOut);
            Token<String,String> token=aLexico.generarToken(); 
            for (int i = 0; token.first!="EOF"; i++) {
                System.out.println(token);

                token=aLexico.generarToken();
            }   
            System.out.println(token);
            */

            // Recolector de Parse
            PrintStream fileOut = new PrintStream("./outputs/Parse.txt");
            System.setOut(fileOut);
            String parse=aSintactico.aSintactico();;
            System.out.println(parse);
           
            // Recolector de Tokens de ALex
            fileOut = new PrintStream("./outputs/tokens.txt");
            System.setOut(fileOut);
            for (Token<String,String> token : aSintactico.tokensUsados) {
                System.out.println(token);
            }
            
            // Log Semantico
            fileOut = new PrintStream("./outputs/logSemantico.txt");
            System.setOut(fileOut);
            for(String str : aSemantico.logSemantico){
                System.out.println(str);
            }
            
            // Recolector de tabla de Simbolos
            fileOut = new PrintStream("./outputs/tablaSimbolos.txt");
            System.setOut(fileOut);
            
            gestorTablaSimbolos.showAllTables();

            fileOut = new PrintStream("./outputs/erroresDeAnalisis.txt");
            System.setOut(fileOut);
            for (String error : errorModule.analysisErrors) {
                System.out.println(error);
            }

            fileOut.close();
        }
        else{
            throw new Exception("No hay archivo para analizar"); // No hay archivo a analizador
        }
        
    }
}
