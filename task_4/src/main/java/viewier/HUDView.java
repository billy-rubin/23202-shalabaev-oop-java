package viewier;

import model.Game;

import javax.swing.*;
import java.awt.*;

public class HUDView extends JPanel {
    private Game game;
    private JLabel killsLabel, waveLabel, timeLabel, livesLabel;

    public HUDView(Game game) {
        this.game = game;
        setLayout(new GridLayout(4, 1));
        killsLabel = new JLabel("Kills: 0");
        waveLabel = new JLabel("Wave: 0");
        timeLabel = new JLabel("Next Wave: 5s");
        livesLabel = new JLabel("Lives: 3");
        add(killsLabel);
        add(waveLabel);
        add(timeLabel);
        add(livesLabel);
        setOpaque(false);
    }

    public void update() {
        killsLabel.setText("Kills: " + game.getKills());
        waveLabel.setText("Wave: " + game.getWaveNumber());
        timeLabel.setText("Next Wave: " + game.getTimeToNextWave() + "s");
        livesLabel.setText("Lives: " + game.getLives());
    }
}
