import java.util.List;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileWriter;

public class Main { 
    public static void main(String[] args)  {
        
        String[] productFile = FileInput.readFile(args[0],false, false);  // Take from user as product information.
        
        String[] purchaseFile = FileInput.readFile(args[1], false, false);  // Take from user as purchase information.
        
        String outputFile = args[2];   // Take output name from user.
        
        List<String> lastGym = Plug.machine(productFile, purchaseFile);   // Take all string output list as array.
    
        // Write all ouput to file.
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile)))  {
            for (String line : lastGym)  {
                writer.write(line);  
            }
        } catch (IOException e)  {
            e.printStackTrace();
        }
       
        
    }

   
    
}