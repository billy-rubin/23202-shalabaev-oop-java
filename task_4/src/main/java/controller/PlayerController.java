package controller;

import model.entities.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class PlayerController implements KeyListener {
    private Player player;
    private Map<Integer, ControllerCommand> keyBinds;
    private LinkedList<ControllerCommand> activeCommands;

    public PlayerController(Player player) {
        this.player = player;
        this.keyBinds = new HashMap<>();
        this.activeCommands = new LinkedList<>();
        keyBinds.put(KeyEvent.VK_W, ControllerCommand.UP);
        keyBinds.put(KeyEvent.VK_S, ControllerCommand.DOWN);
        keyBinds.put(KeyEvent.VK_A, ControllerCommand.LEFT);
        keyBinds.put(KeyEvent.VK_D, ControllerCommand.RIGHT);
        keyBinds.put(KeyEvent.VK_SPACE, ControllerCommand.SHOOT);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ControllerCommand cmd = keyBinds.get(e.getKeyCode());
        if (cmd != null && !activeCommands.contains(cmd)) {
            activeCommands.add(cmd);
        }
        player.setActiveCommands(new LinkedList<>(activeCommands));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ControllerCommand cmd = keyBinds.get(e.getKeyCode());
        if (cmd != null) {
            activeCommands.remove(cmd);
        }
        player.setActiveCommands(new LinkedList<>(activeCommands));
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}