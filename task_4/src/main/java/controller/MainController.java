package controller;

import javax.swing.*;
import model.Game;
import net.Client;
import net.GameServer;
import view.*;

import java.util.UUID;

public class MainController {
    private JFrame frame;
    private Game game;
    private GameServer gameServer;

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
        game.setGodMode(SettingsController.isNewState());
        new GameController(game, gameView, null);
    }
    public void startHost() {
        gameServer = new GameServer(game);
        //game = new Game();
        GameView gameView = new GameView(game);
        frame.setContentPane(gameView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        new GameController(game, gameView, gameServer);
    }

    public void startClient(String hostIp) {
        try {
            Client client = new Client(hostIp);
            String playerId = UUID.randomUUID().toString();
            ClientGameView clientGameView = new ClientGameView(game);
            frame.setContentPane(clientGameView);
            frame.pack();
            frame.setLocationRelativeTo(null);
            new ClientController(client, playerId, clientGameView);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Failed to connect to host.");
        }
    }
}