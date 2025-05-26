package net;

import controller.ControllerCommand;
import java.io.Serializable;
import java.util.LinkedList;

public class PlayerInput implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;

    private final LinkedList<ControllerCommand> commands;

    public String getId() {
        return id;
    }

    public LinkedList<ControllerCommand> getCommands() {
        return commands;
    }

    public PlayerInput(String id, LinkedList<ControllerCommand> commands) {
        this.id = id;
        this.commands = commands;
    }
}