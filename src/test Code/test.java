import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;


public class test {
    public static void main(String[] args) throws IOException{
        
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView());
        File workingDirectory = new File(System.getProperty("user.dir"));
        jfc.setCurrentDirectory(workingDirectory);



		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);
        
        
		if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jfc.getSelectedFile();
            String archivo=selectedFile.getCanonicalPath();
            // Inicializamos archivo a leer
        File f = new File(archivo); // Creation of File Descriptor for input file
        FileReader fr = new FileReader(f); // Creation of File Reader object
        BufferedReader file = new BufferedReader(fr); // Creation of BufferedReader object

        int charInt=0;
        while(charInt!=-1){
            
            charInt = file.read();
            
            System.out.println((char) charInt+"-->"+charInt);
        }
            
			//System.out.println(selectedFile.getCanonicalPath());
		}
    }
}
