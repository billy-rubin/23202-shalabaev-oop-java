package controller;

import model.Game;
import model.entities.Player;
import net.GameServer;
import view.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameController {
    private final Game game;
    private final GameView gameView;
    private final GameServer gameServer; // null for single-player
    private final Timer timer;
    private boolean gameOverHandled = false;

    public GameController(Game game, GameView gameView, GameServer gameServer) {
        this.game = game;
        this.gameView = gameView;
        this.gameServer = gameServer;

        // Привязываем PlayerController только к локальному игроку хоста
        if (gameServer != null) {
            for (Player player : game.getPlayers()) {
                if (player.getId().startsWith("host-")) {
                    gameView.addKeyListener(new PlayerController(player));
                    break;
                }
            }
        } else {
            for (Player player : game.getPlayers()) {
                gameView.addKeyListener(new PlayerController(player));
            }
        }

        gameView.setFocusable(true);
        gameView.requestFocusInWindow();

        timer = new Timer(20, this::updateGame);
        timer.start();
        System.out.println("GameController запущен, сетевой режим: " + (gameServer != null));
    }

    private void updateGame(ActionEvent e) {
        game.update();
        if (gameServer != null) {
            gameServer.sendUpdate(game.getGameState());
            System.out.println("Отправлен GameState: " + game.getGameState());
        }
        gameView.repaint();
        if (!game.isRunning() && !gameOverHandled) {
            handleGameOver();
        }
    }

    private void handleGameOver() {
        gameOverHandled = true;
        timer.stop();
        System.out.println("Игра завершена, обработка Game Over");

        StringBuilder scoresText = new StringBuilder("Game Over! Scores:\n");
        for (Player player : game.getPlayers()) {
            scoresText.append("Player ").append(player.getId(), 0, 4)
                    .append(": ").append(player.getHealth() > 0 ? "Alive" : "Dead")
                    .append("\n");
        }

        String name = JOptionPane.showInputDialog(gameView,
                scoresText + "\nEnter your name for high score:");
        if (name != null && !name.trim().isEmpty()) {
            game.getScoreManager().saveScoreWithName(name);
        }
    }
}