import commands.Command;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class CommandFactory {
    private Properties properties;

    CommandFactory() {
        this.properties = new Properties();
        readConfigFile();
    }

    public void readConfigFile(){
        try {
            InputStream input = CommandFactory.class.getResourceAsStream("/command.properties");
            properties.load(input);
        } catch (NullPointerException e){
            throw new RuntimeException("Invalid configuration, " + e.getMessage());
        } catch (IOException e){
            throw new RuntimeException("Failed to load cfg file, " + e.getMessage());
        }
    }

    public Command createCommand(String command){
        String commandName = properties.getProperty(command);
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


