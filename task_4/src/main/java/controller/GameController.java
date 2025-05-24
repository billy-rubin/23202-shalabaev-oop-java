package controller;

import model.Game;
import view.GameView;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameController {
    private Game game;
    private GameView gameView;
    private Timer timer;
    private boolean gameOverHandled = false;

    public GameController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        gameView.addKeyListener(new PlayerController(game.getPlayer()));
        gameView.setFocusable(true);
        gameView.requestFocusInWindow();
        timer = new Timer(20, (ActionEvent e) -> {
            game.update();
            gameView.repaint();
            if (!game.isRunning() && !gameOverHandled) {
                handleGameOver();
            }
        });
        timer.start();
    }

    private void handleGameOver() {
        gameOverHandled = true;
        timer.stop(); // Stop the game loop
        String name = JOptionPane.showInputDialog(gameView, "Game Over! Enter your name:");
        if (name != null && !name.trim().isEmpty()) {
            game.getScoreManager().saveScoreWithName(name);
        }
    }
}