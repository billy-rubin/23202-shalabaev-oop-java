package controller;

import java.io.Serializable;

public class Key implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean isKeyActive;
    private final ControllerCommand controllerCommand;

    public Key(ControllerCommand controllerCommand) {
        this.controllerCommand = controllerCommand;
    }

    public void setKeyActive() {
        isKeyActive = true;
    }
    public void setKeyNotActive() {
        isKeyActive = false;
    }

    public boolean isKeyActive() {
        return isKeyActive;
    }
    public ControllerCommand getName() {
        return controllerCommand;
    }
}
