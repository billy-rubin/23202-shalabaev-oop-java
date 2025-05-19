package controller;

import javax.swing.*;
import model.Game;
import viewier.*;

public class MainController {
    private JFrame frame;

    public MainController() {
        frame = new JFrame("ALAbuGA.game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }

    public void showMenu() {
        MenuView menuView = new MenuView();
        frame.setContentPane(menuView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new MenuController(menuView, this);
    }

    public void startGame() {
        Game game = new Game();
        GameView gameView = new GameView(game);
        frame.setContentPane(gameView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        new GameController(game, gameView);
    }
}
