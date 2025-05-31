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
        clientGameView.setLocalPlayerId(playerId);
        clientGameView.addKeyListener(this);
        clientGameView.setFocusable(true);
        clientGameView.requestFocusInWindow();
        new Thread(this::networkLoop).start();
        System.out.println("ClientController запущен для игрока: " + playerId);
    }

    private void networkLoop() {
        while (running) {
            try {
                PlayerInput playerInput = new PlayerInput(playerId, new LinkedList<>(activeCommands));
                client.sendPlayerData(playerInput);
                System.out.println("Клиент отправил PlayerInput: " + playerInput);
                GameState gameState = client.receiveSavedData();
                if (gameState != null) {
                    SwingUtilities.invokeLater(() -> {
                        clientGameView.setGameState(gameState);
                        clientGameView.repaint();
                        if (!gameState.isRunning()) {
                            running = false;
                            JOptionPane.showMessageDialog(clientGameView, "Game Over");
                        }
                    });
                } else {
                    System.out.println("Пропущен GameState, продолжаем цикл");
                }
                Thread.sleep(20); // 50 Hz
            } catch (Exception e) {
                System.err.println("Критическая ошибка в networkLoop: " + e.getMessage());
                e.printStackTrace();
                running = false;
                SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(clientGameView, "Connection lost: " + e.getMessage()));
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        ControllerCommand cmd = getCommandFromKey(e.getKeyCode());
        if (cmd != null && !activeCommands.contains(cmd)) {
            activeCommands.add(cmd);
            System.out.println("Команда добавлена: " + cmd + " для игрока " + playerId);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        ControllerCommand cmd = getCommandFromKey(e.getKeyCode());
        if (cmd != null) {
            activeCommands.remove(cmd);
            System.out.println("Команда удалена: " + cmd + " для игрока " + playerId);
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