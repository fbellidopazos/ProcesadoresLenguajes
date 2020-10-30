import DataStructures.Pair;
import java.io.IOException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

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
            
            analizadorLexico aLexico=new analizadorLexico(archivo,errorModule);
            aLexico.generarToken();
            
            /*
            Aqui el cuerpo de funcionamiento del procesador
            */



        }
        else{
            throw new Exception("No hay archivo para analizar"); // No hay archivo a analizador
        }
        


        System.out.println("");
    }
}
