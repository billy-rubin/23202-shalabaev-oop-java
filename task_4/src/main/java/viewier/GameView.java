package viewier;

import model.Game;
import model.Obstacle;
import model.entities.missliles.Missile;
import model.entities.Sprite;
import javax.swing.*;
import java.awt.*;

public class GameView extends JPanel {
    private Game game;
    private Image background;
    private HUDView hud;

    public GameView(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(1525, 789));
        background = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        hud = new HUDView(game);
        setLayout(null);
        hud.setBounds(10, 10, 200, 100);
        add(hud);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D;
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        for (Sprite enemy : game.getEnemies()) {
            if (enemy.isVisible()) {
                g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
            }
        }
        for (Obstacle obstacle : game.getObstacles()) { // Added
            if (obstacle.isVisibility()) {
                g.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(), this);
            }
        }
        if (game.getPlayer().isVisible()) {
            g.drawImage(game.getPlayer().getImage(), game.getPlayer().getX(), game.getPlayer().getY(), this);
        }
        for (Missile missile : game.getMissiles()) {
            if (missile.isVisible()) {
                g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
            }
        }
        hud.update();
        if (!game.isRunning()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", 600, 400);
        }
    }
}
