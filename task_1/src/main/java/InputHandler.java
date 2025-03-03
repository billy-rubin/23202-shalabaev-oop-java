import java.io.*;
public class InputHandler {
    public BufferedReader reader;
    InputHandler(String[] args){
        try {
            if (args.length > 0){
                reader = new BufferedReader(new FileReader(args[0]));
            } else {
                reader = new BufferedReader(new InputStreamReader(System.in));
            }
        } catch (IOException e){
            throw new RuntimeException("Error, while reading file");
        }
    }
}
