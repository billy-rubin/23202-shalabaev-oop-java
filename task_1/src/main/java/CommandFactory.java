import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class CommandFactory {
    Properties properties;
    CommandFactory() {
        this.properties = new Properties();
        readConfigFile();
    }
    public void readConfigFile(){
        try {
            InputStream input = CommandFactory.class.getResourceAsStream("/command.properties");
            properties.load(input);
        } catch (NullPointerException e){
            System.err.println("Invalid configuration" + e.getMessage());
        } catch (IOException e){
            System.err.println("Failed to load cfg file" + e.getMessage());
        }
    }
    public Command createCommand(String command){
        String commandName = properties.getProperty(command);
        System.out.println(command + commandName);

        if (commandName.isEmpty()){
            throw new IllegalArgumentException("Error" + command + "There's no such command in config file");
        }
        Command cmd;
        try {
            cmd = (Command) Class.forName(commandName).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e){
            throw new RuntimeException(e.getMessage());
        }
        return cmd;
    }
}


