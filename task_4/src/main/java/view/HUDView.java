package view;

import model.Game;

import javax.swing.*;
import java.awt.*;

public class HUDView extends JPanel {
    private Game game;
    private JLabel killsLabel, waveLabel, livesLabel, scoreLabel;

    public HUDView(Game game) {
        this.game = game;
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

    public void update() {
        scoreLabel.setText("SCORE: " + game.getScoreManager().getScore());
        killsLabel.setText("Kills: " + game.getKills());
        waveLabel.setText("Wave: " + game.getWaveNumber());
        livesLabel.setText("Lives: " + game.getLives());
    }
}
