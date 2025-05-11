package controller;

import model.Game;
import viewier.GameView;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GameController {
    private Game game;
    private GameView gameView;
    private Timer timer;

    public GameController(Game game, GameView gameView) {
        this.game = game;
        this.gameView = gameView;
        gameView.addKeyListener(new PlayerController(game.getPlayer()));
        gameView.setFocusable(true);
        gameView.requestFocusInWindow();
        timer = new Timer(16, (ActionEvent e) -> {
            game.update();
            gameView.repaint();
        });
        timer.start();
    }
}