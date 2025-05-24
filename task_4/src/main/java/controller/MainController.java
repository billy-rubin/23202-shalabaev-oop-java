package controller;

import javax.swing.*;
import model.Game;
import view.*;

public class MainController {
    private JFrame frame;
    private Game game;

    public MainController() {
        frame = new JFrame("ALAbuGA.game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        game = new Game(); // Initialize Game instance
    }

    public void showMenu() {
        MenuView menuView = new MenuView();
        frame.setContentPane(menuView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new MenuController(menuView, this, game); // Pass Game instance
    }

    public void startGame() {
        GameView gameView = new GameView(game); // Use existing Game instance
        frame.setContentPane(gameView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        new GameController(game, gameView);
    }
}