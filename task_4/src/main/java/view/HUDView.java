package view;

import model.entities.Movable;
import model.entities.Player;
import net.GameState;
import net.PlayerHandler;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class HUDView extends JPanel {
    private JLabel killsLabel, waveLabel, livesLabel, scoreLabel;

    public HUDView() {
        setLayout(new GridLayout(4, 1));
        killsLabel = new JLabel("Kills: 0");
        waveLabel = new JLabel("Wave: 0");
        livesLabel = new JLabel("Lives: N/A");
        scoreLabel = new JLabel("SCORE: 0");

        add(scoreLabel);
        add(killsLabel);
        add(waveLabel);
        add(livesLabel);
        setOpaque(false);
    }

    public void update(GameState gameState, String[] playerIds) {
        scoreLabel.setText("SCORE: " + gameState.score());
        killsLabel.setText("Kills: " + gameState.kills());
        waveLabel.setText("Wave: " + gameState.waveNumber());

        // Формируем строку с жизнями всех игроков
        StringBuilder livesText = new StringBuilder("Lives: ");
        boolean hasPlayers = false;
        for (String playerId : playerIds) {
            int lives = 0;
            for (Movable movable : gameState.movables()) {
                if (movable instanceof Player){
                    Player player = (Player) movable;
                    if (player.getId().equals(playerId)) {
                        lives = player.getHealth();
                        hasPlayers = true;
                        break;
                    }
                }
            }
            String shortId = playerId.length() > 4 ? playerId.substring(0, 4) : playerId; // первые 4 символа айди
            livesText.append("P").append(shortId).append(": ").append(lives).append(", ");
        }

        if (hasPlayers) {
            livesText.setLength(livesText.length() - 2);
        } else {
            livesText.append("None");
        }

        livesLabel.setText(livesText.toString());
        System.out.println("HUD обновлён: " + livesText + ", Players: " + Arrays.toString(playerIds));
        repaint();
    }
}