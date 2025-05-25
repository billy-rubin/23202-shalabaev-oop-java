package view;

import model.Game;
import model.entities.Movable;
import model.entities.Player;
import net.GameState;

import javax.swing.*;
import java.awt.*;

public class HUDView extends JPanel {
    private JLabel killsLabel, waveLabel, livesLabel, scoreLabel;

    public HUDView() {
        setLayout(new GridLayout(4, 1));
        killsLabel = new JLabel("Kills: 0");
        waveLabel = new JLabel("Wave: 0");
        livesLabel = new JLabel("Lives: 3");
        scoreLabel = new JLabel("SCORE: 0");

        add(scoreLabel);
        add(killsLabel);
        add(waveLabel);
        add(livesLabel);
        setOpaque(false);
    }

    public void update(GameState gameState, String playerId) {
        scoreLabel.setText("SCORE: " + gameState.score());
        killsLabel.setText("Kills: " + gameState.kills());
        waveLabel.setText("Wave: " + gameState.waveNumber());
        int lives = 0;
        for (Movable movable : gameState.movables()) {
            if (movable instanceof Player && ((Player) movable).getId().equals(playerId)) {
                lives = ((Player) movable).getHealth();
                break;
            }
        }
        livesLabel.setText("Lives: " + lives);
    }
}
