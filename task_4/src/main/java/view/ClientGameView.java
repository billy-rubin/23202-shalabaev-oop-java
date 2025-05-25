package view;

import model.Game;
import model.Obstacle;
import model.entities.Movable;
import model.entities.Sprite;
import net.GameState;
import javax.swing.*;
import java.awt.*;

public class ClientGameView extends JPanel {
    private GameState gameState;
    private String playerId;
    private Image background;
    private HUDView hud;
    private Game game;

    public ClientGameView(Game game) {
        this.game = game;
        setPreferredSize(new Dimension(1525, 789));
        background = new ImageIcon(getClass().getResource("/images/background.png")).getImage();
        hud = new HUDView(game);
        setLayout(null);
        hud.setBounds(10, 10, 200, 100);
        add(hud);
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        hud.update(gameState, playerId);
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(background, 0, 0, this);

        if (gameState != null) {
            for (Movable movable : gameState.movables()) {
                if (((Sprite) movable).isVisible()) {
                    graphics2D.drawImage(((Sprite) movable).getImage(),
                            ((Sprite) movable).getX(),
                            ((Sprite) movable).getY(),
                            ((Sprite) movable).getWidth(),
                            ((Sprite) movable).getHeight(), this);
                }
            }
            for (Obstacle obstacle : gameState.obstacles()) {
                if (obstacle.isVisibility()) {
                    graphics2D.drawImage(obstacle.getImage(), obstacle.getX(), obstacle.getY(), this);
                }
            }
            if (!gameState.isRunning()) {
                graphics2D.setColor(Color.WHITE);
                graphics2D.setFont(new Font("Arial", Font.BOLD, 50));
                graphics2D.drawString("Game Over", 600, 400);
            }
        }
    }
}