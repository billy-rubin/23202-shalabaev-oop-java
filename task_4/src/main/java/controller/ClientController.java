package controller;

import model.entities.Player;
import net.Client;
import net.GameState;
import net.PlayerInput;
import view.ClientGameView;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.*;

public class ClientController implements KeyListener {
    private final Client client;
    private final String playerId;
    private final LinkedList<ControllerCommand> activeCommands = new LinkedList<>();
    private final ClientGameView clientGameView;
    private volatile boolean running = true;

    public ClientController(Client client, String playerId, ClientGameView clientGameView) {
        this.client = client;
        this.playerId = playerId;
        this.clientGameView = clientGameView;
        clientGameView.setPlayerId(playerId);
        clientGameView.addKeyListener(this);
        clientGameView.setFocusable(true);
        clientGameView.requestFocusInWindow();
        new Thread(this::networkLoop).start();
    }

    private void networkLoop() {
        while (running) {
            try {
                PlayerInput playerInput = new PlayerInput(playerId, new LinkedList<>(activeCommands));
                client.sendPlayerData(playerInput);
                GameState gameState = client.receiveSavedData();
                SwingUtilities.invokeLater(() -> {
                    clientGameView.setGameState(gameState);
                    clientGameView.repaint();
                    if (!gameState.isRunning()) {
                        running = false;
                        JOptionPane.showMessageDialog(clientGameView, "Game Over");
                    }
                });
                Thread.sleep(20); // 50 Hz
            } catch (Exception e) {
                running = false;
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(clientGameView, "Connection lost."));
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ControllerCommand cmd = getCommandFromKey(e.getKeyCode());
        if (cmd != null && !activeCommands.contains(cmd)) {
            activeCommands.add(cmd);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ControllerCommand cmd = getCommandFromKey(e.getKeyCode());
        if (cmd != null) {
            activeCommands.remove(cmd);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    private ControllerCommand getCommandFromKey(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W: return ControllerCommand.UP;
            case KeyEvent.VK_S: return ControllerCommand.DOWN;
            case KeyEvent.VK_A: return ControllerCommand.LEFT;
            case KeyEvent.VK_D: return ControllerCommand.RIGHT;
            case KeyEvent.VK_SPACE: return ControllerCommand.SHOOT;
            default: return null;
        }
    }
}