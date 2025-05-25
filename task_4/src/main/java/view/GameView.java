package view;

import model.Game;
import model.Obstacle;
import model.entities.Movable;
import model.entities.Player;
import model.entities.Sprite;
import net.GameState;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameView extends JPanel {
    private final Game game;
    private Image background;
    private HUDView hud;

    public GameView(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(1525, 789));
        background = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        hud = new HUDView();
        setLayout(null);
        hud.setBounds(10, 10, 200, 100);
        add(hud);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(background, 0, 0, getWidth(), getHeight(), this);

        for (Movable movable : new ArrayList<>(game.getMovables())) {
            if (movable instanceof Sprite && ((Sprite) movable).isVisible()) {
                Sprite sprite = (Sprite) movable;
                graphics2D.drawImage(sprite.getImage(), sprite.getX(), sprite.getY(),
                        sprite.getWidth(), sprite.getHeight(), this);
            }
        }

        for (Obstacle obstacle : new ArrayList<>(game.getObstacles())) {
            if (obstacle.isVisibility()) {
                graphics2D.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(),
                        obstacle.getWidth(), obstacle.getHeight(), this);
            }
        }
        GameState gameState = game.getGameState();
        hud.update(gameState, getPlayerIds());
        System.out.println("Отрисован GameView, GameState: " + gameState);

        if (!game.isRunning()) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.setFont(new Font("Arial", Font.BOLD, 50));
            graphics2D.drawString("Game Over", 600, 400);
        }
    }

    private String[] getPlayerIds() {
        return game.getPlayers().stream()
                .map(Player::getId)
                .toArray(String[]::new);
    }
}