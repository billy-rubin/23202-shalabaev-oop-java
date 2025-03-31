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

    public Command createCommand(String command) {
        String propertyKey = properties.getProperty(command);
        if (propertyKey == null) {
            System.out.println(command + " such command doesn't exist, check that command is typed correctly");
            throw new IllegalArgumentException("Error " + command + " There's no such command in config file");
        }
        Command cmd;
        try {
            cmd = (Command) Class.forName(propertyKey).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("CLassNotFound Exception");
        } catch (NoSuchMethodException e){
            throw new RuntimeException("NoSuchMethod Exception");
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException e){
            throw new RuntimeException(e.getMessage());
        }

        return cmd;
    }
}


